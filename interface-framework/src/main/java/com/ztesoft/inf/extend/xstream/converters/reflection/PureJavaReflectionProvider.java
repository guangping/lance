/*     */ package com.ztesoft.inf.extend.xstream.converters.reflection;
/*     */ 
/*     */ import com.ztesoft.inf.extend.xstream.core.JVM;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectStreamClass;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PureJavaReflectionProvider
/*     */   implements ReflectionProvider
/*     */ {
/*  49 */   private transient Map serializedDataCache = Collections.synchronizedMap(new HashMap());
/*     */   protected FieldDictionary fieldDictionary;
/*     */ 
/*     */   public PureJavaReflectionProvider()
/*     */   {
/*  54 */     this(new FieldDictionary(new ImmutableFieldKeySorter()));
/*     */   }
/*     */ 
/*     */   public PureJavaReflectionProvider(FieldDictionary fieldDictionary) {
/*  58 */     this.fieldDictionary = fieldDictionary;
/*     */   }
/*     */ 
/*     */   public Object newInstance(Class type) {
/*     */     try {
/*  63 */       Constructor[] constructors = type.getDeclaredConstructors();
/*  64 */       for (int i = 0; i < constructors.length; i++) {
/*  65 */         if (constructors[i].getParameterTypes().length == 0) {
/*  66 */           if (!Modifier.isPublic(constructors[i].getModifiers())) {
/*  67 */             constructors[i].setAccessible(true);
/*     */           }
/*  69 */           return constructors[i].newInstance(new Object[0]);
/*     */         }
/*     */       }
/*  72 */       if (Serializable.class.isAssignableFrom(type)) {
/*  73 */         return instantiateUsingSerialization(type);
/*     */       }
/*  75 */       throw new ObjectAccessException("Cannot construct " + type.getName() + " as it does not have a no-args constructor");
/*     */     }
/*     */     catch (InstantiationException e)
/*     */     {
/*  80 */       throw new ObjectAccessException("Cannot construct " + type.getName(), e);
/*     */     }
/*     */     catch (IllegalAccessException e) {
/*  83 */       throw new ObjectAccessException("Cannot construct " + type.getName(), e);
/*     */     }
/*     */     catch (InvocationTargetException e) {
/*  86 */       if ((e.getTargetException() instanceof RuntimeException))
/*  87 */         throw ((RuntimeException)e.getTargetException());
/*  88 */       if ((e.getTargetException() instanceof Error)) {
/*  89 */         throw ((Error)e.getTargetException());
/*     */       }
/*  91 */       throw new ObjectAccessException("Constructor for " + type.getName() + " threw an exception", e.getTargetException());
/*     */     }
/*     */   }
/*     */ 
/*     */   private Object instantiateUsingSerialization(Class type)
/*     */   {
/*     */     try
/*     */     {
/*     */       byte[] data;
/*     */       byte[] data;
/* 101 */       if (this.serializedDataCache.containsKey(type)) {
/* 102 */         data = (byte[])this.serializedDataCache.get(type);
/*     */       } else {
/* 104 */         ByteArrayOutputStream bytes = new ByteArrayOutputStream();
/* 105 */         DataOutputStream stream = new DataOutputStream(bytes);
/* 106 */         stream.writeShort(-21267);
/* 107 */         stream.writeShort(5);
/* 108 */         stream.writeByte(115);
/* 109 */         stream.writeByte(114);
/* 110 */         stream.writeUTF(type.getName());
/* 111 */         stream.writeLong(ObjectStreamClass.lookup(type).getSerialVersionUID());
/*     */ 
/* 113 */         stream.writeByte(2);
/* 114 */         stream.writeShort(0);
/* 115 */         stream.writeByte(120);
/* 116 */         stream.writeByte(112);
/* 117 */         data = bytes.toByteArray();
/* 118 */         this.serializedDataCache.put(type, data);
/*     */       }
/*     */ 
/* 121 */       ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));
/*     */ 
/* 123 */       return in.readObject();
/*     */     } catch (IOException e) {
/* 125 */       throw new ObjectAccessException("Cannot create " + type.getName() + " by JDK serialization", e);
/*     */     }
/*     */     catch (ClassNotFoundException e) {
/* 128 */       throw new ObjectAccessException("Cannot find class " + e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void visitSerializableFields(Object object, ReflectionProvider.Visitor visitor)
/*     */   {
/* 135 */     Iterator iterator = this.fieldDictionary.fieldsFor(object.getClass());
/* 136 */     while (iterator.hasNext()) {
/* 137 */       Field field = (Field)iterator.next();
/* 138 */       if (fieldModifiersSupported(field))
/*     */       {
/* 141 */         validateFieldAccess(field);
/*     */         try {
/* 143 */           Object value = field.get(object);
/* 144 */           visitor.visit(field.getName(), field.getType(), field.getDeclaringClass(), value);
/*     */         }
/*     */         catch (IllegalArgumentException e) {
/* 147 */           throw new ObjectAccessException("Could not get field " + field.getClass() + "." + field.getName(), e);
/*     */         }
/*     */         catch (IllegalAccessException e) {
/* 150 */           throw new ObjectAccessException("Could not get field " + field.getClass() + "." + field.getName(), e);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void writeField(Object object, String fieldName, Object value, Class definedIn)
/*     */   {
/* 158 */     Field field = this.fieldDictionary.field(object.getClass(), fieldName, definedIn);
/*     */ 
/* 160 */     validateFieldAccess(field);
/*     */     try {
/* 162 */       field.set(object, value);
/*     */     } catch (IllegalArgumentException e) {
/* 164 */       throw new ObjectAccessException("Could not set field " + object.getClass() + "." + field.getName(), e);
/*     */     }
/*     */     catch (IllegalAccessException e) {
/* 167 */       throw new ObjectAccessException("Could not set field " + object.getClass() + "." + field.getName(), e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Class getFieldType(Object object, String fieldName, Class definedIn)
/*     */   {
/* 173 */     return this.fieldDictionary.field(object.getClass(), fieldName, definedIn).getType();
/*     */   }
/*     */ 
/*     */   public boolean fieldDefinedInClass(String fieldName, Class type)
/*     */   {
/*     */     try {
/* 179 */       Field field = this.fieldDictionary.field(type, fieldName, null);
/* 180 */       return (fieldModifiersSupported(field)) || (Modifier.isTransient(field.getModifiers()));
/*     */     } catch (ObjectAccessException e) {
/*     */     }
/* 183 */     return false;
/*     */   }
/*     */ 
/*     */   protected boolean fieldModifiersSupported(Field field)
/*     */   {
/* 188 */     return (!Modifier.isStatic(field.getModifiers())) && (!Modifier.isTransient(field.getModifiers()));
/*     */   }
/*     */ 
/*     */   protected void validateFieldAccess(Field field)
/*     */   {
/* 193 */     if (Modifier.isFinal(field.getModifiers()))
/* 194 */       if (JVM.is15())
/* 195 */         field.setAccessible(true);
/*     */       else
/* 197 */         throw new ObjectAccessException("Invalid final field " + field.getDeclaringClass().getName() + "." + field.getName());
/*     */   }
/*     */ 
/*     */   public Field getField(Class definedIn, String fieldName)
/*     */   {
/* 205 */     return this.fieldDictionary.field(definedIn, fieldName, null);
/*     */   }
/*     */ 
/*     */   public void setFieldDictionary(FieldDictionary dictionary) {
/* 209 */     this.fieldDictionary = dictionary;
/*     */   }
/*     */ 
/*     */   protected Object readResolve() {
/* 213 */     this.serializedDataCache = Collections.synchronizedMap(new HashMap());
/* 214 */     return this;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.reflection.PureJavaReflectionProvider
 * JD-Core Version:    0.6.2
 */