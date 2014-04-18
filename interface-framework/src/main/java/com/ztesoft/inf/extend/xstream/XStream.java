/*     */ package com.ztesoft.inf.extend.xstream;
/*     */ 
/*     */ import com.ztesoft.inf.extend.xstream.converters.Converter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.ConverterLookup;
/*     */ import com.ztesoft.inf.extend.xstream.converters.ConverterRegistry;
/*     */ import com.ztesoft.inf.extend.xstream.converters.SingleValueConverter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.SingleValueConverterWrapper;
/*     */ import com.ztesoft.inf.extend.xstream.converters.basic.BigDecimalConverter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.basic.BigIntegerConverter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.basic.BooleanConverter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.basic.ByteConverter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.basic.CharConverter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.basic.DateConverter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.basic.DoubleConverter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.basic.FloatConverter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.basic.IntConverter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.basic.LongConverter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.basic.NullConverter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.basic.ShortConverter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.basic.StringBufferConverter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.basic.StringConverter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.collections.ArrayConverter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.collections.CharArrayConverter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.collections.CollectionConverter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.collections.MapConverter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.extended.JavaClassConverter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.extended.JavaMethodConverter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.extended.LocaleConverter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.extended.SqlDateConverter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.extended.SqlTimeConverter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.extended.SqlTimestampConverter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.reflection.ReflectionConverter;
/*     */ import com.ztesoft.inf.extend.xstream.converters.reflection.ReflectionProvider;
/*     */ import com.ztesoft.inf.extend.xstream.core.DefaultConverterLookup;
/*     */ import com.ztesoft.inf.extend.xstream.core.JVM;
/*     */ import com.ztesoft.inf.extend.xstream.core.TreeMarshallingStrategy;
/*     */ import com.ztesoft.inf.extend.xstream.core.util.ClassLoaderReference;
/*     */ import com.ztesoft.inf.extend.xstream.core.util.CompositeClassLoader;
/*     */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamDriver;
/*     */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamReader;
/*     */ import com.ztesoft.inf.extend.xstream.io.HierarchicalStreamWriter;
/*     */ import com.ztesoft.inf.extend.xstream.io.xml.DocumentWriter;
/*     */ import com.ztesoft.inf.extend.xstream.io.xml.Dom4JDriver;
/*     */ import com.ztesoft.inf.extend.xstream.mapper.Mapper;
/*     */ import com.ztesoft.inf.extend.xstream.mapper.MapperContext;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.io.StringWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.List;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.Element;
/*     */ 
/*     */ public class XStream
/*     */ {
/*     */   private static XStream instance;
/*     */   private ReflectionProvider reflectionProvider;
/*     */   private HierarchicalStreamDriver hierarchicalStreamDriver;
/*     */   private ClassLoaderReference classLoaderReference;
/*     */   private MarshallingStrategy marshallingStrategy;
/*     */   private ConverterLookup converterLookup;
/*     */   private ConverterRegistry converterRegistry;
/*  67 */   private transient JVM jvm = new JVM();
/*     */   public static final int PRIORITY_VERY_HIGH = 10000;
/*     */   public static final int PRIORITY_NORMAL = 0;
/*     */   public static final int PRIORITY_LOW = -10;
/*     */   public static final int PRIORITY_VERY_LOW = -20;
/*     */ 
/*     */   public static synchronized XStream instance()
/*     */   {
/*  74 */     if (instance == null) {
/*  75 */       instance = new XStream();
/*     */     }
/*  77 */     return instance;
/*     */   }
/*     */ 
/*     */   public XStream() {
/*  81 */     this(null, (Mapper)null, new Dom4JDriver());
/*     */   }
/*     */ 
/*     */   public XStream(ReflectionProvider reflectionProvider) {
/*  85 */     this(reflectionProvider, (Mapper)null, new Dom4JDriver());
/*     */   }
/*     */ 
/*     */   public XStream(HierarchicalStreamDriver hierarchicalStreamDriver) {
/*  89 */     this(null, (Mapper)null, hierarchicalStreamDriver);
/*     */   }
/*     */ 
/*     */   public XStream(ReflectionProvider reflectionProvider, HierarchicalStreamDriver hierarchicalStreamDriver)
/*     */   {
/*  94 */     this(reflectionProvider, (Mapper)null, hierarchicalStreamDriver);
/*     */   }
/*     */ 
/*     */   public XStream(ReflectionProvider reflectionProvider, Mapper mapper, HierarchicalStreamDriver hierarchicalStreamDriver)
/*     */   {
/*  99 */     this(reflectionProvider, hierarchicalStreamDriver, new ClassLoaderReference(new CompositeClassLoader()), mapper);
/*     */   }
/*     */ 
/*     */   public XStream(ReflectionProvider reflectionProvider, HierarchicalStreamDriver driver, ClassLoader classLoader)
/*     */   {
/* 105 */     this(reflectionProvider, driver, classLoader, null);
/*     */   }
/*     */ 
/*     */   public XStream(ReflectionProvider reflectionProvider, HierarchicalStreamDriver driver, ClassLoader classLoader, Mapper mapper)
/*     */   {
/* 111 */     this(reflectionProvider, driver, classLoader, mapper, new DefaultConverterLookup(), null);
/*     */   }
/*     */ 
/*     */   public XStream(ReflectionProvider reflectionProvider, HierarchicalStreamDriver driver, ClassLoader classLoader, Mapper mapper, ConverterLookup converterLookup, ConverterRegistry converterRegistry)
/*     */   {
/* 119 */     this.jvm = new JVM();
/* 120 */     if (reflectionProvider == null)
/* 121 */       this.reflectionProvider = this.jvm.bestReflectionProvider();
/*     */     else
/* 123 */       this.reflectionProvider = reflectionProvider;
/* 124 */     this.hierarchicalStreamDriver = driver;
/* 125 */     this.classLoaderReference = ((classLoader instanceof ClassLoaderReference) ? (ClassLoaderReference)classLoader : new ClassLoaderReference(classLoader));
/*     */ 
/* 127 */     this.converterLookup = converterLookup;
/* 128 */     this.converterRegistry = ((converterLookup instanceof ConverterRegistry) ? (ConverterRegistry)converterLookup : converterRegistry != null ? converterRegistry : null);
/*     */ 
/* 131 */     setupConverters();
/* 132 */     setMarshallingStrategy(new TreeMarshallingStrategy());
/*     */   }
/*     */ 
/*     */   protected boolean useXStream11XmlFriendlyMapper() {
/* 136 */     return false;
/*     */   }
/*     */ 
/*     */   protected void setupConverters() {
/* 140 */     ReflectionConverter reflectionConverter = new ReflectionConverter(this.reflectionProvider);
/*     */ 
/* 142 */     registerConverter(reflectionConverter, -20);
/* 143 */     registerConverter(new NullConverter(), 10000);
/* 144 */     registerConverter(new IntConverter(), 0);
/* 145 */     registerConverter(new FloatConverter(), 0);
/* 146 */     registerConverter(new DoubleConverter(), 0);
/* 147 */     registerConverter(new LongConverter(), 0);
/* 148 */     registerConverter(new ShortConverter(), 0);
/* 149 */     registerConverter(new CharConverter(), 0);
/* 150 */     registerConverter(new BooleanConverter(), 0);
/* 151 */     registerConverter(new ByteConverter(), 0);
/* 152 */     registerConverter(new StringConverter(), 0);
/* 153 */     registerConverter(new StringBufferConverter(), 0);
/* 154 */     registerConverter(new DateConverter(), 0);
/* 155 */     registerConverter(new BigIntegerConverter(), 0);
/* 156 */     registerConverter(new BigDecimalConverter(), 0);
/* 157 */     registerConverter(new ArrayConverter(), 0);
/* 158 */     registerConverter(new CharArrayConverter(), 0);
/* 159 */     registerConverter(new CollectionConverter(), 0);
/* 160 */     registerConverter(new MapConverter(), 0);
/* 161 */     if (this.jvm.supportsSQL()) {
/* 162 */       registerConverter(new SqlTimestampConverter(), 0);
/* 163 */       registerConverter(new SqlTimeConverter(), 0);
/* 164 */       registerConverter(new SqlDateConverter(), 0);
/*     */     }
/* 166 */     registerConverter(new JavaClassConverter(this.classLoaderReference), 0);
/*     */ 
/* 168 */     registerConverter(new JavaMethodConverter(this.classLoaderReference), 0);
/*     */ 
/* 170 */     registerConverter(new LocaleConverter(), 0);
/*     */   }
/*     */ 
/*     */   public void setMarshallingStrategy(MarshallingStrategy marshallingStrategy) {
/* 174 */     this.marshallingStrategy = marshallingStrategy;
/*     */   }
/*     */ 
/*     */   public String toXML(Object obj, MapperContext mapperCtx) {
/* 178 */     if (mapperCtx == null)
/* 179 */       mapperCtx = new MapperContext();
/* 180 */     Writer writer = new StringWriter();
/* 181 */     toXML(obj, writer, mapperCtx);
/* 182 */     return writer.toString();
/*     */   }
/*     */ 
/*     */   public Document toXMLDocument(Object obj, MapperContext mapperCtx) {
/* 186 */     DocumentWriter writer = this.hierarchicalStreamDriver.createDoumentWriter();
/*     */     try {
/* 188 */       marshal(obj, writer, mapperCtx);
/* 189 */       List tops = writer.getTopLevelNodes();
/* 190 */       return (Document)tops.get(0);
/*     */     } finally {
/* 192 */       writer.flush();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void toXML(Object obj, Writer out, MapperContext mapperCtx) {
/* 197 */     HierarchicalStreamWriter writer = this.hierarchicalStreamDriver.createWriter(out);
/*     */     try
/*     */     {
/* 200 */       marshal(obj, writer, mapperCtx);
/*     */     } finally {
/* 202 */       writer.flush();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void toXML(Object obj, OutputStream out) {
/* 207 */     HierarchicalStreamWriter writer = this.hierarchicalStreamDriver.createWriter(out);
/*     */     try
/*     */     {
/* 210 */       marshal(obj, writer);
/*     */     } finally {
/* 212 */       writer.flush();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void marshal(Object obj, HierarchicalStreamWriter writer) {
/* 217 */     marshal(obj, writer, new MapperContext());
/*     */   }
/*     */ 
/*     */   public void marshal(Object obj, HierarchicalStreamWriter writer, MapperContext mapCtx)
/*     */   {
/* 222 */     mapCtx.setClassLoader(this.classLoaderReference);
/* 223 */     mapCtx.setConverterLookup(this.converterLookup);
/* 224 */     this.marshallingStrategy.marshal(writer, obj, this.converterLookup, mapCtx);
/*     */   }
/*     */ 
/*     */   public Object fromXML(String xml, MapperContext mapperCtx) {
/* 228 */     return fromXML(new StringReader(xml), mapperCtx);
/*     */   }
/*     */ 
/*     */   public Object fromXML(Reader xml, MapperContext mapperCtx) {
/* 232 */     return unmarshal(this.hierarchicalStreamDriver.createReader(xml), null, mapperCtx);
/*     */   }
/*     */ 
/*     */   public Object fromXML(InputStream input, MapperContext mapperCtx)
/*     */   {
/* 237 */     return unmarshal(this.hierarchicalStreamDriver.createReader(input), null, mapperCtx);
/*     */   }
/*     */ 
/*     */   public Object fromXML(String xml, Object root, MapperContext mapperCtx)
/*     */   {
/* 242 */     return fromXML(new StringReader(xml), root, mapperCtx);
/*     */   }
/*     */ 
/*     */   public Object fromXML(Reader xml, Object root, MapperContext mapperCtx) {
/* 246 */     return unmarshal(this.hierarchicalStreamDriver.createReader(xml), root, mapperCtx);
/*     */   }
/*     */ 
/*     */   public Object fromXML(InputStream xml, Object root)
/*     */   {
/* 251 */     return unmarshal(this.hierarchicalStreamDriver.createReader(xml), root);
/*     */   }
/*     */ 
/*     */   public Object fromElement(Element element, MapperContext mapperCtx) {
/* 255 */     return unmarshal(this.hierarchicalStreamDriver.createReader(element), null, mapperCtx);
/*     */   }
/*     */ 
/*     */   public Object unmarshal(HierarchicalStreamReader reader)
/*     */   {
/* 260 */     return unmarshal(reader, null, null);
/*     */   }
/*     */ 
/*     */   public Object unmarshal(HierarchicalStreamReader reader, Object root) {
/* 264 */     return unmarshal(reader, root, new MapperContext());
/*     */   }
/*     */ 
/*     */   public Object unmarshal(HierarchicalStreamReader reader, Object root, MapperContext mapperCtx)
/*     */   {
/* 269 */     mapperCtx.setClassLoader(this.classLoaderReference);
/* 270 */     mapperCtx.setConverterLookup(this.converterLookup);
/* 271 */     return this.marshallingStrategy.unmarshal(root, reader, this.converterLookup, mapperCtx);
/*     */   }
/*     */ 
/*     */   public void registerConverter(Converter converter)
/*     */   {
/* 276 */     registerConverter(converter, 0);
/*     */   }
/*     */ 
/*     */   public void registerConverter(Converter converter, int priority) {
/* 280 */     if (this.converterRegistry != null)
/* 281 */       this.converterRegistry.registerConverter(converter, priority);
/*     */   }
/*     */ 
/*     */   public void registerConverter(SingleValueConverter converter)
/*     */   {
/* 286 */     registerConverter(converter, 0);
/*     */   }
/*     */ 
/*     */   public void registerConverter(SingleValueConverter converter, int priority) {
/* 290 */     if (this.converterRegistry != null)
/* 291 */       this.converterRegistry.registerConverter(new SingleValueConverterWrapper(converter), priority);
/*     */   }
/*     */ 
/*     */   public ReflectionProvider getReflectionProvider()
/*     */   {
/* 297 */     return this.reflectionProvider;
/*     */   }
/*     */ 
/*     */   public ConverterLookup getConverterLookup() {
/* 301 */     return this.converterLookup;
/*     */   }
/*     */ 
/*     */   public void setClassLoader(ClassLoader classLoader) {
/* 305 */     this.classLoaderReference.setReference(classLoader);
/*     */   }
/*     */ 
/*     */   public ClassLoader getClassLoader() {
/* 309 */     return this.classLoaderReference.getReference();
/*     */   }
/*     */ 
/*     */   private Object readResolve() {
/* 313 */     this.jvm = new JVM();
/* 314 */     return this;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.XStream
 * JD-Core Version:    0.6.2
 */