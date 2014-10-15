package com.framework.web.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-10-15 16:39
 * To change this template use File | Settings | File Templates.
 */
public class AccessLog implements Serializable {

    private String userName;

    private String jsessionId;

    private String ip;

    private String userAgent;

    private String accept;

    private String url;

    private Map<String, List<String>> headers;

    private  Map<String, String[]> params;

    private String referer;

    public AccessLog() {
    }

    public AccessLog(String jsessionId, String ip, String userAgent, String accept, String url, Map<String, List<String>> headers, Map<String, String[]> params, String referer) {
        this.jsessionId = jsessionId;
        this.ip = ip;
        this.userAgent = userAgent;
        this.accept = accept;
        this.url = url;
        this.headers = headers;
        this.params = params;
        this.referer = referer;
    }

    public String getJsessionId() {
        return jsessionId;
    }

    public void setJsessionId(String jsessionId) {
        this.jsessionId = jsessionId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public Map<String, String[]> getParams() {
        return params;
    }

    public void setParams(Map<String, String[]> params) {
        this.params = params;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
