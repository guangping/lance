/*     */ package com.ztesoft.inf.operation;
/*     */ 
/*     */ import com.ztesoft.inf.extend.xstream.XStream;
/*     */ import com.ztesoft.inf.extend.xstream.mapper.MapperContext;
/*     */ import com.ztesoft.inf.extend.xstream.mapper.MapperContextBuilder;
/*     */ import com.ztesoft.inf.framework.cache.Cache;
/*     */ import com.ztesoft.inf.framework.cache.CacheItemCreateCallback;
/*     */ import com.ztesoft.inf.framework.cache.CacheManager;
/*     */ import com.ztesoft.inf.operation.bo.OperationBO;
/*     */ import com.ztesoft.inf.operation.vo.Operation;
/*     */ import com.ztesoft.inf.operation.vo.OperationArg;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.List;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.Element;
/*     */ 
/*     */ public class OperationInvoker
/*     */ {
/*  22 */   private MapperContextBuilder mapperCtxBuilder = new MapperContextBuilder();
/*  23 */   private OperationBO operationBo = new OperationBO();
/*  24 */   private Cache operationCache = CacheManager.getCache("OPERATION");
/*     */ 
/*  26 */   private XStream xstream = XStream.instance();
/*     */ 
/*     */   public Document invoke(String operationCode, Document inXml) throws Exception
/*     */   {
/*  30 */     OperationInternal operationIntenal = getOperationConfig(operationCode);
/*  31 */     return invoke(operationIntenal, inXml);
/*     */   }
/*     */ 
/*     */   protected Document invoke(OperationInternal operationIntenal, Document doc) throws Exception
/*     */   {
/*  36 */     Object[] arguments = unmarshallArgument(operationIntenal, doc);
/*  37 */     Method method = operationIntenal.getMethod();
/*  38 */     Object result = null;
/*  39 */     Document resultXml = null;
/*     */     try {
/*  41 */       result = method.invoke(operationIntenal.getTarget(), arguments);
/*  42 */       if (result != null);
/*  43 */       return this.xstream.toXMLDocument(result, operationIntenal.getResultMapperCtx());
/*     */     }
/*     */     catch (IllegalArgumentException e)
/*     */     {
/*  48 */       throw e;
/*     */     } catch (IllegalAccessException e) {
/*  50 */       throw e;
/*     */     } catch (InvocationTargetException e) {
/*  52 */       throw ((Exception)e.getTargetException());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Object[] unmarshallArgument(OperationInternal operationIntenal, Document doc) throws Exception
/*     */   {
/*  58 */     List argContexts = operationIntenal.getInArgMapperContext();
/*  59 */     List argXpaths = operationIntenal.getInArgXpaths();
/*  60 */     Object[] arguments = new Object[argContexts.size()];
/*  61 */     Object argument = null;
/*     */ 
/*  63 */     Element element = null;
/*     */ 
/*  65 */     for (int i = 0; i < argContexts.size(); i++) {
/*  66 */       MapperContext argContext = (MapperContext)argContexts.get(i);
/*  67 */       String xpath = (String)argXpaths.get(i);
/*  68 */       element = (Element)doc.selectSingleNode(xpath);
/*  69 */       argument = null;
/*  70 */       if (element != null)
/*  71 */         argument = this.xstream.fromElement(element, argContext);
/*  72 */       arguments[i] = argument;
/*     */     }
/*  74 */     return arguments;
/*     */   }
/*     */ 
/*     */   private OperationInternal getOperationConfig(final String operationCode) throws Exception
/*     */   {
/*  79 */     OperationInternal operationIntenal = (OperationInternal)this.operationCache.get(operationCode, new CacheItemCreateCallback()
/*     */     {
/*     */       public Object create() throws Exception {
/*  82 */         Operation operation = OperationInvoker.this.operationBo.getOperationByCode(operationCode);
/*     */ 
/*  84 */         return OperationInvoker.this.resolveOperation(operation);
/*     */       }
/*     */     });
/*  87 */     return operationIntenal;
/*     */   }
/*     */ 
/*     */   private OperationInternal resolveOperation(Operation operation) {
/*  91 */     OperationInternal operationIntenal = new OperationInternal();
/*  92 */     List args = operation.getOperationInArgs();
/*     */ 
/*  95 */     Class[] argTypes = new Class[args.size()];
/*  96 */     for (int i = 0; i < args.size(); i++) {
/*  97 */       OperationArg arg = (OperationArg)args.get(i);
/*  98 */       MapperContext ctx = this.mapperCtxBuilder.build(arg.getXmlMapper());
/*  99 */       operationIntenal.addArgMapperContext(ctx);
/* 100 */       argTypes[i] = ctx.getRootType();
/* 101 */       operationIntenal.addArgXpath(arg.getXpath()); } OperationArg arg = operation.getResultArg();
/* 104 */     MapperContext ctx = this.mapperCtxBuilder.build(arg.getXmlMapper());
/* 105 */     operationIntenal.setResultMapperContext(ctx);
/* 106 */     String className = operation.getClassName();
/*     */     Class clazz;
/*     */     try { ClassLoader loader = Thread.currentThread().getContextClassLoader();
/* 110 */       clazz = loader.loadClass(className);
/* 111 */       operationIntenal.setTarget(clazz.newInstance());
/*     */     } catch (Exception e) {
/* 113 */       throw new RuntimeException("", e);
/*     */     }
/*     */     try {
/* 116 */       Method method = clazz.getMethod(operation.getMethodName(), argTypes);
/*     */ 
/* 118 */       operationIntenal.setMethod(method);
/*     */     } catch (Exception e) {
/* 120 */       throw new RuntimeException("", e);
/*     */     }
/* 122 */     return operationIntenal;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.operation.OperationInvoker
 * JD-Core Version:    0.6.2
 */