/*    */ package com.ztesoft.inf.extend.xstream.converters.basic;
/*    */ 
/*    */ import java.math.BigInteger;
/*    */ 
/*    */ public class BigIntegerConverter extends AbstractSingleValueConverter
/*    */ {
/*    */   public boolean canConvert(Class type)
/*    */   {
/* 25 */     return type.equals(BigInteger.class);
/*    */   }
/*    */ 
/*    */   public Object fromString(String str)
/*    */   {
/* 30 */     return new BigInteger(str);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.basic.BigIntegerConverter
 * JD-Core Version:    0.6.2
 */