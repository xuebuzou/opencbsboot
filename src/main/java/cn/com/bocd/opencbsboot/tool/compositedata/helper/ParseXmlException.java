package cn.com.bocd.opencbsboot.tool.compositedata.helper;

public class ParseXmlException extends RuntimeException {
    public ParseXmlException() {
        super();
    }

    public ParseXmlException(String msg) {
        super(msg);
    }

    public ParseXmlException(Throwable t) {
        super(t);
    }

    public ParseXmlException(String msg, Throwable t) {
        super(msg, t);
    }
}
