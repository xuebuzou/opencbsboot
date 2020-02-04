package cn.com.bocd.opencbsboot.exception;

import cn.com.bocd.opencbsboot.service.sys.LogService;
import cn.com.bocd.opencbsboot.tool.BeanUtils;
import cn.com.bocd.opencbsboot.tool.compositedata.helper.CompositeData;

public class ZgPtException extends Exception implements ZgException {
    public String retCode;
    public String retMsg;
    private CompositeData compositeData;
    public ZgPtException(String retMsg, CompositeData compositeData) {
        this.retCode = "账管系统内部错误";
        this.retMsg = retMsg;
        this.compositeData = compositeData;
        LogService logService = BeanUtils.getBean(LogService.class);
        logService.writeErrorLog(this);

    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public CompositeData getCompositeData() {
        return compositeData;
    }

    public void setCompositeData(CompositeData compositeData) {
        this.compositeData = compositeData;
    }
}
