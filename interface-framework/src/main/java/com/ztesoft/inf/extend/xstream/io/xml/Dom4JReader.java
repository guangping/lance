/*    */ package com.ztesoft.inf.extend.xstream.io.xml;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.converters.ErrorWriter;
/*    */ import java.util.List;
/*    */ import org.dom4j.Attribute;
/*    */ import org.dom4j.Document;
/*    */ import org.dom4j.Element;
/*    */ 
/*    */ public class Dom4JReader extends AbstractDocumentReader
/*    */ {
/*    */   private Element currentElement;
/*    */ 
/*    */   public Dom4JReader(Element rootElement)
/*    */   {
/* 24 */     this(rootElement, new XmlFriendlyReplacer());
/*    */   }
/*    */ 
/*    */   public Dom4JReader(Document document) {
/* 28 */     this(document.getRootElement());
/*    */   }
/*    */ 
/*    */   public Dom4JReader(Element rootElement, XmlFriendlyReplacer replacer)
/*    */   {
/* 35 */     super(rootElement, replacer);
/*    */   }
/*    */ 
/*    */   public Dom4JReader(Document document, XmlFriendlyReplacer replacer)
/*    */   {
/* 42 */     this(document.getRootElement(), replacer);
/*    */   }
/*    */ 
/*    */   public String getNodeName() {
/* 46 */     return unescapeXmlName(this.currentElement.getName());
/*    */   }
/*    */ 
/*    */   public String getValue() {
/* 50 */     return this.currentElement.getText();
/*    */   }
/*    */ 
/*    */   public String getAttribute(String name) {
/* 54 */     return this.currentElement.attributeValue(name);
/*    */   }
/*    */ 
/*    */   public String getAttribute(int index) {
/* 58 */     return this.currentElement.attribute(index).getValue();
/*    */   }
/*    */ 
/*    */   public int getAttributeCount() {
/* 62 */     return this.currentElement.attributeCount();
/*    */   }
/*    */ 
/*    */   public String getAttributeName(int index) {
/* 66 */     return unescapeXmlName(this.currentElement.attribute(index).getQualifiedName());
/*    */   }
/*    */ 
/*    */   protected Object getParent()
/*    */   {
/* 72 */     return this.currentElement.getParent();
/*    */   }
/*    */ 
/*    */   protected Object getChild(int index)
/*    */   {
/* 77 */     return this.currentElement.elements().get(index);
/*    */   }
/*    */ 
/*    */   protected int getChildCount()
/*    */   {
/* 82 */     return this.currentElement.elements().size();
/*    */   }
/*    */ 
/*    */   protected void reassignCurrentElement(Object current)
/*    */   {
/* 87 */     this.currentElement = ((Element)current);
/*    */   }
/*    */ 
/*    */   public void appendErrors(ErrorWriter errorWriter)
/*    */   {
/* 92 */     errorWriter.add("xpath", this.currentElement.getPath());
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.io.xml.Dom4JReader
 * JD-Core Version:    0.6.2
 */