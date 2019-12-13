package cn.com.bocd.opencbsboot.exception.other;

public class NoServiceFoundException extends RuntimeException {
    public NoServiceFoundException() {
        super();
    }

    public NoServiceFoundException(String msg) {
        super(msg);
    }

    public NoServiceFoundException(Throwable t) {
        super(t);
    }

    public NoServiceFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
