/*     */ package com.ztesoft.inf.extend.xstream.mapper;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.List;
/*     */ import org.dom4j.Attribute;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ import org.springframework.util.StringUtils;
/*     */ 
/*     */ public class MapperContextBuilder
/*     */ {
/*     */   public MapperContext build(String xml)
/*     */   {
/*     */     try
/*     */     {
/*  20 */       return buildImpl(xml);
/*     */     } catch (Exception e) {
/*  22 */       throw new RuntimeException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected MapperContext buildImpl(String xml)
/*     */     throws DocumentException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
/*     */   {
/*  29 */     MapperContext ctx = new MapperContext();
/*  30 */     if (!StringUtils.hasLength(xml)) {
/*  31 */       return ctx;
/*     */     }
/*  33 */     SAXReader saxReader = new SAXReader();
/*  34 */     InputStream in = new ByteArrayInputStream(xml.getBytes());
/*  35 */     Document doc = saxReader.read(in);
/*  36 */     List children = doc.getRootElement().elements();
/*  37 */     for (Element child : children) {
/*  38 */       String name = child.getName();
/*  39 */       String methodName = "process" + name;
/*     */       try {
/*  41 */         Method method = getClass().getDeclaredMethod(methodName, new Class[] { Element.class, MapperContext.class });
/*     */ 
/*  43 */         method.invoke(this, new Object[] { child, ctx });
/*     */       } catch (NoSuchMethodException e) {
/*  45 */         throw new RuntimeException("XSrteam配置错误,没有该配置项!", e);
/*     */       }
/*     */     }
/*  48 */     return ctx;
/*     */   }
/*     */ 
/*     */   protected void processUseGlobalImplicitCollection(Element child, MapperContext ctx)
/*     */   {
/*  53 */     String value = noNull(child, "value");
/*  54 */     ctx.useGlobalImplicitCollection(Boolean.valueOf(value).booleanValue());
/*     */   }
/*     */ 
/*     */   protected void processRootType(Element child, MapperContext ctx) {
/*  58 */     String clz = null;
/*     */     try {
/*  60 */       clz = noNull(child, "value");
/*  61 */       ctx.setRootType(Class.forName(clz));
/*     */     } catch (ClassNotFoundException e) {
/*  63 */       throw new RuntimeException("XSrteam配置错误,指定的类不存在!" + clz); } 
/*     */   }
/*  68 */   protected void processAddImplicitCollection(Element child, MapperContext ctx) { String itemTypeStr = nullable(child, "itemType");
/*  69 */     String ownerTypeStr = noNull(child, "ownerType");
/*     */     Class itemType;
/*     */     Class ownerType;
/*     */     try { itemType = !StringUtils.hasLength(itemTypeStr) ? Object.class : Class.forName(itemTypeStr);
/*     */ 
/*  74 */       ownerType = Class.forName(ownerTypeStr);
/*     */     } catch (ClassNotFoundException e) {
/*  76 */       throw new RuntimeException("XSrteam配置错误,指定的类不存在!" + e.getMessage());
/*     */     }
/*  78 */     String fieldName = noNull(child, "fieldName");
/*  79 */     String itemName = noNull(child, "itemName");
/*  80 */     ctx.addImplicitCollection(ownerType, fieldName, itemName, itemType); }
/*     */ 
/*     */   protected void processRootNodeName(Element child, MapperContext ctx)
/*     */   {
/*  84 */     ctx.setRootNodeName(noNull(child, "value"));
/*     */   }
/*     */ 
/*     */   protected void processXpathToCollection(Element child, MapperContext ctx) {
/*  88 */     ctx.xpathToCollection(noNull(child, "xpath"), noNull(child, "nodeName"), nullable(child, "itemType"));
/*     */   }
/*     */ 
/*     */   protected void processXpathImplicitCollection(Element child, MapperContext ctx)
/*     */   {
/*  94 */     ctx.addXpathImplicitCollection(noNull(child, "xpath"), noNull(child, "nodeName"), noNull(child, "fieldName"), nullable(child, "itemType"));
/*     */   }
/*     */ 
/*     */   private String nullable(Element child, String key)
/*     */   {
/* 101 */     Attribute attr = child.attribute(key);
/*     */     String value;
/*     */     String value;
/* 103 */     if (attr != null)
/* 104 */       value = attr.getText();
/*     */     else
/* 106 */       value = child.getText();
/* 107 */     return value;
/*     */   }
/*     */ 
/*     */   private String noNull(Element child, String key) {
/* 111 */     String value = nullable(child, key);
/* 112 */     if (!StringUtils.hasLength(value)) {
/* 113 */       throw new RuntimeException("XSteam配置错误,元素{" + child.getName() + "}的属性{" + key + "}值不能为空!");
/*     */     }
/* 115 */     return value;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.mapper.MapperContextBuilder
 * JD-Core Version:    0.6.2
 */