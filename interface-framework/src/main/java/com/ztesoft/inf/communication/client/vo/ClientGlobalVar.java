/*    */ package com.ztesoft.inf.communication.client.vo;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ClientGlobalVar
/*    */ {
/*    */   private String gvarsId;
/*    */   private String gvarsDefinition;
/*    */ 
/*    */   public ClientGlobalVar(Map map)
/*    */   {
/* 11 */     setValues(map);
/*    */   }
/*    */ 
/*    */   private void setValues(Map map) {
/* 15 */     this.gvarsId = ((String)map.get("gvar_id"));
/* 16 */     byte[] tpl = (byte[])map.get("gvar_def");
/* 17 */     if (tpl != null)
/* 18 */       this.gvarsDefinition = new String(tpl);
/*    */   }
/*    */ 
/*    */   public String getGvarsId() {
/* 22 */     return this.gvarsId;
/*    */   }
/*    */ 
/*    */   public void setGvarsId(String gvarsId) {
/* 26 */     this.gvarsId = gvarsId;
/*    */   }
/*    */ 
/*    */   public String getGvarsDefinition() {
/* 30 */     return this.gvarsDefinition;
/*    */   }
/*    */ 
/*    */   public void setGvarsDefinition(String gvarsDefinition) {
/* 34 */     this.gvarsDefinition = gvarsDefinition;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.communication.client.vo.ClientGlobalVar
 * JD-Core Version:    0.6.2
 */