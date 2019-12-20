package cn.com.bocd.opencbsboot;

import cn.com.bocd.opencbsboot.web.tcp.TcpServer;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@ServletComponentScan
//@EnableAutoConfiguration(exclude = {
//        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
//})
public class OpencbsbootApplication {
    private static final Logger logger = Logger.getLogger(OpencbsbootApplication.class);
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(OpencbsbootApplication.class, args);
        Thread t = new Thread(context.getBean(TcpServer.class));
        t.start();
        logger.info("服务已启动...");
    }
}
