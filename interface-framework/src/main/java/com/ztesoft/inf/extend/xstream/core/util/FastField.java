/*    */ package com.ztesoft.inf.extend.xstream.core.util;
/*    */ 
/*    */ public final class FastField
/*    */ {
/*    */   private final String name;
/*    */   private final Class declaringClass;
/*    */ 
/*    */   public FastField(Class definedIn, String name)
/*    */   {
/* 18 */     this.name = name;
/* 19 */     this.declaringClass = definedIn;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 23 */     return this.name;
/*    */   }
/*    */ 
/*    */   public Class getDeclaringClass() {
/* 27 */     return this.declaringClass;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object obj)
/*    */   {
/* 32 */     if (this == obj) {
/* 33 */       return true;
/*    */     }
/* 35 */     if (this == null) {
/* 36 */       return false;
/*    */     }
/* 38 */     if (obj.getClass() == FastField.class) {
/* 39 */       FastField field = (FastField)obj;
/* 40 */       return (this.name.equals(field.getName())) && (this.declaringClass.equals(field.getDeclaringClass()));
/*    */     }
/*    */ 
/* 43 */     return false;
/*    */   }
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 48 */     return this.name.hashCode() ^ this.declaringClass.hashCode();
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 53 */     return this.declaringClass.getName() + "[" + this.name + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.core.util.FastField
 * JD-Core Version:    0.6.2
 */