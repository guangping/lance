/*     */ package com.ztesoft.inf.extend.xstream.io.path;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PathTracker
/*     */ {
/*     */   private int pointer;
/*     */   private int capacity;
/*     */   private String[] pathStack;
/*     */   private Map[] indexMapStack;
/*     */   private Path currentPath;
/*     */ 
/*     */   public PathTracker()
/*     */   {
/*  60 */     this(16);
/*     */   }
/*     */ 
/*     */   public PathTracker(int initialCapacity)
/*     */   {
/*  71 */     this.capacity = Math.max(1, initialCapacity);
/*  72 */     this.pathStack = new String[this.capacity];
/*  73 */     this.indexMapStack = new Map[this.capacity];
/*     */   }
/*     */ 
/*     */   public void pushElement(String name)
/*     */   {
/*  83 */     if (this.pointer + 1 >= this.capacity) {
/*  84 */       resizeStacks(this.capacity * 2);
/*     */     }
/*  86 */     this.pathStack[this.pointer] = name;
/*  87 */     Map indexMap = this.indexMapStack[this.pointer];
/*  88 */     if (indexMap == null) {
/*  89 */       indexMap = new HashMap();
/*  90 */       this.indexMapStack[this.pointer] = indexMap;
/*     */     }
/*  92 */     if (indexMap.containsKey(name)) {
/*  93 */       indexMap.put(name, new Integer(((Integer)indexMap.get(name)).intValue() + 1));
/*     */     }
/*     */     else {
/*  96 */       indexMap.put(name, new Integer(1));
/*     */     }
/*  98 */     this.pointer += 1;
/*  99 */     this.currentPath = null;
/*     */   }
/*     */ 
/*     */   public void popElement()
/*     */   {
/* 106 */     String name = this.pathStack[(this.pointer - 1)];
/* 107 */     Map indexMap = this.indexMapStack[(this.pointer - 1)];
/* 108 */     if ((indexMap != null) && (indexMap.containsKey(name))) {
/* 109 */       indexMap.put(name, new Integer(((Integer)indexMap.get(name)).intValue() - 1));
/*     */     }
/*     */ 
/* 112 */     this.indexMapStack[this.pointer] = null;
/* 113 */     this.currentPath = null;
/* 114 */     this.pointer -= 1;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public String getCurrentPath()
/*     */   {
/* 122 */     return getPath().toString();
/*     */   }
/*     */ 
/*     */   private void resizeStacks(int newCapacity) {
/* 126 */     String[] newPathStack = new String[newCapacity];
/* 127 */     Map[] newIndexMapStack = new Map[newCapacity];
/* 128 */     int min = Math.min(this.capacity, newCapacity);
/* 129 */     System.arraycopy(this.pathStack, 0, newPathStack, 0, min);
/* 130 */     System.arraycopy(this.indexMapStack, 0, newIndexMapStack, 0, min);
/* 131 */     this.pathStack = newPathStack;
/* 132 */     this.indexMapStack = newIndexMapStack;
/* 133 */     this.capacity = newCapacity;
/*     */   }
/*     */ 
/*     */   public Path getPath()
/*     */   {
/* 140 */     if (this.currentPath == null) {
/* 141 */       String[] chunks = new String[this.pointer + 1];
/* 142 */       chunks[0] = "";
/* 143 */       for (int i = 0; i < this.pointer; i++) {
/* 144 */         Integer integer = (Integer)this.indexMapStack[i].get(this.pathStack[i]);
/* 145 */         int index = integer.intValue();
/* 146 */         if (index > 1) {
/* 147 */           StringBuffer chunk = new StringBuffer(this.pathStack[i].length() + 6);
/*     */ 
/* 149 */           chunk.append(this.pathStack[i]).append('[').append(index).append(']');
/*     */ 
/* 151 */           chunks[(i + 1)] = chunk.toString();
/*     */         } else {
/* 153 */           chunks[(i + 1)] = this.pathStack[i];
/*     */         }
/*     */       }
/* 156 */       this.currentPath = new Path(chunks);
/*     */     }
/* 158 */     return this.currentPath;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.io.path.PathTracker
 * JD-Core Version:    0.6.2
 */