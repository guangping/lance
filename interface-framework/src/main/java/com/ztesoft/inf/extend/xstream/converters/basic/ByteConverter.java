/*    */ package com.ztesoft.inf.extend.xstream.converters.basic;
/*    */ 
/*    */ public class ByteConverter extends AbstractSingleValueConverter
/*    */ {
/*    */   public boolean canConvert(Class type)
/*    */   {
/* 22 */     return (type.equals(Byte.TYPE)) || (type.equals(Byte.class));
/*    */   }
/*    */ 
/*    */   public Object fromString(String str) {
/* 26 */     int value = Integer.decode(str).intValue();
/* 27 */     if ((value < -128) || (value > 255)) {
/* 28 */       throw new NumberFormatException("For input string: \"" + str + '"');
/*    */     }
/* 30 */     return new Byte((byte)value);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.basic.ByteConverter
 * JD-Core Version:    0.6.2
 */