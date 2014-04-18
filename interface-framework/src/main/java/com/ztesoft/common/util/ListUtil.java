/*     */ package com.ztesoft.common.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class ListUtil
/*     */ {
/*     */   public static boolean isEmpty(List list)
/*     */   {
/*  13 */     if (list == null)
/*  14 */       return true;
/*  15 */     return list.size() == 0;
/*     */   }
/*     */ 
/*     */   public static List mapToList(Map map) {
/*  19 */     List list = new ArrayList();
/*  20 */     Iterator it_map = map.keySet().iterator();
/*  21 */     while (it_map.hasNext()) {
/*  22 */       String key = (String)it_map.next();
/*  23 */       Object obj = map.get(key);
/*  24 */       list.add(obj);
/*     */     }
/*  26 */     return list;
/*     */   }
/*     */ 
/*     */   public static List mixedList(List listA, List listB)
/*     */   {
/*  36 */     List mixedList = new ArrayList();
/*  37 */     if ((!isEmpty(listA)) && (!isEmpty(listB))) {
/*  38 */       for (int i = 0; i < listA.size(); i++) {
/*  39 */         Object objA = listA.get(i);
/*  40 */         if (listB.contains(objA)) {
/*  41 */           mixedList.add(objA);
/*     */         }
/*     */       }
/*     */     }
/*  45 */     return mixedList;
/*     */   }
/*     */ 
/*     */   public static List notContainList(List listA, List listB)
/*     */   {
/*  55 */     List NotContainList = new ArrayList();
/*  56 */     if (!isEmpty(listA)) {
/*  57 */       if (isEmpty(listB))
/*  58 */         return listA;
/*  59 */       for (int i = 0; i < listA.size(); i++) {
/*  60 */         Object objA = listA.get(i);
/*  61 */         if (!listB.contains(objA)) {
/*  62 */           NotContainList.add(objA);
/*     */         }
/*     */       }
/*     */     }
/*  66 */     return NotContainList;
/*     */   }
/*     */ 
/*     */   public static List mergeList(List listA, List listB)
/*     */   {
/*  76 */     List res = new ArrayList();
/*     */ 
/*  78 */     if ((listA == null) && (listB == null))
/*  79 */       return res;
/*  80 */     if (isEmpty(listA)) {
/*  81 */       return listB;
/*     */     }
/*  83 */     if (isEmpty(listB)) {
/*  84 */       return listA;
/*     */     }
/*  86 */     for (int i = 0; i < listB.size(); i++) {
/*  87 */       Object obj = listB.get(i);
/*  88 */       if (!listA.contains(obj))
/*     */       {
/*  90 */         listA.add(obj);
/*     */       }
/*     */     }
/*  92 */     return listA;
/*     */   }
/*     */ 
/*     */   public static List strToList(String seperator, String str)
/*     */   {
/* 105 */     List resList = new ArrayList();
/* 106 */     if (StringUtils.isEmpty(str)) {
/* 107 */       return resList;
/*     */     }
/* 109 */     String[] strArray = str.split(seperator);
/* 110 */     if (strArray.length == 0)
/* 111 */       return resList;
/* 112 */     for (int i = 0; i < resList.size(); i++) {
/* 113 */       String strCell = strArray[i];
/* 114 */       if (!resList.contains(strCell))
/* 115 */         resList.add(strCell);
/*     */     }
/* 117 */     return resList;
/*     */   }
/*     */ 
/*     */   public static List<String> getStrValues(List<Map> mapList, String key)
/*     */   {
/* 128 */     List strList = new ArrayList();
/*     */ 
/* 130 */     if ((isEmpty(mapList)) || (key == null) || (key.equals(""))) {
/* 131 */       return strList;
/*     */     }
/*     */ 
/* 134 */     for (Map map : mapList) {
/* 135 */       String strValue = StringUtils.getStrValue(map, key);
/* 136 */       strList.add(strValue);
/*     */     }
/*     */ 
/* 139 */     return strList;
/*     */   }
/*     */ 
/*     */   public static Object getFrist(List o)
/*     */   {
/* 147 */     if (isEmpty(o)) {
/* 148 */       return null;
/*     */     }
/*     */ 
/* 151 */     return o.get(0);
/*     */   }
/*     */ 
/*     */   public static List safe(List list) {
/* 155 */     if (list == null)
/* 156 */       return new ArrayList();
/* 157 */     return list;
/*     */   }
/*     */ 
/*     */   public static boolean isExistMix(List<String> AList, List<String> ZList)
/*     */   {
/* 164 */     if ((isEmpty(AList)) || (isEmpty(ZList))) {
/* 165 */       return false;
/*     */     }
/* 167 */     for (String aString : AList) {
/* 168 */       if (ZList.contains(aString)) {
/* 169 */         return true;
/*     */       }
/*     */     }
/* 172 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.common.util.ListUtil
 * JD-Core Version:    0.6.2
 */