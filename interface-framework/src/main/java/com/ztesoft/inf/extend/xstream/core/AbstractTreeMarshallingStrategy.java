/*    */ package com.ztesoft.inf.extend.xstream.core;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.MarshallingStrategy;
/*    */ import com.ztesoft.inf.extend.xstream.converters.ConverterLookup;
/*    */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
/*    */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;
/*    */ import com.ztesoft.inf.extend.xstream.mapper.MapperContext;
/*    */ 
/*    */ public abstract class AbstractTreeMarshallingStrategy
/*    */   implements MarshallingStrategy
/*    */ {
/*    */   public Object unmarshal(Object root, HierarchicalStreamReader reader, ConverterLookup converterLookup, MapperContext mapperCtx)
/*    */   {
/* 31 */     TreeUnmarshaller context = createUnmarshallingContext(root, reader, converterLookup, mapperCtx);
/*    */ 
/* 33 */     return context.start();
/*    */   }
/*    */ 
/*    */   public void marshal(HierarchicalStreamWriter writer, Object obj, ConverterLookup converterLookup, MapperContext mapperCtx)
/*    */   {
/* 38 */     TreeMarshaller context = createMarshallingContext(writer, converterLookup, mapperCtx);
/*    */ 
/* 40 */     context.start(obj);
/*    */   }
/*    */ 
/*    */   protected abstract TreeUnmarshaller createUnmarshallingContext(Object paramObject, HierarchicalStreamReader paramHierarchicalStreamReader, ConverterLookup paramConverterLookup, MapperContext paramMapperContext);
/*    */ 
/*    */   protected abstract TreeMarshaller createMarshallingContext(HierarchicalStreamWriter paramHierarchicalStreamWriter, ConverterLookup paramConverterLookup, MapperContext paramMapperContext);
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.core.AbstractTreeMarshallingStrategy
 * JD-Core Version:    0.6.2
 */