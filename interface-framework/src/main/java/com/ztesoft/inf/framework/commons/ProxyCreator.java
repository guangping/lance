/*    */ package com.ztesoft.inf.framework.commons;
/*    */ 
/*    */ import net.sf.cglib.proxy.Enhancer;
/*    */ import net.sf.cglib.proxy.MethodInterceptor;
/*    */ 
/*    */ public class ProxyCreator
/*    */ {
/*    */   public static <T> T newInstance(Class<? extends MethodInterceptor> proxyClz, Class<T> targetClz)
/*    */   {
/*    */     try
/*    */     {
/* 12 */       MethodInterceptor proxy = (MethodInterceptor)proxyClz.newInstance();
/* 13 */       return newInstance(proxy, targetClz);
/*    */     } catch (Exception e) {
/* 15 */       throw new RuntimeException("生成代理对象失败!", e);
/*    */     }
/*    */   }
/*    */ 
/* 19 */   public static <T> T newInstance(MethodInterceptor proxy, Class<T> targetClz) { Enhancer enhancer = new Enhancer();
/* 20 */     enhancer.setSuperclass(targetClz);
/* 21 */     enhancer.setCallback(proxy);
/* 22 */     return enhancer.create();
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.framework.commons.ProxyCreator
 * JD-Core Version:    0.6.2
 */