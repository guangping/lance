/*    */ package com.ztesoft.inf.extend.xstream.mapper;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public abstract class AbstractAttributeAliasingMapper extends MapperWrapper
/*    */ {
/* 25 */   protected final Map aliasToName = new HashMap();
/* 26 */   protected transient Map nameToAlias = new HashMap();
/*    */ 
/*    */   public AbstractAttributeAliasingMapper(Mapper wrapped) {
/* 29 */     super(wrapped);
/*    */   }
/*    */ 
/*    */   public void addAliasFor(String attributeName, String alias) {
/* 33 */     this.aliasToName.put(alias, attributeName);
/* 34 */     this.nameToAlias.put(attributeName, alias);
/*    */   }
/*    */ 
/*    */   private Object readResolve() {
/* 38 */     this.nameToAlias = new HashMap();
/* 39 */     Iterator iter = this.aliasToName.keySet().iterator();
/* 40 */     while (iter.hasNext()) {
/* 41 */       Object alias = iter.next();
/* 42 */       this.nameToAlias.put(this.aliasToName.get(alias), alias);
/*    */     }
/* 44 */     return this;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.mapper.AbstractAttributeAliasingMapper
 * JD-Core Version:    0.6.2
 */