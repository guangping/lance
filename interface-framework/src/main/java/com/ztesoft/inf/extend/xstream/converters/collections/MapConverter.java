/*     */ package com.ztesoft.inf.extend.xstream.converters.collections;
/*     */ 
/*     */ import com.ztesoft.inf.extend.xstream.converters.MarshallingContext;
/*     */ import com.ztesoft.inf.extend.xstream.converters.UnmarshallingContext;
/*     */ import com.ztesoft.inf.extend.xstream.converters.reflection.PureJavaReflectionProvider;
/*     */ import com.ztesoft.inf.extend.xstream.converters.reflection.ReflectionProvider;
/*     */ import com.ztesoft.inf.extend.xstream.core.util.HierarchicalStreams;
/*     */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
/*     */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;
/*     */ import com.ztesoft.inf.extend.xstream.io.path.Path;
/*     */ import com.ztesoft.inf.extend.xstream.io.path.PathTracker;
/*     */ import com.ztesoft.inf.extend.xstream.mapper.CannotResolveClassException;
/*     */ import com.ztesoft.inf.extend.xstream.mapper.Mapper;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class MapConverter extends AbstractCollectionConverter
/*     */ {
/*     */   private ReflectionProvider pureJavaReflectionProvider;
/*     */ 
/*     */   public boolean canConvert(Class type)
/*     */   {
/*  25 */     return Map.class.isAssignableFrom(type);
/*     */   }
/*     */ 
/*     */   public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context)
/*     */   {
/*  31 */     Mapper mapper = context.getMapper();
/*  32 */     Set set = ((Map)source).entrySet();
/*  33 */     for (Map.Entry entry : set) {
/*  34 */       Object value = entry.getValue();
/*  35 */       String fieldName = entry.getKey().toString();
/*  36 */       PathTracker tracker = context.getPathTracker();
/*  37 */       tracker.pushElement(fieldName);
/*  38 */       String nodeName = mapper.getImplicitCollectionItemNameByPath(tracker.getPath().toString());
/*     */ 
/*  41 */       tracker.popElement();
/*  42 */       boolean isImplicitCollection = false;
/*  43 */       if (nodeName != null) {
/*  44 */         if (!(value instanceof Collection)) {
/*  45 */           throw new RuntimeException();
/*     */         }
/*  47 */         isImplicitCollection = true;
/*     */       } else {
/*  49 */         if ((mapper.globalImplicitCollection()) && ((value instanceof Collection)))
/*     */         {
/*  51 */           isImplicitCollection = true;
/*     */         }
/*  53 */         nodeName = fieldName;
/*     */       }
/*     */       Iterator iter;
/*  55 */       if (isImplicitCollection) {
/*  56 */         Collection list = (Collection)value;
/*  57 */         for (iter = list.iterator(); iter.hasNext(); ) {
/*  58 */           Object obj = iter.next();
/*  59 */           writer.startNode(nodeName);
/*  60 */           context.convertAnother(obj);
/*  61 */           writer.endNode();
/*     */         }
/*     */       } else {
/*  64 */         writer.startNode(fieldName);
/*  65 */         if (value != null)
/*  66 */           context.convertAnother(value);
/*  67 */         writer.endNode();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
/*     */   {
/*  75 */     Map map = (Map)createCollection(context.getRequiredType(), context);
/*  76 */     populateMap(reader, context, map);
/*  77 */     return map;
/*     */   }
/*     */ 
/*     */   protected void populateMap(HierarchicalStreamReader reader, UnmarshallingContext context, Map result)
/*     */   {
/*  82 */     Mapper mapper = context.getMapper();
/*  83 */     Map implicitCollectionsForCurrentObject = null;
/*  84 */     PathTracker pathTracker = context.getPathTracker();
/*  85 */     while (reader.hasMoreChildren()) {
/*  86 */       reader.moveDown();
/*  87 */       String fieldName = mapper.getColFieldNameByPath(pathTracker.getPath().toString());
/*     */ 
/*  89 */       boolean isImplicitCollection = false;
/*  90 */       if (fieldName == null) {
/*  91 */         fieldName = mapper.getAliasNameByPath(context.getPathTracker().getPath().toString());
/*     */ 
/*  93 */         if (fieldName == null)
/*  94 */           fieldName = reader.getNodeName();
/*     */       }
/*     */       else {
/*  97 */         isImplicitCollection = true;
/*     */       }
/*     */       Class type;
/*     */       try {
/* 101 */         type = HierarchicalStreams.readClassType(reader, mapper, context);
/*     */       }
/*     */       catch (CannotResolveClassException e)
/*     */       {
/*     */         Class type;
/* 104 */         if ((!reader.hasMoreChildren()) && (reader.getAttributeCount() < 1))
/* 105 */           type = String.class;
/*     */         else
/* 107 */           type = mapper.defaultImplementationOf(Map.class);
/*     */       }
/* 109 */       Object value = context.convertAnother(result, type);
/* 110 */       if ((value instanceof Map)) {
/* 111 */         Iterator iter = reader.getAttributeNames();
/* 112 */         Map valueMap = (Map)value;
/*     */ 
/* 114 */         while (iter.hasNext()) {
/* 115 */           String attrName = (String)iter.next();
/* 116 */           valueMap.put(attrName, reader.getAttribute(attrName));
/*     */         }
/*     */       }
/* 119 */       if (isImplicitCollection) {
/* 120 */         if (implicitCollectionsForCurrentObject == null) {
/*     */           try {
/* 122 */             implicitCollectionsForCurrentObject = (Map)mapper.defaultImplementationOf(Map.class).newInstance();
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 126 */             throw new RuntimeException(e);
/*     */           }
/*     */         }
/* 129 */         implicitCollectionsForCurrentObject = writeValueToImplicitCollection(context, result, implicitCollectionsForCurrentObject, value, fieldName, mapper.defaultImplementationOf(Collection.class));
/*     */       }
/*     */       else
/*     */       {
/* 134 */         result.put(fieldName, value);
/* 135 */       }reader.moveUp();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Map writeValueToImplicitCollection(UnmarshallingContext context, Map result, Map implicitCollections, Object value, String fieldName, Class fieldType)
/*     */   {
/* 142 */     Collection collection = (Collection)implicitCollections.get(fieldName);
/* 143 */     if (collection == null) {
/* 144 */       if (this.pureJavaReflectionProvider == null) {
/* 145 */         this.pureJavaReflectionProvider = new PureJavaReflectionProvider();
/*     */       }
/* 147 */       collection = (Collection)this.pureJavaReflectionProvider.newInstance(fieldType);
/*     */ 
/* 149 */       result.put(fieldName, collection);
/* 150 */       implicitCollections.put(fieldName, collection);
/*     */     }
/* 152 */     collection.add(value);
/* 153 */     return implicitCollections;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.collections.MapConverter
 * JD-Core Version:    0.6.2
 */