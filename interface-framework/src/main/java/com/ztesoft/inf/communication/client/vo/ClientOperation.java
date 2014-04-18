/*    */ package com.ztesoft.inf.communication.client.vo;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ClientOperation
/*    */ {
/*    */   private String operationId;
/*    */   private String operationCode;
/*    */   private String endpointId;
/*    */   private String requestId;
/*    */   private String responseId;
/*    */   private String description;
/*    */   private String logLevel;
/*    */   private String reqUserId;
/*    */   private boolean closed;
/*    */ 
/*    */   public ClientOperation()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ClientOperation(Map map)
/*    */   {
/* 21 */     setValues(map);
/*    */   }
/*    */ 
/*    */   private void setValues(Map map) {
/* 25 */     this.operationId = ((String)map.get("op_id"));
/* 26 */     this.operationCode = ((String)map.get("op_code"));
/* 27 */     this.endpointId = ((String)map.get("ep_id"));
/* 28 */     this.requestId = ((String)map.get("req_id"));
/* 29 */     this.responseId = ((String)map.get("rsp_id"));
/* 30 */     this.description = ((String)map.get("op_desc"));
/* 31 */     this.logLevel = ((String)map.get("log_level"));
/* 32 */     this.closed = "T".equals(map.get("close_flag"));
/* 33 */     this.reqUserId = ((String)((map.get("req_user_id") == null) || ("".equals(map.get("req_user_id"))) ? "-1" : map.get("req_user_id")));
/*    */   }
/*    */ 
/*    */   public String getOperationId() {
/* 37 */     return this.operationId;
/*    */   }
/*    */ 
/*    */   public void setOperationId(String operationId) {
/* 41 */     this.operationId = operationId;
/*    */   }
/*    */ 
/*    */   public String getOperationCode() {
/* 45 */     return this.operationCode;
/*    */   }
/*    */ 
/*    */   public void setOperationCode(String operationCode) {
/* 49 */     this.operationCode = operationCode;
/*    */   }
/*    */ 
/*    */   public String getEndpointId() {
/* 53 */     return this.endpointId;
/*    */   }
/*    */ 
/*    */   public void setEndpointId(String endpointId) {
/* 57 */     this.endpointId = endpointId;
/*    */   }
/*    */ 
/*    */   public String getRequestId() {
/* 61 */     return this.requestId;
/*    */   }
/*    */ 
/*    */   public void setRequestId(String requestId) {
/* 65 */     this.requestId = requestId;
/*    */   }
/*    */ 
/*    */   public String getResponseId() {
/* 69 */     return this.responseId;
/*    */   }
/*    */ 
/*    */   public void setResponseId(String responseId) {
/* 73 */     this.responseId = responseId;
/*    */   }
/*    */ 
/*    */   public String getDescription() {
/* 77 */     return this.description;
/*    */   }
/*    */ 
/*    */   public void setDescription(String description) {
/* 81 */     this.description = description;
/*    */   }
/*    */ 
/*    */   public String getLogLevel() {
/* 85 */     return this.logLevel;
/*    */   }
/*    */ 
/*    */   public void setLogLevel(String logLevel) {
/* 89 */     this.logLevel = logLevel;
/*    */   }
/*    */ 
/*    */   public boolean isClosed() {
/* 93 */     return this.closed;
/*    */   }
/*    */ 
/*    */   public String getReqUserId() {
/* 97 */     return this.reqUserId;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.communication.client.vo.ClientOperation
 * JD-Core Version:    0.6.2
 */