/*    */ package com.ztesoft.framework.sqls;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class SqlUtil
/*    */ {
/*    */   private static final int PRIVATE = 2;
/*    */ 
/*    */   public static void initSqls(Class clazz, Object instance, Map sqls)
/*    */   {
/* 11 */     Field[] fields = clazz.getDeclaredFields();
/*    */     try {
/* 13 */       for (Field f : fields) {
/* 14 */         f.setAccessible(true);
/* 15 */         if ((null == f.get(instance)) || ("".equals(((String)f.get(instance)).trim()))) {
/* 16 */           throw new RuntimeException("类【" + clazz.getName() + "】的属性【" + f.getName() + "】值不能为空！" + " 或许【" + f.getName() + "】对应的SQL使用到的对象与当前对象有循环调用，请把使用到的对象方法抽取到DatabaseFunction类中，避免循环引用!");
/*    */         }
/*    */ 
/* 19 */         sqls.put(f.getName(), (String)f.get(instance));
/*    */       }
/*    */     } catch (IllegalArgumentException e) {
/* 22 */       throw new RuntimeException(e);
/*    */     } catch (IllegalAccessException e) {
/* 24 */       throw new RuntimeException(e);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.framework.sqls.SqlUtil
 * JD-Core Version:    0.6.2
 */