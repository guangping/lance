package com.framework.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: lance
 * Date: 2014-12-12 11:22
 * To change this template use File | Settings | File Templates.
 * <p/>
 * 登陆辅助
 */
public class LoginUtils {
    private Logger logger = LoggerFactory.getLogger(getClass());
    public static final String CURRENT_USER = "CURRENT_USER";  //当前用户key
    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     */
    public static void setSession(Object key, Object value) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            if (null != session) {
                session.setAttribute(key, value);
            }
        }
    }



}
