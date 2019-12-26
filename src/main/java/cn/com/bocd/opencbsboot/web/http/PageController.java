package cn.com.bocd.opencbsboot.web.http;

import cn.com.bocd.opencbsboot.entity.sys.ResponseBo;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class PageController {
    private static final Logger logger = Logger.getLogger(PageController.class);

    @GetMapping("/zg")
    public String index() {
        return "index";
    }

    @GetMapping("/unAuthorized")
    @ResponseBody
    public String unAuthorized() {
        return "你没有该交易权限，请联系管理员";
    }

    @GetMapping("/zg/home")
    public String home() {
        return "home";
    }

    @GetMapping("/zg/home/openacct")
    public String openAcctProgress() {
        return "module/openacct";
    }

    @RequestMapping(value = "/checkLogin")
    @ResponseBody
    public ResponseBo login(String username,String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
            return ResponseBo.ok();
        } catch (UnknownAccountException e) {
            return ResponseBo.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return ResponseBo.error(e.getMessage());
        } catch (LockedAccountException e) {
            return ResponseBo.error(e.getMessage());
        } catch (AuthenticationException e) {
            return ResponseBo.error("认证失败！");
        }
    }
}

