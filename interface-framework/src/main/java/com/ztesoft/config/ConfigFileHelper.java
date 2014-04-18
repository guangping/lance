/*    */ package com.ztesoft.config;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class ConfigFileHelper
/*    */ {
/*    */   private static final String params_properties = "config/inf.properties";
/*    */   private static final String log4j_properties = "log4j.properties";
/*    */   private static final String Resources_Informix_properties = "Resources_Informix.properties";
/*    */   private static final String Resources_ORA_properties = "Resources_ORA.properties";
/*    */ 
/*    */   private static InputStream getFileInputStream(String fileName)
/*    */     throws Exception
/*    */   {
/* 29 */     return ConfigFileHelper.class.getClassLoader().getResourceAsStream(fileName);
/*    */   }
/*    */ 
/*    */   public static Properties getConfigFileProperties(String fileName)
/*    */     throws Exception
/*    */   {
/* 35 */     InputStream is = getFileInputStream(fileName);
/* 36 */     Properties configFile = new Properties();
/* 37 */     configFile.load(is);
/* 38 */     is.close();
/*    */ 
/* 40 */     return configFile;
/*    */   }
/*    */ 
/*    */   public static Properties getInfParamConfigFile() throws Exception {
/* 44 */     return getConfigFileProperties("config/inf.properties");
/*    */   }
/*    */ 
/*    */   public static Properties getLog4JConfigFile() throws Exception {
/* 48 */     return getConfigFileProperties("log4j.properties");
/*    */   }
/*    */ 
/*    */   public static Properties getInformixDBConfigFile() throws Exception {
/* 52 */     return getConfigFileProperties("Resources_Informix.properties");
/*    */   }
/*    */ 
/*    */   public static Properties getOracleDBConfigFile() throws Exception {
/* 56 */     return getConfigFileProperties("Resources_ORA.properties");
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.config.ConfigFileHelper
 * JD-Core Version:    0.6.2
 */