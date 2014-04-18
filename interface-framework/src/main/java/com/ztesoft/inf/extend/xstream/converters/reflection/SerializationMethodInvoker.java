/*     */ package com.ztesoft.inf.extend.xstream.converters.reflection;
/*     */ 
/*     */ import com.ztesoft.inf.extend.xstream.converters.ConversionException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class SerializationMethodInvoker
/*     */ {
/*  32 */   private Map cache = Collections.synchronizedMap(new HashMap());
/*  33 */   private static final Object NO_METHOD = new Object();
/*  34 */   private static final Object[] EMPTY_ARGS = new Object[0];
/*     */ 
/*     */   public Object callReadResolve(Object result)
/*     */   {
/*  41 */     if (result == null) {
/*  42 */       return null;
/*     */     }
/*  44 */     Method readResolveMethod = getMethod(result.getClass(), "readResolve", null, true);
/*     */ 
/*  46 */     if (readResolveMethod != null) {
/*     */       try {
/*  48 */         return readResolveMethod.invoke(result, EMPTY_ARGS);
/*     */       } catch (IllegalAccessException e) {
/*  50 */         throw new ObjectAccessException("Could not call " + result.getClass().getName() + ".readResolve()", e);
/*     */       }
/*     */       catch (InvocationTargetException e) {
/*  53 */         throw new ObjectAccessException("Could not call " + result.getClass().getName() + ".readResolve()", e.getTargetException());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  58 */     return result;
/*     */   }
/*     */ 
/*     */   public Object callWriteReplace(Object object)
/*     */   {
/*  64 */     if (object == null) {
/*  65 */       return null;
/*     */     }
/*  67 */     Method writeReplaceMethod = getMethod(object.getClass(), "writeReplace", null, true);
/*     */ 
/*  69 */     if (writeReplaceMethod != null) {
/*     */       try {
/*  71 */         Object[] args = new Object[0];
/*  72 */         return writeReplaceMethod.invoke(object, args);
/*     */       } catch (IllegalAccessException e) {
/*  74 */         throw new ObjectAccessException("Could not call " + object.getClass().getName() + ".writeReplace()", e);
/*     */       }
/*     */       catch (InvocationTargetException e)
/*     */       {
/*  78 */         throw new ObjectAccessException("Could not call " + object.getClass().getName() + ".writeReplace()", e.getTargetException());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  83 */     return object;
/*     */   }
/*     */ 
/*     */   public boolean supportsReadObject(Class type, boolean includeBaseClasses)
/*     */   {
/*  89 */     return getMethod(type, "readObject", new Class[] { ObjectInputStream.class }, includeBaseClasses) != null;
/*     */   }
/*     */ 
/*     */   public void callReadObject(Class type, Object object, ObjectInputStream stream)
/*     */   {
/*     */     try
/*     */     {
/*  96 */       Method readObjectMethod = getMethod(type, "readObject", new Class[] { ObjectInputStream.class }, false);
/*     */ 
/*  98 */       readObjectMethod.invoke(object, new Object[] { stream });
/*     */     } catch (IllegalAccessException e) {
/* 100 */       throw new ConversionException("Could not call " + object.getClass().getName() + ".readObject()", e);
/*     */     }
/*     */     catch (InvocationTargetException e) {
/* 103 */       throw new ConversionException("Could not call " + object.getClass().getName() + ".readObject()", e.getTargetException());
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean supportsWriteObject(Class type, boolean includeBaseClasses)
/*     */   {
/* 110 */     return getMethod(type, "writeObject", new Class[] { ObjectOutputStream.class }, includeBaseClasses) != null;
/*     */   }
/*     */ 
/*     */   public void callWriteObject(Class type, Object instance, ObjectOutputStream stream)
/*     */   {
/*     */     try
/*     */     {
/* 117 */       Method readObjectMethod = getMethod(type, "writeObject", new Class[] { ObjectOutputStream.class }, false);
/*     */ 
/* 119 */       readObjectMethod.invoke(instance, new Object[] { stream });
/*     */     } catch (IllegalAccessException e) {
/* 121 */       throw new ConversionException("Could not call " + instance.getClass().getName() + ".writeObject()", e);
/*     */     }
/*     */     catch (InvocationTargetException e) {
/* 124 */       throw new ConversionException("Could not call " + instance.getClass().getName() + ".writeObject()", e.getTargetException());
/*     */     }
/*     */   }
/*     */ 
/*     */   private Method getMethod(Class _type, String name, Class[] parameterTypes, boolean includeBaseclasses)
/*     */   {
/* 132 */     Class type = _type;
/* 133 */     String typeName = type.getName();
/* 134 */     StringBuffer sb = new StringBuffer(typeName.length() + name.length() + 7);
/*     */ 
/* 136 */     sb.append(typeName).append('.').append(name).append('.').append(includeBaseclasses).toString();
/*     */ 
/* 138 */     String key = sb.toString();
/* 139 */     Object resultOb = this.cache.get(key);
/*     */ 
/* 141 */     if (resultOb != null) {
/* 142 */       return resultOb == NO_METHOD ? null : (Method)resultOb;
/*     */     }
/* 144 */     if (includeBaseclasses) {
/* 145 */       while (type != null) {
/*     */         try {
/* 147 */           Method result = type.getDeclaredMethod(name, parameterTypes);
/*     */ 
/* 149 */           result.setAccessible(true);
/* 150 */           this.cache.put(key, result);
/* 151 */           return result;
/*     */         } catch (NoSuchMethodException e) {
/* 153 */           type = type.getSuperclass();
/*     */         }
/*     */       }
/* 156 */       this.cache.put(key, NO_METHOD);
/* 157 */       return null;
/*     */     }
/*     */     try {
/* 160 */       Method result = type.getDeclaredMethod(name, parameterTypes);
/* 161 */       result.setAccessible(true);
/* 162 */       this.cache.put(key, result);
/* 163 */       return result;
/*     */     } catch (NoSuchMethodException e) {
/* 165 */       this.cache.put(key, NO_METHOD);
/* 166 */     }return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.reflection.SerializationMethodInvoker
 * JD-Core Version:    0.6.2
 */