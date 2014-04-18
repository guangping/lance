/*     */ package com.ztesoft.inf.extend.xstream.converters.basic;
/*     */ 
/*     */ import com.ztesoft.inf.extend.xstream.converters.ConversionException;
/*     */ import com.ztesoft.inf.extend.xstream.core.util.ThreadSafeSimpleDateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class DateConverter extends AbstractSingleValueConverter
/*     */ {
/*     */   private final ThreadSafeSimpleDateFormat defaultFormat;
/*     */   private final ThreadSafeSimpleDateFormat[] acceptableFormats;
/*     */ 
/*     */   public DateConverter()
/*     */   {
/*  37 */     this(false);
/*     */   }
/*     */ 
/*     */   public DateConverter(boolean lenient)
/*     */   {
/*  49 */     this("yyyy-MM-dd HH:mm:ss.S z", new String[] { "yyyy-MM-dd HH:mm:ss.S a", "yyyy-MM-dd HH:mm:ssz", "yyyy-MM-dd HH:mm:ss z", "yyyy-MM-dd HH:mm:ssa" }, lenient);
/*     */   }
/*     */ 
/*     */   public DateConverter(String defaultFormat, String[] acceptableFormats)
/*     */   {
/*  65 */     this(defaultFormat, acceptableFormats, false);
/*     */   }
/*     */ 
/*     */   public DateConverter(String defaultFormat, String[] acceptableFormats, boolean lenient)
/*     */   {
/*  82 */     this.defaultFormat = new ThreadSafeSimpleDateFormat(defaultFormat, 4, 20, lenient);
/*     */ 
/*  84 */     this.acceptableFormats = new ThreadSafeSimpleDateFormat[acceptableFormats.length];
/*  85 */     for (int i = 0; i < acceptableFormats.length; i++)
/*  86 */       this.acceptableFormats[i] = new ThreadSafeSimpleDateFormat(acceptableFormats[i], 1, 20, lenient);
/*     */   }
/*     */ 
/*     */   public boolean canConvert(Class type)
/*     */   {
/*  93 */     return type.equals(Date.class);
/*     */   }
/*     */ 
/*     */   public Object fromString(String str)
/*     */   {
/*     */     try {
/*  99 */       return this.defaultFormat.parse(str);
/*     */     } catch (ParseException e) {
/* 101 */       for (int i = 0; i < this.acceptableFormats.length; i++)
/*     */         try {
/* 103 */           return this.acceptableFormats[i].parse(str);
/*     */         }
/*     */         catch (ParseException e2)
/*     */         {
/*     */         }
/*     */     }
/* 109 */     throw new ConversionException("Cannot parse date " + str);
/*     */   }
/*     */ 
/*     */   public String toString(Object obj)
/*     */   {
/* 115 */     return this.defaultFormat.format((Date)obj);
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.basic.DateConverter
 * JD-Core Version:    0.6.2
 */