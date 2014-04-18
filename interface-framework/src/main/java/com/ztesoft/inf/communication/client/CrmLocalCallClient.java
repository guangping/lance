/*    */ package com.ztesoft.inf.communication.client;
/*    */ 
/*    */ import com.ztesoft.common.application.AppClass;
/*    */ import com.ztesoft.common.util.convert.ObjectSerialUtils;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class CrmLocalCallClient
/*    */ {
/*    */   public void localCall(String serviceName, String methodName, String methodParam)
/*    */     throws Exception
/*    */   {
/* 20 */     localCall(serviceName, methodName, methodParam, null);
/*    */   }
/*    */ 
/*    */   public void localCall(String serviceName, String methodName, String methodXml, CrmCallBack callBack)
/*    */     throws Exception
/*    */   {
/* 26 */     AppClass aps = new AppClass();
/* 27 */     aps.loadFromClass(serviceName, null);
/*    */ 
/* 30 */     ObjectSerialUtils objSerial = new ObjectSerialUtils();
/* 31 */     HashMap val = (HashMap)objSerial.readStringToObject(methodXml);
/*    */ 
/* 33 */     Object[] _param = (Object[])val.get("____ROOT");
/* 34 */     Object result = aps.executeMethodNoAop(methodName, _param, null);
/* 35 */     if (callBack != null) {
/* 36 */       callBack.setResult(result);
/* 37 */       callBack.execute();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.communication.client.CrmLocalCallClient
 * JD-Core Version:    0.6.2
 */