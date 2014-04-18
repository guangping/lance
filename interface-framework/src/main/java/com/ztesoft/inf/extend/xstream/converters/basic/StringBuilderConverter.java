/*    */ package com.ztesoft.inf.extend.xstream.converters.basic;
/*    */ 
/*    */ public class StringBuilderConverter extends AbstractSingleValueConverter
/*    */ {
/*    */   public Object fromString(String str)
/*    */   {
/* 22 */     return new StringBuilder(str);
/*    */   }
/*    */ 
/*    */   public boolean canConvert(Class type)
/*    */   {
/* 27 */     return type.equals(StringBuilder.class);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.basic.StringBuilderConverter
 * JD-Core Version:    0.6.2
 */