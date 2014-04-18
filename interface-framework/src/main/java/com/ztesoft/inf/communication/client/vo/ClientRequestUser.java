/*    */ package com.ztesoft.inf.communication.client.vo;
/*    */ 
/*    */ import com.ztesoft.common.util.StringUtils;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ClientRequestUser
/*    */ {
/* 15 */   private String user_id = "";
/* 16 */   private String user_code = "";
/* 17 */   private String user_name = "";
/* 18 */   private String user_pwd = "";
/* 19 */   private String user_param = "";
/* 20 */   private String user_desc = "";
/*    */ 
/*    */   public ClientRequestUser() {
/*    */   }
/*    */ 
/*    */   public ClientRequestUser(Map map) {
/* 26 */     setValues(map);
/*    */   }
/*    */ 
/*    */   private void setValues(Map map) {
/* 30 */     if (map != null) {
/* 31 */       this.user_id = StringUtils.getStrValue(map, "user_id");
/* 32 */       this.user_code = StringUtils.getStrValue(map, "user_code");
/* 33 */       this.user_name = StringUtils.getStrValue(map, "user_name");
/* 34 */       this.user_pwd = StringUtils.getStrValue(map, "user_pwd");
/* 35 */       this.user_param = StringUtils.getStrValue(map, "user_param");
/* 36 */       this.user_desc = StringUtils.getStrValue(map, "user_desc");
/*    */     }
/*    */   }
/*    */ 
/*    */   public String getUser_id() {
/* 41 */     return this.user_id;
/*    */   }
/*    */ 
/*    */   public void setUser_id(String user_id) {
/* 45 */     this.user_id = user_id;
/*    */   }
/*    */ 
/*    */   public String getUser_code() {
/* 49 */     return this.user_code;
/*    */   }
/*    */ 
/*    */   public void setUser_code(String user_code) {
/* 53 */     this.user_code = user_code;
/*    */   }
/*    */ 
/*    */   public String getUser_name() {
/* 57 */     return this.user_name;
/*    */   }
/*    */ 
/*    */   public void setUser_name(String user_name) {
/* 61 */     this.user_name = user_name;
/*    */   }
/*    */ 
/*    */   public String getUser_pwd() {
/* 65 */     return this.user_pwd;
/*    */   }
/*    */ 
/*    */   public void setUser_pwd(String user_pwd) {
/* 69 */     this.user_pwd = user_pwd;
/*    */   }
/*    */ 
/*    */   public String getUser_param() {
/* 73 */     return this.user_param;
/*    */   }
/*    */ 
/*    */   public void setUser_param(String user_param) {
/* 77 */     this.user_param = user_param;
/*    */   }
/*    */ 
/*    */   public String getUser_desc() {
/* 81 */     return this.user_desc;
/*    */   }
/*    */ 
/*    */   public void setUser_desc(String user_desc) {
/* 85 */     this.user_desc = user_desc;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.communication.client.vo.ClientRequestUser
 * JD-Core Version:    0.6.2
 */