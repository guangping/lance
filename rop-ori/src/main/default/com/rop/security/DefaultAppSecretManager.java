package com.rop.security;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-06-27 11:08
 * To change this template use File | Settings | File Templates.
 */
public class DefaultAppSecretManager implements AppSecretManager {
    @Override
    public String getSecret(String appKey) {
        return "123";
    }

    @Override
    public boolean isValidAppKey(String appKey) {
        return true;
    }
}
