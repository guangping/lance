package com.framework.http;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-03-13 09:18
 * To change this template use File | Settings | File Templates.
 */
public class Configuration implements HttpConnectionConfiguration {
    private String connectUrl = null;
    private int httpConnectionTimeout = 5;
    private int httpReadTimeout = 90;
    private int httpConnectRetryCount = 3;//重发次数
    private int httpConnectRetryInterval = 16;
    private int sleepTimeOfServerInUpgrade = 300;
    private Map<String, String> reqHeader;
    private int httpReconnectInterval = 86100;
    private int minThreads = 5;
    private int maxThreads = 100;
    private int queueSize = 50000;

    public void setHttpConnectionTimeout(int httpConnectionTimeout) {
        this.httpConnectionTimeout = httpConnectionTimeout;
    }

    public int getHttpConnectionTimeout() {
        return this.httpConnectionTimeout;
    }

    public int getHttpReadTimeout() {
        return this.httpReadTimeout;
    }

    public void setHttpReadTimeout(int httpReadTimeout) {
        this.httpReadTimeout = httpReadTimeout;
    }

    public void setHttpConnectRetryCount(int httpConnectRetryCount) {
        this.httpConnectRetryCount = httpConnectRetryCount;
    }

    public int getHttpConnectRetryCount() {
        return this.httpConnectRetryCount;
    }

    public void setConnectUrl(String connectUrl) {
        this.connectUrl = connectUrl;
    }

    public String getConnectUrl() {
        return this.connectUrl;
    }

    public void setHttpConnectRetryInterval(int httpConnectRetryInterval) {
        this.httpConnectRetryInterval = httpConnectRetryInterval;
    }

    public int getHttpConnectRetryInterval() {
        return this.httpConnectRetryInterval;
    }

    public void setSleepTimeOfServerInUpgrade(int sleepSecond) {
        this.sleepTimeOfServerInUpgrade = sleepSecond;
    }

    public int getSleepTimeOfServerInUpgrade() {
        return this.sleepTimeOfServerInUpgrade;
    }

    public void setHttpReconnectInterval(int httpReconnectInterval) {
        this.httpReconnectInterval = httpReconnectInterval;
    }

    public int getHttpReconnectInterval() {
        return this.httpReconnectInterval;
    }

    public void setRequestHeader(Map<String, String> reqHeader) {
        this.reqHeader = reqHeader;
    }

    public Map<String, String> getRequestHeader() {
        return this.reqHeader;
    }

    public void setMinThreads(int minThreads) {
        this.minThreads = minThreads;
    }

    public int getMinThreads() {
        return this.minThreads;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public int getMaxThreads() {
        return this.maxThreads;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    public int getQueueSize() {
        return this.queueSize;
    }

}
