package cn.com.bocd.opencbsboot.config;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/druid/*",
        initParams = {
                @WebInitParam(name = "allow", value = "192.168.1.20,127.0.0.1"),
                @WebInitParam(name = "deny", value = "192.168.16.111"),
                @WebInitParam(name = "loginUsername", value = "admin"),
                @WebInitParam(name = "loginPassword", value = "admin"),
                @WebInitParam(name = "resetEnable", value = "false")
        })
public class DruidStatViewServlet extends StatViewServlet {

}
