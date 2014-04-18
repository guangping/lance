/*    */ package com.ztesoft.inf.extend.freemarker;
/*    */ 
/*    */ import freemarker.template.TemplateMethodModel;
/*    */ import freemarker.template.TemplateModelException;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ByteLenMethod
/*    */   implements TemplateMethodModel
/*    */ {
/*    */   public Object exec(List args)
/*    */     throws TemplateModelException
/*    */   {
/* 11 */     if (args.size() != 1) {
/* 12 */       throw new TemplateModelException("调用ByteLenMethod参数个数错误，应为1");
/*    */     }
/* 14 */     return new Integer(((String)args.get(0)).getBytes().length);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.freemarker.ByteLenMethod
 * JD-Core Version:    0.6.2
 */