/*    */ package com.ztesoft.inf.communication.client;
/*    */ 
/*    */ import com.ztesoft.common.util.convert.ObjectSerialUtils;
/*    */ import com.ztesoft.inf.framework.utils.ParseXml;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class CrmRemoteCallClient
/*    */ {
/*    */   public void remoteCall(String operationCode, String serviceName, String methodName, Object[] param, CrmCallBack callBack)
/*    */     throws Exception
/*    */   {
/* 20 */     CommCaller comCaller = new CommCaller();
/* 21 */     ParseXml parse = new ParseXml();
/* 22 */     HashMap _params = new HashMap();
/* 23 */     _params.put("serviceName", serviceName);
/* 24 */     _params.put("methodName", methodName);
/*    */ 
/* 26 */     ObjectSerialUtils objSerial = new ObjectSerialUtils();
/* 27 */     if (param == null) {
/* 28 */       _params.put("param", null);
/*    */     } else {
/* 30 */       parse.setEncoding("");
/* 31 */       HashMap rootVo = new HashMap();
/* 32 */       rootVo.put("____ROOT", param);
/*    */ 
/* 34 */       String strObj = objSerial.writeObjectToString(rootVo);
/* 35 */       _params.put("param", strObj);
/*    */     }
/* 37 */     Object retObj = comCaller.invoke(operationCode, _params);
/* 38 */     if (callBack != null)
/*    */     {
/* 54 */       HashMap responeHashMap = (HashMap)retObj;
/* 55 */       HashMap body = (HashMap)responeHashMap.get("Body");
/* 56 */       HashMap exchangeResponse = (HashMap)body.get("exchangeResponse");
/* 57 */       String outStr = (String)exchangeResponse.get("out");
/* 58 */       HashMap out = parse.parseXml(outStr);
/* 59 */       HashMap root = (HashMap)out.get("Root");
/* 60 */       HashMap header = (HashMap)root.get("Header");
/* 61 */       HashMap interfaceRespone = (HashMap)header.get("Response");
/* 62 */       HashMap crmRemoteCallResult = (HashMap)root.get("CrmRemoteCallResult");
/*    */ 
/* 64 */       HashMap _ret = new HashMap();
/* 65 */       String code = (String)interfaceRespone.get("Code");
/* 66 */       if ((code == null) || (!code.equals("0000"))) {
/* 67 */         String message = (String)interfaceRespone.get("Message");
/* 68 */         throw new Exception("远程调用失败!" + message);
/*    */       }
/* 70 */       _ret.putAll(crmRemoteCallResult);
/* 71 */       callBack.setResult(_ret);
/* 72 */       callBack.execute();
/*    */     }
/*    */   }
/*    */ 
/*    */   public void remoteCall(String serviceName, String methodName, Object[] param, CrmCallBack callBack)
/*    */     throws Exception
/*    */   {
/* 79 */     remoteCall("crm.CrmRemoteCall", serviceName, methodName, param, callBack);
/*    */   }
/*    */ 
/*    */   public void remoteCall(String serviceName, String methodName, Object[] param)
/*    */     throws Exception
/*    */   {
/* 86 */     remoteCall(serviceName, methodName, param, null);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.communication.client.CrmRemoteCallClient
 * JD-Core Version:    0.6.2
 */