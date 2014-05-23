package com.rop.pojo;

import com.rop.client.exception.ApiException;
import com.rop.consts.RopClientConsts;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-22 21:50
 * To change this template use File | Settings | File Templates.
 *
 *入参请求父类
 */
public abstract class RopRequest<T extends RopResponse> implements Serializable {

    private String format =String.valueOf(RopClientConsts.json);

    private String locale = "zh_CN";

    private String method;

    private String version = "1.0";

    private String appKey;

    private String appSecret;

    private String sign;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getMethod() {
        this.method=getApiMethodName();
        return method;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }


    /*
    *
    *请求前的验证
    * **/
    public abstract void check()throws ApiException;

    /*
    * 请求对应的apiname
    * */
    public abstract String getApiMethodName();


}
