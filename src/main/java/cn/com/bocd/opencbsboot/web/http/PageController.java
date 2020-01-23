package cn.com.bocd.opencbsboot.web.http;

import cn.com.bocd.opencbsboot.entity.ReservInfo;
import cn.com.bocd.opencbsboot.entity.sys.ResponseBo;
import cn.com.bocd.opencbsboot.entity.sys.RetDTO;
import cn.com.bocd.opencbsboot.entity.sys.UserVO;
//import cn.com.bocd.opencbsboot.service.openacct.OpenAcctService;
import cn.com.bocd.opencbsboot.service.sys.ParamService;
import cn.com.bocd.opencbsboot.service.sys.UserService;
import cn.com.bocd.opencbsboot.tool.security.MD5Utils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@Controller
public class PageController {
    private static final Logger logger = Logger.getLogger(PageController.class);
    @Autowired
    private UserService userService;
//    @Autowired
//    OpenAcctService oaService;
    @Autowired
    ParamService paramService;

    @GetMapping("/zg")
    public String renderIndex() {
        return "index";
    }

    @GetMapping("/unAuthorized")
    @ResponseBody
    public String unAuthorized() {
        return "unAuthorized";
    }

    @GetMapping("/zg/home")
    public String renderHome() {
        return "home";
    }

    @RequestMapping(value = "/zg/login")
    @ResponseBody
    public RetDTO login(String username, String password) {
        RetDTO ret = new RetDTO();
        Subject subject = SecurityUtils.getSubject();
        password = MD5Utils.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
            ret.setData(subject.getSession().getAttribute("user"));
            return ret;
        } catch (UnknownAccountException e) {
            ret.setFailRet("账号不存在");
            return ret;
        } catch (IncorrectCredentialsException e) {
            ret.setFailRet("密码错误");
            return ret;
        } catch (LockedAccountException e) {
            ret.setFailRet("账号被锁定");
            return ret;
        } catch (AuthenticationException e) {
            ret.setFailRet("认证失败");
            return ret;
        }
    }
    @GetMapping("/zg/home/init")
    @ResponseBody
    public RetDTO init(ReservInfo param) {
        RetDTO ret = new RetDTO();
        HashMap params = new HashMap();
        try{
            params.put("reserv_status_def",paramService.selectAll());
            params.put("dep",paramService.qryDepInfo());
            params.put("acct_type_def",paramService.qryAcctTypeDef());
            ret.setResult(params);
        }catch (Exception e){
            ret.setFailRet(e.getMessage());
        }finally {
            return ret;
        }
    }
}

