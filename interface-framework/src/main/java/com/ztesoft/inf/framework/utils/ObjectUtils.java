/*    */ package com.ztesoft.inf.framework.utils;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import java.io.StringWriter;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.beanutils.BeanUtils;
/*    */ import org.omg.CORBA.portable.UnknownException;
/*    */ 
/*    */ public class ObjectUtils
/*    */ {
/*    */   public static Map toMap(Object[] pairs)
/*    */   {
/* 17 */     Map map = new HashMap();
/* 18 */     for (int i = 0; i < pairs.length; i += 2) {
/* 19 */       map.put(pairs[i], pairs[(i + 1)]);
/*    */     }
/* 21 */     return map;
/*    */   }
/*    */   public static String toString(Object object) {
/* 24 */     if (object == null)
/* 25 */       return "";
/* 26 */     if (object.getClass().isPrimitive()) {
/* 27 */       return object.toString();
/*    */     }
/* 29 */     String str = "";
/*    */     Iterator iterator;
/* 31 */     if ((object instanceof Collection)) {
/* 32 */       Collection c = (Collection)object;
/*    */ 
/* 34 */       for (iterator = c.iterator(); iterator.hasNext(); ) {
/* 35 */         Object o = iterator.next();
/* 36 */         str = str + toString(o);
/*    */       }
/* 38 */     } else if (object.getClass().isArray()) {
/* 39 */       Object[] c = (Object[])object;
/* 40 */       for (int i = 0; i < c.length; i++)
/*    */       {
/* 42 */         Object o = c[i];
/* 43 */         str = str + toString(o);
/*    */       }
/*    */     } else { if ((object instanceof Map))
/* 46 */         return object.toString();
/*    */       try
/*    */       {
/* 49 */         str = BeanUtils.describe(object).toString();
/*    */       } catch (Exception e) {
/* 51 */         return "";
/*    */       }
/*    */     }
/* 54 */     return str;
/*    */   }
/*    */   public static Object nvl(Object message, Object replace) {
/* 57 */     return message == null ? replace : message;
/*    */   }
/*    */ 
/*    */   public static String getStackTraceAsString(Throwable e) {
/* 61 */     if (e == null)
/* 62 */       return "";
/* 63 */     if ((e instanceof UnknownException)) {
/* 64 */       e = ((UnknownException)e).originalEx;
/*    */     }
/* 66 */     PrintWriter printWriter = null;
/* 67 */     StringWriter stringWriter = null;
/* 68 */     String expMsg = "";
/*    */     try {
/* 70 */       stringWriter = new StringWriter();
/* 71 */       printWriter = new PrintWriter(stringWriter);
/* 72 */       e.printStackTrace(printWriter);
/* 73 */       expMsg = stringWriter.getBuffer().toString();
/*    */     } finally {
/* 75 */       printWriter.close();
/*    */       try {
/* 77 */         stringWriter.close();
/*    */       }
/*    */       catch (IOException e1)
/*    */       {
/*    */       }
/*    */     }
/* 83 */     return expMsg;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.framework.utils.ObjectUtils
 * JD-Core Version:    0.6.2
 */