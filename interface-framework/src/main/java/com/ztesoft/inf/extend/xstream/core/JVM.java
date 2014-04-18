/*     */ package com.ztesoft.inf.extend.xstream.core;
/*     */ 
/*     */ import com.ztesoft.inf.extend.xstream.converters.reflection.PureJavaReflectionProvider;
/*     */ import com.ztesoft.inf.extend.xstream.converters.reflection.ReflectionProvider;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class JVM
/*     */ {
/*     */   private ReflectionProvider reflectionProvider;
/*  13 */   private transient Map loaderCache = new HashMap();
/*     */ 
/*  15 */   private final boolean supportsAWT = loadClass("java.awt.Color") != null;
/*  16 */   private final boolean supportsSwing = loadClass("javax.swing.LookAndFeel") != null;
/*  17 */   private final boolean supportsSQL = loadClass("java.sql.Date") != null;
/*     */ 
/*  19 */   private static final String vendor = System.getProperty("java.vm.vendor");
/*  20 */   private static final float majorJavaVersion = getMajorJavaVersion();
/*  21 */   private static final boolean reverseFieldOrder = (isHarmony()) || ((isIBM()) && (!is15()));
/*     */   static final float DEFAULT_JAVA_VERSION = 1.3F;
/*     */ 
/*     */   private static final float getMajorJavaVersion()
/*     */   {
/*     */     try
/*     */     {
/*  28 */       return Float.parseFloat(System.getProperty("java.specification.version"));
/*     */     } catch (NumberFormatException e) {
/*     */     }
/*  31 */     return 1.3F;
/*     */   }
/*     */ 
/*     */   public static boolean is14()
/*     */   {
/*  36 */     return majorJavaVersion >= 1.4F;
/*     */   }
/*     */ 
/*     */   public static boolean is15() {
/*  40 */     return majorJavaVersion >= 1.5F;
/*     */   }
/*     */ 
/*     */   public static boolean is16() {
/*  44 */     return majorJavaVersion >= 1.6F;
/*     */   }
/*     */ 
/*     */   private static boolean isIBM() {
/*  48 */     return vendor.indexOf("IBM") != -1;
/*     */   }
/*     */ 
/*     */   private static boolean isHarmony() {
/*  52 */     return vendor.indexOf("Apache Software Foundation") != -1;
/*     */   }
/*     */ 
/*     */   public Class loadClass(String name) {
/*     */     try {
/*  57 */       WeakReference reference = (WeakReference)this.loaderCache.get(name);
/*  58 */       if (reference != null) {
/*  59 */         Class cached = (Class)reference.get();
/*  60 */         if (cached != null) {
/*  61 */           return cached;
/*     */         }
/*     */       }
/*     */ 
/*  65 */       Class clazz = Class.forName(name, false, getClass().getClassLoader());
/*     */ 
/*  67 */       this.loaderCache.put(name, new WeakReference(clazz));
/*  68 */       return clazz; } catch (ClassNotFoundException e) {
/*     */     }
/*  70 */     return null;
/*     */   }
/*     */ 
/*     */   public synchronized ReflectionProvider bestReflectionProvider()
/*     */   {
/*  75 */     if (this.reflectionProvider == null) {
/*  76 */       this.reflectionProvider = new PureJavaReflectionProvider();
/*     */     }
/*  78 */     return this.reflectionProvider;
/*     */   }
/*     */ 
/*     */   public static boolean reverseFieldDefinition() {
/*  82 */     return reverseFieldOrder;
/*     */   }
/*     */ 
/*     */   public boolean supportsAWT()
/*     */   {
/*  89 */     return this.supportsAWT;
/*     */   }
/*     */ 
/*     */   public boolean supportsSwing()
/*     */   {
/*  96 */     return this.supportsSwing;
/*     */   }
/*     */ 
/*     */   public boolean supportsSQL()
/*     */   {
/* 103 */     return this.supportsSQL;
/*     */   }
/*     */ 
/*     */   private Object readResolve() {
/* 107 */     this.loaderCache = new HashMap();
/* 108 */     return this;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.core.JVM
 * JD-Core Version:    0.6.2
 */