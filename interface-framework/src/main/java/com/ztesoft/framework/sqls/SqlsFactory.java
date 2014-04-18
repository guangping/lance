/*    */ package com.ztesoft.framework.sqls;
/*    */ 
/*    */ import com.ztesoft.config.ParamsConfig;
/*    */ 
/*    */ public class SqlsFactory
/*    */ {
/*  9 */   private static String databaseType = ParamsConfig.getInstance().getParamValue("DATABASE_TYPE");
/*    */ 
/*    */   public static Sqls getCSQLS()
/*    */   {
/* 24 */     Sqls sqls = null;
/* 25 */     if ("ORACLE".equals(databaseType))
/* 26 */       sqls = C_SQLS.getInstance();
/* 27 */     else if ("INFORMIX".equals(databaseType)) {
/* 28 */       sqls = C_SQLS_INFORMIX.getInstance();
/*    */     }
/*    */ 
/* 31 */     return sqls;
/*    */   }
/*    */ 
/*    */   public static Sqls getFSQLS()
/*    */   {
/* 59 */     Sqls sqls = null;
/* 60 */     if ("ORACLE".equals(databaseType))
/* 61 */       sqls = F_SQLS.getInstance();
/* 62 */     else if ("INFORMIX".equals(databaseType)) {
/* 63 */       sqls = F_SQLS_INFORMIX.getInstance();
/*    */     }
/*    */ 
/* 66 */     return sqls;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.framework.sqls.SqlsFactory
 * JD-Core Version:    0.6.2
 */