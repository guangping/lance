/*    */ package com.ztesoft.inf.framework.commons;
/*    */ 
/*    */ import com.powerise.ibss.util.SysSet;
/*    */ import com.ztesoft.common.dao.ConnectionContext;
/*    */ import java.lang.reflect.Method;
/*    */ import javax.transaction.UserTransaction;
/*    */ import net.sf.cglib.proxy.MethodInterceptor;
/*    */ import net.sf.cglib.proxy.MethodProxy;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class TransactionCommitProxy
/*    */   implements MethodInterceptor
/*    */ {
/* 18 */   private boolean useXa = false;
/* 19 */   private static Logger logger = Logger.getLogger(TransactionCommitProxy.class);
/*    */ 
/*    */   public TransactionCommitProxy(boolean useXa) {
/* 22 */     this.useXa = useXa;
/*    */   }
/*    */   public TransactionCommitProxy() {
/* 25 */     this(false);
/*    */   }
/*    */ 
/*    */   public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
/* 29 */     if (method.getModifiers() != 1) {
/* 30 */       return proxy.invokeSuper(obj, args);
/*    */     }
/* 32 */     boolean commit = true;
/* 33 */     UserTransaction usTran = null;
/*    */     try {
/* 35 */       if (this.useXa) {
/* 36 */         usTran = SysSet.getUserTransaction();
/* 37 */         SysSet.tpBegin(usTran);
/*    */       }
/* 39 */       Object result = proxy.invokeSuper(obj, args);
/* 40 */       return result;
/*    */     } catch (Exception e) {
/* 42 */       commit = false;
/* 43 */       throw e;
/*    */     } finally {
/*    */       try {
/* 46 */         if (commit) {
/* 47 */           if (this.useXa)
/* 48 */             SysSet.tpCommit(usTran);
/*    */           else
/* 50 */             ConnectionContext.getContext().allCommit();
/*    */         }
/* 52 */         else if (this.useXa)
/* 53 */           SysSet.tpRollback(usTran);
/*    */         else {
/* 55 */           ConnectionContext.getContext().allRollback();
/*    */         }
/* 57 */         ConnectionContext.getContext().allCloseConnection();
/* 58 */         if (usTran != null) {
/* 59 */           logger.debug("关闭UserTransaction");
/* 60 */           usTran = null;
/*    */         }
/*    */       } catch (Exception e) {
/* 63 */         logger.error("关闭数据库连接时，出现异常" + e);
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.framework.commons.TransactionCommitProxy
 * JD-Core Version:    0.6.2
 */