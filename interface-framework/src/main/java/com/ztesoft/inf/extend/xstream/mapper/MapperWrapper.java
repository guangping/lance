/*     */ package com.ztesoft.inf.extend.xstream.mapper;
/*     */ 
/*     */ import com.ztesoft.inf.extend.xstream.converters.Converter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.SingleValueConverter;
/*     */ 
/*     */ public abstract class MapperWrapper
/*     */   implements Mapper
/*     */ {
/*     */   private final Mapper wrapped;
/*     */ 
/*     */   public MapperWrapper(Mapper wrapped)
/*     */   {
/*  22 */     this.wrapped = wrapped;
/*     */   }
/*     */ 
/*     */   public String serializedClass(Class type) {
/*  26 */     return this.wrapped.serializedClass(type);
/*     */   }
/*     */ 
/*     */   public Class realClass(String elementName) {
/*  30 */     return this.wrapped.realClass(elementName);
/*     */   }
/*     */ 
/*     */   public String serializedMember(Class type, String memberName) {
/*  34 */     return this.wrapped.serializedMember(type, memberName);
/*     */   }
/*     */ 
/*     */   public String realMember(Class type, String serialized) {
/*  38 */     return this.wrapped.realMember(type, serialized);
/*     */   }
/*     */ 
/*     */   public boolean isImmutableValueType(Class type) {
/*  42 */     return this.wrapped.isImmutableValueType(type);
/*     */   }
/*     */ 
/*     */   public Class defaultImplementationOf(Class type) {
/*  46 */     return this.wrapped.defaultImplementationOf(type);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public String attributeForClassDefiningField()
/*     */   {
/*  54 */     return this.wrapped.attributeForClassDefiningField();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public String attributeForImplementationClass()
/*     */   {
/*  62 */     return this.wrapped.attributeForImplementationClass();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public String attributeForReadResolveField()
/*     */   {
/*  70 */     return this.wrapped.attributeForReadResolveField();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public String attributeForEnumType()
/*     */   {
/*  78 */     return this.wrapped.attributeForEnumType();
/*     */   }
/*     */ 
/*     */   public String aliasForAttribute(String attribute) {
/*  82 */     return this.wrapped.aliasForAttribute(attribute);
/*     */   }
/*     */ 
/*     */   public String attributeForAlias(String alias) {
/*  86 */     return this.wrapped.attributeForAlias(alias);
/*     */   }
/*     */ 
/*     */   public String aliasForSystemAttribute(String attribute) {
/*  90 */     return this.wrapped.aliasForSystemAttribute(attribute);
/*     */   }
/*     */ 
/*     */   public String getFieldNameForItemTypeAndName(Class definedIn, Class itemType, String itemFieldName)
/*     */   {
/*  95 */     return this.wrapped.getFieldNameForItemTypeAndName(definedIn, itemType, itemFieldName);
/*     */   }
/*     */ 
/*     */   public Class getItemTypeForItemFieldName(Class definedIn, String itemFieldName)
/*     */   {
/* 101 */     return this.wrapped.getItemTypeForItemFieldName(definedIn, itemFieldName);
/*     */   }
/*     */ 
/*     */   public Mapper.ImplicitCollectionMapping getImplicitCollectionDefForFieldName(Class itemType, String fieldName)
/*     */   {
/* 106 */     return this.wrapped.getImplicitCollectionDefForFieldName(itemType, fieldName);
/*     */   }
/*     */ 
/*     */   public boolean shouldSerializeMember(Class definedIn, String fieldName)
/*     */   {
/* 111 */     return this.wrapped.shouldSerializeMember(definedIn, fieldName);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public SingleValueConverter getConverterFromItemType(String fieldName, Class type)
/*     */   {
/* 121 */     return this.wrapped.getConverterFromItemType(fieldName, type);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public SingleValueConverter getConverterFromItemType(Class type)
/*     */   {
/* 130 */     return this.wrapped.getConverterFromItemType(type);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public SingleValueConverter getConverterFromAttribute(String name)
/*     */   {
/* 139 */     return this.wrapped.getConverterFromAttribute(name);
/*     */   }
/*     */ 
/*     */   public Converter getLocalConverter(Class definedIn, String fieldName) {
/* 143 */     return this.wrapped.getLocalConverter(definedIn, fieldName);
/*     */   }
/*     */ 
/*     */   public Mapper lookupMapperOfType(Class type) {
/* 147 */     return type.isAssignableFrom(getClass()) ? this : this.wrapped.lookupMapperOfType(type);
/*     */   }
/*     */ 
/*     */   public SingleValueConverter getConverterFromItemType(String fieldName, Class type, Class definedIn)
/*     */   {
/* 153 */     return this.wrapped.getConverterFromItemType(fieldName, type, definedIn);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public String aliasForAttribute(Class definedIn, String fieldName)
/*     */   {
/* 163 */     return this.wrapped.aliasForAttribute(definedIn, fieldName);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public String attributeForAlias(Class definedIn, String alias)
/*     */   {
/* 173 */     return this.wrapped.attributeForAlias(definedIn, alias);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public SingleValueConverter getConverterFromAttribute(Class type, String attribute)
/*     */   {
/* 183 */     return this.wrapped.getConverterFromAttribute(type, attribute);
/*     */   }
/*     */ 
/*     */   public SingleValueConverter getConverterFromAttribute(Class definedIn, String attribute, Class type)
/*     */   {
/* 188 */     return this.wrapped.getConverterFromAttribute(definedIn, attribute, type);
/*     */   }
/*     */ 
/*     */   public Class realClassByPath(String path) {
/* 192 */     return this.wrapped.realClassByPath(path);
/*     */   }
/*     */ 
/*     */   public String getColFieldNameByPath(String path) {
/* 196 */     return this.wrapped.getColFieldNameByPath(path);
/*     */   }
/*     */ 
/*     */   public String getAliasNameByPath(String path) {
/* 200 */     return this.wrapped.getAliasNameByPath(path);
/*     */   }
/*     */ 
/*     */   public String getImplicitCollectionItemNameByPath(String path) {
/* 204 */     return this.wrapped.getImplicitCollectionItemNameByPath(path);
/*     */   }
/*     */ 
/*     */   public String genMapNodeNameByPath(String path) {
/* 208 */     return this.wrapped.genMapNodeNameByPath(path);
/*     */   }
/*     */ 
/*     */   public boolean globalImplicitCollection() {
/* 212 */     return this.wrapped.globalImplicitCollection();
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.mapper.MapperWrapper
 * JD-Core Version:    0.6.2
 */