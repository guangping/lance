/*    */ package com.ztesoft.common.query.util;
/*    */ 
/*    */ import com.ztesoft.inf.framework.dao.SqlExe;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class DcSystemParamUtil
/*    */ {
/* 18 */   public static HashMap sysParamsCache = new HashMap();
/*    */ 
/*    */   public static String getSysParamByCache(String paramCode)
/*    */   {
/* 27 */     if (sysParamsCache.get(paramCode) != null) {
/* 28 */       return sysParamsCache.get(paramCode).toString();
/*    */     }
/* 30 */     String paramVal = getParamValFromDb(paramCode);
/* 31 */     setVariable(paramCode, paramVal);
/* 32 */     return paramVal;
/*    */   }
/*    */ 
/*    */   public static String getParamValFromDb(String paramCode)
/*    */   {
/* 43 */     String sql = "select param_val from dc_system_param where param_code ='" + paramCode + "'";
/*    */ 
/* 45 */     String paramVal = "";
/*    */     try {
/* 47 */       paramVal = new SqlExe().queryForString(sql);
/* 48 */       setVariable(paramCode, paramVal);
/*    */     }
/*    */     catch (Exception e) {
/*    */     }
/* 52 */     return paramVal;
/*    */   }
/*    */ 
/*    */   private static void setVariable(String key, String value)
/*    */   {
/* 62 */     sysParamsCache.put(key, value);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.common.query.util.DcSystemParamUtil
 * JD-Core Version:    0.6.2
 */