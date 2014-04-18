/*     */ package com.ztesoft.common.util.date;
/*     */ 
/*     */ import com.ztesoft.common.util.StringUtils;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.Timestamp;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.GregorianCalendar;
/*     */ 
/*     */ public class DateUtil
/*     */ {
/*     */   public static final String defaultPattern = "yyyy-MM-dd HH:mm:ss";
/*     */   public static final String PATTERN_TIMESTAMP = "yyyyMMddHHmmssSSS";
/*     */   public static final String PATTERN_TIME = "yyyyMMddHHmmss";
/*     */   public static final String PATTERN_DATE = "yyyyMMdd";
/*     */   public static final int TRUNCTO_YEAR = 0;
/*     */   public static final int TRUNCTO_MONTH = 1;
/*     */   public static final int TRUNCTO_DAY = 2;
/*     */   public static final String PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss";
/*     */   public static final String PATTERN_MONTH = "yyyyMM";
/*     */   public static final String PATTERN_SHORT = "yyyyMMdd";
/*     */   public static final String DATE_FORMAT = "yyyy-MM-dd";
/*     */   public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
/*     */   public static final String DATE_FORMAT_8 = "yyyyMMdd";
/*     */   public static final String DATE_TIME_FORMAT_14 = "yyyyMMddHHmmss";
/*     */   public static final String DATE_TIME_FORMAT_12 = "yyMMddHHmmss";
/*     */   public static final String DEFAULT_EXPIRED_DATE = "2030-1-1 00:00:00";
/*     */   public static final String DATE_FORMAT_MONTH = "yyyyMM";
/*     */ 
/*     */   public static java.util.Date current()
/*     */   {
/*  40 */     return new java.sql.Date(System.currentTimeMillis());
/*     */   }
/*     */ 
/*     */   public static Timestamp currentTime() {
/*  44 */     return new Timestamp(System.currentTimeMillis());
/*     */   }
/*     */ 
/*     */   public static Timestamp addDay(Timestamp time, int days) {
/*  48 */     Calendar c = Calendar.getInstance();
/*  49 */     c.setTimeInMillis(time.getTime());
/*  50 */     c.add(6, days);
/*  51 */     return new Timestamp(c.getTimeInMillis());
/*     */   }
/*     */ 
/*     */   public static Timestamp addDay(java.util.Date time, int days) {
/*  55 */     Calendar c = Calendar.getInstance();
/*  56 */     c.setTimeInMillis(time.getTime());
/*  57 */     c.add(6, days);
/*  58 */     return new Timestamp(c.getTimeInMillis());
/*     */   }
/*     */ 
/*     */   public static Timestamp trunc(Timestamp time, int truncTo)
/*     */   {
/*  63 */     Calendar c = Calendar.getInstance();
/*  64 */     c.setTimeInMillis(time.getTime());
/*  65 */     int year = 0; int month = 0; int date = 1; int hour = 0; int minute = 0; int second = 0;
/*  66 */     if (truncTo >= 0) {
/*  67 */       year = c.get(1);
/*     */     }
/*  69 */     if (truncTo >= 1) {
/*  70 */       month = c.get(2);
/*     */     }
/*  72 */     if (truncTo >= 2) {
/*  73 */       date = c.get(5);
/*     */     }
/*  75 */     c.set(year, month, date, hour, minute, second);
/*  76 */     c.set(14, 0);
/*  77 */     return new Timestamp(c.getTimeInMillis());
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/*  81 */     String day = current("dd");
/*  82 */     System.out.println(day);
/*     */   }
/*     */ 
/*     */   public static java.sql.Date addDay(java.sql.Date date, int days) {
/*  86 */     Calendar c = Calendar.getInstance();
/*  87 */     c.setTimeInMillis(date.getTime());
/*  88 */     c.add(6, days);
/*  89 */     return new java.sql.Date(c.getTimeInMillis());
/*     */   }
/*     */ 
/*     */   public static java.util.Date add(java.util.Date date, int filed, int amount) {
/*  93 */     Calendar c = Calendar.getInstance();
/*  94 */     c.setTimeInMillis(date.getTime());
/*  95 */     c.add(filed, amount);
/*  96 */     return new java.util.Date(c.getTimeInMillis());
/*     */   }
/*     */ 
/*     */   public static Calendar toCalander(Timestamp timestamp) {
/* 100 */     Calendar c = Calendar.getInstance();
/* 101 */     c.setTimeInMillis(timestamp.getTime());
/* 102 */     return c;
/*     */   }
/*     */ 
/*     */   public static Calendar toCalander(java.util.Date date) {
/* 106 */     Calendar c = Calendar.getInstance();
/* 107 */     c.setTimeInMillis(date.getTime());
/* 108 */     return c;
/*     */   }
/*     */ 
/*     */   public static String formatDate(java.util.Date date)
/*     */   {
/* 118 */     return formatDate(date, "yyyy-MM-dd HH:mm:ss");
/*     */   }
/*     */ 
/*     */   public static String formatCurDate(String pattern) {
/* 122 */     java.util.Date currentDate = getCurrnetDate();
/* 123 */     return formatDate(currentDate, pattern);
/*     */   }
/*     */ 
/*     */   public static String formatDate(java.util.Date date, String pattern) {
/* 127 */     if (date == null)
/* 128 */       return null;
/* 129 */     if (pattern == null) {
/* 130 */       throw new IllegalArgumentException("pattern is null");
/*     */     }
/* 132 */     SimpleDateFormat formatter = new SimpleDateFormat(pattern);
/* 133 */     return formatter.format(date);
/*     */   }
/*     */ 
/*     */   public static java.util.Date parse(String dateStr, Class resultClass)
/*     */     throws ParseException
/*     */   {
/* 139 */     return parse(dateStr, "yyyy-MM-dd HH:mm:ss", resultClass);
/*     */   }
/*     */ 
/*     */   public static java.util.Date parseAsDate(String dateStr) {
/* 143 */     return parse(dateStr, "yyyy-MM-dd HH:mm:ss", java.sql.Date.class);
/*     */   }
/*     */ 
/*     */   public static java.util.Date parseAsDate(String dateStr, String pattern) {
/* 147 */     return parse(dateStr, pattern, java.sql.Date.class);
/*     */   }
/*     */ 
/*     */   public static java.util.Date parseAsTimestamp(String dateStr) {
/* 151 */     return parse(dateStr, "yyyy-MM-dd HH:mm:ss", Timestamp.class);
/*     */   }
/*     */ 
/*     */   public static java.util.Date parse(String dateStr, String pattern, Class resultClass) {
/* 155 */     if (dateStr == null)
/* 156 */       throw new IllegalArgumentException("dateStr is null");
/* 157 */     if (pattern == null) {
/* 158 */       throw new IllegalArgumentException("pattern is null");
/* 161 */     }
/*     */ SimpleDateFormat formatter = new SimpleDateFormat(pattern);
/*     */     java.util.Date date;
/*     */     try {
/* 164 */       date = formatter.parse(dateStr);
/*     */     } catch (ParseException e) {
/* 166 */       throw new RuntimeException(e);
/*     */     }
/* 168 */     if (resultClass == null)
/* 169 */       return date;
/* 170 */     if (resultClass.equals(java.sql.Date.class))
/* 171 */       return new java.sql.Date(date.getTime());
/* 172 */     if (resultClass.equals(Timestamp.class)) {
/* 173 */       return new Timestamp(date.getTime());
/*     */     }
/* 175 */     throw new IllegalArgumentException("result class must be java.sql.Date or java.sql.Timestamp");
/*     */   }
/*     */ 
/*     */   public static java.util.Date addMonth(int amount)
/*     */   {
/* 182 */     return add(currentTime(), 2, amount);
/*     */   }
/*     */ 
/*     */   public static java.util.Date addMonth(java.util.Date date, int amount) {
/* 186 */     return add(date, 2, amount);
/*     */   }
/*     */ 
/*     */   public static int getDay() {
/* 190 */     return Calendar.getInstance().get(5);
/*     */   }
/*     */ 
/*     */   public static String current(String pattern) {
/* 194 */     return formatDate(new java.util.Date(), pattern);
/*     */   }
/*     */ 
/*     */   public static String currentTimstamp()
/*     */   {
/* 203 */     return formatDate(new java.util.Date(), "yyyyMMddHHmmssSSS");
/*     */   }
/*     */ 
/*     */   public static String currentDate() {
/* 207 */     return formatDate(new java.util.Date(), "yyyyMMdd");
/*     */   }
/*     */ 
/*     */   public static String currentDateTime() {
/* 211 */     return formatDate(new java.util.Date(), "yyyy-MM-dd HH:mm:ss");
/*     */   }
/*     */ 
/*     */   public static java.util.Date nextMonth(java.util.Date currentDate, int value) {
/* 215 */     GregorianCalendar cal = new GregorianCalendar();
/* 216 */     cal.setTime(currentDate);
/* 217 */     cal.add(2, value);
/* 218 */     return cal.getTime();
/*     */   }
/*     */ 
/*     */   public static java.util.Date nextDay(java.util.Date currentDate, int value) {
/* 222 */     GregorianCalendar cal = new GregorianCalendar();
/* 223 */     cal.setTime(currentDate);
/* 224 */     cal.add(5, value);
/* 225 */     return cal.getTime();
/*     */   }
/*     */ 
/*     */   public static String invokeDate(int value) {
/* 229 */     java.util.Date currentDate = getCurrnetDate();
/* 230 */     java.util.Date dt = nextDay(currentDate, value);
/* 231 */     return getDate(dt);
/*     */   }
/*     */ 
/*     */   public static String invokeDateTime(int value) {
/* 235 */     java.util.Date currentDate = getCurrnetDate();
/* 236 */     java.util.Date dt = nextDay(currentDate, value);
/* 237 */     return getDateTime(dt);
/*     */   }
/*     */ 
/*     */   public static java.util.Date getCurrnetDate() {
/* 241 */     return new java.util.Date();
/*     */   }
/*     */ 
/*     */   public static String getDateTime(java.util.Date dt) {
/* 245 */     return formatDate(dt, "yyyy-MM-dd HH:mm:ss");
/*     */   }
/*     */ 
/*     */   public static String getDate(java.util.Date dt) {
/* 249 */     return formatDate(dt, "yyyyMMdd");
/*     */   }
/*     */ 
/*     */   public static String getYearMonth(java.util.Date dt) {
/* 253 */     return formatDate(dt, "yyyyMM");
/*     */   }
/*     */ 
/*     */   public static String invokeYearMonth(int value) {
/* 257 */     java.util.Date currentDate = getCurrnetDate();
/* 258 */     java.util.Date dt = nextMonth(currentDate, value);
/* 259 */     return getYearMonth(dt);
/*     */   }
/*     */ 
/*     */   public static String invokeYearMonth(java.util.Date currentDate, int value) {
/* 263 */     java.util.Date dt = nextMonth(currentDate, value);
/* 264 */     return getYearMonth(dt);
/*     */   }
/*     */ 
/*     */   public static String currentMonth() {
/* 268 */     return formatDate(new java.util.Date(), "yyyyMM");
/*     */   }
/*     */ 
/*     */   public static String getCurrentTime(String def_value, int pos) {
/* 272 */     if (StringUtils.isEmpty(def_value)) {
/* 273 */       return "";
/*     */     }
/*     */ 
/* 276 */     if (def_value.equalsIgnoreCase("today")) {
/* 277 */       return currentDate();
/*     */     }
/*     */ 
/* 280 */     if (def_value.indexOf("-") > 0) {
/*     */       try {
/* 282 */         String[] str = def_value.split("-");
/* 283 */         if (str[0].equalsIgnoreCase("today"))
/*     */         {
/* 285 */           int num = -Integer.parseInt(str[1]);
/* 286 */           return invokeDate(num);
/*     */         }
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/*     */       }
/*     */     }
/*     */ 
/* 294 */     if (def_value.indexOf("+") > 0) {
/*     */       try {
/* 296 */         String[] str = def_value.split("+");
/* 297 */         if (str[0].equalsIgnoreCase("today")) {
/* 298 */           int num = Integer.parseInt(str[1]);
/* 299 */           return invokeDate(num);
/*     */         }
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/*     */       }
/*     */     }
/* 306 */     if (def_value.indexOf(",") > 0) {
/* 307 */       String[] arr = def_value.split(",");
/* 308 */       if (pos == 1) {
/* 309 */         return arr[0];
/*     */       }
/* 311 */       return arr[1];
/*     */     }
/*     */ 
/* 315 */     return def_value;
/*     */   }
/*     */ 
/*     */   public static String getCurrentDate(String def_value, int pos)
/*     */   {
/* 326 */     if (StringUtils.isEmpty(def_value)) {
/* 327 */       return "";
/*     */     }
/*     */ 
/* 330 */     if (def_value.equalsIgnoreCase("today")) {
/* 331 */       return currentDate();
/*     */     }
/*     */ 
/* 334 */     if (def_value.indexOf("-") > 0) {
/*     */       try {
/* 336 */         String[] str = def_value.split("-");
/* 337 */         if (str[0].equalsIgnoreCase("today"))
/*     */         {
/* 339 */           int num = -Integer.parseInt(str[1]);
/* 340 */           return invokeDate(num);
/*     */         }
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/*     */       }
/*     */     }
/*     */ 
/* 348 */     if (def_value.indexOf("+") > 0) {
/*     */       try {
/* 350 */         String[] str = def_value.split("+");
/* 351 */         if (str[0].equalsIgnoreCase("today")) {
/* 352 */           int num = Integer.parseInt(str[1]);
/* 353 */           return invokeDate(num);
/*     */         }
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/*     */       }
/*     */     }
/* 360 */     if (def_value.indexOf(",") > 0) {
/* 361 */       String[] arr = def_value.split(",");
/* 362 */       if (pos == 1) {
/* 363 */         return arr[0];
/*     */       }
/* 365 */       return arr[1];
/*     */     }
/*     */ 
/* 369 */     return def_value;
/*     */   }
/*     */ 
/*     */   public static String getCurrentMonth(String def_value, int pos)
/*     */   {
/* 374 */     if (StringUtils.isEmpty(def_value)) {
/* 375 */       return "";
/*     */     }
/*     */ 
/* 378 */     if (def_value.equalsIgnoreCase("today")) {
/* 379 */       return currentMonth();
/*     */     }
/*     */ 
/* 382 */     if (def_value.indexOf("-") > 0) {
/*     */       try {
/* 384 */         String[] str = def_value.split("-");
/* 385 */         if (str[0].equalsIgnoreCase("today"))
/*     */         {
/* 387 */           int num = -Integer.parseInt(str[1]);
/* 388 */           return invokeYearMonth(num);
/*     */         }
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/*     */       }
/*     */     }
/*     */ 
/* 396 */     if (def_value.indexOf("+") > 0) {
/*     */       try {
/* 398 */         String[] str = def_value.split("+");
/* 399 */         if (str[0].equalsIgnoreCase("today")) {
/* 400 */           int num = Integer.parseInt(str[1]);
/* 401 */           return invokeYearMonth(num);
/*     */         }
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/*     */       }
/*     */     }
/* 408 */     if (def_value.indexOf(",") > 0) {
/* 409 */       String[] arr = def_value.split(",");
/* 410 */       if (pos == 1) {
/* 411 */         return arr[0];
/*     */       }
/* 413 */       return arr[1];
/*     */     }
/*     */ 
/* 417 */     return def_value;
/*     */   }
/*     */ 
/*     */   public static String getCurrentDateTime(String def_value, int pos)
/*     */   {
/* 422 */     if (StringUtils.isEmpty(def_value)) {
/* 423 */       return "";
/*     */     }
/*     */ 
/* 426 */     if (def_value.equalsIgnoreCase("today")) {
/* 427 */       return currentDateTime();
/*     */     }
/*     */ 
/* 430 */     if (def_value.indexOf("-") > 0) {
/*     */       try {
/* 432 */         String[] str = def_value.split("-");
/* 433 */         if (str[0].equalsIgnoreCase("today"))
/*     */         {
/* 435 */           int num = -Integer.parseInt(str[1]);
/* 436 */           return invokeDateTime(num);
/*     */         }
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/*     */       }
/*     */     }
/*     */ 
/* 444 */     if (def_value.indexOf("+") > 0) {
/*     */       try {
/* 446 */         String[] str = def_value.split("+");
/* 447 */         if (str[0].equalsIgnoreCase("today")) {
/* 448 */           int num = Integer.parseInt(str[1]);
/* 449 */           return invokeDateTime(num);
/*     */         }
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/*     */       }
/*     */     }
/* 456 */     if (def_value.indexOf(",") > 0) {
/* 457 */       String[] arr = def_value.split(",");
/* 458 */       if (pos == 1) {
/* 459 */         return arr[0];
/*     */       }
/* 461 */       return arr[1];
/*     */     }
/*     */ 
/* 465 */     return def_value;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.common.util.date.DateUtil
 * JD-Core Version:    0.6.2
 */