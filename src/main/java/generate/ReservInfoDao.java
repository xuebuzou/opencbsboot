package generate;

import generate.ReservInfo;
import generate.ReservInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReservInfoDao {
    long countByExample(ReservInfoExample example);

    int deleteByExample(ReservInfoExample example);

    int deleteByPrimaryKey(String reservId);

    int insert(ReservInfo record);

    int insertSelective(ReservInfo record);

    List<ReservInfo> selectByExample(ReservInfoExample example);

    ReservInfo selectByPrimaryKey(String reservId);

    int updateByExampleSelective(@Param("record") ReservInfo record, @Param("example") ReservInfoExample example);

    int updateByExample(@Param("record") ReservInfo record, @Param("example") ReservInfoExample example);

    int updateByPrimaryKeySelective(ReservInfo record);

    int updateByPrimaryKey(ReservInfo record);
}