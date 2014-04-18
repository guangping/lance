/*    */ package com.ztesoft.inf.extend.xstream.core.util;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ 
/*    */ public class Fields
/*    */ {
/*    */   public static Field find(Class type, String name)
/*    */   {
/*    */     try
/*    */     {
/* 25 */       Field result = type.getDeclaredField(name);
/* 26 */       result.setAccessible(true);
/* 27 */       return result;
/*    */     } catch (NoSuchFieldException e) {
/* 29 */       throw new IllegalArgumentException("Could not access " + type.getName() + "." + name + " field: " + e.getMessage());
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void write(Field field, Object instance, Object value)
/*    */   {
/*    */     try {
/* 36 */       field.set(instance, value);
/*    */     } catch (IllegalAccessException e) {
/* 38 */       throw new RuntimeException("Could not write " + field.getType().getName() + "." + field.getName() + " field");
/*    */     }
/*    */   }
/*    */ 
/*    */   public static Object read(Field field, Object instance)
/*    */   {
/*    */     try
/*    */     {
/* 46 */       return field.get(instance); } catch (IllegalAccessException e) {
/*    */     }
/* 48 */     throw new RuntimeException("Could not read " + field.getType().getName() + "." + field.getName() + " field");
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.core.util.Fields
 * JD-Core Version:    0.6.2
 */