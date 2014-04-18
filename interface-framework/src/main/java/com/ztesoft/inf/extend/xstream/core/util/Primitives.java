/*    */ package com.ztesoft.inf.extend.xstream.core.util;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public final class Primitives
/*    */ {
/* 23 */   private static final Map BOX = new HashMap();
/* 24 */   private static final Map UNBOX = new HashMap();
/*    */ 
/*    */   public static Class box(Class type)
/*    */   {
/* 39 */     return (Class)BOX.get(type);
/*    */   }
/*    */ 
/*    */   public static Class unbox(Class type) {
/* 43 */     return (Class)UNBOX.get(type);
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 27 */     Class[][] boxing = { { Byte.TYPE, Byte.class }, { Character.TYPE, Character.class }, { Short.TYPE, Short.class }, { Integer.TYPE, Integer.class }, { Long.TYPE, Long.class }, { Float.TYPE, Float.class }, { Double.TYPE, Double.class }, { Boolean.TYPE, Boolean.class }, { Void.TYPE, Void.class } };
/*    */ 
/* 32 */     for (int i = 0; i < boxing.length; i++) {
/* 33 */       BOX.put(boxing[i][0], boxing[i][1]);
/* 34 */       UNBOX.put(boxing[i][1], boxing[i][0]);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.core.util.Primitives
 * JD-Core Version:    0.6.2
 */