/*     */ package com.ztesoft.common.util.convert;
/*     */ 
/*     */ import com.ztesoft.common.dao.DAOSystemException;
/*     */ import com.ztesoft.common.util.ListUtil;
/*     */ import com.ztesoft.common.util.MapUtil;
/*     */ import com.ztesoft.common.util.StringUtils;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.beanutils.PropertyUtils;
/*     */ 
/*     */ public final class Converts
/*     */ {
/*     */   public static Map<String, Object> toHashMap(Object fromObject)
/*     */     throws RuntimeException
/*     */   {
/*  26 */     HashMap hashMap = new HashMap();
/*  27 */     Class clazz = fromObject.getClass();
/*     */ 
/*  29 */     List clazzs = new ArrayList();
/*     */     try
/*     */     {
/*     */       do
/*     */       {
/*  35 */         clazzs.add(clazz);
/*  36 */         clazz = clazz.getSuperclass();
/*  37 */       }while (!clazz.equals(Object.class));
/*     */ 
/*  39 */       for (Class iClazz : clazzs) {
/*  40 */         Field[] fields = iClazz.getDeclaredFields();
/*  41 */         for (Field field : fields) {
/*  42 */           Object fieldVal = null;
/*  43 */           field.setAccessible(true);
/*  44 */           fieldVal = field.get(fromObject);
/*  45 */           hashMap.put(field.getName(), fieldVal);
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/*  49 */       throw new RuntimeException(e);
/*     */     }
/*     */ 
/*  52 */     return hashMap;
/*     */   }
/*     */ 
/*     */   public static Object toObject(List<Object> list, Class<?> cls, String keyName, String keyValueNmae)
/*     */   {
/*  60 */     Object obj = null;
/*     */     try {
/*  62 */       obj = cls.newInstance();
/*     */     } catch (Exception e) {
/*  64 */       e.printStackTrace();
/*     */     }
/*  66 */     Map map = toMapByListMap(list, keyName, keyValueNmae);
/*  67 */     return toObject(map, cls);
/*     */   }
/*     */ 
/*     */   public static Map toMapByListMap(List<Object> list, String keyName, String keyValueNmae)
/*     */   {
/*  76 */     Map map = new HashMap();
/*     */ 
/*  78 */     for (Iterator i$ = list.iterator(); i$.hasNext(); ) { Object object = i$.next();
/*     */ 
/*  80 */       Map record = (Map)object;
/*     */ 
/*  82 */       String newKeyName = String.valueOf(record.get(keyName));
/*  83 */       Object newKeyValue = record.get(keyValueNmae);
/*  84 */       if (!StringUtils.isEmpty(newKeyName)) {
/*  85 */         map.put(newKeyName.toLowerCase(), newKeyValue);
/*     */       }
/*     */     }
/*     */ 
/*  89 */     return map;
/*     */   }
/*     */ 
/*     */   public static <T> T toObject(Map<?, Object> map, Class<T> cls)
/*     */     throws RuntimeException
/*     */   {
/*  95 */     Object obj = null;
/*     */     try {
/*  97 */       obj = cls.newInstance();
/*     */     } catch (Exception e) {
/*  99 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 102 */     Method[] methods = cls.getMethods();
/* 103 */     for (int i = 0; i < methods.length; i++)
/*     */     {
/* 105 */       String method = methods[i].getName();
/*     */ 
/* 108 */       Class[] cc = methods[i].getParameterTypes();
/* 109 */       if (cc.length == 1)
/*     */       {
/* 113 */         if (method.indexOf("set") >= 0)
/*     */         {
/* 116 */           String type = cc[0].getSimpleName();
/*     */           try
/*     */           {
/* 120 */             Object value = method.substring(3, 4).toLowerCase() + method.substring(4);
/*     */ 
/* 123 */             if ((StringUtils.containMapKey(map, value)) && (StringUtils.getObjectFromMap(map, value) != null))
/*     */             {
/* 126 */               setValue(type, StringUtils.getObjectFromMap(map, value), i, methods, obj);
/*     */             }
/*     */           }
/*     */           catch (Exception e) {
/* 130 */             e.printStackTrace();
/* 131 */             throw new RuntimeException(e);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 135 */     if ((obj instanceof IConvert)) {
/* 136 */       ((IConvert)obj).initObject(map);
/*     */     }
/*     */ 
/* 139 */     return obj;
/*     */   }
/*     */ 
/*     */   public static void toObject(Map<?, Object> map, Object descObj)
/*     */     throws RuntimeException
/*     */   {
/* 146 */     Method[] methods = descObj.getClass().getMethods();
/* 147 */     for (int i = 0; i < methods.length; i++)
/*     */     {
/* 149 */       String method = methods[i].getName();
/*     */ 
/* 152 */       Class[] cc = methods[i].getParameterTypes();
/* 153 */       if (cc.length == 1)
/*     */       {
/* 157 */         if (method.indexOf("set") >= 0)
/*     */         {
/* 160 */           String type = cc[0].getSimpleName();
/*     */           try
/*     */           {
/* 164 */             Object value = method.substring(3, 4).toLowerCase() + method.substring(4);
/*     */ 
/* 167 */             if ((map.containsKey(value)) && (map.get(value) != null))
/*     */             {
/* 169 */               setValue(type, map.get(value), i, methods, descObj);
/*     */             }
/*     */           } catch (Exception e) {
/* 172 */             e.printStackTrace();
/* 173 */             throw new RuntimeException(e);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void setValue(String type, Object value, int i, Method[] method, Object bean)
/*     */     throws RuntimeException
/*     */   {
/* 184 */     if ((value == null) || ("".equals(value)))
/* 185 */       return;
/*     */     try
/*     */     {
/*     */       try {
/* 189 */         if (type.equals("String"))
/* 190 */           method[i].invoke(bean, new Object[] { value });
/* 191 */         else if ((type.equals("int")) || (type.equals("Integer"))) {
/* 192 */           method[i].invoke(bean, new Object[] { new Integer("" + value) });
/*     */         }
/* 194 */         else if ((type.equals("long")) || (type.equals("Long"))) {
/* 195 */           method[i].invoke(bean, new Object[] { new Long("" + value) });
/*     */         }
/* 197 */         else if ((type.equals("boolean")) || (type.equals("Boolean"))) {
/* 198 */           method[i].invoke(bean, new Object[] { Boolean.valueOf("" + value) });
/*     */         }
/*     */         else
/* 201 */           method[i].invoke(bean, new Object[] { value });
/*     */       }
/*     */       catch (Exception e) {
/* 204 */         method[i].invoke(bean, new Object[] { value.toString() });
/*     */       }
/*     */     } catch (Exception e) {
/* 207 */       e.printStackTrace();
/* 208 */       System.out.println("method[" + i + "]=" + method[i] + ",bean=" + bean + ",value" + value.getClass() + ",value=" + value);
/*     */ 
/* 210 */       throw new RuntimeException(e.getMessage() + " 将HashMap 或 HashTable 里的值填充到JAVA对象时出错,请检查!");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void copy(Object dest, Object orgi)
/*     */   {
/*     */     try {
/* 217 */       PropertyUtils.copyProperties(dest, orgi);
/*     */     }
/*     */     catch (IllegalAccessException e) {
/* 220 */       throw new RuntimeException(e);
/*     */     }
/*     */     catch (InvocationTargetException e) {
/* 223 */       e.printStackTrace();
/* 224 */       throw new RuntimeException(e);
/*     */     }
/*     */     catch (NoSuchMethodException e) {
/* 227 */       e.printStackTrace();
/* 228 */       throw new RuntimeException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static List copyList(List<Object> list, Class cls)
/*     */   {
/* 237 */     List result = new ArrayList();
/* 238 */     for (Iterator i$ = list.iterator(); i$.hasNext(); ) { Object orgObj = i$.next();
/* 239 */       Object desObj = null;
/*     */       try {
/* 241 */         desObj = cls.newInstance();
/*     */       } catch (Exception e) {
/* 243 */         e.printStackTrace();
/*     */       }
/* 245 */       copy(desObj, orgObj);
/* 246 */       result.add(desObj);
/*     */     }
/* 248 */     return result;
/*     */   }
/*     */ 
/*     */   public static <T> List<T> toObjList(Class<T> resultClass, List<Map> list) {
/* 252 */     List result = new ArrayList();
/* 253 */     for (Map resultMap : ListUtil.safe(list)) {
/*     */       try
/*     */       {
/* 256 */         Object obj = toObject(resultMap, resultClass);
/*     */ 
/* 258 */         result.add(obj);
/*     */       }
/*     */       catch (Exception e) {
/* 261 */         e.printStackTrace();
/* 262 */         throw new DAOSystemException(e);
/*     */       }
/*     */     }
/*     */ 
/* 266 */     return result;
/*     */   }
/*     */ 
/*     */   public static List<Map<String, Object>> listMapKeyToLowerCase(List<Map<String, Object>> list)
/*     */   {
/* 277 */     List listNew = new ArrayList();
/* 278 */     for (Map map : list) {
/* 279 */       listNew.add(MapUtil.mapKeyToLowerCase(map));
/*     */     }
/* 281 */     return listNew;
/*     */   }
/*     */ 
/*     */   public static List<Map<String, Object>> listMapKeyToUpperCase(List<Map<String, Object>> list)
/*     */   {
/* 292 */     List listNew = new ArrayList();
/* 293 */     for (Map map : list) {
/* 294 */       listNew.add(MapUtil.mapKeyToUpperCase(map));
/*     */     }
/* 296 */     return listNew;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.common.util.convert.Converts
 * JD-Core Version:    0.6.2
 */