/*     */ package com.ztesoft.inf.communication.client;
/*     */ 
/*     */ import com.ztesoft.common.util.StringUtils;
/*     */ import com.ztesoft.common.util.date.DateUtil;
/*     */ import com.ztesoft.inf.communication.client.util.DomUtils;
/*     */ import com.ztesoft.inf.extend.freemarker.TemplateUtils;
/*     */ import com.ztesoft.inf.extend.xstream.XStream;
/*     */ import com.ztesoft.inf.framework.commons.CachedNamespaceContext;
/*     */ import com.ztesoft.inf.framework.commons.CodedException;
/*     */ import freemarker.ext.dom.NodeModel;
/*     */ import freemarker.template.Template;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.StringWriter;
/*     */ import java.net.ConnectException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.xpath.XPath;
/*     */ import javax.xml.xpath.XPathFactory;
/*     */ import org.apache.axis.AxisFault;
/*     */ import org.apache.axis.Message;
/*     */ import org.apache.axis.client.Call;
/*     */ import org.apache.axis.client.Service;
/*     */ import org.apache.axis.message.SOAPEnvelope;
/*     */ import org.w3c.dom.Document;
/*     */ 
/*     */ public class StringObjectInvoker extends Invoker
/*     */ {
/*     */   protected String cdataPath;
/*     */   protected Map dataNamespaces;
/*     */   protected Map cdataNamespaces;
/*  36 */   private static XStream xstream = XStream.instance();
/*  37 */   private static XPath xpath = XPathFactory.newInstance().newXPath();
/*  38 */   private static Service service = new Service();
/*     */   private static final String TEXT = "_EXCEPTION";
/*     */ 
/*     */   public Object invoke(InvokeContext _context)
/*     */     throws Exception
/*     */   {
/*  45 */     WsInvokeContext context = (WsInvokeContext)_context;
/*     */ 
/*  47 */     context.setEndpoint(this.endpoint);
/*     */ 
/*  49 */     context.setRequestTime(DateUtil.currentTime());
/*     */ 
/*  51 */     String reqString = generateRequestString(context);
/*  52 */     context.setRequestString(reqString);
/*     */ 
/*  54 */     SOAPEnvelope req = new SOAPEnvelope(new ByteArrayInputStream(reqString.trim().getBytes("UTF-8")));
/*  55 */     context.setRequestSOAP(req);
/*     */ 
/*  58 */     Call call = new Call(service);
/*  59 */     call.setTargetEndpointAddress(this.endpoint);
/*     */ 
/*  62 */     if (this.timeout != null)
/*  63 */       call.setTimeout(Integer.valueOf(this.timeout.intValue() * 1000));
/*     */     SOAPEnvelope rsp;
/*     */     try {
/*  66 */       rsp = call.invoke(req);
/*     */     }
/*     */     catch (AxisFault fault)
/*     */     {
/*     */       SOAPEnvelope rsp;
/*  68 */       fault.printStackTrace();
/*  69 */       if ((fault.detail instanceof ConnectException)) {
/*  70 */         throw new CodedException("8001", "无法连接到操作[" + context.getOperationCode() + "]所请求的服务地址");
/*     */       }
/*     */ 
/*  73 */       if (fault.detail == null) {
/*  74 */         throw new CodedException("8999", "WebService调用异常," + fault.getFaultString());
/*     */       }
/*     */ 
/*  78 */       context.setFailure(fault.dumpToString());
/*     */ 
/*  80 */       if (!this.transformFault) {
/*  81 */         throw fault;
/*     */       }
/*     */ 
/*  84 */       Message msg = call.getResponseMessage();
/*     */ 
/*  86 */       if ((msg != null) && (msg.getSOAPEnvelope() != null)) {
/*  87 */         rsp = msg.getSOAPEnvelope();
/*  88 */         context.setResponseSOAP(rsp);
/*     */       } else {
/*  90 */         throw new CodedException("8002", fault.getFaultString());
/*     */       }
/*     */     } finally {
/*  93 */       context.setResponseTime(DateUtil.currentTime());
/*     */     }
/*     */ 
/*  96 */     Document resultDoc = rsp.getAsDocument();
/*  97 */     String resultStr = DomUtils.DocumentToString(resultDoc);
/*  98 */     context.setResponeString(resultStr);
/*     */     try
/*     */     {
/* 102 */       if (!StringUtils.isEmpty(this.cdataPath)) {
/* 103 */         CachedNamespaceContext nsCtx = new CachedNamespaceContext();
/* 104 */         nsCtx.putInCache(this.cdataNamespaces);
/* 105 */         xpath.setNamespaceContext(nsCtx);
/* 106 */         String cdataContent = xpath.evaluate(this.cdataPath, resultDoc);
/*     */ 
/* 109 */         if (StringUtils.isEmpty(cdataContent))
/* 110 */           return new HashMap();
/*     */         try
/*     */         {
/* 113 */           resultDoc = DomUtils.newDocument(cdataContent, false);
/*     */         }
/*     */         catch (CodedException e)
/*     */         {
/* 117 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 121 */       throw new CodedException("9002", "根据返回的SOAP报文取业务报文[" + context.getOperationCode() + "]时出错.", e);
/*     */     }
/*     */ 
/* 126 */     StringWriter out = new StringWriter();
/*     */     Object result;
/*     */     try {
/* 128 */       if (this.transformTemplate != null) {
/* 129 */         Map rootMap = new HashMap();
/* 130 */         rootMap.put("doc", NodeModel.wrap(resultDoc));
/* 131 */         TemplateUtils.addUtils(rootMap);
/* 132 */         this.transformTemplate.process(rootMap, out);
/* 133 */         resultStr = out.toString();
/* 134 */         context.setResultString(resultStr);
/*     */       }
/* 136 */       result = xstream.fromXML(resultStr, this.mapperContext);
/*     */ 
/* 139 */       if ((result instanceof Map))
/* 140 */         ((Map)result).put("resultXml", resultStr);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 144 */       throw new CodedException("9003", "根据反馈的业务报文转为目标格式[" + context.getOperationCode() + "]时出错.", e);
/*     */     }
/*     */ 
/* 147 */     return result;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.communication.client.StringObjectInvoker
 * JD-Core Version:    0.6.2
 */