/*    */ package com.ztesoft.inf.extend.xstream.converters.extended;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.converters.basic.AbstractSingleValueConverter;
/*    */ import java.sql.Time;
/*    */ 
/*    */ public class SqlTimeConverter extends AbstractSingleValueConverter
/*    */ {
/*    */   public boolean canConvert(Class type)
/*    */   {
/* 28 */     return type.equals(Time.class);
/*    */   }
/*    */ 
/*    */   public Object fromString(String str)
/*    */   {
/* 33 */     return Time.valueOf(str);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.extended.SqlTimeConverter
 * JD-Core Version:    0.6.2
 */