package cn.com.bocd.opencbsboot.web.util.rpc.impl;


import cn.com.bocd.opencbsboot.tool.compositedata.helper.CDUtils;
import cn.com.bocd.opencbsboot.tool.compositedata.helper.CompositeData;
import cn.com.bocd.opencbsboot.web.util.rpc.BoundTransformer;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class ESBBoundTransformer implements BoundTransformer {

    private static final Logger logger = Logger.getLogger(ESBBoundTransformer.class);
    private void readFromInputStream(InputStream in, byte[] readBuffer, int offset, int len) throws IOException {
        int realLen = in.read(readBuffer, offset, len);
        if (0 != len && -1 == realLen) {
            throw new IOException("cannot read bytes any more.");
        }
        else if (realLen != len)
        {
            readFromInputStream(in, readBuffer, offset + realLen, len - realLen);
        }
    }

    @Override
    public void compositeData2Byte(CompositeData req, OutputStream os) throws IOException {
        String xml = CDUtils.toXml(req);
        int length = xml.getBytes().length;
        os.write((lpadZero(length) + xml).getBytes());
    }

    private static String lpadZero(int length) {
        return String.format("%08d", length);
    }

    @Override
    public CompositeData byte2CompositeData(InputStream is) throws IOException {
        byte[] buf = new byte[8];
        if (0 >= is.read(buf,0,8)) return null;
        int msgLen  = Integer.valueOf(new String(buf));
        if (0 >= msgLen) return null;
        byte[] content = new byte[msgLen];
        logger.info("content.toString():\n"+content.toString());
        readFromInputStream(is, content, 0, msgLen);
        logger.info("content\n"+content);
        return CDUtils.fromXml(new String(content));
    }
}
