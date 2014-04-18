/*    */ package com.ztesoft.inf.framework.utils;
/*    */ 
/*    */ import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
/*    */ import org.apache.commons.httpclient.HttpClient;
/*    */ import org.apache.commons.httpclient.HttpConnectionManager;
/*    */ import org.apache.commons.httpclient.methods.PostMethod;
/*    */ import org.apache.commons.httpclient.methods.StringRequestEntity;
/*    */ import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
/*    */ import org.apache.commons.httpclient.params.HttpMethodParams;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class HttpSendUtils
/*    */ {
/* 21 */   private static Logger logger = Logger.getLogger(HttpSendUtils.class);
/*    */ 
/*    */   public static String post(String url, String data, int timeout)
/*    */   {
/* 25 */     HttpClient client = null;
/* 26 */     PostMethod method = null;
/* 27 */     String rval = null;
/*    */     try {
/* 29 */       client = new HttpClient();
/* 30 */       method = new PostMethod(url);
/*    */ 
/* 32 */       method.getParams().setParameter("http.method.retry-handler", new DefaultHttpMethodRetryHandler());
/*    */ 
/* 34 */       method.getParams().setParameter("http.socket.timeout", new Integer(timeout));
/* 35 */       client.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
/*    */ 
/* 37 */       method.setRequestEntity(new StringRequestEntity(data, "text/html", "utf-8"));
/* 38 */       method.setRequestHeader("Content-type", "text/xml;charset=utf-8");
/* 39 */       int status = client.executeMethod(method);
/* 40 */       logger.debug("请求地址:" + url);
/* 41 */       if (status == 200)
/* 42 */         rval = new String(method.getResponseBody());
/*    */     }
/*    */     catch (Exception e) {
/* 45 */       e.printStackTrace();
/*    */     } finally {
/* 47 */       if (null != client) method.releaseConnection();
/*    */     }
/* 49 */     return rval;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.framework.utils.HttpSendUtils
 * JD-Core Version:    0.6.2
 */