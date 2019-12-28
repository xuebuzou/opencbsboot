package cn.com.bocd.opencbsboot.dao.sys;

import cn.com.bocd.opencbsboot.entity.sys.Permission;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;
@Mapper
public interface PermissionDao {
    List<Permission> getByMap(Map<String, Object> map);
    Permission getById(Integer id);
    Integer create(Permission permission);
    int update(Permission permission);
    int delete(Integer id);
    List<Permission> getList();
    List<Permission> getByUserId(Integer userId);
}