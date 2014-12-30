package com.framework.admin.action;

import com.alibaba.fastjson.JSONObject;
import com.framework.admin.pojo.SysUser;
import com.framework.database.pojo.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: lance
 * Date: 2014-12-30 17:56
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/public")
public class LoginAction {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "admin/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, SysUser user) {
        Result result = new Result();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken();
        token.setUsername(user.getUserName());
        token.setPassword(user.getPassword().toCharArray());

        try {
            subject.login(token);
            result.setSuccess(true);
            result.setMsg("success!");
        } catch (IncorrectCredentialsException ice) {
            result.setCode("3");
            result.setMsg("密码错误!");
            ice.printStackTrace();
        } catch (LockedAccountException lae) {
            result.setCode("3");
            result.setMsg("密码错误!");
            lae.printStackTrace();
        } catch (AuthenticationException ae) {
            result.setCode("3");
            result.setMsg("密码错误!");
            ae.printStackTrace();
        } catch (Exception e) {
            result.setCode("3");
            result.setMsg("密码错误!");
            e.printStackTrace();
        }
        request.setAttribute("data", JSONObject.toJSONString(result));
        return "common/data";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "common/data";
    }
}
