/*     */ package com.ztesoft.common.util.sql;
/*     */ 
/*     */ import com.ztesoft.common.util.StringUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class SqlParamUtil
/*     */ {
/*     */   public static final String MISSION_MONTH = "[mission_month]";
/*     */   public static final String MISSION_HOLIDAY = "[mission_holiday]";
/*  18 */   private static List<String> paramList = new ArrayList();
/*     */   public static final String FIND_SQL_OPTIONAL_REXP = "\\[[\\S\\s]*?\\]";
/*     */   public static final String FIND_QUERY_VARIABLE_REXP = "\\u0024\\u007B\\S+?}";
/*     */   public static final String FIND_SQL_REPLACE_REXP = "#\\u007B\\S+?}";
/*     */   public static final String FIND_SQL_WHERE_COND_REXP = "\\[where_cond\\]";
/*     */   public static final String QUERY_SQL = "query_sql";
/*     */   public static final String QUERY_PARAMS = "query_params";
/*     */ 
/*     */   public static String parseAllSingleSqlParam(String sql, String code, String value)
/*     */   {
/*  27 */     while (sql.indexOf(code) >= 0) {
/*  28 */       sql = sql.replace(code, value);
/*     */     }
/*  30 */     return sql;
/*     */   }
/*     */ 
/*     */   public static Map parseSqlContent(String queryContent, Map params, String where_cond)
/*     */     throws Exception
/*     */   {
/*  45 */     Map resultMap = new HashMap();
/*  46 */     List varList = new ArrayList();
/*  47 */     if (params == null)
/*  48 */       params = new HashMap();
/*     */     try
/*     */     {
/*  51 */       queryContent = replaceWhereCond(queryContent, where_cond);
/*     */ 
/*  53 */       queryContent = replaceOptionSqlByParam(queryContent, params);
/*     */ 
/*  56 */       queryContent = replaceSqlByParam(queryContent, params);
/*     */ 
/*  59 */       Pattern p = Pattern.compile("\\u0024\\u007B\\S+?}");
/*  60 */       Matcher m = p.matcher(queryContent);
/*  61 */       while (m.find()) {
/*  62 */         int start = m.start();
/*  63 */         int end = m.end();
/*  64 */         String variable = queryContent.substring(start, end);
/*     */ 
/*  66 */         varList.add(variable.substring(2, variable.length() - 1));
/*     */       }
/*     */ 
/*  70 */       List queryParamsList = new ArrayList();
/*     */ 
/*  72 */       for (int i = 0; i < varList.size(); i++) {
/*  73 */         String varName = (String)varList.get(i);
/*  74 */         String varNameBuf = varName;
/*     */ 
/*  76 */         if ((varName.startsWith("%")) || (varName.endsWith("%"))) {
/*  77 */           varNameBuf = varName.replaceAll("%", "");
/*     */         }
/*     */ 
/*  80 */         String value = StringUtils.getStringFromMap(params, varNameBuf);
/*  81 */         if (StringUtils.isEmpty(value)) {
/*  82 */           throw new RuntimeException("未能找到入参变量：" + varName.toUpperCase());
/*     */         }
/*     */ 
/*  85 */         queryParamsList.add(varName.replaceAll(varNameBuf, params.get(varNameBuf.toLowerCase()).toString()));
/*     */       }
/*     */ 
/*  90 */       resultMap.put("query_sql", queryContent.replaceAll("\\u0024\\u007B\\S+?}", "?"));
/*     */ 
/*  93 */       resultMap.put("query_params", queryParamsList);
/*     */ 
/*  95 */       return resultMap;
/*     */     }
/*     */     catch (Exception e) {
/*  98 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String replaceWhereCond(String queryContent, String where_cond)
/*     */   {
/* 113 */     String result = queryContent.replaceAll("\\[where_cond\\]", where_cond);
/*     */ 
/* 115 */     return result;
/*     */   }
/*     */ 
/*     */   private static String replaceOptionSqlByParam(String queryContent, Map params)
/*     */   {
/* 127 */     String queryContentBuf = queryContent.toString();
/*     */ 
/* 129 */     Pattern p = Pattern.compile("\\[[\\S\\s]*?\\]");
/* 130 */     Matcher m = p.matcher(queryContent);
/* 131 */     while (m.find()) {
/* 132 */       int start = m.start();
/* 133 */       int end = m.end();
/* 134 */       String optionSql = queryContent.substring(start, end);
/*     */ 
/* 139 */       boolean delete = false;
/*     */ 
/* 141 */       Pattern pBuf = Pattern.compile("#\\u007B\\S+?}");
/* 142 */       Matcher mBuf = pBuf.matcher(optionSql);
/* 143 */       while (mBuf.find()) {
/* 144 */         int startBuf = mBuf.start();
/* 145 */         int endBuf = mBuf.end();
/*     */ 
/* 147 */         String variable = optionSql.substring(startBuf, endBuf);
/*     */ 
/* 149 */         variable = variable.substring(2, variable.length() - 1);
/* 150 */         if ((variable.startsWith("%")) || (variable.endsWith("%")))
/*     */         {
/* 152 */           variable = variable.replaceAll("%", "");
/*     */         }
/*     */ 
/* 155 */         String value = StringUtils.getStringFromMap(params, variable);
/* 156 */         if (StringUtils.isEmpty(value)) {
/* 157 */           delete = true;
/* 158 */           break;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 163 */       if (delete)
/*     */       {
/* 165 */         queryContentBuf = StringUtils.replaceAll(queryContentBuf, optionSql, "");
/*     */       }
/*     */       else
/*     */       {
/* 170 */         queryContentBuf = StringUtils.replaceAll(queryContentBuf, optionSql, optionSql.substring(1, optionSql.length() - 1));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 176 */     return queryContentBuf;
/*     */   }
/*     */ 
/*     */   private static String replaceSqlByParam(String queryContent, Map params)
/*     */   {
/* 186 */     String queryContentBuf = queryContent.toString();
/*     */ 
/* 188 */     Pattern p = Pattern.compile("#\\u007B\\S+?}");
/* 189 */     Matcher m = p.matcher(queryContent);
/* 190 */     while (m.find()) {
/* 191 */       int start = m.start();
/* 192 */       int end = m.end();
/* 193 */       String variable = queryContent.substring(start, end);
/*     */ 
/* 196 */       variable = variable.substring(2, variable.length() - 1);
/* 197 */       String variableBuf = variable;
/* 198 */       if ((variable.startsWith("%")) || (variable.endsWith("%")))
/*     */       {
/* 200 */         variableBuf = variable.replaceAll("%", "");
/*     */       }
/* 202 */       String value = StringUtils.getStringFromMap(params, variable);
/* 203 */       if (StringUtils.isEmpty(value)) {
/* 204 */         throw new RuntimeException("未能找到入参变量：" + variableBuf.toUpperCase());
/*     */       }
/*     */ 
/* 209 */       queryContentBuf = queryContentBuf.replaceAll("#\\u007B" + variable + "}", value);
/*     */     }
/*     */ 
/* 213 */     return queryContentBuf;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  21 */     paramList.add("[mission_month]");
/*  22 */     paramList.add("[mission_holiday]");
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.common.util.sql.SqlParamUtil
 * JD-Core Version:    0.6.2
 */