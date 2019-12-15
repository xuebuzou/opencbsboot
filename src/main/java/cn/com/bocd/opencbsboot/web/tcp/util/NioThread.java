package cn.com.bocd.opencbsboot.web.tcp.util;

import cn.com.bocd.opencbsboot.tool.compositedata.handler.CDHandler;
import cn.com.bocd.opencbsboot.exception.other.NoServiceFoundException;
import cn.com.bocd.opencbsboot.exception.other.NullResponseException;
import cn.com.bocd.opencbsboot.web.tcp.util.session.Session;
import cn.com.bocd.opencbsboot.web.tcp.util.session.SessionDef;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

import java.net.SocketAddress;

public class NioThread implements Runnable {
    private static final Logger logger = Logger.getLogger(NioThread.class);
    private final ChannelHandlerContext ctx;
    private byte[] req;
    private CDHandler cdHandler;

    public NioThread(byte[] req, ChannelHandlerContext ctx, CDHandler cdHandler) {
        this.req = req;
        this.ctx = ctx;
        this.cdHandler = cdHandler;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    @Override
    public void run() {
        ByteBuf out = null;
        try {
            // create session
            Session s = Session.getSession();
            SocketAddress rsa = this.ctx.channel().remoteAddress();
            SocketAddress lsa = this.ctx.channel().localAddress();

            s.set(SessionDef.REQUEST_REMOTE_ADDR, rsa);
            s.set(SessionDef.REQUEST_LOCAL_ADDR, lsa);
            s.set(SessionDef.RAW_REQUEST, req);

            logger.info("请求数据   :"+new String(req));
            byte[] resp = cdHandler.doHandle(req);
            logger.info("响应数据   :"+new String(resp));
            if (resp == null) {
                throw new NullResponseException("response is null");
            } else {
                out = NioKit.getOutBytes(resp);
            }
        } catch (NoServiceFoundException e) {

            logger.error("catch NoServiceFoundException", e);

            // TODO: return a response : no service found
        } catch (Exception e) {

            logger.error("catch exception", e);

        } finally {
            Session.clearSession();
        }
        if (out != null) {
//            try {
////                logger.info("response data is ");
////                logger.info(new String(out.array(), "utf-8"));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//                throw new RuntimeException(e);
//            }
        }
        synchronized (this.ctx) {
            Boolean flag = NioSync.SyncMap.get(ctx);
            if (flag != null && flag) {
                NioSync.SyncMap.remove(ctx);
                if (out != null) {
                    ctx.writeAndFlush(out).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.close();
                }
            } else {

                logger.error("timeout, output not send");

            }
        }

    }
}
