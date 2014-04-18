/*     */ package com.ztesoft.inf.extend.xstream.converters.reflection;
/*     */ 
/*     */ import com.ztesoft.inf.extend.xstream.converters.ConversionException;
/*     */ import com.ztesoft.inf.extend.xstream.converters.Converter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.MarshallingContext;
/*     */ import com.ztesoft.inf.extend.xstream.converters.SingleValueConverter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.UnmarshallingContext;
/*     */ import com.ztesoft.inf.extend.xstream.core.util.HierarchicalStreams;
/*     */ import com.ztesoft.inf.extend.xstream.core.util.Primitives;
/*     */ import com.ztesoft.inf.extend.xstream.io.ExtendedHierarchicalStreamWriterHelper;
/*     */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
/*     */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;
/*     */ import com.ztesoft.inf.extend.xstream.io.path.Path;
/*     */ import com.ztesoft.inf.extend.xstream.io.path.PathTracker;
/*     */ import com.ztesoft.inf.extend.xstream.mapper.Mapper;
/*     */ import com.ztesoft.inf.extend.xstream.mapper.Mapper.ImplicitCollectionMapping;
/*     */ import com.ztesoft.inf.extend.xstream.mapper.Mapper.Null;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public abstract class AbstractReflectionConverter
/*     */   implements Converter
/*     */ {
/*     */   protected final ReflectionProvider reflectionProvider;
/*     */   protected transient SerializationMethodInvoker serializationMethodInvoker;
/*     */   private transient ReflectionProvider pureJavaReflectionProvider;
/*     */ 
/*     */   public AbstractReflectionConverter(ReflectionProvider reflectionProvider)
/*     */   {
/*  43 */     this.reflectionProvider = reflectionProvider;
/*  44 */     this.serializationMethodInvoker = new SerializationMethodInvoker();
/*     */   }
/*     */ 
/*     */   public void marshal(Object original, HierarchicalStreamWriter writer, MarshallingContext context)
/*     */   {
/*  49 */     Object source = this.serializationMethodInvoker.callWriteReplace(original);
/*     */ 
/*  51 */     Mapper mapper = context.getMapper();
/*  52 */     if (source.getClass() != original.getClass()) {
/*  53 */       String attributeName = mapper.aliasForSystemAttribute("resolves-to");
/*     */ 
/*  55 */       if (attributeName != null) {
/*  56 */         writer.addAttribute(attributeName, mapper.serializedClass(source.getClass()));
/*     */       }
/*     */ 
/*  59 */       context.convertAnother(source);
/*     */     } else {
/*  61 */       doMarshal(source, writer, context);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doMarshal(final Object source, final HierarchicalStreamWriter writer, final MarshallingContext context)
/*     */   {
/*  68 */     final Set seenFields = new HashSet();
/*  69 */     final Map defaultFieldDefinition = new HashMap();
/*  70 */     final Mapper mapper = context.getMapper();
/*     */ 
/*  72 */     this.reflectionProvider.visitSerializableFields(source, new ReflectionProvider.Visitor()
/*     */     {
/*     */       public void visit(String fieldName, Class type, Class definedIn, Object value)
/*     */       {
/*  77 */         if (!mapper.shouldSerializeMember(definedIn, fieldName)) {
/*  78 */           return;
/*     */         }
/*  80 */         if (!defaultFieldDefinition.containsKey(fieldName)) {
/*  81 */           Class lookupType = source.getClass();
/*     */ 
/*  88 */           defaultFieldDefinition.put(fieldName, AbstractReflectionConverter.this.reflectionProvider.getField(lookupType, fieldName));
/*     */         }
/*     */ 
/*  92 */         SingleValueConverter converter = mapper.getConverterFromItemType(fieldName, type, definedIn);
/*     */ 
/*  95 */         if (converter != null) {
/*  96 */           if (value != null) {
/*  97 */             if (seenFields.contains(fieldName)) {
/*  98 */               throw new ConversionException("Cannot write field with name '" + fieldName + "' twice as attribute for object of type " + source.getClass().getName());
/*     */             }
/*     */ 
/* 105 */             String str = converter.toString(value);
/* 106 */             if (str != null) {
/* 107 */               writer.addAttribute(mapper.aliasForAttribute(mapper.serializedMember(definedIn, fieldName)), str);
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 115 */           seenFields.add(fieldName);
/*     */         }
/*     */       }
/*     */     });
/* 120 */     this.reflectionProvider.visitSerializableFields(source, new ReflectionProvider.Visitor()
/*     */     {
/*     */       public void visit(String fieldName, Class fieldType, Class definedIn, Object newObj)
/*     */       {
/* 125 */         if (!mapper.shouldSerializeMember(definedIn, fieldName)) {
/* 126 */           return;
/*     */         }
/* 128 */         if ((!seenFields.contains(fieldName)) && (newObj != null)) {
/* 129 */           Mapper.ImplicitCollectionMapping mapping = mapper.getImplicitCollectionDefForFieldName(source.getClass(), fieldName);
/*     */ 
/* 132 */           if (mapping != null) {
/* 133 */             if (mapping.getItemFieldName() != null) {
/* 134 */               Collection list = (Collection)newObj;
/* 135 */               Iterator iter = list.iterator();
/* 136 */               while (iter.hasNext()) {
/* 137 */                 Object obj = iter.next();
/* 138 */                 writeField(fieldName, obj == null ? mapper.serializedClass(null) : mapping.getItemFieldName(), mapping.getItemType(), definedIn, obj);
/*     */               }
/*     */ 
/*     */             }
/*     */             else
/*     */             {
/* 148 */               context.convertAnother(newObj);
/*     */             }
/*     */           } else {
/* 151 */             PathTracker tracker = context.getPathTracker();
/* 152 */             tracker.pushElement(fieldName);
/* 153 */             String path = tracker.getPath().toString();
/* 154 */             String nodeName = mapper.getImplicitCollectionItemNameByPath(path);
/*     */ 
/* 156 */             Class itemType = mapper.realClassByPath(path);
/* 157 */             tracker.popElement();
/* 158 */             if (nodeName != null) {
/* 159 */               Collection list = (Collection)newObj;
/* 160 */               Iterator iter = list.iterator();
/* 161 */               while (iter.hasNext()) {
/* 162 */                 Object obj = iter.next();
/* 163 */                 writeField(fieldName, nodeName, itemType, definedIn, obj);
/*     */               }
/*     */             }
/*     */             else {
/* 167 */               tracker.pushElement(fieldName);
/* 168 */               nodeName = mapper.getAliasNameByPath(context.getPathTracker().getPath().toString());
/*     */ 
/* 172 */               tracker.popElement();
/* 173 */               writeField(fieldName, nodeName, fieldType, definedIn, newObj);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */       private void writeField(String fieldName, String aliasName, Class fieldType, Class definedIn, Object newObj)
/*     */       {
/* 182 */         String nodeName = aliasName != null ? aliasName : mapper.serializedMember(source.getClass(), fieldName);
/*     */ 
/* 185 */         ExtendedHierarchicalStreamWriterHelper.startNode(writer, nodeName, fieldType);
/*     */ 
/* 187 */         if (newObj != null)
/*     */         {
/* 205 */           Field defaultField = (Field)defaultFieldDefinition.get(fieldName);
/*     */ 
/* 207 */           if (defaultField.getDeclaringClass() != definedIn) {
/* 208 */             String attributeName = mapper.aliasForSystemAttribute("defined-in");
/*     */ 
/* 210 */             if (attributeName != null) {
/* 211 */               writer.addAttribute(attributeName, mapper.serializedClass(definedIn));
/*     */             }
/*     */           }
/*     */ 
/* 215 */           Field field = AbstractReflectionConverter.this.reflectionProvider.getField(definedIn, fieldName);
/*     */ 
/* 217 */           AbstractReflectionConverter.this.marshallField(context, newObj, field);
/*     */         }
/* 219 */         writer.endNode();
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   protected void marshallField(MarshallingContext context, Object newObj, Field field)
/*     */   {
/* 226 */     Mapper mapper = context.getMapper();
/* 227 */     context.convertAnother(newObj, mapper.getLocalConverter(field.getDeclaringClass(), field.getName()));
/*     */   }
/*     */ 
/*     */   public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
/*     */   {
/* 235 */     Object result = instantiateNewInstance(reader, context);
/* 236 */     result = doUnmarshal(result, reader, context);
/* 237 */     return this.serializationMethodInvoker.callReadResolve(result);
/*     */   }
/*     */ 
/*     */   public Object doUnmarshal(Object result, HierarchicalStreamReader reader, UnmarshallingContext context)
/*     */   {
/* 243 */     SeenFields seenFields = new SeenFields(null);
/* 244 */     Iterator it = reader.getAttributeNames();
/* 245 */     Mapper mapper = context.getMapper();
/*     */ 
/* 247 */     while (it.hasNext()) {
/* 248 */       String attrAlias = (String)it.next();
/* 249 */       String attrName = mapper.realMember(result.getClass(), mapper.attributeForAlias(attrAlias));
/*     */ 
/* 251 */       Class classDefiningField = determineWhichClassDefinesField(reader, mapper);
/*     */ 
/* 253 */       boolean fieldExistsInClass = this.reflectionProvider.fieldDefinedInClass(attrName, result.getClass());
/*     */ 
/* 255 */       if (fieldExistsInClass) {
/* 256 */         Field field = this.reflectionProvider.getField(result.getClass(), attrName);
/*     */ 
/* 258 */         if ((!Modifier.isTransient(field.getModifiers())) || (shouldUnmarshalTransientFields()))
/*     */         {
/* 262 */           SingleValueConverter converter = mapper.getConverterFromAttribute(field.getDeclaringClass(), attrName, field.getType());
/*     */ 
/* 265 */           Class type = field.getType();
/* 266 */           if (converter != null) {
/* 267 */             Object value = converter.fromString(reader.getAttribute(attrAlias));
/*     */ 
/* 269 */             if (type.isPrimitive()) {
/* 270 */               type = Primitives.box(type);
/*     */             }
/* 272 */             if ((value != null) && (!type.isAssignableFrom(value.getClass())))
/*     */             {
/* 274 */               throw new ConversionException("Cannot convert type " + value.getClass().getName() + " to type " + type.getName());
/*     */             }
/*     */ 
/* 278 */             this.reflectionProvider.writeField(result, attrName, value, classDefiningField);
/*     */ 
/* 280 */             seenFields.add(classDefiningField, attrName);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 284 */     Map implicitCollectionsForCurrentObject = null;
/* 285 */     while (reader.hasMoreChildren()) {
/* 286 */       reader.moveDown();
/* 287 */       String originalNodeName = reader.getNodeName();
/* 288 */       String fieldName = mapper.realMember(result.getClass(), originalNodeName);
/*     */ 
/* 290 */       Mapper.ImplicitCollectionMapping implicitCollectionMapping = mapper.getImplicitCollectionDefForFieldName(result.getClass(), fieldName);
/*     */ 
/* 293 */       Class classDefiningField = determineWhichClassDefinesField(reader, mapper);
/*     */ 
/* 295 */       boolean fieldExistsInClass = (implicitCollectionMapping == null) && (this.reflectionProvider.fieldDefinedInClass(fieldName, result.getClass()));
/*     */ 
/* 298 */       Class type = implicitCollectionMapping == null ? determineType(reader, fieldExistsInClass, result, fieldName, classDefiningField, context) : implicitCollectionMapping.getItemType();
/*     */ 
/* 303 */       if (fieldExistsInClass) {
/* 304 */         Field field = this.reflectionProvider.getField(classDefiningField != null ? classDefiningField : result.getClass(), fieldName);
/*     */ 
/* 307 */         if ((Modifier.isTransient(field.getModifiers())) && (!shouldUnmarshalTransientFields()))
/*     */         {
/* 309 */           reader.moveUp();
/*     */         }
/*     */         else {
/* 312 */           Object value = unmarshallField(context, result, type, field);
/*     */ 
/* 315 */           Class definedType = this.reflectionProvider.getFieldType(result, fieldName, classDefiningField);
/*     */ 
/* 317 */           if (!definedType.isPrimitive())
/* 318 */             type = definedType;
/*     */         }
/*     */       } else {
/* 321 */         Object value = type != null ? context.convertAnother(result, type) : null;
/*     */ 
/* 324 */         if ((value != null) && (!type.isAssignableFrom(value.getClass()))) {
/* 325 */           throw new ConversionException("Cannot convert type " + value.getClass().getName() + " to type " + type.getName());
/*     */         }
/*     */ 
/* 329 */         if (fieldExistsInClass) {
/* 330 */           this.reflectionProvider.writeField(result, fieldName, value, classDefiningField);
/*     */ 
/* 332 */           seenFields.add(classDefiningField, fieldName);
/* 333 */         } else if (type != null) {
/* 334 */           implicitCollectionsForCurrentObject = writeValueToImplicitCollection(context, value, implicitCollectionsForCurrentObject, result, originalNodeName);
/*     */         }
/*     */ 
/* 338 */         reader.moveUp();
/*     */       }
/*     */     }
/* 340 */     return result;
/*     */   }
/*     */ 
/*     */   protected Object unmarshallField(UnmarshallingContext context, Object result, Class type, Field field)
/*     */   {
/* 345 */     Mapper mapper = context.getMapper();
/* 346 */     return context.convertAnother(result, type, mapper.getLocalConverter(field.getDeclaringClass(), field.getName()));
/*     */   }
/*     */ 
/*     */   protected boolean shouldUnmarshalTransientFields()
/*     */   {
/* 354 */     return false;
/*     */   }
/*     */ 
/*     */   private Map writeValueToImplicitCollection(UnmarshallingContext context, Object value, Map _implicitCollections, Object result, String itemFieldName)
/*     */   {
/* 360 */     Mapper mapper = context.getMapper();
/* 361 */     Map implicitCollections = _implicitCollections;
/* 362 */     String fieldName = mapper.getFieldNameForItemTypeAndName(context.getRequiredType(), value != null ? value.getClass() : Mapper.Null.class, itemFieldName);
/*     */ 
/* 365 */     if (fieldName == null) {
/* 366 */       fieldName = mapper.getColFieldNameByPath(context.getPathTracker().getPath().toString());
/*     */     }
/*     */ 
/* 369 */     if (fieldName != null) {
/* 370 */       if (implicitCollections == null) {
/* 371 */         implicitCollections = new HashMap();
/*     */       }
/* 373 */       Collection collection = (Collection)implicitCollections.get(fieldName);
/*     */ 
/* 375 */       if (collection == null) {
/* 376 */         Class fieldType = mapper.defaultImplementationOf(this.reflectionProvider.getFieldType(result, fieldName, null));
/*     */ 
/* 379 */         if (!Collection.class.isAssignableFrom(fieldType)) {
/* 380 */           throw new ObjectAccessException("Field " + fieldName + " of " + result.getClass().getName() + " is configured for an implicit Collection, but field is of type " + fieldType.getName());
/*     */         }
/*     */ 
/* 388 */         if (this.pureJavaReflectionProvider == null) {
/* 389 */           this.pureJavaReflectionProvider = new PureJavaReflectionProvider();
/*     */         }
/* 391 */         collection = (Collection)this.pureJavaReflectionProvider.newInstance(fieldType);
/*     */ 
/* 393 */         this.reflectionProvider.writeField(result, fieldName, collection, null);
/*     */ 
/* 395 */         implicitCollections.put(fieldName, collection);
/*     */       }
/* 397 */       collection.add(value);
/*     */     } else {
/* 399 */       throw new ConversionException("Element " + itemFieldName + " of type " + value.getClass().getName() + " is not defined as field in type " + result.getClass().getName());
/*     */     }
/*     */ 
/* 404 */     return implicitCollections;
/*     */   }
/*     */ 
/*     */   private Class determineWhichClassDefinesField(HierarchicalStreamReader reader, Mapper mapper)
/*     */   {
/* 409 */     String attributeName = mapper.aliasForSystemAttribute("defined-in");
/* 410 */     String definedIn = attributeName == null ? null : reader.getAttribute(attributeName);
/*     */ 
/* 412 */     return definedIn == null ? null : mapper.realClass(definedIn);
/*     */   }
/*     */ 
/*     */   protected Object instantiateNewInstance(HierarchicalStreamReader reader, UnmarshallingContext context)
/*     */   {
/* 417 */     Mapper mapper = context.getMapper();
/* 418 */     String attributeName = mapper.aliasForSystemAttribute("resolves-to");
/* 419 */     String readResolveValue = attributeName == null ? null : reader.getAttribute(attributeName);
/*     */ 
/* 421 */     Object currentObject = context.currentObject();
/* 422 */     if (currentObject != null)
/* 423 */       return currentObject;
/* 424 */     if (readResolveValue != null) {
/* 425 */       return this.reflectionProvider.newInstance(mapper.realClass(readResolveValue));
/*     */     }
/*     */ 
/* 428 */     return this.reflectionProvider.newInstance(context.getRequiredType());
/*     */   }
/*     */ 
/*     */   private Class determineType(HierarchicalStreamReader reader, boolean validField, Object result, String fieldName, Class _definedInCls, UnmarshallingContext context)
/*     */   {
/* 452 */     Mapper mapper = context.getMapper();
/* 453 */     Class definedInCls = _definedInCls;
/* 454 */     String classAttribute = HierarchicalStreams.readClassAttribute(reader, mapper);
/*     */ 
/* 456 */     if (classAttribute != null)
/* 457 */       return mapper.realClass(classAttribute);
/* 458 */     if (!validField) {
/* 459 */       Class itemType = mapper.getItemTypeForItemFieldName(result.getClass(), fieldName);
/*     */ 
/* 461 */       if (itemType != null) {
/* 462 */         return itemType;
/*     */       }
/* 464 */       itemType = HierarchicalStreams.readClassByXPath(mapper, context);
/*     */ 
/* 466 */       if (itemType != null)
/* 467 */         return itemType;
/* 468 */       String originalNodeName = reader.getNodeName();
/* 469 */       if (definedInCls == null) {
/* 470 */         for (definedInCls = result.getClass(); definedInCls != null; definedInCls = definedInCls.getSuperclass())
/*     */         {
/* 472 */           if (!mapper.shouldSerializeMember(definedInCls, originalNodeName))
/*     */           {
/* 474 */             return null;
/*     */           }
/*     */         }
/*     */       }
/* 478 */       return HierarchicalStreams.readClassType(originalNodeName, reader, mapper);
/*     */     }
/*     */ 
/* 482 */     return mapper.defaultImplementationOf(this.reflectionProvider.getFieldType(result, fieldName, definedInCls));
/*     */   }
/*     */ 
/*     */   private Object readResolve()
/*     */   {
/* 488 */     this.serializationMethodInvoker = new SerializationMethodInvoker();
/* 489 */     return this;
/*     */   }
/*     */ 
/*     */   public static class DuplicateFieldException extends ConversionException
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */     public DuplicateFieldException(String msg) {
/* 497 */       super();
/* 498 */       add("duplicate-field", msg);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class SeenFields
/*     */   {
/* 434 */     private Set seen = new HashSet();
/*     */ 
/*     */     public void add(Class definedInCls, String fieldName) {
/* 437 */       String uniqueKey = fieldName;
/* 438 */       if (definedInCls != null) {
/* 439 */         uniqueKey = uniqueKey + " [" + definedInCls.getName() + "]";
/*     */       }
/* 441 */       if (this.seen.contains(uniqueKey)) {
/* 442 */         throw new AbstractReflectionConverter.DuplicateFieldException(uniqueKey);
/*     */       }
/* 444 */       this.seen.add(uniqueKey);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.reflection.AbstractReflectionConverter
 * JD-Core Version:    0.6.2
 */