/*    */ package com.ztesoft.inf.extend.xstream.converters.basic;
/*    */ 
/*    */ public class LongConverter extends AbstractSingleValueConverter
/*    */ {
/*    */   public boolean canConvert(Class type)
/*    */   {
/* 23 */     return (type.equals(Long.TYPE)) || (type.equals(Long.class));
/*    */   }
/*    */ 
/*    */   public Object fromString(String str)
/*    */   {
/* 28 */     return Long.decode(str);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.basic.LongConverter
 * JD-Core Version:    0.6.2
 */