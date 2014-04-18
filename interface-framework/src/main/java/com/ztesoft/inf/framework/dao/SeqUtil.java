/*     */ package com.ztesoft.inf.framework.dao;
/*     */ 
/*     */ import com.powerise.ibss.framework.FrameException;
/*     */ import com.ztesoft.common.util.DateFormatUtils;
/*     */ import com.ztesoft.ibss.common.util.StringUtils;
/*     */ import java.sql.Date;
/*     */ import java.sql.SQLException;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.NumberFormat;
/*     */ 
/*     */ public class SeqUtil
/*     */ {
/*  16 */   private SqlExe sqlExe = new SqlExe();
/*     */ 
/*     */   public String getNextSequence(String tableCode, String fieldCode)
/*     */   {
/*  25 */     String result = null;
/*  26 */     String GET_SEQUENCE_CODE = "SELECT sequence_code FROM sequence_management  WHERE table_code=? AND field_code=?";
/*     */     try
/*     */     {
/*  29 */       String sequenceCode = this.sqlExe.queryForString(GET_SEQUENCE_CODE, new String[] { tableCode, fieldCode });
/*  30 */       return next(sequenceCode);
/*     */     } catch (FrameException e) {
/*  32 */       e.printStackTrace();
/*     */     } catch (SQLException e) {
/*  34 */       e.printStackTrace();
/*     */     }
/*  36 */     return result;
/*     */   }
/*     */ 
/*     */   public String getNextSequenceFormat(String tableCode, String fieldCode, int seqNum)
/*     */   {
/*  49 */     String result = null;
/*  50 */     String GET_SEQUENCE_CODE = "SELECT sequence_code FROM sequence_management  WHERE table_code=? AND field_code=?";
/*     */     try
/*     */     {
/*  53 */       String sequenceCode = this.sqlExe.queryForString(GET_SEQUENCE_CODE, new String[] { tableCode, fieldCode });
/*  54 */       String GET_SEQUENCE = "select LPAD( " + sequenceCode + ".nextval," + seqNum + ",'0') seq_value FROM dual";
/*  55 */       result = this.sqlExe.queryForString(GET_SEQUENCE);
/*     */     } catch (FrameException e) {
/*  57 */       e.printStackTrace();
/*     */     } catch (SQLException e) {
/*  59 */       e.printStackTrace();
/*     */     }
/*  61 */     return result;
/*     */   }
/*     */ 
/*     */   public synchronized String next(String sequenceCode)
/*     */   {
/*  66 */     String GET_SEQUENCE = "SELECT " + sequenceCode + ".nextval seq_value FROM dual";
/*  67 */     String result = null;
/*     */     try {
/*  69 */       result = this.sqlExe.queryForString(GET_SEQUENCE);
/*     */     } catch (FrameException e) {
/*  71 */       e.printStackTrace();
/*     */     } catch (SQLException e) {
/*  73 */       e.printStackTrace();
/*     */     }
/*  75 */     return result;
/*     */   }
/*     */ 
/*     */   public String getTimeSequence(String seq_name)
/*     */   {
/*  84 */     String currentTime = DateFormatUtils.getFormatedDate();
/*  85 */     NumberFormat format = new DecimalFormat("0000000000");
/*     */     try {
/*  87 */       String rval = next(seq_name);
/*  88 */       if (StringUtils.isNotEmpty(rval))
/*  89 */         currentTime = currentTime + format.format(new Long(rval).longValue());
/*     */     }
/*     */     catch (Exception e) {
/*  92 */       currentTime = DateFormatUtils.formatDate(new Date(System.currentTimeMillis()), "yyyyMMddHHmmss");
/*  93 */       e.printStackTrace();
/*     */     }
/*  95 */     return currentTime;
/*     */   }
/*     */   public String getSequenceLen(String strSeqName, String strSeqType, int intSeqLen) {
/*  98 */     String sql = "";
/*  99 */     if (strSeqType.trim().equals("0"))
/* 100 */       sql = "SELECT LPAD(getseq('" + strSeqName + "')," + intSeqLen + ",'0') SEQ FROM DUAL";
/* 101 */     else if (strSeqType.trim().equals("1"))
/* 102 */       sql = "SELECT to_char2(getdate(),'yyyymmdd')||LPAD(getseq('" + strSeqName + "')," + intSeqLen + "-8,'0')  seq  FROM DUAL";
/* 103 */     else if (strSeqType.trim().equals("2"))
/* 104 */       sql = "select to_char2(getdate(),'yyyymmddhh24miss')||LPAD(getseq('" + strSeqName + "')," + intSeqLen + "-14,'0') seq from dual ";
/*     */     else {
/* 106 */       sql = "select getseq('" + strSeqName + "') from dual";
/*     */     }
/* 108 */     String ret = null;
/*     */     try {
/* 110 */       ret = this.sqlExe.queryForString(sql);
/*     */     } catch (FrameException e) {
/* 112 */       e.printStackTrace();
/*     */     } catch (SQLException e) {
/* 114 */       e.printStackTrace();
/*     */     }
/* 116 */     return ret;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.framework.dao.SeqUtil
 * JD-Core Version:    0.6.2
 */