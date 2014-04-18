/*     */ package com.ztesoft.inf.extend.xstream.io.path;
/*     */ 
/*     */ import com.ztesoft.inf.extend.xstream.core.util.FastStack;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Path
/*     */ {
/*     */   private final String[] chunks;
/*     */   private transient String pathAsString;
/*  59 */   private static final Path DOT = new Path(new String[] { "." });
/*     */ 
/*     */   public Path(String pathAsString)
/*     */   {
/*  63 */     List result = new ArrayList();
/*  64 */     int currentIndex = 0;
/*     */     int nextSeperator;
/*  66 */     while ((nextSeperator = pathAsString.indexOf('/', currentIndex)) != -1) {
/*  67 */       result.add(pathAsString.substring(currentIndex, nextSeperator));
/*  68 */       currentIndex = nextSeperator + 1;
/*     */     }
/*  70 */     result.add(pathAsString.substring(currentIndex));
/*  71 */     String[] arr = new String[result.size()];
/*  72 */     result.toArray(arr);
/*  73 */     this.chunks = arr;
/*  74 */     this.pathAsString = pathAsString;
/*     */   }
/*     */ 
/*     */   public Path(String[] chunks) {
/*  78 */     this.chunks = chunks;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  83 */     if (this.pathAsString == null) {
/*  84 */       StringBuffer buffer = new StringBuffer();
/*  85 */       for (int i = 0; i < this.chunks.length; i++) {
/*  86 */         if (i > 0)
/*  87 */           buffer.append('/');
/*  88 */         buffer.append(this.chunks[i]);
/*     */       }
/*  90 */       this.pathAsString = buffer.toString();
/*     */     }
/*  92 */     return this.pathAsString;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object o)
/*     */   {
/*  97 */     if (this == o)
/*  98 */       return true;
/*  99 */     if (!(o instanceof Path)) {
/* 100 */       return false;
/*     */     }
/* 102 */     Path other = (Path)o;
/* 103 */     if (this.chunks.length != other.chunks.length)
/* 104 */       return false;
/* 105 */     for (int i = 0; i < this.chunks.length; i++) {
/* 106 */       if (!this.chunks[i].equals(other.chunks[i])) {
/* 107 */         return false;
/*     */       }
/*     */     }
/* 110 */     return true;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 115 */     int result = 543645643;
/* 116 */     for (int i = 0; i < this.chunks.length; i++) {
/* 117 */       result = 29 * result + this.chunks[i].hashCode();
/*     */     }
/* 119 */     return result;
/*     */   }
/*     */ 
/*     */   public Path relativeTo(Path that) {
/* 123 */     int depthOfPathDivergence = depthOfPathDivergence(this.chunks, that.chunks);
/* 124 */     String[] result = new String[this.chunks.length + that.chunks.length - 2 * depthOfPathDivergence];
/*     */ 
/* 126 */     int count = 0;
/*     */ 
/* 128 */     for (int i = depthOfPathDivergence; i < this.chunks.length; i++) {
/* 129 */       result[(count++)] = "..";
/*     */     }
/* 131 */     for (int j = depthOfPathDivergence; j < that.chunks.length; j++) {
/* 132 */       result[(count++)] = that.chunks[j];
/*     */     }
/*     */ 
/* 135 */     if (count == 0) {
/* 136 */       return DOT;
/*     */     }
/* 138 */     return new Path(result);
/*     */   }
/*     */ 
/*     */   private int depthOfPathDivergence(String[] path1, String[] path2)
/*     */   {
/* 143 */     int minLength = Math.min(path1.length, path2.length);
/* 144 */     for (int i = 0; i < minLength; i++) {
/* 145 */       if (!path1[i].equals(path2[i])) {
/* 146 */         return i;
/*     */       }
/*     */     }
/* 149 */     return minLength;
/*     */   }
/*     */ 
/*     */   public Path apply(Path relativePath) {
/* 153 */     FastStack absoluteStack = new FastStack(16);
/*     */ 
/* 155 */     for (int i = 0; i < this.chunks.length; i++) {
/* 156 */       absoluteStack.push(this.chunks[i]);
/*     */     }
/*     */ 
/* 159 */     for (int i = 0; i < relativePath.chunks.length; i++) {
/* 160 */       String relativeChunk = relativePath.chunks[i];
/* 161 */       if (relativeChunk.equals(".."))
/* 162 */         absoluteStack.pop();
/* 163 */       else if (!relativeChunk.equals(".")) {
/* 164 */         absoluteStack.push(relativeChunk);
/*     */       }
/*     */     }
/*     */ 
/* 168 */     String[] result = new String[absoluteStack.size()];
/* 169 */     for (int i = 0; i < result.length; i++) {
/* 170 */       result[i] = ((String)absoluteStack.get(i));
/*     */     }
/*     */ 
/* 173 */     return new Path(result);
/*     */   }
/*     */ 
/*     */   public boolean isAncestor(Path child) {
/* 177 */     if ((child == null) || (child.chunks.length < this.chunks.length)) {
/* 178 */       return false;
/*     */     }
/* 180 */     for (int i = 0; i < this.chunks.length; i++) {
/* 181 */       if (!this.chunks[i].equals(child.chunks[i])) {
/* 182 */         return false;
/*     */       }
/*     */     }
/* 185 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.io.path.Path
 * JD-Core Version:    0.6.2
 */