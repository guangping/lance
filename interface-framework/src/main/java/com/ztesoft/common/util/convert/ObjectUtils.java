/*    */ package com.ztesoft.common.util.convert;
/*    */ 
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
/* 15 */     Map map = new HashMap();
/* 16 */     for (int i = 0; i < pairs.length; i += 2) {
/* 17 */       map.put(pairs[i], pairs[(i + 1)]);
/*    */     }
/* 19 */     return map;
/*    */   }
/*    */ 
/*    */   public static String toString(Object object) {
/* 23 */     if (object == null)
/* 24 */       return "";
/* 25 */     if (object.getClass().isPrimitive()) {
/* 26 */       return object.toString();
/*    */     }
/* 28 */     String str = "";
/*    */     Iterator i$;
/* 30 */     if ((object instanceof Collection)) {
/* 31 */       Collection c = (Collection)object;
/* 32 */       for (i$ = c.iterator(); i$.hasNext(); ) { Object o = i$.next();
/* 33 */         str = str + toString(o); }
/*    */     }
/* 35 */     else if (object.getClass().isArray()) {
/* 36 */       Object[] c = (Object[])object;
/* 37 */       for (Object o : c)
/* 38 */         str = str + toString(o);
/*    */     } else {
/* 40 */       if ((object instanceof Map))
/* 41 */         return object.toString();
/*    */       try
/*    */       {
/* 44 */         str = BeanUtils.describe(object).toString();
/*    */       } catch (Exception e) {
/* 46 */         return "";
/*    */       }
/*    */     }
/* 49 */     return str;
/*    */   }
/*    */ 
/*    */   public static Object nvl(Object message, Object replace) {
/* 53 */     return message == null ? replace : message;
/*    */   }
/*    */ 
/*    */   public static String getStackTraceAsString(Throwable e) {
/* 57 */     if (e == null)
/* 58 */       return "";
/* 59 */     if ((e instanceof UnknownException)) {
/* 60 */       e = ((UnknownException)e).originalEx;
/*    */     }
/* 62 */     StringWriter stringWriter = new StringWriter();
/* 63 */     PrintWriter printWriter = new PrintWriter(stringWriter);
/* 64 */     e.printStackTrace(printWriter);
/* 65 */     return stringWriter.getBuffer().toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.common.util.convert.ObjectUtils
 * JD-Core Version:    0.6.2
 */