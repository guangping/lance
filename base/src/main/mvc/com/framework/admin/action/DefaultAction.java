package com.framework.admin.action;

import com.alibaba.fastjson.JSONObject;
import com.framework.database.pojo.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: lance
 * Date: 2014-12-30 17:09
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequiresPermissions("user:*")
public class DefaultAction extends AbstractAdminAction {

    @RequestMapping(value = "",method = RequestMethod.GET)
    public String execute(HttpServletRequest request){
        request.setAttribute("data", JSONObject.toJSONString(new Result()));

        return JSON;
    }
}
