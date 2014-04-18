/*    */ package com.ztesoft.inf.communication.client;
/*    */ 
/*    */ import com.ztesoft.common.util.StringUtils;
/*    */ import com.ztesoft.common.util.date.DateUtil;
/*    */ import com.ztesoft.common.util.web.SocketUtils;
/*    */ import com.ztesoft.inf.extend.freemarker.TemplateUtils;
/*    */ import com.ztesoft.inf.extend.xstream.XStream;
/*    */ import com.ztesoft.inf.framework.commons.CodedException;
/*    */ import freemarker.template.Template;
/*    */ import java.io.StringWriter;
/*    */ import java.net.InetSocketAddress;
/*    */ import java.net.Socket;
/*    */ import java.net.SocketAddress;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class SocketInvoker extends Invoker
/*    */ {
/*    */   int port;
/*    */   private InetSocketAddress address;
/* 21 */   private static XStream xstream = XStream.instance();
/*    */ 
/*    */   public Object invoke(InvokeContext context)
/*    */     throws Exception
/*    */   {
/* 26 */     context.setEndpoint(this.endpoint);
/* 27 */     context.setRequestTime(DateUtil.currentTime());
/* 28 */     String reqString = generateRequestString(context);
/* 29 */     context.setRequestString(reqString);
/* 30 */     String rspString = sendRequest(reqString);
/* 31 */     Object result = rspString;
/* 32 */     context.setResponseTime(DateUtil.currentTime());
/* 33 */     context.setResultString(rspString);
/*    */ 
/* 35 */     StringUtils.printInfo("\n\n reqStr=\n" + reqString + "\n\n rspStr=\n" + rspString);
/*    */ 
/* 38 */     if (this.transformTemplate != null) {
/*    */       try
/*    */       {
/* 41 */         String resultStr = rspString;
/* 42 */         Map rootMap = new HashMap();
/* 43 */         rootMap.put("rsp", rspString);
/* 44 */         TemplateUtils.addUtils(rootMap);
/* 45 */         StringWriter out = new StringWriter();
/* 46 */         this.transformTemplate.process(rootMap, out);
/* 47 */         resultStr = out.toString();
/* 48 */         result = xstream.fromXML(resultStr, this.mapperContext);
/*    */       } catch (Exception e) {
/* 50 */         throw new CodedException("9003", "根据返回的消息转为目标格式时出错.", e);
/*    */       }
/*    */     }
/*    */ 
/* 54 */     return result;
/*    */   }
/*    */ 
/*    */   private synchronized SocketAddress getAddress()
/*    */   {
/* 59 */     if (this.address == null) {
/* 60 */       this.address = new InetSocketAddress(this.endpoint, this.port);
/*    */     }
/* 62 */     return this.address;
/*    */   }
/*    */ 
/*    */   private String sendRequest(String reqString) throws Exception {
/* 66 */     Socket socket = null;
/*    */     try {
/* 68 */       socket = SocketUtils.newClient(2, this.timeout.intValue() * 1000, 32768, 32768);
/*    */ 
/* 70 */       socket.connect(getAddress());
/* 71 */       return SocketUtils.sendAndRecieve(socket, reqString);
/*    */     } finally {
/* 73 */       if (socket != null)
/*    */         try {
/* 75 */           socket.close();
/*    */         }
/*    */         catch (Exception ex)
/*    */         {
/*    */         }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.communication.client.SocketInvoker
 * JD-Core Version:    0.6.2
 */