package cn.com.bocd.opencbsboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @RequestMapping("demo")
    public String demo(){
        return "demo";
    }
}
