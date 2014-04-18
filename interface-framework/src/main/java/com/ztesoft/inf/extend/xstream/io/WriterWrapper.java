/*    */ package com.ztesoft.inf.extend.xstream.io;
/*    */ 
/*    */ public abstract class WriterWrapper
/*    */   implements ExtendedHierarchicalStreamWriter
/*    */ {
/*    */   protected HierarchicalStreamWriter wrapped;
/*    */ 
/*    */   protected WriterWrapper(HierarchicalStreamWriter wrapped)
/*    */   {
/* 25 */     this.wrapped = wrapped;
/*    */   }
/*    */ 
/*    */   public void startNode(String name) {
/* 29 */     this.wrapped.startNode(name);
/*    */   }
/*    */ 
/*    */   public void startNode(String name, Class clazz)
/*    */   {
/* 34 */     ((ExtendedHierarchicalStreamWriter)this.wrapped).startNode(name, clazz);
/*    */   }
/*    */ 
/*    */   public void endNode() {
/* 38 */     this.wrapped.endNode();
/*    */   }
/*    */ 
/*    */   public void addAttribute(String key, String value) {
/* 42 */     this.wrapped.addAttribute(key, value);
/*    */   }
/*    */ 
/*    */   public void setValue(String text) {
/* 46 */     this.wrapped.setValue(text);
/*    */   }
/*    */ 
/*    */   public void flush() {
/* 50 */     this.wrapped.flush();
/*    */   }
/*    */ 
/*    */   public void close() {
/* 54 */     this.wrapped.close();
/*    */   }
/*    */ 
/*    */   public HierarchicalStreamWriter underlyingWriter() {
/* 58 */     return this.wrapped.underlyingWriter();
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.io.WriterWrapper
 * JD-Core Version:    0.6.2
 */