package cn.com.bocd.opencbsboot.controller.helper;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建人 chengke
 * @创建时间 2019/12/3
 * @描述
 */
public class FlowParserContext<E> {
    private static final Logger logger = Logger.getLogger(FlowParserContext.class);
    private String msgType;
    private String msgCode;
    private String srcType;
    private List<E> components = new ArrayList<E>();

    public List<E> getComponents() {
        return components;
    }

    public void setComponents(List<E> components) {
        this.components = components;
    }

    public String getSrcType() {
        return srcType;
    }

    public void setSrcType(String srcType) {
        this.srcType = srcType;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }
}


