/*    */ package com.ztesoft.inf.extend.freemarker;
/*    */ 
/*    */ import com.ztesoft.common.util.Clazz;
/*    */ import com.ztesoft.config.ParamsConfig;
/*    */ import com.ztesoft.inf.communication.client.Invoker;
/*    */ import freemarker.template.Template;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class TemplateUtils
/*    */ {
/* 16 */   private static ITplParam tpl = getInstance();
/*    */ 
/*    */   public static ITplParam getInstance()
/*    */   {
/* 20 */     String clazzStr = ParamsConfig.getInstance().getParamValue("TemplateParamClass");
/*    */ 
/* 22 */     if (!StringUtils.isEmpty(clazzStr)) {
/* 23 */       tpl = (ITplParam)Clazz.newInstance(clazzStr);
/*    */     }
/* 25 */     if (tpl == null) {
/* 26 */       tpl = new TplParamInstEx();
/*    */     }
/*    */ 
/* 29 */     return tpl;
/*    */   }
/*    */ 
/*    */   public static void addUtils(Map root) {
/* 33 */     tpl.addUtils(root);
/*    */   }
/*    */ 
/*    */   public static Template createTemplate(String content) throws Exception {
/* 37 */     return tpl.createTemplate(content);
/*    */   }
/*    */ 
/*    */   public static void addInvokerInfo(Map root, Invoker invoker) {
/* 41 */     tpl.addInvokerInfo(root, invoker);
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.freemarker.TemplateUtils
 * JD-Core Version:    0.6.2
 */