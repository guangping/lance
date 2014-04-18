/*   */ package com.ztesoft.inf.framework.cache;
/*   */ 
/*   */ public class LRUMap<K, V> extends FIFOMap<K, V>
/*   */ {
/*   */   private static final long serialVersionUID = 1L;
/*   */ 
/*   */   public LRUMap()
/*   */   {
/* 8 */     super(16, 0.75F, true);
/*   */   }
/*   */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.framework.cache.LRUMap
 * JD-Core Version:    0.6.2
 */