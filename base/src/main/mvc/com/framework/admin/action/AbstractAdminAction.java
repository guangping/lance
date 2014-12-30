package com.framework.admin.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 * User: lance
 * Date: 2014-12-30 15:40
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/admin")
public class AbstractAdminAction {
    protected final String JSON = "common/data";
}
