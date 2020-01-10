package cn.com.bocd.opencbsboot.config;

import cn.com.bocd.opencbsboot.dao.sys.PermissionDao;
import cn.com.bocd.opencbsboot.dao.sys.UserDao;
import cn.com.bocd.opencbsboot.entity.sys.Permission;
import cn.com.bocd.opencbsboot.entity.sys.Role;
import cn.com.bocd.opencbsboot.entity.sys.UserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ShiroRealm extends AuthorizingRealm {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserVO user = userService.getByUserName((String) principalCollection.getPrimaryPrincipal());
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        for (Role userRole : user.getRoles()) {
            authorizationInfo.addRole(userRole.getName());
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName = token.getUsername();
        String password = new String((char[]) token.getCredentials());
        UserVO user = userService.getByUserName(userName);
        if (user == null) {
            throw new UnknownAccountException();
        }
        if (!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException();
        }

        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("user", user);
        String name = getName();
        return new SimpleAuthenticationInfo(userName, user.getPassword(), name);
    }

}
