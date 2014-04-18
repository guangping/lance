/*    */ package com.ztesoft.inf.extend.xstream.core;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.converters.ConverterLookup;
/*    */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
/*    */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;
/*    */ import com.ztesoft.inf.extend.xstream.mapper.MapperContext;
/*    */ 
/*    */ public class TreeMarshallingStrategy extends AbstractTreeMarshallingStrategy
/*    */ {
/*    */   protected TreeUnmarshaller createUnmarshallingContext(Object root, HierarchicalStreamReader reader, ConverterLookup converterLookup, MapperContext mapperCtx)
/*    */   {
/* 25 */     return new TreeUnmarshaller(root, reader, converterLookup, mapperCtx);
/*    */   }
/*    */ 
/*    */   protected TreeMarshaller createMarshallingContext(HierarchicalStreamWriter writer, ConverterLookup converterLookup, MapperContext mapperCtx)
/*    */   {
/* 32 */     return new TreeMarshaller(writer, converterLookup, mapperCtx);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.core.TreeMarshallingStrategy
 * JD-Core Version:    0.6.2
 */