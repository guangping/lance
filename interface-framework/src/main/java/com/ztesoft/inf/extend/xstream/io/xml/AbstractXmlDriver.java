/*    */ package com.ztesoft.inf.extend.xstream.io.xml;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamDriver;
/*    */ 
/*    */ public abstract class AbstractXmlDriver
/*    */   implements HierarchicalStreamDriver
/*    */ {
/*    */   private XmlFriendlyReplacer replacer;
/*    */ 
/*    */   public AbstractXmlDriver()
/*    */   {
/* 31 */     this(new XmlFriendlyReplacer());
/*    */   }
/*    */ 
/*    */   public AbstractXmlDriver(XmlFriendlyReplacer replacer)
/*    */   {
/* 41 */     this.replacer = replacer;
/*    */   }
/*    */ 
/*    */   protected XmlFriendlyReplacer xmlFriendlyReplacer() {
/* 45 */     return this.replacer;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.io.xml.AbstractXmlDriver
 * JD-Core Version:    0.6.2
 */