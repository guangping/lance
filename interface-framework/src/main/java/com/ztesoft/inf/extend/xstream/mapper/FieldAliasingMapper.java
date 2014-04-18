/*    */ package com.ztesoft.inf.extend.xstream.mapper;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.core.util.FastField;
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class FieldAliasingMapper extends MapperWrapper
/*    */ {
/* 29 */   protected final Map fieldToAliasMap = new HashMap();
/* 30 */   protected final Map aliasToFieldMap = new HashMap();
/* 31 */   protected final Set fieldsToOmit = new HashSet();
/*    */ 
/*    */   public FieldAliasingMapper(Mapper wrapped) {
/* 34 */     super(wrapped);
/*    */   }
/*    */ 
/*    */   public void addFieldAlias(String alias, Class type, String fieldName) {
/* 38 */     this.fieldToAliasMap.put(key(type, fieldName), alias);
/* 39 */     this.aliasToFieldMap.put(key(type, alias), fieldName);
/*    */   }
/*    */ 
/*    */   private Object key(Class type, String name) {
/* 43 */     return new FastField(type, name);
/*    */   }
/*    */ 
/*    */   public String serializedMember(Class type, String memberName)
/*    */   {
/* 48 */     String alias = getMember(type, memberName, this.fieldToAliasMap);
/* 49 */     if (alias == null) {
/* 50 */       return super.serializedMember(type, memberName);
/*    */     }
/* 52 */     return alias;
/*    */   }
/*    */ 
/*    */   public String realMember(Class type, String serialized)
/*    */   {
/* 58 */     String real = getMember(type, serialized, this.aliasToFieldMap);
/* 59 */     if (real == null) {
/* 60 */       return super.realMember(type, serialized);
/*    */     }
/* 62 */     return real;
/*    */   }
/*    */ 
/*    */   private String getMember(Class type, String name, Map map)
/*    */   {
/* 67 */     String member = null;
/* 68 */     for (Class declaringType = type; 
/* 69 */       (member == null) && (declaringType != Object.class); declaringType = declaringType.getSuperclass())
/*    */     {
/* 71 */       member = (String)map.get(key(declaringType, name));
/*    */     }
/* 73 */     return member;
/*    */   }
/*    */ 
/*    */   public boolean shouldSerializeMember(Class definedIn, String fieldName)
/*    */   {
/* 78 */     return !this.fieldsToOmit.contains(key(definedIn, fieldName));
/*    */   }
/*    */ 
/*    */   public void omitField(Class definedIn, String fieldName) {
/* 82 */     this.fieldsToOmit.add(key(definedIn, fieldName));
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.mapper.FieldAliasingMapper
 * JD-Core Version:    0.6.2
 */