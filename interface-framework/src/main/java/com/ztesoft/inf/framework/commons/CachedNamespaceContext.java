/*    */ package com.ztesoft.inf.framework.commons;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import javax.xml.namespace.NamespaceContext;
/*    */ 
/*    */ public class CachedNamespaceContext
/*    */   implements NamespaceContext
/*    */ {
/* 11 */   protected Map<String, String> prefix2Uri = new HashMap();
/* 12 */   protected Map<String, String> uri2Prefix = new HashMap();
/*    */   protected static final String DEFAULT_NS = "DEFAULT";
/*    */ 
/*    */   public CachedNamespaceContext()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CachedNamespaceContext(Map namespaces)
/*    */   {
/* 18 */     putInCache(namespaces);
/*    */   }
/*    */   public void putInCache(Map<String, String> namespaces) {
/* 21 */     if (namespaces == null)
/* 22 */       return;
/* 23 */     for (String key : namespaces.keySet())
/* 24 */       putInCache(key, (String)namespaces.get(key));
/*    */   }
/*    */ 
/*    */   public String getNamespaceURI(String prefix) {
/* 28 */     return (String)this.prefix2Uri.get(prefix);
/*    */   }
/*    */   public void putInCache(String prefix, String uri) {
/* 31 */     if (prefix == null)
/* 32 */       throw new IllegalArgumentException("prefix could not  be null");
/* 33 */     this.prefix2Uri.put(prefix, uri);
/* 34 */     this.uri2Prefix.put(uri, prefix);
/*    */   }
/*    */   public String getPrefix(String namespaceURI) {
/* 37 */     return (String)this.uri2Prefix.get(namespaceURI);
/*    */   }
/*    */ 
/*    */   public Iterator getPrefixes(String namespaceURI) {
/* 41 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.framework.commons.CachedNamespaceContext
 * JD-Core Version:    0.6.2
 */