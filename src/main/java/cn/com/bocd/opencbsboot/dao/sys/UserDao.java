package cn.com.bocd.opencbsboot.dao.sys;

import cn.com.bocd.opencbsboot.entity.sys.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface UserDao {

	List<User> getByMap(Map<String, Object> map);
	User getById(Integer id);
	Integer create(User user);
	int update(User user);
	int delete(Integer id);
	User getByUserName(String userName);
	List<User> queryUserInfo(User user);
}