/*    */ package com.ztesoft.inf.extend.freemarker;
/*    */ 
/*    */ import com.ztesoft.common.util.StringUtils;
/*    */ import freemarker.template.TemplateMethodModel;
/*    */ import freemarker.template.TemplateModelException;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ByteLenlimitMethod
/*    */   implements TemplateMethodModel
/*    */ {
/*    */   public static final int LEFT_PAD = 1;
/*    */   public static final int RIGHT_PAD = 2;
/*    */   private int padType;
/*    */ 
/*    */   public ByteLenlimitMethod(int padType)
/*    */   {
/* 18 */     this.padType = padType;
/*    */   }
/*    */ 
/*    */   public Object exec(List args) throws TemplateModelException {
/* 22 */     if (this.padType != 0) {
/* 23 */       if (args.size() != 3) {
/* 24 */         throw new TemplateModelException("调用ByteLenlimitMethod参数个数错误，应为3");
/*    */       }
/*    */     }
/* 27 */     else if (args.size() != 2) {
/* 28 */       throw new TemplateModelException("调用ByteLenlimitMethod参数个数错误，应为2");
/*    */     }
/* 30 */     String str = (String)args.get(0);
/* 31 */     Integer len = Integer.valueOf(Integer.parseInt((String)args.get(1)));
/* 32 */     if (str.getBytes().length > len.intValue()) {
/* 33 */       throw new TemplateModelException("字符串长度(按字节)限制为" + len + ",{" + str + "}不符合!");
/*    */     }
/*    */ 
/* 36 */     if (this.padType != 0) {
/* 37 */       String pad = (String)args.get(2);
/* 38 */       if (this.padType == 1)
/* 39 */         str = StringUtils.leftPad(str, len.intValue(), pad.charAt(0));
/*    */       else {
/* 41 */         str = StringUtils.rightPad(str, len, pad.charAt(0));
/*    */       }
/*    */     }
/* 44 */     return str;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.freemarker.ByteLenlimitMethod
 * JD-Core Version:    0.6.2
 */