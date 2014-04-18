/*     */ package com.ztesoft.inf.communication.client.bo;
/*     */ 
/*     */ import com.powerise.ibss.framework.FrameException;
/*     */ import com.ztesoft.common.dao.ConnectionContext;
/*     */ import com.ztesoft.common.dao.DAOSystemException;
/*     */ import com.ztesoft.common.dao.DAOUtils;
/*     */ import com.ztesoft.common.util.StringUtils;
/*     */ import com.ztesoft.framework.sqls.SF;
/*     */ import com.ztesoft.inf.communication.client.vo.ClientEndPoint;
/*     */ import com.ztesoft.inf.communication.client.vo.ClientGlobalVar;
/*     */ import com.ztesoft.inf.communication.client.vo.ClientOperation;
/*     */ import com.ztesoft.inf.communication.client.vo.ClientRequest;
/*     */ import com.ztesoft.inf.communication.client.vo.ClientRequestUser;
/*     */ import com.ztesoft.inf.communication.client.vo.ClientResponse;
/*     */ import com.ztesoft.inf.framework.dao.SeqUtil;
/*     */ import com.ztesoft.inf.framework.dao.SqlExe;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.springframework.jdbc.core.PreparedStatementSetter;
/*     */ 
/*     */ public class CommClientBO
/*     */ {
/*  35 */   private static final String SELECT_OPERATION_BY_CODE = SF.frameworkSql("SELECT_OPERATION_BY_CODE");
/*     */ 
/*  37 */   private static final String SELECT_ENDPOINT = SF.frameworkSql("SELECT_ENDPOINT");
/*     */ 
/*  39 */   private static final String SELECT_REQUEST = SF.frameworkSql("SELECT_REQUEST");
/*     */ 
/*  41 */   private static final String SELECT_RESPONSE = SF.frameworkSql("SELECT_RESPONSE");
/*     */ 
/*  43 */   private static final String SELECT_GLOBAL_VARS = SF.frameworkSql("SELECT_GLOBAL_VARS");
/*     */ 
/*  45 */   private static final String SELECT_LOG_COLS = SF.frameworkSql("SELECT_LOG_COLS");
/*     */ 
/*  47 */   private static final String SELECT_REQUEST_USER = SF.frameworkSql("SELECT_REQUEST_USER");
/*     */ 
/*  51 */   private static final String INSERT_CLIENT_CALLLOG = SF.frameworkSql("INSERT_CLIENT_CALLLOG");
/*     */ 
/*  54 */   private static final String INSERT_INF_CRM_SPS_LOG = SF.frameworkSql("INSERT_INF_CRM_SPS_LOG");
/*     */ 
/*  57 */   private SqlExe sqlExec = new SqlExe();
/*     */ 
/*     */   public Map getLogColsByOpId(String opId) throws FrameException {
/*  60 */     Map logCols = new HashMap();
/*  61 */     List list = null;
/*     */     try {
/*  63 */       list = this.sqlExec.queryForList(SELECT_LOG_COLS, opId);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */     Iterator i$;
/*  67 */     if (list != null) {
/*  68 */       for (i$ = list.iterator(); i$.hasNext(); ) { Object obj = i$.next();
/*  69 */         Map item = (Map)obj;
/*  70 */         logCols.put(item.get("col_name"), item.get("param_key"));
/*     */       }
/*     */     }
/*  73 */     return logCols;
/*     */   }
/*     */ 
/*     */   public ClientOperation getOperationByCode(String operationCode) throws FrameException
/*     */   {
/*  78 */     Map map = null;
/*     */     try {
/*  80 */       map = this.sqlExec.queryForMap(SELECT_OPERATION_BY_CODE, operationCode);
/*     */     } catch (Exception e) {
/*  82 */       e.printStackTrace();
/*     */     }
/*     */ 
/*  85 */     if (map == null)
/*  86 */       return null;
/*  87 */     return new ClientOperation(map);
/*     */   }
/*     */ 
/*     */   public ClientEndPoint getEndPoint(String endpointId) throws FrameException {
/*  91 */     Map map = null;
/*     */     try {
/*  93 */       map = this.sqlExec.queryForMap(SELECT_ENDPOINT, endpointId);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*  98 */     if (map == null)
/*  99 */       return null;
/* 100 */     return new ClientEndPoint(map);
/*     */   }
/*     */ 
/*     */   public ClientRequest getRequest(String requestId) throws FrameException
/*     */   {
/* 105 */     Map map = null;
/*     */     try {
/* 107 */       map = this.sqlExec.queryForMap(SELECT_REQUEST, requestId);
/*     */     }
/*     */     catch (Exception e) {
/*     */     }
/* 111 */     if (map == null) {
/* 112 */       return null;
/*     */     }
/* 114 */     ClientRequest cl = new ClientRequest(map);
/*     */ 
/* 119 */     return cl;
/*     */   }
/*     */ 
/*     */   public ClientResponse getResponse(String responseId) throws FrameException {
/* 123 */     Map map = null;
/*     */     try {
/* 125 */       map = this.sqlExec.queryForMap(SELECT_RESPONSE, responseId);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/* 130 */     if (map == null)
/* 131 */       return null;
/* 132 */     ClientResponse cl = new ClientResponse(map);
/*     */ 
/* 137 */     return cl;
/*     */   }
/*     */ 
/*     */   public ClientRequestUser getRequestUser(String userId) throws FrameException
/*     */   {
/* 142 */     Map map = null;
/*     */     try {
/* 144 */       map = this.sqlExec.queryForMap(SELECT_REQUEST_USER, userId);
/*     */     }
/*     */     catch (Exception e) {
/*     */     }
/* 148 */     if (map == null)
/* 149 */       return new ClientRequestUser();
/* 150 */     return new ClientRequestUser(map);
/*     */   }
/*     */ 
/*     */   public void logCall(final List args) throws Exception {
/* 154 */     if ((args != null) && (args.size() != 15));
/* 157 */     String reqXml = (String)args.get(4);
/* 158 */     if (args.get(4) != null)
/* 159 */       reqXml = args.get(4).toString();
/*     */     else {
/* 161 */       reqXml = "";
/*     */     }
/* 163 */     final ByteArrayInputStream reqStream = new ByteArrayInputStream(reqXml.getBytes("GBK"));
/*     */ 
/* 165 */     args.set(4, reqStream);
/*     */ 
/* 167 */     String rsqXml = (String)args.get(5);
/* 168 */     if (args.get(5) != null)
/* 169 */       rsqXml = args.get(5).toString();
/*     */     else {
/* 171 */       rsqXml = "";
/*     */     }
/* 173 */     final ByteArrayInputStream rspStream = new ByteArrayInputStream(rsqXml.getBytes("GBK"));
/*     */ 
/* 175 */     args.set(5, rspStream);
/* 176 */     String seq = new SeqUtil().getNextSequence("INF_COMM_CLIENT_CALLLOG", "LOG_ID");
/* 177 */     args.add(seq);
/*     */ 
/* 179 */     final int reqLength = reqXml.getBytes("GBK").length;
/* 180 */     final int rsqLength = rsqXml.getBytes("GBK").length;
/*     */     try {
/* 182 */       this.sqlExec.update(INSERT_CLIENT_CALLLOG, new PreparedStatementSetter()
/*     */       {
/*     */         public void setValues(PreparedStatement ps) throws SQLException
/*     */         {
/* 186 */           int i = 0;
/* 187 */           ps.setTimestamp(++i, (Timestamp)args.get(i - 1));
/* 188 */           ps.setTimestamp(++i, (Timestamp)args.get(i - 1));
/* 189 */           ps.setString(++i, (String)args.get(i - 1));
/* 190 */           ps.setString(++i, (String)args.get(i - 1));
/* 191 */           ps.setBinaryStream(++i, reqStream, reqLength);
/* 192 */           ps.setBinaryStream(++i, rspStream, rsqLength);
/* 193 */           ps.setString(++i, (String)args.get(i - 1));
/* 194 */           ps.setString(++i, StringUtils.substr((String)args.get(i - 1), 2000));
/*     */ 
/* 196 */           ps.setString(++i, StringUtils.substr((String)args.get(i - 1), 2000));
/*     */ 
/* 198 */           ps.setString(++i, StringUtils.substr((String)args.get(i - 1), 2000));
/*     */ 
/* 200 */           ps.setString(++i, StringUtils.substr((String)args.get(i - 1), 2000));
/*     */ 
/* 202 */           ps.setString(++i, StringUtils.substr((String)args.get(i - 1), 2000));
/*     */ 
/* 204 */           ps.setString(++i, StringUtils.substr((String)args.get(i - 1), 2000));
/*     */ 
/* 206 */           ps.setString(++i, StringUtils.substr((String)args.get(i - 1), 2000));
/*     */ 
/* 208 */           ps.setString(++i, StringUtils.substr((String)args.get(i - 1), 2000));
/*     */ 
/* 210 */           ps.setString(++i, (String)args.get(i - 1));
/*     */         }
/*     */       });
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public ClientGlobalVar getGlobalVars(String globalVarsId) {
/* 220 */     if ((StringUtils.isEmpty(globalVarsId)) || (StringUtils.isEmpty(globalVarsId.trim())))
/*     */     {
/* 222 */       return null;
/* 223 */     }Map map = null;
/*     */     try {
/* 225 */       map = this.sqlExec.queryForMap(SELECT_GLOBAL_VARS, globalVarsId);
/*     */     }
/*     */     catch (Exception e) {
/*     */     }
/* 229 */     if (map == null)
/* 230 */       return null;
/* 231 */     return new ClientGlobalVar(map);
/*     */   }
/*     */ 
/*     */   public void logINF_CRM_SPS_LOG(final List args) throws Exception
/*     */   {
/* 236 */     String content = (String)args.get(4);
/* 237 */     if ((content != null) && (!"".endsWith(content))) {
/* 238 */       InputStream xml = new ByteArrayInputStream(content.getBytes("GBK"));
/* 239 */       args.add(4, xml);
/*     */     }
/*     */     try {
/* 242 */       this.sqlExec.update(INSERT_INF_CRM_SPS_LOG, new PreparedStatementSetter()
/*     */       {
/*     */         public void setValues(PreparedStatement ps) throws SQLException
/*     */         {
/* 246 */           int i = 0;
/* 247 */           ps.setString(++i, (String)args.get(i - 1));
/* 248 */           ps.setString(++i, (String)args.get(i - 1));
/* 249 */           ps.setString(++i, (String)args.get(i - 1));
/* 250 */           ps.setString(++i, (String)args.get(i - 1));
/* 251 */           InputStream content = (InputStream)args.get(i);
/*     */           int islen;
/*     */           try
/*     */           {
/*     */             int islen;
/* 254 */             if (content != null)
/* 255 */               islen = content.available();
/*     */             else
/* 257 */               islen = 0;
/*     */           }
/*     */           catch (IOException e)
/*     */           {
/* 261 */             throw new RuntimeException(e.getMessage());
/*     */           }
/* 263 */           ps.setBinaryStream(++i, content, islen);
/*     */         }
/*     */       });
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   private String getBlob(String strSql, String id)
/*     */   {
/* 278 */     PreparedStatement stmt = null;
/* 279 */     ResultSet rs = null;
/* 280 */     Connection conn = null;
/*     */     try
/*     */     {
/* 283 */       conn = ConnectionContext.getContext().getConnection("DEFAULT");
/*     */ 
/* 286 */       stmt = conn.prepareStatement(strSql);
/* 287 */       stmt.setString(1, id);
/* 288 */       rs = stmt.executeQuery();
/*     */       Blob blob;
/* 289 */       if (rs.next()) {
/* 290 */         blob = rs.getBlob(1);
/* 291 */         if (blob != null) {
/* 292 */           byte[] bdata = blob.getBytes(1L, (int)blob.length());
/* 293 */           String queryContent = new String(bdata);
/* 294 */           return queryContent;
/*     */         }
/*     */       }
/*     */ 
/* 298 */       return "";
/*     */     }
/*     */     catch (Exception se) {
/* 301 */       throw new DAOSystemException("SQLException while getting blob:\n" + se.getMessage(), se);
/*     */     }
/*     */     finally {
/* 304 */       DAOUtils.closeResultSet(rs, this);
/* 305 */       DAOUtils.closeStatement(stmt, this);
/* 306 */       DAOUtils.closeConnection(conn, this);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.communication.client.bo.CommClientBO
 * JD-Core Version:    0.6.2
 */