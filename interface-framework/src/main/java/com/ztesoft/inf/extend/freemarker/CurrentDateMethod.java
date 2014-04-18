/*    */ package com.ztesoft.inf.extend.freemarker;
/*    */ 
/*    */ import com.ztesoft.common.util.date.DateUtil;
/*    */ import freemarker.template.TemplateMethodModel;
/*    */ import freemarker.template.TemplateModelException;
/*    */ import java.util.List;
/*    */ 
/*    */ public class CurrentDateMethod
/*    */   implements TemplateMethodModel
/*    */ {
/*    */   public Object exec(List args)
/*    */     throws TemplateModelException
/*    */   {
/* 13 */     return DateUtil.current();
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.freemarker.CurrentDateMethod
 * JD-Core Version:    0.6.2
 */