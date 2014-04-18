/*    */ package com.ztesoft.inf.extend.xstream.converters.basic;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.converters.Converter;
/*    */ import com.ztesoft.inf.extend.xstream.converters.MarshallingContext;
/*    */ import com.ztesoft.inf.extend.xstream.converters.SingleValueConverter;
/*    */ import com.ztesoft.inf.extend.xstream.converters.UnmarshallingContext;
/*    */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
/*    */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;
/*    */ 
/*    */ public class CharConverter
/*    */   implements Converter, SingleValueConverter
/*    */ {
/*    */   public boolean canConvert(Class type)
/*    */   {
/* 31 */     return (type.equals(Character.TYPE)) || (type.equals(Character.class));
/*    */   }
/*    */ 
/*    */   public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context)
/*    */   {
/* 36 */     writer.setValue(toString(source));
/*    */   }
/*    */ 
/*    */   public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
/*    */   {
/* 41 */     String nullAttribute = reader.getAttribute("null");
/* 42 */     if ((nullAttribute != null) && (nullAttribute.equals("true"))) {
/* 43 */       return new Character('\000');
/*    */     }
/* 45 */     return fromString(reader.getValue());
/*    */   }
/*    */ 
/*    */   public Object fromString(String str)
/*    */   {
/* 50 */     if (str.length() == 0) {
/* 51 */       return new Character('\000');
/*    */     }
/* 53 */     return new Character(str.charAt(0));
/*    */   }
/*    */ 
/*    */   public String toString(Object obj)
/*    */   {
/* 58 */     char ch = ((Character)obj).charValue();
/* 59 */     return ch == 0 ? "" : obj.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.basic.CharConverter
 * JD-Core Version:    0.6.2
 */