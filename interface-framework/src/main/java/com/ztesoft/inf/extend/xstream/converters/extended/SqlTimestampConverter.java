/*    */ package com.ztesoft.inf.extend.xstream.converters.extended;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.converters.basic.AbstractSingleValueConverter;
/*    */ import java.sql.Timestamp;
/*    */ 
/*    */ public class SqlTimestampConverter extends AbstractSingleValueConverter
/*    */ {
/*    */   public boolean canConvert(Class type)
/*    */   {
/* 26 */     return type.equals(Timestamp.class);
/*    */   }
/*    */ 
/*    */   public Object fromString(String str) {
/* 30 */     return Timestamp.valueOf(str);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.extended.SqlTimestampConverter
 * JD-Core Version:    0.6.2
 */