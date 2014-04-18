/*    */ package com.ztesoft.inf;
/*    */ 
/*    */ import com.ztesoft.inf.communication.client.CommCaller;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class InfTest
/*    */ {
/*    */   public static String doService(String acc_nbr, String lan_code)
/*    */   {
/* 15 */     Map params = new HashMap();
/* 16 */     params.put("accNbr", acc_nbr);
/* 17 */     params.put("lan_code", lan_code);
/*    */     try {
/* 19 */       Object o = new CommCaller().invoke("INF_GetCustBasicByProduct", params);
/* 20 */       return o.toString().substring(o.toString().indexOf("<Message>") + 9, o.toString().indexOf("</Message>"));
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/*    */     }
/* 25 */     return null;
/*    */   }
/*    */   public static void main(String[] args) {
/* 28 */     doService("18970993899", "0791");
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.InfTest
 * JD-Core Version:    0.6.2
 */