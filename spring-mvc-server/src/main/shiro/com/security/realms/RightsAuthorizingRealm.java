package com.security.realms;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-07-09 15:29
 * To change this template use File | Settings | File Templates.
 */
public class RightsAuthorizingRealm extends AuthorizingRealm {
    private final String ADMIN = "admin";
    private final String TEST = "test";
    private final String MAX = "max";

    private Logger logger = LoggerFactory.getLogger(RightsAuthorizingRealm.class);

    /*
    * 授权方法
    * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Subject subject = SecurityUtils.getSubject();
        logger.debug("当前用户：" + subject.getPrincipal());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (null != subject) {
            if (ADMIN.equals(String.valueOf(subject.getPrincipal()))) {
                info.addRole("admin");
                info.addStringPermission("role:create,delete,edit");
            }
            if (TEST.equals(String.valueOf(subject.getPrincipal()))) {
                info.addStringPermission("role:create");
            }
            if (MAX.equals(String.valueOf(subject.getPrincipal()))) {
                info.addRole("admin");
                info.addStringPermission("role:delete");
            }
        }
        return info;
    }

    /*
    * 认证方法 返回认证对象
    * **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String userName = String.valueOf(token.getPrincipal());
        String password = String.valueOf(token.getPassword());
        System.out.println("密码:" + password);
        if (token.getPrincipal().equals(ADMIN) || token.getPrincipal().equals(MAX) || token.getPrincipal().equals(TEST)) {
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userName, "e10adc3949ba59abbe56e057f20f883e", getName());

            this.setSession("currentUser", token);
            return info;
        }
        return null;
    }

    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     */
    private void setSession(Object key, Object value) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            logger.debug("Session默认超时时间为{}毫秒", session.getTimeout());
            if (null != session) {
                session.setAttribute(key, value);
            }
        }
    }
}
