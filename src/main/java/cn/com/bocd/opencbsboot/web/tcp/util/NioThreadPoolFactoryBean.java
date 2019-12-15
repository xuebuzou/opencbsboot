package cn.com.bocd.opencbsboot.web.tcp.util;

//import cn.com.bocd.connection.tcp.handler.Service;

import cn.com.bocd.opencbsboot.web.tcp.TcpServer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component(value = "nioExecutor")
public class NioThreadPoolFactoryBean implements FactoryBean<Executor> {
    @Value("32")
    private int corePoolSize;
    @Value("32768")
    private int queueSize;

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    @Override
    public Executor getObject() throws Exception {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(queueSize);
        NioQueueHolder.setQueue(queue);
        return new ThreadPoolExecutor(corePoolSize, corePoolSize, 0, TimeUnit.NANOSECONDS, queue, new RejectExecution());
//        return new ThreadPoolExecutor(corePoolSize, corePoolSize, 0, TimeUnit.NANOSECONDS, new LinkedBlockingQueue<Runnable>());
    }

    @Override
    public Class<?> getObjectType() {
        return ThreadPoolExecutor.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public static class RejectExecution implements RejectedExecutionHandler {
        private static final Logger log = Logger.getLogger(TcpServer.class);

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            log.error("executor queue has no slot, reject");

            if (r instanceof NioThread) {
                //TODO: send error msg to client

                NioThread s = (NioThread) r;
                NioSync.SyncMap.remove(((NioThread) r).getCtx());
                s.getCtx().close();
            }
            throw new RejectedExecutionException();
        }
    }

}
