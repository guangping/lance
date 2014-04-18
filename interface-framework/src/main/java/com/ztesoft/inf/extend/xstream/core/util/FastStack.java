/*    */ package com.ztesoft.inf.extend.xstream.core.util;
/*    */ 
/*    */ public final class FastStack
/*    */ {
/*    */   private Object[] stack;
/*    */   private int pointer;
/*    */ 
/*    */   public FastStack(int initialCapacity)
/*    */   {
/* 26 */     this.stack = new Object[initialCapacity];
/*    */   }
/*    */ 
/*    */   public Object push(Object value) {
/* 30 */     if (this.pointer + 1 >= this.stack.length) {
/* 31 */       resizeStack(this.stack.length * 2);
/*    */     }
/* 33 */     this.stack[(this.pointer++)] = value;
/* 34 */     return value;
/*    */   }
/*    */ 
/*    */   public void popSilently() {
/* 38 */     this.stack[(--this.pointer)] = null;
/*    */   }
/*    */ 
/*    */   public Object pop() {
/* 42 */     Object result = this.stack[(--this.pointer)];
/* 43 */     this.stack[this.pointer] = null;
/* 44 */     return result;
/*    */   }
/*    */ 
/*    */   public Object peek() {
/* 48 */     return this.pointer == 0 ? null : this.stack[(this.pointer - 1)];
/*    */   }
/*    */ 
/*    */   public int size() {
/* 52 */     return this.pointer;
/*    */   }
/*    */ 
/*    */   public boolean hasStuff() {
/* 56 */     return this.pointer > 0;
/*    */   }
/*    */ 
/*    */   public Object get(int i) {
/* 60 */     return this.stack[i];
/*    */   }
/*    */ 
/*    */   private void resizeStack(int newCapacity) {
/* 64 */     Object[] newStack = new Object[newCapacity];
/* 65 */     System.arraycopy(this.stack, 0, newStack, 0, Math.min(this.pointer, newCapacity));
/* 66 */     this.stack = newStack;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 71 */     StringBuffer result = new StringBuffer("[");
/* 72 */     for (int i = 0; i < this.pointer; i++) {
/* 73 */       if (i > 0) {
/* 74 */         result.append(", ");
/*    */       }
/* 76 */       result.append(this.stack[i]);
/*    */     }
/* 78 */     result.append(']');
/* 79 */     return result.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.core.util.FastStack
 * JD-Core Version:    0.6.2
 */