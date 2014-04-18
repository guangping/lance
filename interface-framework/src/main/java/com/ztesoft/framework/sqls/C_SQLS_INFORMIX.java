/*    */ package com.ztesoft.framework.sqls;
/*    */ 
/*    */ import com.ztesoft.common.util.PlatService;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class C_SQLS_INFORMIX extends Sqls
/*    */ {
/*    */   static final String mySQL = "select * from dual";
/*    */ 
/*    */   public static Sqls getInstance()
/*    */   {
/* 33 */     return SingletonHolder.sqlsManager;
/*    */   }
/*    */ 
/*    */   public C_SQLS_INFORMIX()
/*    */   {
/* 41 */     SqlUtil.initSqls(C_SQLS_INFORMIX.class, this, this.sqls);
/*    */   }
/*    */ 
/*    */   static class SingletonHolder
/*    */   {
/* 11 */     static Sqls sqlsManager = new C_SQLS_INFORMIX();
/*    */ 
/*    */     static {
/*    */       try { Map mysqls = sqlsManager.sqls;
/*    */ 
/* 21 */         Sqls c_sql_local = (Sqls)PlatService.getPlatClass(sqlsManager.getClass());
/* 22 */         Class c_sql_local_class = c_sql_local.getClass();
/* 23 */         SqlUtil.initSqls(c_sql_local_class, c_sql_local, mysqls); } catch (Exception ex) {
/* 24 */         ex = 
/* 28 */           ex;
/*    */       }
/*    */       finally
/*    */       {
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.framework.sqls.C_SQLS_INFORMIX
 * JD-Core Version:    0.6.2
 */