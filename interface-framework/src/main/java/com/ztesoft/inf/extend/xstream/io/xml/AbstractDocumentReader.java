/*     */ package com.ztesoft.inf.extend.xstream.io.xml;
/*     */ 
/*     */ import com.ztesoft.inf.extend.xstream.converters.ErrorWriter;
/*     */ import com.ztesoft.inf.extend.xstream.core.util.FastStack;
/*     */ import com.ztesoft.inf.extend.xstream.io.AttributeNameIterator;
/*     */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public abstract class AbstractDocumentReader extends AbstractXmlReader
/*     */   implements DocumentReader
/*     */ {
/*  24 */   private FastStack pointers = new FastStack(16);
/*     */   private Object current;
/*     */ 
/*     */   protected AbstractDocumentReader(Object rootElement)
/*     */   {
/*  28 */     this(rootElement, new XmlFriendlyReplacer());
/*     */   }
/*     */ 
/*     */   protected AbstractDocumentReader(Object rootElement, XmlFriendlyReplacer replacer)
/*     */   {
/*  36 */     super(replacer);
/*  37 */     this.current = rootElement;
/*  38 */     this.pointers.push(new Pointer(null));
/*  39 */     reassignCurrentElement(this.current);
/*     */   }
/*     */ 
/*     */   protected abstract void reassignCurrentElement(Object paramObject);
/*     */ 
/*     */   protected abstract Object getParent();
/*     */ 
/*     */   protected abstract Object getChild(int paramInt);
/*     */ 
/*     */   protected abstract int getChildCount();
/*     */ 
/*     */   public boolean hasMoreChildren()
/*     */   {
/*  55 */     Pointer pointer = (Pointer)this.pointers.peek();
/*     */ 
/*  57 */     if (pointer.v < getChildCount()) {
/*  58 */       return true;
/*     */     }
/*  60 */     return false;
/*     */   }
/*     */ 
/*     */   public void moveUp()
/*     */   {
/*  65 */     this.current = getParent();
/*  66 */     this.pointers.popSilently();
/*  67 */     reassignCurrentElement(this.current);
/*     */   }
/*     */ 
/*     */   public void moveDown() {
/*  71 */     Pointer pointer = (Pointer)this.pointers.peek();
/*  72 */     this.pointers.push(new Pointer(null));
/*     */ 
/*  74 */     this.current = getChild(pointer.v);
/*     */ 
/*  76 */     pointer.v += 1;
/*  77 */     reassignCurrentElement(this.current);
/*     */   }
/*     */ 
/*     */   public Iterator getAttributeNames() {
/*  81 */     return new AttributeNameIterator(this);
/*     */   }
/*     */ 
/*     */   public void appendErrors(ErrorWriter errorWriter)
/*     */   {
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Object peekUnderlyingNode()
/*     */   {
/*  92 */     return this.current;
/*     */   }
/*     */ 
/*     */   public Object getCurrent() {
/*  96 */     return this.current;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/*     */   }
/*     */ 
/*     */   public HierarchicalStreamReader underlyingReader() {
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */   private static class Pointer
/*     */   {
/*     */     public int v;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.io.xml.AbstractDocumentReader
 * JD-Core Version:    0.6.2
 */