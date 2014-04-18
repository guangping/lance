/*     */ package com.ztesoft.inf.extend.xstream.core;
/*     */ 
/*     */ import com.ztesoft.inf.extend.xstream.XStreamException;
/*     */ import com.ztesoft.inf.extend.xstream.converters.ConversionException;
/*     */ import com.ztesoft.inf.extend.xstream.converters.Converter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.ConverterLookup;
/*     */ import com.ztesoft.inf.extend.xstream.converters.MarshallingContext;
/*     */ import com.ztesoft.inf.extend.xstream.core.util.ObjectIdDictionary;
/*     */ import com.ztesoft.inf.extend.xstream.io.ExtendedHierarchicalStreamWriterHelper;
/*     */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;
/*     */ import com.ztesoft.inf.extend.xstream.io.path.PathTracker;
/*     */ import com.ztesoft.inf.extend.xstream.io.path.PathTrackingWriter;
/*     */ import com.ztesoft.inf.extend.xstream.mapper.Mapper;
/*     */ import com.ztesoft.inf.extend.xstream.mapper.MapperContext;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class TreeMarshaller
/*     */   implements MarshallingContext
/*     */ {
/*     */   protected HierarchicalStreamWriter writer;
/*     */   protected ConverterLookup converterLookup;
/*     */   private Mapper mapper;
/*     */   private MapperContext mapperCtx;
/*  37 */   private ObjectIdDictionary parentObjects = new ObjectIdDictionary();
/*  38 */   protected PathTracker pathTracker = new PathTracker();
/*     */ 
/*     */   public TreeMarshaller(HierarchicalStreamWriter writer, ConverterLookup converterLookup, MapperContext mapperCtx)
/*     */   {
/*  42 */     this.writer = new PathTrackingWriter(writer, this.pathTracker);
/*  43 */     this.converterLookup = converterLookup;
/*  44 */     this.mapperCtx = mapperCtx;
/*  45 */     this.mapper = mapperCtx.getMapper();
/*     */   }
/*     */ 
/*     */   public void convertAnother(Object item) {
/*  49 */     convertAnother(item, null);
/*     */   }
/*     */ 
/*     */   public void convertAnother(Object item, Converter _converter) {
/*  53 */     Converter converter = _converter;
/*  54 */     if (converter == null) {
/*  55 */       converter = this.converterLookup.lookupConverterForType(item.getClass());
/*     */     }
/*  57 */     else if (!converter.canConvert(item.getClass())) {
/*  58 */       ConversionException e = new ConversionException("Explicit selected converter cannot handle item");
/*     */ 
/*  60 */       e.add("item-type", item.getClass().getName());
/*  61 */       e.add("converter-type", converter.getClass().getName());
/*  62 */       throw e;
/*     */     }
/*     */ 
/*  65 */     convert(item, converter);
/*     */   }
/*     */ 
/*     */   protected void convert(Object item, Converter converter)
/*     */   {
/*  70 */     if (this.parentObjects.containsId(item)) {
/*  71 */       throw new CircularReferenceException();
/*     */     }
/*  73 */     this.parentObjects.associateId(item, "");
/*  74 */     converter.marshal(item, this.writer, this);
/*  75 */     this.parentObjects.removeId(item);
/*     */   }
/*     */ 
/*     */   public void start(Object item) {
/*  79 */     if (item == null) {
/*  80 */       this.writer.startNode(this.mapper.serializedClass(null));
/*  81 */       this.writer.endNode();
/*     */     } else {
/*  83 */       String nodeName = null;
/*  84 */       nodeName = (String)get("_RootName_");
/*  85 */       if ((((item instanceof Map)) || ((item instanceof Collection))) && 
/*  86 */         (nodeName == null)) {
/*  87 */         throw new XStreamException("root object is a Map or Collection,must set rootName!");
/*     */       }
/*     */ 
/*  91 */       if ((nodeName == null) || (nodeName.trim().length() < 1)) {
/*  92 */         nodeName = this.mapper.serializedClass(item.getClass());
/*     */       }
/*  94 */       ExtendedHierarchicalStreamWriterHelper.startNode(this.writer, nodeName, item.getClass());
/*     */ 
/*  96 */       convertAnother(item);
/*  97 */       this.writer.endNode();
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object get(Object key) {
/* 102 */     return this.mapperCtx.get(key);
/*     */   }
/*     */ 
/*     */   public void put(Object key, Object value) {
/* 106 */     this.mapperCtx.put(key, value);
/*     */   }
/*     */ 
/*     */   public Iterator keys() {
/* 110 */     return this.mapperCtx.keys();
/*     */   }
/*     */ 
/*     */   public Mapper getMapper() {
/* 114 */     return this.mapper;
/*     */   }
/*     */ 
/*     */   public PathTracker getPathTracker()
/*     */   {
/* 123 */     return this.pathTracker;
/*     */   }
/*     */ 
/*     */   public static class CircularReferenceException extends XStreamException
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.core.TreeMarshaller
 * JD-Core Version:    0.6.2
 */