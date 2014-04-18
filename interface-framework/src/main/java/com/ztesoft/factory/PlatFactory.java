/*    */ package com.ztesoft.factory;
/*    */ 
/*    */ import com.ztesoft.config.ParamsConfig;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class PlatFactory
/*    */ {
/*    */   public static <T> T getInstance(Class<T> cla)
/*    */     throws Exception
/*    */   {
/* 25 */     String debug_tag = "";
/* 26 */     String className = cla.getName();
/* 27 */     Object t = null;
/* 28 */     Class claName = null;
/*    */ 
/* 30 */     if (ParamsConfig.isDebug()) {
/* 31 */       debug_tag = "Test";
/* 32 */       String classNameLocal = className + debug_tag;
/*    */       try {
/* 34 */         claName = Class.forName(classNameLocal);
/*    */       } catch (Exception e) {
/* 36 */         System.err.println("没有找到调试接口实例类:/n" + e.getMessage());
/*    */       }
/*    */     }
/*    */     try
/*    */     {
/* 41 */       if (claName == null)
/* 42 */         claName = Class.forName(className);
/*    */     }
/*    */     catch (Exception e) {
/* 45 */       System.err.println("没有找到接口实例类:/n" + e.getMessage());
/*    */     }
/*    */ 
/* 48 */     if (claName != null) {
/* 49 */       t = claName.newInstance();
/*    */     }
/* 51 */     return t;
/*    */   }
/*    */ 
/*    */   public static <T> T getInstance(Class<T> cla, boolean is_debug)
/*    */     throws Exception
/*    */   {
/* 57 */     String debug_tag = "";
/* 58 */     String className = cla.getName();
/* 59 */     Object t = null;
/* 60 */     Class claName = null;
/*    */ 
/* 62 */     if (is_debug) {
/* 63 */       debug_tag = "Test";
/* 64 */       String classNameLocal = className + debug_tag;
/*    */       try {
/* 66 */         claName = Class.forName(classNameLocal);
/*    */       } catch (Exception e) {
/* 68 */         System.err.println("没有找到调试接口实例类:/n" + e.getMessage());
/*    */       }
/*    */     }
/*    */     try
/*    */     {
/* 73 */       if (claName == null)
/* 74 */         claName = Class.forName(className);
/*    */     }
/*    */     catch (Exception e) {
/* 77 */       System.err.println("没有找到接口实例类:/n" + e.getMessage());
/*    */     }
/*    */ 
/* 80 */     if (claName != null) {
/* 81 */       t = claName.newInstance();
/*    */     }
/* 83 */     return t;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.factory.PlatFactory
 * JD-Core Version:    0.6.2
 */