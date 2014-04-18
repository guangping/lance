/*     */ package com.ztesoft.inf.communication.client;
/*     */ 
/*     */ import com.thoughtworks.xstream.XStream;
/*     */ import com.ztesoft.common.util.date.DateUtil;
/*     */ import com.ztesoft.inf.framework.commons.CodedException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.rpc.ParameterMode;
/*     */ import org.apache.axis.client.Call;
/*     */ import org.apache.axis.client.Service;
/*     */ import org.apache.axis.encoding.ser.BeanDeserializerFactory;
/*     */ import org.apache.axis.encoding.ser.BeanSerializerFactory;
/*     */ 
/*     */ public class ObjectInvoker extends Invoker
/*     */ {
/*     */   String qnameEncoding;
/*     */   String qname;
/*     */   String operClassPath;
/*     */   String operMethodName;
/*     */   String reqPath;
/*     */   String rspPath;
/*     */   String rspType;
/*     */ 
/*     */   public Object invoke(InvokeContext context)
/*     */     throws Exception
/*     */   {
/*  31 */     String operationCode = context.getOperationCode();
/*  32 */     Map param = (Map)context.getParameters();
/*  33 */     context.setEndpoint(this.endpoint);
/*  34 */     context.setRequestTime(DateUtil.currentTime());
/*  35 */     Object result = new Object();
/*     */     try {
/*  37 */       Object object = getClassObject(this.reqPath);
/*  38 */       Object request = getReqeust(this.reqPath, param);
/*     */ 
/*  41 */       Object resultObj = getClassObject(this.rspPath);
/*  42 */       Service service = new Service();
/*  43 */       Call call = (Call)service.createCall();
/*     */ 
/*  46 */       QName qn = new QName(this.qnameEncoding, this.qname);
/*     */ 
/*  48 */       call.registerTypeMapping(object.getClass(), qn, new BeanSerializerFactory(object.getClass(), qn), new BeanDeserializerFactory(object.getClass(), qn));
/*     */ 
/*  55 */       call.registerTypeMapping(resultObj.getClass(), qn, new BeanSerializerFactory(resultObj.getClass(), qn), new BeanDeserializerFactory(resultObj.getClass(), qn));
/*     */ 
/*  64 */       if ((this.rspType != null) && (!"".equals(this.rspType)) && (!"null".equals(this.rspType))) {
/*  65 */         String[] responseTypes = this.rspType.split("\\;");
/*     */ 
/*  67 */         for (String rspTypeString : responseTypes) {
/*  68 */           Object rspObject = getClassObject(rspTypeString);
/*  69 */           call.registerTypeMapping(rspObject.getClass(), qn, new BeanSerializerFactory(rspObject.getClass(), qn), new BeanDeserializerFactory(rspObject.getClass(), qn));
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*  78 */       call.setReturnType(new QName(resultObj.getClass().toString()), resultObj.getClass());
/*  79 */       call.setTargetEndpointAddress(new URL(this.endpoint));
/*  80 */       call.setOperationName(new QName(this.operClassPath, this.operMethodName));
/*  81 */       call.addParameter(operationCode, qn, ParameterMode.IN);
/*     */ 
/*  86 */       if (this.timeout != null)
/*  87 */         call.setTimeout(Integer.valueOf(this.timeout.intValue() * 1000));
/*     */       else
/*  89 */         call.setTimeout(Integer.valueOf(20000));
/*  90 */       result = call.invoke(new Object[] { request });
/*  91 */       context.setResponseTime(DateUtil.currentTime());
/*  92 */       context.setRequestString(new XStream().toXML(request));
/*  93 */       context.setResponeString(new XStream().toXML(result));
/*     */     } catch (Exception e) {
/*  95 */       e.printStackTrace();
/*  96 */       throw e;
/*     */     }
/*  98 */     return result;
/*     */   }
/*     */ 
/*     */   private Object getReqeust(String operationCode, Map context)
/*     */     throws Exception
/*     */   {
/* 110 */     Object object = new Object();
/*     */     try {
/* 112 */       object = getClassObject(operationCode);
/* 113 */       if (object != null) {
/* 114 */         Method[] methods = object.getClass().getMethods();
/* 115 */         for (Method method : methods) {
/* 116 */           String setFieldName = method.getName();
/* 117 */           if ((setFieldName != null) && (setFieldName.startsWith("set")))
/*     */           {
/* 120 */             Iterator itor = context.entrySet().iterator();
/* 121 */             while (itor.hasNext()) {
/* 122 */               Map.Entry map = (Map.Entry)itor.next();
/* 123 */               String key = String.valueOf(map.getKey());
/* 124 */               String setKey = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
/* 125 */               if (setKey.equals(setFieldName))
/* 126 */                 method.invoke(object, new Object[] { map.getValue() });
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 133 */       throw e;
/*     */     }
/* 135 */     return object;
/*     */   }
/*     */ 
/*     */   private Object getClassObject(String classPath) throws Exception {
/* 139 */     Object object = new Object();
/*     */     try {
/* 141 */       if ((classPath != null) && (!"".equals(classPath)))
/* 142 */         object = Class.forName(classPath).newInstance();
/*     */       else
/* 144 */         throw new CodedException("9999", "操作编码[" + classPath + "] 未能找到对应的类");
/*     */     }
/*     */     catch (Exception e) {
/* 147 */       throw e;
/*     */     }
/* 149 */     return object;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/*     */     try {
/* 154 */       Map map = new HashMap();
/* 155 */       CommCaller call = new CommCaller();
/*     */ 
/* 157 */       map.put("instanceType", "3");
/* 158 */       map.put("instanceId", "13307498133");
/* 159 */       map.put("longInstanceId", Long.valueOf(Long.parseLong("33813786")));
/* 160 */       call.invoke("bill.queryBalance", map);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 188 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.communication.client.ObjectInvoker
 * JD-Core Version:    0.6.2
 */