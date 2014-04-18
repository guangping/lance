/*    */ package com.ztesoft.inf.extend.freemarker;
/*    */ 
/*    */ import freemarker.template.TemplateMethodModel;
/*    */ import freemarker.template.TemplateModelException;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ByteLenFixMethod
/*    */   implements TemplateMethodModel
/*    */ {
/*    */   public Object exec(List args)
/*    */     throws TemplateModelException
/*    */   {
/* 14 */     if (args.size() != 2) {
/* 15 */       throw new TemplateModelException("调用ByteLenFixMethod参数个数错误，应为2");
/*    */     }
/* 17 */     String str = (String)args.get(0);
/* 18 */     Integer len = Integer.valueOf(Integer.parseInt((String)args.get(1)));
/* 19 */     if (str.getBytes().length != len.intValue()) {
/* 20 */       throw new TemplateModelException("字符串字节长度必须为" + len + ",{" + str + "}不符合!");
/*    */     }
/*    */ 
/* 23 */     return str;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.freemarker.ByteLenFixMethod
 * JD-Core Version:    0.6.2
 */