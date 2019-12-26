package cn.com.bocd.opencbsboot.service.check.common;

import cn.com.bocd.opencbsboot.tool.compositedata.helper.CDUtils;
import cn.com.bocd.opencbsboot.tool.compositedata.helper.CompositeData;
import cn.com.bocd.opencbsboot.dao.cif.FmClientPointDAO;
import cn.com.bocd.opencbsboot.exception.OpenCbsException;
import cn.com.bocd.opencbsboot.service.check.cif.CifCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("CommonCheckService")
public class CommonCheckService implements CifCheckService{
//    @Autowired
//    private FmClientPointDAO fmClientPointDAO;
    public void checkTranDate(CompositeData req, CompositeData data, CompositeData resp) throws Exception{
    }
}
