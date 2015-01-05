package com.framework.shiro.credential;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-07-09 15:50
 * To change this template use File | Settings | File Templates.
 */
public class DefaultCredentialsMatcher extends HashedCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        //认证密钥
        String tokenCredentials = String.valueOf(usernamePasswordToken.getPassword());

        String accountCredentials = String.valueOf(info.getCredentials());
        tokenCredentials.equals(accountCredentials);
        return true;
    }
}
