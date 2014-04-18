/*    */ package com.ztesoft.framework.sqls;
/*    */ 
/*    */ import com.ztesoft.common.util.PlatService;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class C_SQLS extends Sqls
/*    */ {
/*    */   static final String mySQL = "select * from dual";
/*    */   private static final String SELECT_DC_SQL = "select * from dc_sql a where a.dc_name=?";
/*    */   private static final String SELECT_DC_PUBLIC_BY_STYPE = "select * from dc_public a where a.stype=?";
/*    */   private static final String SELECT_DC_PUBLIC_BY_STYPE_PKEY = "select * from dc_public a where a.stype=? and a.pkey=?";
/*    */ 
/*    */   public static Sqls getInstance()
/*    */   {
/* 30 */     return SingletonHolder.sqlsManager;
/*    */   }
/*    */ 
/*    */   public C_SQLS()
/*    */   {
/* 38 */     SqlUtil.initSqls(C_SQLS.class, this, this.sqls);
/*    */   }
/*    */ 
/*    */   static class SingletonHolder
/*    */   {
/* 11 */     static Sqls sqlsManager = new C_SQLS();
/*    */ 
/*    */     static {
/*    */       try { Map mysqls = sqlsManager.sqls;
/*    */ 
/* 20 */         Sqls c_sql_local = (Sqls)PlatService.getPlatClass(sqlsManager.getClass());
/* 21 */         Class c_sql_local_class = c_sql_local.getClass();
/* 22 */         SqlUtil.initSqls(c_sql_local_class, c_sql_local, mysqls);
/*    */       }
/*    */       catch (Exception ex)
/*    */       {
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.framework.sqls.C_SQLS
 * JD-Core Version:    0.6.2
 */