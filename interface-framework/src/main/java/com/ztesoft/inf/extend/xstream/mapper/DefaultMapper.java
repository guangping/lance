/*     */ package com.ztesoft.inf.extend.xstream.mapper;
/*     */ 
/*     */ import com.ztesoft.inf.extend.xstream.converters.Converter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.SingleValueConverter;
/*     */ 
/*     */ public class DefaultMapper
/*     */   implements Mapper
/*     */ {
/*     */   private ClassLoader classLoader;
/*  27 */   private boolean useSimpleName = true;
/*  28 */   private boolean useGlobalImplicitCollection = false;
/*     */ 
/*     */   @Deprecated
/*     */   private transient String classAttributeIdentifier;
/*     */ 
/*     */   public DefaultMapper()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setClassLoader(ClassLoader classLoader) {
/*  39 */     this.classLoader = classLoader;
/*     */   }
/*     */ 
/*     */   public DefaultMapper(ClassLoader classLoader) {
/*  43 */     this.classLoader = classLoader;
/*  44 */     this.classAttributeIdentifier = "class";
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public DefaultMapper(ClassLoader classLoader, String classAttributeIdentifier)
/*     */   {
/*  54 */     this(classLoader);
/*  55 */     this.classAttributeIdentifier = (classAttributeIdentifier == null ? "class" : classAttributeIdentifier);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   private Object readResolve()
/*     */   {
/*  64 */     this.classAttributeIdentifier = "class";
/*  65 */     return this;
/*     */   }
/*     */ 
/*     */   public String serializedClass(Class type) {
/*  69 */     if (this.useSimpleName)
/*  70 */       return type.getSimpleName();
/*  71 */     return type.getName();
/*     */   }
/*     */ 
/*     */   public Class realClass(String elementName) {
/*     */     try {
/*  76 */       if (elementName.charAt(0) != '[')
/*  77 */         return this.classLoader.loadClass(elementName);
/*  78 */       if (elementName.endsWith(";")) {
/*  79 */         return Class.forName(elementName.toString(), false, this.classLoader);
/*     */       }
/*     */ 
/*  82 */       return Class.forName(elementName.toString());
/*     */     }
/*     */     catch (ClassNotFoundException e) {
/*  85 */       throw new CannotResolveClassException(elementName + " : " + e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public Class defaultImplementationOf(Class type)
/*     */   {
/*  91 */     return type;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public String attributeForClassDefiningField()
/*     */   {
/*  99 */     return "defined-in";
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public String attributeForReadResolveField()
/*     */   {
/* 107 */     return "resolves-to";
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public String attributeForEnumType()
/*     */   {
/* 115 */     return "enum-type";
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public String attributeForImplementationClass()
/*     */   {
/* 123 */     return this.classAttributeIdentifier;
/*     */   }
/*     */ 
/*     */   public String aliasForAttribute(String attribute) {
/* 127 */     return attribute;
/*     */   }
/*     */ 
/*     */   public String attributeForAlias(String alias) {
/* 131 */     return alias;
/*     */   }
/*     */ 
/*     */   public String aliasForSystemAttribute(String attribute) {
/* 135 */     return attribute;
/*     */   }
/*     */ 
/*     */   public boolean isImmutableValueType(Class type) {
/* 139 */     return false;
/*     */   }
/*     */ 
/*     */   public String getFieldNameForItemTypeAndName(Class definedIn, Class itemType, String itemFieldName)
/*     */   {
/* 144 */     return null;
/*     */   }
/*     */ 
/*     */   public Class getItemTypeForItemFieldName(Class definedIn, String itemFieldName)
/*     */   {
/* 149 */     return null;
/*     */   }
/*     */ 
/*     */   public Mapper.ImplicitCollectionMapping getImplicitCollectionDefForFieldName(Class itemType, String fieldName)
/*     */   {
/* 154 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean shouldSerializeMember(Class definedIn, String fieldName) {
/* 158 */     return true;
/*     */   }
/*     */ 
/*     */   public String lookupName(Class type) {
/* 162 */     return serializedClass(type);
/*     */   }
/*     */ 
/*     */   public Class lookupType(String elementName) {
/* 166 */     return realClass(elementName);
/*     */   }
/*     */ 
/*     */   public String serializedMember(Class type, String memberName) {
/* 170 */     return memberName;
/*     */   }
/*     */ 
/*     */   public String realMember(Class type, String serialized) {
/* 174 */     return serialized;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public SingleValueConverter getConverterFromAttribute(String name)
/*     */   {
/* 183 */     return null;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public SingleValueConverter getConverterFromItemType(String fieldName, Class type)
/*     */   {
/* 193 */     return null;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public SingleValueConverter getConverterFromItemType(Class type)
/*     */   {
/* 202 */     return null;
/*     */   }
/*     */ 
/*     */   public SingleValueConverter getConverterFromItemType(String fieldName, Class type, Class definedIn)
/*     */   {
/* 207 */     return null;
/*     */   }
/*     */ 
/*     */   public Converter getLocalConverter(Class definedIn, String fieldName) {
/* 211 */     return null;
/*     */   }
/*     */ 
/*     */   public Mapper lookupMapperOfType(Class type) {
/* 215 */     return null;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public String aliasForAttribute(Class definedIn, String fieldName)
/*     */   {
/* 225 */     return fieldName;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public String attributeForAlias(Class definedIn, String alias)
/*     */   {
/* 235 */     return alias;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public SingleValueConverter getConverterFromAttribute(Class definedIn, String attribute)
/*     */   {
/* 245 */     return null;
/*     */   }
/*     */ 
/*     */   public SingleValueConverter getConverterFromAttribute(Class definedIn, String attribute, Class type)
/*     */   {
/* 250 */     return null;
/*     */   }
/*     */ 
/*     */   public Class realClassByPath(String path) {
/* 254 */     return null;
/*     */   }
/*     */ 
/*     */   public String getColFieldNameByPath(String path) {
/* 258 */     return null;
/*     */   }
/*     */ 
/*     */   public String getAliasNameByPath(String path) {
/* 262 */     return null;
/*     */   }
/*     */ 
/*     */   public String getImplicitCollectionItemNameByPath(String path) {
/* 266 */     return null;
/*     */   }
/*     */ 
/*     */   public String genMapNodeNameByPath(String path) {
/* 270 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean globalImplicitCollection() {
/* 274 */     return this.useGlobalImplicitCollection;
/*     */   }
/*     */ 
/*     */   public void useGlobalImplicitCollection(boolean flag) {
/* 278 */     this.useGlobalImplicitCollection = flag;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.mapper.DefaultMapper
 * JD-Core Version:    0.6.2
 */