/*    */ package com.ztesoft.inf.communication.client;
/*    */ 
/*    */ import com.ztesoft.common.util.convert.ObjectUtils;
/*    */ import java.sql.Timestamp;
/*    */ 
/*    */ public class InvokeContext
/*    */ {
/*    */   private Object parameters;
/*    */   private String resultString;
/*    */   private String requestString;
/*    */   private String responeString;
/*    */   private String failure;
/*    */   private String operationCode;
/*    */   private Timestamp requestTime;
/*    */   private Timestamp responseTime;
/*    */   private String endpoint;
/*    */ 
/*    */   public String getEndpoint()
/*    */   {
/* 20 */     return this.endpoint;
/*    */   }
/*    */ 
/*    */   public void setEndpoint(String endpoint) {
/* 24 */     this.endpoint = endpoint;
/*    */   }
/*    */ 
/*    */   public String getOperationCode() {
/* 28 */     return this.operationCode;
/*    */   }
/*    */ 
/*    */   public void setOperationCode(String operationCode) {
/* 32 */     this.operationCode = operationCode;
/*    */   }
/*    */ 
/*    */   public String getRequestString() {
/* 36 */     return this.requestString;
/*    */   }
/*    */ 
/*    */   public void setRequestString(String requestString) {
/* 40 */     this.requestString = requestString;
/*    */   }
/*    */ 
/*    */   public String getResponeString() {
/* 44 */     return this.responeString;
/*    */   }
/*    */ 
/*    */   public void setResponeString(String responeString) {
/* 48 */     this.responeString = responeString;
/*    */   }
/*    */ 
/*    */   public String getFailure() {
/* 52 */     return this.failure;
/*    */   }
/*    */ 
/*    */   public void setFailure(String failure) {
/* 56 */     if (failure == null) {
/* 57 */       failure = "异常";
/*    */     }
/* 59 */     this.failure = failure;
/*    */   }
/*    */ 
/*    */   public Object getParameters() {
/* 63 */     return this.parameters;
/*    */   }
/*    */ 
/*    */   public void setParameters(Object parameters) {
/* 67 */     this.parameters = parameters;
/*    */   }
/*    */ 
/*    */   public Timestamp getRequestTime() {
/* 71 */     return this.requestTime;
/*    */   }
/*    */ 
/*    */   public void setRequestTime(Timestamp requestTime) {
/* 75 */     this.requestTime = requestTime;
/*    */   }
/*    */ 
/*    */   public Timestamp getResponseTime() {
/* 79 */     return this.responseTime;
/*    */   }
/*    */ 
/*    */   public void setResponseTime(Timestamp responseTime) {
/* 83 */     this.responseTime = responseTime;
/*    */   }
/*    */ 
/*    */   public String paramsAsString() {
/* 87 */     return ObjectUtils.toString(this.parameters);
/*    */   }
/*    */ 
/*    */   public String getResultString() {
/* 91 */     return this.resultString;
/*    */   }
/*    */ 
/*    */   public void setResultString(String resultString) {
/* 95 */     this.resultString = resultString;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.communication.client.InvokeContext
 * JD-Core Version:    0.6.2
 */