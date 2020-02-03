package cn.com.bocd.opencbsboot.dao.openacct;


import cn.com.bocd.opencbsboot.entity.ZgErrorLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ZgErrorLogDao {
    int insert(ZgErrorLog record);

    int insertSelective(ZgErrorLog record);
}