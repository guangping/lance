/*    */ package com.ztesoft.inf.framework.cache;
/*    */ 
/*    */ import java.util.Date;
/*    */ 
/*    */ public class Cache<K, V>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected BoundedMap<K, V> backed;
/*    */   private Class<? extends BoundedMap> backedMapClass;
/* 10 */   private static int DEFAULT_MAXSIZE = -1;
/*    */   private int maxSize;
/*    */   private int period;
/*    */   private String cacheKey;
/*    */   private Date lastRefresh;
/*    */ 
/*    */   public Date getLastRefresh()
/*    */   {
/* 17 */     return this.lastRefresh;
/*    */   }
/*    */   public void setLastRefresh(Date lastRefresh) {
/* 20 */     this.lastRefresh = lastRefresh;
/*    */   }
/*    */   public String getCacheKey() {
/* 23 */     return this.cacheKey;
/*    */   }
/*    */   public void setCacheKey(String cacheKey) {
/* 26 */     this.cacheKey = cacheKey;
/*    */   }
/*    */   public int getPeriod() {
/* 29 */     return this.period;
/*    */   }
/*    */   public void setPeriod(int period) {
/* 32 */     this.period = period;
/*    */   }
/*    */   void setBackedClass(Class<? extends BoundedMap> backedMapClass) {
/* 35 */     if (this.backed.size() > 0) {
/* 36 */       throw new RuntimeException("");
/*    */     }
/* 38 */     this.backedMapClass = backedMapClass;
/*    */   }
/*    */   public Cache() {
/* 41 */     this(DEFAULT_MAXSIZE, FIFOMap.class);
/*    */   }
/*    */   public Cache(int maxSize, Class<? extends BoundedMap> backedMapClass) {
/* 44 */     this.backedMapClass = backedMapClass;
/* 45 */     this.maxSize = maxSize;
/* 46 */     this.backed = newBackedMap();
/*    */   }
/*    */   public Cache(int capacity) {
/* 49 */     this(capacity, FIFOMap.class);
/*    */   }
/*    */   protected BoundedMap newBackedMap() {
/*    */     BoundedMap map;
/*    */     try {
/* 54 */       map = (BoundedMap)this.backedMapClass.newInstance();
/* 55 */       map.setMaxSize(this.maxSize);
/*    */     } catch (Exception e) {
/* 57 */       throw new RuntimeException(e.getMessage(), e);
/*    */     }
/* 59 */     return map;
/*    */   }
/*    */   public synchronized void clear() {
/* 62 */     this.backed.clear();
/*    */   }
/*    */ 
/*    */   public synchronized V get(K key, CacheItemCreateCallback<V> callback) throws Exception {
/* 66 */     this.backed = getBackedMap();
/* 67 */     Object item = this.backed.get(key);
/* 68 */     if (item == null) {
/* 69 */       item = callback.create();
/* 70 */       this.backed.put(key, item);
/*    */     }
/* 72 */     return item;
/*    */   }
/*    */   private BoundedMap<K, V> getBackedMap() {
/* 75 */     if (this.backed == null) {
/* 76 */       this.backed = newBackedMap();
/*    */     }
/* 78 */     return this.backed;
/*    */   }
/*    */   public synchronized V put(K key, V value) {
/* 81 */     this.backed = getBackedMap();
/* 82 */     return this.backed.put(key, value);
/*    */   }
/*    */   public int size() {
/* 85 */     return this.backed.size();
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.framework.cache.Cache
 * JD-Core Version:    0.6.2
 */