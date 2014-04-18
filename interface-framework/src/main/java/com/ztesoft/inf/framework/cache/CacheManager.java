/*     */ package com.ztesoft.inf.framework.cache;
/*     */ 
/*     */ import com.ztesoft.common.util.date.DateUtil;
/*     */ import com.ztesoft.inf.communication.client.CrmRemoteCallClient;
/*     */ import com.ztesoft.inf.framework.commons.CodedException;
/*     */ import com.ztesoft.inf.framework.commons.TransactionalBeanManager;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class CacheManager
/*     */ {
/*  18 */   private static final Class<? extends Cache> DEFAULT_CACHETYPE = Cache.class;
/*     */   private static final int RUNNING = 0;
/*     */   private static final int SHUTDOWNED = 1;
/*  21 */   private static Map<String, Cache> cacheMap = new HashMap();
/*  22 */   private static List<Cache> cacheStore = new ArrayList();
/*     */   private static Thread refreshThread;
/*     */   private static int state;
/*  25 */   private static int DEFAULT_CACHE_TIME = 30;
/*     */ 
/*     */   public static synchronized void startup()
/*     */   {
/*  48 */     state = 0;
/*  49 */     refreshThread = new Thread(new Runnable() {
/*     */       public void run() {
/*     */         try {
/*  52 */           CacheManager.CacheRefresher refresher = (CacheManager.CacheRefresher)TransactionalBeanManager.createTransactional(CacheManager.CacheRefresher.class);
/*     */           while (true)
/*     */           {
/*  55 */             List store = new ArrayList(CacheManager.cacheStore);
/*  56 */             for (Cache c : store) {
/*  57 */               refresher.refresh(c);
/*     */             }
/*     */ 
/*  60 */             store = null;
/*  61 */             if (CacheManager.state == 1)
/*     */               break;
/*     */             try {
/*  64 */               Thread.sleep(120000L);
/*     */             } catch (InterruptedException e) {
/*  66 */               break;
/*     */             }
/*  68 */             if (CacheManager.state == 1)
/*     */               break;
/*     */           }
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/*     */       }
/*     */     });
/*  75 */     refreshThread.start();
/*     */   }
/*     */ 
/*     */   public static synchronized void shutdown() {
/*  79 */     state = 1;
/*  80 */     if (refreshThread != null)
/*  81 */       refreshThread.interrupt();
/*     */   }
/*     */ 
/*     */   public static synchronized void refreshCache(String cacheKey, String cacheCode) throws Exception
/*     */   {
/*  86 */     List store = new ArrayList(cacheStore);
/*  87 */     for (Cache cache : store)
/*  88 */       if ((cache != null) && (cacheKey.equals(cache.getCacheKey())))
/*     */       {
/*  91 */         Object cacheValue = cache.get(cacheCode, new CacheItemCreateCallback()
/*     */         {
/*     */           public Object create() throws Exception {
/*  94 */             return null;
/*     */           }
/*     */         });
/*  98 */         if (cacheValue != null) {
/*  99 */           cache.clear();
/* 100 */           cache.setLastRefresh(new Date());
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   public static void refreshAllCache()
/*     */     throws Exception
/*     */   {
/* 113 */     synchronized (cacheStore) {
/* 114 */       List store = new ArrayList(cacheStore);
/* 115 */       for (Cache cache : store) {
/* 116 */         cache.clear();
/* 117 */         cache.setLastRefresh(new Date());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static synchronized void refreshAll()
/*     */   {
/* 124 */     CrmRemoteCallClient client = new CrmRemoteCallClient();
/*     */ 
/* 126 */     String operationCode = "crm.CrmRemoteCall";
/*     */     try
/*     */     {
/* 129 */       String rets = getPorts();
/*     */ 
/* 131 */       if (("".equals(rets)) || (null == rets) || (rets.split(",").length == 0)) {
/* 132 */         return;
/*     */       }
/*     */ 
/* 135 */       String[] arrs = rets.split(",");
/* 136 */       for (int i = 0; i < arrs.length; i++) {
/* 137 */         String arr = arrs[i];
/*     */         try {
/* 139 */           client.remoteCall(operationCode + arr.trim(), "com.ztesoft.inf.framework.cache.CacheManager", "refreshAllCache", new Object[0], null);
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 143 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 147 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static synchronized void refreshAll(String cacheKey, String cacheCode)
/*     */   {
/* 153 */     CrmRemoteCallClient client = new CrmRemoteCallClient();
/*     */ 
/* 155 */     String operationCode = "crm.CrmRemoteCall";
/*     */     try
/*     */     {
/* 158 */       String rets = getPorts();
/*     */ 
/* 160 */       if (("".equals(rets)) || (null == rets) || (rets.split(",").length == 0)) {
/* 161 */         return;
/*     */       }
/*     */ 
/* 164 */       String[] arrs = rets.split(",");
/* 165 */       for (int i = 0; i < arrs.length; i++) {
/* 166 */         String arr = arrs[i];
/*     */         try {
/* 168 */           client.remoteCall(operationCode + arr.trim(), "com.ztesoft.inf.framework.cache.CacheManager", "refreshCache", new Object[] { cacheKey, cacheCode }, null);
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 173 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 177 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String getPorts()
/*     */   {
/* 183 */     String value = "";
/*     */ 
/* 192 */     return value;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 197 */     refreshAll();
/*     */   }
/*     */ 
/*     */   public static <K, V> Cache<K, V> getCache(String cacheId) {
/* 201 */     return getCache(cacheId, DEFAULT_CACHE_TIME);
/*     */   }
/*     */ 
/*     */   public static <K, V> Cache<K, V> getCache(String cacheId, int period) {
/* 205 */     return getCache(cacheId, period, FIFOMap.class);
/*     */   }
/*     */ 
/*     */   public static <K, V> Cache<K, V> getCache(String cacheId, Class<? extends BoundedMap> backedMapClass)
/*     */   {
/* 210 */     return getCache(cacheId, DEFAULT_CACHE_TIME, backedMapClass);
/*     */   }
/*     */ 
/*     */   public static <K, V> Cache<K, V> getCache(String cacheId, int period, Class<? extends BoundedMap> backedMapClass)
/*     */   {
/* 215 */     Cache cache = null;
/* 216 */     synchronized (cacheMap) {
/* 217 */       if (!cacheMap.containsKey(cacheId)) {
/* 218 */         cache = createCache(period, backedMapClass);
/* 219 */         cache.setCacheKey(cacheId);
/* 220 */         cacheMap.put(cacheId, cache);
/*     */       } else {
/* 222 */         cache = (Cache)cacheMap.get(cacheId);
/* 223 */       }return cache;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static <K, V> Cache<K, V> createCache(int period) {
/* 228 */     return createCache(period, DEFAULT_CACHETYPE, FIFOMap.class);
/*     */   }
/*     */ 
/*     */   public static <K, V> Cache<K, V> createCache(int period, Class<? extends BoundedMap> backedMapClass)
/*     */   {
/* 233 */     return createCache(period, DEFAULT_CACHETYPE, backedMapClass);
/*     */   }
/*     */ 
/*     */   public static <K, V> Cache<K, V> createCache(Class<? extends Cache> cacheClz, Class<? extends BoundedMap> backedMapClass)
/*     */   {
/* 239 */     return createCache(DEFAULT_CACHE_TIME, cacheClz, backedMapClass);
/*     */   }
/*     */ 
/*     */   public static synchronized <K, V> Cache<K, V> createCache(int period, Class<? extends Cache> cacheClz, Class<? extends BoundedMap> backedMapClass)
/*     */   {
/*     */     try
/*     */     {
/* 247 */       Cache instance = (Cache)cacheClz.newInstance();
/* 248 */       instance.setBackedClass(backedMapClass);
/* 249 */       instance.setPeriod(period);
/* 250 */       instance.setLastRefresh(new Date());
/* 251 */       cacheStore.add(instance);
/* 252 */       return instance;
/*     */     } catch (Exception e) {
/* 254 */       throw new CodedException("", "", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  28 */     startup();
/*     */   }
/*     */ 
/*     */   public static class CacheRefresher {
/*     */     public void refresh(Cache c) {
/*  33 */       String key = c.getCacheKey();
/*  34 */       if ((key != null) && (CacheManager.cacheMap.containsKey(key))) {
/*  35 */         int period = c.getPeriod();
/*  36 */         Date lastRefresh = c.getLastRefresh();
/*  37 */         if ((period > 0) && (new Date().after(DateUtil.add(lastRefresh, 12, period))))
/*     */         {
/*  40 */           c.clear();
/*  41 */           c.setLastRefresh(new Date());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.framework.cache.CacheManager
 * JD-Core Version:    0.6.2
 */