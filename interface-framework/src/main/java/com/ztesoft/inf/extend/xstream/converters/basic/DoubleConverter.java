/*    */ package com.ztesoft.inf.extend.xstream.converters.basic;
/*    */ 
/*    */ public class DoubleConverter extends AbstractSingleValueConverter
/*    */ {
/*    */   public boolean canConvert(Class type)
/*    */   {
/* 23 */     return (type.equals(Double.TYPE)) || (type.equals(Double.class));
/*    */   }
/*    */ 
/*    */   public Object fromString(String str)
/*    */   {
/* 28 */     return Double.valueOf(str);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.basic.DoubleConverter
 * JD-Core Version:    0.6.2
 */