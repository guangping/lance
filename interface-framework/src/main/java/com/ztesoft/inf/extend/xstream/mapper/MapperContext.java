/*     */ package com.ztesoft.inf.extend.xstream.mapper;
/*     */ 
/*     */ import com.ztesoft.inf.extend.xstream.InitializationException;
/*     */ import com.ztesoft.inf.extend.xstream.converters.ConverterLookup;
/*     */ import com.ztesoft.inf.extend.xstream.converters.DataHolder;
/*     */ import com.ztesoft.inf.extend.xstream.core.MapBackedDataHolder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.springframework.util.StringUtils;
/*     */ 
/*     */ public class MapperContext
/*     */   implements DataHolder
/*     */ {
/*     */   private static final String ROOT_TYPE = "_ROOT_TYPE_";
/*     */   private Mapper mapper;
/*     */   private DefaultMapper defaultMapper;
/*     */   private ClassAliasingMapper classAliasingMapper;
/*     */   private FieldAliasingMapper fieldAliasingMapper;
/*     */   private XPathMapper xpathMapper;
/*     */   private AttributeAliasingMapper attributeAliasingMapper;
/*     */   private AttributeMapper attributeMapper;
/*     */   private DefaultImplementationsMapper defaultImplementationsMapper;
/*     */   private ImplicitCollectionMapper implicitCollectionMapper;
/*  33 */   private DataHolder dataHolder = new MapBackedDataHolder();
/*     */ 
/*     */   public MapperContext() {
/*  36 */     this.mapper = buildMapper();
/*  37 */     setupMappers();
/*  38 */     setupDefaultImplementations();
/*  39 */     put("_ROOT_TYPE_", Map.class);
/*     */   }
/*     */ 
/*     */   public void setClassLoader(ClassLoader classLoader) {
/*  43 */     this.defaultMapper.setClassLoader(classLoader);
/*     */   }
/*     */ 
/*     */   public void setConverterLookup(ConverterLookup converterLookup) {
/*  47 */     this.attributeMapper.setConverterLookup(converterLookup);
/*     */   }
/*     */ 
/*     */   private Mapper buildMapper() {
/*  51 */     Mapper mapper = new DefaultMapper();
/*  52 */     this.defaultMapper = ((DefaultMapper)mapper);
/*  53 */     mapper = new ClassAliasingMapper(mapper);
/*  54 */     mapper = new FieldAliasingMapper(mapper);
/*  55 */     mapper = new AttributeAliasingMapper(mapper);
/*  56 */     mapper = new XPathMapper(mapper);
/*  57 */     mapper = new ImplicitCollectionMapper(mapper);
/*  58 */     mapper = new ArrayMapper(mapper);
/*  59 */     mapper = new DefaultImplementationsMapper(mapper);
/*  60 */     mapper = new AttributeMapper(mapper);
/*  61 */     mapper = new CachingMapper(mapper);
/*  62 */     return mapper;
/*     */   }
/*     */ 
/*     */   protected void setupDefaultImplementations() {
/*  66 */     if (this.defaultImplementationsMapper == null) {
/*  67 */       return;
/*     */     }
/*  69 */     addDefaultImplementation(HashMap.class, Map.class);
/*  70 */     addDefaultImplementation(ArrayList.class, List.class);
/*  71 */     addDefaultImplementation(HashSet.class, Set.class);
/*  72 */     addDefaultImplementation(GregorianCalendar.class, Calendar.class);
/*     */   }
/*     */ 
/*     */   private void setupMappers() {
/*  76 */     this.xpathMapper = ((XPathMapper)this.mapper.lookupMapperOfType(XPathMapper.class));
/*     */ 
/*  78 */     this.classAliasingMapper = ((ClassAliasingMapper)this.mapper.lookupMapperOfType(ClassAliasingMapper.class));
/*     */ 
/*  80 */     this.fieldAliasingMapper = ((FieldAliasingMapper)this.mapper.lookupMapperOfType(FieldAliasingMapper.class));
/*     */ 
/*  82 */     this.attributeMapper = ((AttributeMapper)this.mapper.lookupMapperOfType(AttributeMapper.class));
/*     */ 
/*  84 */     this.attributeAliasingMapper = ((AttributeAliasingMapper)this.mapper.lookupMapperOfType(AttributeAliasingMapper.class));
/*     */ 
/*  86 */     this.implicitCollectionMapper = ((ImplicitCollectionMapper)this.mapper.lookupMapperOfType(ImplicitCollectionMapper.class));
/*     */ 
/*  88 */     this.defaultImplementationsMapper = ((DefaultImplementationsMapper)this.mapper.lookupMapperOfType(DefaultImplementationsMapper.class));
/*     */   }
/*     */ 
/*     */   public void xpathToCollection(String xpath, String nodeName, String itemType)
/*     */   {
/*  93 */     Class clz = null;
/*  94 */     if (StringUtils.hasLength(itemType)) {
/*     */       try {
/*  96 */         clz = Class.forName(itemType);
/*     */       } catch (ClassNotFoundException e) {
/*  98 */         throw new IllegalArgumentException();
/*     */       }
/*     */     }
/* 101 */     xpathToCollection(xpath, nodeName, clz);
/*     */   }
/*     */ 
/*     */   public void xpathToCollection(String xpath, String nodeName, Class _itemType) {
/* 105 */     if (this.xpathMapper == null) {
/* 106 */       throw new InitializationException("No " + XPathMapper.class.getName() + " available");
/*     */     }
/*     */ 
/* 109 */     this.xpathMapper.addXpathType(xpath, this.defaultImplementationsMapper.defaultImplementationOf(Collection.class));
/*     */ 
/* 111 */     if (nodeName != null) {
/* 112 */       this.xpathMapper.addXpathAlias(xpath, nodeName);
/*     */     }
/* 114 */     Class itemType = _itemType;
/* 115 */     if (itemType == null) {
/* 116 */       itemType = this.defaultImplementationsMapper.defaultImplementationOf(Map.class);
/*     */     }
/*     */ 
/* 119 */     this.xpathMapper.addXpathType(xpath + "/" + nodeName, itemType);
/*     */   }
/*     */ 
/*     */   public void xpathAlias(String xpath, String aliasName) {
/* 123 */     this.xpathMapper.addXpathAlias(xpath, aliasName);
/*     */   }
/*     */ 
/*     */   public void xpathToCollection(String xpath, String nodeName) {
/* 127 */     xpathToCollection(xpath, nodeName, (Class)null);
/*     */   }
/*     */ 
/*     */   public void xpathToCollection(String xpath) {
/* 131 */     xpathToCollection(xpath, null, (Class)null);
/*     */   }
/*     */ 
/*     */   public void setRootNodeName(String rootNodeName) {
/* 135 */     this.dataHolder.put("_RootName_", rootNodeName);
/*     */   }
/*     */ 
/*     */   public void addXpathImplicitCollection(String xpath, String nodeName, String fieldName, String itemType)
/*     */   {
/* 140 */     Class clz = null;
/* 141 */     if (StringUtils.hasLength(itemType)) {
/*     */       try {
/* 143 */         clz = Class.forName(itemType);
/*     */       } catch (ClassNotFoundException e) {
/* 145 */         throw new IllegalArgumentException();
/*     */       }
/*     */     }
/* 148 */     addXpathImplicitCollection(xpath, nodeName, fieldName, clz);
/*     */   }
/*     */ 
/*     */   public void addXpathImplicitCollection(String xpath, String nodeName, String fieldName, Class _itemType)
/*     */   {
/* 153 */     if (this.xpathMapper == null) {
/* 154 */       throw new InitializationException("No " + XPathMapper.class.getName() + " available");
/*     */     }
/*     */ 
/* 157 */     Class itemType = _itemType;
/* 158 */     if (_itemType == null) {
/* 159 */       itemType = this.defaultImplementationsMapper.defaultImplementationOf(Map.class);
/*     */     }
/*     */ 
/* 162 */     this.xpathMapper.addXpathImplicitCollection(xpath, nodeName, fieldName, itemType);
/*     */   }
/*     */ 
/*     */   public void addXpathImplicitCollection(String xpath, String nodeName, String fieldName)
/*     */   {
/* 168 */     addXpathImplicitCollection(xpath, nodeName, fieldName, (Class)null);
/*     */   }
/*     */ 
/*     */   public void omitField(Class definedIn, String fieldName) {
/* 172 */     if (this.fieldAliasingMapper == null) {
/* 173 */       throw new InitializationException("No " + FieldAliasingMapper.class.getName() + " available");
/*     */     }
/*     */ 
/* 176 */     this.fieldAliasingMapper.omitField(definedIn, fieldName);
/*     */   }
/*     */ 
/*     */   public void addImplicitCollection(Class ownerType, String fieldName) {
/* 180 */     if (this.implicitCollectionMapper == null) {
/* 181 */       throw new InitializationException("No " + ImplicitCollectionMapper.class.getName() + " available");
/*     */     }
/*     */ 
/* 184 */     this.implicitCollectionMapper.add(ownerType, fieldName, null, null);
/*     */   }
/*     */ 
/*     */   public void addImplicitCollection(Class ownerType, String fieldName, Class itemType)
/*     */   {
/* 189 */     if (this.implicitCollectionMapper == null) {
/* 190 */       throw new InitializationException("No " + ImplicitCollectionMapper.class.getName() + " available");
/*     */     }
/*     */ 
/* 193 */     this.implicitCollectionMapper.add(ownerType, fieldName, null, itemType);
/*     */   }
/*     */ 
/*     */   public void addImplicitCollection(Class ownerType, String fieldName, String itemFieldName, Class itemType)
/*     */   {
/* 198 */     if (this.implicitCollectionMapper == null) {
/* 199 */       throw new InitializationException("No " + ImplicitCollectionMapper.class.getName() + " available");
/*     */     }
/*     */ 
/* 202 */     this.implicitCollectionMapper.add(ownerType, fieldName, itemFieldName, itemType);
/*     */   }
/*     */ 
/*     */   public void alias(String name, Class type)
/*     */   {
/* 207 */     if (this.classAliasingMapper == null) {
/* 208 */       throw new InitializationException("No " + ClassAliasingMapper.class.getName() + " available");
/*     */     }
/*     */ 
/* 211 */     this.classAliasingMapper.addClassAlias(name, type);
/*     */   }
/*     */ 
/*     */   public void xpathToType(String name, Class type) {
/* 215 */     if (this.xpathMapper == null) {
/* 216 */       throw new InitializationException("No " + XPathMapper.class.getName() + " available");
/*     */     }
/*     */ 
/* 219 */     this.xpathMapper.addXpathType(name, type);
/*     */   }
/*     */ 
/*     */   public void xpathToType(String name, String typeName) {
/* 223 */     if (this.xpathMapper == null) {
/* 224 */       throw new InitializationException("No " + XPathMapper.class.getName() + " available");
/*     */     }
/*     */ 
/* 227 */     this.xpathMapper.addXpathType(name, typeName);
/*     */   }
/*     */ 
/*     */   public void aliasType(String name, Class type) {
/* 231 */     if (this.classAliasingMapper == null) {
/* 232 */       throw new InitializationException("No " + ClassAliasingMapper.class.getName() + " available");
/*     */     }
/*     */ 
/* 235 */     this.classAliasingMapper.addTypeAlias(name, type);
/*     */   }
/*     */ 
/*     */   public void alias(String name, Class type, Class defaultImplementation) {
/* 239 */     alias(name, type);
/* 240 */     addDefaultImplementation(defaultImplementation, type);
/*     */   }
/*     */ 
/*     */   public void aliasField(String alias, Class definedIn, String fieldName) {
/* 244 */     if (this.fieldAliasingMapper == null) {
/* 245 */       throw new InitializationException("No " + FieldAliasingMapper.class.getName() + " available");
/*     */     }
/*     */ 
/* 248 */     this.fieldAliasingMapper.addFieldAlias(alias, definedIn, fieldName);
/*     */   }
/*     */ 
/*     */   public void aliasAttribute(String alias, String attributeName) {
/* 252 */     if (this.attributeAliasingMapper == null) {
/* 253 */       throw new InitializationException("No " + AttributeAliasingMapper.class.getName() + " available");
/*     */     }
/*     */ 
/* 256 */     this.attributeAliasingMapper.addAliasFor(attributeName, alias);
/*     */   }
/*     */ 
/*     */   public void aliasAttribute(Class definedIn, String attributeName, String alias)
/*     */   {
/* 261 */     aliasField(alias, definedIn, attributeName);
/* 262 */     useAttributeFor(definedIn, attributeName);
/*     */   }
/*     */ 
/*     */   public void useAttributeFor(String fieldName, Class type) {
/* 266 */     if (this.attributeMapper == null) {
/* 267 */       throw new InitializationException("No " + AttributeMapper.class.getName() + " available");
/*     */     }
/*     */ 
/* 270 */     this.attributeMapper.addAttributeFor(fieldName, type);
/*     */   }
/*     */ 
/*     */   public void useGlobalImplicitCollection(boolean flag) {
/* 274 */     this.defaultMapper.useGlobalImplicitCollection(flag);
/*     */   }
/*     */ 
/*     */   public void useAttributeFor(Class definedIn, String fieldName) {
/* 278 */     if (this.attributeMapper == null) {
/* 279 */       throw new InitializationException("No " + AttributeMapper.class.getName() + " available");
/*     */     }
/*     */ 
/* 282 */     this.attributeMapper.addAttributeFor(definedIn, fieldName);
/*     */   }
/*     */ 
/*     */   public void useAttributeFor(Class type) {
/* 286 */     if (this.attributeMapper == null) {
/* 287 */       throw new InitializationException("No " + AttributeMapper.class.getName() + " available");
/*     */     }
/*     */ 
/* 290 */     this.attributeMapper.addAttributeFor(type);
/*     */   }
/*     */ 
/*     */   public void addDefaultImplementation(Class defaultImplementation, Class ofType)
/*     */   {
/* 295 */     if (this.defaultImplementationsMapper == null) {
/* 296 */       throw new InitializationException("No " + DefaultImplementationsMapper.class.getName() + " available");
/*     */     }
/*     */ 
/* 300 */     this.defaultImplementationsMapper.addDefaultImplementation(defaultImplementation, ofType);
/*     */   }
/*     */ 
/*     */   public void setMapNodeElementSuffix(String suffix)
/*     */   {
/* 305 */     this.xpathMapper.setMapNodeElementSuffix(suffix);
/*     */   }
/*     */ 
/*     */   public Object get(Object key) {
/* 309 */     return this.dataHolder.get(key);
/*     */   }
/*     */ 
/*     */   public Iterator keys() {
/* 313 */     return this.dataHolder.keys();
/*     */   }
/*     */ 
/*     */   public void put(Object key, Object value) {
/* 317 */     this.dataHolder.put(key, value);
/*     */   }
/*     */ 
/*     */   public Mapper getMapper() {
/* 321 */     return this.mapper;
/*     */   }
/*     */ 
/*     */   public Class getRootType() {
/* 325 */     return (Class)get("_ROOT_TYPE_");
/*     */   }
/*     */ 
/*     */   public void setRootType(Class type) {
/* 329 */     put("_ROOT_TYPE_", type);
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.mapper.MapperContext
 * JD-Core Version:    0.6.2
 */