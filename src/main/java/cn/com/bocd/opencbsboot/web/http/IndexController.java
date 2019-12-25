package cn.com.bocd.opencbsboot.web.http;

import cn.com.bocd.opencbsboot.entity.sys.ResponseBo;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class IndexController {
    private static final Logger logger = Logger.getLogger(IndexController.class);

    @GetMapping("/index")
    public String index() {
        logger.info("render index");
        return "index";
    }

    @GetMapping("/zg")
    public String index2() {
        logger.info("render index2");
        return "index2";
    }

    @GetMapping("/zg/home")
    public String home() {
        logger.info("render home");
        return "home";
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
//        return ResponseBo.ok();
    }

