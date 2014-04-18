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
/*    */ public class TplParamInst
/*    */   implements ITplParam
/*    */ {
/*    */   private static Configuration conf;
/* 16 */   private static int tempSeq = 1;
/* 17 */   private static Map utils = new HashMap();
/*    */ 
/*    */   public Template createTemplate(String content)
/*    */     throws Exception
/*    */   {
/* 36 */     return new Template("ID:" + tempSeq++, new StringReader(content), conf);
/*    */   }
/*    */ 
/*    */   public static Map getUtilMethods()
/*    */   {
/* 41 */     return utils;
/*    */   }
/*    */ 
/*    */   public void addUtils(Map root) {
/* 45 */     root.put("ut", utils);
/*    */   }
/*    */ 
/*    */   public void addInvokerInfo(Map root, Invoker invoker)
/*    */   {
/* 50 */     Map invoke = new HashMap();
/* 51 */     invoke.put("user_code", invoker.getReqUser().getUser_code());
/* 52 */     invoke.put("user_pwd", invoker.getReqUser().getUser_pwd());
/* 53 */     invoke.put("user_name", invoker.getReqUser().getUser_name());
/* 54 */     invoke.put("user_param", invoker.getReqUser().getUser_param());
/* 55 */     invoke.put("exchangeidgen", new ExchangeIdGenMethod(invoker.getReqUser().getUser_code()));
/*    */ 
/* 57 */     root.put("inf", invoke);
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 20 */     conf = new Configuration();
/* 21 */     conf.setNumberFormat("0");
/* 22 */     utils.put("blen", new ByteLenMethod());
/* 23 */     utils.put("curdate", new CurrentDateMethod());
/* 24 */     utils.put("nextseq", new ExchangeIdGenMethod(""));
/* 25 */     utils.put("blimit", new ByteLenlimitMethod(0));
/* 26 */     utils.put("blimit_lpad", new ByteLenlimitMethod(1));
/*    */ 
/* 28 */     utils.put("blimit_rpad", new ByteLenlimitMethod(2));
/*    */ 
/* 30 */     utils.put("bfix", new ByteLenFixMethod());
/* 31 */     utils.put("base64md5", new Base64MD5Method());
/* 32 */     utils.put("exchangeidgen", new ExchangeIdGenMethod());
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.freemarker.TplParamInst
 * JD-Core Version:    0.6.2
 */