package com.ztesoft.inf.extend.xstream.converters;

import com.ztesoft.inf.extend.xstream.io.path.PathTracker;
import com.ztesoft.inf.extend.xstream.mapper.Mapper;

public abstract interface UnmarshallingContext extends DataHolder
{
  public abstract Object convertAnother(Object paramObject, Class paramClass);

  public abstract Object convertAnother(Object paramObject, Class paramClass, Converter paramConverter);

  public abstract Object currentObject();

  public abstract Class getRequiredType();

  public abstract PathTracker getPathTracker();

  public abstract void addCompletionCallback(Runnable paramRunnable, int paramInt);

  public abstract Mapper getMapper();
}

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.UnmarshallingContext
 * JD-Core Version:    0.6.2
 */