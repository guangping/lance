/*    */ package com.ztesoft.inf.extend.freemarker;
/*    */ 
/*    */ import com.ztesoft.common.util.date.DateUtil;
/*    */ import freemarker.template.TemplateMethodModel;
/*    */ import freemarker.template.TemplateModelException;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ReqTimeGenMethod
/*    */   implements TemplateMethodModel
/*    */ {
/*    */   public Object exec(List arg0)
/*    */     throws TemplateModelException
/*    */   {
/* 16 */     return DateUtil.formatCurDate("yyyyMMddHHmmss");
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.freemarker.ReqTimeGenMethod
 * JD-Core Version:    0.6.2
 */