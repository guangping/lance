/*     */ package com.ztesoft.common.util.page;
/*     */ 
/*     */ import com.google.gson.annotations.Expose;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PageModel<T>
/*     */   implements Serializable
/*     */ {
/*     */ 
/*     */   @Expose
/*     */   private int totalCount;
/*     */ 
/*     */   @Expose
/*     */   private int pageIndex;
/*     */ 
/*     */   @Expose
/*     */   private int pageCount;
/*     */ 
/*     */   @Expose
/*     */   private int pageSize;
/*     */ 
/*     */   @Expose
/*     */   private List<T> list;
/*     */ 
/*     */   @Expose
/*     */   private List<T> sumInfoList;
/*     */   private Object dataEx;
/*     */   private List itemList;
/*     */   private Map<String, Object> other;
/*  44 */   private int columns = 1;
/*  45 */   private String needPage = "T";
/*     */ 
/*     */   public Map<String, Object> getOther() {
/*  48 */     return this.other;
/*     */   }
/*     */ 
/*     */   public void setOther(Map<String, Object> other) {
/*  52 */     this.other = other;
/*     */   }
/*     */ 
/*     */   public List getItemList() {
/*  56 */     return this.itemList;
/*     */   }
/*     */ 
/*     */   public void setItemList(List itemList) {
/*  60 */     this.itemList = itemList;
/*     */   }
/*     */ 
/*     */   public String getNeedPage() {
/*  64 */     return this.needPage;
/*     */   }
/*     */ 
/*     */   public void setNeedPage(String needPage) {
/*  68 */     this.needPage = needPage;
/*     */   }
/*     */ 
/*     */   public int getColumns() {
/*  72 */     return this.columns;
/*     */   }
/*     */ 
/*     */   public void setColumns(int columns) {
/*  76 */     this.columns = columns;
/*     */   }
/*     */ 
/*     */   public PageModel()
/*     */   {
/*  86 */     this.totalCount = 0;
/*  87 */     this.pageIndex = 1;
/*  88 */     this.pageCount = 1;
/*  89 */     this.pageSize = 10;
/*  90 */     this.list = new ArrayList();
/*  91 */     this.sumInfoList = new ArrayList();
/*  92 */     this.dataEx = null;
/*     */   }
/*     */ 
/*     */   public Object getDataEx()
/*     */   {
/*  97 */     return this.dataEx;
/*     */   }
/*     */ 
/*     */   public void setDataEx(Object dataEx) {
/* 101 */     this.dataEx = dataEx;
/*     */   }
/*     */ 
/*     */   public List getSumInfoList() {
/* 105 */     return this.sumInfoList;
/*     */   }
/*     */ 
/*     */   public void setSumInfoList(List sumInfoList) {
/* 109 */     this.sumInfoList = sumInfoList;
/*     */   }
/*     */ 
/*     */   public List getList() {
/* 113 */     return this.list;
/*     */   }
/*     */ 
/*     */   public void setList(List list) {
/* 117 */     this.list = list;
/*     */   }
/*     */ 
/*     */   public int getPageSize() {
/* 121 */     return this.pageSize;
/*     */   }
/*     */ 
/*     */   public void setPageSize(int pageSize) {
/* 125 */     this.pageSize = pageSize;
/*     */   }
/*     */ 
/*     */   public int getPageIndex() {
/* 129 */     return this.pageIndex;
/*     */   }
/*     */ 
/*     */   public void setPageIndex(int pageIndex) {
/* 133 */     this.pageIndex = pageIndex;
/*     */   }
/*     */ 
/*     */   public int getTotalCount() {
/* 137 */     return this.totalCount;
/*     */   }
/*     */ 
/*     */   public void setTotalCount(int totalCount) {
/* 141 */     this.totalCount = totalCount;
/*     */   }
/*     */ 
/*     */   public int getPageCount() {
/* 145 */     return this.pageCount;
/*     */   }
/*     */ 
/*     */   public void setPageCount(int pageCount) {
/* 149 */     this.pageCount = pageCount;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.common.util.page.PageModel
 * JD-Core Version:    0.6.2
 */