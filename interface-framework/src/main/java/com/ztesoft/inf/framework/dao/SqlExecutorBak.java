/*      */ package com.ztesoft.inf.framework.dao;
/*      */ 
/*      */ import com.ztesoft.common.dao.DAOSystemException;
/*      */ import com.ztesoft.inf.framework.utils.CollectionUtils;
/*      */ import java.lang.reflect.Method;
/*      */ import java.sql.CallableStatement;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.SQLWarning;
/*      */ import java.sql.Statement;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import org.apache.log4j.Logger;
/*      */ import org.springframework.dao.DataAccessException;
/*      */ import org.springframework.dao.InvalidDataAccessApiUsageException;
/*      */ import org.springframework.dao.support.DataAccessUtils;
/*      */ import org.springframework.jdbc.SQLWarningException;
/*      */ import org.springframework.jdbc.UncategorizedSQLException;
/*      */ import org.springframework.jdbc.core.BatchPreparedStatementSetter;
/*      */ import org.springframework.jdbc.core.CallableStatementCallback;
/*      */ import org.springframework.jdbc.core.CallableStatementCreator;
/*      */ import org.springframework.jdbc.core.ColumnMapRowMapper;
/*      */ import org.springframework.jdbc.core.ParameterDisposer;
/*      */ import org.springframework.jdbc.core.PreparedStatementCallback;
/*      */ import org.springframework.jdbc.core.PreparedStatementCreator;
/*      */ import org.springframework.jdbc.core.PreparedStatementSetter;
/*      */ import org.springframework.jdbc.core.ResultReader;
/*      */ import org.springframework.jdbc.core.ResultSetExtractor;
/*      */ import org.springframework.jdbc.core.ResultSetSupportingSqlParameter;
/*      */ import org.springframework.jdbc.core.RowCallbackHandler;
/*      */ import org.springframework.jdbc.core.RowMapper;
/*      */ import org.springframework.jdbc.core.RowMapperResultReader;
/*      */ import org.springframework.jdbc.core.SingleColumnRowMapper;
/*      */ import org.springframework.jdbc.core.SqlOutParameter;
/*      */ import org.springframework.jdbc.core.SqlProvider;
/*      */ import org.springframework.jdbc.core.SqlReturnResultSet;
/*      */ import org.springframework.jdbc.core.SqlReturnType;
/*      */ import org.springframework.jdbc.core.SqlRowSetResultSetExtractor;
/*      */ import org.springframework.jdbc.core.StatementCallback;
/*      */ import org.springframework.jdbc.core.StatementCreatorUtils;
/*      */ import org.springframework.jdbc.support.JdbcUtils;
/*      */ import org.springframework.jdbc.support.KeyHolder;
/*      */ import org.springframework.jdbc.support.rowset.SqlRowSet;
/*      */ 
/*      */ public class SqlExecutorBak
/*      */ {
/*      */   private boolean useStrValue;
/*   53 */   private boolean ignoreWarnings = true;
/*   54 */   private int fetchSize = 0;
/*   55 */   private int maxRows = 0;
/*   56 */   private Logger logger = Logger.getLogger(SqlExecutorBak.class);
/*      */   private Connection conn;
/*      */ 
/*      */   public SqlExecutorBak(String dataSourceName)
/*      */   {
/*   59 */     this(dataSourceName, false);
/*      */   }
/*      */ 
/*      */   public SqlExecutorBak(Connection conn) {
/*   63 */     this.conn = conn;
/*      */   }
/*      */ 
/*      */   public SqlExecutorBak(String dataSourceName, boolean useStrValue) {
/*   67 */     this.useStrValue = useStrValue;
/*      */   }
/*      */ 
/*      */   public void setIgnoreWarnings(boolean ignoreWarnings) {
/*   71 */     this.ignoreWarnings = ignoreWarnings;
/*      */   }
/*      */ 
/*      */   public boolean isIgnoreWarnings() {
/*   75 */     return this.ignoreWarnings;
/*      */   }
/*      */ 
/*      */   public void setFetchSize(int fetchSize) {
/*   79 */     this.fetchSize = fetchSize;
/*      */   }
/*      */ 
/*      */   public int getFetchSize() {
/*   83 */     return this.fetchSize;
/*      */   }
/*      */ 
/*      */   public void setMaxRows(int maxRows) {
/*   87 */     this.maxRows = maxRows;
/*      */   }
/*      */ 
/*      */   public int getMaxRows() {
/*   91 */     return this.maxRows;
/*      */   }
/*      */ 
/*      */   public Object execute(StatementCallback action)
/*      */     throws DataAccessException
/*      */   {
/*   97 */     Statement stmt = null;
/*      */     try {
/*   99 */       stmt = this.conn.createStatement();
/*  100 */       applyStatementSettings(stmt);
/*  101 */       Statement stmtToUse = stmt;
/*  102 */       Object result = action.doInStatement(stmtToUse);
/*  103 */       SQLWarning warning = stmt.getWarnings();
/*  104 */       throwExceptionOnWarningIfNotIgnoringWarnings(warning);
/*  105 */       return result;
/*      */     } catch (SQLException ex) {
/*  107 */       throw new UncategorizedSQLException("StatementCallback", getSql(action), ex);
/*      */     }
/*      */     finally {
/*  110 */       JdbcUtils.closeStatement(stmt);
/*  111 */       JdbcUtils.closeConnection(this.conn);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Object query(final String sql, final ResultSetExtractor rse)
/*      */     throws DataAccessException
/*      */   {
/*  118 */     if (sql == null) {
/*  119 */       throw new InvalidDataAccessApiUsageException("SQL must not be null");
/*      */     }
/*      */ 
/*  125 */     if (this.logger.isDebugEnabled()) {
/*  126 */       this.logger.debug("Executing SQL query [" + sql + "]");
/*      */     }
/*      */ 
/*  144 */     return execute(new StatementCallback()
/*      */     {
/*      */       public Object doInStatement(Statement stmt)
/*      */         throws SQLException
/*      */       {
/*  130 */         ResultSet rs = null;
/*      */         try {
/*  132 */           rs = stmt.executeQuery(sql);
/*  133 */           ResultSet rsToUse = rs;
/*  134 */           return rse.extractData(rsToUse);
/*      */         } finally {
/*  136 */           JdbcUtils.closeResultSet(rs);
/*      */         }
/*      */       }
/*      */ 
/*      */       public String getSql() {
/*  141 */         return sql;
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   public List query(String sql, RowCallbackHandler rch)
/*      */     throws DataAccessException
/*      */   {
/*  149 */     return (List)query(sql, new RowCallbackHandlerResultSetExtractor(rch));
/*      */   }
/*      */ 
/*      */   public List query(String sql, RowMapper rowMapper) throws DataAccessException
/*      */   {
/*  154 */     return query(sql, new RowMapperResultReader(rowMapper));
/*      */   }
/*      */ 
/*      */   public Map queryForMap(String sql) throws DataAccessException {
/*  158 */     return (Map)queryForObject(sql, new LowwerCaseColumnMapRowMapper(this.useStrValue));
/*      */   }
/*      */ 
/*      */   public Object queryForObject(String sql, RowMapper rowMapper)
/*      */     throws DataAccessException
/*      */   {
/*  164 */     List results = query(sql, rowMapper);
/*  165 */     return CollectionUtils.unique(results);
/*      */   }
/*      */ 
/*      */   public Object queryForObject(String sql, Class requiredType) throws DataAccessException
/*      */   {
/*  170 */     return queryForObject(sql, new SingleColumnRowMapper(requiredType));
/*      */   }
/*      */ 
/*      */   public long queryForLong(String sql) throws DataAccessException
/*      */   {
/*  175 */     Number number = (Number)queryForObject(sql, Long.class);
/*  176 */     return number != null ? number.longValue() : 0L;
/*      */   }
/*      */ 
/*      */   public int queryForInt(String sql) throws DataAccessException {
/*  180 */     Number number = (Number)queryForObject(sql, Integer.class);
/*  181 */     return number != null ? number.intValue() : 0;
/*      */   }
/*      */ 
/*      */   public List queryForList(String sql, Class elementType) throws DataAccessException
/*      */   {
/*  186 */     return query(sql, new SingleColumnRowMapper(elementType));
/*      */   }
/*      */ 
/*      */   public List queryForList(String sql) throws DataAccessException {
/*  190 */     return query(sql, new LowwerCaseColumnMapRowMapper(this.useStrValue));
/*      */   }
/*      */ 
/*      */   public SqlRowSet queryForRowSet(String sql) throws DataAccessException {
/*  194 */     return (SqlRowSet)query(sql, new SqlRowSetResultSetExtractor());
/*      */   }
/*      */ 
/*      */   public int update(final String sql) throws DataAccessException {
/*  198 */     if (sql == null) {
/*  199 */       throw new InvalidDataAccessApiUsageException("SQL must not be null");
/*      */     }
/*  201 */     if (this.logger.isDebugEnabled()) {
/*  202 */       this.logger.debug("Executing SQL update [" + sql + "]");
/*      */     }
/*      */ 
/*  218 */     return ((Integer)execute(new StatementCallback()
/*      */     {
/*      */       public Object doInStatement(Statement stmt)
/*      */         throws SQLException
/*      */       {
/*  207 */         int rows = stmt.executeUpdate(sql);
/*  208 */         if (this.this$0.logger.isDebugEnabled()) {
/*  209 */           this.this$0.logger.debug("SQL update affected " + rows + " rows");
/*      */         }
/*  211 */         return new Integer(rows);
/*      */       }
/*      */ 
/*      */       public String getSql() {
/*  215 */         return sql;
/*      */       }
/*      */     })).intValue();
/*      */   }
/*      */ 
/*      */   public int[] batchUpdate(final String[] sql) throws DataAccessException {
/*  222 */     if (sql == null) {
/*  223 */       throw new InvalidDataAccessApiUsageException("SQL must not be null");
/*      */     }
/*  225 */     if (this.logger.isDebugEnabled()) {
/*  226 */       this.logger.debug("Executing SQL batch update of " + sql.length + " statements");
/*      */     }
/*      */ 
/*  259 */     return (int[])execute(new StatementCallback()
/*      */     {
/*      */       private String currSql;
/*      */ 
/*      */       public Object doInStatement(Statement stmt)
/*      */         throws SQLException, DataAccessException
/*      */       {
/*  234 */         int[] rowsAffected = new int[sql.length];
/*  235 */         if (JdbcUtils.supportsBatchUpdates(stmt.getConnection())) {
/*  236 */           for (int i = 0; i < sql.length; i++) {
/*  237 */             this.currSql = sql[i];
/*  238 */             stmt.addBatch(sql[i]);
/*      */           }
/*  240 */           rowsAffected = stmt.executeBatch();
/*      */         } else {
/*  242 */           for (int i = 0; i < sql.length; i++) {
/*  243 */             this.currSql = sql[i];
/*  244 */             if (!stmt.execute(sql[i]))
/*  245 */               rowsAffected[i] = stmt.getUpdateCount();
/*      */             else {
/*  247 */               throw new InvalidDataAccessApiUsageException("Invalid batch SQL statement: " + sql[i]);
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/*  252 */         return rowsAffected;
/*      */       }
/*      */ 
/*      */       public String getSql() {
/*  256 */         return this.currSql;
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   public Object execute(PreparedStatementCreator psc, PreparedStatementCallback action)
/*      */     throws DataAccessException
/*      */   {
/*  267 */     PreparedStatement ps = null;
/*      */     try {
/*  269 */       ps = psc.createPreparedStatement(this.conn);
/*  270 */       applyStatementSettings(ps);
/*  271 */       PreparedStatement psToUse = ps;
/*  272 */       Object result = action.doInPreparedStatement(psToUse);
/*  273 */       SQLWarning warning = ps.getWarnings();
/*  274 */       throwExceptionOnWarningIfNotIgnoringWarnings(warning);
/*  275 */       if ((result != null) && ((result instanceof Integer)) && 
/*  276 */         (Integer.parseInt(result.toString()) > 0)) {
/*  277 */         this.conn.commit();
/*      */       }
/*      */ 
/*  280 */       return result;
/*      */     } catch (SQLException ex) {
/*  282 */       if ((psc instanceof ParameterDisposer)) {
/*  283 */         ((ParameterDisposer)psc).cleanupParameters();
/*      */       }
/*  285 */       String sql = getSql(psc);
/*  286 */       JdbcUtils.closeStatement(ps);
/*  287 */       throw new UncategorizedSQLException("PreparedStatementCallback", sql, ex);
/*      */     }
/*      */     finally {
/*  290 */       if ((psc instanceof ParameterDisposer)) {
/*  291 */         ((ParameterDisposer)psc).cleanupParameters();
/*      */       }
/*  293 */       JdbcUtils.closeStatement(ps);
/*  294 */       psc = null;
/*  295 */       ps = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Object execute(String sql, PreparedStatementCallback action) throws DataAccessException
/*      */   {
/*  301 */     return execute(new SimplePreparedStatementCreator(sql), action);
/*      */   }
/*      */ 
/*      */   protected Object query(PreparedStatementCreator psc, final PreparedStatementSetter pss, final ResultSetExtractor rse)
/*      */     throws DataAccessException
/*      */   {
/*  325 */     if (this.logger.isDebugEnabled()) {
/*  326 */       String sql = getSql(psc);
/*  327 */       this.logger.debug("SQL:" + (sql != null ? " [" + sql + "]" : ""));
/*      */     }
/*  329 */     return execute(psc, new PreparedStatementCallback()
/*      */     {
/*      */       public Object doInPreparedStatement(PreparedStatement ps) throws SQLException {
/*  332 */         ResultSet rs = null;
/*      */         try {
/*  334 */           if (pss != null) {
/*  335 */             pss.setValues(ps);
/*      */           }
/*  337 */           rs = ps.executeQuery();
/*  338 */           ResultSet rsToUse = rs;
/*  339 */           return rse.extractData(rsToUse);
/*      */         } finally {
/*  341 */           JdbcUtils.closeResultSet(rs);
/*  342 */           if ((pss instanceof ParameterDisposer))
/*  343 */             ((ParameterDisposer)pss).cleanupParameters();
/*      */         }
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   public Object query(PreparedStatementCreator psc, ResultSetExtractor rse)
/*      */     throws DataAccessException
/*      */   {
/*  352 */     return query(psc, null, rse);
/*      */   }
/*      */ 
/*      */   public Object query(String sql, PreparedStatementSetter pss, ResultSetExtractor rse) throws DataAccessException
/*      */   {
/*  357 */     if (sql == null) {
/*  358 */       throw new InvalidDataAccessApiUsageException("SQL may not be null");
/*      */     }
/*  360 */     return query(new SimplePreparedStatementCreator(sql), pss, rse);
/*      */   }
/*      */ 
/*      */   public Object query(String sql, Object[] args, int[] argTypes, ResultSetExtractor rse) throws DataAccessException
/*      */   {
/*  365 */     return query(sql, new ArgTypePreparedStatementSetter(args, argTypes), rse);
/*      */   }
/*      */ 
/*      */   public Object query(String sql, Object[] args, ResultSetExtractor rse)
/*      */     throws DataAccessException
/*      */   {
/*  371 */     return query(sql, new ArgPreparedStatementSetter(args), rse);
/*      */   }
/*      */ 
/*      */   public List query(PreparedStatementCreator psc, RowCallbackHandler rch) throws DataAccessException
/*      */   {
/*  376 */     return (List)query(psc, new RowCallbackHandlerResultSetExtractor(rch));
/*      */   }
/*      */ 
/*      */   public List query(String sql, PreparedStatementSetter pss, RowCallbackHandler rch) throws DataAccessException
/*      */   {
/*  381 */     return (List)query(sql, pss, new RowCallbackHandlerResultSetExtractor(rch));
/*      */   }
/*      */ 
/*      */   public List query(String sql, Object[] args, int[] argTypes, RowCallbackHandler rch)
/*      */     throws DataAccessException
/*      */   {
/*  387 */     return query(sql, new ArgTypePreparedStatementSetter(args, argTypes), rch);
/*      */   }
/*      */ 
/*      */   public List query(String sql, Object[] args, RowCallbackHandler rch)
/*      */     throws DataAccessException
/*      */   {
/*  393 */     return query(sql, new ArgPreparedStatementSetter(args), rch);
/*      */   }
/*      */ 
/*      */   public List query(PreparedStatementCreator psc, RowMapper rowMapper) throws DataAccessException
/*      */   {
/*  398 */     return query(psc, new RowMapperResultReader(rowMapper));
/*      */   }
/*      */ 
/*      */   public List query(String sql, PreparedStatementSetter pss, RowMapper rowMapper) throws DataAccessException
/*      */   {
/*  403 */     return query(sql, pss, new RowMapperResultReader(rowMapper));
/*      */   }
/*      */ 
/*      */   public List query(String sql, Object[] args, int[] argTypes, RowMapper rowMapper) throws DataAccessException
/*      */   {
/*  408 */     return query(sql, args, argTypes, new RowMapperResultReader(rowMapper));
/*      */   }
/*      */ 
/*      */   public List query(String sql, Object[] args, RowMapper rowMapper) throws DataAccessException
/*      */   {
/*  413 */     return query(sql, args, new RowMapperResultReader(rowMapper));
/*      */   }
/*      */ 
/*      */   public Object queryForObject(String sql, Object[] args, int[] argTypes, RowMapper rowMapper) throws DataAccessException
/*      */   {
/*  418 */     List results = query(sql, args, argTypes, new RowMapperResultReader(rowMapper, 1));
/*      */ 
/*  420 */     return DataAccessUtils.uniqueResult(results);
/*      */   }
/*      */ 
/*      */   public Object queryForObject(String sql, Object[] args, RowMapper rowMapper) throws DataAccessException
/*      */   {
/*  425 */     List results = query(sql, args, new RowMapperResultReader(rowMapper, 1));
/*  426 */     return DataAccessUtils.uniqueResult(results);
/*      */   }
/*      */ 
/*      */   public Object queryForObject(String sql, Object[] args, int[] argTypes, Class requiredType) throws DataAccessException
/*      */   {
/*  431 */     return queryForObject(sql, args, argTypes, new SingleColumnRowMapper(requiredType));
/*      */   }
/*      */ 
/*      */   public Object queryForObject(String sql, Class requiredType, Object[] args)
/*      */     throws DataAccessException
/*      */   {
/*  437 */     return queryForObject(sql, args, new SingleColumnRowMapper(requiredType));
/*      */   }
/*      */ 
/*      */   public Map queryForMap(String sql, Object[] args, int[] argTypes)
/*      */     throws DataAccessException
/*      */   {
/*  443 */     return (Map)queryForObject(sql, args, argTypes, new LowwerCaseColumnMapRowMapper(this.useStrValue));
/*      */   }
/*      */ 
/*      */   public Map queryForMap(String sql, Object[] args)
/*      */     throws DataAccessException
/*      */   {
/*  449 */     return (Map)queryForObject(sql, args, new LowwerCaseColumnMapRowMapper(this.useStrValue));
/*      */   }
/*      */ 
/*      */   public long queryForLong(String sql, Object[] args, int[] argTypes)
/*      */     throws DataAccessException
/*      */   {
/*  455 */     Number number = (Number)queryForObject(sql, args, argTypes, Long.class);
/*  456 */     return number != null ? number.longValue() : 0L;
/*      */   }
/*      */ 
/*      */   public long queryForLong(String sql, Object[] args) throws DataAccessException
/*      */   {
/*  461 */     Number number = (Number)queryForObject(sql, Long.class, args);
/*  462 */     return number != null ? number.longValue() : 0L;
/*      */   }
/*      */ 
/*      */   public int queryForInt(String sql, Object[] args, int[] argTypes) throws DataAccessException
/*      */   {
/*  467 */     Number number = (Number)queryForObject(sql, args, argTypes, Integer.class);
/*      */ 
/*  469 */     return number != null ? number.intValue() : 0;
/*      */   }
/*      */ 
/*      */   public int queryForInt(String sql, Object[] args) throws DataAccessException
/*      */   {
/*  474 */     Number number = (Number)queryForObject(sql, Integer.class, args);
/*  475 */     return number != null ? number.intValue() : 0;
/*      */   }
/*      */ 
/*      */   public String queryForString(String sql, Object[] args)
/*      */     throws DataAccessException
/*      */   {
/*  484 */     return (String)queryForObject(sql, String.class, args);
/*      */   }
/*      */ 
/*      */   public String queryForString(String sql) throws DataAccessException {
/*  488 */     return (String)queryForObject(sql, String.class, new Object[0]);
/*      */   }
/*      */ 
/*      */   public List queryForList(String sql, Object[] args, int[] argTypes, Class elementType) throws DataAccessException
/*      */   {
/*  493 */     return query(sql, args, argTypes, new SingleColumnRowMapper(elementType));
/*      */   }
/*      */ 
/*      */   public List queryForList(String sql, Object[] args, Class elementType)
/*      */     throws DataAccessException
/*      */   {
/*  499 */     return query(sql, args, new SingleColumnRowMapper(elementType));
/*      */   }
/*      */ 
/*      */   public List queryForList(String sql, Object[] args, int[] argTypes) throws DataAccessException
/*      */   {
/*  504 */     return query(sql, args, argTypes, new LowwerCaseColumnMapRowMapper(this.useStrValue));
/*      */   }
/*      */ 
/*      */   public List queryForList(String sql, Object[] args)
/*      */     throws DataAccessException
/*      */   {
/*  510 */     return query(sql, args, new LowwerCaseColumnMapRowMapper(this.useStrValue));
/*      */   }
/*      */ 
/*      */   public SqlRowSet queryForRowSet(String sql, Object[] args, int[] argTypes) throws DataAccessException
/*      */   {
/*  515 */     return (SqlRowSet)query(sql, args, argTypes, new SqlRowSetResultSetExtractor());
/*      */   }
/*      */ 
/*      */   public SqlRowSet queryForRowSet(String sql, Object[] args)
/*      */     throws DataAccessException
/*      */   {
/*  521 */     return (SqlRowSet)query(sql, args, new SqlRowSetResultSetExtractor());
/*      */   }
/*      */ 
/*      */   protected int update(PreparedStatementCreator psc, final PreparedStatementSetter pss) throws DataAccessException
/*      */   {
/*  526 */     if (this.logger.isDebugEnabled()) {
/*  527 */       String sql = getSql(psc);
/*  528 */       this.logger.debug("Executing SQL update" + (sql != null ? " [" + sql + "]" : ""));
/*      */     }
/*      */ 
/*  531 */     Integer result = (Integer)execute(psc, new PreparedStatementCallback()
/*      */     {
/*      */       public Object doInPreparedStatement(PreparedStatement ps) throws SQLException
/*      */       {
/*      */         try {
/*  536 */           if (pss != null) {
/*  537 */             pss.setValues(ps);
/*      */           }
/*  539 */           int rows = ps.executeUpdate();
/*  540 */           if (SqlExecutorBak.this.logger.isDebugEnabled()) {
/*  541 */             SqlExecutorBak.this.logger.debug("SQL update affected " + rows + " rows");
/*      */           }
/*      */ 
/*  544 */           return new Integer(rows);
/*      */         } finally {
/*  546 */           if ((pss instanceof ParameterDisposer))
/*  547 */             ((ParameterDisposer)pss).cleanupParameters();
/*      */         }
/*      */       }
/*      */     });
/*  552 */     return result.intValue();
/*      */   }
/*      */ 
/*      */   public int update(PreparedStatementCreator psc) throws DataAccessException {
/*  556 */     return update(psc, (PreparedStatementSetter)null);
/*      */   }
/*      */ 
/*      */   public int update(PreparedStatementCreator psc, final KeyHolder generatedKeyHolder) throws DataAccessException
/*      */   {
/*  561 */     if (this.logger.isDebugEnabled()) {
/*  562 */       String sql = getSql(psc);
/*  563 */       this.logger.debug("Executing SQL update and returning generated keys" + (sql != null ? " [" + sql + "]" : ""));
/*      */     }
/*      */ 
/*  566 */     Integer result = (Integer)execute(psc, new PreparedStatementCallback()
/*      */     {
/*      */       public Object doInPreparedStatement(PreparedStatement ps)
/*      */         throws SQLException
/*      */       {
/*  571 */         int rows = ps.executeUpdate();
/*  572 */         List generatedKeys = generatedKeyHolder.getKeyList();
/*  573 */         generatedKeys.clear();
/*  574 */         ResultSet keys = ps.getGeneratedKeys();
/*  575 */         if (keys != null) {
/*      */           try {
/*  577 */             ColumnMapRowMapper rowMapper = new LowwerCaseColumnMapRowMapper(SqlExecutorBak.this.useStrValue);
/*      */ 
/*  579 */             RowMapperResultReader resultReader = new RowMapperResultReader(rowMapper, 1);
/*      */ 
/*  581 */             while (keys.next()) {
/*  582 */               resultReader.processRow(keys);
/*      */             }
/*  584 */             generatedKeys.addAll(resultReader.getResults());
/*      */           } finally {
/*  586 */             JdbcUtils.closeResultSet(keys);
/*      */           }
/*      */         }
/*  589 */         if (SqlExecutorBak.this.logger.isDebugEnabled()) {
/*  590 */           SqlExecutorBak.this.logger.debug("SQL update affected " + rows + " rows and returned " + generatedKeys.size() + " keys");
/*      */         }
/*      */ 
/*  594 */         return new Integer(rows);
/*      */       }
/*      */     });
/*  597 */     return result.intValue();
/*      */   }
/*      */ 
/*      */   public int update(String sql, PreparedStatementSetter pss) throws DataAccessException
/*      */   {
/*  602 */     return update(new SimplePreparedStatementCreator(sql), pss);
/*      */   }
/*      */ 
/*      */   public int update(String sql, Object[] args, int[] argTypes) throws DataAccessException
/*      */   {
/*  607 */     return update(sql, new ArgTypePreparedStatementSetter(args, argTypes));
/*      */   }
/*      */ 
/*      */   public int update(String sql, Object[] args) throws DataAccessException
/*      */   {
/*  612 */     return update(sql, new ArgPreparedStatementSetter(args));
/*      */   }
/*      */ 
/*      */   public int[] batchUpdate(String sql, final BatchPreparedStatementSetter pss) throws DataAccessException {
/*  616 */     if (this.logger.isDebugEnabled()) {
/*  617 */       this.logger.debug("Executing SQL batch update [" + sql + "]");
/*      */     }
/*  619 */     return (int[])execute(sql, new PreparedStatementCallback()
/*      */     {
/*      */       public Object doInPreparedStatement(PreparedStatement ps) throws SQLException {
/*      */         try {
/*  623 */           int batchSize = pss.getBatchSize();
/*  624 */           if (JdbcUtils.supportsBatchUpdates(ps.getConnection())) {
/*  625 */             for (int i = 0; i < batchSize; i++) {
/*  626 */               pss.setValues(ps, i);
/*  627 */               ps.addBatch();
/*      */             }
/*  629 */             return ps.executeBatch();
/*      */           }
/*  631 */           int[] rowsAffected = new int[batchSize];
/*  632 */           for (int i = 0; i < batchSize; i++) {
/*  633 */             pss.setValues(ps, i);
/*  634 */             rowsAffected[i] = ps.executeUpdate();
/*      */           }
/*  636 */           return rowsAffected;
/*      */         }
/*      */         finally {
/*  639 */           if ((pss instanceof ParameterDisposer))
/*  640 */             ((ParameterDisposer)pss).cleanupParameters();
/*      */         }
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   public Object execute(CallableStatementCreator csc, CallableStatementCallback action)
/*      */     throws DataAccessException
/*      */   {
/*  652 */     if (this.logger.isDebugEnabled()) {
/*  653 */       String sql = getSql(csc);
/*  654 */       this.logger.debug("Calling stored procedure" + (sql != null ? " [" + sql + "]" : ""));
/*      */     }
/*      */ 
/*  657 */     CallableStatement cs = null;
/*      */     try {
/*  659 */       Connection conToUse = this.conn;
/*  660 */       cs = csc.createCallableStatement(conToUse);
/*  661 */       applyStatementSettings(cs);
/*  662 */       CallableStatement csToUse = cs;
/*  663 */       Object result = action.doInCallableStatement(csToUse);
/*  664 */       SQLWarning warning = cs.getWarnings();
/*  665 */       throwExceptionOnWarningIfNotIgnoringWarnings(warning);
/*  666 */       return result;
/*      */     } catch (SQLException ex) {
/*  668 */       if ((csc instanceof ParameterDisposer)) {
/*  669 */         ((ParameterDisposer)csc).cleanupParameters();
/*      */       }
/*  671 */       String sql = getSql(csc);
/*  672 */       JdbcUtils.closeStatement(cs);
/*  673 */       throw new UncategorizedSQLException("CallableStatementCallback", sql, ex);
/*      */     }
/*      */     finally {
/*  676 */       if ((csc instanceof ParameterDisposer)) {
/*  677 */         ((ParameterDisposer)csc).cleanupParameters();
/*      */       }
/*  679 */       JdbcUtils.closeStatement(cs);
/*  680 */       csc = null;
/*  681 */       cs = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Object execute(String callString, CallableStatementCallback action) throws DataAccessException
/*      */   {
/*  687 */     return execute(new SimpleCallableStatementCreator(callString), action);
/*      */   }
/*      */ 
/*      */   public Map call(CallableStatementCreator csc, final List declaredParameters) throws DataAccessException
/*      */   {
/*  692 */     return (Map)execute(csc, new CallableStatementCallback()
/*      */     {
/*      */       public Object doInCallableStatement(CallableStatement cs) throws SQLException
/*      */       {
/*  696 */         boolean retVal = cs.execute();
/*  697 */         int updateCount = cs.getUpdateCount();
/*  698 */         if (SqlExecutorBak.this.logger.isDebugEnabled()) {
/*  699 */           SqlExecutorBak.this.logger.debug("CallableStatement.execute() returned '" + retVal + "'");
/*      */ 
/*  701 */           SqlExecutorBak.this.logger.debug("CallableStatement.getUpdateCount() returned " + updateCount);
/*      */         }
/*      */ 
/*  704 */         Map returnedResults = new HashMap();
/*  705 */         if ((retVal) || (updateCount != -1)) {
/*  706 */           returnedResults.putAll(SqlExecutorBak.this.extractReturnedResultSets(cs, declaredParameters, updateCount));
/*      */         }
/*      */ 
/*  709 */         returnedResults.putAll(SqlExecutorBak.this.extractOutputParameters(cs, declaredParameters));
/*      */ 
/*  711 */         return returnedResults;
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   protected Map extractReturnedResultSets(CallableStatement cs, List parameters, int updateCount)
/*      */     throws SQLException
/*      */   {
/*  727 */     Map returnedResults = new HashMap();
/*  728 */     int rsIndex = 0;
/*      */     boolean moreResults;
/*      */     do
/*      */     {
/*  731 */       if (updateCount == -1) {
/*  732 */         Object param = null;
/*  733 */         if ((parameters != null) && (parameters.size() > rsIndex)) {
/*  734 */           param = parameters.get(rsIndex);
/*      */         }
/*  736 */         if ((param instanceof SqlReturnResultSet)) {
/*  737 */           SqlReturnResultSet rsParam = (SqlReturnResultSet)param;
/*  738 */           returnedResults.putAll(processResultSet(cs.getResultSet(), rsParam));
/*      */         }
/*      */         else {
/*  741 */           this.logger.warn("Results returned from stored procedure but a corresponding SqlOutParameter/SqlReturnResultSet parameter was not declared");
/*      */         }
/*      */ 
/*  745 */         rsIndex++;
/*      */       }
/*  747 */       moreResults = cs.getMoreResults();
/*  748 */       updateCount = cs.getUpdateCount();
/*  749 */       if (this.logger.isDebugEnabled()) {
/*  750 */         this.logger.debug("CallableStatement.getUpdateCount() returned " + updateCount);
/*      */       }
/*      */     }
/*  753 */     while ((moreResults) || (updateCount != -1));
/*  754 */     return returnedResults;
/*      */   }
/*      */ 
/*      */   protected Map extractOutputParameters(CallableStatement cs, List parameters)
/*      */     throws SQLException
/*      */   {
/*  769 */     Map returnedResults = new HashMap();
/*  770 */     int sqlColIndex = 1;
/*  771 */     for (int i = 0; i < parameters.size(); i++) {
/*  772 */       Object param = parameters.get(i);
/*  773 */       if ((param instanceof SqlOutParameter)) {
/*  774 */         SqlOutParameter outParam = (SqlOutParameter)param;
/*  775 */         if (outParam.isReturnTypeSupported()) {
/*  776 */           Object out = outParam.getSqlReturnType().getTypeValue(cs, sqlColIndex, outParam.getSqlType(), outParam.getTypeName());
/*      */ 
/*  779 */           returnedResults.put(outParam.getName(), out);
/*      */         } else {
/*  781 */           Object out = cs.getObject(sqlColIndex);
/*  782 */           if ((out instanceof ResultSet)) {
/*  783 */             if (outParam.isResultSetSupported()) {
/*  784 */               returnedResults.putAll(processResultSet((ResultSet)out, outParam));
/*      */             }
/*      */             else {
/*  787 */               this.logger.warn("ResultSet returned from stored procedure but a corresponding SqlOutParameter with a RowCallbackHandler was not declared");
/*      */ 
/*  790 */               returnedResults.put(outParam.getName(), "ResultSet was returned but not processed");
/*      */             }
/*      */           }
/*      */           else {
/*  794 */             returnedResults.put(outParam.getName(), out);
/*      */           }
/*      */         }
/*      */       }
/*  798 */       if (!(param instanceof SqlReturnResultSet)) {
/*  799 */         sqlColIndex++;
/*      */       }
/*      */     }
/*  802 */     return returnedResults;
/*      */   }
/*      */ 
/*      */   protected Map processResultSet(ResultSet rs, ResultSetSupportingSqlParameter param)
/*      */     throws SQLException
/*      */   {
/*  816 */     Map returnedResults = new HashMap();
/*      */ 
/*  842 */     return returnedResults;
/*      */   }
/*      */ 
/*      */   protected void applyStatementSettings(Statement stmt) throws SQLException {
/*  846 */     if (getFetchSize() > 0) {
/*  847 */       stmt.setFetchSize(getFetchSize());
/*      */     }
/*  849 */     if (getMaxRows() > 0)
/*  850 */       stmt.setMaxRows(getMaxRows());
/*      */   }
/*      */ 
/*      */   private void throwExceptionOnWarningIfNotIgnoringWarnings(SQLWarning warning)
/*      */     throws SQLWarningException
/*      */   {
/*  856 */     if (warning != null)
/*  857 */       if (isIgnoreWarnings())
/*  858 */         this.logger.warn("SQLWarning ignored: " + warning);
/*      */       else
/*  860 */         throw new SQLWarningException("Warning not ignored", warning);
/*      */   }
/*      */ 
/*      */   private static String getSql(Object sqlProvider)
/*      */   {
/*  866 */     if ((sqlProvider instanceof SqlProvider)) {
/*  867 */       return ((SqlProvider)sqlProvider).getSql();
/*      */     }
/*  869 */     return null;
/*      */   }
/*      */ 
/*      */   public Map callProc(String procName, Map inParamMap, String methodName, Class newClass)
/*      */     throws Exception
/*      */   {
/* 1007 */     Connection dbConnection = null;
/* 1008 */     CallableStatement proc = null;
/* 1009 */     Map resultMap = new HashMap();
/*      */     try {
/* 1011 */       dbConnection = this.conn;
/* 1012 */       proc = dbConnection.prepareCall("{ call " + procName.toUpperCase() + "}");
/*      */ 
/* 1014 */       Object object = Class.forName(newClass.getName()).newInstance();
/* 1015 */       Method method = newClass.getMethod(methodName, new Class[] { CallableStatement.class, Map.class });
/*      */ 
/* 1017 */       resultMap = (Map)method.invoke(object, new Object[] { proc, inParamMap });
/*      */     }
/*      */     catch (Exception se) {
/* 1020 */       se.printStackTrace();
/* 1021 */       throw new DAOSystemException("SQLException while execProc:" + procName + "\n", se);
/*      */     }
/*      */     finally {
/* 1024 */       proc.close();
/*      */       try {
/* 1026 */         closeConnection(dbConnection);
/*      */       } catch (SQLException e) {
/* 1028 */         e.printStackTrace();
/*      */       }
/*      */     }
/* 1031 */     return resultMap;
/*      */   }
/*      */ 
/*      */   private static void closeConnection(Connection conn) throws SQLException {
/*      */     try {
/* 1036 */       if ((conn != null) && (!conn.isClosed())) {
/* 1037 */         if (conn.getTransactionIsolation() == 1) {
/* 1038 */           conn.setTransactionIsolation(2);
/*      */         }
/*      */ 
/* 1041 */         conn.close();
/*      */       }
/*      */     } catch (SQLException e) {
/* 1044 */       e.printStackTrace();
/* 1045 */       throw new SQLException("SQL Exception while closing DB connection : \n");
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class RowCallbackHandlerResultSetExtractor
/*      */     implements ResultSetExtractor
/*      */   {
/*      */     private final RowCallbackHandler rch;
/*      */ 
/*      */     public RowCallbackHandlerResultSetExtractor(RowCallbackHandler rch)
/*      */     {
/*  990 */       this.rch = rch;
/*      */     }
/*      */ 
/*      */     public Object extractData(ResultSet rs) throws SQLException {
/*  994 */       while (rs.next()) {
/*  995 */         this.rch.processRow(rs);
/*      */       }
/*  997 */       if ((this.rch instanceof ResultReader)) {
/*  998 */         return ((ResultReader)this.rch).getResults();
/*      */       }
/* 1000 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class ArgTypePreparedStatementSetter
/*      */     implements PreparedStatementSetter, ParameterDisposer
/*      */   {
/*      */     private final Object[] args;
/*      */     private final int[] argTypes;
/*      */ 
/*      */     public ArgTypePreparedStatementSetter(Object[] args, int[] argTypes)
/*      */     {
/*  952 */       if (((args != null) && (argTypes == null)) || ((args == null) && (argTypes != null)) || ((args != null) && (args.length != argTypes.length)))
/*      */       {
/*  955 */         throw new InvalidDataAccessApiUsageException("args and argTypes parameters must match");
/*      */       }
/*      */ 
/*  958 */       this.args = args;
/*  959 */       this.argTypes = argTypes;
/*      */     }
/*      */ 
/*      */     public void setValues(PreparedStatement ps) throws SQLException {
/*  963 */       if (this.args != null)
/*  964 */         for (int i = 0; i < this.args.length; i++)
/*  965 */           StatementCreatorUtils.setParameterValue(ps, i + 1, this.argTypes[i], null, this.args[i]);
/*      */     }
/*      */ 
/*      */     public void cleanupParameters()
/*      */     {
/*  972 */       StatementCreatorUtils.cleanupParameters(this.args);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class ArgPreparedStatementSetter
/*      */     implements PreparedStatementSetter, ParameterDisposer
/*      */   {
/*      */     private final Object[] args;
/*      */ 
/*      */     public ArgPreparedStatementSetter(Object[] args)
/*      */     {
/*  921 */       this.args = args;
/*      */     }
/*      */ 
/*      */     public void setValues(PreparedStatement ps) throws SQLException {
/*  925 */       if (this.args != null)
/*  926 */         for (int i = 0; i < this.args.length; i++)
/*  927 */           if (this.args[i] == null)
/*  928 */             ps.setObject(i + 1, null);
/*      */           else
/*  930 */             StatementCreatorUtils.setParameterValue(ps, i + 1, -2147483648, null, this.args[i]);
/*      */     }
/*      */ 
/*      */     public void cleanupParameters()
/*      */     {
/*  937 */       StatementCreatorUtils.cleanupParameters(this.args);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class SimpleCallableStatementCreator
/*      */     implements CallableStatementCreator, SqlProvider
/*      */   {
/*      */     private final String callString;
/*      */ 
/*      */     public SimpleCallableStatementCreator(String callString)
/*      */     {
/*  898 */       this.callString = callString;
/*      */     }
/*      */ 
/*      */     public CallableStatement createCallableStatement(Connection con) throws SQLException
/*      */     {
/*  903 */       return con.prepareCall(this.callString);
/*      */     }
/*      */ 
/*      */     public String getSql() {
/*  907 */       return this.callString;
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class SimplePreparedStatementCreator
/*      */     implements PreparedStatementCreator, SqlProvider
/*      */   {
/*      */     private final String sql;
/*      */ 
/*      */     public SimplePreparedStatementCreator(String sql)
/*      */     {
/*  877 */       this.sql = sql;
/*      */     }
/*      */ 
/*      */     public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
/*  881 */       return con.prepareStatement(this.sql);
/*      */     }
/*      */     public String getSql() {
/*  884 */       return this.sql;
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.framework.dao.SqlExecutorBak
 * JD-Core Version:    0.6.2
 */