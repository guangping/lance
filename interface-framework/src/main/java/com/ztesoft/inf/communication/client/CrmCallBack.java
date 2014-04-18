package com.ztesoft.inf.communication.client;

public abstract interface CrmCallBack
{
  public abstract void setResult(Object paramObject);

  public abstract Object getResult();

  public abstract Object execute();
}

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.communication.client.CrmCallBack
 * JD-Core Version:    0.6.2
 */