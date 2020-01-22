package generate;

import generate.ReservStatusDef;

public interface ReservStatusDefDao {
    int deleteByPrimaryKey(String status);

    int insert(ReservStatusDef record);

    int insertSelective(ReservStatusDef record);

    ReservStatusDef selectByPrimaryKey(String status);

    int updateByPrimaryKeySelective(ReservStatusDef record);

    int updateByPrimaryKey(ReservStatusDef record);
}