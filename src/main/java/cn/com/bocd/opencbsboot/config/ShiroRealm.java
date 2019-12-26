package cn.com.bocd.opencbsboot.config;

import cn.com.bocd.opencbsboot.dao.sys.PermissionDao;
import cn.com.bocd.opencbsboot.dao.sys.UserDao;
import cn.com.bocd.opencbsboot.entity.sys.Permission;
import cn.com.bocd.opencbsboot.entity.sys.Role;
import cn.com.bocd.opencbsboot.entity.sys.User;
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
    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userService;
    @Autowired
    private PermissionDao permissionService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = userService.getByUserName((String) principalCollection.getPrimaryPrincipal());
        SecurityUtils.getSubject().getSession().setAttribute(String.valueOf(user.getId()), SecurityUtils.getSubject().getPrincipals());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for(Role userRole:user.getRoles()){
            info.addRole(userRole.getName());
        }
        for(Permission permission:permissionService.getByUserId(user.getId())){
                info.addStringPermission(permission.getName());
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName=token.getUsername();
        logger.info(userName+token.getPassword());
        User user = userService.getByUserName(userName);
        if (user != null) {
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("user", user);
            return new SimpleAuthenticationInfo(userName,user.getPassword(),getName());
        } else {
            return null;
        }
    }

}
