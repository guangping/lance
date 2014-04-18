/*    */ package com.ztesoft.inf.framework.concurrent;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ public class LockedException extends RuntimeException
/*    */ {
/*    */   private Object key;
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public LockedException(Object key)
/*    */   {
/* 10 */     super(key.toString());
/* 11 */     this.key = key;
/*    */   }
/*    */ 
/*    */   public Object getKey() {
/* 15 */     return this.key;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 21 */     String a = "(^[0-9]{3,4}-[0-9]{3,8}$)|(^([0-9]{3,4})[0-9]{3,8}$)|(^1[358][0-9]{9}$)";
/* 22 */     byte[] aa = a.getBytes();
/* 23 */     for (int i = 0; i < aa.length; i++) {
/* 24 */       System.out.print(aa[i]);
/*    */     }
/* 26 */     System.out.println("");
/* 27 */     System.out.println(Pattern.matches(a, "111111110791123"));
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.framework.concurrent.LockedException
 * JD-Core Version:    0.6.2
 */