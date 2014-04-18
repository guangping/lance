/*    */ package com.ztesoft.inf.extend.xstream.core.util;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.converters.UnmarshallingContext;
/*    */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
/*    */ import com.ztesoft.inf.extend.xstream.io.path.Path;
/*    */ import com.ztesoft.inf.extend.xstream.io.path.PathTracker;
/*    */ import com.ztesoft.inf.extend.xstream.mapper.CannotResolveClassException;
/*    */ import com.ztesoft.inf.extend.xstream.mapper.Mapper;
/*    */ 
/*    */ public class HierarchicalStreams
/*    */ {
/*    */   public static Class readClassType(HierarchicalStreamReader reader, Mapper mapper, UnmarshallingContext context)
/*    */   {
/* 31 */     return readClassType(reader, mapper, context, null);
/*    */   }
/*    */ 
/*    */   public static Class readClassType(HierarchicalStreamReader reader, Mapper mapper, UnmarshallingContext context, Class defaultType)
/*    */   {
/* 36 */     String classAttribute = readClassAttribute(reader, mapper);
/* 37 */     Class type = readClassByXPath(mapper, context);
/* 38 */     if (type == null) {
/* 39 */       type = readClassType(classAttribute == null ? reader.getNodeName() : classAttribute, reader, mapper, defaultType);
/*    */     }
/*    */ 
/* 42 */     return type;
/*    */   }
/*    */ 
/*    */   public static Class readClassByXPath(Mapper mapper, UnmarshallingContext context)
/*    */   {
/* 47 */     PathTracker pathTracker = context.getPathTracker();
/* 48 */     if (pathTracker != null) {
/* 49 */       return mapper.realClassByPath(pathTracker.getPath().toString());
/*    */     }
/* 51 */     return null;
/*    */   }
/*    */ 
/*    */   public static Class readClassType(String elementName, HierarchicalStreamReader reader, Mapper mapper)
/*    */   {
/* 56 */     return mapper.realClass(elementName);
/*    */   }
/*    */ 
/*    */   public static Class readClassType(String elementName, HierarchicalStreamReader reader, Mapper mapper, Class defaultType)
/*    */   {
/*    */     try {
/* 62 */       return mapper.realClass(elementName);
/*    */     } catch (CannotResolveClassException e) {
/* 64 */       if (defaultType != null)
/* 65 */         return mapper.defaultImplementationOf(defaultType);
/* 66 */       throw e;
/*    */     }
/*    */   }
/*    */ 
/*    */   public static String readClassAttribute(HierarchicalStreamReader reader, Mapper mapper)
/*    */   {
/* 73 */     String attributeName = mapper.aliasForSystemAttribute("resolves-to");
/* 74 */     String classAttribute = attributeName == null ? null : reader.getAttribute(attributeName);
/*    */ 
/* 76 */     if (classAttribute == null) {
/* 77 */       attributeName = mapper.aliasForSystemAttribute("class");
/* 78 */       if (attributeName != null) {
/* 79 */         classAttribute = reader.getAttribute(attributeName);
/*    */       }
/*    */     }
/* 82 */     return classAttribute;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.core.util.HierarchicalStreams
 * JD-Core Version:    0.6.2
 */