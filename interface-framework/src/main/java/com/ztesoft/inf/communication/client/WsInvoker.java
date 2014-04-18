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
/*     */ public class WsInvoker extends Invoker
/*     */ {
/*     */   String cdataPath;
/*     */   Map dataNamespaces;
/*     */   Map cdataNamespaces;
/*  40 */   private static XStream xstream = XStream.instance();
/*  41 */   private static XPath xpath = XPathFactory.newInstance().newXPath();
/*     */   private static final String TEXT = "_EXCEPTION";
/*     */ 
/*     */   public Object invoke(InvokeContext _context)
/*     */     throws Exception
/*     */   {
/*  48 */     WsInvokeContext context = (WsInvokeContext)_context;
/*     */ 
/*  50 */     context.setEndpoint(this.endpoint);
/*     */ 
/*  52 */     context.setRequestTime(DateUtil.currentTime());
/*     */ 
/*  54 */     String reqString = generateRequestString(context);
/*  55 */     context.setRequestString(reqString);
/*  56 */     System.out.println(reqString);
/*  57 */     SOAPEnvelope req = new SOAPEnvelope(new ByteArrayInputStream(reqString.trim().getBytes("UTF-8")));
/*     */ 
/*  59 */     context.setRequestSOAP(req);
/*     */ 
/*  62 */     Call call = new Call(new Service());
/*  63 */     call.setTargetEndpointAddress(this.endpoint);
/*     */ 
/*  66 */     Map param = (Map)context.getParameters();
/*  67 */     if (StringUtils.equals("T", (String)param.get("ws_isNet"))) {
/*  68 */       call.setOperationName((String)param.get("ws_method"));
/*  69 */       call.setUseSOAPAction(true);
/*  70 */       call.setSOAPActionURI((String)param.get("ws_namespace"));
/*     */     }
/*     */ 
/*  73 */     if (this.timeout != null)
/*  74 */       call.setTimeout(Integer.valueOf(this.timeout.intValue() * 1000));
/*     */     SOAPEnvelope rsp;
/*     */     try {
/*  77 */       rsp = call.invoke(req);
/*     */     }
/*     */     catch (AxisFault fault)
/*     */     {
/*     */       SOAPEnvelope rsp;
/*  79 */       fault.printStackTrace();
/*  80 */       if ((fault.detail instanceof ConnectException)) {
/*  81 */         throw new CodedException("8001", "无法连接到操作[" + context.getOperationCode() + "]所请求的服务地址");
/*     */       }
/*     */ 
/*  84 */       if (fault.detail == null) {
/*  85 */         throw new CodedException("8999", "WebService调用异常," + fault.getFaultString());
/*     */       }
/*     */ 
/*  89 */       context.setFailure(fault.dumpToString());
/*     */ 
/*  91 */       if (!this.transformFault) {
/*  92 */         throw fault;
/*     */       }
/*     */ 
/*  95 */       Message msg = call.getResponseMessage();
/*     */ 
/*  97 */       if ((msg != null) && (msg.getSOAPEnvelope() != null)) {
/*  98 */         rsp = msg.getSOAPEnvelope();
/*  99 */         context.setResponseSOAP(rsp);
/*     */       } else {
/* 101 */         throw new CodedException("8002", fault.getFaultString());
/*     */       }
/*     */     } finally {
/* 104 */       context.setResponseTime(DateUtil.currentTime());
/*     */     }
/*     */ 
/* 107 */     org.w3c.dom.Document resultDoc = rsp.getAsDocument();
/* 108 */     String resultStr = DomUtils.DocumentToString(resultDoc);
/* 109 */     context.setResponeString(resultStr);
/*     */ 
/* 111 */     System.out.println(resultStr);
/*     */     try {
/* 113 */       if (!StringUtils.isEmpty(this.cdataPath)) {
/* 114 */         CachedNamespaceContext nsCtx = new CachedNamespaceContext();
/* 115 */         nsCtx.putInCache(this.cdataNamespaces);
/* 116 */         xpath.setNamespaceContext(nsCtx);
/* 117 */         String cdataContent = xpath.evaluate(this.cdataPath, resultDoc);
/*     */ 
/* 120 */         if (StringUtils.isEmpty(cdataContent))
/* 121 */           return new HashMap();
/*     */         try
/*     */         {
/* 124 */           resultDoc = DomUtils.newDocument(cdataContent, false);
/*     */         }
/*     */         catch (CodedException e)
/*     */         {
/* 128 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 132 */       throw new CodedException("9002", "根据返回的SOAP报文取业务报文[" + context.getOperationCode() + "]时出错.", e);
/*     */     }
/*     */ 
/* 137 */     StringWriter out = new StringWriter();
/*     */     Object result;
/*     */     try {
/* 139 */       if (this.transformTemplate != null) {
/* 140 */         Map rootMap = new HashMap();
/* 141 */         rootMap.put("doc", NodeModel.wrap(resultDoc));
/* 142 */         TemplateUtils.addUtils(rootMap);
/* 143 */         this.transformTemplate.process(rootMap, out);
/* 144 */         resultStr = out.toString();
/* 145 */         context.setResultString(resultStr);
/*     */       }
/* 147 */       result = xstream.fromXML(resultStr, this.mapperContext);
/*     */     } catch (Exception e) {
/* 149 */       throw new CodedException("9003", "根据反馈的业务报文转为目标格式[" + context.getOperationCode() + "]时出错.", e);
/*     */     }
/*     */ 
/* 152 */     return result;
/*     */   }
/*     */ 
/*     */   public org.w3c.dom.Document parseToDoc(String Content)
/*     */     throws Exception
/*     */   {
/* 159 */     StringWriter out = new StringWriter();
/* 160 */     Map content = new HashMap();
/* 161 */     Map rootMap = new HashMap();
/* 162 */     content.put("_EXCEPTION", Content);
/* 163 */     rootMap.put("doc", content);
/* 164 */     this.transformTemplate.process(rootMap, out);
/* 165 */     String resultStr = out.toString();
/* 166 */     out.close();
/* 167 */     return DomUtils.newDocument(resultStr, false);
/*     */   }
/*     */ 
/*     */   public static String format(String str)
/*     */     throws Exception
/*     */   {
/* 178 */     StringReader in = null;
/* 179 */     StringWriter out = null;
/*     */     try {
/* 181 */       SAXReader reader = new SAXReader();
/*     */ 
/* 183 */       in = new StringReader(str);
/* 184 */       org.dom4j.Document doc = reader.read(in);
/*     */ 
/* 186 */       OutputFormat formate = OutputFormat.createPrettyPrint();
/*     */ 
/* 188 */       out = new StringWriter();
/*     */ 
/* 190 */       XMLWriter writer = new XMLWriter(out, formate);
/*     */ 
/* 192 */       writer.write(doc);
/*     */     }
/*     */     finally {
/* 195 */       if (in != null) {
/* 196 */         in.close();
/*     */       }
/* 198 */       if (out != null) {
/* 199 */         out.close();
/*     */       }
/*     */     }
/* 202 */     return out.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.communication.client.WsInvoker
 * JD-Core Version:    0.6.2
 */