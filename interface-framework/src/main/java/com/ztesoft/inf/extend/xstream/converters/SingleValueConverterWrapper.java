/*    */ package com.ztesoft.inf.extend.xstream.converters;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
/*    */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;
/*    */ 
/*    */ public class SingleValueConverterWrapper
/*    */   implements Converter, SingleValueConverter
/*    */ {
/*    */   private final SingleValueConverter wrapped;
/*    */ 
/*    */   public SingleValueConverterWrapper(SingleValueConverter wrapped)
/*    */   {
/* 31 */     this.wrapped = wrapped;
/*    */   }
/*    */ 
/*    */   public boolean canConvert(Class type) {
/* 35 */     return this.wrapped.canConvert(type);
/*    */   }
/*    */ 
/*    */   public String toString(Object obj) {
/* 39 */     return this.wrapped.toString(obj);
/*    */   }
/*    */ 
/*    */   public Object fromString(String str) {
/* 43 */     return this.wrapped.fromString(str);
/*    */   }
/*    */ 
/*    */   public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context)
/*    */   {
/* 48 */     writer.setValue(toString(source));
/*    */   }
/*    */ 
/*    */   public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
/*    */   {
/* 53 */     return fromString(reader.getValue());
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.SingleValueConverterWrapper
 * JD-Core Version:    0.6.2
 */