package com.framework.http;

import java.util.Map;

public abstract interface HttpConnectionConfiguration
{
    public abstract String getConnectUrl();

    public abstract int getHttpConnectionTimeout();

    public abstract int getHttpReadTimeout();

    public abstract int getHttpConnectRetryCount();//重发次数

    public abstract int getHttpConnectRetryInterval();

    public abstract int getSleepTimeOfServerInUpgrade();

    public abstract int getHttpReconnectInterval();

    public abstract Map<String, String> getRequestHeader();
}