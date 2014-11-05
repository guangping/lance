package com.cms.controller.login;

import com.alibaba.fastjson.JSONObject;
import com.cms.pojo.common.Result;
import com.cms.pojo.user.SysUser;
import com.cms.service.login.LoginService;
import com.framework.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-11-04 14:25
 * To change this template use File | Settings | File Templates.
 * <p/>
 * 处理登录退出操作
 */
@Controller
@RequestMapping(value = "/common/")
public class LoginController {

    @Resource
    private LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request) {
        String userName = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        String code = request.getParameter("code");
        Result result = new Result();
        if(StringUtils.isBlank(userName) || StringUtils.isBlank(pwd)){
            result.setMsg("用户名或密码不能为空!");
            request.setAttribute("data", JSONObject.toJSONString(result));
            return "public/common/json";
        }
        if(StringUtils.isBlank(code)){
            result.setMsg("验证码不能为空!");
            request.setAttribute("data", JSONObject.toJSONString(result));
            return "public/common/json";
        }
        if (!judgeCode(request, code)) {
            result.setMsg("验证码错误!");
            request.setAttribute("data", JSONObject.toJSONString(result));
            return "public/common/json";
        }
        SysUser user = loginService.findByUserName(userName);
        if (null == user) {
            result.setMsg("用户不存在!");
            request.setAttribute("data", JSONObject.toJSONString(result));
            return "public/common/json";
        }
        if (!pwd.equals(user.getPassword())) {
            result.setMsg("用户名或密码错误!");
            request.setAttribute("data", JSONObject.toJSONString(result));
            return "public/common/json";
        }
        result.setSuccess(true);
        result.setMsg("success!");
        request.setAttribute("data", JSONObject.toJSONString(result));
        return "public/common/json";
    }

    private boolean judgeCode(HttpServletRequest request, String code) {
        HttpSession session = request.getSession();
        String vacode = session.getAttribute("rand") != null ? String.valueOf(session.getAttribute("rand")) : "";
        return (vacode == null || code == null) ? false : vacode.equals(code);
    }


    @RequestMapping(value = "/loginOut")
    public void loginOut(HttpServletRequest request) {
        request.getSession().invalidate();
    }
}
