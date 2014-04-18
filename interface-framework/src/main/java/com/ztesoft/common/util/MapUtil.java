/*     */ package com.ztesoft.common.util;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.beanutils.BeanUtils;
/*     */ 
/*     */ public class MapUtil
/*     */ {
/*     */   public static Map beanToMap(Object obj)
/*     */   {
/*     */     try
/*     */     {
/*  22 */       return BeanUtils.describe(obj);
/*     */     } catch (Exception e1) {
/*  24 */       e1.printStackTrace();
/*     */     }
/*  26 */     return null;
/*     */   }
/*     */ 
/*     */   public static Map listToMap(Iterator objects)
/*     */   {
/*  31 */     Map map = new HashMap();
/*     */ 
/*  33 */     while (objects.hasNext())
/*     */     {
/*  35 */       String object = objects.next().toString();
/*  36 */       map.put(object, object);
/*     */     }
/*  38 */     return map;
/*     */   }
/*     */ 
/*     */   public static Map arrayToMap(String[] objects)
/*     */   {
/*  43 */     Map map = new HashMap();
/*     */ 
/*  45 */     for (String str : objects)
/*     */     {
/*  47 */       map.put(str, str);
/*     */     }
/*     */ 
/*  50 */     return map;
/*     */   }
/*     */ 
/*     */   public static boolean isEmpty(Map map)
/*     */   {
/*  55 */     if (null == map)
/*     */     {
/*  57 */       return true;
/*     */     }
/*     */ 
/*  60 */     return map.isEmpty();
/*     */   }
/*     */ 
/*     */   public static Map mapKeyToLowerCase(Map map)
/*     */   {
/*  70 */     if (null == map)
/*  71 */       return null;
/*  72 */     Map map_key_lower = new HashMap();
/*  73 */     Iterator it_map = map.keySet().iterator();
/*  74 */     while (it_map.hasNext())
/*     */     {
/*  76 */       Object obj = it_map.next();
/*  77 */       if (obj.getClass().getName().endsWith("String"))
/*  78 */         map_key_lower.put(((String)obj).toLowerCase(), map.get(obj));
/*     */       else
/*  80 */         map_key_lower.put(obj, map.get(obj));
/*     */     }
/*  82 */     return map_key_lower;
/*     */   }
/*     */ 
/*     */   public static Map mapKeyToUpperCase(Map map)
/*     */   {
/*  92 */     if (null == map)
/*  93 */       return null;
/*  94 */     Map map_key_lower = new HashMap();
/*  95 */     Iterator it_map = map.keySet().iterator();
/*  96 */     while (it_map.hasNext())
/*     */     {
/*  98 */       Object obj = it_map.next();
/*  99 */       if (obj.getClass().getName().endsWith("String"))
/* 100 */         map_key_lower.put(((String)obj).toUpperCase(), map.get(obj));
/*     */       else
/* 102 */         map_key_lower.put(obj, map.get(obj));
/*     */     }
/* 104 */     return map_key_lower;
/*     */   }
/*     */ 
/*     */   public static Map mergeMap(Map sm, Map tm)
/*     */   {
/* 113 */     if ((sm == null) || (sm.isEmpty()) || (tm == null) || (tm.isEmpty()))
/* 114 */       return new HashMap();
/* 115 */     Map retmp = new HashMap();
/* 116 */     retmp.putAll(tm);
/* 117 */     retmp.putAll(mergeMapFromMap(sm, tm));
/* 118 */     return retmp;
/*     */   }
/*     */ 
/*     */   public static Map mergeMapFromMap(Map sm, Map tm)
/*     */   {
/* 127 */     if ((sm == null) || (sm.isEmpty()) || (tm == null) || (tm.isEmpty()))
/* 128 */       return new HashMap();
/* 129 */     Map retmp = new HashMap();
/*     */ 
/* 131 */     for (Iterator it = sm.keySet().iterator(); it.hasNext(); ) {
/* 132 */       String n = (String)it.next();
/* 133 */       if ((tm.get(n) != null) && (!"".equals(tm.get(n))))
/* 134 */         retmp.put(n, (String)tm.get(n));
/*     */       else {
/* 136 */         retmp.put(n, (String)sm.get(n));
/*     */       }
/*     */     }
/* 139 */     return retmp;
/*     */   }
/*     */ 
/*     */   public static Map mapToTargetMap(Map sm, Map tm)
/*     */   {
/* 147 */     if ((sm == null) || (sm.isEmpty()) || (tm == null) || (tm.isEmpty())) {
/* 148 */       return new HashMap();
/*     */     }
/* 150 */     for (Iterator it = sm.keySet().iterator(); it.hasNext(); ) {
/* 151 */       String n = (String)it.next();
/* 152 */       if ((tm.get(n) == null) || ("".equals(tm.get(n)))) {
/* 153 */         tm.put(n, (String)sm.get(n));
/*     */       }
/*     */     }
/*     */ 
/* 157 */     return tm;
/*     */   }
/*     */ 
/*     */   public static Map compareToMap(Map newMap, Map oldMap)
/*     */   {
/* 165 */     if ((newMap == null) || (newMap.isEmpty()) || (oldMap == null) || (oldMap.isEmpty()))
/* 166 */       return new HashMap();
/* 167 */     Map retmp = new HashMap();
/*     */ 
/* 169 */     for (Iterator it = newMap.keySet().iterator(); it.hasNext(); ) {
/* 170 */       String n = (String)it.next();
/*     */ 
/* 172 */       if (((newMap.get(n) == null) || (!newMap.get(n).equals(oldMap.get(n)))) && (
/* 174 */         (newMap.get(n) != null) || (oldMap.get(n) != null)))
/*     */       {
/* 177 */         retmp.put(n, (String)newMap.get(n));
/*     */       }
/*     */     }
/* 179 */     return retmp;
/*     */   }
/*     */ 
/*     */   public static boolean isEqual(Map o, Map c)
/*     */   {
/* 185 */     if ((o == null) || (c == null)) return false;
/*     */ 
/* 187 */     return o.equals(c);
/*     */   }
/*     */ 
/*     */   public static boolean isEqual(Map o, Map c, String[] keys)
/*     */   {
/* 222 */     if ((o == null) || (c == null)) return false;
/* 223 */     if (o == c)
/* 224 */       return true;
/* 225 */     if ((keys == null) || (keys.length == 0)) {
/* 226 */       o.equals(c);
/*     */     }
/* 228 */     if (!(o instanceof Map))
/* 229 */       return false;
/*     */     try
/*     */     {
/* 232 */       for (String mkey : keys) {
/* 233 */         Object oVal = o.get(mkey);
/* 234 */         if (oVal == null) {
/* 235 */           if ((c.get(mkey) != null) || (!c.containsKey(mkey)))
/* 236 */             return false;
/*     */         }
/* 238 */         else if (!oVal.equals(c.get(mkey)))
/* 239 */           return false;
/*     */       }
/*     */     }
/*     */     catch (ClassCastException unused)
/*     */     {
/* 244 */       return false;
/*     */     } catch (NullPointerException unused) {
/* 246 */       return false;
/*     */     }
/*     */ 
/* 249 */     return true;
/*     */   }
/*     */ 
/*     */   public static boolean isObjEqual(Object o, Map c, String[] keys)
/*     */   {
/* 257 */     if ((o == null) || (c == null)) return false;
/* 258 */     if (o == c)
/* 259 */       return true;
/* 260 */     if ((keys == null) || (keys.length == 0))
/* 261 */       o.equals(c);
/*     */     try
/*     */     {
/* 264 */       for (String mkey : keys) {
/* 265 */         Object oVal = StringUtils.getObjectValue(o, mkey);
/* 266 */         if (oVal == null) {
/* 267 */           if ((c.get(mkey) != null) || (!c.containsKey(mkey)))
/* 268 */             return false;
/*     */         }
/* 270 */         else if (!oVal.equals(c.get(mkey)))
/* 271 */           return false;
/*     */       }
/*     */     }
/*     */     catch (ClassCastException unused)
/*     */     {
/* 276 */       return false;
/*     */     } catch (NullPointerException unused) {
/* 278 */       return false;
/*     */     }
/*     */ 
/* 281 */     return true;
/*     */   }
/*     */ 
/*     */   public static Map safe(Map compParams)
/*     */   {
/* 286 */     if (compParams == null) {
/* 287 */       return new HashMap();
/*     */     }
/*     */ 
/* 290 */     return compParams;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.common.util.MapUtil
 * JD-Core Version:    0.6.2
 */