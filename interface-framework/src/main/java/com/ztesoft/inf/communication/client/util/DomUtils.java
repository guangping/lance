/*    */ package com.ztesoft.inf.communication.client.util;
/*    */ 
/*    */ import com.ztesoft.common.util.StringUtils;
/*    */ import com.ztesoft.inf.framework.commons.CodedException;
/*    */ import java.io.ByteArrayInputStream;
/*    */ import javax.xml.parsers.DocumentBuilder;
/*    */ import javax.xml.parsers.DocumentBuilderFactory;
/*    */ import org.apache.axis.utils.XMLUtils;
/*    */ import org.w3c.dom.Document;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class DomUtils
/*    */ {
/*    */   public static Document newDocument(String xmlStr, boolean nsaware)
/*    */   {
/* 18 */     return newDocument(xmlStr, nsaware, getXMLEncoding(xmlStr));
/*    */   }
/*    */ 
/*    */   public static Document newDocument(String xmlStr, boolean nsaware, String encoding) {
/*    */     try {
/* 23 */       DocumentBuilderFactory xmlFact = DocumentBuilderFactory.newInstance();
/* 24 */       xmlFact.setNamespaceAware(nsaware);
/* 25 */       DocumentBuilder builder = xmlFact.newDocumentBuilder();
/* 26 */       return builder.parse(new ByteArrayInputStream(xmlStr.getBytes(encoding)));
/*    */     }
/*    */     catch (Exception e) {
/* 29 */       throw new CodedException("-1", "从字符串创建DOM对象失败", e);
/*    */     }
/*    */   }
/*    */ 
/*    */   public static String ElementToString(Element element) {
/* 34 */     return XMLUtils.ElementToString(element);
/*    */   }
/*    */   public static String DocumentToString(Document doc) {
/* 37 */     return XMLUtils.DocumentToString(doc);
/*    */   }
/*    */ 
/*    */   private static String getXMLEncoding(String xmlStr) {
/* 41 */     String defaultCode = "UTF-8";
/* 42 */     String encode = StringUtils.toXmlAttr(xmlStr, "?xml", "", "encoding");
/* 43 */     if (StringUtils.isEmpty(encode)) {
/* 44 */       encode = defaultCode;
/*    */     }
/*    */ 
/* 47 */     return StringUtils.toUpperCase(encode);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.communication.client.util.DomUtils
 * JD-Core Version:    0.6.2
 */