/*    */ package com.ztesoft.inf.extend.xstream.mapper;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public class AttributeAliasingMapper extends AbstractAttributeAliasingMapper
/*    */ {
/*    */   public AttributeAliasingMapper(Mapper wrapped)
/*    */   {
/* 24 */     super(wrapped);
/*    */   }
/*    */ 
/*    */   public String aliasForAttribute(String attribute)
/*    */   {
/* 29 */     String alias = (String)this.nameToAlias.get(attribute);
/* 30 */     return alias == null ? super.aliasForAttribute(attribute) : alias;
/*    */   }
/*    */ 
/*    */   public String attributeForAlias(String alias)
/*    */   {
/* 35 */     String name = (String)this.aliasToName.get(alias);
/* 36 */     return name == null ? super.attributeForAlias(alias) : name;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.mapper.AttributeAliasingMapper
 * JD-Core Version:    0.6.2
 */