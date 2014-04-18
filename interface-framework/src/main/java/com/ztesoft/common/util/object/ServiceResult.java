/*    */ package com.ztesoft.common.util.object;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class ServiceResult<T>
/*    */   implements Serializable
/*    */ {
/*    */   private String resultCode;
/*    */   private String resultMessage;
/*    */   private T resultObject;
/*    */ 
/*    */   public ServiceResult()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ServiceResult(String resultCode, String resultMessage, T resultObject)
/*    */   {
/* 29 */     this.resultCode = resultCode;
/* 30 */     this.resultMessage = resultMessage;
/* 31 */     this.resultObject = resultObject;
/*    */   }
/*    */ 
/*    */   public String getResultCode()
/*    */   {
/* 40 */     return this.resultCode;
/*    */   }
/*    */ 
/*    */   public void setResultCode(String resultCode)
/*    */   {
/* 51 */     this.resultCode = resultCode;
/*    */   }
/*    */ 
/*    */   public String getResultMessage()
/*    */   {
/* 60 */     return this.resultMessage;
/*    */   }
/*    */ 
/*    */   public void setResultMessage(String resultMessage)
/*    */   {
/* 71 */     this.resultMessage = resultMessage;
/*    */   }
/*    */ 
/*    */   public T getResultObject()
/*    */   {
/* 81 */     return this.resultObject;
/*    */   }
/*    */ 
/*    */   public void setResultObject(T resultObject)
/*    */   {
/* 92 */     this.resultObject = resultObject;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.common.util.object.ServiceResult
 * JD-Core Version:    0.6.2
 */