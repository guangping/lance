/*    */ package com.ztesoft.inf.extend.xstream.converters.basic;
/*    */ 
/*    */ public class StringBufferConverter extends AbstractSingleValueConverter
/*    */ {
/*    */   public Object fromString(String str)
/*    */   {
/* 23 */     return new StringBuffer(str);
/*    */   }
/*    */ 
/*    */   public boolean canConvert(Class type)
/*    */   {
/* 28 */     return type.equals(StringBuffer.class);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.basic.StringBufferConverter
 * JD-Core Version:    0.6.2
 */