/*    */ package com.ztesoft.common.query;
/*    */ 
/*    */ public class DatabaseUtil
/*    */ {
/*    */   public static String doPreFilterWithoutFilterSQL(String sql, int startIndex, int endIndex)
/*    */   {
/*  6 */     sql = "select * from (select my_table.*,rownum as my_rownum from( " + sql + " ) my_table where rownum< " + endIndex + ") where my_rownum>= " + startIndex;
/*    */ 
/*  8 */     return sql;
/*    */   }
/*    */ 
/*    */   public static String doPreFilterWithoutFilterCountSQL(String sql) {
/* 12 */     sql = "select count(*) as amount from ( " + sql + " )  tab_1 ";
/* 13 */     return sql;
/*    */   }
/*    */ 
/*    */   public static int getEndIndex(String pageIndex, String pageSize) {
/* 17 */     int nPageIndex = (int)Double.parseDouble(pageIndex);
/* 18 */     int nPageSize = (int)Double.parseDouble(pageSize);
/*    */ 
/* 22 */     if (nPageIndex - 1 < 0)
/* 23 */       nPageIndex = 0;
/*    */     else {
/* 25 */       nPageIndex -= 1;
/*    */     }
/* 27 */     return nPageIndex * nPageSize + nPageSize + 1;
/*    */   }
/*    */ 
/*    */   public static int getPageIndex(String pageIndex, String pageSize)
/*    */   {
/* 32 */     int nPageIndex = (int)Double.parseDouble(pageIndex);
/* 33 */     int nPageSize = (int)Double.parseDouble(pageSize);
/*    */ 
/* 35 */     if (nPageIndex - 1 < 0)
/* 36 */       nPageIndex = 0;
/*    */     else {
/* 38 */       nPageIndex -= 1;
/*    */     }
/* 40 */     return nPageIndex * nPageSize + 1;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.common.query.DatabaseUtil
 * JD-Core Version:    0.6.2
 */