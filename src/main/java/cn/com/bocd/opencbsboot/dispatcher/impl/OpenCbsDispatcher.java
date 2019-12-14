package cn.com.bocd.opencbsboot.dispatcher.impl;

import cn.com.bocd.opencbsboot.tool.compositedata.helper.CDUtils;
import cn.com.bocd.opencbsboot.tool.compositedata.helper.CompositeData;
import cn.com.bocd.opencbsboot.tool.compositedata.helper.StringField;
import cn.com.bocd.opencbsboot.dispatcher.Dispatcher;
import cn.com.bocd.opencbsboot.dispatcher.util.*;
import cn.com.bocd.opencbsboot.exception.OpenCbsException;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.xmlpull.mxp1.MXParser;
import org.xmlpull.v1.XmlPullParserException;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.xmlpull.v1.XmlPullParser.*;

/**
 * @创建人 chengke
 * @创建时间 2019/11/29
 * @描述 调度器实现类
 */
@Component("openCbsDispatcher")
public class OpenCbsDispatcher implements Dispatcher, ApplicationContextAware {
    private static final Logger logger = Logger.getLogger(OpenCbsDispatcher.class);
    Map<String, Flow> handlerMapping = new HashMap<String, Flow>();
    @Value(value = "${flow.scanBasePackege}")
    private String scanBasePackege;
    private ApplicationContext context;

    public Map<String, Flow> getHandlerMapping() {
        return handlerMapping;
    }

    public void setHandlerMapping(Map<String, Flow> handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public String getScanBasePackege() {
        return scanBasePackege;
    }

    public void setScanBasePackege(String scanBasePackege) {
        this.scanBasePackege = scanBasePackege;
    }

    @Override
    @PostConstruct
    public void init() throws IOException {
        doLoadConfig(getScanBasePackege());
    }

    public void doLoadConfig(String scanBasePackege) throws IOException {
        File dir = new File(getClass().getResource("/" + scanBasePackege).getFile());
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                doLoadConfig(scanBasePackege + "/" + file.getName());
            } else {
                if (!file.getName().endsWith(".xml")) {
                    continue;
                }
                doParseConfig(file.getAbsolutePath());
            }
        }
    }


    public void doParseConfig(String fileName) throws IOException {
        MXParser parser = new MXParser();
        try {
            parser.setInput(new FileInputStream(new File(fileName)), "UTF-8");
            int eventType;
            FlowParserContext ctx = new FlowParserContext();
            while (END_DOCUMENT != (eventType = parser.next())) {
                switch (eventType) {
                    case START_TAG:
                        String tagName = parser.getName();
                        if ("flowconfig".equals(tagName)) {
                            ctx.setMsgType(parser.getAttributeValue(0));
                            ctx.setMsgCode(parser.getAttributeValue(1));
                            ctx.setSrcType(parser.getAttributeValue(2));
                        } else if ("component".equals(tagName)) {
                            ctx.getComponents().add(new BizComponent(parser.getAttributeValue(1), parser.getAttributeValue(2)));
                        }
                        break;
                    case END_TAG:
                        if ("flowconfig".equals(parser.getName())) {
                            Flow flow = new Flow();
                            flow.setMsgType(ctx.getMsgType());
                            flow.setMsgCode(ctx.getMsgCode());
                            flow.setSrcType(ctx.getSrcType());
                            flow.setComponents((ArrayList<BizComponent>) ctx.getComponents());
                            ctx.setMsgType("");
                            ctx.setMsgCode("");
                            ctx.setSrcType("");
                            ctx.setComponents(null);
                            handlerMapping.put(flow.getMsgType() + "_" + flow.getMsgCode() + "_" + flow.getSrcType(), flow);
                        }
                        break;
                    default:
                        break;
                }
            }
        } catch (XmlPullParserException e) {
        }

    }

//    @Transactional
    @Override
    public CompositeData doDispatch(CompositeData req) {
        CompositeData resp = CDUtils.getRespFromReq(req);
        CompositeData data = req.deepCopy();
        req.makeReadOnly();
        try {
            String srctype = ((StringField) req.mGet("SYS_HEAD.SOURCE_TYPE")).getValue();
            String msgtype = ((StringField) req.mGet("SYS_HEAD.MESSAGE_TYPE")).getValue();
            String msgcode = ((StringField) req.mGet("SYS_HEAD.MESSAGE_CODE")).getValue();
            String reqUrl = msgtype + "_" + msgcode + "_" + srctype;
            Flow flow = handlerMapping.get(reqUrl);
            for (BizComponent component : flow.getComponents()) {
                Object serviceObj = context.getBean(component.getService());
                Method method = serviceObj.getClass().getMethod(component.getFunc(), CompositeData.class, CompositeData.class, CompositeData.class);
                method.invoke(serviceObj, req, data, resp);
            }
        }catch (Exception e){
            return doHandleException(resp,e);
        }
        CDUtils.setRespStatus(resp, "S", "000000", "Success");
        return resp;
    }

    public CompositeData doHandleException(CompositeData resp,Exception e) {
        if (e instanceof InvocationTargetException) {
            Throwable targetEx = ((InvocationTargetException) e).getTargetException();
            if (targetEx instanceof OpenCbsException) {
                OpenCbsException realEx = (OpenCbsException) targetEx;
                Class clazz = realEx.getClass();
                try {
                    Field retCodeField = clazz.getField("retCode");
                    Field retMsgField = clazz.getField("retMsg");
                    e.printStackTrace();
                    CDUtils.setRespStatus(resp, "F", (String) retCodeField.get(realEx), (String) retMsgField.get(realEx));
                    return resp;
                } catch (NoSuchFieldException | IllegalAccessException e1) {
                    e1.printStackTrace();
                    CDUtils.setRespStatus(resp, "F", "888888", "平台配置错误，请检查flow配置文件和Service类定义");
                    return resp;
                }
            } else {
                Throwable targetExCause = targetEx.getCause();
                while(targetExCause != null){
                    targetEx = targetExCause;
                    targetExCause = targetEx.getCause();
                }
                e.printStackTrace();
                CDUtils.setRespStatus(resp, "F", "999999", targetEx.getMessage());
                return resp;
            }
        } else if (e instanceof NoSuchMethodException
                || e instanceof IllegalAccessException
                || e instanceof NoSuchFieldException
                || e instanceof NoSuchBeanDefinitionException) {
            e.printStackTrace();
            CDUtils.setRespStatus(resp, "F", "888888", "平台配置错误，请检查flow配置文件和Service类定义");
            return resp;
        } else {
            e.printStackTrace();
            CDUtils.setRespStatus(resp, "F", "888888", "运行时错误");
            return resp;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
