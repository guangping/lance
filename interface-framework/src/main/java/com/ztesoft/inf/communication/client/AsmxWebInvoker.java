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
/*     */ import java.io.PrintStream;
/*     */ import java.io.StringReader;
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
/*     */ import org.dom4j.io.OutputFormat;
/*     */ import org.dom4j.io.SAXReader;
/*     */ import org.dom4j.io.XMLWriter;
/*     */ 
/*     */ public class AsmxWebInvoker extends Invoker
/*     */ {
/*     */   String cdataPath;
/*     */   Map dataNamespaces;
/*     */   Map cdataNamespaces;
/*  40 */   private static XStream xstream = XStream.instance();
/*  41 */   private static XPath xpath = XPathFactory.newInstance().newXPath();
/*  42 */   private static Service service = new Service();
/*     */   private static final String TEXT = "_EXCEPTION";
/*     */ 
/*     */   public Object invoke(InvokeContext _context)
/*     */     throws Exception
/*     */   {
/*  48 */     AsmxWebInvokeContext context = (AsmxWebInvokeContext)_context;
/*     */ 
/*  50 */     context.setEndpoint(this.endpoint);
/*  51 */     context.setRequestTime(DateUtil.currentTime());
/*  52 */     String reqType = (String)((Map)context.getParameters()).get("TYPE");
/*     */ 
/*  54 */     String reqString = generateRequestString(context);
/*  55 */     context.setRequestString(reqString);
/*     */ 
/*  58 */     SOAPEnvelope req = new SOAPEnvelope(new ByteArrayInputStream(reqString.trim().getBytes("UTF-8")));
/*  59 */     context.setRequestSOAP(req);
/*     */ 
/*  62 */     Call call = new Call(service);
/*  63 */     call.setTargetEndpointAddress(this.endpoint);
/*  64 */     call.setSOAPActionURI("http://crm.jx.ct10000.com/" + reqType);
/*     */ 
/*  66 */     if (this.timeout != null)
/*  67 */       call.setTimeout(Integer.valueOf(this.timeout.intValue() * 1000));
/*     */     SOAPEnvelope rsp;
/*     */     try
/*     */     {
/*  71 */       rsp = call.invoke(req);
/*     */     }
/*     */     catch (AxisFault fault)
/*     */     {
/*     */       SOAPEnvelope rsp;
/*  73 */       if ((fault.detail instanceof ConnectException)) {
/*  74 */         throw new CodedException("8001", "无法连接到操作[" + context.getOperationCode() + "]所请求的服务地址");
/*     */       }
/*  76 */       if (fault.detail == null) {
/*  77 */         throw new CodedException("8999", "WebService调用异常," + fault.getFaultString());
/*     */       }
/*     */ 
/*  80 */       context.setFailure(fault.dumpToString());
/*     */ 
/*  82 */       if (!this.transformFault) {
/*  83 */         throw fault;
/*     */       }
/*     */ 
/*  86 */       Message msg = call.getResponseMessage();
/*     */ 
/*  88 */       if ((msg != null) && (msg.getSOAPEnvelope() != null)) {
/*  89 */         rsp = msg.getSOAPEnvelope();
/*  90 */         context.setResponseSOAP(rsp);
/*     */       } else {
/*  92 */         throw new CodedException("8002", fault.getFaultString());
/*     */       }
/*     */     } finally {
/*  95 */       context.setResponseTime(DateUtil.currentTime());
/*     */     }
/*     */ 
/*  98 */     org.w3c.dom.Document resultDoc = rsp.getAsDocument();
/*  99 */     String resultStr = DomUtils.DocumentToString(resultDoc);
/* 100 */     context.setResponeString(resultStr);
/*     */ 
/* 102 */     System.out.println("\n\n reqStr=\n" + reqString + "\n\n rspStr=\n");
/* 103 */     System.out.println(format(resultStr.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&quot;", "\"").replace(">", ">\n")));
/*     */     try
/*     */     {
/* 106 */       if (!StringUtils.isEmpty(this.cdataPath)) {
/* 107 */         CachedNamespaceContext nsCtx = new CachedNamespaceContext();
/* 108 */         nsCtx.putInCache(this.cdataNamespaces);
/* 109 */         xpath.setNamespaceContext(nsCtx);
/* 110 */         String cdataContent = xpath.evaluate(this.cdataPath, resultDoc);
/*     */ 
/* 113 */         if (StringUtils.isEmpty(cdataContent))
/* 114 */           return new HashMap();
/*     */         try
/*     */         {
/* 117 */           resultDoc = DomUtils.newDocument(cdataContent, false);
/*     */         }
/*     */         catch (CodedException e) {
/* 120 */           resultDoc = parseToDoc(cdataContent);
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 124 */       throw new CodedException("9002", "根据返回的SOAP报文取业务报文[" + context.getOperationCode() + "]时出错.", e);
/* 128 */     }
/*     */ StringWriter out = new StringWriter();
/*     */     Object result;
/*     */     try {
/* 130 */       if (this.transformTemplate != null) {
/* 131 */         Map rootMap = new HashMap();
/* 132 */         rootMap.put("doc", NodeModel.wrap(resultDoc));
/* 133 */         TemplateUtils.addUtils(rootMap);
/* 134 */         this.transformTemplate.process(rootMap, out);
/* 135 */         resultStr = out.toString();
/* 136 */         context.setResultString(resultStr);
/*     */       }
/* 138 */       result = xstream.fromXML(resultStr, this.mapperContext);
/*     */     } catch (Exception e) {
/* 140 */       throw new CodedException("9003", "根据反馈的业务报文转为目标格式[" + context.getOperationCode() + "]时出错.", e);
/*     */     }
/* 142 */     return result;
/*     */   }
/*     */ 
/*     */   public org.w3c.dom.Document parseToDoc(String Content)
/*     */     throws Exception
/*     */   {
/* 149 */     StringWriter out = new StringWriter();
/* 150 */     Map content = new HashMap();
/* 151 */     Map rootMap = new HashMap();
/* 152 */     content.put("_EXCEPTION", Content);
/* 153 */     rootMap.put("doc", content);
/* 154 */     this.transformTemplate.process(rootMap, out);
/* 155 */     String resultStr = out.toString();
/* 156 */     out.close();
/* 157 */     return DomUtils.newDocument(resultStr, false);
/*     */   }
/*     */ 
/*     */   public static String format(String str)
/*     */     throws Exception
/*     */   {
/* 167 */     StringReader in = null;
/* 168 */     StringWriter out = null;
/*     */     try {
/* 170 */       SAXReader reader = new SAXReader();
/*     */ 
/* 172 */       in = new StringReader(str);
/* 173 */       org.dom4j.Document doc = reader.read(in);
/*     */ 
/* 175 */       OutputFormat formate = OutputFormat.createPrettyPrint();
/*     */ 
/* 177 */       out = new StringWriter();
/*     */ 
/* 179 */       XMLWriter writer = new XMLWriter(out, formate);
/*     */ 
/* 181 */       writer.write(doc);
/*     */     }
/*     */     finally {
/* 184 */       if (in != null) {
/* 185 */         in.close();
/*     */       }
/* 187 */       if (out != null) {
/* 188 */         out.close();
/*     */       }
/*     */     }
/* 191 */     return out.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.communication.client.AsmxWebInvoker
 * JD-Core Version:    0.6.2
 */