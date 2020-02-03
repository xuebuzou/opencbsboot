package cn.com.bocd.opencbsboot.tool;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

public class BeanUtils {
    public static ApplicationContext applicationContext;
    public static <T> T getBean(Class<T> c) {
        return applicationContext.getBean(c);
    }
}


