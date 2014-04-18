/*    */ package com.ztesoft.inf.framework.commons;
/*    */ 
/*    */ import com.ztesoft.common.dao.ConnectionContext;
/*    */ import com.ztesoft.common.dao.SysSet;
/*    */ import java.lang.reflect.Method;
/*    */ import javax.transaction.UserTransaction;
/*    */ import net.sf.cglib.proxy.MethodInterceptor;
/*    */ import net.sf.cglib.proxy.MethodProxy;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class TransactionProxy
/*    */   implements MethodInterceptor
/*    */ {
/* 19 */   private boolean useXa = false;
/* 20 */   private static Logger logger = Logger.getLogger(TransactionProxy.class);
/*    */ 
/*    */   public TransactionProxy(boolean useXa) {
/* 23 */     this.useXa = useXa;
/*    */   }
/*    */   public TransactionProxy() {
/* 26 */     this(false);
/*    */   }
/*    */ 
/*    */   public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
/* 30 */     if (method.getModifiers() != 1) {
/* 31 */       return proxy.invokeSuper(obj, args);
/*    */     }
/* 33 */     boolean commit = true;
/* 34 */     UserTransaction usTran = null;
/*    */     try {
/* 36 */       if (this.useXa) {
/* 37 */         usTran = SysSet.getUserTransaction();
/* 38 */         SysSet.tpBegin(usTran);
/*    */       }
/* 40 */       Object result = proxy.invokeSuper(obj, args);
/* 41 */       return result;
/*    */     } catch (Exception e) {
/* 43 */       commit = false;
/* 44 */       throw e;
/*    */     } finally {
/*    */       try {
/* 47 */         if (commit) {
/* 48 */           if (this.useXa)
/* 49 */             SysSet.tpCommit(usTran);
/*    */           else
/* 51 */             ConnectionContext.getContext().allCommit();
/*    */         }
/* 53 */         else if (this.useXa)
/* 54 */           SysSet.tpRollback(usTran);
/*    */         else {
/* 56 */           ConnectionContext.getContext().allRollback();
/*    */         }
/* 58 */         ConnectionContext.getContext().allCloseConnection();
/* 59 */         if (usTran != null) {
/* 60 */           logger.debug("关闭UserTransaction");
/* 61 */           usTran = null;
/*    */         }
/*    */       } catch (Exception e) {
/* 64 */         logger.error("关闭数据库连接时，出现异常" + e);
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.framework.commons.TransactionProxy
 * JD-Core Version:    0.6.2
 */