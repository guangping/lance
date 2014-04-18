/*    */ package com.ztesoft.common.util.web;
/*    */ 
/*    */ import com.ztesoft.common.util.StringUtils;
/*    */ import java.io.PrintStream;
/*    */ import org.apache.commons.httpclient.HttpClient;
/*    */ import org.apache.commons.httpclient.HttpConnectionManager;
/*    */ import org.apache.commons.httpclient.HttpMethod;
/*    */ import org.apache.commons.httpclient.methods.GetMethod;
/*    */ import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
/*    */ 
/*    */ public class HttpUtils
/*    */ {
/*    */   public static String getContentByUrl(String url, Integer timeout)
/*    */     throws Exception
/*    */   {
/* 16 */     HttpClient client = new HttpClient();
/* 17 */     HttpMethod method = null;
/* 18 */     String content = "";
/*    */     try
/*    */     {
/* 21 */       HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();
/*    */ 
/* 23 */       if (timeout != null)
/*    */       {
/* 25 */         managerParams.setConnectionTimeout(timeout.intValue() * 1000);
/*    */ 
/* 27 */         managerParams.setSoTimeout(timeout.intValue() * 1000);
/*    */       }
/* 29 */       method = new GetMethod(url);
/* 30 */       client.executeMethod(method);
/* 31 */       content = method.getResponseBodyAsString();
/*    */     }
/*    */     catch (Exception e) {
/* 34 */       StringUtils.printInfo("获取请求URL失败：" + url + "\n" + e.getMessage());
/* 35 */       throw e;
/*    */     } finally {
/*    */       try {
/* 38 */         if (method != null)
/* 39 */           method.releaseConnection();
/* 40 */         client.getHttpConnectionManager().closeIdleConnections(0L);
/*    */       }
/*    */       catch (Exception e) {
/*    */       }
/*    */     }
/* 45 */     StringUtils.printInfo("获取请求URL返回数据：" + url + "\n content = " + content);
/* 46 */     return content;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) throws Exception {
/* 50 */     System.out.println(getContentByUrl("http://127.0.0.1:8081/nexus/index.html", Integer.valueOf(10)));
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.common.util.web.HttpUtils
 * JD-Core Version:    0.6.2
 */