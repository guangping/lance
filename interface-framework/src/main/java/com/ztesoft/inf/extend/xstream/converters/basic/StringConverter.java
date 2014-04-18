/*    */ package com.ztesoft.inf.extend.xstream.converters.basic;
/*    */ 
/*    */ import java.lang.ref.WeakReference;
/*    */ import java.util.Collections;
/*    */ import java.util.Map;
/*    */ import java.util.WeakHashMap;
/*    */ 
/*    */ public class StringConverter extends AbstractSingleValueConverter
/*    */ {
/*    */   private final Map cache;
/*    */ 
/*    */   public StringConverter(Map map)
/*    */   {
/* 43 */     this.cache = map;
/*    */   }
/*    */ 
/*    */   public StringConverter() {
/* 47 */     this(Collections.synchronizedMap(new WeakHashMap()));
/*    */   }
/*    */ 
/*    */   public boolean canConvert(Class type)
/*    */   {
/* 52 */     return type.equals(String.class);
/*    */   }
/*    */ 
/*    */   public Object fromString(String str)
/*    */   {
/* 57 */     WeakReference ref = (WeakReference)this.cache.get(str);
/* 58 */     String s = (String)(ref == null ? null : ref.get());
/*    */ 
/* 60 */     if (s == null)
/*    */     {
/* 62 */       this.cache.put(str, new WeakReference(str));
/*    */ 
/* 64 */       s = str;
/*    */     }
/*    */ 
/* 67 */     return s;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.basic.StringConverter
 * JD-Core Version:    0.6.2
 */