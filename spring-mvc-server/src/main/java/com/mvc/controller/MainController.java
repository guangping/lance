package com.mvc.controller;

import com.alibaba.fastjson.JSONObject;
import com.mvc.pojo.LoginRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-07-08 14:21
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/main")
public class MainController {


    @RequestMapping(value = "/message.form")
    public ModelAndView message() {
        ModelAndView view = new ModelAndView();

        view.addObject("message", "注解");
        view.setViewName("hello");
        return view;
    }

    @RequestMapping(value = "/message/{arg}")
    public String message(Map<String, Object> map, @PathVariable String arg, HttpServletRequest request) {
        System.out.println("业务处理方法!" + arg + ";HttpServletRequest=====>" + request);
        map.put("message", arg);
        return "hello";
    }

    @RequestMapping(value = "/{arg}/message")
    public String message(Map<String, Object> map, @PathVariable String arg) {
        System.out.println("业务处理方法!" + arg);
        map.put("message", arg);
        return "hello";
    }

    @RequestMapping(value = "/message/json")
    public String json(Map<String, Object> params) {
        LoginRequest request = new LoginRequest();
        request.setUserName("mac");
        request.setPassword("123ed");
        params.put("data", JSONObject.toJSONString(request));
        return "json";
    }
}
