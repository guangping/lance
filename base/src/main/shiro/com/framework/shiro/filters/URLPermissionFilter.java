package com.framework.shiro.filters;

import com.framework.web.utils.WebUtils;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: lance
 * Date: 2015-01-05 11:01
 * To change this template use File | Settings | File Templates.
 * 自定义权限验证 filter
 */
public class URLPermissionFilter extends PermissionsAuthorizationFilter {

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        return super.isAccessAllowed(request, response, buildPermissions(request));
    }

    /**
     * 根据请求URL产生权限字符串，这里只产生，而比对的事交给Realm
     *
     * @param request
     * @return
     */
    protected String[] buildPermissions(ServletRequest request) {
        String[] perms = new String[1];
        HttpServletRequest req = (HttpServletRequest) request;
        String method=req.getMethod();
        perms[0] = WebUtils.getRequestURI(req);
        return perms;
    }
}
