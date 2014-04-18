/*    */ package com.ztesoft.framework.sqls;
/*    */ 
/*    */ public class SF
/*    */ {
/*    */   public static String commonSql(String name)
/*    */   {
/* 12 */     return SqlsFactory.getCSQLS().getSql(name);
/*    */   }
/*    */ 
/*    */   public static String frameworkSql(String name)
/*    */   {
/* 21 */     return SqlsFactory.getFSQLS().getSql(name);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.framework.sqls.SF
 * JD-Core Version:    0.6.2
 */