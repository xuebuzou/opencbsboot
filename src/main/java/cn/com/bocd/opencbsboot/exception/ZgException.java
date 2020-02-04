package cn.com.bocd.opencbsboot.exception;

import cn.com.bocd.opencbsboot.tool.compositedata.helper.CompositeData;

import java.io.PrintStream;
import java.io.PrintWriter;

public interface ZgException {
    public CompositeData getCompositeData();
    public String getRetCode();
    public String getRetMsg();
    public void printStackTrace(PrintWriter s);
}
