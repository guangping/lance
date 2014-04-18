/*    */ package com.ztesoft.inf.communication.client.vo;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ClientResponse
/*    */ {
/*    */   private String responseId;
/*    */   private String cdataPath;
/*    */   private String transform;
/*    */   private String xmlMapper;
/*    */   private boolean transformFault;
/*    */   private String rspPath;
/*    */   private String rspType;
/*    */ 
/*    */   public ClientResponse()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ClientResponse(Map map)
/*    */   {
/* 19 */     setValues(map);
/*    */   }
/*    */ 
/*    */   private void setValues(Map map) {
/* 23 */     this.responseId = ((String)map.get("rsp_id"));
/* 24 */     this.cdataPath = ((String)map.get("cdata_path"));
/* 25 */     byte[] tpl = (byte[])map.get("trans_tpl");
/* 26 */     if (tpl != null)
/* 27 */       this.transform = new String(tpl);
/*    */     else {
/* 29 */       this.transform = "";
/*    */     }
/* 31 */     byte[] mapper = (byte[])map.get("xml_mapper");
/* 32 */     if (mapper != null)
/* 33 */       this.xmlMapper = new String(mapper);
/*    */     else {
/* 35 */       this.xmlMapper = "";
/*    */     }
/*    */ 
/* 38 */     String str = (String)map.get("trans_fault");
/* 39 */     this.transformFault = ("1".equals(str));
/* 40 */     this.rspPath = ((String)map.get("rsp_classpath"));
/* 41 */     this.rspType = ((String)map.get("rsp_type"));
/*    */   }
/*    */ 
/*    */   public String getResponseId() {
/* 45 */     return this.responseId;
/*    */   }
/*    */ 
/*    */   public void setResponseId(String responseId) {
/* 49 */     this.responseId = responseId;
/*    */   }
/*    */ 
/*    */   public String getCdataPath() {
/* 53 */     return this.cdataPath;
/*    */   }
/*    */ 
/*    */   public void setCdataPath(String subPath) {
/* 57 */     this.cdataPath = subPath;
/*    */   }
/*    */ 
/*    */   public String getTransform() {
/* 61 */     return this.transform;
/*    */   }
/*    */ 
/*    */   public void setTransform(String transform) {
/* 65 */     this.transform = transform;
/*    */   }
/*    */ 
/*    */   public String getXmlMapper() {
/* 69 */     return this.xmlMapper;
/*    */   }
/*    */ 
/*    */   public void setXmlMapper(String xmlMapper) {
/* 73 */     this.xmlMapper = xmlMapper;
/*    */   }
/*    */ 
/*    */   public boolean isTransformFault() {
/* 77 */     return this.transformFault;
/*    */   }
/*    */ 
/*    */   public String getRspType() {
/* 81 */     return this.rspType;
/*    */   }
/*    */ 
/*    */   public void setRspType(String rspType) {
/* 85 */     this.rspType = rspType;
/*    */   }
/*    */ 
/*    */   public String getRspPath() {
/* 89 */     return this.rspPath;
/*    */   }
/*    */ 
/*    */   public void setRspPath(String rspPath) {
/* 93 */     this.rspPath = rspPath;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.communication.client.vo.ClientResponse
 * JD-Core Version:    0.6.2
 */