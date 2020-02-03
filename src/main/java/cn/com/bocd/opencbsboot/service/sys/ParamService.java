package cn.com.bocd.opencbsboot.service.sys;

import cn.com.bocd.opencbsboot.dao.openacct.AcctTypeDefDao;
import cn.com.bocd.opencbsboot.dao.openacct.ReservInfoDao;
import cn.com.bocd.opencbsboot.dao.openacct.ReservStatusDefDao;
import cn.com.bocd.opencbsboot.dao.sys.DepDao;
import cn.com.bocd.opencbsboot.entity.AcctTypeDef;
import cn.com.bocd.opencbsboot.entity.ReservStatusDef;

import cn.com.bocd.opencbsboot.entity.sys.ZgDep;
import cn.com.bocd.opencbsboot.dao.openacct.ZgDepDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @创建人 chengke
 * @创建时间 2020/1/19
 * @描述
 */
@Component
public class ParamService {
    @Autowired
    ReservInfoDao reservInfoDao;
    @Autowired
    ReservStatusDefDao reservStatusDefDao;
    @Autowired
    DepDao depDao;
    @Autowired
    AcctTypeDefDao acctTypeDefDao;

    public List<ReservStatusDef> selectAll(){
        return reservStatusDefDao.selectAll();
    }

    public List<ZgDep> qryDepInfo(){
        return depDao.qryDepInfo(new HashMap());
    }
    public List<AcctTypeDef> qryAcctTypeDef(){
        return acctTypeDefDao.selectByPrimaryKey(null);
    }
}

