package generate;

import generate.ZgErrorLog;

public interface ZgErrorLogDao {
    int insert(ZgErrorLog record);

    int insertSelective(ZgErrorLog record);
}