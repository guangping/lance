package com.ztesoft.inf.extend.xstream;

import com.ztesoft.inf.extend.xstream.converters.ConverterLookup;
import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;
import com.ztesoft.inf.extend.xstream.mapper.MapperContext;

public abstract interface MarshallingStrategy
{
  public abstract Object unmarshal(Object paramObject, HierarchicalStreamReader paramHierarchicalStreamReader, ConverterLookup paramConverterLookup, MapperContext paramMapperContext);

  public abstract void marshal(HierarchicalStreamWriter paramHierarchicalStreamWriter, Object paramObject, ConverterLookup paramConverterLookup, MapperContext paramMapperContext);
}

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.MarshallingStrategy
 * JD-Core Version:    0.6.2
 */