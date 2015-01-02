package com.framework.main.action;

import com.alibaba.fastjson.JSONObject;
import com.framework.database.pojo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: lance
 * Date: 2015-01-02 10:42
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class DefaultAction extends AbstractAction {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        request.setAttribute("data", JSONObject.toJSONString(new Result()));

        return JSON;
    }

}
