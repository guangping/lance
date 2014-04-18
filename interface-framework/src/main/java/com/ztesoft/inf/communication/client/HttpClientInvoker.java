/*     */ package com.ztesoft.inf.communication.client;
/*     */ 
/*     */ import com.ztesoft.common.util.StringUtils;
/*     */ import com.ztesoft.common.util.date.DateUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Map;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLException;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import javax.net.ssl.SSLSocket;
/*     */ import javax.net.ssl.TrustManager;
/*     */ import javax.net.ssl.X509TrustManager;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.HttpResponse;
/*     */ import org.apache.http.StatusLine;
/*     */ import org.apache.http.client.methods.HttpGet;
/*     */ import org.apache.http.client.methods.HttpPost;
/*     */ import org.apache.http.client.methods.HttpRequestBase;
/*     */ import org.apache.http.client.params.HttpClientParams;
/*     */ import org.apache.http.conn.ClientConnectionManager;
/*     */ import org.apache.http.conn.scheme.Scheme;
/*     */ import org.apache.http.conn.scheme.SchemeRegistry;
/*     */ import org.apache.http.conn.ssl.SSLSocketFactory;
/*     */ import org.apache.http.conn.ssl.X509HostnameVerifier;
/*     */ import org.apache.http.entity.StringEntity;
/*     */ import org.apache.http.impl.client.DefaultHttpClient;
/*     */ import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
/*     */ import org.apache.http.params.BasicHttpParams;
/*     */ import org.apache.http.params.HttpConnectionParams;
/*     */ import org.apache.http.params.HttpParams;
/*     */ import org.apache.http.params.HttpProtocolParams;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class HttpClientInvoker extends Invoker
/*     */ {
/*  47 */   private static DefaultHttpClient httpclient = null;
/*  48 */   protected Logger logger = Logger.getLogger(HttpClientInvoker.class);
/*     */ 
/*     */   public Object invoke(InvokeContext context) throws Exception
/*     */   {
/*  52 */     Object result = null;
/*  53 */     context.setEndpoint(this.endpoint);
/*  54 */     String url = context.getEndpoint();
/*  55 */     context.setRequestTime(DateUtil.currentTime());
/*  56 */     String reqString = generateRequestString(context);
/*  57 */     context.setRequestString(reqString);
/*  58 */     this.logger.debug("====>" + reqString);
/*  59 */     Map params = (Map)context.getParameters();
/*  60 */     String methodType = StringUtils.getStrValue(params, "METHOD_TYPE", "POST");
/*     */ 
/*  63 */     DefaultHttpClient httpClient = getHttpClient(context);
/*  64 */     HttpResponse httpResponse = null;
/*  65 */     HttpRequestBase uriRequest = null;
/*     */ 
/*  67 */     if ("GET".equals(methodType))
/*     */     {
/*  69 */       url = context.getEndpoint() + "?" + reqString;
/*  70 */       uriRequest = new HttpGet(url);
/*     */     }
/*  72 */     else if ("POST".equals(methodType))
/*     */     {
/*  74 */       uriRequest = new HttpPost(url);
/*  75 */       HttpEntity entity = new StringEntity(context.getRequestString(), "UTF-8");
/*     */ 
/*  77 */       ((HttpPost)uriRequest).setEntity(entity);
/*     */     } else {
/*  79 */       new IllegalArgumentException("mothed unsupport");
/*     */     }
/*     */     try
/*     */     {
/*  83 */       httpResponse = httpClient.execute(uriRequest);
/*     */ 
/*  85 */       int statusCode = httpResponse.getStatusLine().getStatusCode();
/*  86 */       if ((statusCode != 200) && (statusCode != 204))
/*     */       {
/*  88 */         throw new Exception("HttpStatusCode:" + statusCode);
/*  89 */       }if (statusCode == 408) {
/*  90 */         throw new Exception("网络连接超时");
/*     */       }
/*     */ 
/*  93 */       HttpEntity entity2 = httpResponse.getEntity();
/*  94 */       if (entity2 != null) {
/*  95 */         InputStream instream = entity2.getContent();
/*  96 */         String rspStr = StringUtils.convertStreamToString(instream);
/*  97 */         this.logger.debug("rspStr====>" + rspStr);
/*  98 */         context.setResponeString(rspStr);
/*  99 */         result = dealResult(context);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 103 */       throw e;
/*     */     } finally {
/* 105 */       uriRequest.releaseConnection();
/*     */     }
/* 107 */     return result;
/*     */   }
/*     */ 
/*     */   public static DefaultHttpClient getHttpClient(InvokeContext context)
/*     */     throws Exception
/*     */   {
/* 113 */     if ((context.getEndpoint().contains("https")) || (context.getEndpoint().contains("HTTPS")))
/*     */     {
/* 115 */       return getHttpsClient();
/*     */     }
/* 117 */     return getHttpClientDefault();
/*     */   }
/*     */ 
/*     */   public static DefaultHttpClient getHttpClientDefault()
/*     */   {
/* 122 */     if (null != httpclient) {
/* 123 */       return httpclient;
/*     */     }
/* 125 */     int timeoutConnection = 23000;
/* 126 */     int timeoutSocket = 28000;
/* 127 */     int BUFFER_SIZE = 163840;
/*     */ 
/* 129 */     HttpParams httpParameters = new BasicHttpParams();
/* 130 */     HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
/*     */ 
/* 132 */     HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
/* 133 */     HttpConnectionParams.setSocketBufferSize(httpParameters, BUFFER_SIZE);
/*     */ 
/* 135 */     HttpClientParams.setRedirecting(httpParameters, true);
/* 136 */     HttpClientParams.setCookiePolicy(httpParameters, "compatibility");
/*     */ 
/* 138 */     HttpProtocolParams.setUserAgent(httpParameters, "apache.http.client");
/* 139 */     httpclient = new DefaultHttpClient(new ThreadSafeClientConnManager(), httpParameters);
/*     */ 
/* 141 */     return httpclient;
/*     */   }
/*     */ 
/*     */   public static DefaultHttpClient getHttpsClient()
/*     */     throws Exception
/*     */   {
/* 148 */     X509TrustManager xtm = new X509TrustManager()
/*     */     {
/*     */       public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
/*     */       {
/*     */       }
/*     */ 
/*     */       public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
/*     */       {
/*     */       }
/*     */ 
/*     */       public X509Certificate[] getAcceptedIssuers()
/*     */       {
/* 160 */         return null;
/*     */       }
/*     */     };
/* 168 */     X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier()
/*     */     {
/*     */       public boolean verify(String arg0, SSLSession arg1)
/*     */       {
/* 172 */         return true;
/*     */       }
/*     */ 
/*     */       public void verify(String arg0, SSLSocket arg1)
/*     */         throws IOException
/*     */       {
/*     */       }
/*     */ 
/*     */       public void verify(String arg0, String[] arg1, String[] arg2)
/*     */         throws SSLException
/*     */       {
/*     */       }
/*     */ 
/*     */       public void verify(String arg0, X509Certificate arg1)
/*     */         throws SSLException
/*     */       {
/*     */       }
/*     */     };
/* 191 */     SSLContext ctx = SSLContext.getInstance("TLS");
/*     */ 
/* 195 */     ctx.init(null, new TrustManager[] { xtm }, null);
/*     */ 
/* 199 */     SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
/*     */ 
/* 201 */     socketFactory.setHostnameVerifier(hostnameVerifier);
/*     */ 
/* 204 */     httpclient = new DefaultHttpClient();
/* 205 */     httpclient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", socketFactory, 443));
/*     */ 
/* 208 */     return httpclient;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.communication.client.HttpClientInvoker
 * JD-Core Version:    0.6.2
 */