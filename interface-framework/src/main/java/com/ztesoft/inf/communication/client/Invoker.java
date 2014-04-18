/*     */ package com.ztesoft.inf.communication.client;
/*     */ 
/*     */ import com.ztesoft.common.util.BeanUtils;
/*     */ import com.ztesoft.common.util.StringUtils;
/*     */ import com.ztesoft.common.util.file.FileUtil;
/*     */ import com.ztesoft.config.ParamsConfig;
/*     */ import com.ztesoft.inf.communication.client.util.DomUtils;
/*     */ import com.ztesoft.inf.communication.client.vo.ClientOperation;
/*     */ import com.ztesoft.inf.communication.client.vo.ClientRequestUser;
/*     */ import com.ztesoft.inf.extend.freemarker.TemplateUtils;
/*     */ import com.ztesoft.inf.extend.xstream.XStream;
/*     */ import com.ztesoft.inf.extend.xstream.mapper.MapperContext;
/*     */ import com.ztesoft.inf.framework.commons.CachedNamespaceContext;
/*     */ import com.ztesoft.inf.framework.commons.CodedException;
/*     */ import freemarker.ext.dom.NodeModel;
/*     */ import freemarker.template.Template;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.StringWriter;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.xpath.XPath;
/*     */ import javax.xml.xpath.XPathFactory;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.w3c.dom.Document;
/*     */ 
/*     */ public abstract class Invoker
/*     */ {
/*     */   String endpoint;
/*     */   String logLevel;
/*     */   Map globalVars;
/*     */   boolean transformFault;
/*     */   Integer timeout;
/*     */   Template requestTemplate;
/*     */   Template transformTemplate;
/*     */   MapperContext mapperContext;
/*     */   Map logColMap;
/*     */   ClientRequestUser reqUser;
/*     */   ClientOperation operation;
/*  45 */   private static Logger logger = Logger.getLogger(Invoker.class);
/*     */ 
/*     */   public abstract Object invoke(InvokeContext paramInvokeContext) throws Exception;
/*     */ 
/*     */   public Object invokeTest(InvokeContext context) throws Exception
/*     */   {
/*  51 */     String testXmlPath = ParamsConfig.getInstance().getParamValue("TestXmlPath");
/*     */ 
/*  54 */     String reqStr = FileUtil.readFileContent(testXmlPath + this.operation.getRequestId() + ".xml");
/*     */ 
/*  56 */     logger.debug("reqXmlExm====>\n" + reqStr);
/*     */ 
/*  59 */     String reqString = generateRequestString(context);
/*  60 */     logger.debug("genReqXml====>\n" + reqString);
/*  61 */     context.setRequestString(reqString);
/*     */ 
/*  64 */     String rspStr = FileUtil.readFileContent(testXmlPath + this.operation.getResponseId() + ".xml");
/*     */ 
/*  66 */     logger.debug("rspXmlExm====>\n" + rspStr);
/*  67 */     context.setResponeString(rspStr);
/*  68 */     if (StringUtils.isEmpty(rspStr))
/*     */     {
/*  70 */       throw new Exception("没有返回报文" + testXmlPath + this.operation.getResponseId() + ".xml");
/*     */     }
/*     */ 
/*  75 */     Object result = dealResult(context);
/*  76 */     logger.debug("result====>\n" + result.toString());
/*  77 */     return result;
/*     */   }
/*     */ 
/*     */   protected Object dealResult(InvokeContext context)
/*     */   {
/*  83 */     String resultStr = context.getResponeString();
/*  84 */     Document resultDoc = DomUtils.newDocument(resultStr, false);
/*  85 */     String cdataPath = null;
/*  86 */     Map dataNamespaces = null;
/*  87 */     Map cdataNamespaces = null;
/*  88 */     XStream xstream = XStream.instance();
/*  89 */     XPath xpath = XPathFactory.newInstance().newXPath();
/*     */     try
/*     */     {
/*  92 */       if (!StringUtils.isEmpty(cdataPath)) {
/*  93 */         CachedNamespaceContext nsCtx = new CachedNamespaceContext();
/*  94 */         nsCtx.putInCache(cdataNamespaces);
/*  95 */         xpath.setNamespaceContext(nsCtx);
/*  96 */         String cdataContent = xpath.evaluate(cdataPath, resultDoc);
/*     */ 
/*  98 */         if (StringUtils.isEmpty(cdataContent))
/*  99 */           return new HashMap();
/*     */         try
/*     */         {
/* 102 */           resultDoc = DomUtils.newDocument(cdataContent, false);
/*     */         }
/*     */         catch (CodedException e) {
/* 105 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 109 */       throw new CodedException("9002", "根据返回的SOAP报文取业务报文[" + context.getOperationCode() + "]时出错.", e);
/*     */     }
/*     */ 
/* 114 */     StringWriter out = new StringWriter();
/*     */     Object result;
/*     */     try {
/* 116 */       if (this.transformTemplate != null) {
/* 117 */         Map rootMap = new HashMap();
/* 118 */         rootMap.put("doc", NodeModel.wrap(resultDoc));
/* 119 */         TemplateUtils.addUtils(rootMap);
/* 120 */         this.transformTemplate.process(rootMap, out);
/* 121 */         resultStr = out.toString();
/* 122 */         context.setResultString(resultStr);
/*     */       }
/* 124 */       result = xstream.fromXML(resultStr, this.mapperContext);
/*     */ 
/* 126 */       System.out.println(result);
/*     */     } catch (Exception e) {
/* 128 */       throw new CodedException("9003", "根据反馈的业务报文转为目标格式[" + context.getOperationCode() + "]时出错.", e);
/*     */     }
/*     */ 
/* 131 */     return result;
/*     */   }
/*     */ 
/*     */   public Map getLogColMap() {
/* 135 */     return this.logColMap;
/*     */   }
/*     */ 
/*     */   public String getEndpoint() {
/* 139 */     return this.endpoint;
/*     */   }
/*     */ 
/*     */   public void setLogLevel(String logLevel) {
/* 143 */     this.logLevel = logLevel;
/*     */   }
/*     */ 
/*     */   public String getLogLevel() {
/* 147 */     return this.logLevel;
/*     */   }
/*     */ 
/*     */   public ClientRequestUser getReqUser() {
/* 151 */     return this.reqUser;
/*     */   }
/*     */ 
/*     */   public ClientOperation getOperation() {
/* 155 */     return this.operation;
/*     */   }
/*     */ 
/*     */   public void setOperation(ClientOperation operation) {
/* 159 */     this.operation = operation;
/*     */   }
/*     */ 
/*     */   public void setReqUser(ClientRequestUser reqUser) {
/* 163 */     this.reqUser = reqUser;
/*     */   }
/*     */ 
/*     */   protected String generateRequestString(InvokeContext context)
/*     */   {
/* 168 */     Object params = context.getParameters();
/* 169 */     StringWriter out = new StringWriter();
/*     */     try
/*     */     {
/* 172 */       Map root = new HashMap();
/* 173 */       BeanUtils.copyProperties(root, params);
/*     */ 
/* 175 */       if (this.globalVars != null) {
/* 176 */         root.put("_global", this.globalVars);
/*     */       }
/*     */ 
/* 179 */       TemplateUtils.addUtils(root);
/* 180 */       TemplateUtils.addInvokerInfo(root, this);
/*     */ 
/* 182 */       this.requestTemplate.process(root, out);
/*     */ 
/* 184 */       String reqString = out.toString();
/* 185 */       context.setRequestString(reqString);
/*     */     }
/*     */     catch (Exception e) {
/* 188 */       throw new CodedException("9001", "根据模板[" + context.getOperationCode() + "]组装请求报文出错.", e);
/*     */     }
/*     */     finally {
/*     */       try {
/* 192 */         out.close();
/*     */       } catch (IOException e) {
/*     */       }
/*     */     }
/* 196 */     return out.toString();
/*     */   }
/*     */ 
/*     */   public Object invokeTestXml(InvokeContext context) throws Exception {
/* 200 */     String testXmlPath = ParamsConfig.getInstance().getParamValue("TestXmlPath");
/*     */ 
/* 203 */     String reqStr = FileUtil.readFileContent(testXmlPath + this.operation.getRequestId() + ".xml");
/*     */ 
/* 205 */     logger.debug("reqXmlExm====>\n" + reqStr);
/*     */ 
/* 208 */     String reqString = generateRequestString(context);
/* 209 */     logger.debug("genReqXml====>\n" + reqString);
/* 210 */     context.setRequestString(reqString);
/* 211 */     return reqString;
/*     */   }
/*     */ 
/*     */   public Object invokeTestToMap(InvokeContext context) throws Exception {
/* 215 */     String testXmlPath = ParamsConfig.getInstance().getParamValue("TestXmlPath");
/*     */ 
/* 218 */     String reqStr = FileUtil.readFileContent(testXmlPath + this.operation.getRequestId() + ".xml");
/*     */ 
/* 220 */     logger.debug("reqXmlExm====>\n" + reqStr);
/*     */ 
/* 224 */     Object result = dealResult(context);
/*     */ 
/* 226 */     return result;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.communication.client.Invoker
 * JD-Core Version:    0.6.2
 */