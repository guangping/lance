package com.security.credential;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
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
        Object tokenCredentials = token.getCredentials();
        Object accountCredentials=info.getCredentials();
       // Object accountCredentials = getCredentials(info);
        System.out.println("密码:"+tokenCredentials.toString());

        return equals(tokenCredentials, accountCredentials);
    }
}
