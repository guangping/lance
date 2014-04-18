/*     */ package com.ztesoft.inf.extend.xstream.io.xml;
/*     */ 
/*     */ import com.ztesoft.inf.extend.xstream.core.util.FastStack;
/*     */ import com.ztesoft.inf.extend.xstream.io.StreamException;
/*     */ import java.io.IOException;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.XMLWriter;
/*     */ import org.dom4j.tree.DefaultElement;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.AttributesImpl;
/*     */ 
/*     */ public class Dom4JXmlWriter extends AbstractXmlWriter
/*     */ {
/*     */   private final XMLWriter writer;
/*     */   private final FastStack elementStack;
/*     */   private AttributesImpl attributes;
/*     */   private boolean started;
/*     */   private boolean children;
/*     */   private Element rootElement;
/*     */ 
/*     */   public Dom4JXmlWriter(XMLWriter writer)
/*     */   {
/*  35 */     this(writer, new XmlFriendlyReplacer());
/*     */   }
/*     */ 
/*     */   public Dom4JXmlWriter(XMLWriter writer, XmlFriendlyReplacer replacer)
/*     */   {
/*  42 */     super(replacer);
/*  43 */     this.writer = writer;
/*  44 */     this.elementStack = new FastStack(16);
/*  45 */     this.attributes = new AttributesImpl();
/*     */     try {
/*  47 */       writer.startDocument();
/*     */     } catch (SAXException e) {
/*  49 */       throw new StreamException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void startNode(String name) {
/*  54 */     if (this.elementStack.size() > 0) {
/*     */       try {
/*  56 */         startElement();
/*     */       } catch (SAXException e) {
/*  58 */         throw new StreamException(e);
/*     */       }
/*  60 */       this.started = false;
/*     */     }
/*  62 */     this.elementStack.push(escapeXmlName(name));
/*  63 */     this.children = false;
/*     */   }
/*     */ 
/*     */   public void setValue(String text) {
/*  67 */     char[] value = text.toCharArray();
/*  68 */     if (value.length > 0) {
/*     */       try {
/*  70 */         startElement();
/*  71 */         this.writer.characters(value, 0, value.length);
/*     */       } catch (SAXException e) {
/*  73 */         throw new StreamException(e);
/*     */       }
/*  75 */       this.children = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addAttribute(String key, String value) {
/*  80 */     this.attributes.addAttribute("", "", escapeXmlName(key), "string", value);
/*     */   }
/*     */ 
/*     */   public void endNode() {
/*     */     try {
/*  85 */       if (!this.children) {
/*  86 */         Element element = new DefaultElement((String)this.elementStack.pop());
/*     */ 
/*  88 */         for (int i = 0; i < this.attributes.getLength(); i++) {
/*  89 */           element.addAttribute(this.attributes.getQName(i), this.attributes.getValue(i));
/*     */         }
/*     */ 
/*  92 */         this.writer.write(element);
/*  93 */         this.attributes.clear();
/*  94 */         this.children = true;
/*     */ 
/*  96 */         this.started = true;
/*  97 */         this.rootElement = element;
/*     */       } else {
/*  99 */         startElement();
/* 100 */         this.writer.endElement("", "", (String)this.elementStack.pop());
/*     */       }
/*     */     } catch (SAXException e) {
/* 103 */       throw new StreamException(e);
/*     */     } catch (IOException e) {
/* 105 */       throw new StreamException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Element getRootElement() {
/* 110 */     return this.rootElement;
/*     */   }
/*     */ 
/*     */   public void setRootElement(Element rootElement) {
/* 114 */     this.rootElement = rootElement;
/*     */   }
/*     */ 
/*     */   public void flush()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void close() {
/*     */     try {
/* 123 */       this.writer.endDocument();
/*     */     } catch (SAXException e) {
/* 125 */       throw new StreamException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void startElement() throws SAXException {
/* 130 */     if (!this.started) {
/* 131 */       this.writer.startElement("", "", (String)this.elementStack.peek(), this.attributes);
/*     */ 
/* 133 */       this.attributes.clear();
/* 134 */       this.started = true;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.io.xml.Dom4JXmlWriter
 * JD-Core Version:    0.6.2
 */