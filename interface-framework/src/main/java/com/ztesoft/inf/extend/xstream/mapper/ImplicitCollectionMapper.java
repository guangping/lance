/*     */ package com.ztesoft.inf.extend.xstream.mapper;
/*     */ 
/*     */ import com.ztesoft.inf.extend.xstream.InitializationException;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class ImplicitCollectionMapper extends MapperWrapper
/*     */ {
/*  17 */   private final Map classNameToMapper = new HashMap();
/*     */ 
/*     */   public ImplicitCollectionMapper(Mapper wrapped)
/*     */   {
/*  14 */     super(wrapped);
/*     */   }
/*     */ 
/*     */   private ImplicitCollectionMapperForClass getMapper(Class _definedIn)
/*     */   {
/*  20 */     Class definedIn = _definedIn;
/*  21 */     while (definedIn != null) {
/*  22 */       ImplicitCollectionMapperForClass mapper = (ImplicitCollectionMapperForClass)this.classNameToMapper.get(definedIn);
/*     */ 
/*  24 */       if (mapper != null) {
/*  25 */         return mapper;
/*     */       }
/*  27 */       definedIn = definedIn.getSuperclass();
/*     */     }
/*  29 */     return null;
/*     */   }
/*     */ 
/*     */   private ImplicitCollectionMapperForClass getOrCreateMapper(Class definedIn) {
/*  33 */     ImplicitCollectionMapperForClass mapper = getMapper(definedIn);
/*  34 */     if (mapper == null) {
/*  35 */       mapper = new ImplicitCollectionMapperForClass(null);
/*  36 */       this.classNameToMapper.put(definedIn, mapper);
/*     */     }
/*  38 */     return mapper;
/*     */   }
/*     */ 
/*     */   public String getFieldNameForItemTypeAndName(Class definedIn, Class itemType, String itemFieldName)
/*     */   {
/*  44 */     ImplicitCollectionMapperForClass mapper = getMapper(definedIn);
/*  45 */     if (mapper != null) {
/*  46 */       return mapper.getFieldNameForItemTypeAndName(itemType, itemFieldName);
/*     */     }
/*     */ 
/*  49 */     return null;
/*     */   }
/*     */ 
/*     */   public Class getItemTypeForItemFieldName(Class definedIn, String itemFieldName)
/*     */   {
/*  56 */     ImplicitCollectionMapperForClass mapper = getMapper(definedIn);
/*  57 */     if (mapper != null) {
/*  58 */       return mapper.getItemTypeForItemFieldName(itemFieldName);
/*     */     }
/*  60 */     return null;
/*     */   }
/*     */ 
/*     */   public Mapper.ImplicitCollectionMapping getImplicitCollectionDefForFieldName(Class itemType, String fieldName)
/*     */   {
/*  67 */     ImplicitCollectionMapperForClass mapper = getMapper(itemType);
/*  68 */     if (mapper != null) {
/*  69 */       return mapper.getImplicitCollectionDefForFieldName(fieldName);
/*     */     }
/*  71 */     return null;
/*     */   }
/*     */ 
/*     */   public void add(Class definedIn, String fieldName, Class itemType)
/*     */   {
/*  76 */     add(definedIn, fieldName, null, itemType);
/*     */   }
/*     */ 
/*     */   public void add(Class _definedIn, String fieldName, String itemFieldName, Class itemType)
/*     */   {
/*  81 */     Field field = null;
/*  82 */     Class definedIn = _definedIn;
/*  83 */     while (definedIn != Object.class) {
/*     */       try {
/*  85 */         field = definedIn.getDeclaredField(fieldName);
/*     */       }
/*     */       catch (SecurityException e) {
/*  88 */         throw new InitializationException("Access denied for field with implicit collection", e);
/*     */       }
/*     */       catch (NoSuchFieldException e) {
/*  91 */         definedIn = definedIn.getSuperclass();
/*     */       }
/*     */     }
/*  94 */     if (field == null) {
/*  95 */       throw new InitializationException("No field \"" + fieldName + "\" for implicit collection");
/*     */     }
/*  97 */     if (!Collection.class.isAssignableFrom(field.getType())) {
/*  98 */       throw new InitializationException("Field \"" + fieldName + "\" declares no collection");
/*     */     }
/*     */ 
/* 101 */     ImplicitCollectionMapperForClass mapper = getOrCreateMapper(definedIn);
/* 102 */     mapper.add(new ImplicitCollectionMappingImpl(fieldName, itemType, itemFieldName));
/*     */   }
/*     */ 
/*     */   private static class NamedItemType
/*     */   {
/*     */     Class itemType;
/*     */     String itemFieldName;
/*     */ 
/*     */     NamedItemType(Class itemType, String itemFieldName)
/*     */     {
/* 237 */       this.itemType = itemType;
/* 238 */       this.itemFieldName = itemFieldName;
/*     */     }
/*     */ 
/*     */     public boolean equals(Object obj)
/*     */     {
/* 243 */       if ((obj instanceof NamedItemType)) {
/* 244 */         NamedItemType b = (NamedItemType)obj;
/* 245 */         return (this.itemType.equals(b.itemType)) && (isEquals(this.itemFieldName, b.itemFieldName));
/*     */       }
/*     */ 
/* 248 */       return false;
/*     */     }
/*     */ 
/*     */     private static boolean isEquals(Object a, Object b)
/*     */     {
/* 253 */       if (a == null) {
/* 254 */         return b == null;
/*     */       }
/* 256 */       return a.equals(b);
/*     */     }
/*     */ 
/*     */     public int hashCode()
/*     */     {
/* 262 */       int hash = this.itemType.hashCode() << 7;
/* 263 */       if (this.itemFieldName != null) {
/* 264 */         hash += this.itemFieldName.hashCode();
/*     */       }
/* 266 */       return hash;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class ImplicitCollectionMappingImpl
/*     */     implements Mapper.ImplicitCollectionMapping
/*     */   {
/*     */     private String fieldName;
/*     */     private String itemFieldName;
/*     */     private Class itemType;
/*     */ 
/*     */     ImplicitCollectionMappingImpl(String fieldName, Class itemType, String itemFieldName)
/*     */     {
/* 182 */       this.fieldName = fieldName;
/* 183 */       this.itemFieldName = itemFieldName;
/* 184 */       this.itemType = (itemType == null ? Object.class : itemType);
/*     */     }
/*     */ 
/*     */     public boolean equals(Object obj)
/*     */     {
/* 189 */       if ((obj instanceof ImplicitCollectionMappingImpl)) {
/* 190 */         ImplicitCollectionMappingImpl b = (ImplicitCollectionMappingImpl)obj;
/* 191 */         return (this.fieldName.equals(b.fieldName)) && (isEquals(this.itemFieldName, b.itemFieldName));
/*     */       }
/*     */ 
/* 194 */       return false;
/*     */     }
/*     */ 
/*     */     public ImplicitCollectionMapper.NamedItemType createNamedItemType()
/*     */     {
/* 199 */       return new ImplicitCollectionMapper.NamedItemType(this.itemType, this.itemFieldName);
/*     */     }
/*     */ 
/*     */     private static boolean isEquals(Object a, Object b) {
/* 203 */       if (a == null) {
/* 204 */         return b == null;
/*     */       }
/* 206 */       return a.equals(b);
/*     */     }
/*     */ 
/*     */     public int hashCode()
/*     */     {
/* 212 */       int hash = this.fieldName.hashCode();
/* 213 */       if (this.itemFieldName != null) {
/* 214 */         hash += (this.itemFieldName.hashCode() << 7);
/*     */       }
/* 216 */       return hash;
/*     */     }
/*     */ 
/*     */     public String getFieldName() {
/* 220 */       return this.fieldName;
/*     */     }
/*     */ 
/*     */     public String getItemFieldName() {
/* 224 */       return this.itemFieldName;
/*     */     }
/*     */ 
/*     */     public Class getItemType() {
/* 228 */       return this.itemType;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class ImplicitCollectionMapperForClass
/*     */   {
/* 107 */     private Map namedItemTypeToDef = new HashMap();
/* 108 */     private Map itemFieldNameToDef = new HashMap();
/* 109 */     private Map fieldNameToDef = new HashMap();
/*     */ 
/*     */     public String getFieldNameForItemTypeAndName(Class itemType, String itemFieldName)
/*     */     {
/* 113 */       ImplicitCollectionMapper.ImplicitCollectionMappingImpl unnamed = null;
/* 114 */       Iterator iterator = this.namedItemTypeToDef.keySet().iterator();
/* 115 */       while (iterator.hasNext()) {
/* 116 */         ImplicitCollectionMapper.NamedItemType itemTypeForFieldName = (ImplicitCollectionMapper.NamedItemType)iterator.next();
/*     */ 
/* 118 */         ImplicitCollectionMapper.ImplicitCollectionMappingImpl def = (ImplicitCollectionMapper.ImplicitCollectionMappingImpl)this.namedItemTypeToDef.get(itemTypeForFieldName);
/*     */ 
/* 120 */         if (itemType == Mapper.Null.class) {
/* 121 */           unnamed = def;
/*     */         }
/* 123 */         else if (itemTypeForFieldName.itemType.isAssignableFrom(itemType))
/*     */         {
/* 125 */           if (def.getItemFieldName() != null) {
/* 126 */             if (def.getItemFieldName().equals(itemFieldName))
/* 127 */               return def.getFieldName();
/*     */           }
/*     */           else {
/* 130 */             unnamed = def;
/* 131 */             if (itemFieldName == null) {
/*     */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 137 */       return unnamed != null ? unnamed.getFieldName() : null;
/*     */     }
/*     */ 
/*     */     public Class getItemTypeForItemFieldName(String itemFieldName) {
/* 141 */       ImplicitCollectionMapper.ImplicitCollectionMappingImpl def = getImplicitCollectionDefByItemFieldName(itemFieldName);
/* 142 */       if (def != null) {
/* 143 */         return def.getItemType();
/*     */       }
/* 145 */       return null;
/*     */     }
/*     */ 
/*     */     private ImplicitCollectionMapper.ImplicitCollectionMappingImpl getImplicitCollectionDefByItemFieldName(String itemFieldName)
/*     */     {
/* 151 */       if (itemFieldName == null) {
/* 152 */         return null;
/*     */       }
/* 154 */       return (ImplicitCollectionMapper.ImplicitCollectionMappingImpl)this.itemFieldNameToDef.get(itemFieldName);
/*     */     }
/*     */ 
/*     */     public Mapper.ImplicitCollectionMapping getImplicitCollectionDefForFieldName(String fieldName)
/*     */     {
/* 161 */       return (Mapper.ImplicitCollectionMapping)this.fieldNameToDef.get(fieldName);
/*     */     }
/*     */ 
/*     */     public void add(ImplicitCollectionMapper.ImplicitCollectionMappingImpl def) {
/* 165 */       this.fieldNameToDef.put(def.getFieldName(), def);
/* 166 */       this.namedItemTypeToDef.put(def.createNamedItemType(), def);
/* 167 */       if (def.getItemFieldName() != null)
/* 168 */         this.itemFieldNameToDef.put(def.getItemFieldName(), def);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.mapper.ImplicitCollectionMapper
 * JD-Core Version:    0.6.2
 */