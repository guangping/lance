/*    */ package com.ztesoft.inf.framework.logger;
/*    */ 
/*    */ public class AppLogContext
/*    */ {
/*    */   public static final int TIME_OUT = 5000;
/*    */   private AppLogger appLogger;
/*    */   private Object logObj;
/*    */ 
/*    */   public AppLogContext()
/*    */   {
/*    */   }
/*    */ 
/*    */   public AppLogContext(AppLogger appLogger, Object logObj)
/*    */   {
/* 16 */     this.appLogger = appLogger;
/* 17 */     this.logObj = logObj;
/*    */   }
/*    */ 
/*    */   public AppLogger getAppLogger() {
/* 21 */     return this.appLogger;
/*    */   }
/*    */ 
/*    */   public void setAppLogger(AppLogger appLogger) {
/* 25 */     this.appLogger = appLogger;
/*    */   }
/*    */ 
/*    */   public Object getLogObj() {
/* 29 */     return this.logObj;
/*    */   }
/*    */ 
/*    */   public void setLogObj(Object logObj) {
/* 33 */     this.logObj = logObj;
/*    */   }
/*    */ 
/*    */   public void logToDB()
/*    */     throws Exception
/*    */   {
/* 40 */     this.appLogger.log(this.logObj);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.framework.logger.AppLogContext
 * JD-Core Version:    0.6.2
 */