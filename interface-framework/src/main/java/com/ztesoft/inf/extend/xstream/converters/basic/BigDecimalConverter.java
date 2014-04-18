/*    */ package com.ztesoft.inf.extend.xstream.converters.basic;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ 
/*    */ public class BigDecimalConverter extends AbstractSingleValueConverter
/*    */ {
/*    */   public boolean canConvert(Class type)
/*    */   {
/* 24 */     return type.equals(BigDecimal.class);
/*    */   }
/*    */ 
/*    */   public Object fromString(String str) {
/* 28 */     return new BigDecimal(str);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.basic.BigDecimalConverter
 * JD-Core Version:    0.6.2
 */