/*    */ package com.ztesoft.inf.extend.xstream.converters.basic;
/*    */ 
/*    */ public class ShortConverter extends AbstractSingleValueConverter
/*    */ {
/*    */   public boolean canConvert(Class type)
/*    */   {
/* 23 */     return (type.equals(Short.TYPE)) || (type.equals(Short.class));
/*    */   }
/*    */ 
/*    */   public Object fromString(String str)
/*    */   {
/* 28 */     int value = Integer.decode(str).intValue();
/* 29 */     if ((value < -32768) || (value > 65535)) {
/* 30 */       throw new NumberFormatException("For input string: \"" + str + '"');
/*    */     }
/* 32 */     return new Short((short)value);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.basic.ShortConverter
 * JD-Core Version:    0.6.2
 */