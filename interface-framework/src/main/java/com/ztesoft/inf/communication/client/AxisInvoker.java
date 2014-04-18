/*     */ package com.ztesoft.inf.communication.client;
/*     */ 
/*     */ import com.thoughtworks.xstream.XStream;
/*     */ import com.ztesoft.common.util.Clazz;
/*     */ import com.ztesoft.common.util.StringUtils;
/*     */ import com.ztesoft.common.util.date.DateUtil;
/*     */ import com.ztesoft.inf.framework.commons.CodedException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.apache.axis.client.Stub;
/*     */ 
/*     */ public class AxisInvoker extends Invoker
/*     */ {
/*     */   String qnameEncoding;
/*     */   String qname;
/*     */   String operClassPath;
/*     */   String operMethodName;
/*     */   String reqPath;
/*     */   String rspPath;
/*     */ 
/*     */   public Object invoke(InvokeContext context)
/*     */     throws Exception
/*     */   {
/*  26 */     String operationCode = context.getOperationCode();
/*  27 */     Object param = context.getParameters();
/*  28 */     context.setEndpoint(this.endpoint);
/*  29 */     context.setRequestTime(DateUtil.currentTime());
/*  30 */     Object result = new Object();
/*     */     try {
/*  32 */       Object object = getClassObject(this.operClassPath);
/*     */ 
/*  34 */       Stub _stub = (Stub)object;
/*     */ 
/*  37 */       if (this.timeout != null)
/*  38 */         _stub.setTimeout(this.timeout.intValue() * 1000);
/*     */       else {
/*  40 */         _stub.setTimeout(30000);
/*     */       }
/*     */ 
/*  43 */       StringUtils.setObjectValue(_stub, "cachedEndpoint", new URL(this.endpoint));
/*  44 */       Method method = Clazz.findMethod(object.getClass(), this.operMethodName);
/*  45 */       if (method == null) {
/*  46 */         throw new CodedException("9999", "操作编码[" + operationCode + "] 对应的[" + this.reqPath + "]类方法[" + this.operMethodName + "]");
/*     */       }
/*  48 */       result = method.invoke(object, new Object[] { param });
/*     */ 
/*  50 */       context.setResponseTime(DateUtil.currentTime());
/*  51 */       context.setRequestString(new XStream().toXML(param));
/*  52 */       context.setResponeString(new XStream().toXML(result));
/*     */     } catch (Exception e) {
/*  54 */       e.printStackTrace();
/*  55 */       throw e;
/*     */     }
/*  57 */     return result;
/*     */   }
/*     */ 
/*     */   private Object getReqeust(String operationCode, Map context)
/*     */     throws Exception
/*     */   {
/*  69 */     Object object = new Object();
/*     */     try {
/*  71 */       object = getClassObject(operationCode);
/*  72 */       if (object != null) {
/*  73 */         Method[] methods = object.getClass().getMethods();
/*  74 */         for (Method method : methods) {
/*  75 */           String setFieldName = method.getName();
/*  76 */           if ((setFieldName != null) && (setFieldName.startsWith("set")))
/*     */           {
/*  79 */             Iterator itor = context.entrySet().iterator();
/*  80 */             while (itor.hasNext()) {
/*  81 */               Map.Entry map = (Map.Entry)itor.next();
/*  82 */               String key = String.valueOf(map.getKey());
/*  83 */               String setKey = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
/*  84 */               if (setKey.equals(setFieldName))
/*  85 */                 method.invoke(object, new Object[] { map.getValue() });
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/*  92 */       throw e;
/*     */     }
/*  94 */     return object;
/*     */   }
/*     */ 
/*     */   private Object getClassObject(String classPath) throws Exception {
/*  98 */     Object object = new Object();
/*     */     try {
/* 100 */       if ((classPath != null) && (!"".equals(classPath)))
/* 101 */         object = Class.forName(classPath).newInstance();
/*     */       else
/* 103 */         throw new CodedException("9999", "操作编码[" + classPath + "] 未能找到对应的类");
/*     */     }
/*     */     catch (Exception e) {
/* 106 */       throw e;
/*     */     }
/* 108 */     return object;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/*     */     try {
/* 113 */       Map map = new HashMap();
/* 114 */       CommCaller call = new CommCaller();
/*     */ 
/* 116 */       map.put("instanceType", "3");
/* 117 */       map.put("instanceId", "13307498133");
/* 118 */       map.put("longInstanceId", Long.valueOf(Long.parseLong("33813786")));
/* 119 */       call.invoke("bill.queryBalance", map);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 147 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.communication.client.AxisInvoker
 * JD-Core Version:    0.6.2
 */