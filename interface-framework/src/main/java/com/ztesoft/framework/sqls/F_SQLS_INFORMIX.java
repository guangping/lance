/*    */ package com.ztesoft.framework.sqls;
/*    */ 
/*    */ import com.ztesoft.common.util.PlatService;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class F_SQLS_INFORMIX extends Sqls
/*    */ {
/* 44 */   String mySQL = "select * from dual";
/*    */ 
/* 47 */   String qrySqlRow = "select first ? *  from (select my_table.*  from ( @# )  my_table )   ";
/*    */ 
/*    */   public static Sqls getInstance()
/*    */   {
/* 41 */     return SingletonHolder.sqlsManager;
/*    */   }
/*    */ 
/*    */   public F_SQLS_INFORMIX()
/*    */   {
/* 51 */     SqlUtil.initSqls(F_SQLS_INFORMIX.class, this, this.sqls);
/*    */   }
/*    */ 
/*    */   static class SingletonHolder
/*    */   {
/* 16 */     static Sqls sqlsManager = new F_SQLS_INFORMIX();
/*    */ 
/*    */     static {
/*    */       try { Map mysqls = sqlsManager.sqls;
/*    */ 
/* 31 */         Sqls f_sql_local = (Sqls)PlatService.getPlatClass(sqlsManager.getClass());
/* 32 */         Class f_sql_local_class = f_sql_local.getClass();
/* 33 */         SqlUtil.initSqls(f_sql_local_class, f_sql_local, mysqls);
/*    */       }
/*    */       catch (Exception ex)
/*    */       {
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.framework.sqls.F_SQLS_INFORMIX
 * JD-Core Version:    0.6.2
 */