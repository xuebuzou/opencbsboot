package cn.com.bocd.opencbsboot.web.tcp.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;

public class NioKit {

    private static final Logger logger = Logger.getLogger(NioKit.class);

    public static ByteBuf getOutBytes(byte[] resp) {
        int len = resp.length;
        String slen = "" + len;
        if (slen.length() > 8) {

            logger.error("response too big:");

            // TODO:
            return null;
        } else {
            try {
                ByteBuf out = Unpooled.buffer(len + 8);
                int loopcnt = 8 - slen.length();
                for (int i = 0; i < loopcnt; i++) {
                    out.writeBytes("0".getBytes("utf-8"));
                }
                out.writeBytes(slen.getBytes("utf-8"));
                out.writeBytes(resp);
                return out;
            } catch (UnsupportedEncodingException e) {

                logger.error("should never get this", e);

                return null;
            }
        }
    }
}
