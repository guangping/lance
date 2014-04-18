package com.ztesoft.inf.extend.xstream.converters;

public abstract interface ConverterLookup
{
  public abstract Converter lookupConverterForType(Class paramClass);
}

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.ConverterLookup
 * JD-Core Version:    0.6.2
 */