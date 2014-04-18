/*    */ package com.ztesoft.inf.extend.xstream.converters.basic;
/*    */ 
/*    */ public class BooleanConverter extends AbstractSingleValueConverter
/*    */ {
/* 22 */   public static final BooleanConverter TRUE_FALSE = new BooleanConverter("true", "false", false);
/*    */ 
/* 25 */   public static final BooleanConverter YES_NO = new BooleanConverter("yes", "no", false);
/*    */ 
/* 28 */   public static final BooleanConverter BINARY = new BooleanConverter("1", "0", true);
/*    */   private final String positive;
/*    */   private final String negative;
/*    */   private final boolean caseSensitive;
/*    */ 
/*    */   public BooleanConverter(String positive, String negative, boolean caseSensitive)
/*    */   {
/* 37 */     this.positive = positive;
/* 38 */     this.negative = negative;
/* 39 */     this.caseSensitive = caseSensitive;
/*    */   }
/*    */ 
/*    */   public BooleanConverter() {
/* 43 */     this("true", "false", false);
/*    */   }
/*    */ 
/*    */   public boolean shouldConvert(Class type, Object value) {
/* 47 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean canConvert(Class type)
/*    */   {
/* 52 */     return (type.equals(Boolean.TYPE)) || (type.equals(Boolean.class));
/*    */   }
/*    */ 
/*    */   public Object fromString(String str)
/*    */   {
/* 57 */     if (this.caseSensitive) {
/* 58 */       return this.positive.equals(str) ? Boolean.TRUE : Boolean.FALSE;
/*    */     }
/* 60 */     return this.positive.equalsIgnoreCase(str) ? Boolean.TRUE : Boolean.FALSE;
/*    */   }
/*    */ 
/*    */   public String toString(Object obj)
/*    */   {
/* 67 */     Boolean value = (Boolean)obj;
/* 68 */     return value.booleanValue() ? this.positive : obj == null ? null : this.negative;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.basic.BooleanConverter
 * JD-Core Version:    0.6.2
 */