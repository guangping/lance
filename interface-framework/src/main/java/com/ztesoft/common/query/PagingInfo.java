/*    */ package com.ztesoft.common.query;
/*    */ 
/*    */ public class PagingInfo
/*    */ {
/*  5 */   private String pageIndex = "0";
/*  6 */   private String pageSize = "10";
/*  7 */   private boolean isPaging = false;
/*    */ 
/*    */   public boolean isPaging()
/*    */   {
/* 13 */     return this.isPaging;
/*    */   }
/*    */ 
/*    */   public void setPaging(boolean isPaging) {
/* 17 */     this.isPaging = isPaging;
/*    */   }
/*    */ 
/*    */   public String getPageIndex() {
/* 21 */     return this.pageIndex;
/*    */   }
/*    */ 
/*    */   public void setPageIndex(String pageIndex) {
/* 25 */     this.pageIndex = pageIndex;
/*    */   }
/*    */ 
/*    */   public String getPageSize() {
/* 29 */     return this.pageSize;
/*    */   }
/*    */ 
/*    */   public void setPageSize(String pageSize) {
/* 33 */     this.pageSize = pageSize;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.common.query.PagingInfo
 * JD-Core Version:    0.6.2
 */