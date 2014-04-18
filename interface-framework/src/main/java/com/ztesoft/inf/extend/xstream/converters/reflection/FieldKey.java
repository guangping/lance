/*    */ package com.ztesoft.inf.extend.xstream.converters.reflection;
/*    */ 
/*    */ public class FieldKey
/*    */ {
/*    */   private final String fieldName;
/*    */   private final Class declaringClass;
/*    */   private final int depth;
/*    */   private final int order;
/*    */ 
/*    */   public FieldKey(String fieldName, Class declaringClass, int order)
/*    */   {
/* 26 */     if ((fieldName == null) || (declaringClass == null)) {
/* 27 */       throw new IllegalArgumentException("fieldName or declaringClass is null");
/*    */     }
/*    */ 
/* 30 */     this.fieldName = fieldName;
/* 31 */     this.declaringClass = declaringClass;
/* 32 */     this.order = order;
/* 33 */     Class c = declaringClass;
/* 34 */     int i = 0;
/* 35 */     while (c.getSuperclass() != null) {
/* 36 */       i++;
/* 37 */       c = c.getSuperclass();
/*    */     }
/* 39 */     this.depth = i;
/*    */   }
/*    */ 
/*    */   public String getFieldName() {
/* 43 */     return this.fieldName;
/*    */   }
/*    */ 
/*    */   public Class getDeclaringClass() {
/* 47 */     return this.declaringClass;
/*    */   }
/*    */ 
/*    */   public int getDepth() {
/* 51 */     return this.depth;
/*    */   }
/*    */ 
/*    */   public int getOrder() {
/* 55 */     return this.order;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object o)
/*    */   {
/* 60 */     if (this == o)
/* 61 */       return true;
/* 62 */     if (!(o instanceof FieldKey)) {
/* 63 */       return false;
/*    */     }
/* 65 */     FieldKey fieldKey = (FieldKey)o;
/*    */ 
/* 67 */     if (!this.declaringClass.equals(fieldKey.declaringClass))
/* 68 */       return false;
/* 69 */     if (!this.fieldName.equals(fieldKey.fieldName)) {
/* 70 */       return false;
/*    */     }
/* 72 */     return true;
/*    */   }
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 78 */     int result = this.fieldName.hashCode();
/* 79 */     result = 29 * result + this.declaringClass.hashCode();
/* 80 */     return result;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 85 */     return "FieldKey{order=" + this.order + ", writer=" + this.depth + ", declaringClass=" + this.declaringClass + ", fieldName='" + this.fieldName + "'" + "}";
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.reflection.FieldKey
 * JD-Core Version:    0.6.2
 */