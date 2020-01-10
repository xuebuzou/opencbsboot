package cn.com.bocd.opencbsboot.service.sys;

import cn.com.bocd.opencbsboot.dao.sys.UserDao;
import cn.com.bocd.opencbsboot.entity.sys.RetDTO;
import cn.com.bocd.opencbsboot.entity.sys.UserVO;
import cn.com.bocd.opencbsboot.tool.security.MD5Utils;
import org.apache.catalina.User;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.sun.javafx.font.FontResource.SALT;

@Service
public class UserService {
    @Autowired
	private UserDao userDao;
	
	public List<UserVO> getByMap(Map<String,Object> map) {
	    return userDao.getByMap(map);
	}
	
	public UserVO getById(Integer id) {
		return userDao.getById(id);
	}

	@Transactional
	public RetDTO create(UserVO user) {
		RetDTO ret = new RetDTO();
		try {
			user.setPassword(MD5Utils.encrypt(user.getUsername(),"bocd"));
			userDao.create(user);
			userDao.createUserRole(user);
			userDao.createUserDep(user);
		}catch (Exception e){
			e.printStackTrace();
			ret.setFailRet("新建用户失败,错误原因为:	"+e.getMessage());
			return ret;
		}
		return ret;
	}
	
	public UserVO update(UserVO user) {
		userDao.update(user);
		userDao.updateUserRole(user);
		userDao.updateUserDep(user);
		return user;
	}
	
	public int delete(Integer id) {
		return userDao.delete(id);
	}

	public UserVO getByUserName(String userName) {
		return userDao.getByUserName(userName);
	}

	public RetDTO queryUserInfo(UserVO u){
		RetDTO ret = new RetDTO();
		ret.setRows(userDao.queryUserInfo(u));
		return ret;
	}

	public Integer resetPwd(UserVO user){
		user.setPassword(MD5Utils.encrypt(user.getUsername(),"bocd"));
		return userDao.resetPwd(user);
	}

}