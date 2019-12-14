package cn.com.bocd.opencbsboot.tool.compositedata.handler;

import java.lang.reflect.InvocationTargetException;

public interface CDHandler {
    byte[] doHandle(byte[] req) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
