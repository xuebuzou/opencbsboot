package cn.com.bocd.opencbsboot.exception;

import cn.com.bocd.opencbsboot.tool.compositedata.helper.CompositeData;

/**
 * @创建人 chengke
 * @创建时间 2019/12/5
 * @描述
 */
public class OpenCbsException extends Exception {
    public String retCode;
    public String retMsg;

    public OpenCbsException(String retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public void printStack(){
        this.printStackTrace();
    }
}
