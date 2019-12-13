package cn.com.bocd.opencbsboot.exception.other;

public class BzRollBackException extends Exception {
    private String retCode;
    private String retMsg;

    public String getRetCode() {
        return retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public BzRollBackException(String retCode, String retMsg) {
        super(retMsg);
        this.retMsg = retMsg;
        this.retCode = retCode;
    }

    public BzRollBackException(String retCode, String retMsg, Throwable cause) {
        super(retMsg, cause);
        this.retMsg = retMsg;
        this.retCode = retCode;
    }
}
