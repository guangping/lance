/*    */ package com.ztesoft.inf.extend.xstream.converters.extended;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.converters.basic.AbstractSingleValueConverter;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class LocaleConverter extends AbstractSingleValueConverter
/*    */ {
/*    */   public boolean canConvert(Class type)
/*    */   {
/* 28 */     return type.equals(Locale.class);
/*    */   }
/*    */ 
/*    */   public Object fromString(String str)
/*    */   {
/* 33 */     int[] underscorePositions = underscorePositions(str);
/*    */     String variant;
/*    */     String language;
/*    */     String country;
/*    */     String variant;
/* 35 */     if (underscorePositions[0] == -1) {
/* 36 */       String language = str;
/* 37 */       String country = "";
/* 38 */       variant = "";
/*    */     }
/*    */     else
/*    */     {
/*    */       String variant;
/* 39 */       if (underscorePositions[1] == -1) {
/* 40 */         String language = str.substring(0, underscorePositions[0]);
/* 41 */         String country = str.substring(underscorePositions[0] + 1);
/* 42 */         variant = "";
/*    */       } else {
/* 44 */         language = str.substring(0, underscorePositions[0]);
/* 45 */         country = str.substring(underscorePositions[0] + 1, underscorePositions[1]);
/*    */ 
/* 47 */         variant = str.substring(underscorePositions[1] + 1);
/*    */       }
/*    */     }
/* 49 */     return new Locale(language, country, variant);
/*    */   }
/*    */ 
/*    */   private int[] underscorePositions(String in) {
/* 53 */     int[] result = new int[2];
/* 54 */     for (int i = 0; i < result.length; i++) {
/* 55 */       int last = i == 0 ? 0 : result[(i - 1)];
/* 56 */       result[i] = in.indexOf(95, last + 1);
/*    */     }
/* 58 */     return result;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.extended.LocaleConverter
 * JD-Core Version:    0.6.2
 */