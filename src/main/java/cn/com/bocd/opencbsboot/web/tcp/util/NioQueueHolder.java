package cn.com.bocd.opencbsboot.web.tcp.util;

import java.util.concurrent.BlockingQueue;

public class NioQueueHolder {
    private static volatile BlockingQueue queue;

    public static BlockingQueue getQueue() {
        return queue;
    }

    public static void setQueue(BlockingQueue queue) {
        NioQueueHolder.queue = queue;
    }
}
