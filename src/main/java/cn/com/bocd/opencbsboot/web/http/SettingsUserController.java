package cn.com.bocd.opencbsboot.web.http;

import cn.com.bocd.opencbsboot.entity.sys.RetDTO;
import cn.com.bocd.opencbsboot.entity.sys.User;
import cn.com.bocd.opencbsboot.service.sys.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SettingsUserController {
    private static final Logger logger = Logger.getLogger(SettingsUserController.class);
    @Autowired
    private UserService userService;

    @GetMapping("/zg/home/settings/user")
    public String renderSettingsUser() {
        return "module/settings/user";
    }

    @PostMapping("/zg/home/settings/user/query")
    @ResponseBody
    public RetDTO queryUser(User u) {
        return userService.queryUserInfo(u);
    }

    @PostMapping("/zg/home/settings/user/adduser")
    @ResponseBody
    public RetDTO addUser(User u) {
        return userService.create(u);
    }
}
