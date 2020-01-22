package cn.com.bocd.opencbsboot.web.http;

import cn.com.bocd.opencbsboot.entity.ReservInfo;
import cn.com.bocd.opencbsboot.entity.sys.RetDTO;
import cn.com.bocd.opencbsboot.service.openacct.OpenAcctService;
import cn.com.bocd.opencbsboot.service.sys.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class OpenAcctProgressController {
    @Autowired
    OpenAcctService oaService;
    @Autowired
    ParamService paramService;

    @GetMapping("/zg/home/openacct/progress")
    public String renderAcctProgress() {
        return "module/openacct/progress/progress";
    }

    @PostMapping("/zg/home/openacct/progress/datagrid")
    @ResponseBody
    public RetDTO datagrid(ReservInfo param) {
        RetDTO ret = new RetDTO();
        try{
            ret.setRows(oaService.selectByParam(param));
        }catch (Exception e){
            e.printStackTrace();
            ret.setFailRet(e.getMessage());
        }finally {
            return ret;
        }
    }

    @GetMapping("/zg/home/openacct/progress/detail")
    public String renderDetail() {
        return "module/openacct/progress/detail";
    }

    @GetMapping("/zg/home/openacct/progress/init")
    @ResponseBody
    public RetDTO init(ReservInfo param) {
        return new RetDTO();
    }
}
