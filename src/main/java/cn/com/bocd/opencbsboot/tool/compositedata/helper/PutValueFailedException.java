package cn.com.bocd.opencbsboot.tool.compositedata.helper;

public class PutValueFailedException extends Exception {
    public PutValueFailedException() {
        super();
    }

    public PutValueFailedException(String msg) {
        super(msg);
    }

    public PutValueFailedException(Throwable t) {
        super(t);
    }

    public PutValueFailedException(String msg, Throwable t) {
        super(msg, t);
    }
}
