/*    */ package com.ztesoft.inf.extend.xstream.converters.basic;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.converters.Converter;
/*    */ import com.ztesoft.inf.extend.xstream.converters.MarshallingContext;
/*    */ import com.ztesoft.inf.extend.xstream.converters.UnmarshallingContext;
/*    */ import com.ztesoft.inf.extend.xstream.io.ExtendedHierarchicalStreamWriterHelper;
/*    */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
/*    */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;
/*    */ import com.ztesoft.inf.extend.xstream.mapper.Mapper.Null;
/*    */ 
/*    */ public class NullConverter
/*    */   implements Converter
/*    */ {
/*    */   public boolean canConvert(Class type)
/*    */   {
/* 30 */     return (type == null) || (Mapper.Null.class.isAssignableFrom(type));
/*    */   }
/*    */ 
/*    */   public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context)
/*    */   {
/* 35 */     ExtendedHierarchicalStreamWriterHelper.startNode(writer, "null", null);
/* 36 */     writer.endNode();
/*    */   }
/*    */ 
/*    */   public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
/*    */   {
/* 41 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.basic.NullConverter
 * JD-Core Version:    0.6.2
 */