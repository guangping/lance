/*    */ package com.ztesoft.inf.communication.client;
/*    */ 
/*    */ import org.apache.axis.message.SOAPEnvelope;
/*    */ 
/*    */ public class AsmxWebInvokeContext extends InvokeContext
/*    */ {
/*    */   private SOAPEnvelope requestSOAP;
/*    */   private SOAPEnvelope responseSOAP;
/*    */ 
/*    */   public SOAPEnvelope getRequestSOAP()
/*    */   {
/* 11 */     return this.requestSOAP;
/*    */   }
/*    */ 
/*    */   public void setRequestSOAP(SOAPEnvelope requestSOAP) {
/* 15 */     this.requestSOAP = requestSOAP;
/*    */   }
/*    */ 
/*    */   public SOAPEnvelope getResponseSOAP() {
/* 19 */     return this.responseSOAP;
/*    */   }
/*    */ 
/*    */   public void setResponseSOAP(SOAPEnvelope responseSOAP) {
/* 23 */     this.responseSOAP = responseSOAP;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.communication.client.AsmxWebInvokeContext
 * JD-Core Version:    0.6.2
 */