/*    */ package com.ztesoft.inf.extend.xstream.converters.basic;
/*    */ 
/*    */ public class IntConverter extends AbstractSingleValueConverter
/*    */ {
/*    */   public boolean canConvert(Class type)
/*    */   {
/* 23 */     return (type.equals(Integer.TYPE)) || (type.equals(Integer.class));
/*    */   }
/*    */ 
/*    */   public Object fromString(String str)
/*    */   {
/* 28 */     long value = Long.decode(str).longValue();
/* 29 */     if ((value < -2147483648L) || (value > 4294967295L)) {
/* 30 */       throw new NumberFormatException("For input string: \"" + str + '"');
/*    */     }
/* 32 */     return new Integer((int)value);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.basic.IntConverter
 * JD-Core Version:    0.6.2
 */