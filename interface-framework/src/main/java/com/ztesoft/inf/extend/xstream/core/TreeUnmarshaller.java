/*     */ package com.ztesoft.inf.extend.xstream.core;
/*     */ 
/*     */ import com.ztesoft.inf.extend.xstream.converters.ConversionException;
/*     */ import com.ztesoft.inf.extend.xstream.converters.Converter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.ConverterLookup;
/*     */ import com.ztesoft.inf.extend.xstream.converters.ErrorWriter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.UnmarshallingContext;
/*     */ import com.ztesoft.inf.extend.xstream.core.util.FastStack;
/*     */ import com.ztesoft.inf.extend.xstream.core.util.HierarchicalStreams;
/*     */ import com.ztesoft.inf.extend.xstream.core.util.PrioritizedList;
/*     */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
/*     */ import com.ztesoft.inf.extend.xstream.io.path.PathTracker;
/*     */ import com.ztesoft.inf.extend.xstream.io.path.PathTrackingReader;
/*     */ import com.ztesoft.inf.extend.xstream.mapper.Mapper;
/*     */ import com.ztesoft.inf.extend.xstream.mapper.MapperContext;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class TreeUnmarshaller
/*     */   implements UnmarshallingContext
/*     */ {
/*     */   private Object root;
/*     */   protected HierarchicalStreamReader reader;
/*     */   private ConverterLookup converterLookup;
/*     */   private Mapper mapper;
/*     */   private MapperContext mapperCtx;
/*  26 */   private FastStack types = new FastStack(16);
/*  27 */   private final PrioritizedList validationList = new PrioritizedList();
/*  28 */   protected PathTracker pathTracker = new PathTracker();
/*     */ 
/*     */   public TreeUnmarshaller(Object root, HierarchicalStreamReader reader, ConverterLookup converterLookup, MapperContext mapperCtx)
/*     */   {
/*  32 */     this.root = root;
/*  33 */     this.reader = new PathTrackingReader(reader, this.pathTracker);
/*  34 */     this.converterLookup = converterLookup;
/*  35 */     this.mapperCtx = mapperCtx;
/*  36 */     this.mapper = mapperCtx.getMapper();
/*     */   }
/*     */ 
/*     */   public Object convertAnother(Object parent, Class type) {
/*  40 */     return convertAnother(parent, type, null);
/*     */   }
/*     */ 
/*     */   public Object convertAnother(Object parent, Class _type, Converter _converter)
/*     */   {
/*  45 */     Class type = _type;
/*  46 */     Converter converter = _converter;
/*  47 */     type = this.mapper.defaultImplementationOf(type);
/*  48 */     if (converter == null) {
/*  49 */       converter = this.converterLookup.lookupConverterForType(type);
/*     */     }
/*  51 */     else if (!converter.canConvert(type)) {
/*  52 */       ConversionException e = new ConversionException("Explicit selected converter cannot handle type");
/*     */ 
/*  54 */       e.add("item-type", type.getName());
/*  55 */       e.add("converter-type", converter.getClass().getName());
/*  56 */       throw e;
/*     */     }
/*     */ 
/*  59 */     return convert(parent, type, converter);
/*     */   }
/*     */ 
/*     */   protected Object convert(Object parent, Class type, Converter converter) {
/*     */     try {
/*  64 */       this.types.push(type);
/*  65 */       Object result = converter.unmarshal(this.reader, this);
/*  66 */       this.types.popSilently();
/*  67 */       return result;
/*     */     } catch (ConversionException conversionException) {
/*  69 */       addInformationTo(conversionException, type);
/*  70 */       throw conversionException;
/*     */     } catch (RuntimeException e) {
/*  72 */       ConversionException conversionException = new ConversionException(e);
/*  73 */       addInformationTo(conversionException, type);
/*  74 */       throw conversionException;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addInformationTo(ErrorWriter errorWriter, Class type) {
/*  79 */     errorWriter.add("class", type.getName());
/*  80 */     errorWriter.add("required-type", getRequiredType().getName());
/*  81 */     this.reader.appendErrors(errorWriter);
/*     */   }
/*     */ 
/*     */   public void addCompletionCallback(Runnable work, int priority) {
/*  85 */     this.validationList.add(work, priority);
/*     */   }
/*     */ 
/*     */   public Object currentObject() {
/*  89 */     return this.types.size() == 1 ? this.root : null;
/*     */   }
/*     */ 
/*     */   public Class getRequiredType() {
/*  93 */     return (Class)this.types.peek();
/*     */   }
/*     */ 
/*     */   public Object get(Object key) {
/*  97 */     return this.mapperCtx.get(key);
/*     */   }
/*     */ 
/*     */   public void put(Object key, Object value) {
/* 101 */     this.mapperCtx.put(key, value);
/*     */   }
/*     */ 
/*     */   public Iterator keys() {
/* 105 */     return this.mapperCtx.keys();
/*     */   }
/*     */ 
/*     */   public Object start() {
/* 109 */     Class type = HierarchicalStreams.readClassType(this.reader, this.mapper, this, this.mapper.defaultImplementationOf(this.mapperCtx.getRootType()));
/*     */ 
/* 111 */     Object result = convertAnother(null, type);
/* 112 */     Iterator validations = this.validationList.iterator();
/* 113 */     while (validations.hasNext()) {
/* 114 */       Runnable runnable = (Runnable)validations.next();
/* 115 */       runnable.run();
/*     */     }
/* 117 */     return result;
/*     */   }
/*     */ 
/*     */   public Mapper getMapper() {
/* 121 */     return this.mapper;
/*     */   }
/*     */ 
/*     */   public PathTracker getPathTracker() {
/* 125 */     return this.pathTracker;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.core.TreeUnmarshaller
 * JD-Core Version:    0.6.2
 */