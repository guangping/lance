package com.framework.admin.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: lance
 * Date: 2014-12-30 15:40
 * To change this template use File | Settings | File Templates.
 * 用于管理后台
 */
@Controller
@RequestMapping("/admin")
public class AbstractAdminAction {
    protected final String JSON = "common/data";

    @ModelAttribute
    public void modeAttribute(HttpServletRequest request) throws Exception {
        String uri = request.getRequestURI().replace(request.getContextPath(), "");
        System.out.println("uri==>" + uri);
    }
}
