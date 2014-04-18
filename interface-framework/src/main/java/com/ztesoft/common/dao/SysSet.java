/*     */ package com.ztesoft.common.dao;
/*     */ 
/*     */ import com.powerise.ibss.framework.FrameException;
/*     */ import java.util.HashMap;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.InitialContext;
/*     */ import javax.transaction.UserTransaction;
/*     */ 
/*     */ public class SysSet
/*     */ {
/*  14 */   private static HashMap m_DBLink = null;
/*     */ 
/*  16 */   private static String m_DefaultDBName = "DEFAULT";
/*     */ 
/*  18 */   private static int m_XATranTimeOut = 60;
/*     */ 
/*  20 */   public static boolean surportAjax = true;
/*     */ 
/*     */   public static UserTransaction getUserTransaction()
/*     */     throws FrameException
/*     */   {
/*  29 */     UserTransaction usTran = null;
/*  30 */     Context usContext = null;
/*     */     try {
/*  32 */       usContext = new InitialContext();
/*  33 */       usTran = (UserTransaction)usContext.lookup("java:comp/UserTransaction");
/*     */ 
/*  35 */       usTran.setTransactionTimeout(m_XATranTimeOut);
/*     */     } catch (Exception e) {
/*  37 */       throw new FrameException(-22990013, "创建UserTransaction异常", e.toString());
/*     */     }
/*  39 */     return usTran;
/*     */   }
/*     */ 
/*     */   public static void tpBegin(UserTransaction usTran)
/*     */     throws FrameException
/*     */   {
/*     */     try
/*     */     {
/*  53 */       usTran.begin();
/*     */     } catch (Exception e) {
/*  55 */       throw new FrameException(-22990010, "xa初始化出错:" + e.getClass().getName(), e.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void tpCommit(UserTransaction usTran)
/*     */     throws FrameException
/*     */   {
/*  66 */     if (usTran != null)
/*     */       try {
/*  68 */         usTran.commit();
/*  69 */         usTran = null;
/*     */       } catch (Exception e) {
/*  71 */         throw new FrameException(-22990010, "xa提交数据出错", e.toString());
/*     */       }
/*     */   }
/*     */ 
/*     */   public static void tpRollback(UserTransaction usTran)
/*     */     throws FrameException
/*     */   {
/*  83 */     if (usTran != null)
/*     */       try {
/*  85 */         usTran.rollback();
/*  86 */         usTran = null;
/*     */       } catch (Exception e) {
/*  88 */         throw new FrameException(-22990010, "xa回滚数据出错", e.toString());
/*     */       }
/*     */   }
/*     */ 
/*     */   public static String getHomeDirectory()
/*     */   {
/*  98 */     String s = System.getProperty("IBSS_HOME");
/*  99 */     if (s != null) {
/* 100 */       return s;
/*     */     }
/* 102 */     return System.getProperty("user.home");
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.common.dao.SysSet
 * JD-Core Version:    0.6.2
 */