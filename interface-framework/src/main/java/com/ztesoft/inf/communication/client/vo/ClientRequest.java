/*     */ package com.ztesoft.inf.communication.client.vo;
/*     */ 
/*     */ import java.util.Map;
/*     */ 
/*     */ public class ClientRequest
/*     */ {
/*     */   private String requestId;
/*     */   private String globalVarsId;
/*     */   private String template;
/*     */   private String classPath;
/*     */   private String qnameEncode;
/*     */   private String qname;
/*     */   private String operClassName;
/*     */   private String operMethod;
/*     */ 
/*     */   public ClientRequest()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ClientRequest(Map map)
/*     */   {
/*  20 */     setValues(map);
/*     */   }
/*     */ 
/*     */   private void setValues(Map map) {
/*  24 */     this.requestId = ((String)map.get("req_id"));
/*  25 */     this.globalVarsId = ((String)map.get("gvar_id"));
/*  26 */     this.classPath = ((String)map.get("class_path"));
/*  27 */     this.qnameEncode = ((String)map.get("qname_encode"));
/*  28 */     this.qname = ((String)map.get("qname"));
/*  29 */     this.operClassName = ((String)map.get("oper_classname"));
/*  30 */     this.operMethod = ((String)map.get("oper_method"));
/*  31 */     byte[] tpl = (byte[])map.get("req_tpl");
/*  32 */     if (tpl != null)
/*  33 */       this.template = new String(tpl);
/*     */     else
/*  35 */       this.template = "";
/*     */   }
/*     */ 
/*     */   public String getRequestId()
/*     */   {
/*  40 */     return this.requestId;
/*     */   }
/*     */ 
/*     */   public void setRequestId(String requestId) {
/*  44 */     this.requestId = requestId;
/*     */   }
/*     */ 
/*     */   public String getGlobalVarsId() {
/*  48 */     return this.globalVarsId;
/*     */   }
/*     */ 
/*     */   public void setGlobalVarsId(String globalVarsId) {
/*  52 */     this.globalVarsId = globalVarsId;
/*     */   }
/*     */ 
/*     */   public String getTemplate() {
/*  56 */     return this.template;
/*     */   }
/*     */ 
/*     */   public void setTemplate(String template) {
/*  60 */     this.template = template;
/*     */   }
/*     */ 
/*     */   public String getLogCol()
/*     */   {
/*  65 */     return null;
/*     */   }
/*     */ 
/*     */   public String getClassPath() {
/*  69 */     return this.classPath;
/*     */   }
/*     */ 
/*     */   public void setClassPath(String classPath) {
/*  73 */     this.classPath = classPath;
/*     */   }
/*     */ 
/*     */   public String getQnameEncode() {
/*  77 */     return this.qnameEncode;
/*     */   }
/*     */ 
/*     */   public void setQnameEncode(String qnameEncode) {
/*  81 */     this.qnameEncode = qnameEncode;
/*     */   }
/*     */ 
/*     */   public String getQname() {
/*  85 */     return this.qname;
/*     */   }
/*     */ 
/*     */   public void setQname(String qname) {
/*  89 */     this.qname = qname;
/*     */   }
/*     */ 
/*     */   public String getOperClassName() {
/*  93 */     return this.operClassName;
/*     */   }
/*     */ 
/*     */   public void setOperClassName(String operClassName) {
/*  97 */     this.operClassName = operClassName;
/*     */   }
/*     */ 
/*     */   public String getOperMethod() {
/* 101 */     return this.operMethod;
/*     */   }
/*     */ 
/*     */   public void setOperMethod(String operMethod) {
/* 105 */     this.operMethod = operMethod;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.communication.client.vo.ClientRequest
 * JD-Core Version:    0.6.2
 */