/*    */ package com.ztesoft.inf.extend.xstream.converters.extended;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.converters.basic.AbstractSingleValueConverter;
/*    */ import java.sql.Date;
/*    */ 
/*    */ public class SqlDateConverter extends AbstractSingleValueConverter
/*    */ {
/*    */   public boolean canConvert(Class type)
/*    */   {
/* 27 */     return type.equals(Date.class);
/*    */   }
/*    */ 
/*    */   public Object fromString(String str)
/*    */   {
/* 32 */     return Date.valueOf(str);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.extended.SqlDateConverter
 * JD-Core Version:    0.6.2
 */