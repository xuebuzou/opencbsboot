package cn.com.bocd.opencbsboot.dao.openacct;


import cn.com.bocd.opencbsboot.entity.ReservInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReservInfoDao {
    int deleteByPrimaryKey(String reservId);

    int insert(ReservInfo record);

    int insertSelective(ReservInfo record);

    ReservInfo selectByPrimaryKey(String reservId);

    List<ReservInfo> selectByParam(ReservInfo reservId);

    int updateByPrimaryKeySelective(ReservInfo record);

    int updateByPrimaryKey(ReservInfo record);
}