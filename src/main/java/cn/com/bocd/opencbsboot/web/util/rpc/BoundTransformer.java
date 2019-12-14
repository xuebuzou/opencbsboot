package cn.com.bocd.opencbsboot.web.util.rpc;


import cn.com.bocd.opencbsboot.tool.compositedata.helper.CompositeData;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface BoundTransformer {
    void compositeData2Byte(CompositeData req, OutputStream os) throws IOException;
    CompositeData byte2CompositeData(InputStream is) throws IOException;
}
