package com.ztesoft.inf.framework.cache;

import java.util.Map;

public abstract interface BoundedMap<K, V> extends Map<K, V>
{
  public abstract void setMaxSize(int paramInt);
}

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.framework.cache.BoundedMap
 * JD-Core Version:    0.6.2
 */