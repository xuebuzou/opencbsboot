package cn.com.bocd.opencbsboot.dao.openacct;

import cn.com.bocd.opencbsboot.entity.sys.ZgDep;

public interface ZgDepDao {
    int deleteByPrimaryKey(String depCode);

    int insert(ZgDep record);

    int insertSelective(ZgDep record);

    ZgDep selectByPrimaryKey(String depCode);

    int updateByPrimaryKeySelective(ZgDep record);

    int updateByPrimaryKey(ZgDep record);
}