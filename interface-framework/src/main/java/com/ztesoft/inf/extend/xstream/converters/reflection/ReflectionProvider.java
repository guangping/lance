package com.ztesoft.inf.extend.xstream.converters.reflection;

import java.lang.reflect.Field;

public abstract interface ReflectionProvider
{
  public abstract Object newInstance(Class paramClass);

  public abstract void visitSerializableFields(Object paramObject, Visitor paramVisitor);

  public abstract void writeField(Object paramObject1, String paramString, Object paramObject2, Class paramClass);

  public abstract Class getFieldType(Object paramObject, String paramString, Class paramClass);

  public abstract boolean fieldDefinedInClass(String paramString, Class paramClass);

  public abstract Field getField(Class paramClass, String paramString);

  public static abstract interface Visitor
  {
    public abstract void visit(String paramString, Class paramClass1, Class paramClass2, Object paramObject);
  }
}

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.reflection.ReflectionProvider
 * JD-Core Version:    0.6.2
 */