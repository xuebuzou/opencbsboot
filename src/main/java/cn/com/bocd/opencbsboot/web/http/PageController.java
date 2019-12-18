package cn.com.bocd.opencbsboot.web.http;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class PageController {
    @RequestMapping("/login")
    public String renderLoginPage(){
        return "login";
    }
    @RequestMapping("/home")
    public String renderHomePage(){
        return "home";
    }
}
