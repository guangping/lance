/*    */ package com.ztesoft.inf.extend.xstream.mapper;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.InitializationException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class DefaultImplementationsMapper extends MapperWrapper
/*    */ {
/* 31 */   private final Map typeToImpl = new HashMap();
/* 32 */   private transient Map implToType = new HashMap();
/*    */ 
/*    */   public DefaultImplementationsMapper(Mapper wrapped) {
/* 35 */     super(wrapped);
/* 36 */     addDefaults();
/*    */   }
/*    */ 
/*    */   protected void addDefaults()
/*    */   {
/* 41 */     addDefaultImplementation(null, Mapper.Null.class);
/*    */ 
/* 43 */     addDefaultImplementation(Boolean.class, Boolean.TYPE);
/* 44 */     addDefaultImplementation(Character.class, Character.TYPE);
/* 45 */     addDefaultImplementation(Integer.class, Integer.TYPE);
/* 46 */     addDefaultImplementation(Float.class, Float.TYPE);
/* 47 */     addDefaultImplementation(Double.class, Double.TYPE);
/* 48 */     addDefaultImplementation(Short.class, Short.TYPE);
/* 49 */     addDefaultImplementation(Byte.class, Byte.TYPE);
/* 50 */     addDefaultImplementation(Long.class, Long.TYPE);
/*    */ 
/* 52 */     addDefaultImplementation(ArrayList.class, Collection.class);
/*    */   }
/*    */ 
/*    */   public void addDefaultImplementation(Class defaultImplementation, Class ofType)
/*    */   {
/* 57 */     if ((defaultImplementation != null) && (defaultImplementation.isInterface()))
/*    */     {
/* 59 */       throw new InitializationException("Default implementation is not a concrete class: " + defaultImplementation.getName());
/*    */     }
/*    */ 
/* 63 */     this.typeToImpl.put(ofType, defaultImplementation);
/* 64 */     this.implToType.put(defaultImplementation, ofType);
/*    */   }
/*    */ 
/*    */   public String serializedClass(Class type)
/*    */   {
/* 69 */     Class baseType = (Class)this.implToType.get(type);
/* 70 */     return baseType == null ? super.serializedClass(type) : super.serializedClass(baseType);
/*    */   }
/*    */ 
/*    */   public Class defaultImplementationOf(Class type)
/*    */   {
/* 76 */     if (this.typeToImpl.containsKey(type)) {
/* 77 */       return (Class)this.typeToImpl.get(type);
/*    */     }
/* 79 */     return super.defaultImplementationOf(type);
/*    */   }
/*    */ 
/*    */   private Object readResolve()
/*    */   {
/* 84 */     this.implToType = new HashMap();
/* 85 */     Iterator iter = this.typeToImpl.keySet().iterator();
/* 86 */     while (iter.hasNext()) {
/* 87 */       Object type = iter.next();
/* 88 */       this.implToType.put(this.typeToImpl.get(type), type);
/*    */     }
/* 90 */     return this;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.mapper.DefaultImplementationsMapper
 * JD-Core Version:    0.6.2
 */