/*    */ package com.ztesoft.inf.extend.xstream.converters.extended;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.converters.ConversionException;
/*    */ import com.ztesoft.inf.extend.xstream.converters.Converter;
/*    */ import com.ztesoft.inf.extend.xstream.converters.MarshallingContext;
/*    */ import com.ztesoft.inf.extend.xstream.converters.SingleValueConverter;
/*    */ import com.ztesoft.inf.extend.xstream.converters.UnmarshallingContext;
/*    */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
/*    */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class JavaMethodConverter
/*    */   implements Converter
/*    */ {
/*    */   private final SingleValueConverter javaClassConverter;
/*    */ 
/*    */   /** @deprecated */
/*    */   public JavaMethodConverter()
/*    */   {
/* 42 */     this(JavaMethodConverter.class.getClassLoader());
/*    */   }
/*    */ 
/*    */   public JavaMethodConverter(ClassLoader classLoader) {
/* 46 */     this.javaClassConverter = new JavaClassConverter(classLoader);
/*    */   }
/*    */ 
/*    */   public boolean canConvert(Class type) {
/* 50 */     return (type.equals(Method.class)) || (type.equals(Constructor.class));
/*    */   }
/*    */ 
/*    */   public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context)
/*    */   {
/* 55 */     if ((source instanceof Method)) {
/* 56 */       Method method = (Method)source;
/* 57 */       String declaringClassName = this.javaClassConverter.toString(method.getDeclaringClass());
/*    */ 
/* 59 */       marshalMethod(writer, declaringClassName, method.getName(), method.getParameterTypes());
/*    */     }
/*    */     else {
/* 62 */       Constructor method = (Constructor)source;
/* 63 */       String declaringClassName = this.javaClassConverter.toString(method.getDeclaringClass());
/*    */ 
/* 65 */       marshalMethod(writer, declaringClassName, null, method.getParameterTypes());
/*    */     }
/*    */   }
/*    */ 
/*    */   private void marshalMethod(HierarchicalStreamWriter writer, String declaringClassName, String methodName, Class[] parameterTypes)
/*    */   {
/* 73 */     writer.startNode("class");
/* 74 */     writer.setValue(declaringClassName);
/* 75 */     writer.endNode();
/*    */ 
/* 77 */     if (methodName != null)
/*    */     {
/* 79 */       writer.startNode("name");
/* 80 */       writer.setValue(methodName);
/* 81 */       writer.endNode();
/*    */     }
/*    */ 
/* 84 */     writer.startNode("parameter-types");
/* 85 */     for (int i = 0; i < parameterTypes.length; i++) {
/* 86 */       writer.startNode("class");
/* 87 */       writer.setValue(this.javaClassConverter.toString(parameterTypes[i]));
/* 88 */       writer.endNode();
/*    */     }
/* 90 */     writer.endNode();
/*    */   }
/*    */ 
/*    */   public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
/*    */   {
/*    */     try {
/* 96 */       boolean isMethodNotConstructor = context.getRequiredType().equals(Method.class);
/*    */ 
/* 99 */       reader.moveDown();
/* 100 */       String declaringClassName = reader.getValue();
/* 101 */       Class declaringClass = (Class)this.javaClassConverter.fromString(declaringClassName);
/*    */ 
/* 103 */       reader.moveUp();
/*    */ 
/* 105 */       String methodName = null;
/* 106 */       if (isMethodNotConstructor) {
/* 107 */         reader.moveDown();
/* 108 */         methodName = reader.getValue();
/* 109 */         reader.moveUp();
/*    */       }
/*    */ 
/* 112 */       reader.moveDown();
/* 113 */       List parameterTypeList = new ArrayList();
/* 114 */       while (reader.hasMoreChildren()) {
/* 115 */         reader.moveDown();
/* 116 */         String parameterTypeName = reader.getValue();
/* 117 */         parameterTypeList.add(this.javaClassConverter.fromString(parameterTypeName));
/*    */ 
/* 119 */         reader.moveUp();
/*    */       }
/* 121 */       Class[] parameterTypes = (Class[])parameterTypeList.toArray(new Class[parameterTypeList.size()]);
/*    */ 
/* 123 */       reader.moveUp();
/*    */ 
/* 125 */       if (isMethodNotConstructor) {
/* 126 */         return declaringClass.getDeclaredMethod(methodName, parameterTypes);
/*    */       }
/*    */ 
/* 129 */       return declaringClass.getDeclaredConstructor(parameterTypes);
/*    */     }
/*    */     catch (NoSuchMethodException e) {
/* 132 */       throw new ConversionException(e);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.extended.JavaMethodConverter
 * JD-Core Version:    0.6.2
 */