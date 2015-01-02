package com.framework.interceptor;

import com.framework.web.utils.WebUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: lance
 * Date: 2015-01-02 13:59
 * To change this template use File | Settings | File Templates.
 * 权限处理
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        System.out.println("url===>"+WebUtils.getRequestURI(request));
        return super.preHandle(request, response, handler);
    }
}
