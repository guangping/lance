/*    */ package com.ztesoft.inf.operation;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.mapper.MapperContext;
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class OperationInternal
/*    */ {
/* 11 */   private List inArgMapperContext = new ArrayList();
/* 12 */   private List inArgXpaths = new ArrayList();
/*    */   private Method method;
/*    */   private MapperContext resultMapperContext;
/*    */   private Object target;
/*    */ 
/*    */   public void addArgMapperContext(MapperContext ctx)
/*    */   {
/* 19 */     this.inArgMapperContext.add(ctx);
/*    */   }
/*    */   public void addArgXpath(String xpath) {
/* 22 */     this.inArgXpaths.add(xpath);
/*    */   }
/*    */   public List getInArgMapperContext() {
/* 25 */     return this.inArgMapperContext;
/*    */   }
/*    */   public List getInArgXpaths() {
/* 28 */     return this.inArgXpaths;
/*    */   }
/*    */   public Method getMethod() {
/* 31 */     return this.method;
/*    */   }
/*    */   public MapperContext getResultMapperContext() {
/* 34 */     return this.resultMapperContext;
/*    */   }
/*    */   public MapperContext getResultMapperCtx() {
/* 37 */     return this.resultMapperContext;
/*    */   }
/*    */   public Object getTarget() {
/* 40 */     return this.target;
/*    */   }
/*    */   public void setInArgMapperContext(List inArgMapperContext) {
/* 43 */     this.inArgMapperContext = inArgMapperContext;
/*    */   }
/*    */   public void setInArgXpaths(List inArgXpaths) {
/* 46 */     this.inArgXpaths = inArgXpaths;
/*    */   }
/*    */   public void setMethod(Method method) {
/* 49 */     this.method = method;
/*    */   }
/*    */   public void setResultMapperContext(MapperContext resultMapperContext) {
/* 52 */     this.resultMapperContext = resultMapperContext;
/*    */   }
/*    */   public void setTarget(Object target) {
/* 55 */     this.target = target;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.operation.OperationInternal
 * JD-Core Version:    0.6.2
 */