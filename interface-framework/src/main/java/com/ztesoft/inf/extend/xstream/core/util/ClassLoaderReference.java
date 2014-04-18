/*    */ package com.ztesoft.inf.extend.xstream.core.util;
/*    */ 
/*    */ public class ClassLoaderReference extends ClassLoader
/*    */ {
/*    */   private transient ClassLoader reference;
/*    */ 
/*    */   public ClassLoaderReference(ClassLoader reference)
/*    */   {
/* 27 */     this.reference = reference;
/*    */   }
/*    */ 
/*    */   public Class loadClass(String name) throws ClassNotFoundException
/*    */   {
/* 32 */     return this.reference.loadClass(name);
/*    */   }
/*    */ 
/*    */   public ClassLoader getReference() {
/* 36 */     return this.reference;
/*    */   }
/*    */ 
/*    */   public void setReference(ClassLoader reference) {
/* 40 */     this.reference = reference;
/*    */   }
/*    */ 
/*    */   private Object writeReplace() {
/* 44 */     return new Replacement();
/*    */   }
/*    */ 
/*    */   static class Replacement
/*    */   {
/*    */     private Object readResolve() {
/* 50 */       return new ClassLoaderReference(new CompositeClassLoader());
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.core.util.ClassLoaderReference
 * JD-Core Version:    0.6.2
 */