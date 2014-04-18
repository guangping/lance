package com.ztesoft.inf.extend.xstream.converters;

import java.util.Iterator;

public abstract interface DataHolder
{
  public abstract Object get(Object paramObject);

  public abstract void put(Object paramObject1, Object paramObject2);

  public abstract Iterator keys();
}

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.DataHolder
 * JD-Core Version:    0.6.2
 */