/*     */ package com.ztesoft.dict.invoke;
/*     */ 
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.powerise.ibss.framework.DynamicDict;
/*     */ import com.powerise.ibss.framework.FrameException;
/*     */ import com.powerise.ibss.util.Base64;
/*     */ import com.ztesoft.common.util.StringUtils;
/*     */ import com.ztesoft.dict.config.ServerConfig;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.Type;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.HttpResponse;
/*     */ import org.apache.http.StatusLine;
/*     */ import org.apache.http.client.methods.HttpPost;
/*     */ import org.apache.http.client.methods.HttpUriRequest;
/*     */ import org.apache.http.client.params.HttpClientParams;
/*     */ import org.apache.http.conn.ClientConnectionManager;
/*     */ import org.apache.http.conn.HttpHostConnectException;
/*     */ import org.apache.http.conn.scheme.PlainSocketFactory;
/*     */ import org.apache.http.conn.scheme.Scheme;
/*     */ import org.apache.http.conn.scheme.SchemeRegistry;
/*     */ import org.apache.http.conn.ssl.SSLSocketFactory;
/*     */ import org.apache.http.entity.StringEntity;
/*     */ import org.apache.http.impl.client.DefaultHttpClient;
/*     */ import org.apache.http.impl.conn.SingleClientConnManager;
/*     */ import org.apache.http.params.BasicHttpParams;
/*     */ import org.apache.http.params.HttpConnectionParams;
/*     */ import org.apache.http.params.HttpParams;
/*     */ import org.apache.http.params.HttpProtocolParams;
/*     */ 
/*     */ public class Caller
/*     */ {
/*  40 */   private static DefaultHttpClient httpclient = null;
/*     */ 
/*     */   private static synchronized DefaultHttpClient getHttpClient()
/*     */   {
/*  60 */     if (null != httpclient) {
/*  61 */       return httpclient;
/*     */     }
/*     */ 
/*  64 */     int timeoutConnection = 23000;
/*  65 */     int timeoutSocket = 28000;
/*     */ 
/*  67 */     int BUFFER_SIZE = 163840;
/*     */ 
/*  69 */     HttpParams httpParameters = new BasicHttpParams();
/*     */ 
/*  71 */     HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
/*     */ 
/*  73 */     HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
/*  74 */     HttpConnectionParams.setSocketBufferSize(httpParameters, BUFFER_SIZE);
/*     */ 
/*  76 */     SchemeRegistry schemeRegistry = new SchemeRegistry();
/*  77 */     schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
/*     */ 
/*  79 */     schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
/*     */ 
/*  81 */     SingleClientConnManager mgr = new SingleClientConnManager(httpParameters, schemeRegistry);
/*     */ 
/*  84 */     HttpClientParams.setRedirecting(httpParameters, true);
/*  85 */     HttpClientParams.setCookiePolicy(httpParameters, "compatibility");
/*     */ 
/*  87 */     HttpProtocolParams.setUserAgent(httpParameters, "apache.http.client");
/*  88 */     httpclient = new DefaultHttpClient(mgr, httpParameters);
/*  89 */     return httpclient;
/*     */   }
/*     */ 
/*     */   private static synchronized DefaultHttpClient getHttpClient(int timeout) {
/*  93 */     int timeoutConnection = timeout == -1 ? 23000 : timeout * 1000;
/*  94 */     int timeoutSocket = timeout == -1 ? 28000 : timeout * 1000;
/*  95 */     int BUFFER_SIZE = 163840;
/*  96 */     DefaultHttpClient client = null;
/*     */ 
/*  98 */     HttpParams httpParameters = new BasicHttpParams();
/*  99 */     HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
/*     */ 
/* 101 */     HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
/* 102 */     HttpConnectionParams.setSocketBufferSize(httpParameters, BUFFER_SIZE);
/*     */ 
/* 104 */     SchemeRegistry schemeRegistry = new SchemeRegistry();
/* 105 */     schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
/*     */ 
/* 107 */     schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
/*     */ 
/* 109 */     SingleClientConnManager mgr = new SingleClientConnManager(httpParameters, schemeRegistry);
/*     */ 
/* 112 */     HttpClientParams.setRedirecting(httpParameters, true);
/* 113 */     HttpClientParams.setCookiePolicy(httpParameters, "compatibility");
/*     */ 
/* 115 */     HttpProtocolParams.setUserAgent(httpParameters, "apache.http.client");
/* 116 */     client = new DefaultHttpClient(mgr, httpParameters);
/* 117 */     return client;
/*     */   }
/*     */ 
/*     */   public static <T> T invoke(DynamicDict dto, TypeToken<T> rstClass, int timeoutSeconds)
/*     */     throws FrameException
/*     */   {
/* 127 */     if (dto == null) {
/* 128 */       throw new FrameException("Mothod:Caller.invoke param dto can not input null!");
/*     */     }
/*     */ 
/* 132 */     return invokeJsonPost(dto, rstClass, timeoutSeconds);
/*     */   }
/*     */ 
/*     */   public static <T> T invoke(DynamicDict dto, TypeToken<T> rstClass)
/*     */     throws FrameException
/*     */   {
/* 139 */     return invoke(dto, rstClass, -1);
/*     */   }
/*     */ 
/*     */   private static <T> T invokeJsonPost(DynamicDict dto, TypeToken<T> typeToken, int timeoutSeconds)
/*     */     throws FrameException
/*     */   {
/* 146 */     String url = ServerConfig.getServerPath() + "/BackAgentServlet";
/* 147 */     return invokeJsonPost(url, dto, typeToken, timeoutSeconds);
/*     */   }
/*     */ 
/*     */   public static <T> T invokeByUrl(String paramUrl, TypeToken<T> typeToken)
/*     */     throws FrameException
/*     */   {
/* 153 */     String url = ServerConfig.getServerUrl() + "" + paramUrl;
/*     */ 
/* 155 */     return invokeJsonPost(url, null, typeToken, -1);
/*     */   }
/*     */ 
/*     */   private static synchronized <T> T invokeJsonPost(String url, DynamicDict dto, TypeToken<T> typeToken, int timeoutSeconds)
/*     */     throws FrameException
/*     */   {
/* 162 */     DefaultHttpClient client = null;
/* 163 */     HttpEntity entity2 = null;
/* 164 */     HttpPost httpPost = null;
/* 165 */     Object obj2 = null;
/*     */     try {
/* 167 */       httpPost = new HttpPost(url);
/*     */ 
/* 169 */       setHeader(httpPost, "application/json", dto);
/* 170 */       Type targetType = new TypeToken() {  } .getType();
/*     */ 
/* 173 */       String req = Base64.encodeObject(dto);
/* 174 */       HttpEntity entity = new StringEntity(req, "UTF-8");
/*     */ 
/* 177 */       httpPost.setEntity(entity);
/* 178 */       if (timeoutSeconds == -1)
/* 179 */         client = getHttpClient();
/*     */       else {
/* 181 */         client = getHttpClient(timeoutSeconds);
/*     */       }
/* 183 */       HttpResponse response = client.execute(httpPost);
/* 184 */       int statusCode = response.getStatusLine().getStatusCode();
/* 185 */       if ((statusCode != 200) && (statusCode != 204))
/*     */       {
/* 187 */         throw new Exception("HttpStatusCode:" + statusCode);
/* 188 */       }if (statusCode == 408) {
/* 189 */         throw new Exception("网络连接超时");
/*     */       }
/*     */ 
/* 192 */       entity2 = response.getEntity();
/* 193 */       if (entity2 != null) {
/* 194 */         InputStream instream = entity2.getContent();
/* 195 */         String result = StringUtils.convertStreamToString(instream);
/* 196 */         instream.close();
/* 197 */         Object serviceResult = null;
/* 198 */         if (dto != null) {
/* 199 */           Object obj = Base64.decodeToObject(result);
/* 200 */           serviceResult = obj;
/*     */         }
/*     */         else {
/* 203 */           result = result.trim();
/* 204 */           serviceResult = result;
/*     */         }
/*     */ 
/* 213 */         if (null == serviceResult) {
/* 214 */           throwException(result);
/*     */         }
/* 216 */         obj2 = serviceResult;
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 220 */       if (httpPost.isAborted()) {
/* 221 */         httpPost.abort();
/*     */       }
/* 223 */       if ((e instanceof HttpHostConnectException)) {
/* 224 */         throw new FrameException("网络不可用，或服务端正在维护中，请稍后再试");
/*     */       }
/* 226 */       throw new FrameException("请求服务器出错：" + e.getMessage());
/*     */     }
/*     */     finally
/*     */     {
/*     */       try {
/* 231 */         if ((timeoutSeconds != -1) && (client != null))
/* 232 */           client.getConnectionManager().shutdown();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 236 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 239 */     return obj2;
/*     */   }
/*     */ 
/*     */   private static void setHeader(HttpUriRequest httpUriRequest, String accept)
/*     */   {
/* 246 */     setHeader(httpUriRequest, accept, null);
/*     */   }
/*     */ 
/*     */   private static void setHeader(HttpUriRequest httpUriRequest, String accept, DynamicDict dto)
/*     */   {
/* 254 */     if (httpUriRequest == null) {
/* 255 */       return;
/*     */     }
/*     */ 
/* 266 */     if (StringUtils.isEmpty(accept)) {
/* 267 */       accept = "application/json";
/*     */     }
/* 269 */     if (StringUtils.isEqual("application/json", accept)) {
/* 270 */       httpUriRequest.addHeader("content-type", "application/json");
/* 271 */       httpUriRequest.setHeader("Accept", "application/json");
/* 272 */     } else if (StringUtils.isEqual("text/plain", accept)) {
/* 273 */       httpUriRequest.setHeader("Accept", "text/plain");
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void throwException(String result) throws Exception
/*     */   {
/* 279 */     if (result == null)
/*     */     {
/* 281 */       throw new Exception("service response error!");
/*     */     }
/*     */ 
/* 284 */     throw new Exception(result);
/*     */   }
/*     */ 
/*     */   public static final class ACCEPT
/*     */   {
/*     */     public static final String JSON = "application/json";
/*     */     public static final String TEXT = "text/plain";
/*     */   }
/*     */ 
/*     */   public static final class RESULT
/*     */   {
/*     */     public static final String SUCCESS_CODE = "0";
/*     */     public static final String FAIL_CODE = "-1";
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.dict.invoke.Caller
 * JD-Core Version:    0.6.2
 */