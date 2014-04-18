/*    */ package com.ztesoft.inf.extend.xstream.core;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.converters.DataHolder;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class MapBackedDataHolder
/*    */   implements DataHolder
/*    */ {
/*    */   private final Map map;
/*    */ 
/*    */   public MapBackedDataHolder()
/*    */   {
/* 25 */     this(new HashMap());
/*    */   }
/*    */ 
/*    */   public MapBackedDataHolder(Map map) {
/* 29 */     this.map = map;
/*    */   }
/*    */ 
/*    */   public Object get(Object key) {
/* 33 */     return this.map.get(key);
/*    */   }
/*    */ 
/*    */   public void put(Object key, Object value) {
/* 37 */     this.map.put(key, value);
/*    */   }
/*    */ 
/*    */   public Iterator keys() {
/* 41 */     return Collections.unmodifiableCollection(this.map.keySet()).iterator();
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.core.MapBackedDataHolder
 * JD-Core Version:    0.6.2
 */