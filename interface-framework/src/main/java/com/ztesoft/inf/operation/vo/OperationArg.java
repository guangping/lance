/*    */ package com.ztesoft.inf.operation.vo;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class OperationArg
/*    */ {
/*    */   private String argType;
/*    */   private String argIndex;
/*    */   private String xpath;
/*    */   private String xmlMapper;
/*    */ 
/*    */   public OperationArg(Map map)
/*    */   {
/* 14 */     this.argType = ((String)map.get("arg_type"));
/* 15 */     this.argIndex = ((BigDecimal)map.get("arg_index") + "");
/* 16 */     this.xpath = ((String)map.get("xpath"));
/* 17 */     this.xmlMapper = ((String)map.get("xml_mapper"));
/*    */   }
/*    */ 
/*    */   public String getArgType()
/*    */   {
/* 22 */     return this.argType;
/*    */   }
/*    */ 
/*    */   public void setArgType(String argType) {
/* 26 */     this.argType = argType;
/*    */   }
/*    */ 
/*    */   public String getArgIndex() {
/* 30 */     return this.argIndex;
/*    */   }
/*    */ 
/*    */   public void setArgIndex(String argIndex) {
/* 34 */     this.argIndex = argIndex;
/*    */   }
/*    */ 
/*    */   public String getXpath() {
/* 38 */     return this.xpath;
/*    */   }
/*    */ 
/*    */   public void setXpath(String xpath) {
/* 42 */     this.xpath = xpath;
/*    */   }
/*    */ 
/*    */   public String getXmlMapper() {
/* 46 */     return this.xmlMapper;
/*    */   }
/*    */ 
/*    */   public void setXmlMapper(String xmlMapper) {
/* 50 */     this.xmlMapper = xmlMapper;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.operation.vo.OperationArg
 * JD-Core Version:    0.6.2
 */