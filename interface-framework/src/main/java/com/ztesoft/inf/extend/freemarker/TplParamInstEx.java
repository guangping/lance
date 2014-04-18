/*    */ package com.ztesoft.inf.extend.freemarker;
/*    */ 
/*    */ import com.ztesoft.inf.communication.client.Invoker;
/*    */ import com.ztesoft.inf.communication.client.vo.ClientRequestUser;
/*    */ import freemarker.template.Configuration;
/*    */ import freemarker.template.Template;
/*    */ import java.io.StringReader;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class TplParamInstEx
/*    */   implements ITplParam
/*    */ {
/*    */   private static Configuration conf;
/* 16 */   private static int tempSeq = 1;
/* 17 */   private static Map utils = new HashMap();
/*    */ 
/*    */   public Template createTemplate(String content) throws Exception {
/* 20 */     return new Template("ID:" + tempSeq++, new StringReader(content), conf);
/*    */   }
/*    */ 
/*    */   public static Map getUtilMethods()
/*    */   {
/* 25 */     return utils;
/*    */   }
/*    */ 
/*    */   public void addUtils(Map root)
/*    */   {
/* 30 */     root.put("ut", utils);
/*    */   }
/*    */ 
/*    */   public void addInvokerInfo(Map root, Invoker invoker) {
/* 34 */     Map invoke = new HashMap();
/* 35 */     invoke.put("transactionIdGen", new ExchangeIdGenMethod(invoker.getReqUser().getUser_code()));
/*    */ 
/* 37 */     invoke.put("reqTimeGen", new ReqTimeGenMethod());
/*    */ 
/* 39 */     root.put("inf", invoke);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.freemarker.TplParamInstEx
 * JD-Core Version:    0.6.2
 */