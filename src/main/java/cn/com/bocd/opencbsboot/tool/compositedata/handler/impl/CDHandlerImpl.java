package cn.com.bocd.opencbsboot.tool.compositedata.handler.impl;


import cn.com.bocd.opencbsboot.tool.compositedata.handler.CDHandler;
import cn.com.bocd.opencbsboot.tool.compositedata.helper.CDUtils;
import cn.com.bocd.opencbsboot.tool.compositedata.helper.CompositeData;
import cn.com.bocd.opencbsboot.dispatcher.Dispatcher;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component(value = "cdHandler")
public class CDHandlerImpl implements CDHandler {
    private static final Logger logger = Logger.getLogger(CDHandler.class);
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
            logger.info("req data: \n" + new String(CDUtils.toXml(cd, true).getBytes("utf-8")));
            CompositeData resp = dispatcher.doDispatch(cd);
            logger.info("resp data: \n" + new String(CDUtils.toXml(resp, true).getBytes("utf-8")));
            return CDUtils.toXml(resp).getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            //TODO def a exception
            throw new RuntimeException(e);
        }
    }

}
