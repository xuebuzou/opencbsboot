package cn.com.bocd.opencbsboot.web.http;

import cn.com.bocd.opencbsboot.dispatcher.impl.OpenCbsDispatcher;
import cn.com.bocd.opencbsboot.entity.sys.RetDTO;
import cn.com.bocd.opencbsboot.tool.DateUtils;
import cn.com.bocd.opencbsboot.tool.compositedata.helper.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Controller
public class GatewayController {
    private static final Logger logger = Logger.getLogger(GatewayController.class);
    @Autowired
    OpenCbsDispatcher dispatcher;

    @RequestMapping("zg/gateway")
    @ResponseBody
    public RetDTO zgGateway(@RequestParam Map<String, Object> map) {
        RetDTO ret = new RetDTO();
        CompositeData cd = new CompositeData();
        CompositeData syshead = new CompositeData();
        CompositeData apphead = new CompositeData();
        syshead.put("SOURCE_TYPE", new StringField("ZG"));
        for (String key : map.keySet()) {
            if (key.equals("MESSAGE_TYPE")) {
                syshead.put("MESSAGE_TYPE", new StringField((String) map.get(key)));

            }
            if (key.equals("MESSAGE_CODE")) {
                syshead.put("MESSAGE_CODE", new StringField((String) map.get(key)));

            }
            if (key.equals("page")) {
                apphead.put("PAGE_START", new StringField((String) map.get(key)));

            }
            if (key.equals("rows")) {
                apphead.put("TOTAL_NUM", new StringField((String) map.get(key)));

            }
            cd.put(key, new StringField((String) map.get(key)));
        }
        cd.remove("rows");
        cd.remove("page");
        cd.remove("MESSAGE_CODE");
        cd.remove("MESSAGE_TYPE");
        syshead.put("SEQ_NO",new StringField("ZG"+DateUtils.getCurrDate(DateUtils.FORMAT_SIX)));
        cd.put("SYS_HEAD", syshead);
        cd.put("APP_HEAD", apphead);
        try {
            logger.info("req data: \n" + new String(CDUtils.toXml(cd, true).getBytes("utf-8")));
            CompositeData2Json(dispatcher.doDispatch(cd), ret);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public void CompositeData2Json(CompositeData cd, RetDTO r) throws UnsupportedEncodingException {
        logger.info("resp data: \n" + new String(CDUtils.toXml(cd, true).getBytes("utf-8")));
        Array arr = (Array) cd.mGet("SYS_HEAD.RET");
        Object[] objs = arr.toArray();
        CompositeData ret = (CompositeData) objs[0];
        r.setRetCode(CDUtils.getFValue(ret, "RET_CODE"));
        r.setRetMsg(CDUtils.getFValue(ret, "RET_MSG"));

        ArrayList rows = new ArrayList();
        Map<String, AtomData> data = cd.getData();
        for (String key : data.keySet()) {
            if (key.equals("SYS_HEAD") || key.equals("APP_HEAD") || key.equals("LOCAL_HEAD")) {
                continue;
            }
            if (key.equals("TOTAL")) {
                IntField intField = (IntField) data.get(key);
                r.setTotal(intField.getValue());
                continue;
            }
            AtomData atomData = data.get(key);
            if (atomData instanceof StringField) {
                //nothing to do
            } else if (atomData instanceof Array) {
                Iterator<AtomData> iterator = ((Array) atomData).iterator();
                while (iterator.hasNext()) {
                    HashMap itemmap = new HashMap();
                    CompositeData item = (CompositeData) iterator.next();
                    Map<String, AtomData> itemdata = item.getData();
                    for (String itemkey : itemdata.keySet()) {
                        StringField itemvalue = (StringField) itemdata.get(itemkey);
                        if (itemvalue == null) {
                            itemmap.put(itemkey, "");
                        } else {
                            itemmap.put(itemkey, itemvalue.getValue());
                        }
                    }
                    rows.add(itemmap);
                }
            }
        }
        r.setRows(rows);
    }

}
