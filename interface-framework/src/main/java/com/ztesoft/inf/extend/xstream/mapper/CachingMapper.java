/*    */ package com.ztesoft.inf.extend.xstream.mapper;
/*    */ 
/*    */ import java.lang.ref.WeakReference;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class CachingMapper extends MapperWrapper
/*    */ {
/*    */   private transient Map realClassCache;
/*    */ 
/*    */   public CachingMapper(Mapper wrapped)
/*    */   {
/* 30 */     super(wrapped);
/* 31 */     readResolve();
/*    */   }
/*    */ 
/*    */   public Class realClass(String elementName)
/*    */   {
/* 36 */     WeakReference reference = (WeakReference)this.realClassCache.get(elementName);
/*    */ 
/* 38 */     if (reference != null) {
/* 39 */       Class cached = (Class)reference.get();
/* 40 */       if (cached != null) {
/* 41 */         return cached;
/*    */       }
/*    */     }
/*    */ 
/* 45 */     Class result = super.realClass(elementName);
/* 46 */     this.realClassCache.put(elementName, new WeakReference(result));
/* 47 */     return result;
/*    */   }
/*    */ 
/*    */   private Object readResolve() {
/* 51 */     this.realClassCache = Collections.synchronizedMap(new HashMap(128));
/* 52 */     return this;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.mapper.CachingMapper
 * JD-Core Version:    0.6.2
 */