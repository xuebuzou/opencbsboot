package cn.com.bocd.opencbsboot.service.sys;

import cn.com.bocd.opencbsboot.dao.sys.UserDao;
import cn.com.bocd.opencbsboot.entity.sys.RetDTO;
import cn.com.bocd.opencbsboot.entity.sys.User;
import cn.com.bocd.opencbsboot.tool.security.MD5Utils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.sun.javafx.font.FontResource.SALT;

@Service
public class UserService {
    @Autowired
	private UserDao userDao;
	
	public List<User> getByMap(Map<String,Object> map) {
	    return userDao.getByMap(map);
	}
	
	public User getById(Integer id) {
		return userDao.getById(id);
	}
	
	public RetDTO create(User user) {
		RetDTO ret = new RetDTO();
		try {
			user.setPassword(MD5Utils.encrypt(user.getUsername(),"bocd"));
			userDao.create(user);
		}catch (Exception e){
			e.printStackTrace();
			ret.setFailRet("新建用户失败:/n"+e.getMessage());
			return ret;
		}
		return ret;
	}
	
	public User update(User user) {
		userDao.update(user);
		return user;
	}
	
	public int delete(Integer id) {
		return userDao.delete(id);
	}

	public User getByUserName(String userName) {
		return userDao.getByUserName(userName);
	}

	public RetDTO queryUserInfo(User u){
		RetDTO ret = new RetDTO();
		ret.setRows(userDao.queryUserInfo(u));
		return ret;
	}

}