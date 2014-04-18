package com.ztesoft.inf.extend.xstream.converters;

import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;

public abstract interface Converter extends ConverterMatcher
{
  public abstract void marshal(Object paramObject, HierarchicalStreamWriter paramHierarchicalStreamWriter, MarshallingContext paramMarshallingContext);

  public abstract Object unmarshal(HierarchicalStreamReader paramHierarchicalStreamReader, UnmarshallingContext paramUnmarshallingContext);
}

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.Converter
 * JD-Core Version:    0.6.2
 */