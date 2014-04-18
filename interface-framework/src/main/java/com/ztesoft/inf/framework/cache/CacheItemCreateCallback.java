package com.ztesoft.inf.framework.cache;

public abstract interface CacheItemCreateCallback<T>
{
  public abstract T create()
    throws Exception;
}

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.framework.cache.CacheItemCreateCallback
 * JD-Core Version:    0.6.2
 */