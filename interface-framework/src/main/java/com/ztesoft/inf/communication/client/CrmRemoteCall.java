package com.ztesoft.inf.communication.client;

public abstract interface CrmRemoteCall
{
  public abstract Object remoteCall(String paramString1, String paramString2, Object[] paramArrayOfObject, CrmCallBack paramCrmCallBack)
    throws Exception;
}

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.communication.client.CrmRemoteCall
 * JD-Core Version:    0.6.2
 */