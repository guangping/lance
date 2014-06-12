package com.framework.dubbo.impl;

import com.framework.dubbo.ILoginService;
import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-06-11 21:09
 * To change this template use File | Settings | File Templates.
 */
public class LoginServiceImpl implements ILoginService {
    @Override
    public boolean login(String user, String password) {
        boolean result = false;
        if (StringUtils.isNotBlank(user) || StringUtils.isNotBlank(password)) {
            result = true;
        }
        return result;
    }
}
