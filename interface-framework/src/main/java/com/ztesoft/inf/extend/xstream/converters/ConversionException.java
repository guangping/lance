/*    */ package com.ztesoft.inf.extend.xstream.converters;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.XStreamException;
/*    */ import com.ztesoft.inf.extend.xstream.core.util.OrderRetainingMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class ConversionException extends XStreamException
/*    */   implements ErrorWriter
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private static final String SEPARATOR = "\n-------------------------------";
/* 40 */   private Map stuff = new OrderRetainingMap();
/*    */ 
/*    */   public ConversionException(String msg, Throwable cause) {
/* 43 */     super(msg, cause);
/* 44 */     if (msg != null) {
/* 45 */       add("message", msg);
/*    */     }
/* 47 */     if (cause != null) {
/* 48 */       add("cause-exception", cause.getClass().getName());
/* 49 */       add("cause-message", (cause instanceof ConversionException) ? ((ConversionException)cause).getShortMessage() : cause.getMessage());
/*    */     }
/*    */   }
/*    */ 
/*    */   public ConversionException(String msg)
/*    */   {
/* 56 */     super(msg);
/*    */   }
/*    */ 
/*    */   public ConversionException(Throwable cause) {
/* 60 */     this(cause.getMessage(), cause);
/*    */   }
/*    */ 
/*    */   public String get(String errorKey) {
/* 64 */     return (String)this.stuff.get(errorKey);
/*    */   }
/*    */ 
/*    */   public void add(String name, String information) {
/* 68 */     this.stuff.put(name, information);
/*    */   }
/*    */ 
/*    */   public Iterator keys() {
/* 72 */     return this.stuff.keySet().iterator();
/*    */   }
/*    */ 
/*    */   public String getMessage()
/*    */   {
/* 77 */     StringBuffer result = new StringBuffer();
/* 78 */     if (super.getMessage() != null) {
/* 79 */       result.append(super.getMessage());
/*    */     }
/* 81 */     if (!result.toString().endsWith("\n-------------------------------")) {
/* 82 */       result.append("\n---- Debugging information ----");
/*    */     }
/* 84 */     for (Iterator iterator = keys(); iterator.hasNext(); ) {
/* 85 */       String k = (String)iterator.next();
/* 86 */       String v = get(k);
/* 87 */       result.append('\n').append(k);
/* 88 */       result.append("                    ".substring(Math.min(20, k.length())));
/*    */ 
/* 90 */       result.append(": ").append(v);
/*    */     }
/* 92 */     result.append("\n-------------------------------");
/* 93 */     return result.toString();
/*    */   }
/*    */ 
/*    */   public String getShortMessage() {
/* 97 */     return super.getMessage();
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.converters.ConversionException
 * JD-Core Version:    0.6.2
 */