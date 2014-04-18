/*    */ package com.ztesoft.inf.framework.commons;
/*    */ 
/*    */ import com.ztesoft.ibss.common.util.StringUtils;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ 
/*    */ public class CodedException extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String code;
/*    */   private String message;
/*    */   private String errStackInfo;
/*    */ 
/*    */   public CodedException(String code, String message)
/*    */   {
/* 15 */     super(message);
/* 16 */     this.code = code;
/* 17 */     this.message = message;
/*    */   }
/*    */   public CodedException(String code, String message, Throwable e) {
/* 20 */     super(refact(message, e), e);
/* 21 */     this.code = code;
/* 22 */     this.message = getMessage();
/* 23 */     if ((e instanceof CodedException)) {
/* 24 */       CodedException ce = (CodedException)e;
/* 25 */       this.code = ce.code;
/*    */     }
/*    */   }
/*    */ 
/* 29 */   private static String refact(String _message, Throwable e) { String message = _message;
/* 30 */     if ((e instanceof CodedException)) {
/* 31 */       CodedException ce = (CodedException)e;
/* 32 */       if (!StringUtils.equals(message, ce.message))
/* 33 */         message = message + "," + ce.message;
/*    */     }
/* 35 */     else if ((e instanceof InvocationTargetException)) {
/* 36 */       Throwable te = ((InvocationTargetException)e).getTargetException();
/* 37 */       message = message + te != null ? te.getMessage() : "";
/*    */     } else {
/* 39 */       message = message + e.getMessage();
/*    */     }
/* 41 */     return message; }
/*    */ 
/*    */   public String getCode() {
/* 44 */     return this.code;
/*    */   }
/*    */   public String toString() {
/* 47 */     return "{" + this.code + "}" + this.message;
/*    */   }
/*    */   public String getErrStackInfo() {
/* 50 */     return this.errStackInfo;
/*    */   }
/*    */   public void setErrStackInfo(String errStackInfo) {
/* 53 */     this.errStackInfo = errStackInfo;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.framework.commons.CodedException
 * JD-Core Version:    0.6.2
 */