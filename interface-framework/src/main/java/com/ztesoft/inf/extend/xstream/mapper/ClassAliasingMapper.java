/*    */ package com.ztesoft.inf.extend.xstream.mapper;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class ClassAliasingMapper extends MapperWrapper
/*    */ {
/*  9 */   private final Map typeToName = new HashMap();
/* 10 */   private final Map classToName = new HashMap();
/* 11 */   private transient Map nameToType = new HashMap();
/*    */ 
/*    */   public ClassAliasingMapper(Mapper wrapped) {
/* 14 */     super(wrapped);
/*    */   }
/*    */ 
/*    */   public void addClassAlias(String name, Class type) {
/* 18 */     this.nameToType.put(name, type.getName());
/* 19 */     this.classToName.put(type.getName(), name);
/*    */   }
/*    */ 
/*    */   public void addTypeAlias(String name, Class type) {
/* 23 */     this.nameToType.put(name, type.getName());
/* 24 */     this.typeToName.put(type, name);
/*    */   }
/*    */ 
/*    */   public String serializedClass(Class type)
/*    */   {
/* 29 */     String alias = (String)this.classToName.get(type.getName());
/* 30 */     if (alias != null) {
/* 31 */       return alias;
/*    */     }
/* 33 */     Iterator iter = this.typeToName.keySet().iterator();
/* 34 */     while (iter.hasNext()) {
/* 35 */       Class compatibleType = (Class)iter.next();
/* 36 */       if (compatibleType.isAssignableFrom(type)) {
/* 37 */         return (String)this.typeToName.get(compatibleType);
/*    */       }
/*    */     }
/* 40 */     return super.serializedClass(type);
/*    */   }
/*    */ 
/*    */   public Class realClass(String _elementName)
/*    */   {
/* 47 */     String elementName = _elementName;
/* 48 */     String mappedName = (String)this.nameToType.get(elementName);
/*    */ 
/* 50 */     if (mappedName != null) {
/* 51 */       Class type = primitiveClassNamed(mappedName);
/* 52 */       if (type != null) {
/* 53 */         return type;
/*    */       }
/* 55 */       elementName = mappedName;
/*    */     }
/*    */ 
/* 58 */     return super.realClass(elementName);
/*    */   }
/*    */ 
/*    */   public boolean itemTypeAsAttribute(Class clazz) {
/* 62 */     return this.classToName.containsKey(clazz);
/*    */   }
/*    */ 
/*    */   public boolean aliasIsAttribute(String name) {
/* 66 */     return this.nameToType.containsKey(name);
/*    */   }
/*    */ 
/*    */   private Object readResolve() {
/* 70 */     this.nameToType = new HashMap();
/* 71 */     Iterator iter = this.classToName.keySet().iterator();
/* 72 */     while (iter.hasNext()) {
/* 73 */       Object type = iter.next();
/* 74 */       this.nameToType.put(this.classToName.get(type), type);
/*    */     }
/* 76 */     Iterator iter = this.typeToName.keySet().iterator();
/* 77 */     while (iter.hasNext()) {
/* 78 */       Class type = (Class)iter.next();
/* 79 */       this.nameToType.put(this.typeToName.get(type), type.getName());
/*    */     }
/* 81 */     return this;
/*    */   }
/*    */ 
/*    */   private Class primitiveClassNamed(String name) {
/* 85 */     return name.equals("double") ? Double.TYPE : name.equals("float") ? Float.TYPE : name.equals("long") ? Long.TYPE : name.equals("int") ? Integer.TYPE : name.equals("short") ? Short.TYPE : name.equals("char") ? Character.TYPE : name.equals("byte") ? Byte.TYPE : name.equals("boolean") ? Boolean.TYPE : name.equals("void") ? Void.TYPE : null;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.mapper.ClassAliasingMapper
 * JD-Core Version:    0.6.2
 */