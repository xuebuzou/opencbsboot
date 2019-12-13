package cn.com.bocd.opencbsboot;

import cn.com.bocd.opencbsboot.service.biz.cif.impl.CifPointBizService;
import cn.com.bocd.opencbsboot.web.server.NettyServer;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@ServletComponentScan
public class OpencbsbootApplication {
    private static final Logger logger = Logger.getLogger(OpencbsbootApplication.class);
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(OpencbsbootApplication.class, args);
        Thread t = new Thread(context.getBean(NettyServer.class));
        t.start();
        logger.info("服务已启动...");
    }
}
