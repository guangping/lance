/*     */ package com.ztesoft.inf.framework.logger;
/*     */ 
/*     */ import com.ztesoft.inf.framework.commons.TransactionalBeanManager;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.springframework.dao.DataAccessException;
/*     */ 
/*     */ public class LogThread extends Thread
/*     */ {
/*     */   public static final int TIME_OUT = 5000;
/*     */   private static final int MAX_QUEUE_SIZE = 500;
/*  17 */   private static final Logger logger = Logger.getLogger(LogThread.class);
/*     */ 
/*  19 */   private static Queue<AppLogContext> logQueue = new ConcurrentLinkedQueue();
/*     */ 
/*  21 */   private static volatile boolean shutdownRequested = false;
/*     */ 
/*  23 */   public static volatile boolean START_UP = false;
/*     */ 
/*     */   public void run()
/*     */   {
/*     */     try
/*     */     {
/*  36 */       while (!shutdownRequested)
/*  37 */         logToDB(getLog());
/*     */     }
/*     */     finally {
/*  40 */       doShutdown();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void addLogQueue(AppLogContext obj)
/*     */   {
/*  54 */     logQueue.add(obj);
/*     */   }
/*     */ 
/*     */   private static AppLogContext getLog()
/*     */   {
/*  60 */     while (logQueue.isEmpty()) {
/*     */       try {
/*  62 */         sleep(1000L);
/*     */       } catch (InterruptedException e) {
/*  64 */         e.printStackTrace();
/*     */       }
/*     */     }
/*  67 */     AppLogContext obj = (AppLogContext)logQueue.poll();
/*     */ 
/*  69 */     return obj;
/*     */   }
/*     */ 
/*     */   private static void logToDB(AppLogContext obj)
/*     */   {
/*     */     try {
/*  75 */       if (obj == null) {
/*  76 */         return;
/*     */       }
/*  78 */       AppLogContext appLogContext = (AppLogContext)TransactionalBeanManager.createTransactional(AppLogContext.class);
/*  79 */       appLogContext.setAppLogger(obj.getAppLogger());
/*  80 */       appLogContext.setLogObj(obj.getLogObj());
/*  81 */       appLogContext.logToDB();
/*     */     } catch (DataAccessException e) {
/*  83 */       e.printStackTrace();
/*  84 */       logger.error(e);
/*     */     } catch (InterruptedException e) {
/*  86 */       e.printStackTrace();
/*  87 */       logger.error(e);
/*     */     } catch (Exception e) {
/*  89 */       e.printStackTrace();
/*  90 */       logger.error(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isShutdownRequested()
/*     */   {
/* 159 */     return shutdownRequested;
/*     */   }
/*     */ 
/*     */   public void shutdown()
/*     */   {
/* 165 */     shutdownRequested = true;
/*     */   }
/*     */ 
/*     */   public static synchronized boolean isStartUP()
/*     */   {
/* 170 */     return START_UP;
/*     */   }
/*     */ 
/*     */   public static synchronized void startUP() {
/* 174 */     if (START_UP)
/* 175 */       return;
/* 176 */     START_UP = true;
/* 177 */     LogThread logThread = new LogThread();
/* 178 */     logThread.setPriority(1);
/* 179 */     logThread.start();
/*     */   }
/*     */ 
/*     */   private void doShutdown() {
/* 183 */     System.out.println(getName() + " is ShutDowned!");
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  26 */     if (!isStartUP())
/*  27 */       startUP();
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.framework.logger.LogThread
 * JD-Core Version:    0.6.2
 */