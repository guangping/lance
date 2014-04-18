/*    */ package com.ztesoft.inf.communication.client.vo;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ClientEndPoint
/*    */ {
/*    */   private String endpointId;
/*    */   private String endpointAddress;
/*    */   private String endpointDesc;
/*    */   private Integer timeout;
/*    */   private String type;
/*    */ 
/*    */   public String getType()
/*    */   {
/* 14 */     return this.type;
/*    */   }
/*    */ 
/*    */   public void setType(String type) {
/* 18 */     this.type = type;
/*    */   }
/*    */ 
/*    */   public Integer getTimeout() {
/* 22 */     return this.timeout;
/*    */   }
/*    */ 
/*    */   public void setTimeout(Integer timeout) {
/* 26 */     this.timeout = timeout;
/*    */   }
/*    */ 
/*    */   public ClientEndPoint() {
/*    */   }
/*    */ 
/*    */   public ClientEndPoint(Map map) {
/* 33 */     setValues(map);
/*    */   }
/*    */ 
/*    */   private void setValues(Map map) {
/* 37 */     this.endpointId = ((String)map.get("ep_id"));
/* 38 */     this.endpointAddress = ((String)map.get("ep_address"));
/*    */ 
/* 40 */     this.endpointDesc = ((String)map.get("ep_desc"));
/* 41 */     this.type = ((String)map.get("ep_type"));
/* 42 */     String _timeout = map.get("timeout").toString();
/* 43 */     if (_timeout != null)
/* 44 */       this.timeout = Integer.valueOf(Integer.parseInt(_timeout));
/*    */   }
/*    */ 
/*    */   public String getEndpointId() {
/* 48 */     return this.endpointId;
/*    */   }
/*    */ 
/*    */   public void setEndpointId(String endpointId) {
/* 52 */     this.endpointId = endpointId;
/*    */   }
/*    */ 
/*    */   public String getEndpointAddress() {
/* 56 */     return this.endpointAddress;
/*    */   }
/*    */ 
/*    */   public void setEndpointAddress(String endpointAddress) {
/* 60 */     this.endpointAddress = endpointAddress;
/*    */   }
/*    */ 
/*    */   public String getEndpointDesc() {
/* 64 */     return this.endpointDesc;
/*    */   }
/*    */ 
/*    */   public void setEndpointDesc(String endpointDesc) {
/* 68 */     this.endpointDesc = endpointDesc;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.communication.client.vo.ClientEndPoint
 * JD-Core Version:    0.6.2
 */