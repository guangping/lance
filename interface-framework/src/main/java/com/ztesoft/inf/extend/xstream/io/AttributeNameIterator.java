/*    */ package com.ztesoft.inf.extend.xstream.io;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class AttributeNameIterator
/*    */   implements Iterator
/*    */ {
/*    */   private int current;
/*    */   private final int count;
/*    */   private final HierarchicalStreamReader reader;
/*    */ 
/*    */   public AttributeNameIterator(HierarchicalStreamReader reader)
/*    */   {
/* 28 */     this.reader = reader;
/* 29 */     this.count = reader.getAttributeCount();
/*    */   }
/*    */ 
/*    */   public boolean hasNext() {
/* 33 */     return this.current < this.count;
/*    */   }
/*    */ 
/*    */   public Object next() {
/* 37 */     return this.reader.getAttributeName(this.current++);
/*    */   }
/*    */ 
/*    */   public void remove() {
/* 41 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.io.AttributeNameIterator
 * JD-Core Version:    0.6.2
 */