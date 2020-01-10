package cn.com.bocd.opencbsboot.service.sys;

import cn.com.bocd.opencbsboot.dao.sys.DepDao;
import cn.com.bocd.opencbsboot.entity.sys.Role;
import cn.com.bocd.opencbsboot.entity.sys.ZgDep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DepService {
    @Autowired
    private DepDao depDao;

    public List<ZgDep> qryDepInfo(Map<String,Object> map) {
        return depDao.qryDepInfo(map);
    }
}
