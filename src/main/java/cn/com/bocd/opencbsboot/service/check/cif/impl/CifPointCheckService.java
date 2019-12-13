package cn.com.bocd.opencbsboot.service.check.cif.impl;

import cn.com.bocd.opencbsboot.cddata.helper.CDUtils;
import cn.com.bocd.opencbsboot.cddata.helper.CompositeData;
import cn.com.bocd.opencbsboot.dao.cif.FmClientPointDAO;
import cn.com.bocd.opencbsboot.exception.OpenCbsException;
import cn.com.bocd.opencbsboot.service.check.cif.CifCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @创建人 chengke
 * @创建时间 2019/12/5
 * @描述 客户积分相关检查类
 */
@Service("CifPointCheckService")
public class CifPointCheckService implements CifCheckService{
    @Autowired
    private FmClientPointDAO fmClientPointDAO;
    public void isClientSign(CompositeData req, CompositeData data, CompositeData resp) throws Exception{
        String clientNo = CDUtils.getFValue(req, "CLIENT_NO");
        if(fmClientPointDAO.selectCountByClientNo(clientNo) == 0){
            throw new OpenCbsException("110450","客户未签约积分");
        }
    }
}
