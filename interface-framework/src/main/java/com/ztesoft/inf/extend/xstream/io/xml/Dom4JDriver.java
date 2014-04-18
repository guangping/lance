/*     */ package com.ztesoft.inf.extend.xstream.io.xml;
/*     */ 
/*     */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
/*     */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;
/*     */ import com.ztesoft.inf.extend.xstream.io.StreamException;
/*     */ import java.io.FilterWriter;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Reader;
/*     */ import java.io.Writer;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.DocumentFactory;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.OutputFormat;
/*     */ import org.dom4j.io.SAXReader;
/*     */ import org.dom4j.io.XMLWriter;
/*     */ import org.dom4j.tree.DefaultDocument;
/*     */ 
/*     */ public class Dom4JDriver extends AbstractXmlDriver
/*     */ {
/*     */   private DocumentFactory documentFactory;
/*     */   private OutputFormat outputFormat;
/*     */ 
/*     */   public Dom4JDriver()
/*     */   {
/*  40 */     this(new DocumentFactory(), OutputFormat.createPrettyPrint());
/*  41 */     this.outputFormat.setTrimText(false);
/*     */   }
/*     */ 
/*     */   public Dom4JDriver(XmlFriendlyReplacer replacer) {
/*  45 */     this(new DocumentFactory(), OutputFormat.createPrettyPrint(), replacer);
/*     */   }
/*     */ 
/*     */   public Dom4JDriver(DocumentFactory documentFactory, OutputFormat outputFormat)
/*     */   {
/*  50 */     this(documentFactory, outputFormat, new XmlFriendlyReplacer());
/*     */   }
/*     */ 
/*     */   public Dom4JDriver(DocumentFactory documentFactory, OutputFormat outputFormat, XmlFriendlyReplacer replacer)
/*     */   {
/*  58 */     super(replacer);
/*  59 */     this.documentFactory = documentFactory;
/*  60 */     this.outputFormat = outputFormat;
/*     */   }
/*     */ 
/*     */   public DocumentFactory getDocumentFactory() {
/*  64 */     return this.documentFactory;
/*     */   }
/*     */ 
/*     */   public void setDocumentFactory(DocumentFactory documentFactory) {
/*  68 */     this.documentFactory = documentFactory;
/*     */   }
/*     */ 
/*     */   public OutputFormat getOutputFormat() {
/*  72 */     return this.outputFormat;
/*     */   }
/*     */ 
/*     */   public void setOutputFormat(OutputFormat outputFormat) {
/*  76 */     this.outputFormat = outputFormat;
/*     */   }
/*     */ 
/*     */   public HierarchicalStreamReader createReader(Reader text) {
/*     */     try {
/*  81 */       SAXReader reader = new SAXReader();
/*  82 */       Document document = reader.read(text);
/*  83 */       return new Dom4JReader(document, xmlFriendlyReplacer());
/*     */     } catch (DocumentException e) {
/*  85 */       throw new StreamException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public HierarchicalStreamReader createReader(Element element) {
/*  90 */     return new Dom4JReader(element, xmlFriendlyReplacer());
/*     */   }
/*     */ 
/*     */   public HierarchicalStreamReader createReader(InputStream in) {
/*     */     try {
/*  95 */       SAXReader reader = new SAXReader();
/*  96 */       Document document = reader.read(in);
/*  97 */       return new Dom4JReader(document, xmlFriendlyReplacer());
/*     */     } catch (DocumentException e) {
/*  99 */       throw new StreamException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public HierarchicalStreamWriter createWriter(Writer out) {
/* 104 */     final HierarchicalStreamWriter[] writer = new HierarchicalStreamWriter[1];
/* 105 */     FilterWriter filter = new FilterWriter(out)
/*     */     {
/*     */       public void close()
/*     */       {
/* 109 */         writer[0].close();
/*     */       }
/*     */     };
/* 112 */     writer[0] = new Dom4JXmlWriter(new XMLWriter(filter, this.outputFormat), xmlFriendlyReplacer());
/*     */ 
/* 114 */     return writer[0];
/*     */   }
/*     */ 
/*     */   public DocumentWriter createDoumentWriter() {
/* 118 */     Dom4JWriter writer = new Dom4JWriter(new DefaultDocument());
/* 119 */     return writer;
/*     */   }
/*     */ 
/*     */   public HierarchicalStreamWriter createWriter(OutputStream out) {
/* 123 */     Writer writer = new OutputStreamWriter(out);
/* 124 */     return createWriter(writer);
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.io.xml.Dom4JDriver
 * JD-Core Version:    0.6.2
 */