package cn.com.bocd.opencbsboot.cddata.handler;

import java.lang.reflect.InvocationTargetException;

public interface CDHandler {
    byte[] doHandle(byte[] req) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
