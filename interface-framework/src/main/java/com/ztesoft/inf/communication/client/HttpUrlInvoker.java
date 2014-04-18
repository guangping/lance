/*    */ package com.ztesoft.inf.communication.client;
/*    */ 
/*    */ import com.ztesoft.common.util.StringUtils;
/*    */ import com.ztesoft.common.util.date.DateUtil;
/*    */ import com.ztesoft.common.util.web.HttpUtils;
/*    */ import com.ztesoft.inf.framework.commons.CodedException;
/*    */ 
/*    */ public class HttpUrlInvoker extends Invoker
/*    */ {
/*    */   public Object invoke(InvokeContext context)
/*    */     throws Exception
/*    */   {
/*    */     try
/*    */     {
/* 14 */       context.setEndpoint(this.endpoint);
/* 15 */       context.setRequestTime(DateUtil.currentTime());
/* 16 */       String reqString = generateRequestString(context);
/* 17 */       context.setRequestString(reqString);
/* 18 */       String url = context.getEndpoint() + "?" + reqString;
/* 19 */       context.setResponeString(HttpUtils.getContentByUrl(url, this.timeout));
/* 20 */       StringUtils.printInfo("\n\n reqStr=\n" + reqString + "\n\n rspStr=\n" + context.getResponeString());
/*    */     }
/*    */     catch (Exception e) {
/* 23 */       context.setFailure(e.getMessage());
/* 24 */       throw new CodedException("9003", "HTTP请求[" + context.getOperationCode() + "]失败", e);
/*    */     }
/*    */     finally {
/* 27 */       context.setResponseTime(DateUtil.currentTime());
/*    */     }
/* 29 */     return context.getResponeString();
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.communication.client.HttpUrlInvoker
 * JD-Core Version:    0.6.2
 */