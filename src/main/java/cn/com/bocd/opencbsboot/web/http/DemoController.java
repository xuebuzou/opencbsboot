package cn.com.bocd.opencbsboot.web.http;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @RequestMapping("/index")
    public String demo(){
        return "index";
    }
}
