/*    */ package com.ztesoft.inf.extend.xstream.converters.reflection;
/*    */ 
/*    */ public class ReflectionConverter extends AbstractReflectionConverter
/*    */ {
/*    */   public ReflectionConverter(ReflectionProvider reflectionProvider)
/*    */   {
/* 17 */     super(reflectionProvider);
/*    */   }
/*    */ 
/*    */   public boolean canConvert(Class type) {
/* 21 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.reflection.ReflectionConverter
 * JD-Core Version:    0.6.2
 */