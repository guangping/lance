package com.ztesoft.inf.extend.xstream.converters;

import java.util.Iterator;

public abstract interface ErrorWriter
{
  public abstract void add(String paramString1, String paramString2);

  public abstract String get(String paramString);

  public abstract Iterator keys();
}

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.ErrorWriter
 * JD-Core Version:    0.6.2
 */