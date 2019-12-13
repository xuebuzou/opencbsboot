package cn.com.bocd.opencbsboot.exception.other;

public class NullResponseException extends RuntimeException {
    public NullResponseException() {
        super();
    }

    public NullResponseException(String msg) {
        super(msg);
    }

    public NullResponseException(Throwable t) {
        super(t);
    }

    public NullResponseException(String msg, Throwable t) {
        super(msg, t);
    }
}
