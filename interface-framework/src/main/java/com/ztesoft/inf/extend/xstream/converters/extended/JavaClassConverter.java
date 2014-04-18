/*    */ package com.ztesoft.inf.extend.xstream.converters.extended;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.converters.ConversionException;
/*    */ import com.ztesoft.inf.extend.xstream.converters.basic.AbstractSingleValueConverter;
/*    */ 
/*    */ public class JavaClassConverter extends AbstractSingleValueConverter
/*    */ {
/*    */   private ClassLoader classLoader;
/*    */ 
/*    */   @Deprecated
/*    */   public JavaClassConverter()
/*    */   {
/* 35 */     this(Thread.currentThread().getContextClassLoader());
/*    */   }
/*    */ 
/*    */   public JavaClassConverter(ClassLoader classLoader) {
/* 39 */     this.classLoader = classLoader;
/*    */   }
/*    */ 
/*    */   public boolean canConvert(Class clazz)
/*    */   {
/* 44 */     return Class.class.equals(clazz);
/*    */   }
/*    */ 
/*    */   public String toString(Object obj)
/*    */   {
/* 49 */     return ((Class)obj).getName();
/*    */   }
/*    */ 
/*    */   public Object fromString(String str)
/*    */   {
/*    */     try {
/* 55 */       return loadClass(str);
/*    */     } catch (ClassNotFoundException e) {
/* 57 */       throw new ConversionException("Cannot load java class " + str, e);
/*    */     }
/*    */   }
/*    */ 
/*    */   private Class loadClass(String className) throws ClassNotFoundException {
/* 62 */     Class resultingClass = primitiveClassForName(className);
/* 63 */     if (resultingClass != null) {
/* 64 */       return resultingClass;
/*    */     }
/*    */ 
/* 67 */     for (int dimension = 0; className.charAt(dimension) == '['; dimension++);
/* 69 */     if (dimension > 0)
/*    */     {
/*    */       ClassLoader classLoaderToUse;
/*    */       ClassLoader classLoaderToUse;
/* 71 */       if (className.charAt(dimension) == 'L') {
/* 72 */         String componentTypeName = className.substring(dimension + 1, className.length() - 1);
/*    */ 
/* 74 */         classLoaderToUse = this.classLoader.loadClass(componentTypeName).getClassLoader();
/*    */       }
/*    */       else {
/* 77 */         classLoaderToUse = null;
/*    */       }
/* 79 */       return Class.forName(className, false, classLoaderToUse);
/*    */     }
/* 81 */     return this.classLoader.loadClass(className);
/*    */   }
/*    */ 
/*    */   private Class primitiveClassForName(String name)
/*    */   {
/* 88 */     return name.equals("double") ? Double.TYPE : name.equals("float") ? Float.TYPE : name.equals("long") ? Long.TYPE : name.equals("int") ? Integer.TYPE : name.equals("short") ? Short.TYPE : name.equals("char") ? Character.TYPE : name.equals("byte") ? Byte.TYPE : name.equals("boolean") ? Boolean.TYPE : name.equals("void") ? Void.TYPE : null;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.extended.JavaClassConverter
 * JD-Core Version:    0.6.2
 */