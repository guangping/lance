/*     */ package com.ztesoft.inf.extend.xstream.mapper;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public class ArrayMapper extends MapperWrapper
/*     */ {
/*   8 */   private static final Collection BOXED_TYPES = Arrays.asList(new Class[] { Boolean.class, Byte.class, Character.class, Short.class, Integer.class, Long.class, Float.class, Double.class });
/*     */ 
/*     */   public ArrayMapper(Mapper wrapped)
/*     */   {
/*  13 */     super(wrapped);
/*     */   }
/*     */ 
/*     */   public String serializedClass(Class _type)
/*     */   {
/*  18 */     StringBuffer arraySuffix = new StringBuffer();
/*  19 */     String name = null;
/*  20 */     Class type = _type;
/*  21 */     while (type.isArray()) {
/*  22 */       name = super.serializedClass(type);
/*  23 */       if (!type.getName().equals(name)) break;
/*  24 */       type = type.getComponentType();
/*  25 */       arraySuffix.append("-array");
/*  26 */       name = null;
/*     */     }
/*     */ 
/*  31 */     if (name == null) {
/*  32 */       name = boxedTypeName(type);
/*     */     }
/*  34 */     if (name == null) {
/*  35 */       name = super.serializedClass(type);
/*     */     }
/*  37 */     if (arraySuffix.length() > 0) {
/*  38 */       return name + arraySuffix;
/*     */     }
/*  40 */     return name;
/*     */   }
/*     */ 
/*     */   public Class realClass(String _elementName)
/*     */   {
/*  46 */     String elementName = _elementName;
/*  47 */     int dimensions = 0;
/*  48 */     while (elementName.endsWith("-array")) {
/*  49 */       elementName = elementName.substring(0, elementName.length() - 6);
/*  50 */       dimensions++;
/*     */     }
/*  52 */     if (dimensions > 0) {
/*  53 */       Class componentType = primitiveClassNamed(elementName);
/*  54 */       if (componentType == null) {
/*  55 */         componentType = super.realClass(elementName);
/*     */       }
/*  57 */       while (componentType.isArray()) {
/*  58 */         componentType = componentType.getComponentType();
/*  59 */         dimensions++;
/*     */       }
/*  61 */       return super.realClass(arrayType(dimensions, componentType));
/*     */     }
/*  63 */     return super.realClass(elementName);
/*     */   }
/*     */ 
/*     */   private String arrayType(int dimensions, Class componentType)
/*     */   {
/*  68 */     StringBuffer className = new StringBuffer();
/*  69 */     for (int i = 0; i < dimensions; i++) {
/*  70 */       className.append('[');
/*     */     }
/*  72 */     if (componentType.isPrimitive()) {
/*  73 */       className.append(charThatJavaUsesToRepresentPrimitiveArrayType(componentType));
/*     */ 
/*  75 */       return className.toString();
/*     */     }
/*  77 */     className.append('L').append(componentType.getName()).append(';');
/*  78 */     return className.toString();
/*     */   }
/*     */ 
/*     */   private Class primitiveClassNamed(String name)
/*     */   {
/*  83 */     return name.equals("double") ? Double.TYPE : name.equals("float") ? Float.TYPE : name.equals("long") ? Long.TYPE : name.equals("int") ? Integer.TYPE : name.equals("short") ? Short.TYPE : name.equals("char") ? Character.TYPE : name.equals("byte") ? Byte.TYPE : name.equals("boolean") ? Boolean.TYPE : name.equals("void") ? Void.TYPE : null;
/*     */   }
/*     */ 
/*     */   private char charThatJavaUsesToRepresentPrimitiveArrayType(Class primvCls)
/*     */   {
/*  95 */     return primvCls == Double.TYPE ? 'D' : primvCls == Float.TYPE ? 'F' : primvCls == Long.TYPE ? 'J' : primvCls == Integer.TYPE ? 'I' : primvCls == Short.TYPE ? 'S' : primvCls == Character.TYPE ? 'C' : primvCls == Byte.TYPE ? 'B' : primvCls == Boolean.TYPE ? 'Z' : '\000';
/*     */   }
/*     */ 
/*     */   private String boxedTypeName(Class type)
/*     */   {
/* 107 */     return BOXED_TYPES.contains(type) ? type.getName() : null;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.mapper.ArrayMapper
 * JD-Core Version:    0.6.2
 */