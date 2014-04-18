/*    */ package com.ztesoft.common.util;
/*    */ 
/*    */ import com.ztesoft.common.query.util.DcSystemParamUtil;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class PlatService
/*    */ {
/* 10 */   private static Logger logger = Logger.getLogger(PlatService.class);
/* 11 */   public static String system_tag = "";
/*    */ 
/*    */   public static <T> T getPlatInstance(Class<T> cla)
/*    */     throws Exception
/*    */   {
/* 22 */     String IClassName = cla.getSimpleName();
/*    */ 
/* 24 */     if ("".equals(system_tag)) {
/* 25 */       system_tag = DcSystemParamUtil.getSysParamByCache("SYSTEM_TAG");
/*    */     }
/*    */ 
/* 28 */     String classNameInst = cla.getPackage().getName() + "." + IClassName.substring(1, IClassName.length()) + "Inst";
/*    */ 
/* 30 */     String classNameLacal = classNameInst + system_tag;
/* 31 */     Object t = null;
/*    */ 
/* 33 */     Class claName = null;
/*    */     try
/*    */     {
/* 38 */       claName = Class.forName(classNameLacal);
/*    */     } catch (Exception e) {
/* 40 */       throw new RuntimeException(e);
/*    */     }
/*    */     try
/*    */     {
/* 44 */       if (claName == null)
/* 45 */         claName = Class.forName(classNameInst);
/*    */     }
/*    */     catch (Exception e) {
/* 48 */       throw new RuntimeException(e);
/*    */     }
/*    */ 
/* 51 */     if (claName != null) {
/* 52 */       t = claName.newInstance();
/*    */     }
/* 54 */     return t;
/*    */   }
/*    */ 
/*    */   public static <T> T getPlatClass(Class<T> cla)
/*    */     throws Exception
/*    */   {
/* 60 */     if ("".equals(system_tag)) {
/* 61 */       system_tag = DcSystemParamUtil.getSysParamByCache("SYSTEM_TAG");
/*    */     }
/*    */ 
/* 64 */     String className = cla.getName();
/* 65 */     String classNameLacal = className + "_" + system_tag;
/* 66 */     Object t = null;
/*    */ 
/* 68 */     Class claName = null;
/*    */     try
/*    */     {
/* 73 */       claName = Class.forName(classNameLacal);
/*    */     } catch (Exception e) {
/* 75 */       logger.debug(e.getMessage());
/*    */     }
/*    */     try
/*    */     {
/* 79 */       if (claName == null)
/* 80 */         claName = Class.forName(className);
/*    */     }
/*    */     catch (Exception e) {
/* 83 */       logger.debug(e.getMessage());
/*    */     }
/*    */ 
/* 86 */     if (claName != null) {
/* 87 */       t = claName.newInstance();
/*    */     }
/* 89 */     return t;
/*    */   }
/*    */ 
/*    */   public static String getPlatService(String service)
/*    */   {
/* 94 */     if ("".equals(system_tag)) {
/* 95 */       system_tag = DcSystemParamUtil.getSysParamByCache("SYSTEM_TAG");
/*    */     }
/* 97 */     String serviceLocal = service + system_tag;
/* 98 */     return serviceLocal;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.common.util.PlatService
 * JD-Core Version:    0.6.2
 */