/*     */ package com.ztesoft.inf.communication.client;
/*     */ 
/*     */ import com.ztesoft.common.util.StringUtils;
/*     */ import com.ztesoft.config.ParamsConfig;
/*     */ import com.ztesoft.inf.communication.client.bo.CommClientBO;
/*     */ import com.ztesoft.inf.framework.cache.Cache;
/*     */ import com.ztesoft.inf.framework.cache.CacheItemCreateCallback;
/*     */ import com.ztesoft.inf.framework.cache.CacheManager;
/*     */ import com.ztesoft.inf.framework.cache.LRUMap;
/*     */ import com.ztesoft.inf.framework.commons.CodedException;
/*     */ import com.ztesoft.inf.framework.logger.AppLogContext;
/*     */ import com.ztesoft.inf.framework.logger.AppLogger;
/*     */ import com.ztesoft.inf.framework.logger.LogThread;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class CommCaller
/*     */ {
/*  28 */   private static Logger logger = Logger.getLogger(CommCaller.class);
/*     */   private static final String LOGLEVEL_NONE = "NONE";
/*     */   private static final String LOGLEVEL_ERROR = "ERROR";
/*     */   private static final String LOGLEVEL_ALL_NOXML = "ALL_NOXML";
/*  36 */   private static Cache<String, Invoker> invokers = CacheManager.getCache("WSCLINET_INVOKER", LRUMap.class);
/*     */ 
/*  38 */   private static InvokerBuilder invokerBuilder = new InvokerBuilder();
/*     */ 
/*     */   public Object invoke(String operationCode, Object params)
/*     */   {
/*  49 */     return invoke(operationCode, params, null);
/*     */   }
/*     */ 
/*     */   public Object invoke(String operationCode, Object params, String endpointAddress)
/*     */   {
/*  66 */     Invoker invoker = getInvoker(operationCode);
/*     */ 
/*  68 */     if (invoker == null) {
/*  69 */       StringUtils.printInfo("operation [" + operationCode + "] is closed !");
/*     */ 
/*  71 */       return InfCloed.getInstance();
/*     */     }
/*     */ 
/*  74 */     if (!StringUtils.isEmpty(endpointAddress))
/*  75 */       invoker.endpoint = endpointAddress;
/*     */     InvokeContext context;
/*     */     InvokeContext context;
/*  79 */     if ((invoker instanceof WsInvoker)) {
/*  80 */       context = new WsInvokeContext();
/*     */     }
/*     */     else
/*     */     {
/*     */       InvokeContext context;
/*  81 */       if ((invoker instanceof StringObjectInvoker)) {
/*  82 */         context = new WsInvokeContext();
/*     */       }
/*     */       else
/*     */       {
/*     */         InvokeContext context;
/*  83 */         if ((invoker instanceof AsmxWebInvoker))
/*  84 */           context = new AsmxWebInvokeContext();
/*     */         else
/*  86 */           context = new InvokeContext();
/*     */       }
/*     */     }
/*  89 */     context.setParameters(params);
/*  90 */     context.setOperationCode(operationCode);
/*     */     Object retObj;
/*     */     try
/*     */     {
/*     */       Object retObj;
/*  94 */       if (ParamsConfig.isDebug())
/*  95 */         retObj = invoker.invokeTest(context);
/*     */       else {
/*  97 */         retObj = invoker.invoke(context);
/*     */       }
/*  99 */       logger.debug("req= " + context.getRequestString());
/* 100 */       logger.debug("rsp= " + context.getResultString());
/* 101 */       logger.debug("rsp= " + context.getResponeString());
/*     */     }
/*     */     catch (Exception e) {
/* 104 */       context.setFailure(e.getMessage());
/* 105 */       throw new CodedException("9999", "调用服务[" + operationCode + "]出错:" + e.getMessage(), e);
/*     */     }
/*     */     finally {
/*     */       try {
/* 109 */         logServiceCall(invoker.getLogLevel(), invoker.getLogColMap(), context);
/*     */       }
/*     */       catch (Exception e) {
/* 112 */         logger.error("记录调用日志出错[" + context.getOperationCode() + "]", e);
/*     */       }
/*     */     }
/*     */ 
/* 116 */     return retObj;
/*     */   }
/*     */ 
/*     */   private void logServiceCall(String logLevel, Map logColMap, InvokeContext context)
/*     */     throws Exception
/*     */   {
/* 167 */     if ("NONE".equals(logLevel)) {
/* 168 */       return;
/*     */     }
/* 170 */     log(logLevel, logColMap, context);
/*     */   }
/*     */ 
/*     */   private void log(String logLevel, Map logColMap, InvokeContext context) throws Exception
/*     */   {
/* 175 */     String fault = context.getFailure();
/* 176 */     if ((fault == null) && ("ERROR".equals(logLevel))) {
/* 177 */       return;
/*     */     }
/* 179 */     List list = new ArrayList();
/* 180 */     list.add(context.getRequestTime());
/* 181 */     list.add(context.getResponseTime());
/* 182 */     list.add(context.getOperationCode());
/* 183 */     list.add(context.getEndpoint());
/* 184 */     if ((fault == null) && ("ALL_NOXML".equals(logLevel))) {
/* 185 */       list.add("");
/* 186 */       list.add("");
/*     */     } else {
/* 188 */       list.add(context.getRequestString());
/* 189 */       list.add(context.getResponeString());
/*     */     }
/* 191 */     list.add(fault != null ? "1" : "0");
/* 192 */     list.add(fault);
/* 193 */     list.add(context.paramsAsString());
/* 194 */     list.add(context.getResultString());
/* 195 */     list.add(getLogValue(logColMap, "col1", context.getParameters()));
/* 196 */     list.add(getLogValue(logColMap, "col2", context.getParameters()));
/* 197 */     list.add(getLogValue(logColMap, "col3", context.getParameters()));
/* 198 */     list.add(getLogValue(logColMap, "col4", context.getParameters()));
/* 199 */     list.add(getLogValue(logColMap, "col5", context.getParameters()));
/*     */ 
/* 201 */     LogThread.addLogQueue(new AppLogContext(new AppLogger() {
/*     */       public void log(Object logObj) throws Exception {
/*     */         try {
/* 204 */           new CommClientBO().logCall((List)logObj);
/*     */         } catch (Throwable e) {
/* 206 */           e.printStackTrace();
/* 207 */           CommCaller.logger.error("记录日志时失败:" + e.getMessage(), e);
/*     */         }
/*     */       }
/*     */     }
/*     */     , list));
/*     */   }
/*     */ 
/*     */   private Object getLogValue(Map logColMap, String colName, Object parameters)
/*     */   {
/* 214 */     Object value = null;
/* 215 */     if (((parameters instanceof Map)) && 
/* 216 */       (logColMap.get(colName) != null)) {
/* 217 */       value = ((Map)parameters).get(logColMap.get(colName));
/*     */     }
/* 219 */     return value != null ? String.valueOf(value) : "";
/*     */   }
/*     */ 
/*     */   private synchronized Invoker getInvoker(final String operationCode) {
/*     */     try {
/* 224 */       return (Invoker)invokers.get(operationCode, new CacheItemCreateCallback()
/*     */       {
/*     */         public Invoker create() throws Exception {
/* 227 */           return CommCaller.invokerBuilder.buildInvoker(operationCode);
/*     */         }
/*     */       });
/*     */     }
/*     */     catch (Exception e) {
/* 232 */       throw new CodedException("9999", "创建Service[" + operationCode + "]调用客户端失败", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object invokeCreateResXml(String operationCode, Object params, String endpointAddress)
/*     */   {
/* 238 */     new ParamsConfig(); ParamsConfig.initParams("");
/* 239 */     Invoker invoker = getInvoker(operationCode);
/*     */ 
/* 241 */     if (invoker == null) {
/* 242 */       StringUtils.printInfo("operation [" + operationCode + "] is closed !");
/*     */ 
/* 244 */       return InfCloed.getInstance();
/*     */     }
/*     */ 
/* 247 */     if (!StringUtils.isEmpty(endpointAddress))
/* 248 */       invoker.endpoint = endpointAddress;
/*     */     InvokeContext context;
/*     */     InvokeContext context;
/* 252 */     if ((invoker instanceof WsInvoker)) {
/* 253 */       context = new WsInvokeContext();
/*     */     }
/*     */     else
/*     */     {
/*     */       InvokeContext context;
/* 254 */       if ((invoker instanceof StringObjectInvoker)) {
/* 255 */         context = new WsInvokeContext();
/*     */       }
/*     */       else
/*     */       {
/*     */         InvokeContext context;
/* 256 */         if ((invoker instanceof AsmxWebInvoker))
/* 257 */           context = new AsmxWebInvokeContext();
/*     */         else
/* 259 */           context = new InvokeContext();
/*     */       }
/*     */     }
/* 262 */     context.setParameters(params);
/* 263 */     context.setOperationCode(operationCode);
/*     */     Object retObj;
/*     */     try
/*     */     {
/*     */       Object retObj;
/* 267 */       if (ParamsConfig.isDebug())
/* 268 */         retObj = invoker.invokeTestXml(context);
/*     */       else {
/* 270 */         retObj = invoker.invokeTestXml(context);
/*     */       }
/* 272 */       logger.debug("req= " + context.getRequestString());
/* 273 */       logger.debug("rsp= " + context.getResultString());
/* 274 */       logger.debug("rsp= " + context.getResponeString());
/*     */     }
/*     */     catch (Exception e) {
/* 277 */       context.setFailure(e.getMessage());
/* 278 */       throw new CodedException("9999", "调用服务[" + operationCode + "]出错:" + e.getMessage(), e);
/*     */     }
/*     */ 
/* 290 */     return retObj;
/*     */   }
/*     */ 
/*     */   public Object invokeCreateResMap(String operationCode, String xml, String endpointAddress) {
/* 294 */     new ParamsConfig(); ParamsConfig.initParams("");
/* 295 */     Invoker invoker = getInvoker(operationCode);
/*     */ 
/* 297 */     if (invoker == null) {
/* 298 */       StringUtils.printInfo("operation [" + operationCode + "] is closed !");
/*     */ 
/* 300 */       return InfCloed.getInstance();
/*     */     }
/*     */ 
/* 303 */     if (!StringUtils.isEmpty(endpointAddress))
/* 304 */       invoker.endpoint = endpointAddress;
/*     */     InvokeContext context;
/*     */     InvokeContext context;
/* 308 */     if ((invoker instanceof WsInvoker)) {
/* 309 */       context = new WsInvokeContext();
/*     */     }
/*     */     else
/*     */     {
/*     */       InvokeContext context;
/* 310 */       if ((invoker instanceof StringObjectInvoker)) {
/* 311 */         context = new WsInvokeContext();
/*     */       }
/*     */       else
/*     */       {
/*     */         InvokeContext context;
/* 312 */         if ((invoker instanceof AsmxWebInvoker))
/* 313 */           context = new AsmxWebInvokeContext();
/*     */         else {
/* 315 */           context = new InvokeContext();
/*     */         }
/*     */       }
/*     */     }
/* 319 */     context.setResponeString(xml);
/* 320 */     context.setOperationCode(operationCode);
/*     */     Object retObj;
/*     */     try
/*     */     {
/*     */       Object retObj;
/* 324 */       if (ParamsConfig.isDebug())
/* 325 */         retObj = invoker.invokeTestToMap(context);
/*     */       else {
/* 327 */         retObj = invoker.invokeTestToMap(context);
/*     */       }
/* 329 */       logger.debug("req= " + context.getRequestString());
/* 330 */       logger.debug("rsp= " + context.getResultString());
/* 331 */       logger.debug("rsp= " + context.getResponeString());
/*     */     }
/*     */     catch (Exception e) {
/* 334 */       context.setFailure(e.getMessage());
/* 335 */       throw new CodedException("9999", "调用服务[" + operationCode + "]出错:" + e.getMessage(), e);
/*     */     }
/*     */ 
/* 347 */     return retObj;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  30 */     ParamsConfig.initParams(null);
/*     */   }
/*     */ 
/*     */   public static class CallLogger
/*     */   {
/* 121 */     static CommClientBO wsClientBO = new CommClientBO();
/*     */ 
/*     */     public void log(String logLevel, Map logColMap, InvokeContext context) throws Exception
/*     */     {
/* 125 */       String fault = context.getFailure();
/* 126 */       if ((fault == null) && ("ERROR".equals(logLevel))) {
/* 127 */         return;
/*     */       }
/* 129 */       List list = new ArrayList();
/* 130 */       list.add(context.getRequestTime());
/* 131 */       list.add(context.getResponseTime());
/* 132 */       list.add(context.getOperationCode());
/* 133 */       list.add(context.getEndpoint());
/* 134 */       if ((fault == null) && ("ALL_NOXML".equals(logLevel))) {
/* 135 */         list.add("");
/* 136 */         list.add("");
/*     */       } else {
/* 138 */         list.add(context.getRequestString());
/* 139 */         list.add(context.getResponeString());
/*     */       }
/* 141 */       list.add(fault != null ? "1" : "0");
/* 142 */       list.add(fault);
/* 143 */       list.add(context.paramsAsString());
/* 144 */       list.add(context.getResultString());
/* 145 */       list.add(getLogValue(logColMap, "col1", context.getParameters()));
/* 146 */       list.add(getLogValue(logColMap, "col2", context.getParameters()));
/* 147 */       list.add(getLogValue(logColMap, "col3", context.getParameters()));
/* 148 */       list.add(getLogValue(logColMap, "col4", context.getParameters()));
/* 149 */       list.add(getLogValue(logColMap, "col5", context.getParameters()));
/* 150 */       wsClientBO.logCall(list);
/*     */     }
/*     */ 
/*     */     private Object getLogValue(Map logColMap, String colName, Object parameters)
/*     */     {
/* 155 */       Object value = null;
/* 156 */       if (((parameters instanceof Map)) && 
/* 157 */         (logColMap.get(colName) != null)) {
/* 158 */         value = ((Map)parameters).get(logColMap.get(colName));
/*     */       }
/* 160 */       return value != null ? String.valueOf(value) : "";
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.communication.client.CommCaller
 * JD-Core Version:    0.6.2
 */