/*    */ package com.ztesoft.inf.extend.xstream.io.xml;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.io.ExtendedHierarchicalStreamWriter;
/*    */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;
/*    */ 
/*    */ public abstract class AbstractXmlWriter
/*    */   implements ExtendedHierarchicalStreamWriter, XmlFriendlyWriter
/*    */ {
/*    */   private XmlFriendlyReplacer replacer;
/*    */ 
/*    */   protected AbstractXmlWriter()
/*    */   {
/* 30 */     this(new XmlFriendlyReplacer());
/*    */   }
/*    */ 
/*    */   protected AbstractXmlWriter(XmlFriendlyReplacer replacer) {
/* 34 */     this.replacer = replacer;
/*    */   }
/*    */ 
/*    */   public void startNode(String name, Class clazz) {
/* 38 */     startNode(name);
/*    */   }
/*    */ 
/*    */   public String escapeXmlName(String name)
/*    */   {
/* 49 */     return this.replacer.escapeName(name);
/*    */   }
/*    */ 
/*    */   public HierarchicalStreamWriter underlyingWriter() {
/* 53 */     return this;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.io.xml.AbstractXmlWriter
 * JD-Core Version:    0.6.2
 */