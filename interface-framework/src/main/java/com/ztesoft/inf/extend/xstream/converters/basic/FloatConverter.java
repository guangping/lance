/*    */ package com.ztesoft.inf.extend.xstream.converters.basic;
/*    */ 
/*    */ public class FloatConverter extends AbstractSingleValueConverter
/*    */ {
/*    */   public boolean canConvert(Class type)
/*    */   {
/* 23 */     return (type.equals(Float.TYPE)) || (type.equals(Float.class));
/*    */   }
/*    */ 
/*    */   public Object fromString(String str)
/*    */   {
/* 28 */     return Float.valueOf(str);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.basic.FloatConverter
 * JD-Core Version:    0.6.2
 */