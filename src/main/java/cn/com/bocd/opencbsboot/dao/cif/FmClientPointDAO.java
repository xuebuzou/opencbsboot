package cn.com.bocd.opencbsboot.dao.cif;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @创建人 chengke
 * @创建时间 2019/12/3
 * @描述 FM_CLIENT_POINT表的DAO
 */
@Mapper
public interface FmClientPointDAO {
    int selectClientTotalPointByClientNo(@Param("clientNo") String clientNo);
    int selectClientDepositPointByClientNo(@Param("clientNo") String clientNo);
    int selectCountByClientNo(@Param("clientNo") String clientNo);
}
