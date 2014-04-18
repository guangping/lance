/*    */ package com.ztesoft.inf.operation.vo;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class Operation
/*    */ {
/*    */   private String className;
/*    */   private String methodName;
/*    */   private String operationCode;
/*    */   private String operationName;
/*    */   private List operationInArgs;
/*    */   private OperationArg resultArg;
/*    */   private String state;
/*    */   private String description;
/*    */ 
/*    */   public Operation(Map map)
/*    */   {
/* 16 */     setValues(map);
/*    */   }
/*    */   private void setValues(Map map) {
/* 19 */     this.className = ((String)map.get("class_name"));
/* 20 */     this.methodName = ((String)map.get("method_name"));
/* 21 */     this.operationCode = ((String)map.get("operation_code"));
/* 22 */     this.operationName = ((String)map.get("operation_name"));
/*    */   }
/*    */   public OperationArg getResultArg() {
/* 25 */     return this.resultArg;
/*    */   }
/*    */   public void setResultArg(OperationArg resultArgument) {
/* 28 */     this.resultArg = resultArgument;
/*    */   }
/*    */   public List getOperationInArgs() {
/* 31 */     return this.operationInArgs;
/*    */   }
/*    */   public void setOperationInArgs(List operationInArgs) {
/* 34 */     this.operationInArgs = operationInArgs;
/*    */   }
/*    */ 
/*    */   public String getState()
/*    */   {
/* 40 */     return this.state;
/*    */   }
/*    */   public void setState(String state) {
/* 43 */     this.state = state;
/*    */   }
/*    */   public String getOperationName() {
/* 46 */     return this.operationName;
/*    */   }
/*    */   public void setOperationName(String operationName) {
/* 49 */     this.operationName = operationName;
/*    */   }
/*    */ 
/*    */   public String getClassName()
/*    */   {
/* 55 */     return this.className;
/*    */   }
/*    */   public void setClassName(String className) {
/* 58 */     this.className = className;
/*    */   }
/*    */   public String getMethodName() {
/* 61 */     return this.methodName;
/*    */   }
/*    */   public void setMethodName(String methodName) {
/* 64 */     this.methodName = methodName;
/*    */   }
/*    */   public String getOperationCode() {
/* 67 */     return this.operationCode;
/*    */   }
/*    */   public void setOperationCode(String operationCode) {
/* 70 */     this.operationCode = operationCode;
/*    */   }
/*    */   public String getDescription() {
/* 73 */     return this.description;
/*    */   }
/*    */   public void setDescription(String description) {
/* 76 */     this.description = description;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.operation.vo.Operation
 * JD-Core Version:    0.6.2
 */