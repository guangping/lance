package com.rop.params.request;

import com.rop.annotation.IgnoreSign;
import com.rop.params.base.BaseRopRequest;
import com.rop.params.response.LoginResponse;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-07-01 13:43
 * To change this template use File | Settings | File Templates.
 */

public class LoginRequest extends BaseRopRequest<LoginResponse> {

    @NotNull
    private String userName;


    @IgnoreSign
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


    @Override
    public String getApiMethodName() {
        return "user.login";
    }
}
