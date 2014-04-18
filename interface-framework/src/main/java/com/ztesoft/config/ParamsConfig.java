/*     */ package com.ztesoft.config;
/*     */ 
/*     */ import java.util.Enumeration;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class ParamsConfig
/*     */ {
/*  11 */   public static boolean CONFIG_LOADED = false;
/*     */   public static final String DB_TYPE_INFORMIX = "INFORMIX";
/*     */   public static final String DB_TYPE_ORACLE = "ORACLE";
/*  16 */   public static String DATABASE_TYPE = "";
/*  17 */   private static Properties params = null;
/*  18 */   private static ParamsConfig paramsConfig = null;
/*     */ 
/*  20 */   public static String WEB_INF_PATH = "CRM_WEB_INF_PATH";
/*     */ 
/*  23 */   public static String SHOW_SQL = "true";
/*     */ 
/*  26 */   public static String SHOW_METHOD_TIME = "true";
/*     */ 
/*  29 */   public static int MAX_PAGE_SIZE = 30;
/*     */ 
/*  32 */   public static int DEFAULT_PAGE_SIZE = 20;
/*     */ 
/*  35 */   public static int MAX_QUERY_SIZE = 3000;
/*     */ 
/*  52 */   public static long MAX_UPLOAD_SIZE = 10000L;
/*     */ 
/*  85 */   public static String[] NOT_FILTER_PAFGES = new String[0];
/*     */ 
/*     */   public static void setMaxQuerySize(String size)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       if ((null != size) && (!"".equals(size)))
/*  40 */         MAX_QUERY_SIZE = Integer.parseInt(size);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static int getMaxQuerySize() {
/*  48 */     return MAX_QUERY_SIZE;
/*     */   }
/*     */ 
/*     */   public static void setMaxUploadSize(String size)
/*     */   {
/*     */     try
/*     */     {
/*  56 */       if ((null != size) && (!"".equals(size)))
/*  57 */         MAX_UPLOAD_SIZE = Long.parseLong(size);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static boolean isDebug()
/*     */   {
/*  71 */     boolean flag = false;
/*  72 */     String isDebug = getInstance().getParamValue("IS_DEBUG");
/*     */ 
/*  74 */     if ("true".equalsIgnoreCase(isDebug)) {
/*  75 */       flag = true;
/*     */     }
/*  77 */     return flag;
/*     */   }
/*     */ 
/*     */   public static long getMaxUploadSize() {
/*  81 */     return MAX_UPLOAD_SIZE;
/*     */   }
/*     */ 
/*     */   public static void setNotFilterPages(String pages)
/*     */   {
/*     */     try
/*     */     {
/*  89 */       if (null != pages)
/*  90 */         NOT_FILTER_PAFGES = pages.split("(,)");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String[] getNotFilterPages()
/*     */   {
/*  99 */     return NOT_FILTER_PAFGES;
/*     */   }
/*     */ 
/*     */   public static String getInterfaceIp()
/*     */   {
/* 109 */     return getInstance().getParamValue("IF_RMI_ADD");
/*     */   }
/*     */ 
/*     */   public static boolean getDebugState()
/*     */   {
/* 119 */     return true;
/*     */   }
/*     */ 
/*     */   public static String getIIOPInterfaceIp()
/*     */   {
/* 128 */     return getInstance().getParamValue("IIOP_RMI_ADD");
/*     */   }
/*     */ 
/*     */   public static String getIomIp()
/*     */   {
/* 137 */     return getInstance().getParamValue("IOM_TACHE_INFO_IP");
/*     */   }
/*     */ 
/*     */   public ParamsConfig() {
/* 141 */     if (params == null) {
/* 142 */       initParams("");
/* 143 */       DATABASE_TYPE = getParamValue("DATABASE_TYPE");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static ParamsConfig getInstance()
/*     */   {
/* 154 */     if (paramsConfig == null) {
/* 155 */       paramsConfig = new ParamsConfig();
/*     */     }
/* 157 */     return paramsConfig;
/*     */   }
/*     */ 
/*     */   public static void initParams(String path)
/*     */   {
/*     */     try
/*     */     {
/* 169 */       params = ConfigFileHelper.getInfParamConfigFile();
/*     */     }
/*     */     catch (Exception e1) {
/* 172 */       e1.printStackTrace();
/*     */     }
/*     */ 
/* 175 */     Enumeration enu = params.keys();
/* 176 */     String key = "";
/* 177 */     String val = "";
/* 178 */     while (enu.hasMoreElements()) {
/* 179 */       key = (String)enu.nextElement();
/* 180 */       val = params.getProperty(key);
/* 181 */       if ((val != null) && (!"".equals(val))) {
/* 182 */         params.put(key, val.trim());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 187 */     if (null != params.getProperty("MAX_UPLOAD_SIZE")) {
/* 188 */       setMaxUploadSize(params.getProperty("MAX_UPLOAD_SIZE"));
/*     */     }
/* 190 */     if (null != params.getProperty("NOT_FILTER_PAFGES")) {
/* 191 */       setNotFilterPages(params.getProperty("NOT_FILTER_PAFGES"));
/*     */     }
/* 193 */     if (null != params.getProperty("SHOW_SQL")) {
/* 194 */       SHOW_SQL = params.getProperty("SHOW_SQL");
/*     */     }
/* 196 */     if (null != params.getProperty("SHOW_METHOD_TIME")) {
/* 197 */       SHOW_METHOD_TIME = params.getProperty("SHOW_METHOD_TIME");
/*     */     }
/* 199 */     if (null != params.getProperty("MAX_QUERY_SIZE"))
/* 200 */       setMaxQuerySize(params.getProperty("MAX_QUERY_SIZE"));
/*     */   }
/*     */ 
/*     */   public String getParamValue(String name)
/*     */   {
/* 212 */     return params.getProperty(name);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 219 */     getInstance(); initParams("D:/workspaces/crmworkspace/VsopWeb/VsopWeb/WEB-INF/");
/*     */   }
/*     */ 
/*     */   public void updateProperty(String paramCode)
/*     */     throws Exception
/*     */   {
/* 228 */     Properties tempProperty = ConfigFileHelper.getInfParamConfigFile();
/* 229 */     Enumeration enu = tempProperty.keys();
/* 230 */     String key = "";
/* 231 */     String val = "";
/* 232 */     while (enu.hasMoreElements()) {
/* 233 */       key = (String)enu.nextElement();
/* 234 */       val = tempProperty.getProperty(key);
/* 235 */       if ((val != null) && (!"".equals(val))) {
/* 236 */         tempProperty.put(key, val.trim());
/*     */       }
/*     */     }
/* 239 */     String paramValue = tempProperty.getProperty(paramCode);
/*     */ 
/* 241 */     if (paramValue != null)
/* 242 */       params.setProperty(paramCode, paramValue);
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.config.ParamsConfig
 * JD-Core Version:    0.6.2
 */