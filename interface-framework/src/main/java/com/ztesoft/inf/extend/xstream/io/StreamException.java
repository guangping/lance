/*    */ package com.ztesoft.inf.extend.xstream.io;
/*    */ 
/*    */ import com.ztesoft.inf.extend.xstream.XStreamException;
/*    */ 
/*    */ public class StreamException extends XStreamException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public StreamException(Throwable e)
/*    */   {
/* 20 */     super(e);
/*    */   }
/*    */ 
/*    */   public StreamException(String message) {
/* 24 */     super(message);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.xstream.io.StreamException
 * JD-Core Version:    0.6.2
 */