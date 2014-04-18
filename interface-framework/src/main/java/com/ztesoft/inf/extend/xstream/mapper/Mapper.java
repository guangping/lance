package com.ztesoft.inf.extend.xstream.mapper;

import com.ztesoft.inf.extend.xstream.converters.Converter;
import com.ztesoft.inf.extend.xstream.converters.SingleValueConverter;

public abstract interface Mapper
{
  public abstract String serializedClass(Class paramClass);

  public abstract Class realClass(String paramString);

  public abstract String serializedMember(Class paramClass, String paramString);

  public abstract String realMember(Class paramClass, String paramString);

  public abstract boolean isImmutableValueType(Class paramClass);

  public abstract Class defaultImplementationOf(Class paramClass);

  @Deprecated
  public abstract String attributeForImplementationClass();

  @Deprecated
  public abstract String attributeForClassDefiningField();

  @Deprecated
  public abstract String attributeForReadResolveField();

  @Deprecated
  public abstract String attributeForEnumType();

  public abstract String aliasForAttribute(String paramString);

  public abstract String attributeForAlias(String paramString);

  public abstract String aliasForSystemAttribute(String paramString);

  public abstract String getFieldNameForItemTypeAndName(Class paramClass1, Class paramClass2, String paramString);

  public abstract Class getItemTypeForItemFieldName(Class paramClass, String paramString);

  public abstract ImplicitCollectionMapping getImplicitCollectionDefForFieldName(Class paramClass, String paramString);

  public abstract boolean shouldSerializeMember(Class paramClass, String paramString);

  @Deprecated
  public abstract SingleValueConverter getConverterFromItemType(String paramString, Class paramClass);

  @Deprecated
  public abstract SingleValueConverter getConverterFromItemType(Class paramClass);

  @Deprecated
  public abstract SingleValueConverter getConverterFromAttribute(String paramString);

  public abstract Converter getLocalConverter(Class paramClass, String paramString);

  public abstract Mapper lookupMapperOfType(Class paramClass);

  public abstract SingleValueConverter getConverterFromItemType(String paramString, Class paramClass1, Class paramClass2);

  @Deprecated
  public abstract String aliasForAttribute(Class paramClass, String paramString);

  @Deprecated
  public abstract String attributeForAlias(Class paramClass, String paramString);

  @Deprecated
  public abstract SingleValueConverter getConverterFromAttribute(Class paramClass, String paramString);

  public abstract SingleValueConverter getConverterFromAttribute(Class paramClass1, String paramString, Class paramClass2);

  public abstract Class realClassByPath(String paramString);

  public abstract String getColFieldNameByPath(String paramString);

  public abstract String getAliasNameByPath(String paramString);

  public abstract String getImplicitCollectionItemNameByPath(String paramString);

  public abstract String genMapNodeNameByPath(String paramString);

  public abstract boolean globalImplicitCollection();

  public static abstract interface ImplicitCollectionMapping
  {
    public abstract String getFieldName();

    public abstract String getItemFieldName();

    public abstract Class getItemType();
  }

  public static class Null
  {
  }
}

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.mapper.Mapper
 * JD-Core Version:    0.6.2
 */