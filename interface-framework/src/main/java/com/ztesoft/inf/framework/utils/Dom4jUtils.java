/*    */ package com.ztesoft.inf.framework.utils;
/*    */ 
/*    */ import java.io.StringReader;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.dom4j.Document;
/*    */ import org.dom4j.Node;
/*    */ import org.dom4j.io.SAXReader;
/*    */ 
/*    */ public class Dom4jUtils
/*    */ {
/*    */   public static String getValue(Node parent, String path)
/*    */   {
/* 14 */     Node node = parent.selectSingleNode(path);
/* 15 */     if (node != null)
/* 16 */       return node.getText();
/* 17 */     return null;
/*    */   }
/*    */ 
/*    */   public static String getValues(Node parent, String path) {
/* 21 */     List lists = parent.selectNodes(path);
/* 22 */     String values = "";
/* 23 */     Iterator iterator = CollectionUtils.safe(lists).iterator();
/* 24 */     while (iterator.hasNext()) {
/* 25 */       Node node = (Node)iterator.next();
/* 26 */       values = values + node.getText() + ",";
/*    */     }
/* 28 */     if (values.length() > 0) {
/* 29 */       return values.substring(0, values.length() - 1);
/*    */     }
/* 31 */     return null;
/*    */   }
/*    */ 
/*    */   public static Document toDoc(String inXml) throws Exception {
/* 35 */     SAXReader reader = new SAXReader();
/* 36 */     return reader.read(new StringReader(inXml));
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.framework.utils.Dom4jUtils
 * JD-Core Version:    0.6.2
 */