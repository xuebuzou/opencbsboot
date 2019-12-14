package cn.com.bocd.opencbsboot.tool.compositedata.helper;

public class InvalidTypeException extends RuntimeException {
    public InvalidTypeException() {
        super();
    }

    public InvalidTypeException(String msg) {
        super(msg);
    }

    public InvalidTypeException(Throwable t) {
        super(t);
    }

    public InvalidTypeException(String msg, Throwable t) {
        super(msg, t);
    }
}
