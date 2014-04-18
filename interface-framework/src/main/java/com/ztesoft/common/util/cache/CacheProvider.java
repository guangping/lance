/*    */ package com.ztesoft.common.util.cache;
/*    */ 
/*    */ import com.ztesoft.common.util.StringUtils;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class CacheProvider
/*    */ {
/* 14 */   private static CacheProvider provider = null;
/*    */ 
/* 16 */   private Map<String, Object> cachemap = null;
/* 17 */   private static Object keyObj1 = new Object();
/* 18 */   private static Object keyObj2 = new Object();
/*    */ 
/*    */   private CacheProvider() {
/* 21 */     this.cachemap = new HashMap();
/*    */   }
/*    */ 
/*    */   public static CacheProvider getInstance() {
/* 25 */     if (provider == null) {
/* 26 */       synchronized (CacheProvider.class) {
/* 27 */         if (provider == null)
/*    */         {
/* 29 */           provider = new CacheProvider();
/*    */         }
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 35 */     return provider;
/*    */   }
/*    */ 
/*    */   public Object getObject(String key) {
/* 39 */     if ((this.cachemap == null) || (this.cachemap.isEmpty())) {
/* 40 */       return null;
/*    */     }
/* 42 */     return this.cachemap.get(key);
/*    */   }
/*    */ 
/*    */   public void pubObject(String key, Object obj) {
/* 46 */     if (this.cachemap == null) {
/* 47 */       synchronized (keyObj1) {
/* 48 */         if (this.cachemap == null) {
/* 49 */           this.cachemap = new HashMap();
/*    */         }
/*    */       }
/*    */     }
/* 53 */     synchronized (keyObj2) {
/* 54 */       this.cachemap.put(key, obj);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void removeCache(String staff)
/*    */   {
/* 64 */     if (StringUtils.isEmpty(staff)) {
/* 65 */       return;
/*    */     }
/* 67 */     if ((this.cachemap == null) || (this.cachemap.isEmpty()))
/* 68 */       return;
/*    */     try {
/* 70 */       Set setKey = this.cachemap.keySet();
/* 71 */       Iterator it = setKey.iterator();
/* 72 */       List cacheKey = new ArrayList();
/* 73 */       while (it.hasNext()) {
/* 74 */         String key = (String)it.next();
/* 75 */         if (!StringUtils.isEmpty(key))
/*    */         {
/* 79 */           if (key.indexOf(staff) > 0)
/* 80 */             cacheKey.add(key);
/*    */         }
/*    */       }
/* 83 */       for (String key : cacheKey)
/* 84 */         this.cachemap.remove(key);
/*    */     }
/*    */     catch (Exception ex)
/*    */     {
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.common.util.cache.CacheProvider
 * JD-Core Version:    0.6.2
 */