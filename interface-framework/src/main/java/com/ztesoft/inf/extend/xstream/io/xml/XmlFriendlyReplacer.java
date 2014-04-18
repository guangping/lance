/*     */ package com.ztesoft.inf.extend.xstream.io.xml;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ 
/*     */ public class XmlFriendlyReplacer
/*     */ {
/*     */   private String dollarReplacement;
/*     */   private String underscoreReplacement;
/*     */   private transient Map escapeCache;
/*     */   private transient Map unescapeCache;
/*     */ 
/*     */   public XmlFriendlyReplacer()
/*     */   {
/*  46 */     this("_-", "_");
/*     */   }
/*     */ 
/*     */   public XmlFriendlyReplacer(String dollarReplacement, String underscoreReplacement)
/*     */   {
/*  59 */     this.dollarReplacement = dollarReplacement;
/*  60 */     this.underscoreReplacement = underscoreReplacement;
/*  61 */     this.escapeCache = new WeakHashMap();
/*  62 */     this.unescapeCache = new WeakHashMap();
/*     */   }
/*     */ 
/*     */   public String escapeName(String name)
/*     */   {
/*  73 */     WeakReference ref = (WeakReference)this.escapeCache.get(name);
/*  74 */     String s = (String)(ref == null ? null : ref.get());
/*     */ 
/*  76 */     if (s == null) {
/*  77 */       int length = name.length();
/*     */ 
/*  80 */       for (int i = 0; 
/*  82 */         i < length; i++) {
/*  83 */         char c = name.charAt(i);
/*  84 */         if ((c == '$') || (c == '_'))
/*     */         {
/*     */           break;
/*     */         }
/*     */       }
/*  89 */       if (i == length) {
/*  90 */         return name;
/*     */       }
/*     */ 
/*  94 */       StringBuffer result = new StringBuffer(length + 8);
/*     */ 
/*  97 */       if (i > 0) {
/*  98 */         result.append(name.substring(0, i));
/*     */       }
/*     */ 
/* 101 */       for (; i < length; i++) {
/* 102 */         char c = name.charAt(i);
/* 103 */         if (c == '$')
/* 104 */           result.append(this.dollarReplacement);
/* 105 */         else if (c == '_')
/* 106 */           result.append(this.underscoreReplacement);
/*     */         else {
/* 108 */           result.append(c);
/*     */         }
/*     */       }
/* 111 */       s = result.toString();
/* 112 */       this.escapeCache.put(name, new WeakReference(s));
/*     */     }
/* 114 */     return s;
/*     */   }
/*     */ 
/*     */   public String unescapeName(String name)
/*     */   {
/* 126 */     WeakReference ref = (WeakReference)this.unescapeCache.get(name);
/* 127 */     String s = (String)(ref == null ? null : ref.get());
/*     */ 
/* 129 */     if (s == null) {
/* 130 */       char dollarReplacementFirstChar = this.dollarReplacement.charAt(0);
/* 131 */       char underscoreReplacementFirstChar = this.underscoreReplacement.charAt(0);
/*     */ 
/* 133 */       int length = name.length();
/*     */ 
/* 136 */       for (int i = 0; 
/* 138 */         i < length; i++) {
/* 139 */         char c = name.charAt(i);
/*     */ 
/* 141 */         if ((c == dollarReplacementFirstChar) || (c == underscoreReplacementFirstChar))
/*     */         {
/*     */           break;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 149 */       if (i == length) {
/* 150 */         return name;
/*     */       }
/*     */ 
/* 154 */       StringBuffer result = new StringBuffer(length + 8);
/*     */ 
/* 157 */       if (i > 0) {
/* 158 */         result.append(name.substring(0, i));
/*     */       }
/*     */ 
/* 161 */       for (; i < length; i++) {
/* 162 */         char c = name.charAt(i);
/* 163 */         if ((c == dollarReplacementFirstChar) && (name.startsWith(this.dollarReplacement, i)))
/*     */         {
/* 165 */           i += this.dollarReplacement.length() - 1;
/* 166 */           result.append('$');
/* 167 */         } else if ((c == underscoreReplacementFirstChar) && (name.startsWith(this.underscoreReplacement, i)))
/*     */         {
/* 169 */           i += this.underscoreReplacement.length() - 1;
/* 170 */           result.append('_');
/*     */         } else {
/* 172 */           result.append(c);
/*     */         }
/*     */       }
/*     */ 
/* 176 */       s = result.toString();
/* 177 */       this.unescapeCache.put(name, new WeakReference(s));
/*     */     }
/* 179 */     return s;
/*     */   }
/*     */ 
/*     */   private Object readResolve() {
/* 183 */     this.escapeCache = new WeakHashMap();
/* 184 */     this.unescapeCache = new WeakHashMap();
/* 185 */     return this;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.io.xml.XmlFriendlyReplacer
 * JD-Core Version:    0.6.2
 */