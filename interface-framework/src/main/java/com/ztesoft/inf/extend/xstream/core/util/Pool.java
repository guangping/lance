/*    */ package com.ztesoft.inf.extend.xstream.core.util;
/*    */ 
/*    */ public class Pool
/*    */ {
/*    */   private final int initialPoolSize;
/*    */   private final int maxPoolSize;
/*    */   private final Factory factory;
/*    */   private transient Object[] pool;
/*    */   private transient int nextAvailable;
/* 30 */   private transient Object mutex = new Object();
/*    */ 
/*    */   public Pool(int initialPoolSize, int maxPoolSize, Factory factory) {
/* 33 */     this.initialPoolSize = initialPoolSize;
/* 34 */     this.maxPoolSize = maxPoolSize;
/* 35 */     this.factory = factory;
/*    */   }
/*    */ 
/*    */   public Object fetchFromPool()
/*    */   {
/*    */     Object result;
/* 40 */     synchronized (this.mutex) {
/* 41 */       if (this.pool == null) {
/* 42 */         this.pool = new Object[this.maxPoolSize];
/* 43 */         for (this.nextAvailable = this.initialPoolSize; this.nextAvailable > 0; ) {
/* 44 */           putInPool(this.factory.newInstance());
/*    */         }
/*    */       }
/* 47 */       while (this.nextAvailable == this.maxPoolSize) {
/*    */         try {
/* 49 */           this.mutex.wait();
/*    */         } catch (InterruptedException e) {
/* 51 */           throw new RuntimeException("Interrupted whilst waiting for a free item in the pool : " + e.getMessage());
/*    */         }
/*    */       }
/*    */ 
/* 55 */       result = this.pool[(this.nextAvailable++)];
/* 56 */       if (result == null) {
/* 57 */         result = this.factory.newInstance();
/* 58 */         putInPool(result);
/* 59 */         this.nextAvailable += 1;
/*    */       }
/*    */     }
/* 62 */     return result;
/*    */   }
/*    */ 
/*    */   protected void putInPool(Object object) {
/* 66 */     synchronized (this.mutex) {
/* 67 */       this.pool[(--this.nextAvailable)] = object;
/* 68 */       this.mutex.notify();
/*    */     }
/*    */   }
/*    */ 
/*    */   private Object readResolve() {
/* 73 */     this.mutex = new Object();
/* 74 */     return this;
/*    */   }
/*    */ 
/*    */   public static abstract interface Factory
/*    */   {
/*    */     public abstract Object newInstance();
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.core.util.Pool
 * JD-Core Version:    0.6.2
 */