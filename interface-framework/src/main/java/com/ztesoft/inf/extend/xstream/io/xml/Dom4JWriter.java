/*    */ package com.ztesoft.inf.extend.xstream.io.xml;
/*    */ 
/*    */ import org.dom4j.Branch;
/*    */ import org.dom4j.DocumentFactory;
/*    */ import org.dom4j.Element;
/*    */ 
/*    */ public class Dom4JWriter extends AbstractDocumentWriter
/*    */ {
/*    */   private final DocumentFactory documentFactory;
/*    */ 
/*    */   public Dom4JWriter(Branch root, DocumentFactory factory, XmlFriendlyReplacer replacer)
/*    */   {
/* 27 */     super(root, replacer);
/* 28 */     this.documentFactory = factory;
/*    */   }
/*    */ 
/*    */   public Dom4JWriter(DocumentFactory factory, XmlFriendlyReplacer replacer)
/*    */   {
/* 36 */     this(null, factory, replacer);
/*    */   }
/*    */ 
/*    */   public Dom4JWriter(DocumentFactory documentFactory)
/*    */   {
/* 43 */     this(documentFactory, new XmlFriendlyReplacer());
/*    */   }
/*    */ 
/*    */   public Dom4JWriter(Branch root, XmlFriendlyReplacer replacer)
/*    */   {
/* 50 */     this(root, new DocumentFactory(), replacer);
/*    */   }
/*    */ 
/*    */   public Dom4JWriter(Branch root) {
/* 54 */     this(root, new DocumentFactory(), new XmlFriendlyReplacer());
/*    */   }
/*    */ 
/*    */   public Dom4JWriter()
/*    */   {
/* 61 */     this(new DocumentFactory(), new XmlFriendlyReplacer());
/*    */   }
/*    */ 
/*    */   protected Object createNode(String name)
/*    */   {
/* 66 */     Element element = this.documentFactory.createElement(escapeXmlName(name));
/*    */ 
/* 68 */     Branch top = top();
/* 69 */     if (top != null) {
/* 70 */       top().add(element);
/*    */     }
/* 72 */     return element;
/*    */   }
/*    */ 
/*    */   public void setValue(String text) {
/* 76 */     top().setText(text);
/*    */   }
/*    */ 
/*    */   public void addAttribute(String key, String value) {
/* 80 */     ((Element)top()).addAttribute(escapeXmlName(key), value);
/*    */   }
/*    */ 
/*    */   private Branch top() {
/* 84 */     return (Branch)getCurrent();
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.io.xml.Dom4JWriter
 * JD-Core Version:    0.6.2
 */