/*    */ package com.ztesoft.inf.framework.commons;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class TransactionalBeanManager
/*    */ {
/*  8 */   private static Map<Class, Object> transactionalBoCache = new HashMap();
/*    */ 
/*    */   public static synchronized <T> T getTransactional(Class<T> clz) {
/* 11 */     Object bo = transactionalBoCache.get(clz);
/* 12 */     if (bo == null) {
/* 13 */       bo = createTransactional(clz);
/* 14 */       transactionalBoCache.put(clz, bo);
/*    */     }
/* 16 */     return bo;
/*    */   }
/*    */ 
/*    */   public static <T> T createTransactional(Class<T> clz) {
/* 20 */     return ProxyCreator.newInstance(TransactionProxy.class, clz);
/*    */   }
/*    */ 
/*    */   public static <T> T createCommitTransactional(Class<T> clz) {
/* 24 */     return ProxyCreator.newInstance(TransactionCommitProxy.class, clz);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.framework.commons.TransactionalBeanManager
 * JD-Core Version:    0.6.2
 */