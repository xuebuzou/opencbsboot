package cn.com.bocd.opencbsboot.web.http;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OpenAcctProgressController {
    @GetMapping("/zg/home/openacct/progress")
    public String renderAcctProgress() {
        return "module/openacct/progress";
    }
}
