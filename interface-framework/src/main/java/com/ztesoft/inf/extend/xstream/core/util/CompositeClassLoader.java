/*    */ package com.ztesoft.inf.extend.xstream.core.util;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ public class CompositeClassLoader extends ClassLoader
/*    */ {
/* 57 */   private final List classLoaders = Collections.synchronizedList(new ArrayList());
/*    */ 
/*    */   public CompositeClassLoader()
/*    */   {
/* 61 */     add(Object.class.getClassLoader());
/* 62 */     add(getClass().getClassLoader());
/*    */   }
/*    */ 
/*    */   public void add(ClassLoader classLoader)
/*    */   {
/* 72 */     if (classLoader != null)
/* 73 */       this.classLoaders.add(0, classLoader);
/*    */   }
/*    */ 
/*    */   public Class loadClass(String name)
/*    */     throws ClassNotFoundException
/*    */   {
/* 79 */     for (Iterator iterator = this.classLoaders.iterator(); iterator.hasNext(); ) {
/* 80 */       ClassLoader classLoader = (ClassLoader)iterator.next();
/*    */       try {
/* 82 */         return classLoader.loadClass(name);
/*    */       }
/*    */       catch (ClassNotFoundException notFound)
/*    */       {
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 92 */     ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
/*    */ 
/* 94 */     if (contextClassLoader != null) {
/* 95 */       return contextClassLoader.loadClass(name);
/*    */     }
/* 97 */     throw new ClassNotFoundException(name);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.core.util.CompositeClassLoader
 * JD-Core Version:    0.6.2
 */