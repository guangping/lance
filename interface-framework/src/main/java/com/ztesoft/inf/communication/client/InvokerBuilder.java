/*     */ package com.ztesoft.inf.communication.client;
/*     */ 
/*     */ import com.ztesoft.common.util.StringUtils;
/*     */ import com.ztesoft.inf.communication.client.bo.CommClientBO;
/*     */ import com.ztesoft.inf.communication.client.vo.ClientEndPoint;
/*     */ import com.ztesoft.inf.communication.client.vo.ClientGlobalVar;
/*     */ import com.ztesoft.inf.communication.client.vo.ClientOperation;
/*     */ import com.ztesoft.inf.communication.client.vo.ClientRequest;
/*     */ import com.ztesoft.inf.communication.client.vo.ClientRequestUser;
/*     */ import com.ztesoft.inf.communication.client.vo.ClientResponse;
/*     */ import com.ztesoft.inf.extend.freemarker.TemplateUtils;
/*     */ import com.ztesoft.inf.extend.xstream.mapper.MapperContext;
/*     */ import com.ztesoft.inf.extend.xstream.mapper.MapperContextBuilder;
/*     */ import com.ztesoft.inf.framework.commons.CodedException;
/*     */ import freemarker.template.Template;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class InvokerBuilder
/*     */ {
/*  25 */   private CommClientBO commBO = new CommClientBO();
/*  26 */   private MapperContextBuilder mapperCtxBuilder = new MapperContextBuilder();
/*  27 */   private Pattern nsPattern = Pattern.compile("^namespace\\.(\\w+)=(.+)");
/*     */ 
/*     */   public Invoker buildInvoker(String operationCode) throws Exception {
/*  30 */     ClientOperation operation = this.commBO.getOperationByCode(operationCode);
/*  31 */     if (operation == null) {
/*  32 */       throw new CodedException("9001", "该操作调用[" + operationCode + "]未进行配置,请检查!");
/*     */     }
/*     */ 
/*  35 */     return buildInvoker(operation);
/*     */   }
/*     */ 
/*     */   private Invoker buildInvoker(ClientOperation operation) throws Exception
/*     */   {
/*  40 */     if (operation.isClosed()) {
/*  41 */       return null;
/*     */     }
/*     */ 
/*  44 */     ClientEndPoint ep = this.commBO.getEndPoint(operation.getEndpointId());
/*     */ 
/*  46 */     ClientRequestUser reqUser = this.commBO.getRequestUser(operation.getReqUserId());
/*     */ 
/*  49 */     if (ep == null) {
/*  50 */       throw new CodedException("", "该操作[" + operation.getOperationCode() + "]调用请求地址[" + operation.getEndpointId() + "]未进行配置,请检查!");
/*     */     }
/*     */ 
/*  54 */     ClientRequest request = this.commBO.getRequest(operation.getRequestId());
/*     */ 
/*  56 */     if (request == null) {
/*  57 */       throw new CodedException("", "该操作[" + operation.getOperationCode() + "]调用请求报文[" + operation.getRequestId() + "]未进行配置,请检查!");
/*     */     }
/*     */ 
/*  61 */     ClientResponse response = this.commBO.getResponse(operation.getResponseId());
/*  62 */     if (response == null) {
/*  63 */       throw new CodedException("", "该操作[" + operation.getOperationCode() + "]调用反馈报文[" + operation.getResponseId() + "]未进行配置,请检查!");
/*     */     }
/*     */ 
/*  67 */     ClientGlobalVar gvars = this.commBO.getGlobalVars(request.getGlobalVarsId());
/*     */ 
/*  69 */     Invoker invoker = null;
/*     */ 
/*  71 */     if ("SOCKET".equalsIgnoreCase(ep.getType())) {
/*  72 */       invoker = new SocketInvoker();
/*  73 */     } else if ("HttpUrl".equalsIgnoreCase(ep.getType())) {
/*  74 */       invoker = new HttpUrlInvoker();
/*  75 */     } else if ("HttpClient".equalsIgnoreCase(ep.getType())) {
/*  76 */       invoker = new HttpClientInvoker();
/*  77 */     } else if ("HttpJson".equalsIgnoreCase(ep.getType())) {
/*  78 */       invoker = new HttpJsonInvoker();
/*  79 */     } else if ("ASMXWEB".equalsIgnoreCase(ep.getType())) {
/*  80 */       invoker = new AsmxWebInvoker();
/*  81 */     } else if ("RMI".equalsIgnoreCase(ep.getType())) {
/*  82 */       invoker = new RMIInvoker();
/*  83 */     } else if ("Object".equalsIgnoreCase(ep.getType())) {
/*  84 */       invoker = new ObjectInvoker();
/*  85 */     } else if ("Axis".equalsIgnoreCase(ep.getType())) {
/*  86 */       invoker = new AxisInvoker();
/*  87 */       AxisInvoker objectInvoker = (AxisInvoker)invoker;
/*  88 */       objectInvoker.reqPath = request.getClassPath();
/*  89 */       objectInvoker.operClassPath = request.getOperClassName();
/*  90 */       objectInvoker.operMethodName = request.getOperMethod();
/*  91 */       objectInvoker.qname = request.getQname();
/*  92 */       objectInvoker.qnameEncoding = request.getQnameEncode();
/*  93 */       objectInvoker.rspPath = response.getRspPath();
/*  94 */       objectInvoker.endpoint = ep.getEndpointAddress();
/*  95 */     } else if ("StringObject".equalsIgnoreCase(ep.getType())) {
/*  96 */       invoker = new StringObjectInvoker();
/*     */     } else {
/*  98 */       invoker = new WsInvoker();
/*     */     }
/*     */ 
/* 101 */     invoker.endpoint = ep.getEndpointAddress();
/* 102 */     invoker.timeout = ep.getTimeout();
/* 103 */     invoker.transformFault = response.isTransformFault();
/* 104 */     invoker.logColMap = this.commBO.getLogColsByOpId(operation.getOperationId());
/* 105 */     invoker.requestTemplate = buildRequestTemplate(request);
/* 106 */     invoker.transformTemplate = buildTransformTemplate(response);
/* 107 */     invoker.mapperContext = buildMapperContext(response);
/* 108 */     invoker.globalVars = buildGlobalVars(gvars);
/* 109 */     invoker.setLogLevel(operation.getLogLevel());
/* 110 */     invoker.setReqUser(reqUser);
/* 111 */     invoker.setOperation(operation);
/* 112 */     if ("SOCKET".equalsIgnoreCase(ep.getType())) {
/* 113 */       SocketInvoker socketInvoker = (SocketInvoker)invoker;
/* 114 */       String endpoint = ep.getEndpointAddress();
/*     */       try
/*     */       {
/* 117 */         String[] parts = endpoint.split(":");
/* 118 */         socketInvoker.endpoint = parts[0];
/* 119 */         socketInvoker.port = Integer.parseInt(parts[1]);
/*     */       } catch (Exception e) {
/* 121 */         throw new CodedException("9999", "SOCKET地址错误，地址格式为IP:PORT");
/*     */       }
/* 123 */     } else if ("ASMXWEB".equalsIgnoreCase(ep.getType())) {
/* 124 */       invoker.endpoint = ep.getEndpointAddress();
/* 125 */       buildCdataInfo(response, (AsmxWebInvoker)invoker);
/*     */     }
/*     */     else
/*     */     {
/*     */       RMIInvoker rmiInvoker;
/* 126 */       if ("RMI".equalsIgnoreCase(ep.getType())) {
/* 127 */         rmiInvoker = (RMIInvoker)invoker;
/*     */       }
/*     */       else
/*     */       {
/*     */         AxisInvoker rmiInvoker;
/* 128 */         if ("Axis".equalsIgnoreCase(ep.getType())) {
/* 129 */           rmiInvoker = (AxisInvoker)invoker;
/* 130 */         } else if ("Object".equalsIgnoreCase(ep.getType())) {
/* 131 */           ObjectInvoker objectInvoker = (ObjectInvoker)invoker;
/* 132 */           objectInvoker.reqPath = request.getClassPath();
/* 133 */           objectInvoker.operClassPath = request.getOperClassName();
/* 134 */           objectInvoker.operMethodName = request.getOperMethod();
/* 135 */           objectInvoker.qname = request.getQname();
/* 136 */           objectInvoker.qnameEncoding = request.getQnameEncode();
/* 137 */           objectInvoker.rspPath = response.getRspPath();
/* 138 */           invoker.endpoint = ep.getEndpointAddress();
/* 139 */           objectInvoker.rspType = response.getRspType();
/*     */         }
/* 141 */         else if ("StringObject".equalsIgnoreCase(ep.getType())) {
/* 142 */           buildCdataInfo(response, (StringObjectInvoker)invoker);
/* 143 */         } else if ("WsInvoker".equalsIgnoreCase(ep.getType())) {
/* 144 */           buildCdataInfo(response, (WsInvoker)invoker);
/*     */         }
/*     */       }
/*     */     }
/* 147 */     return invoker;
/*     */   }
/*     */ 
/*     */   private void buildCdataInfo(ClientResponse response, AsmxWebInvoker invoker) {
/* 151 */     String cdata = response.getCdataPath();
/* 152 */     if (StringUtils.isEmpty(cdata)) {
/* 153 */       return;
/*     */     }
/*     */ 
/* 156 */     String[] lines = StringUtils.splitLines(cdata);
/* 157 */     invoker.cdataNamespaces = new HashMap();
/*     */ 
/* 159 */     for (String line : lines) {
/* 160 */       Matcher matcher = this.nsPattern.matcher(line);
/* 161 */       if (matcher.matches())
/* 162 */         invoker.cdataNamespaces.put(matcher.group(1), matcher.group(2));
/*     */       else
/* 164 */         invoker.cdataPath = line;
/*     */     }
/*     */   }
/*     */ 
/*     */   private Map buildGlobalVars(ClientGlobalVar gvars)
/*     */   {
/* 170 */     Map globalVars = new HashMap();
/* 171 */     if ((gvars != null) && (!StringUtils.isEmpty(gvars.getGvarsDefinition()))) {
/* 172 */       globalVars = StringUtils.splitLinesIntoMap(gvars.getGvarsDefinition(), "=");
/*     */     }
/*     */ 
/* 175 */     return globalVars;
/*     */   }
/*     */ 
/*     */   private void buildCdataInfo(ClientResponse response, WsInvoker invoker) {
/* 179 */     String cdata = response.getCdataPath();
/* 180 */     if (StringUtils.isEmpty(cdata)) {
/* 181 */       return;
/*     */     }
/*     */ 
/* 184 */     String[] lines = StringUtils.splitLines(cdata);
/* 185 */     invoker.cdataNamespaces = new HashMap();
/*     */ 
/* 187 */     for (String line : lines) {
/* 188 */       Matcher matcher = this.nsPattern.matcher(line);
/* 189 */       if (matcher.matches())
/* 190 */         invoker.cdataNamespaces.put(matcher.group(1), matcher.group(2));
/*     */       else
/* 192 */         invoker.cdataPath = line;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void buildCdataInfo(ClientResponse response, StringObjectInvoker invoker)
/*     */   {
/* 199 */     String cdata = response.getCdataPath();
/* 200 */     if (StringUtils.isEmpty(cdata)) {
/* 201 */       return;
/*     */     }
/*     */ 
/* 204 */     String[] lines = StringUtils.splitLines(cdata);
/* 205 */     invoker.cdataNamespaces = new HashMap();
/*     */ 
/* 207 */     for (String line : lines) {
/* 208 */       Matcher matcher = this.nsPattern.matcher(line);
/* 209 */       if (matcher.matches())
/* 210 */         invoker.cdataNamespaces.put(matcher.group(1), matcher.group(2));
/*     */       else
/* 212 */         invoker.cdataPath = line;
/*     */     }
/*     */   }
/*     */ 
/*     */   private MapperContext buildMapperContext(ClientResponse response)
/*     */   {
/* 218 */     if (StringUtils.isEmpty(response.getXmlMapper()))
/*     */     {
/* 220 */       return new MapperContext();
/*     */     }
/* 222 */     return this.mapperCtxBuilder.build(response.getXmlMapper());
/*     */   }
/*     */ 
/*     */   private Template buildTransformTemplate(ClientResponse response) {
/* 226 */     if (StringUtils.isEmpty(response.getTransform()))
/* 227 */       return null;
/*     */     try
/*     */     {
/* 230 */       return TemplateUtils.createTemplate(response.getTransform());
/*     */     } catch (Exception e) {
/* 232 */       StringUtils.printInfo("buildTransformTemplate==" + response.getTransform());
/*     */ 
/* 234 */       throw new CodedException("9999", "创建响应报文转换模板[" + response.getResponseId() + "]出错.", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private Template buildRequestTemplate(ClientRequest request)
/*     */   {
/*     */     try {
/* 241 */       if (!StringUtils.isEmpty(request.getTemplate())) {
/* 242 */         return TemplateUtils.createTemplate(request.getTemplate());
/*     */       }
/* 244 */       return null;
/*     */     }
/*     */     catch (Exception e) {
/* 247 */       StringUtils.printInfo("buildRequestTemplate=" + request.getTemplate());
/*     */ 
/* 249 */       throw new CodedException("9999", "创建请求报文模板[" + request.getRequestId() + "]出错", e);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.communication.client.InvokerBuilder
 * JD-Core Version:    0.6.2
 */