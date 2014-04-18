/*    */ package com.ztesoft.inf.extend.xstream.core.util;
/*    */ 
/*    */ import java.text.DateFormat;
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.Locale;
/*    */ import java.util.TimeZone;
/*    */ 
/*    */ public class ThreadSafeSimpleDateFormat
/*    */ {
/*    */   private final String formatString;
/*    */   private final Pool pool;
/*    */ 
/*    */   public ThreadSafeSimpleDateFormat(String format, int initialPoolSize, int maxPoolSize, final boolean lenient)
/*    */   {
/* 46 */     this.formatString = format;
/* 47 */     this.pool = new Pool(initialPoolSize, maxPoolSize, new Pool.Factory() {
/*    */       public Object newInstance() {
/* 49 */         SimpleDateFormat dateFormat = new SimpleDateFormat(ThreadSafeSimpleDateFormat.this.formatString, Locale.ENGLISH);
/*    */ 
/* 51 */         dateFormat.setLenient(lenient);
/* 52 */         return dateFormat;
/*    */       }
/*    */     });
/*    */   }
/*    */ 
/*    */   public String format(Date date)
/*    */   {
/* 59 */     DateFormat format = fetchFromPool();
/*    */     try {
/* 61 */       return format.format(date);
/*    */     } finally {
/* 63 */       this.pool.putInPool(format);
/*    */     }
/*    */   }
/*    */ 
/*    */   public Date parse(String date) throws ParseException {
/* 68 */     DateFormat format = fetchFromPool();
/*    */     try {
/* 70 */       return format.parse(date);
/*    */     } finally {
/* 72 */       this.pool.putInPool(format);
/*    */     }
/*    */   }
/*    */ 
/*    */   private DateFormat fetchFromPool() {
/* 77 */     TimeZone tz = TimeZone.getDefault();
/* 78 */     DateFormat format = (DateFormat)this.pool.fetchFromPool();
/* 79 */     if (!tz.equals(format.getTimeZone())) {
/* 80 */       format.setTimeZone(tz);
/*    */     }
/* 82 */     return format;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.core.util.ThreadSafeSimpleDateFormat
 * JD-Core Version:    0.6.2
 */