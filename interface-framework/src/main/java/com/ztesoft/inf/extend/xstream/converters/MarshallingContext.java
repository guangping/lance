package com.ztesoft.inf.extend.xstream.converters;

import com.ztesoft.inf.extend.xstream.io.path.PathTracker;
import com.ztesoft.inf.extend.xstream.mapper.Mapper;

public abstract interface MarshallingContext extends DataHolder
{
  public abstract void convertAnother(Object paramObject);

  public abstract void convertAnother(Object paramObject, Converter paramConverter);

  public abstract PathTracker getPathTracker();

  public abstract Mapper getMapper();
}

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.MarshallingContext
 * JD-Core Version:    0.6.2
 */