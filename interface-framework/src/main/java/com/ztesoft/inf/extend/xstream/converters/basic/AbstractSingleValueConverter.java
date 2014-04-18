/*    */ package com.ztesoft.inf.extend.xstream.converters.basic;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.converters.SingleValueConverter;
/*    */ 
/*    */ public abstract class AbstractSingleValueConverter
/*    */   implements SingleValueConverter
/*    */ {
/*    */   public abstract boolean canConvert(Class paramClass);
/*    */ 
/*    */   public String toString(Object obj)
/*    */   {
/* 35 */     return obj == null ? null : obj.toString();
/*    */   }
/*    */ 
/*    */   public abstract Object fromString(String paramString);
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.basic.AbstractSingleValueConverter
 * JD-Core Version:    0.6.2
 */