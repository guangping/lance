/*    */ package com.ztesoft.inf.extend.freemarker;
/*    */ 
/*    */ import com.seaway.util.UppUtil;
/*    */ import freemarker.template.TemplateMethodModel;
/*    */ import freemarker.template.TemplateModelException;
/*    */ import java.util.List;
/*    */ 
/*    */ public class Base64MD5Method
/*    */   implements TemplateMethodModel
/*    */ {
/*    */   public Object exec(List args)
/*    */     throws TemplateModelException
/*    */   {
/* 13 */     if (args.size() != 1) {
/* 14 */       throw new TemplateModelException("调用Base64MD5Method参数个数错误，应为1");
/*    */     }
/* 16 */     String content = (String)args.get(0);
/* 17 */     UppUtil upp = new UppUtil();
/* 18 */     return upp.base64Md5(content);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.freemarker.Base64MD5Method
 * JD-Core Version:    0.6.2
 */