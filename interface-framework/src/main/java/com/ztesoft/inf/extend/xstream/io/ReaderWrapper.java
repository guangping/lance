/*    */ package com.ztesoft.inf.extend.xstream.io;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.converters.ErrorWriter;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public abstract class ReaderWrapper
/*    */   implements HierarchicalStreamReader
/*    */ {
/*    */   protected HierarchicalStreamReader wrapped;
/*    */ 
/*    */   protected ReaderWrapper(HierarchicalStreamReader reader)
/*    */   {
/* 29 */     this.wrapped = reader;
/*    */   }
/*    */ 
/*    */   public boolean hasMoreChildren() {
/* 33 */     return this.wrapped.hasMoreChildren();
/*    */   }
/*    */ 
/*    */   public void moveDown() {
/* 37 */     this.wrapped.moveDown();
/*    */   }
/*    */ 
/*    */   public void moveUp() {
/* 41 */     this.wrapped.moveUp();
/*    */   }
/*    */ 
/*    */   public String getNodeName() {
/* 45 */     return this.wrapped.getNodeName();
/*    */   }
/*    */ 
/*    */   public String getValue() {
/* 49 */     return this.wrapped.getValue();
/*    */   }
/*    */ 
/*    */   public String getAttribute(String name) {
/* 53 */     return this.wrapped.getAttribute(name);
/*    */   }
/*    */ 
/*    */   public String getAttribute(int index) {
/* 57 */     return this.wrapped.getAttribute(index);
/*    */   }
/*    */ 
/*    */   public int getAttributeCount() {
/* 61 */     return this.wrapped.getAttributeCount();
/*    */   }
/*    */ 
/*    */   public String getAttributeName(int index) {
/* 65 */     return this.wrapped.getAttributeName(index);
/*    */   }
/*    */ 
/*    */   public Iterator getAttributeNames() {
/* 69 */     return this.wrapped.getAttributeNames();
/*    */   }
/*    */ 
/*    */   public void appendErrors(ErrorWriter errorWriter) {
/* 73 */     this.wrapped.appendErrors(errorWriter);
/*    */   }
/*    */ 
/*    */   public void close() {
/* 77 */     this.wrapped.close();
/*    */   }
/*    */ 
/*    */   public HierarchicalStreamReader underlyingReader() {
/* 81 */     return this.wrapped.underlyingReader();
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.io.ReaderWrapper
 * JD-Core Version:    0.6.2
 */