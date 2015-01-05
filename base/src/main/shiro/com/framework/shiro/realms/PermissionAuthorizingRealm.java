package com.framework.shiro.realms;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-07-09 15:29
 * To change this template use File | Settings | File Templates.
 */
public class PermissionAuthorizingRealm extends AuthorizingRealm {
    private Logger logger = LoggerFactory.getLogger(getClass());


    /*
    * 授权方法
    * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Subject subject = SecurityUtils.getSubject();
        logger.debug("当前用户：" + subject.getPrincipal());
        System.out.println("授权方法");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //此 部分根据数据库配置添加用户的权限信息
        info.addStringPermission("user");
        info.addStringPermission("/admin/index.html");

        return info;
    }

    /*
    * 认证方法 根据用户名查询库中的密码 返回认证对象
    * **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String userName = String.valueOf(token.getPrincipal());
        System.out.println("认证方法");

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userName, "", getName());
        return info;
    }
}
