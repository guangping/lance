/*     */ package com.ztesoft.inf.framework.concurrent;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class KeyLockSupport
/*     */ {
/*   9 */   private static Logger logger = Logger.getLogger(KeyLockSupport.class);
/*     */   private boolean single;
/*  26 */   private ThreadLocal threadLocal = new ThreadLocal();
/*  27 */   private Map keyLockMap = new HashMap();
/*     */ 
/*     */   public KeyLockSupport()
/*     */   {
/*  13 */     this(false);
/*     */   }
/*     */ 
/*     */   public KeyLockSupport(boolean single) {
/*  17 */     this.single = single;
/*     */   }
/*     */ 
/*     */   public void lock(Object key)
/*     */   {
/*  30 */     if (key == null) {
/*  31 */       throw new RuntimeException("lock key cannot be null!");
/*     */     }
/*  33 */     LockEntry entry = getLockEntry(key);
/*  34 */     synchronized (entry) {
/*     */       try {
/*  36 */         Thread currentThread = Thread.currentThread();
/*  37 */         if (entry.owner == null) {
/*  38 */           entry.owner = currentThread;
/*  39 */           logger.debug("entry:" + entry.key + " locked by thread:" + currentThread.getName());
/*     */         }
/*     */ 
/*  42 */         Set entries = (Set)this.threadLocal.get();
/*  43 */         if (entries == null) {
/*  44 */           entries = new HashSet();
/*  45 */           this.threadLocal.set(entries);
/*     */         }
/*  47 */         if (currentThread != entry.owner) {
/*  48 */           if (this.single) {
/*  49 */             throw new LockedException(key);
/*     */           }
/*     */ 
/*  52 */           Iterator iter = entries.iterator();
/*  53 */           while (iter.hasNext()) {
/*  54 */             LockEntry lockedEntry = (LockEntry)iter.next();
/*  55 */             if (lockedEntry.waitingThreads.contains(entry.owner)) {
/*  56 */               throw new RuntimeException("would be dead lock!!!! " + currentThread.getName() + " cannot be waiting to lock " + key + " owned by " + entry.owner.getName() + " waiting to lock " + lockedEntry.key + " locked by  " + currentThread.getName());
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*  67 */           logger.debug(currentThread.getName() + " waiting on entry:" + entry.key);
/*     */ 
/*  69 */           entry.waitingThreads.add(currentThread);
/*  70 */           entry.wait();
/*  71 */           entry.waitingThreads.remove(currentThread);
/*  72 */           entry.owner = Thread.currentThread();
/*  73 */           logger.debug("entry:" + entry.key + " locked by thread:" + currentThread.getName());
/*     */         }
/*     */ 
/*  77 */         entries.add(entry);
/*     */       } catch (InterruptedException e) {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private synchronized LockEntry getLockEntry(Object key) {
/*  84 */     LockEntry entry = (LockEntry)this.keyLockMap.get(key);
/*  85 */     if (entry == null) {
/*  86 */       entry = new LockEntry(null);
/*  87 */       entry.key = key;
/*  88 */       this.keyLockMap.put(key, entry);
/*     */     }
/*  90 */     return entry;
/*     */   }
/*     */ 
/*     */   public synchronized void unlock() {
/*  94 */     Set entries = (Set)this.threadLocal.get();
/*  95 */     if (entries != null) {
/*  96 */       Iterator iter = entries.iterator();
/*  97 */       while (iter.hasNext()) {
/*  98 */         LockEntry entry = (LockEntry)iter.next();
/*  99 */         synchronized (entry) {
/* 100 */           if (entry.waitingThreads.size() == 0) {
/* 101 */             this.keyLockMap.remove(entry.key);
/* 102 */             logger.debug("remove entry:" + entry.key);
/*     */           }
/* 104 */           entry.notify();
/* 105 */           logger.debug("release entry:" + entry.key + " by thread:" + entry.owner.getName());
/*     */         }
/*     */       }
/*     */ 
/* 109 */       entries.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 115 */     KeyLockSupport lock = new KeyLockSupport();
/* 116 */     for (int i = 0; i < 10; i++)
/* 117 */       new Thread(new Runnable() {
/*     */         public void run() {
/*     */           try {
/* 120 */             Thread.sleep(()(Math.random() * 1000.0D));
/* 121 */             if (Math.random() * 10.0D > 5.0D) {
/* 122 */               this.val$lock.lock("1a1");
/* 123 */               Thread.sleep(()(Math.random() * 1000.0D));
/* 124 */               this.val$lock.lock("1b1");
/*     */             } else {
/* 126 */               this.val$lock.lock("1b1");
/* 127 */               Thread.sleep(()(Math.random() * 1000.0D));
/* 128 */               this.val$lock.lock("1a1");
/*     */             }
/*     */           } catch (Exception e) {
/* 131 */             KeyLockSupport.logger.error(e);
/*     */           } finally {
/* 133 */             this.val$lock.unlock();
/*     */           }
/*     */         }
/*     */       }).start();
/*     */   }
/*     */ 
/*     */   private static class LockEntry
/*     */   {
/*     */     Object key;
/*  22 */     Set waitingThreads = new HashSet();
/*     */     Thread owner;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.framework.concurrent.KeyLockSupport
 * JD-Core Version:    0.6.2
 */