/*    */ package com.ztesoft.framework.sqls;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class Sqls
/*    */ {
/*  9 */   protected Map<String, String> sqls = new HashMap();
/*    */ 
/*    */   public String getSql(String name)
/*    */   {
/* 13 */     return (String)this.sqls.get(name);
/*    */   }
/*    */ 
/*    */   public Sqls()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Sqls(Class clazz)
/*    */   {
/* 22 */     Field[] fields = clazz.getDeclaredFields();
/*    */     try {
/* 24 */       for (Field f : fields)
/* 25 */         this.sqls.put(f.getName(), (String)f.get(this));
/*    */     }
/*    */     catch (IllegalArgumentException e) {
/* 28 */       e.printStackTrace();
/*    */     } catch (IllegalAccessException e) {
/* 30 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.framework.sqls.Sqls
 * JD-Core Version:    0.6.2
 */