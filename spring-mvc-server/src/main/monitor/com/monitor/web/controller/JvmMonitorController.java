/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.monitor.web.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>User: lance
 * <p>Date: 13-5-27 下午6:50
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/main/admin/monitor")
@RequiresPermissions("monitor:jvm:*")
public class JvmMonitorController {

    @RequestMapping(value = "/jvm")
    public ModelAndView execute() {
        ModelAndView view = new ModelAndView();
        view.setViewName("admin/monitor/jvm/index");
        return view;
    }


}
