package cn.com.bocd.opencbsboot.dao.openacct;

import cn.com.bocd.opencbsboot.entity.ReservStatusDef;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReservStatusDefDao {
    int deleteByPrimaryKey(String status);

    int insert(ReservStatusDef record);

    int insertSelective(ReservStatusDef record);

    ReservStatusDef selectByPrimaryKey(String status);

    List<ReservStatusDef> selectAll();

    int updateByPrimaryKeySelective(ReservStatusDef record);

    int updateByPrimaryKey(ReservStatusDef record);
}