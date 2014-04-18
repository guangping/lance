/*    */ package com.ztesoft.inf.extend.xstream.io.xml;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
/*    */ 
/*    */ public abstract class AbstractXmlReader
/*    */   implements HierarchicalStreamReader, XmlFriendlyReader
/*    */ {
/*    */   private XmlFriendlyReplacer replacer;
/*    */ 
/*    */   protected AbstractXmlReader()
/*    */   {
/* 29 */     this(new XmlFriendlyReplacer());
/*    */   }
/*    */ 
/*    */   protected AbstractXmlReader(XmlFriendlyReplacer replacer) {
/* 33 */     this.replacer = replacer;
/*    */   }
/*    */ 
/*    */   public String unescapeXmlName(String name)
/*    */   {
/* 44 */     return this.replacer.unescapeName(name);
/*    */   }
/*    */ 
/*    */   protected String escapeXmlName(String name)
/*    */   {
/* 55 */     return this.replacer.escapeName(name);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.io.xml.AbstractXmlReader
 * JD-Core Version:    0.6.2
 */