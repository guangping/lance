/*    */ package com.ztesoft.inf.communication.client;
/*    */ 
/*    */ import com.ztesoft.common.util.JsonUtil;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class HttpJsonInvoker extends HttpClientInvoker
/*    */ {
/*    */   protected String generateRequestString(InvokeContext context)
/*    */   {
/* 12 */     Object params = context.getParameters();
/* 13 */     String str = JsonUtil.toJson(params);
/* 14 */     this.logger.debug("json===>" + str);
/*    */ 
/* 16 */     return str;
/*    */   }
/*    */ 
/*    */   protected Object dealResult(InvokeContext context)
/*    */   {
/* 21 */     String resultStr = context.getResponeString();
/* 22 */     Map result = (Map)JsonUtil.fromJson(resultStr, Map.class);
/* 23 */     this.logger.debug("result===>" + result.toString());
/* 24 */     return result;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.communication.client.HttpJsonInvoker
 * JD-Core Version:    0.6.2
 */