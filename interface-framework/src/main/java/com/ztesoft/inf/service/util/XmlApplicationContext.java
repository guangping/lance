/*    */ package com.ztesoft.inf.service.util;
/*    */ 
/*    */ import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
/*    */ import org.springframework.context.ApplicationContext;
/*    */ import org.springframework.context.support.ClassPathXmlApplicationContext;
/*    */ 
/*    */ public class XmlApplicationContext
/*    */ {
/*    */   private ClassPathXmlApplicationContext cxa;
/*    */ 
/*    */   public XmlApplicationContext(String path)
/*    */   {
/* 15 */     this.cxa = new ClassPathXmlApplicationContext(path);
/*    */   }
/*    */   public XmlApplicationContext(String[] configLocations) {
/* 18 */     this.cxa = new ClassPathXmlApplicationContext(configLocations);
/*    */   }
/*    */   public XmlApplicationContext(String[] configLocations, ApplicationContext parent) {
/* 21 */     this.cxa = new ClassPathXmlApplicationContext(configLocations, parent);
/*    */   }
/*    */   public XmlApplicationContext(String[] path, boolean refresh) {
/* 24 */     this.cxa = new ClassPathXmlApplicationContext(path, refresh);
/*    */   }
/*    */   public XmlApplicationContext(String[] path, boolean refresh, ApplicationContext parent) {
/* 27 */     this.cxa = new ClassPathXmlApplicationContext(path, refresh, parent);
/*    */   }
/*    */   public ClassPathXmlApplicationContext getClassPathXmlApplicationContext() {
/* 30 */     return this.cxa;
/*    */   }
/*    */ 
/*    */   public Object getBean(String name) {
/* 34 */     return this.cxa.getBean(name);
/*    */   }
/*    */   public Object getBean(String name, Class clazz) {
/* 37 */     return this.cxa.getBean(name, clazz);
/*    */   }
/*    */   public ConfigurableListableBeanFactory getBeanFactory() {
/* 40 */     return this.cxa.getBeanFactory();
/*    */   }
/*    */   public boolean containsBean(String name) {
/* 43 */     return this.cxa.containsBean(name);
/*    */   }
/*    */   public boolean containsBeanDefinition(String name) {
/* 46 */     return this.cxa.containsBeanDefinition(name);
/*    */   }
/*    */   public boolean isSingleton(String name) {
/* 49 */     return this.cxa.isSingleton(name);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.service.util.XmlApplicationContext
 * JD-Core Version:    0.6.2
 */