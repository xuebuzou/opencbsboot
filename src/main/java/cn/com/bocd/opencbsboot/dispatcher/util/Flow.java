package cn.com.bocd.opencbsboot.dispatcher.util;

import java.util.ArrayList;

/**
 * @创建人 chengke
 * @创建时间 2019/12/3
 * @描述
 */
public class Flow {
    private String msgType;
    private String msgCode;
    private String srcType;
    private ArrayList<BizComponent> components = new ArrayList<>();

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

    public String getSrcType() {
        return srcType;
    }

    public void setSrcType(String srcType) {
        this.srcType = srcType;
    }

    public ArrayList<BizComponent> getComponents() {
        return components;
    }

    public void setComponents(ArrayList<BizComponent> components) {
        this.components = components;
    }
}
