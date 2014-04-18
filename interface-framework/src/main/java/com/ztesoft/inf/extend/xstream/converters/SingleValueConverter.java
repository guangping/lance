package com.ztesoft.inf.extend.xstream.converters;

public abstract interface SingleValueConverter extends ConverterMatcher
{
  public abstract String toString(Object paramObject);

  public abstract Object fromString(String paramString);
}

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.SingleValueConverter
 * JD-Core Version:    0.6.2
 */