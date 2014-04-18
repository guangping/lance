/*     */ package com.ztesoft.common.util.web;
/*     */ 
/*     */ import com.ztesoft.common.util.MapUtil;
/*     */ import com.ztesoft.common.util.StringUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.http.Header;
/*     */ import org.apache.http.client.CookieStore;
/*     */ import org.apache.http.cookie.Cookie;
/*     */ import org.apache.http.impl.client.BasicCookieStore;
/*     */ import org.apache.http.impl.cookie.BasicClientCookie;
/*     */ import org.apache.http.message.BasicHeader;
/*     */ 
/*     */ public class SetCookieUtil
/*     */ {
/*     */   public static List<Cookie> assembleCookie(Map<String, String> params)
/*     */   {
/*  22 */     List cookies = new ArrayList();
/*  23 */     if ((params != null) && (!params.isEmpty())) {
/*  24 */       Iterator iterator = params.keySet().iterator();
/*  25 */       while (iterator.hasNext()) {
/*  26 */         String key = (String)iterator.next();
/*  27 */         if (key.startsWith("cookie_")) {
/*  28 */           String name = key.substring(7, key.length());
/*  29 */           String value = (String)params.get(key);
/*  30 */           Cookie cookie = new BasicClientCookie(name, value);
/*  31 */           cookies.add(cookie);
/*  32 */           iterator.remove();
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  37 */     return cookies;
/*     */   }
/*     */ 
/*     */   public static List<Header> assembleHeaders(List<Cookie> cookies) {
/*  41 */     List headers = new ArrayList();
/*     */ 
/*  43 */     headers.add(new BasicHeader("Accept", "text/html, application/xhtml+xml, */*"));
/*     */ 
/*  45 */     headers.add(new BasicHeader("Accept-Encoding", "gzip, deflate"));
/*  46 */     headers.add(new BasicHeader("Accept-Language", "zh-CN"));
/*  47 */     headers.add(new BasicHeader("Connection", "Keep-Alive"));
/*     */ 
/*  49 */     headers.add(new BasicHeader("Cache-Control", "no-cache"));
/*     */ 
/*  51 */     String dot = "";
/*  52 */     StringBuffer cookieBuffer = new StringBuffer();
/*     */ 
/*  54 */     for (Cookie cookie : cookies) {
/*  55 */       if (cookieBuffer.length() > 0)
/*  56 */         cookieBuffer.append(";");
/*  57 */       cookieBuffer.append(dot).append(cookie.getName()).append("=").append(cookie.getValue());
/*     */     }
/*     */ 
/*  61 */     if (cookieBuffer.length() > 0) {
/*  62 */       headers.add(new BasicHeader("Cookie", cookieBuffer.toString()));
/*     */     }
/*     */ 
/*  65 */     return headers;
/*     */   }
/*     */ 
/*     */   public static CookieStore assembleCookieStore(List<Cookie> inCookies) {
/*  69 */     CookieStore cookieStore = new BasicCookieStore();
/*     */ 
/*  71 */     for (Cookie temp : inCookies) {
/*  72 */       cookieStore.addCookie(temp);
/*     */     }
/*     */ 
/*  75 */     return cookieStore;
/*     */   }
/*     */ 
/*     */   public static Map getHttpParams(Map<String, String> params) {
/*  79 */     Map httpParams = new HashMap();
/*     */ 
/*  81 */     Iterator iterator = MapUtil.safe(params).keySet().iterator();
/*  82 */     while (iterator.hasNext()) {
/*  83 */       String key = (String)iterator.next();
/*  84 */       if (!key.startsWith("cookie_")) {
/*  85 */         httpParams.put(key, params.get(key));
/*     */       }
/*     */     }
/*  88 */     return httpParams;
/*     */   }
/*     */ 
/*     */   public static Map<String, String> getCookieParams(Map params) {
/*  92 */     Map cookieParams = new HashMap();
/*     */ 
/*  94 */     Iterator iterator = MapUtil.safe(params).keySet().iterator();
/*  95 */     while (iterator.hasNext()) {
/*  96 */       String key = (String)iterator.next();
/*  97 */       if (key.startsWith("cookie_")) {
/*  98 */         String value = StringUtils.getStrValue(params, key);
/*  99 */         cookieParams.put(key, value);
/*     */       }
/*     */     }
/* 102 */     return cookieParams;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.common.util.web.SetCookieUtil
 * JD-Core Version:    0.6.2
 */