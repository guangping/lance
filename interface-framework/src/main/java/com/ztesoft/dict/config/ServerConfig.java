/*    */ package com.ztesoft.dict.config;
/*    */ 
/*    */ public class ServerConfig
/*    */ {
/*  5 */   public static String serverIp = "wapjx.189.cn";
/*  6 */   public static int serverPort = 80;
/*    */   public static final String WEB_CONTEXT = "";
/*    */   public static final String COMMON_REQ_URL = "/BackAgentServlet";
/*    */   public static final String QUERY_REQ_URL = "";
/*    */ 
/*    */   public static String getServerPath()
/*    */   {
/* 13 */     String url = "http://" + serverIp.trim();
/* 14 */     if (!"".equals(Integer.valueOf(serverPort))) {
/* 15 */       url = url + ":" + serverPort;
/*    */     }
/*    */ 
/* 18 */     return url;
/*    */   }
/*    */ 
/*    */   public static String getServerUrl()
/*    */   {
/* 23 */     String url = "http://" + serverIp.trim();
/* 24 */     if (!"".equals(Integer.valueOf(serverPort))) {
/* 25 */       url = url + ":" + serverPort;
/*    */     }
/* 27 */     url = url + "";
/* 28 */     return url;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.dict.config.ServerConfig
 * JD-Core Version:    0.6.2
 */