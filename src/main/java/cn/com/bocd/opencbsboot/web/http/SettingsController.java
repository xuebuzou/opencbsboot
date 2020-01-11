package cn.com.bocd.opencbsboot.web.http;

import cn.com.bocd.opencbsboot.entity.sys.RetDTO;
import cn.com.bocd.opencbsboot.entity.sys.UserVO;
import cn.com.bocd.opencbsboot.service.sys.DepService;
import cn.com.bocd.opencbsboot.service.sys.RoleService;
import cn.com.bocd.opencbsboot.service.sys.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SettingsController {
    private static final Logger logger = Logger.getLogger(SettingsController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private DepService depService;

    @GetMapping("/zg/home/settings/user")
    public String renderSettingsUser() {
        return "module/settings/user";
    }

    @PostMapping("/zg/home/settings/user/query")
    @ResponseBody
    public RetDTO queryUser(UserVO u) {
        return userService.queryUserInfo(u);
    }

    @PostMapping("/zg/home/settings/user/adduser")
    @ResponseBody
    public RetDTO addUser(@Valid UserVO u, Errors errors) {
        Map<String,Object> errMap = new HashMap<>();
        List<ObjectError> oes = errors.getAllErrors();
        if(oes.isEmpty()){
            return userService.create(u);
        }else{
            ObjectError oe = oes.get(0);
            RetDTO errDTO = new RetDTO();
            errDTO.setFailRet(oe.getDefaultMessage());
            return errDTO;
        }

    }

    @PostMapping("/zg/home/settings/user/resetpwd")
    @ResponseBody
    public RetDTO resetPwd(UserVO u, Errors errors) {
        RetDTO ret = new RetDTO();
        try {
            userService.resetPwd(u);
        }catch (Exception e){
            ret.setFailRet(e.getMessage());
        }finally {
            return ret;
        }
    }

    @GetMapping("/zg/home/settings/chgpwd")
    public String renderSettingsChgpwd() {
        return "module/settings/chgpwd";

    }

    @PostMapping("/zg/home/settings/user/upduser")
    @ResponseBody
    @Transactional
    public RetDTO updUser(@Valid UserVO u, Errors errors) {
        RetDTO ret = new RetDTO();
        Map<String,Object> errMap = new HashMap<>();
        List<ObjectError> oes = errors.getAllErrors();
        if(oes.isEmpty()){
            try {
                userService.update(u);
            }catch (Exception e){
                ret.setFailRet(e.getMessage());
            }finally {
                return ret;
            }
        }else{
            ObjectError oe = oes.get(0);
            ret.setFailRet(oe.getDefaultMessage());
            return ret;
        }

    }

    @GetMapping("/zg/home/settings/user/init")
    @ResponseBody
    public RetDTO init() {
        RetDTO ret = new RetDTO();
        HashMap params = new HashMap();
        try{
//            ret.setRows(roleService.getByMap(new HashMap<>()));
            params.put("role",roleService.getByMap(new HashMap<>()));
            params.put("dep",depService.qryDepInfo(new HashMap<>()));
            ret.setResult(params);
        }catch (Exception e){
            ret.setFailRet(e.getMessage());
        }finally {
            return ret;
        }
    }
}
