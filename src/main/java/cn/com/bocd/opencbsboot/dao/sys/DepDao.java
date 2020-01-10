package cn.com.bocd.opencbsboot.dao.sys;

import cn.com.bocd.opencbsboot.entity.sys.ZgDep;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DepDao {
    List<ZgDep> qryDepInfo(Map param);
}
