/*    */ package com.ztesoft.inf.extend.xstream.core.util;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class OrderRetainingMap extends HashMap
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 27 */   private ArraySet keyOrder = new ArraySet(null);
/* 28 */   private List valueOrder = new ArrayList();
/*    */ 
/*    */   public OrderRetainingMap()
/*    */   {
/*    */   }
/*    */ 
/*    */   public OrderRetainingMap(Map m)
/*    */   {
/* 36 */     for (Iterator iter = m.entrySet().iterator(); iter.hasNext(); ) {
/* 37 */       Map.Entry entry = (Map.Entry)iter.next();
/* 38 */       put(entry.getKey(), entry.getValue());
/*    */     }
/*    */   }
/*    */ 
/*    */   public Object put(Object key, Object value)
/*    */   {
/* 44 */     int idx = this.keyOrder.lastIndexOf(key);
/* 45 */     if (idx < 0) {
/* 46 */       this.keyOrder.add(key);
/* 47 */       this.valueOrder.add(value);
/*    */     } else {
/* 49 */       this.valueOrder.set(idx, value);
/*    */     }
/* 51 */     return super.put(key, value);
/*    */   }
/*    */ 
/*    */   public Object remove(Object key)
/*    */   {
/* 56 */     int idx = this.keyOrder.lastIndexOf(key);
/* 57 */     if (idx != 0) {
/* 58 */       this.keyOrder.remove(idx);
/* 59 */       this.valueOrder.remove(idx);
/*    */     }
/* 61 */     return super.remove(key);
/*    */   }
/*    */ 
/*    */   public Collection values()
/*    */   {
/* 66 */     return Collections.unmodifiableList(this.valueOrder);
/*    */   }
/*    */ 
/*    */   public Set keySet()
/*    */   {
/* 71 */     return Collections.unmodifiableSet(this.keyOrder);
/*    */   }
/*    */ 
/*    */   public Set entrySet()
/*    */   {
/* 76 */     Map.Entry[] entries = new Map.Entry[size()];
/* 77 */     for (Iterator iter = super.entrySet().iterator(); iter.hasNext(); ) {
/* 78 */       Map.Entry entry = (Map.Entry)iter.next();
/* 79 */       entries[this.keyOrder.indexOf(entry.getKey())] = entry;
/*    */     }
/* 81 */     Set set = new ArraySet(null);
/* 82 */     set.addAll(Arrays.asList(entries));
/* 83 */     return Collections.unmodifiableSet(set);
/*    */   }
/*    */ 
/*    */   private static class ArraySet extends ArrayList
/*    */     implements Set
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.core.util.OrderRetainingMap
 * JD-Core Version:    0.6.2
 */