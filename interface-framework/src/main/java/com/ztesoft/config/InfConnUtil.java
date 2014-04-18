/*    */ package com.ztesoft.config;
/*    */ 
/*    */ import com.ztesoft.net.framework.context.spring.SpringContextHolder;
/*    */ import java.sql.Connection;
/*    */ import java.sql.SQLException;
/*    */ import javax.sql.DataSource;
/*    */ import org.springframework.context.ApplicationContext;
/*    */ import org.springframework.context.support.ClassPathXmlApplicationContext;
/*    */ 
/*    */ public class InfConnUtil
/*    */ {
/*    */   private static final String INF_DATASOURCE = "infdb";
/*    */   private static ApplicationContext applicationContext;
/* 23 */   private static InfConnUtil InfConnUtil = null;
/* 24 */   private static DataSource db = null;
/*    */ 
/*    */   public static Connection getConnection()
/*    */   {
/* 47 */     return getDatasourceConn();
/*    */   }
/*    */ 
/*    */   private static Connection getDatasourceConn()
/*    */   {
/*    */     try
/*    */     {
/* 56 */       if (db == null) {
/* 57 */         db = (DataSource)applicationContext.getBean("infdb", DataSource.class);
/*    */       }
/* 59 */       return db.getConnection();
/*    */     } catch (SQLException e) {
/* 61 */       e.printStackTrace();
/*    */     }
/* 63 */     return null;
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 27 */     if (InfConnUtil == null) {
/* 28 */       InfConnUtil = new InfConnUtil();
/*    */ 
/* 30 */       if (SpringContextHolder.isConfig()) {
/* 31 */         applicationContext = SpringContextHolder.getApplicationContext();
/*    */       } else {
/* 33 */         String springXml = ParamsConfig.getInstance().getParamValue("SpringXml");
/* 34 */         applicationContext = new ClassPathXmlApplicationContext(springXml);
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.config.InfConnUtil
 * JD-Core Version:    0.6.2
 */