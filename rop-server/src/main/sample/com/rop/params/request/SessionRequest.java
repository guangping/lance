/**
 * 版权声明：中图一购网络科技有限公司 版权所有 违者必究 2012 
 * 日    期：12-7-14
 */
package com.rop.params.request;

import com.rop.annotation.IgnoreSign;
import com.rop.params.base.BaseRopRequest;
import com.rop.params.response.SessionResponse;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * 功能说明：
 * </pre>
 *
 * @author 陈雄华
 * @version 1.0
 */
public class SessionRequest extends BaseRopRequest<SessionResponse> {

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
        return "user.getSession";
    }
}

