package cn.com.bocd.opencbsboot.dao.cif;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FmClientPointDAO {
    int selectClientTotalPointByClientNo(@Param("clientNo") String clientNo);
    int selectClientDepositPointByClientNo(@Param("clientNo") String clientNo);
    int selectCountByClientNo(@Param("clientNo") String clientNo);
}
