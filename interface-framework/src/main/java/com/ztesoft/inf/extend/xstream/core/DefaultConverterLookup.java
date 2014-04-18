/*    */ package com.ztesoft.inf.extend.xstream.core;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.converters.ConversionException;
/*    */ import com.ztesoft.inf.extend.xstream.converters.Converter;
/*    */ import com.ztesoft.inf.extend.xstream.converters.ConverterLookup;
/*    */ import com.ztesoft.inf.extend.xstream.converters.ConverterRegistry;
/*    */ import com.ztesoft.inf.extend.xstream.core.util.PrioritizedList;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class DefaultConverterLookup
/*    */   implements ConverterLookup, ConverterRegistry
/*    */ {
/* 35 */   private final PrioritizedList converters = new PrioritizedList();
/* 36 */   private transient Map typeToConverterMap = Collections.synchronizedMap(new HashMap());
/*    */ 
/*    */   public Converter lookupConverterForType(Class type)
/*    */   {
/* 43 */     Converter cachedConverter = (Converter)this.typeToConverterMap.get(type);
/* 44 */     if (cachedConverter != null)
/* 45 */       return cachedConverter;
/* 46 */     Iterator iterator = this.converters.iterator();
/* 47 */     while (iterator.hasNext()) {
/* 48 */       Converter converter = (Converter)iterator.next();
/* 49 */       if (converter.canConvert(type)) {
/* 50 */         this.typeToConverterMap.put(type, converter);
/* 51 */         return converter;
/*    */       }
/*    */     }
/* 54 */     throw new ConversionException("No converter specified for " + type);
/*    */   }
/*    */ 
/*    */   public void registerConverter(Converter converter, int priority) {
/* 58 */     this.converters.add(converter, priority);
/* 59 */     Iterator iter = this.typeToConverterMap.keySet().iterator();
/* 60 */     while (iter.hasNext()) {
/* 61 */       Class type = (Class)iter.next();
/* 62 */       if (converter.canConvert(type))
/* 63 */         iter.remove();
/*    */     }
/*    */   }
/*    */ 
/*    */   private Object readResolve()
/*    */   {
/* 69 */     this.typeToConverterMap = Collections.synchronizedMap(new HashMap());
/* 70 */     return this;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.core.DefaultConverterLookup
 * JD-Core Version:    0.6.2
 */