package cn.com.bocd.opencbsboot.cddata.handler.impl;


import cn.com.bocd.opencbsboot.cddata.handler.CDHandler;
import cn.com.bocd.opencbsboot.cddata.helper.CDUtils;
import cn.com.bocd.opencbsboot.cddata.helper.CompositeData;
import cn.com.bocd.opencbsboot.controller.dispatcher.Dispatcher;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component(value = "cdHandler")
public class CDHandlerImpl implements CDHandler {
    private static final Logger log = Logger.getLogger(CDHandler.class);
    @Autowired
    @Qualifier("openCbsDispatcher")
    private Dispatcher dispatcher;

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public byte[] doHandle(byte[] req) {
        try {
            String sreq = new String(req, "utf-8");
            CompositeData cd = CDUtils.fromXml(sreq);
            CompositeData resp = dispatcher.doDispatch(cd);
            return CDUtils.toXml(resp).getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            //TODO def a exception
            throw new RuntimeException(e);
        }
    }

}
