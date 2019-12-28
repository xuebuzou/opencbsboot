package cn.com.bocd.opencbsboot.web.tcp;

import cn.com.bocd.opencbsboot.tool.compositedata.handler.CDHandler;
import cn.com.bocd.opencbsboot.web.tcp.util.NioSync;
import cn.com.bocd.opencbsboot.web.tcp.util.NioThread;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

public class NettyHandler extends ChannelInboundHandlerAdapter implements Runnable {
    private static final Logger log = Logger.getLogger(NettyHandler.class);
    protected Executor executor;
    private ByteBuf head;
    private ByteBuf body;
    private CDHandler cdHandler;
    private boolean flag; // 0: read head; 1: read body; else error;

    public NettyHandler(Executor executor, CDHandler cdHandler) {
        this.executor = executor;
        this.cdHandler = cdHandler;
        this.flag = true;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public void setExecutor(ThreadPoolExecutor executor) {
        this.executor = executor;
    }

    public ByteBuf getHead() {
        return head;
    }

    public void setHead(ByteBuf head) {
        this.head = head;
    }

    public ByteBuf getBody() {
        return body;
    }

    public void setBody(ByteBuf body) {
        this.body = body;
    }

    public CDHandler getCdHandler() {
        return cdHandler;
    }

    public void setCdHandler(CDHandler cdHandler) {
        this.cdHandler = cdHandler;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        head = Unpooled.buffer(8);
        flag = true;
    }

    private void readhead(ChannelHandlerContext ctx, ByteBuf in) {
        int hw = head.writableBytes();
        int ir = in.readableBytes();
        int toreadlen = (ir < hw) ? ir : hw;
        in.readBytes(head, toreadlen);
        if (head.readableBytes() == 8) {
            flag = false;
            byte[] buf = head.array();
            try {
                String slen = new String(buf, "utf-8");
                int len = Integer.parseInt(slen);
                body = Unpooled.buffer(len);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            if (in.readableBytes() > 0) {
                readbody(ctx, in);
            }
        }
    }

    private void readbody(ChannelHandlerContext ctx, ByteBuf in) {
        int bw = body.writableBytes();
        int ir = in.readableBytes();
        int toreadlen = (ir < bw) ? ir : bw;
        in.readBytes(body, toreadlen);
        if (body.writableBytes() == 0) {
            byte[] buf = body.array();
            doHandle(buf, ctx, cdHandler);
        }
    }

    protected void doHandle(byte[] buf, ChannelHandlerContext ctx, CDHandler cdHandler) {
        NioSync.SyncMap.put(ctx, true);
        NioThread thread = new NioThread(buf, ctx, cdHandler);
        executor.execute(thread);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            ByteBuf in = (ByteBuf) msg;
            if (flag) {
                readhead(ctx, in);
            } else {
                readbody(ctx, in);
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // TODO: timeout response
        if (cause instanceof io.netty.handler.timeout.ReadTimeoutException) {
            synchronized (ctx) {
                Boolean flag = NioSync.SyncMap.get(ctx);
                if (flag != null && flag) {
                    NioSync.SyncMap.remove(ctx);
                    log.error("timeout", cause);
                    ctx.close();
                }
            }
        } else {
            log.error("connection error, close it", cause);
            ctx.close();
        }
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

    }
}
