/*    */ package com.ztesoft.inf.extend.xstream.core.util;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
/*    */ import java.util.TreeSet;
/*    */ 
/*    */ public class PrioritizedList
/*    */ {
/*    */   private final Set set;
/*    */   private int lowestPriority;
/*    */   private int lastId;
/*    */ 
/*    */   public PrioritizedList()
/*    */   {
/* 31 */     this.set = new TreeSet();
/*    */ 
/* 33 */     this.lowestPriority = 2147483647;
/*    */ 
/* 35 */     this.lastId = 0;
/*    */   }
/*    */   public void add(Object item, int priority) {
/* 38 */     if (this.lowestPriority > priority) {
/* 39 */       this.lowestPriority = priority;
/*    */     }
/* 41 */     this.set.add(new PrioritizedItem(item, priority, ++this.lastId));
/*    */   }
/*    */ 
/*    */   public Iterator iterator() {
/* 45 */     return new PrioritizedItemIterator(this.set.iterator());
/*    */   }
/*    */ 
/*    */   private static class PrioritizedItemIterator
/*    */     implements Iterator
/*    */   {
/*    */     private Iterator iterator;
/*    */ 
/*    */     public PrioritizedItemIterator(Iterator iterator)
/*    */     {
/* 80 */       this.iterator = iterator;
/*    */     }
/*    */ 
/*    */     public void remove()
/*    */     {
/* 85 */       throw new UnsupportedOperationException();
/*    */     }
/*    */ 
/*    */     public boolean hasNext() {
/* 89 */       return this.iterator.hasNext();
/*    */     }
/*    */ 
/*    */     public Object next() {
/* 93 */       return ((PrioritizedList.PrioritizedItem)this.iterator.next()).value;
/*    */     }
/*    */   }
/*    */ 
/*    */   private static class PrioritizedItem
/*    */     implements Comparable
/*    */   {
/*    */     final Object value;
/*    */     final int priority;
/*    */     final int id;
/*    */ 
/*    */     public PrioritizedItem(Object value, int Prioritized, int id)
/*    */     {
/* 55 */       this.value = value;
/* 56 */       this.priority = Prioritized;
/* 57 */       this.id = id;
/*    */     }
/*    */ 
/*    */     public int compareTo(Object o) {
/* 61 */       PrioritizedItem other = (PrioritizedItem)o;
/* 62 */       if (this.priority != other.priority) {
/* 63 */         return other.priority - this.priority;
/*    */       }
/* 65 */       return other.id - this.id;
/*    */     }
/*    */ 
/*    */     public boolean equals(Object obj)
/*    */     {
/* 70 */       return this.id == ((PrioritizedItem)obj).id;
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.core.util.PrioritizedList
 * JD-Core Version:    0.6.2
 */