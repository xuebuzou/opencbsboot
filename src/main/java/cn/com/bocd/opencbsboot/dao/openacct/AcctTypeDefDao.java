package cn.com.bocd.opencbsboot.dao.openacct;

import cn.com.bocd.opencbsboot.entity.AcctTypeDef;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AcctTypeDefDao {
    int deleteByPrimaryKey(String acctType);

    int insert(AcctTypeDef record);

    int insertSelective(AcctTypeDef record);

    List<AcctTypeDef> selectByPrimaryKey(String acctType);

    int updateByPrimaryKeySelective(AcctTypeDef record);

    int updateByPrimaryKey(AcctTypeDef record);
}