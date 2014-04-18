/*    */ package com.ztesoft.inf.extend.xstream.io.xml;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.core.util.FastStack;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public abstract class AbstractDocumentWriter extends AbstractXmlWriter
/*    */   implements DocumentWriter
/*    */ {
/* 33 */   private final List result = new ArrayList();
/* 34 */   private final FastStack nodeStack = new FastStack(16);
/*    */ 
/*    */   public AbstractDocumentWriter(Object container, XmlFriendlyReplacer replacer)
/*    */   {
/* 48 */     super(replacer);
/* 49 */     if (container != null) {
/* 50 */       this.nodeStack.push(container);
/* 51 */       this.result.add(container);
/*    */     }
/*    */   }
/*    */ 
/*    */   public final void startNode(String name) {
/* 56 */     Object node = createNode(name);
/* 57 */     this.nodeStack.push(node);
/*    */   }
/*    */ 
/*    */   protected abstract Object createNode(String paramString);
/*    */ 
/*    */   public final void endNode()
/*    */   {
/* 73 */     endNodeInternally();
/* 74 */     Object node = this.nodeStack.pop();
/* 75 */     if (this.nodeStack.size() == 0)
/* 76 */       this.result.add(node);
/*    */   }
/*    */ 
/*    */   public void endNodeInternally()
/*    */   {
/*    */   }
/*    */ 
/*    */   protected final Object getCurrent()
/*    */   {
/* 92 */     return this.nodeStack.peek();
/*    */   }
/*    */ 
/*    */   public List getTopLevelNodes() {
/* 96 */     return this.result;
/*    */   }
/*    */ 
/*    */   public void flush()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void close()
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.io.xml.AbstractDocumentWriter
 * JD-Core Version:    0.6.2
 */