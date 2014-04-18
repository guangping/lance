/*    */ package com.ztesoft.inf.framework.dao;
/*    */ 
/*    */ import com.ztesoft.ibss.common.dao.DAOUtils;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.sql.Timestamp;
/*    */ import org.springframework.jdbc.core.ColumnMapRowMapper;
/*    */ 
/*    */ public class LowwerCaseColumnMapRowMapper extends ColumnMapRowMapper
/*    */ {
/*    */   private boolean useStrValue;
/*    */ 
/*    */   public LowwerCaseColumnMapRowMapper(boolean useStrValue)
/*    */   {
/* 15 */     this.useStrValue = useStrValue;
/*    */   }
/*    */   protected String getColumnKey(String columnName) {
/* 18 */     return columnName.toLowerCase();
/*    */   }
/*    */   protected Object getColumnValue(ResultSet rs, int index) throws SQLException {
/* 21 */     Object obj = super.getColumnValue(rs, index);
/* 22 */     if (!this.useStrValue)
/* 23 */       return obj;
/* 24 */     if ((obj instanceof Timestamp))
/* 25 */       obj = DAOUtils.getFormatedDateTime((Timestamp)obj);
/*    */     else {
/* 27 */       obj = rs.getString(index);
/*    */     }
/* 29 */     return obj;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.framework.dao.LowwerCaseColumnMapRowMapper
 * JD-Core Version:    0.6.2
 */