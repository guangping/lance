/*    */ package com.ztesoft.inf.framework.utils;
/*    */ 
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ClassUtils
/*    */ {
/*  9 */   private static String DC_SQL = "";
/*    */ 
/*    */   public static Object newInstance(String className) {
/* 12 */     ClassLoader loader = Thread.currentThread().getContextClassLoader();
/*    */     try
/*    */     {
/* 15 */       Class clazz = loader.loadClass(className);
/* 16 */       return clazz.newInstance();
/*    */     }
/*    */     catch (Exception e) {
/* 19 */       throw new RuntimeException("根据类名创建实例失败," + e.getMessage(), e);
/*    */     }
/*    */   }
/*    */ 
/*    */   public static Object invoke(String op_code, Map data)
/*    */     throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException
/*    */   {
/* 28 */     String clsName = "";
/* 29 */     String methodName = "";
/* 30 */     Class cls = Class.forName(clsName);
/* 31 */     Object obj = cls.newInstance();
/* 32 */     Method method = cls.getMethod(methodName, new Class[] { Map.class });
/* 33 */     Object ret = method.invoke(obj, new Map[] { data });
/* 34 */     return ret;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.framework.utils.ClassUtils
 * JD-Core Version:    0.6.2
 */