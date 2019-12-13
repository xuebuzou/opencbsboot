package cn.com.bocd.opencbsboot.web.server;


import cn.com.bocd.opencbsboot.cddata.handler.CDHandler;
import cn.com.bocd.opencbsboot.web.handler.impl.NettyHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

@Component
public class NettyServer implements Runnable {
    private static final Logger log = Logger.getLogger(NettyServer.class);
    @Value("${netty.server.listening.port}")
    private int port;
    @Autowired
    @Qualifier("nioExecutor")
    private Executor executor;
    @Autowired
    @Qualifier("cdHandler")
    private CDHandler cdHandler;
    @Value("${netty.server.thread.poolsize}")
    private int threadPoolSize;
    @Value("${netty.server.listening.timeout}")
    private int timeout;

    public NettyServer() {
    }

    public NettyServer(int port, int threadPoolSize, int timeout, Executor executor) {
        this.port = port;
        this.threadPoolSize = threadPoolSize;
        this.timeout = timeout;
        this.executor = executor;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public CDHandler getCdHandler() {
        return cdHandler;
    }

    public void setCdHandler(CDHandler cdHandler) {
        this.cdHandler = cdHandler;
    }

    public void setServiceExecutor(Executor executor) {
        this.executor = executor;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup(this.getThreadPoolSize());
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new ReadTimeoutHandler(NettyServer.this.getTimeout()));
                            ch.pipeline().addLast(new NettyHandler(executor, cdHandler));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128);
            ChannelFuture cf = b.bind(port).sync();

            cf.channel().closeFuture().sync();
            log.error("channel close unexcpeted.");
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("listen and accept connection failed", e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
