package cn.com.bocd.opencbsboot.entity;

import java.io.Serializable;

/**
 * ZG_ERROR_LOG
 *
 * @author
 */

public class ZgErrorLog implements Serializable {
    private static final long serialVersionUID = 1L;
    private String msgType;
    private String msgCode;
    private String srcType;
    private String userId;
    private String stackMsg;
    private String retMsg;
    private String seqNo;
    private String reqXml;
    private String logTime;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStackMsg() {
        return stackMsg;
    }

    public void setStackMsg(String stackMsg) {
        this.stackMsg = stackMsg;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getReqXml() {
        return reqXml;
    }

    public void setReqXml(String reqXml) {
        this.reqXml = reqXml;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }
}