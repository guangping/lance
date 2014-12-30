package com.framework.admin.pojo;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: lance
 * Date: 2014-12-30 17:59
 * To change this template use File | Settings | File Templates.
 */
public class SysUser implements Serializable {

    private String userName;

    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
