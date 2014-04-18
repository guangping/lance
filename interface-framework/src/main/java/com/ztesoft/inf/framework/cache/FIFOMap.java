/*    */ package com.ztesoft.inf.framework.cache;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map.Entry;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class FIFOMap<K, V> extends LinkedHashMap<K, V>
/*    */   implements BoundedMap<K, V>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 11 */   private int maxSize = -1;
/*    */ 
/*    */   protected FIFOMap(int capacity, float factor, boolean accessOrder) {
/* 14 */     super(capacity, factor, accessOrder);
/*    */   }
/*    */ 
/*    */   public FIFOMap()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void setMaxSize(int newMaxSize)
/*    */   {
/*    */     Iterator i$;
/* 20 */     if (((this.maxSize < 0) || (newMaxSize < this.maxSize)) && (size() > newMaxSize)) {
/* 21 */       int i = size() - newMaxSize;
/* 22 */       Iterator iter = keySet().iterator();
/* 23 */       List keys = new ArrayList(i);
/* 24 */       while ((iter.hasNext()) && (i > 0)) {
/* 25 */         i--;
/* 26 */         keys.add(iter.next());
/*    */       }
/* 28 */       for (i$ = keys.iterator(); i$.hasNext(); ) { Object k = i$.next();
/* 29 */         remove(k);
/*    */       }
/*    */     }
/* 32 */     this.maxSize = newMaxSize;
/*    */   }
/*    */ 
/*    */   protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
/* 36 */     return (this.maxSize > 0) && (size() > this.maxSize);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.framework.cache.FIFOMap
 * JD-Core Version:    0.6.2
 */