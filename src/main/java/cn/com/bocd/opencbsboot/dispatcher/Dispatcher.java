package cn.com.bocd.opencbsboot.dispatcher;

import cn.com.bocd.opencbsboot.tool.compositedata.helper.CompositeData;

import java.io.IOException;

public interface Dispatcher {
    void init() throws IOException;
    CompositeData doDispatch(CompositeData req);
}
