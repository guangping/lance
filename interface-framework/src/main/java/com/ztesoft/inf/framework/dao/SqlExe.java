/*      */ package com.ztesoft.inf.framework.dao;
/*      */ 
/*      */ import com.powerise.ibss.framework.FrameException;
/*      */ import com.ztesoft.config.InfConnUtil;
/*      */ import com.ztesoft.ibss.common.dao.DAOUtils;
/*      */ import java.sql.Blob;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.SQLWarning;
/*      */ import java.sql.Statement;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import org.apache.log4j.Logger;
/*      */ import org.springframework.dao.DataAccessException;
/*      */ import org.springframework.jdbc.SQLWarningException;
/*      */ import org.springframework.jdbc.UncategorizedSQLException;
/*      */ import org.springframework.jdbc.core.ParameterDisposer;
/*      */ import org.springframework.jdbc.core.PreparedStatementCallback;
/*      */ import org.springframework.jdbc.core.PreparedStatementCreator;
/*      */ import org.springframework.jdbc.core.PreparedStatementSetter;
/*      */ import org.springframework.jdbc.core.SqlProvider;
/*      */ 
/*      */ public class SqlExe
/*      */ {
/*      */   public Connection conn;
/*   34 */   private static Logger logger = Logger.getLogger(SqlExe.class);
/*      */ 
/*   36 */   private int fetchSize = 0;
/*   37 */   private int maxRows = 0;
/*   38 */   private boolean ignoreWarnings = true;
/*      */ 
/*   66 */   private boolean flag = true;
/*      */ 
/*      */   public boolean isIgnoreWarnings()
/*      */   {
/*   40 */     return this.ignoreWarnings;
/*      */   }
/*      */ 
/*      */   public void setIgnoreWarnings(boolean ignoreWarnings) {
/*   44 */     this.ignoreWarnings = ignoreWarnings;
/*      */   }
/*      */ 
/*      */   public int getFetchSize() {
/*   48 */     return this.fetchSize;
/*      */   }
/*      */ 
/*      */   public void setFetchSize(int fetchSize) {
/*   52 */     this.fetchSize = fetchSize;
/*      */   }
/*      */ 
/*      */   public int getMaxRows() {
/*   56 */     return this.maxRows;
/*      */   }
/*      */ 
/*      */   public void setMaxRows(int maxRows) {
/*   60 */     this.maxRows = maxRows;
/*      */   }
/*      */ 
/*      */   public SqlExe()
/*      */   {
/*      */   }
/*      */ 
/*      */   public SqlExe(Connection conn)
/*      */   {
/*   72 */     this.conn = conn;
/*   73 */     this.flag = false;
/*      */   }
/*      */ 
/*      */   public void setConnection(Connection conn) {
/*   77 */     this.conn = conn;
/*   78 */     this.flag = false;
/*      */   }
/*      */ 
/*      */   private void getPriConn() throws SQLException {
/*   82 */     if ((this.conn == null) || (this.conn.isClosed()))
/*   83 */       this.conn = InfConnUtil.getConnection();
/*      */   }
/*      */ 
/*      */   public String queryForString(String sql)
/*      */     throws FrameException, SQLException
/*      */   {
/*   97 */     getPriConn();
/*   98 */     String retMsg = "";
/*   99 */     PreparedStatement pst = null;
/*  100 */     ResultSet rs = null;
/*      */     try {
/*  102 */       pst = this.conn.prepareStatement(sql);
/*  103 */       rs = pst.executeQuery();
/*  104 */       if ((rs != null) && (rs.next()))
/*  105 */         retMsg = rs.getString(1);
/*      */     }
/*      */     catch (SQLException e) {
/*  108 */       e.printStackTrace();
/*  109 */       throw e;
/*      */     } finally {
/*  111 */       if (rs != null) {
/*  112 */         rs.close();
/*  113 */         rs = null;
/*      */       }
/*  115 */       if (pst != null) {
/*  116 */         pst.close();
/*  117 */         pst = null;
/*      */       }
/*  119 */       closeConnection();
/*      */     }
/*  121 */     return retMsg;
/*      */   }
/*      */ 
/*      */   public String queryForString(String sql, String param)
/*      */     throws FrameException, SQLException
/*      */   {
/*  135 */     getPriConn();
/*  136 */     String retMsg = "";
/*  137 */     PreparedStatement pst = null;
/*  138 */     ResultSet rs = null;
/*      */     try {
/*  140 */       pst = this.conn.prepareStatement(sql);
/*  141 */       pst.setString(1, param);
/*  142 */       rs = pst.executeQuery();
/*  143 */       if ((rs != null) && (rs.next()))
/*  144 */         retMsg = rs.getString(1);
/*      */     }
/*      */     catch (SQLException e) {
/*  147 */       e.printStackTrace();
/*  148 */       throw e;
/*      */     } finally {
/*  150 */       if (rs != null) {
/*  151 */         rs.close();
/*  152 */         rs = null;
/*      */       }
/*  154 */       if (pst != null) {
/*  155 */         pst.close();
/*  156 */         pst = null;
/*      */       }
/*  158 */       closeConnection();
/*      */     }
/*  160 */     return retMsg;
/*      */   }
/*      */ 
/*      */   public String queryForString(String sql, String[] params)
/*      */     throws FrameException, SQLException
/*      */   {
/*  174 */     getPriConn();
/*  175 */     String retMsg = "";
/*  176 */     PreparedStatement pst = null;
/*  177 */     ResultSet rs = null;
/*      */     try {
/*  179 */       pst = this.conn.prepareStatement(sql);
/*  180 */       for (int i = 0; i < params.length; i++) {
/*  181 */         pst.setString(i + 1, params[i]);
/*      */       }
/*  183 */       rs = pst.executeQuery();
/*  184 */       if ((rs != null) && (rs.next()))
/*  185 */         retMsg = rs.getString(1);
/*      */     }
/*      */     catch (SQLException e) {
/*  188 */       e.printStackTrace();
/*  189 */       throw e;
/*      */     } finally {
/*  191 */       if (rs != null) {
/*  192 */         rs.close();
/*  193 */         rs = null;
/*      */       }
/*  195 */       if (pst != null) {
/*  196 */         pst.close();
/*  197 */         pst = null;
/*      */       }
/*  199 */       closeConnection();
/*      */     }
/*  201 */     return retMsg;
/*      */   }
/*      */ 
/*      */   public HashMap queryForMap(String sql, String param)
/*      */     throws FrameException, SQLException
/*      */   {
/*  215 */     getPriConn();
/*  216 */     HashMap retMap = new HashMap();
/*  217 */     PreparedStatement pst = null;
/*  218 */     ResultSet rs = null;
/*      */     try {
/*  220 */       pst = this.conn.prepareStatement(sql);
/*  221 */       pst.setString(1, param);
/*  222 */       rs = pst.executeQuery();
/*  223 */       if ((rs != null) && (rs.next()))
/*  224 */         retMap = convertToMap(rs);
/*      */     }
/*      */     catch (SQLException e) {
/*  227 */       e.printStackTrace();
/*  228 */       throw e;
/*      */     } finally {
/*  230 */       if (rs != null) {
/*  231 */         rs.close();
/*  232 */         rs = null;
/*      */       }
/*  234 */       if (pst != null) {
/*  235 */         pst.close();
/*  236 */         pst = null;
/*      */       }
/*  238 */       closeConnection();
/*      */     }
/*  240 */     return retMap;
/*      */   }
/*      */ 
/*      */   public HashMap queryForMap(String sql)
/*      */     throws FrameException, SQLException
/*      */   {
/*  254 */     getPriConn();
/*  255 */     HashMap retMap = new HashMap();
/*  256 */     PreparedStatement pst = null;
/*  257 */     ResultSet rs = null;
/*      */     try {
/*  259 */       pst = this.conn.prepareStatement(sql);
/*  260 */       rs = pst.executeQuery();
/*  261 */       if ((rs != null) && (rs.next()))
/*  262 */         retMap = convertToMap(rs);
/*      */     }
/*      */     catch (SQLException e) {
/*  265 */       e.printStackTrace();
/*  266 */       throw e;
/*      */     } finally {
/*  268 */       if (rs != null) {
/*  269 */         rs.close();
/*  270 */         rs = null;
/*      */       }
/*  272 */       if (pst != null) {
/*  273 */         pst.close();
/*  274 */         pst = null;
/*      */       }
/*  276 */       closeConnection();
/*      */     }
/*  278 */     return retMap;
/*      */   }
/*      */ 
/*      */   public HashMap queryForMap(String sql, List params)
/*      */     throws FrameException, SQLException
/*      */   {
/*  292 */     getPriConn();
/*  293 */     HashMap retMap = new HashMap();
/*  294 */     PreparedStatement pst = null;
/*  295 */     ResultSet rs = null;
/*      */     try {
/*  297 */       pst = this.conn.prepareStatement(sql);
/*  298 */       for (int i = 0; i < params.size(); i++) {
/*  299 */         pst.setObject(i + 1, params.get(i));
/*      */       }
/*  301 */       rs = pst.executeQuery();
/*  302 */       if ((rs != null) && (rs.next()))
/*  303 */         retMap = convertToMap(rs);
/*      */     }
/*      */     catch (SQLException e) {
/*  306 */       e.printStackTrace();
/*  307 */       throw e;
/*      */     } finally {
/*  309 */       if (rs != null) {
/*  310 */         rs.close();
/*  311 */         rs = null;
/*      */       }
/*  313 */       if (pst != null) {
/*  314 */         pst.close();
/*  315 */         pst = null;
/*      */       }
/*  317 */       closeConnection();
/*      */     }
/*  319 */     return retMap;
/*      */   }
/*      */ 
/*      */   public HashMap queryForMap(String sql, String[] args)
/*      */     throws FrameException, SQLException
/*      */   {
/*  333 */     getPriConn();
/*  334 */     HashMap retMap = new HashMap();
/*  335 */     PreparedStatement pst = null;
/*  336 */     ResultSet rs = null;
/*      */     try {
/*  338 */       pst = this.conn.prepareStatement(sql);
/*  339 */       for (int i = 0; i < args.length; i++) {
/*  340 */         pst.setString(i + 1, args[i]);
/*      */       }
/*  342 */       rs = pst.executeQuery();
/*  343 */       if ((rs != null) && (rs.next()))
/*  344 */         retMap = convertToMap(rs);
/*      */     }
/*      */     catch (SQLException e) {
/*  347 */       e.printStackTrace();
/*  348 */       throw e;
/*      */     } finally {
/*  350 */       if (rs != null) {
/*  351 */         rs.close();
/*  352 */         rs = null;
/*      */       }
/*  354 */       if (pst != null) {
/*  355 */         pst.close();
/*  356 */         pst = null;
/*      */       }
/*  358 */       closeConnection();
/*      */     }
/*  360 */     return retMap;
/*      */   }
/*      */ 
/*      */   public List queryForList(String sql)
/*      */     throws FrameException, SQLException
/*      */   {
/*  373 */     getPriConn();
/*  374 */     PreparedStatement pst = null;
/*  375 */     ResultSet rs = null;
/*      */     try {
/*  377 */       pst = this.conn.prepareStatement(sql);
/*  378 */       rs = pst.executeQuery();
/*  379 */       List results = new ArrayList();
/*  380 */       while ((rs != null) && (rs.next())) {
/*  381 */         results.add(convertToMap(rs));
/*      */       }
/*  383 */       return results;
/*      */     } catch (SQLException e) {
/*  385 */       e.printStackTrace();
/*  386 */       throw e;
/*      */     } finally {
/*  388 */       if (rs != null) {
/*  389 */         rs.close();
/*  390 */         rs = null;
/*      */       }
/*  392 */       if (pst != null) {
/*  393 */         pst.close();
/*  394 */         pst = null;
/*      */       }
/*  396 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   public List queryForList(String sql, String[] args)
/*      */     throws FrameException, SQLException
/*      */   {
/*  411 */     getPriConn();
/*  412 */     PreparedStatement pst = null;
/*  413 */     ResultSet rs = null;
/*      */     try {
/*  415 */       pst = this.conn.prepareStatement(sql);
/*  416 */       for (int i = 0; i < args.length; i++) {
/*  417 */         pst.setString(i + 1, args[i]);
/*      */       }
/*  419 */       rs = pst.executeQuery();
/*  420 */       List results = new ArrayList();
/*      */ 
/*  422 */       while ((rs != null) && (rs.next())) {
/*  423 */         results.add(convertToMap(rs));
/*      */       }
/*  425 */       return results;
/*      */     } catch (SQLException e) {
/*  427 */       e.printStackTrace();
/*  428 */       throw e;
/*      */     } finally {
/*  430 */       if (rs != null) {
/*  431 */         rs.close();
/*  432 */         rs = null;
/*      */       }
/*  434 */       if (pst != null) {
/*  435 */         pst.close();
/*  436 */         pst = null;
/*      */       }
/*  438 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   public List queryForList(String sql, String param)
/*      */     throws FrameException, SQLException
/*      */   {
/*  453 */     getPriConn();
/*  454 */     PreparedStatement pst = null;
/*  455 */     ResultSet rs = null;
/*      */     try {
/*  457 */       pst = this.conn.prepareStatement(sql);
/*  458 */       pst.setString(1, param);
/*  459 */       rs = pst.executeQuery();
/*  460 */       List results = new ArrayList();
/*  461 */       while ((rs != null) && (rs.next())) {
/*  462 */         results.add(convertToMap(rs));
/*      */       }
/*  464 */       return results;
/*      */     } catch (SQLException e) {
/*  466 */       e.printStackTrace();
/*  467 */       throw e;
/*      */     } finally {
/*  469 */       if (rs != null) {
/*  470 */         rs.close();
/*  471 */         rs = null;
/*      */       }
/*  473 */       if (pst != null) {
/*  474 */         pst.close();
/*  475 */         pst = null;
/*      */       }
/*  477 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean execUpdateForList(String sql, List list)
/*      */     throws FrameException, SQLException
/*      */   {
/*  492 */     getPriConn();
/*  493 */     PreparedStatement pst = null;
/*      */     try {
/*  495 */       pst = this.conn.prepareStatement(sql);
/*  496 */       for (int i = 0; i < list.size(); i++) {
/*  497 */         pst.setObject(i + 1, list.get(i));
/*      */       }
/*  499 */       boolean retFlag = pst.executeUpdate() > 0;
/*  500 */       if (this.flag) {
/*  501 */         logger.info("提交事务.........");
/*  502 */         this.conn.commit();
/*      */       }
/*  504 */       return retFlag;
/*      */     } catch (SQLException e) {
/*  506 */       logger.debug("执行更新出错：" + e.getMessage());
/*  507 */       this.conn.rollback();
/*  508 */       e.printStackTrace();
/*  509 */       throw e;
/*      */     } finally {
/*  511 */       if (pst != null) {
/*  512 */         pst.close();
/*  513 */         pst = null;
/*      */       }
/*  515 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean execUpdateForList(String sql, String param)
/*      */     throws FrameException, SQLException
/*      */   {
/*  530 */     getPriConn();
/*  531 */     PreparedStatement pst = null;
/*      */     try {
/*  533 */       pst = this.conn.prepareStatement(sql);
/*  534 */       pst.setObject(1, param);
/*  535 */       boolean retFlag = pst.executeUpdate() > 0;
/*  536 */       if (this.flag) {
/*  537 */         logger.info("提交事务.........");
/*  538 */         this.conn.commit();
/*      */       }
/*  540 */       return retFlag;
/*      */     } catch (SQLException e) {
/*  542 */       logger.debug("执行更新出错：" + e.getMessage());
/*  543 */       this.conn.rollback();
/*  544 */       e.printStackTrace();
/*  545 */       throw e;
/*      */     } finally {
/*  547 */       if (pst != null) {
/*  548 */         pst.close();
/*  549 */         pst = null;
/*      */       }
/*  551 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean execUpdateForList(String sql)
/*      */     throws FrameException, SQLException
/*      */   {
/*  566 */     getPriConn();
/*  567 */     PreparedStatement pst = null;
/*      */     try {
/*  569 */       pst = this.conn.prepareStatement(sql);
/*  570 */       boolean retFlag = pst.executeUpdate() > 0;
/*  571 */       if (this.flag) {
/*  572 */         logger.info("提交事务.........");
/*  573 */         this.conn.commit();
/*      */       }
/*  575 */       return retFlag;
/*      */     } catch (SQLException e) {
/*  577 */       logger.debug("执行更新出错：" + e.getMessage());
/*  578 */       this.conn.rollback();
/*  579 */       e.printStackTrace();
/*  580 */       throw e;
/*      */     } finally {
/*  582 */       if (pst != null) {
/*  583 */         pst.close();
/*  584 */         pst = null;
/*      */       }
/*  586 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean execUpdateForList(String sql, String[] params)
/*      */     throws FrameException, SQLException
/*      */   {
/*  601 */     getPriConn();
/*  602 */     PreparedStatement pst = null;
/*      */     try {
/*  604 */       pst = this.conn.prepareStatement(sql);
/*  605 */       for (int i = 0; i < params.length; i++) {
/*  606 */         pst.setString(i + 1, params[i]);
/*      */       }
/*  608 */       boolean retFlag = pst.executeUpdate() > 0;
/*  609 */       if (this.flag) {
/*  610 */         logger.info("提交事务.........");
/*  611 */         this.conn.commit();
/*      */       }
/*  613 */       return retFlag;
/*      */     } catch (SQLException e) {
/*  615 */       logger.debug("执行更新出错：" + e.getMessage());
/*  616 */       this.conn.rollback();
/*  617 */       e.printStackTrace();
/*  618 */       throw e;
/*      */     } finally {
/*  620 */       if (pst != null) {
/*  621 */         pst.close();
/*  622 */         pst = null;
/*      */       }
/*  624 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean execBatchForListExt(String sql, List list)
/*      */     throws FrameException, SQLException
/*      */   {
/*  639 */     boolean flag = true;
/*  640 */     getPriConn();
/*  641 */     PreparedStatement pst = null;
/*      */     try {
/*  643 */       pst = this.conn.prepareStatement(sql);
/*  644 */       for (int i = 0; i < list.size(); i++) {
/*  645 */         List args = (List)list.get(i);
/*  646 */         for (int j = 0; j < args.size(); j++) {
/*  647 */           pst.setObject(j + 1, args.get(j));
/*      */         }
/*  649 */         pst.addBatch();
/*      */       }
/*  651 */       pst.executeBatch();
/*  652 */       if (flag) {
/*  653 */         logger.info("提交事务.........");
/*  654 */         this.conn.commit();
/*      */       }
/*      */     } catch (SQLException e) {
/*  657 */       logger.debug("执行更新出错：" + e.getMessage());
/*  658 */       flag = false;
/*  659 */       this.conn.rollback();
/*  660 */       e.printStackTrace();
/*  661 */       throw e;
/*      */     } finally {
/*  663 */       if (pst != null) {
/*  664 */         pst.close();
/*  665 */         pst = null;
/*      */       }
/*  667 */       closeConnection();
/*      */     }
/*  669 */     return flag;
/*      */   }
/*      */ 
/*      */   public boolean execBatchForList(String sql, List list)
/*      */     throws FrameException, SQLException
/*      */   {
/*  683 */     getPriConn();
/*  684 */     PreparedStatement pst = null;
/*      */     try {
/*  686 */       pst = this.conn.prepareStatement(sql);
/*  687 */       for (int i = 0; i < list.size(); i++) {
/*  688 */         Object[] args = (Object[])list.get(i);
/*  689 */         for (int j = 0; j < args.length; j++) {
/*  690 */           pst.setObject(j + 1, args[j]);
/*      */         }
/*  692 */         pst.addBatch();
/*      */       }
/*  694 */       pst.executeBatch();
/*  695 */       if (this.flag) {
/*  696 */         logger.info("提交事务.........");
/*  697 */         this.conn.commit();
/*      */       }
/*      */     } catch (SQLException e) {
/*  700 */       logger.debug("执行更新出错：" + e.getMessage());
/*  701 */       this.flag = false;
/*  702 */       this.conn.rollback();
/*  703 */       e.printStackTrace();
/*  704 */       throw e;
/*      */     } finally {
/*  706 */       if (pst != null) {
/*  707 */         pst.close();
/*  708 */         pst = null;
/*      */       }
/*  710 */       closeConnection();
/*      */     }
/*  712 */     return this.flag;
/*      */   }
/*      */ 
/*      */   public static HashMap convertToMap(ResultSet rs) throws SQLException {
/*  716 */     HashMap retMap = new HashMap();
/*  717 */     ResultSetMetaData meatData = rs.getMetaData();
/*  718 */     for (int i = 1; i <= meatData.getColumnCount(); i++) {
/*  719 */       String name = meatData.getColumnLabel(i).toLowerCase();
/*  720 */       int type = meatData.getColumnType(i);
/*  721 */       if ((92 == type) || (93 == type)) {
/*  722 */         String value = DAOUtils.getFormatedDateTime(rs.getTimestamp(name));
/*  723 */         retMap.put(name, value);
/*  724 */       } else if (12 == type) {
/*  725 */         retMap.put(name, rs.getString(name));
/*  726 */       } else if ((4 == type) || (-5 == type)) {
/*  727 */         retMap.put(name, String.valueOf(rs.getInt(name)));
/*  728 */       } else if ((-5 == type) || (2 == type)) {
/*  729 */         retMap.put(name, rs.getBigDecimal(name));
/*  730 */       } else if (2004 == type) {
/*  731 */         Blob blob = rs.getBlob(name);
/*  732 */         byte[] values = null;
/*  733 */         if ((blob != null) && (blob.length() > 0L))
/*  734 */           values = blob.getBytes(1L, (int)blob.length());
/*  735 */         retMap.put(name, values);
/*      */       } else {
/*  737 */         Object value = rs.getObject(name);
/*  738 */         retMap.put(name, value);
/*      */       }
/*      */     }
/*  741 */     return retMap;
/*      */   }
/*      */ 
/*      */   public int execUpdateForMap(String sql, Map params)
/*      */     throws SQLException, Exception
/*      */   {
/*  757 */     PreparedStatement pst = null;
/*  758 */     int retInt = 0;
/*      */     try {
/*  760 */       getPriConn();
/*  761 */       sql = replaceOptionSqlByParam(sql, params);
/*  762 */       sql = replaceSqlByParam(sql, params);
/*  763 */       pst = this.conn.prepareStatement(sql);
/*  764 */       retInt = pst.executeUpdate();
/*  765 */       if (this.flag) {
/*  766 */         logger.debug("提交事务....................");
/*  767 */         this.conn.commit();
/*      */       }
/*      */     } catch (SQLException e) {
/*  770 */       logger.debug("执行更新出错：" + e.getMessage());
/*  771 */       e.printStackTrace();
/*  772 */       throw e;
/*      */     } catch (Exception e) {
/*  774 */       logger.debug("执行更新出错：" + e.getMessage());
/*  775 */       e.printStackTrace();
/*  776 */       throw e;
/*      */     } finally {
/*  778 */       DAOUtils.closeStatement(pst, this);
/*  779 */       pst = null;
/*  780 */       closeConnection();
/*      */     }
/*  782 */     return retInt;
/*      */   }
/*      */ 
/*      */   public String replaceOptionSqlByParam(String sql, Map params)
/*      */   {
/*  793 */     String sqlContent = sql.toString();
/*      */ 
/*  795 */     Pattern p = Pattern.compile("\\[[\\S\\s]*?\\]");
/*  796 */     Matcher m = p.matcher(sql);
/*  797 */     while (m.find()) {
/*  798 */       int start = m.start();
/*  799 */       int end = m.end();
/*  800 */       String optionSql = sql.substring(start, end);
/*  801 */       boolean delete = false;
/*  802 */       Pattern pBuf = Pattern.compile("\\u007B\\S+?}");
/*  803 */       Matcher mBuf = pBuf.matcher(optionSql);
/*  804 */       while (mBuf.find()) {
/*  805 */         int startBuf = mBuf.start();
/*  806 */         int endBuf = mBuf.end();
/*  807 */         String variable = optionSql.substring(startBuf, endBuf);
/*      */ 
/*  809 */         variable = variable.substring(1, variable.length() - 1);
/*  810 */         if ((variable.startsWith("%")) || (variable.endsWith("%"))) {
/*  811 */           variable = variable.replace('%', ' ');
/*      */         }
/*  813 */         if ((params.get(variable.toLowerCase()) == null) || ("".equals(params.get(variable.toLowerCase()))))
/*      */         {
/*  815 */           delete = true;
/*  816 */           break;
/*      */         }
/*      */       }
/*      */ 
/*  820 */       if (delete)
/*  821 */         sqlContent = sqlContent.replaceAll(optionSql, "");
/*      */       else {
/*  823 */         sqlContent = sqlContent.replaceAll(optionSql, optionSql.substring(1, optionSql.length() - 1));
/*      */       }
/*      */     }
/*      */ 
/*  827 */     return sqlContent;
/*      */   }
/*      */ 
/*      */   public String replaceSqlByParam(String sql, Map params)
/*      */     throws Exception
/*      */   {
/*  837 */     String queryContentBuf = sql.toString();
/*      */     try
/*      */     {
/*  840 */       Pattern p = Pattern.compile("\\u007B\\S+?}");
/*  841 */       Matcher m = p.matcher(sql);
/*  842 */       while (m.find()) {
/*  843 */         int start = m.start();
/*  844 */         int end = m.end();
/*  845 */         String variable = sql.substring(start, end);
/*      */ 
/*  847 */         variable = variable.substring(2, variable.length() - 1);
/*  848 */         String var = variable;
/*  849 */         if ((variable.startsWith("%")) || (variable.endsWith("%"))) {
/*  850 */           var = variable.replaceAll("%", "");
/*      */         }
/*  852 */         if (!params.containsKey(var.toLowerCase())) {
/*  853 */           throw new RuntimeException("未能找到入参变量：" + var);
/*      */         }
/*      */ 
/*  856 */         Object object = params.get(var.toLowerCase());
/*  857 */         String value = object == null ? "null" : object.toString();
/*  858 */         if (!"null".equals(value)) {
/*  859 */           value = "'" + value + "'";
/*      */         }
/*  861 */         queryContentBuf = queryContentBuf.replaceAll("#\\u007B" + variable + "}", value);
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*  865 */       e.printStackTrace();
/*  866 */       throw e;
/*      */     }
/*  868 */     return queryContentBuf;
/*      */   }
/*      */ 
/*      */   private void closeConnection()
/*      */   {
/*      */     try
/*      */     {
/*  878 */       if ((this.conn != null) && (!this.conn.isClosed()) && (this.flag)) {
/*  879 */         this.conn.close();
/*  880 */         this.conn = null;
/*      */       } else {
/*  882 */         this.conn = null;
/*      */       }
/*      */     } catch (SQLException e) {
/*  885 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/*  888 */         if ((this.conn != null) && (!this.conn.isClosed()) && (this.flag)) {
/*  889 */           this.conn.close();
/*  890 */           this.conn = null;
/*      */         } else {
/*  892 */           this.conn = null;
/*      */         }
/*      */       } catch (SQLException e) {
/*  895 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static void closeConnection(Connection conn)
/*      */   {
/*      */     try
/*      */     {
/*  907 */       if ((conn != null) && (!conn.isClosed())) {
/*  908 */         conn.close();
/*  909 */         conn = null;
/*      */       } else {
/*  911 */         conn = null;
/*      */       }
/*      */     } catch (SQLException e) {
/*  914 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/*  917 */         if ((conn != null) && (!conn.isClosed())) {
/*  918 */           conn.close();
/*  919 */           conn = null;
/*      */         } else {
/*  921 */           conn = null;
/*      */         }
/*      */       } catch (SQLException e) {
/*  924 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public int update(String sql, PreparedStatementSetter pss)
/*      */     throws DataAccessException
/*      */   {
/*  944 */     return update(new SimplePreparedStatementCreator(sql), pss);
/*      */   }
/*      */   private static String getSql(Object sqlProvider) {
/*  947 */     if ((sqlProvider instanceof SqlProvider)) {
/*  948 */       return ((SqlProvider)sqlProvider).getSql();
/*      */     }
/*  950 */     return null;
/*      */   }
/*      */ 
/*      */   protected int update(PreparedStatementCreator psc, final PreparedStatementSetter pss) throws DataAccessException
/*      */   {
/*  955 */     if (logger.isDebugEnabled()) {
/*  956 */       String sql = getSql(psc);
/*  957 */       logger.debug("Executing SQL update" + (sql != null ? " [" + sql + "]" : ""));
/*      */     }
/*      */ 
/*  960 */     Integer result = (Integer)execute(psc, new PreparedStatementCallback()
/*      */     {
/*      */       public Object doInPreparedStatement(PreparedStatement ps) throws SQLException
/*      */       {
/*      */         try {
/*  965 */           if (pss != null) {
/*  966 */             pss.setValues(ps);
/*      */           }
/*  968 */           int rows = ps.executeUpdate();
/*  969 */           if (SqlExe.logger.isDebugEnabled()) {
/*  970 */             SqlExe.logger.debug("SQL update affected " + rows + " rows");
/*      */           }
/*      */ 
/*  973 */           return new Integer(rows);
/*      */         } finally {
/*  975 */           if ((pss instanceof ParameterDisposer))
/*  976 */             ((ParameterDisposer)pss).cleanupParameters();
/*      */         }
/*      */       }
/*      */     });
/*  981 */     return result.intValue();
/*      */   }
/*      */ 
/*      */   private Object execute(PreparedStatementCreator psc, PreparedStatementCallback action) throws DataAccessException
/*      */   {
/*  986 */     PreparedStatement ps = null;
/*      */     try {
/*  988 */       getPriConn();
/*  989 */       ps = psc.createPreparedStatement(this.conn);
/*  990 */       applyStatementSettings(ps);
/*  991 */       PreparedStatement psToUse = ps;
/*  992 */       Object result = action.doInPreparedStatement(psToUse);
/*  993 */       SQLWarning warning = ps.getWarnings();
/*  994 */       throwExceptionOnWarningIfNotIgnoringWarnings(warning);
/*  995 */       if ((result != null) && ((result instanceof Integer)) && 
/*  996 */         (Integer.parseInt(result.toString()) > 0) && (this.flag)) {
/*  997 */         this.conn.commit();
/*      */       }
/*      */ 
/* 1000 */       return result;
/*      */     } catch (SQLException ex) {
/* 1002 */       if ((psc instanceof ParameterDisposer)) {
/* 1003 */         ((ParameterDisposer)psc).cleanupParameters();
/*      */       }
/* 1005 */       String sql = getSql(psc);
/* 1006 */       throw new UncategorizedSQLException("PreparedStatementCallback", sql, ex);
/*      */     } finally {
/* 1008 */       if ((psc instanceof ParameterDisposer)) {
/* 1009 */         ((ParameterDisposer)psc).cleanupParameters();
/*      */       }
/* 1011 */       psc = null;
/* 1012 */       ps = null;
/* 1013 */       closeConnection();
/*      */     }
/*      */   }
/*      */ 
/* 1017 */   protected void applyStatementSettings(Statement stmt) throws SQLException { if (getFetchSize() > 0) {
/* 1018 */       stmt.setFetchSize(getFetchSize());
/*      */     }
/* 1020 */     if (getMaxRows() > 0)
/* 1021 */       stmt.setMaxRows(getMaxRows());
/*      */   }
/*      */ 
/*      */   private void throwExceptionOnWarningIfNotIgnoringWarnings(SQLWarning warning)
/*      */     throws SQLWarningException
/*      */   {
/* 1027 */     if (warning != null)
/* 1028 */       if (isIgnoreWarnings())
/* 1029 */         logger.warn("SQLWarning ignored: " + warning);
/*      */       else
/* 1031 */         throw new SQLWarningException("Warning not ignored", warning);
/*      */   }
/*      */ 
/*      */   private static class SimplePreparedStatementCreator
/*      */     implements PreparedStatementCreator, SqlProvider
/*      */   {
/*      */     private final String sql;
/*      */ 
/*      */     public SimplePreparedStatementCreator(String sql)
/*      */     {
/*  932 */       this.sql = sql;
/*      */     }
/*      */ 
/*      */     public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
/*  936 */       return con.prepareStatement(this.sql);
/*      */     }
/*      */     public String getSql() {
/*  939 */       return this.sql;
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.framework.dao.SqlExe
 * JD-Core Version:    0.6.2
 */