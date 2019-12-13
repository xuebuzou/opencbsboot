package cn.com.bocd.opencbsboot.aop;


import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class SystemLogAspect {
    private static final Logger logger = Logger.getLogger(SystemLogAspect.class);

    @Pointcut("execution(* cn.com.bocd.opencbsboot.service.*.*(..))")
    public void serviceMethodAspect() {
    }

    @Before("serviceMethodAspect()")
    public void doBefore(JoinPoint joinPoint) throws InterruptedException {
    	logger.info("开始执行"+joinPoint.getSignature().getName());
    }


}
