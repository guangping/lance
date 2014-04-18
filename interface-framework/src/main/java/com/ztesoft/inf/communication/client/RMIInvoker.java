/*    */ package com.ztesoft.inf.communication.client;
/*    */ 
/*    */ import com.ztesoft.common.util.date.DateUtil;
/*    */ import com.ztesoft.inf.framework.commons.CodedException;
/*    */ import java.util.Map;
/*    */ import org.springframework.remoting.rmi.RmiProxyFactoryBean;
/*    */ 
/*    */ public class RMIInvoker extends Invoker
/*    */ {
/*    */   public Object invoke(InvokeContext context)
/*    */     throws Exception
/*    */   {
/* 16 */     Object retObj = null;
/*    */     try {
/* 18 */       context.setRequestTime(DateUtil.currentTime());
/* 19 */       context.setRequestString(generateRequestString(context));
/* 20 */       RmiProxyFactoryBean factory = new RmiProxyFactoryBean();
/* 21 */       factory.setServiceInterface(CrmRemoteCall.class);
/* 22 */       factory.setServiceUrl(this.endpoint);
/* 23 */       factory.afterPropertiesSet();
/* 24 */       CrmRemoteCall service = (CrmRemoteCall)factory.getObject();
/* 25 */       Map parameters = (Map)context.getParameters();
/* 26 */       String serviceName = (String)parameters.get("serviceName");
/* 27 */       String methodName = (String)parameters.get("methodName");
/* 28 */       Object[] param = (Object[])parameters.get("param");
/* 29 */       CrmCallBack callBack = (CrmCallBack)parameters.get("crmCallBack");
/* 30 */       retObj = service.remoteCall(serviceName, methodName, param, callBack);
/* 31 */       return retObj;
/*    */     } catch (Exception e) {
/* 33 */       context.setFailure(e.getMessage());
/* 34 */       throw new CodedException("9003", "RMI请求[" + context.getOperationCode() + "]失败:" + e.getMessage(), e);
/*    */     } finally {
/* 36 */       if (retObj != null) {
/* 37 */         context.setResponeString(retObj.toString());
/*    */       }
/* 39 */       context.setResponseTime(DateUtil.currentTime());
/*    */     }
/*    */   }
/*    */ 
/*    */   protected String generateRequestString(InvokeContext context)
/*    */   {
/* 45 */     return context.getParameters().toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.communication.client.RMIInvoker
 * JD-Core Version:    0.6.2
 */