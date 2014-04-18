/*    */ package com.ztesoft.inf.extend.xstream;
/*    */ 
/*    */ public class XStreamException extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Throwable cause;
/*    */ 
/*    */   protected XStreamException()
/*    */   {
/* 35 */     this("", null);
/*    */   }
/*    */ 
/*    */   public XStreamException(String message)
/*    */   {
/* 45 */     this(message, null);
/*    */   }
/*    */ 
/*    */   public XStreamException(Throwable cause)
/*    */   {
/* 56 */     this("", cause);
/*    */   }
/*    */ 
/*    */   public XStreamException(String message, Throwable cause)
/*    */   {
/* 68 */     super(message + (cause == null ? "" : new StringBuilder().append(" : ").append(cause.getMessage()).toString()));
/* 69 */     this.cause = cause;
/*    */   }
/*    */ 
/*    */   public Throwable getCause()
/*    */   {
/* 74 */     return this.cause;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.XStreamException
 * JD-Core Version:    0.6.2
 */