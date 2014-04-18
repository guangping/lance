/*     */ package com.ztesoft.inf.extend.xstream.converters.reflection;
/*     */ 
/*     */ import com.ztesoft.inf.extend.xstream.core.JVM;
/*     */ import com.ztesoft.inf.extend.xstream.core.util.OrderRetainingMap;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ 
/*     */ public class FieldDictionary
/*     */ {
/*     */   private transient Map keyedByFieldNameCache;
/*     */   private transient Map keyedByFieldKeyCache;
/*     */   private final FieldKeySorter sorter;
/*     */ 
/*     */   public FieldDictionary()
/*     */   {
/*  41 */     this(new ImmutableFieldKeySorter());
/*     */   }
/*     */ 
/*     */   public FieldDictionary(FieldKeySorter sorter) {
/*  45 */     this.sorter = sorter;
/*  46 */     init();
/*     */   }
/*     */ 
/*     */   private void init() {
/*  50 */     this.keyedByFieldNameCache = new WeakHashMap();
/*  51 */     this.keyedByFieldKeyCache = new WeakHashMap();
/*  52 */     this.keyedByFieldNameCache.put(Object.class, Collections.EMPTY_MAP);
/*  53 */     this.keyedByFieldKeyCache.put(Object.class, Collections.EMPTY_MAP);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Iterator serializableFieldsFor(Class cls)
/*     */   {
/*  66 */     return fieldsFor(cls);
/*     */   }
/*     */ 
/*     */   public Iterator fieldsFor(Class cls)
/*     */   {
/*  77 */     return buildMap(cls, true).values().iterator();
/*     */   }
/*     */ 
/*     */   public Field field(Class cls, String name, Class definedIn)
/*     */   {
/*  97 */     Map fields = buildMap(cls, definedIn != null);
/*  98 */     Field field = (Field)fields.get(definedIn != null ? new FieldKey(name, definedIn, 0) : name);
/*     */ 
/* 101 */     if (field == null) {
/* 102 */       throw new ObjectAccessException("No such field " + cls.getName() + "." + name);
/*     */     }
/*     */ 
/* 105 */     return field;
/*     */   }
/*     */ 
/*     */   private Map buildMap(Class type, boolean tupleKeyed)
/*     */   {
/* 110 */     Class cls = type;
/* 111 */     synchronized (this) {
/* 112 */       if (!this.keyedByFieldNameCache.containsKey(type)) {
/* 113 */         List superClasses = new ArrayList();
/* 114 */         while (!Object.class.equals(cls)) {
/* 115 */           superClasses.add(0, cls);
/* 116 */           cls = cls.getSuperclass();
/*     */         }
/* 118 */         Map lastKeyedByFieldName = Collections.EMPTY_MAP;
/* 119 */         Map lastKeyedByFieldKey = Collections.EMPTY_MAP;
/* 120 */         Iterator iter = superClasses.iterator();
/* 121 */         while (iter.hasNext()) {
/* 122 */           cls = (Class)iter.next();
/* 123 */           if (!this.keyedByFieldNameCache.containsKey(cls)) {
/* 124 */             Map keyedByFieldName = new HashMap(lastKeyedByFieldName);
/*     */ 
/* 126 */             Map keyedByFieldKey = new OrderRetainingMap(lastKeyedByFieldKey);
/*     */ 
/* 128 */             Field[] fields = cls.getDeclaredFields();
/*     */             int i;
/* 129 */             if (JVM.reverseFieldDefinition()) {
/* 130 */               for (i = fields.length >> 1; i-- > 0; ) {
/* 131 */                 int idx = fields.length - i - 1;
/* 132 */                 Field field = fields[i];
/* 133 */                 fields[i] = fields[idx];
/* 134 */                 fields[idx] = field;
/*     */               }
/*     */             }
/* 137 */             for (int i = 0; i < fields.length; i++) {
/* 138 */               Field field = fields[i];
/* 139 */               FieldKey fieldKey = new FieldKey(field.getName(), field.getDeclaringClass(), i);
/*     */ 
/* 141 */               field.setAccessible(true);
/* 142 */               Field existent = (Field)keyedByFieldName.get(field.getName());
/*     */ 
/* 144 */               if ((existent == null) || ((existent.getModifiers() & 0x8) != 0) || ((existent != null) && ((field.getModifiers() & 0x8) == 0)))
/*     */               {
/* 151 */                 keyedByFieldName.put(field.getName(), field);
/*     */               }
/* 153 */               keyedByFieldKey.put(fieldKey, field);
/*     */             }
/* 155 */             this.keyedByFieldNameCache.put(cls, keyedByFieldName);
/* 156 */             this.keyedByFieldKeyCache.put(cls, this.sorter.sort(type, keyedByFieldKey));
/*     */           }
/*     */ 
/* 159 */           lastKeyedByFieldName = (Map)this.keyedByFieldNameCache.get(cls);
/* 160 */           lastKeyedByFieldKey = (Map)this.keyedByFieldKeyCache.get(cls);
/*     */         }
/*     */       }
/*     */     }
/* 164 */     return (Map)(tupleKeyed ? this.keyedByFieldKeyCache.get(type) : this.keyedByFieldNameCache.get(type));
/*     */   }
/*     */ 
/*     */   protected Object readResolve()
/*     */   {
/* 169 */     init();
/* 170 */     return this;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.reflection.FieldDictionary
 * JD-Core Version:    0.6.2
 */