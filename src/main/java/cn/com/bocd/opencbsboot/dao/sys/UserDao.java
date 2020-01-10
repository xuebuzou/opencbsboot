package cn.com.bocd.opencbsboot.dao.sys;

import cn.com.bocd.opencbsboot.entity.sys.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface UserDao {

	List<UserVO> getByMap(Map<String, Object> map);
	UserVO getById(Integer id);
	Integer create(UserVO user);
	Integer createUserRole(UserVO user);
	Integer createUserDep(UserVO user);
	int update(UserVO user);
	int updateUserRole(UserVO user);
	int updateUserDep(UserVO user);
	int resetPwd(UserVO user);
	int delete(Integer id);
	UserVO getByUserName(String userName);
	List<UserVO> queryUserInfo(UserVO user);
}