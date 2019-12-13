package cn.com.bocd.opencbsboot.cddata.helper;

public class InvalidFieldTypeException extends RuntimeException {
    public InvalidFieldTypeException() {
        super();
    }

    public InvalidFieldTypeException(String msg) {
        super(msg);
    }

    public InvalidFieldTypeException(Throwable t) {
        super(t);
    }

    public InvalidFieldTypeException(String msg, Throwable t) {
        super(msg, t);
    }
}
