/*    */ package com.ztesoft.inf.extend.freemarker;
/*    */ 
/*    */ import com.ztesoft.common.util.StringUtils;
/*    */ import com.ztesoft.inf.framework.dao.SeqUtil;
/*    */ import freemarker.template.TemplateMethodModel;
/*    */ import freemarker.template.TemplateModelException;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ExchangeIdGenMethod
/*    */   implements TemplateMethodModel
/*    */ {
/* 20 */   private String client_id = "mmarkt";
/*    */ 
/*    */   public ExchangeIdGenMethod() {
/*    */   }
/* 24 */   public ExchangeIdGenMethod(String client_id) { if (!StringUtils.isEmpty(client_id))
/* 25 */       this.client_id = client_id; }
/*    */ 
/*    */   public Object exec(List arg0)
/*    */     throws TemplateModelException
/*    */   {
/* 30 */     return this.client_id + new SeqUtil().getSequenceLen("seq_crm2to2_id", "2", 24);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.freemarker.ExchangeIdGenMethod
 * JD-Core Version:    0.6.2
 */