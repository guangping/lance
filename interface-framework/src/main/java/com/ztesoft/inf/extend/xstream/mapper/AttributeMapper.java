/*     */ package com.ztesoft.inf.extend.xstream.mapper;
/*     */ 
/*     */ import com.ztesoft.inf.extend.xstream.converters.Converter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.ConverterLookup;
/*     */ import com.ztesoft.inf.extend.xstream.converters.SingleValueConverter;
/*     */ import com.ztesoft.inf.extend.xstream.core.util.Fields;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class AttributeMapper extends MapperWrapper
/*     */ {
/*  39 */   private final Map fieldNameToTypeMap = new HashMap();
/*  40 */   private final Set typeSet = new HashSet();
/*     */   private ConverterLookup converterLookup;
/*  42 */   private final Set fieldToUseAsAttribute = new HashSet();
/*     */ 
/*     */   public AttributeMapper(Mapper wrapped) {
/*  45 */     this(wrapped, null);
/*     */   }
/*     */ 
/*     */   public AttributeMapper(Mapper wrapped, ConverterLookup converterLookup) {
/*  49 */     super(wrapped);
/*  50 */     this.converterLookup = converterLookup;
/*     */   }
/*     */ 
/*     */   public void setConverterLookup(ConverterLookup converterLookup) {
/*  54 */     this.converterLookup = converterLookup;
/*     */   }
/*     */ 
/*     */   public void addAttributeFor(String fieldName, Class type) {
/*  58 */     this.fieldNameToTypeMap.put(fieldName, type);
/*     */   }
/*     */ 
/*     */   public void addAttributeFor(Class type) {
/*  62 */     this.typeSet.add(type);
/*     */   }
/*     */ 
/*     */   private SingleValueConverter getLocalConverterFromItemType(Class type) {
/*  66 */     Converter converter = this.converterLookup.lookupConverterForType(type);
/*  67 */     if ((converter != null) && ((converter instanceof SingleValueConverter))) {
/*  68 */       return (SingleValueConverter)converter;
/*     */     }
/*  70 */     return null;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public SingleValueConverter getConverterFromItemType(String fieldName, Class type)
/*     */   {
/*  82 */     if (this.fieldNameToTypeMap.get(fieldName) == type) {
/*  83 */       return getLocalConverterFromItemType(type);
/*     */     }
/*  85 */     return null;
/*     */   }
/*     */ 
/*     */   public SingleValueConverter getConverterFromItemType(String fieldName, Class type, Class definedIn)
/*     */   {
/*  92 */     if (shouldLookForSingleValueConverter(fieldName, type, definedIn)) {
/*  93 */       SingleValueConverter converter = getLocalConverterFromItemType(type);
/*  94 */       if (converter != null) {
/*  95 */         return converter;
/*     */       }
/*     */     }
/*  98 */     return super.getConverterFromItemType(fieldName, type, definedIn);
/*     */   }
/*     */ 
/*     */   public boolean shouldLookForSingleValueConverter(String fieldName, Class type, Class definedIn)
/*     */   {
/* 103 */     Field field = getField(definedIn, fieldName);
/* 104 */     return (this.fieldToUseAsAttribute.contains(field)) || (this.fieldNameToTypeMap.get(fieldName) == type) || (this.typeSet.contains(type));
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public SingleValueConverter getConverterFromItemType(Class type)
/*     */   {
/* 116 */     if (this.typeSet.contains(type)) {
/* 117 */       return getLocalConverterFromItemType(type);
/*     */     }
/* 119 */     return null;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public SingleValueConverter getConverterFromAttribute(String attributeName)
/*     */   {
/* 130 */     SingleValueConverter converter = null;
/* 131 */     Class type = (Class)this.fieldNameToTypeMap.get(attributeName);
/* 132 */     if (type != null) {
/* 133 */       converter = getLocalConverterFromItemType(type);
/*     */     }
/* 135 */     return converter;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public SingleValueConverter getConverterFromAttribute(Class definedIn, String attribute)
/*     */   {
/* 146 */     Field field = getField(definedIn, attribute);
/* 147 */     return getConverterFromAttribute(definedIn, attribute, field.getType());
/*     */   }
/*     */ 
/*     */   public SingleValueConverter getConverterFromAttribute(Class definedIn, String attribute, Class type)
/*     */   {
/* 153 */     if (shouldLookForSingleValueConverter(attribute, type, definedIn)) {
/* 154 */       SingleValueConverter converter = getLocalConverterFromItemType(type);
/* 155 */       if (converter != null) {
/* 156 */         return converter;
/*     */       }
/*     */     }
/* 159 */     return super.getConverterFromAttribute(definedIn, attribute, type);
/*     */   }
/*     */ 
/*     */   public void addAttributeFor(Field field)
/*     */   {
/* 170 */     this.fieldToUseAsAttribute.add(field);
/*     */   }
/*     */ 
/*     */   public void addAttributeFor(Class definedIn, String fieldName)
/*     */   {
/* 185 */     this.fieldToUseAsAttribute.add(getField(definedIn, fieldName));
/*     */   }
/*     */ 
/*     */   private Field getField(Class definedIn, String fieldName) {
/* 189 */     return Fields.find(definedIn, fieldName);
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.mapper.AttributeMapper
 * JD-Core Version:    0.6.2
 */