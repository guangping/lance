/*     */ package com.ztesoft.inf.extend.xstream.mapper;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.springframework.util.StringUtils;
/*     */ 
/*     */ public class XPathMapper extends MapperWrapper
/*     */ {
/*  13 */   private final Map<String, String> xpathToType = new HashMap();
/*  14 */   private final Map<String, String> xpathToCollFieldName = new HashMap();
/*  15 */   private final Map<String, String> xpathToAliasName = new HashMap();
/*  16 */   private final Map<String, String> xpathToImplicitItemName = new HashMap();
/*     */   private String mapNodeElementSuffix;
/*     */ 
/*     */   public XPathMapper(Mapper wrapped)
/*     */   {
/*  10 */     super(wrapped);
/*     */   }
/*     */ 
/*     */   public void addXpathType(String xpath, String typeName)
/*     */   {
/*  20 */     this.xpathToType.put(xpath, typeName);
/*     */   }
/*     */ 
/*     */   public void addXpathType(String xpath, Class type) {
/*  24 */     this.xpathToType.put(xpath, type.getName());
/*     */   }
/*     */ 
/*     */   public void addXpathAlias(String xpath, String nodeName) {
/*  28 */     this.xpathToAliasName.put(xpath, nodeName);
/*     */   }
/*     */ 
/*     */   public Class realClassByPath(String path)
/*     */   {
/*  33 */     String mappedName = null;
/*  34 */     for (String key : this.xpathToType.keySet()) {
/*  35 */       if (match(path, key)) {
/*  36 */         mappedName = (String)this.xpathToType.get(key);
/*  37 */         break;
/*     */       }
/*     */     }
/*  40 */     if (mappedName != null) {
/*  41 */       return super.realClass(mappedName);
/*     */     }
/*  43 */     return super.realClassByPath(path);
/*     */   }
/*     */ 
/*     */   private boolean match(String xpath, String toMatch) {
/*  47 */     return toMatch.equals(xpath.replaceAll("\\[\\d*\\]", ""));
/*     */   }
/*     */ 
/*     */   public void addXpathImplicitCollection(String xpath, String nodeName, String fieldName, Class itemType)
/*     */   {
/*  52 */     if (itemType != null)
/*  53 */       this.xpathToType.put(xpath + "/" + nodeName, itemType.getName());
/*  54 */     this.xpathToCollFieldName.put(xpath + "/" + nodeName, fieldName);
/*  55 */     this.xpathToImplicitItemName.put(xpath + "/" + fieldName, nodeName);
/*     */   }
/*     */ 
/*     */   public String getColFieldNameByPath(String path)
/*     */   {
/*  60 */     String mappedName = null;
/*  61 */     for (String key : this.xpathToCollFieldName.keySet()) {
/*  62 */       if (match(path, key)) {
/*  63 */         mappedName = (String)this.xpathToCollFieldName.get(key);
/*  64 */         break;
/*     */       }
/*     */     }
/*  67 */     return mappedName;
/*     */   }
/*     */ 
/*     */   public String getAliasNameByPath(String path)
/*     */   {
/*  72 */     String mappedName = null;
/*  73 */     for (String key : this.xpathToAliasName.keySet()) {
/*  74 */       if (match(path, key)) {
/*  75 */         mappedName = (String)this.xpathToAliasName.get(key);
/*  76 */         break;
/*     */       }
/*     */     }
/*  79 */     return mappedName;
/*     */   }
/*     */ 
/*     */   public String getImplicitCollectionItemNameByPath(String path)
/*     */   {
/*  84 */     String mappedName = null;
/*  85 */     for (String key : this.xpathToImplicitItemName.keySet()) {
/*  86 */       if (match(path, key)) {
/*  87 */         mappedName = (String)this.xpathToImplicitItemName.get(key);
/*  88 */         break;
/*     */       }
/*     */     }
/*  91 */     return mappedName;
/*     */   }
/*     */ 
/*     */   public String genMapNodeNameByPath(String path)
/*     */   {
/*  96 */     int i = path.toString().lastIndexOf("/");
/*  97 */     return path.substring(i + 1) + (StringUtils.hasLength(this.mapNodeElementSuffix) ? this.mapNodeElementSuffix : "_Item");
/*     */   }
/*     */ 
/*     */   public void setMapNodeElementSuffix(String suffix)
/*     */   {
/* 103 */     this.mapNodeElementSuffix = suffix;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.mapper.XPathMapper
 * JD-Core Version:    0.6.2
 */