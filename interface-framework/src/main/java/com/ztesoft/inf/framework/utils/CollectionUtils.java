/*    */ package com.ztesoft.inf.framework.utils;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ public abstract class CollectionUtils extends org.springframework.util.CollectionUtils
/*    */ {
/*    */   public static List list(Object[] values)
/*    */   {
/* 10 */     List list = new ArrayList();
/*    */ 
/* 12 */     for (int i = 0; i < values.length; i++) {
/* 13 */       Object value = values[i];
/* 14 */       list.add(value);
/*    */     }
/* 16 */     return list;
/*    */   }
/*    */   public static Object unique(List list, String errorMessage) {
/* 19 */     if ((list == null) || (list.size() == 0))
/* 20 */       return null;
/* 21 */     if (list.size() > 1) {
/* 22 */       throw new RuntimeException(errorMessage);
/*    */     }
/* 24 */     return list.get(0);
/*    */   }
/*    */   public static Object unique(List list) {
/* 27 */     return unique(list, "列表包含多个值!");
/*    */   }
/*    */   public static List safe(List list) {
/* 30 */     if (list == null)
/* 31 */       return Collections.EMPTY_LIST;
/* 32 */     return list;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.framework.utils.CollectionUtils
 * JD-Core Version:    0.6.2
 */