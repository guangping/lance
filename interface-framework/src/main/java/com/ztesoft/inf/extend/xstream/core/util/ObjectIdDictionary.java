/*     */ package com.ztesoft.inf.extend.xstream.core.util;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class ObjectIdDictionary
/*     */ {
/*     */   private final Map map;
/*     */   private volatile int counter;
/*     */ 
/*     */   public ObjectIdDictionary()
/*     */   {
/*  29 */     this.map = new HashMap();
/*     */   }
/*     */ 
/*     */   public void associateId(Object obj, Object id)
/*     */   {
/* 105 */     this.map.put(new WeakIdWrapper(obj), id);
/* 106 */     this.counter += 1;
/* 107 */     cleanup();
/*     */   }
/*     */ 
/*     */   public Object lookupId(Object obj) {
/* 111 */     Object id = this.map.get(new IdWrapper(obj));
/* 112 */     this.counter += 1;
/* 113 */     return id;
/*     */   }
/*     */ 
/*     */   public boolean containsId(Object item) {
/* 117 */     boolean b = this.map.containsKey(new IdWrapper(item));
/* 118 */     this.counter += 1;
/* 119 */     return b;
/*     */   }
/*     */ 
/*     */   public void removeId(Object item) {
/* 123 */     this.map.remove(new IdWrapper(item));
/* 124 */     this.counter += 1;
/* 125 */     cleanup();
/*     */   }
/*     */ 
/*     */   public int size() {
/* 129 */     return this.map.size();
/*     */   }
/*     */ 
/*     */   private void cleanup() {
/* 133 */     if (this.counter > 10000) {
/* 134 */       this.counter = 0;
/*     */ 
/* 136 */       Iterator iterator = this.map.keySet().iterator();
/* 137 */       while (iterator.hasNext()) {
/* 138 */         WeakIdWrapper key = (WeakIdWrapper)iterator.next();
/* 139 */         if (key.get() == null)
/* 140 */           iterator.remove();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class WeakIdWrapper
/*     */     implements ObjectIdDictionary.Wrapper
/*     */   {
/*     */     private final int hashCode;
/*     */     private final WeakReference ref;
/*     */ 
/*     */     public WeakIdWrapper(Object obj)
/*     */     {
/*  78 */       this.hashCode = System.identityHashCode(obj);
/*  79 */       this.ref = new WeakReference(obj);
/*     */     }
/*     */ 
/*     */     public int hashCode()
/*     */     {
/*  84 */       return this.hashCode;
/*     */     }
/*     */ 
/*     */     public boolean equals(Object other)
/*     */     {
/*  89 */       return get() == ((ObjectIdDictionary.Wrapper)other).get();
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/*  94 */       Object obj = get();
/*  95 */       return obj == null ? "(null)" : obj.toString();
/*     */     }
/*     */ 
/*     */     public Object get() {
/*  99 */       Object obj = this.ref.get();
/* 100 */       return obj;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class IdWrapper
/*     */     implements ObjectIdDictionary.Wrapper
/*     */   {
/*     */     private final Object obj;
/*     */     private final int hashCode;
/*     */ 
/*     */     public IdWrapper(Object obj)
/*     */     {
/*  48 */       this.hashCode = System.identityHashCode(obj);
/*  49 */       this.obj = obj;
/*     */     }
/*     */ 
/*     */     public int hashCode()
/*     */     {
/*  54 */       return this.hashCode;
/*     */     }
/*     */ 
/*     */     public boolean equals(Object other)
/*     */     {
/*  59 */       return this.obj == ((ObjectIdDictionary.Wrapper)other).get();
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/*  64 */       return this.obj.toString();
/*     */     }
/*     */ 
/*     */     public Object get() {
/*  68 */       return this.obj;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static abstract interface Wrapper
/*     */   {
/*     */     public abstract int hashCode();
/*     */ 
/*     */     public abstract boolean equals(Object paramObject);
/*     */ 
/*     */     public abstract String toString();
/*     */ 
/*     */     public abstract Object get();
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.core.util.ObjectIdDictionary
 * JD-Core Version:    0.6.2
 */