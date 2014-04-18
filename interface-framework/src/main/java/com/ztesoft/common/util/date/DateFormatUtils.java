/*     */ package com.ztesoft.common.util.date;
/*     */ 
/*     */ import java.text.ParsePosition;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class DateFormatUtils
/*     */ {
/*     */   public static final String YEAR = "year";
/*     */   public static final String MONTH = "month";
/*     */   public static final String DAY = "day";
/*     */   public static final String HOUR = "hour";
/*     */   public static final String MINUTE = "minute";
/*     */   public static final String SECOND = "second";
/*     */   public static final String WEEK = "week";
/*     */ 
/*     */   public static String getFormatedDate()
/*     */   {
/*  32 */     Date date = getCurrentDate();
/*  33 */     SimpleDateFormat dateFormator = new SimpleDateFormat("yyyy-MM-dd");
/*  34 */     return dateFormator.format(date);
/*     */   }
/*     */ 
/*     */   public static String getFormatedDateTime()
/*     */   {
/*  45 */     Date date = getCurrentDate();
/*  46 */     SimpleDateFormat dateFormator = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*  47 */     return dateFormator.format(date);
/*     */   }
/*     */ 
/*     */   public static Date getCurrentDate()
/*     */   {
/*  57 */     return new Date(System.currentTimeMillis());
/*     */   }
/*     */ 
/*     */   public static long getCurrentTimeMillis()
/*     */   {
/*  66 */     return System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */   public static String formatDate(String pattern)
/*     */   {
/*  78 */     Date date = new Date();
/*  79 */     SimpleDateFormat dateFormator = new SimpleDateFormat(pattern);
/*  80 */     String str = dateFormator.format(date);
/*     */ 
/*  82 */     return str;
/*     */   }
/*     */ 
/*     */   public static String formatDate(Date date, String pattern)
/*     */   {
/*  95 */     if (date == null) {
/*  96 */       return "";
/*     */     }
/*     */ 
/*  99 */     SimpleDateFormat dateFormator = new SimpleDateFormat(pattern);
/* 100 */     String str = dateFormator.format(date);
/*     */ 
/* 102 */     return str;
/*     */   }
/*     */ 
/*     */   public static String formatDate(Date date, String pattern, Locale loc)
/*     */   {
/* 116 */     if ((pattern == null) || (date == null)) {
/* 117 */       return "";
/*     */     }
/* 119 */     String newDate = "";
/* 120 */     if (loc == null) {
/* 121 */       loc = Locale.getDefault();
/*     */     }
/* 123 */     if (date != null) {
/* 124 */       SimpleDateFormat sdf = new SimpleDateFormat(pattern, loc);
/* 125 */       newDate = sdf.format(date);
/*     */     }
/* 127 */     return newDate;
/*     */   }
/*     */ 
/*     */   public static String convertDateFormat(String dateStr, String patternFrom, String patternTo)
/*     */   {
/* 146 */     if ((dateStr == null) || (patternFrom == null) || (patternTo == null)) {
/* 147 */       return "";
/*     */     }
/*     */ 
/* 150 */     String newDate = "";
/*     */     try
/*     */     {
/* 154 */       Date dateFrom = parseStrToDate(dateStr, patternFrom);
/* 155 */       newDate = formatDate(dateFrom, patternTo);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/* 160 */     return newDate;
/*     */   }
/*     */ 
/*     */   public static Date parseStrToDate(String dateStr)
/*     */   {
/* 171 */     if ((null == dateStr) || ("".equals(dateStr))) {
/* 172 */       return null;
/*     */     }
/*     */ 
/* 175 */     SimpleDateFormat dateFormator = new SimpleDateFormat("yyyy-MM-dd");
/*     */ 
/* 177 */     Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));
/*     */ 
/* 179 */     return tDate;
/*     */   }
/*     */ 
/*     */   public static String parseDateStrToDateTimeStr(String dateStr)
/*     */   {
/* 184 */     if ((null == dateStr) || ("".equals(dateStr))) {
/* 185 */       return null;
/*     */     }
/*     */ 
/* 188 */     SimpleDateFormat dateFormator = new SimpleDateFormat("yyyy-MM-dd");
/*     */ 
/* 190 */     Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));
/* 191 */     return formatDate(tDate, "yyyy-MM-dd HH:mm:ss");
/*     */   }
/*     */ 
/*     */   public static Calendar parseStrToCalendar(String dateStr)
/*     */   {
/* 203 */     if ((null == dateStr) || ("".equals(dateStr))) {
/* 204 */       return null;
/*     */     }
/*     */ 
/* 207 */     SimpleDateFormat dateFormator = new SimpleDateFormat("yyyy-MM-dd");
/*     */ 
/* 209 */     Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));
/*     */ 
/* 211 */     Locale loc = Locale.getDefault();
/* 212 */     Calendar cal = new GregorianCalendar(loc);
/* 213 */     cal.setTime(tDate);
/*     */ 
/* 215 */     return cal;
/*     */   }
/*     */ 
/*     */   public static Date parseStrToDateTime(String dateStr)
/*     */   {
/* 226 */     if ((null == dateStr) || ("".equals(dateStr))) {
/* 227 */       return null;
/*     */     }
/*     */ 
/* 230 */     SimpleDateFormat dateFormator = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */ 
/* 232 */     Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));
/*     */ 
/* 234 */     return tDate;
/*     */   }
/*     */ 
/*     */   public static Date parseStrToDate(String dateStr, String pattern)
/*     */   {
/* 247 */     if ((null == dateStr) || ("".equals(dateStr))) {
/* 248 */       return null;
/*     */     }
/*     */ 
/* 251 */     SimpleDateFormat dateFormator = new SimpleDateFormat(pattern);
/*     */ 
/* 253 */     Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));
/*     */ 
/* 255 */     return tDate;
/*     */   }
/*     */ 
/*     */   public static String addMonth(Date dt, int step)
/*     */   {
/* 260 */     Locale loc = Locale.getDefault();
/* 261 */     Calendar cal = new GregorianCalendar(loc);
/* 262 */     cal.setTime(dt);
/* 263 */     cal.add(2, step);
/* 264 */     return formatDate(cal.getTime(), "yyyyMM");
/*     */   }
/*     */ 
/*     */   public static String addDate(String dateStr, String pattern, int step, String type)
/*     */   {
/* 281 */     if (dateStr == null) {
/* 282 */       return dateStr;
/*     */     }
/* 284 */     Date date1 = parseStrToDate(dateStr, pattern);
/*     */ 
/* 286 */     Locale loc = Locale.getDefault();
/* 287 */     Calendar cal = new GregorianCalendar(loc);
/* 288 */     cal.setTime(date1);
/*     */ 
/* 290 */     if ("week".equals(type))
/*     */     {
/* 292 */       cal.add(4, step);
/*     */     }
/* 294 */     else if ("year".equals(type))
/*     */     {
/* 296 */       cal.add(1, step);
/*     */     }
/* 298 */     else if ("month".equals(type))
/*     */     {
/* 300 */       cal.add(2, step);
/*     */     }
/* 302 */     else if ("day".equals(type))
/*     */     {
/* 304 */       cal.add(5, step);
/*     */     }
/* 306 */     else if ("hour".equals(type))
/*     */     {
/* 308 */       cal.add(10, step);
/*     */     }
/* 310 */     else if ("minute".equals(type))
/*     */     {
/* 312 */       cal.add(12, step);
/*     */     }
/* 314 */     else if ("second".equals(type))
/*     */     {
/* 316 */       cal.add(13, step);
/*     */     }
/*     */ 
/* 320 */     return formatDate(cal.getTime(), pattern);
/*     */   }
/*     */ 
/*     */   public static String minusDate(String dateStr, String pattern, int step, String type)
/*     */   {
/* 338 */     return addDate(dateStr, pattern, 0 - step, type);
/*     */   }
/*     */ 
/*     */   public static Date addDay(Date date, int days)
/*     */   {
/* 350 */     if (date == null) {
/* 351 */       return date;
/*     */     }
/* 353 */     Locale loc = Locale.getDefault();
/* 354 */     Calendar cal = new GregorianCalendar(loc);
/* 355 */     cal.setTime(date);
/* 356 */     cal.add(5, days);
/* 357 */     return cal.getTime();
/*     */   }
/*     */ 
/*     */   public static int getDays(Date date1, Date date2)
/*     */   {
/* 362 */     if ((date1 == null) || (date2 == null)) {
/* 363 */       return 0;
/*     */     }
/* 365 */     return (int)((date2.getTime() - date1.getTime()) / 86400000L);
/*     */   }
/*     */ 
/*     */   public static int compareDate(String dateStr1, String dateStr2, String pattern)
/*     */   {
/* 379 */     Date date1 = parseStrToDate(dateStr1, pattern);
/* 380 */     Date date2 = parseStrToDate(dateStr2, pattern);
/*     */ 
/* 382 */     return date1.compareTo(date2);
/*     */   }
/*     */ 
/*     */   public static String getFirstDayInMonth(String dateStr, String pattern)
/*     */   {
/* 392 */     Calendar cal = parseStrToCalendar(dateStr);
/* 393 */     int month = cal.get(2);
/* 394 */     int day = cal.get(5);
/*     */ 
/* 396 */     cal.add(5, 1 - day);
/*     */ 
/* 398 */     return formatDate(cal.getTime(), pattern);
/*     */   }
/*     */ 
/*     */   public static String getFirstDayInMonth(String dateStr, String pattern, String isZeroSecond)
/*     */   {
/* 409 */     Calendar cal = parseStrToCalendar(dateStr);
/* 410 */     int year = cal.get(1);
/* 411 */     int month = cal.get(2);
/* 412 */     int day = cal.get(5);
/* 413 */     int hour = cal.get(10);
/* 414 */     int minute = cal.get(12);
/* 415 */     int second = cal.get(13);
/* 416 */     day = 1;
/* 417 */     hour = 0;
/* 418 */     minute = 0;
/* 419 */     second = 0;
/* 420 */     cal.clear();
/* 421 */     cal.set(year, month, day, hour, minute, second);
/* 422 */     return formatDate(cal.getTime(), pattern);
/*     */   }
/*     */ 
/*     */   public static String getLastDayInMonth(String dateStr, String pattern)
/*     */   {
/* 431 */     Calendar cal = parseStrToCalendar(dateStr);
/* 432 */     int month = cal.get(2);
/* 433 */     int day = cal.get(5);
/*     */ 
/* 435 */     int maxDayInMonth = cal.getActualMaximum(5);
/* 436 */     int step = maxDayInMonth - day;
/*     */ 
/* 438 */     cal.add(5, step);
/*     */ 
/* 440 */     return formatDate(cal.getTime(), pattern);
/*     */   }
/*     */ 
/*     */   public static String getFirstDayInWeek(String dateStr, String pattern)
/*     */   {
/* 449 */     Calendar cal = parseStrToCalendar(dateStr);
/* 450 */     int day = cal.get(7);
/*     */ 
/* 452 */     cal.add(5, 1 - day);
/*     */ 
/* 454 */     return formatDate(cal.getTime(), pattern);
/*     */   }
/*     */ 
/*     */   public static String getLastDayInWeek(String dateStr, String pattern)
/*     */   {
/* 463 */     Calendar cal = parseStrToCalendar(dateStr);
/* 464 */     int day = cal.get(7);
/*     */ 
/* 466 */     cal.add(5, 6 - day);
/*     */ 
/* 468 */     return formatDate(cal.getTime(), pattern);
/*     */   }
/*     */ 
/*     */   public static long calendarDayPlus(String dateStr1, String dateStr2)
/*     */   {
/* 473 */     if ((dateStr1 == null) || (dateStr2 == null) || (dateStr1.equals("")) || (dateStr2.equals(""))) {
/* 474 */       return 0L;
/*     */     }
/* 476 */     Date date1 = parseStrToDate(dateStr1);
/* 477 */     Date date2 = parseStrToDate(dateStr2);
/* 478 */     Calendar c1 = Calendar.getInstance();
/* 479 */     c1.setTime(date1);
/* 480 */     Calendar c2 = Calendar.getInstance();
/* 481 */     c2.setTime(date2);
/* 482 */     long t1 = c1.getTimeInMillis();
/* 483 */     long t2 = c2.getTimeInMillis();
/* 484 */     long day = (t2 - t1) / 86400000L;
/* 485 */     return day;
/*     */   }
/*     */ 
/*     */   public static int calendarPlus(String dateStr1, String dateStr2)
/*     */   {
/* 495 */     if ((dateStr1 == null) || (dateStr2 == null) || (dateStr1.equals("")) || (dateStr2.equals(""))) {
/* 496 */       return 0;
/*     */     }
/*     */ 
/* 499 */     Calendar cal1 = parseStrToCalendar(dateStr1);
/*     */ 
/* 501 */     Calendar cal2 = parseStrToCalendar(dateStr2);
/*     */ 
/* 503 */     int dataStr1Year = cal1.get(1);
/* 504 */     int dataStr2Year = cal2.get(1);
/*     */ 
/* 506 */     int dataStr1Month = cal1.get(2);
/* 507 */     int dataStr2Month = cal2.get(2);
/*     */ 
/* 509 */     return dataStr2Year * 12 + dataStr2Month + 1 - (dataStr1Year * 12 + dataStr1Month);
/*     */   }
/*     */ 
/*     */   public static void main(String[] argv)
/*     */   {
/* 515 */     String dateStr = "2004-2-18 12:13:34";
/* 516 */     String dateStr1 = "2006-7-18 12:13:34";
/* 517 */     String dateStr2 = "2006-7-18 12:13:34";
/*     */ 
/* 519 */     Date date = addDay(parseStrToDate(dateStr, "yyyy-MM-dd HH:mm:ss"), 20);
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.common.util.date.DateFormatUtils
 * JD-Core Version:    0.6.2
 */