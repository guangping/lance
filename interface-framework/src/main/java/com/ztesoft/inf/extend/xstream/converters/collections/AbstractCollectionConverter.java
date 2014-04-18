/*    */ package com.ztesoft.inf.extend.xstream.converters.collections;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.converters.ConversionException;
/*    */ import com.ztesoft.inf.extend.xstream.converters.Converter;
/*    */ import com.ztesoft.inf.extend.xstream.converters.MarshallingContext;
/*    */ import com.ztesoft.inf.extend.xstream.converters.UnmarshallingContext;
/*    */ import com.ztesoft.inf.extend.xstream.core.util.HierarchicalStreams;
/*    */ import com.ztesoft.inf.extend.xstream.io.ExtendedHierarchicalStreamWriterHelper;
/*    */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
/*    */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;
/*    */ import com.ztesoft.inf.extend.xstream.io.path.Path;
/*    */ import com.ztesoft.inf.extend.xstream.io.path.PathTracker;
/*    */ import com.ztesoft.inf.extend.xstream.mapper.Mapper;
/*    */ import java.util.Map;
/*    */ 
/*    */ public abstract class AbstractCollectionConverter
/*    */   implements Converter
/*    */ {
/*    */   public abstract boolean canConvert(Class paramClass);
/*    */ 
/*    */   public abstract void marshal(Object paramObject, HierarchicalStreamWriter paramHierarchicalStreamWriter, MarshallingContext paramMarshallingContext);
/*    */ 
/*    */   public abstract Object unmarshal(HierarchicalStreamReader paramHierarchicalStreamReader, UnmarshallingContext paramUnmarshallingContext);
/*    */ 
/*    */   protected void writeItem(Object item, MarshallingContext context, HierarchicalStreamWriter writer)
/*    */   {
/* 55 */     Mapper mapper = context.getMapper();
/* 56 */     if (item == null)
/*    */     {
/* 58 */       String name = mapper.serializedClass(null);
/* 59 */       writer.startNode(name);
/* 60 */       writer.endNode();
/*    */     } else {
/* 62 */       String path = context.getPathTracker().getPath().toString();
/* 63 */       String name = mapper.getColFieldNameByPath(path);
/* 64 */       if (name == null) {
/* 65 */         if ((item instanceof Map))
/* 66 */           name = mapper.genMapNodeNameByPath(path);
/*    */         else
/* 68 */           name = mapper.serializedClass(item.getClass());
/*    */       }
/* 70 */       ExtendedHierarchicalStreamWriterHelper.startNode(writer, name, item.getClass());
/*    */ 
/* 72 */       context.convertAnother(item);
/* 73 */       writer.endNode();
/*    */     }
/*    */   }
/*    */ 
/*    */   protected Object readItem(HierarchicalStreamReader reader, UnmarshallingContext context, Object current)
/*    */   {
/* 79 */     Mapper mapper = context.getMapper();
/* 80 */     Class type = HierarchicalStreams.readClassType(reader, mapper, context, Map.class);
/*    */ 
/* 82 */     return context.convertAnother(current, type);
/*    */   }
/*    */ 
/*    */   protected Object createCollection(Class type, UnmarshallingContext context) {
/* 86 */     Mapper mapper = context.getMapper();
/* 87 */     Class defaultType = mapper.defaultImplementationOf(type);
/*    */     try {
/* 89 */       return defaultType.newInstance();
/*    */     } catch (InstantiationException e) {
/* 91 */       throw new ConversionException("Cannot instantiate " + defaultType.getName(), e);
/*    */     }
/*    */     catch (IllegalAccessException e) {
/* 94 */       throw new ConversionException("Cannot instantiate " + defaultType.getName(), e);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.collections.AbstractCollectionConverter
 * JD-Core Version:    0.6.2
 */