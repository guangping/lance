package com.ztesoft.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;



public class MapUtil
{
	
	/**
	 * 一个bean以map的形式展示
	 * @param obj
	 * @return
	 * @throws FrameException
	 */
	public static Map beanToMap(Object obj) {
		try {
			return BeanUtils.describe(obj);
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		return null;
	}	
	
	public static Map listToMap(Iterator objects)
	{
		Map map = new HashMap();

		while (objects.hasNext())
		{
			String object = objects.next().toString();
			map.put(object, object);
		}
		return map;
	}

	public static Map arrayToMap(String[] objects)
	{
		Map map = new HashMap();

		for (String str : objects)
		{
			map.put(str, str);
		}

		return map;
	}

	public static boolean isEmpty(Map map)
	{
		if (null == map)
		{
			return true;
		}

		return map.isEmpty();
	}
	
	/**
	 * 将Map中为字符串的键全部转换为小写字母。注意：转换后的结果与参数不再是同一个对象，其地址不相同了，但内容还是一样。
	 * @param map
	 * @return Map:转换后的Map对象
	 */
	public static Map mapKeyToLowerCase(Map map)
	{
		if (null == map)
			return null;
		Map map_key_lower = new HashMap();
		Iterator it_map = map.keySet().iterator();
		while(it_map.hasNext())
		{
			Object obj = it_map.next();
			if(obj.getClass().getName().endsWith("String"))
				map_key_lower.put(((String)obj).toLowerCase(), map.get(obj));
			else
				map_key_lower.put(obj, map.get(obj));
		}
		return map_key_lower;
	}
	
	/**
	 * 将Map中为字符串的键全部转换为大写字母。注意：转换后的结果与参数不再是同一个对象，其地址不相同了，但内容还是一样。
	 * @param map
	 * @return Map:转换后的Map对象
	 */
	public static Map mapKeyToUpperCase(Map map)
	{
		if (null == map)
			return null;
		Map map_key_lower = new HashMap();
		Iterator it_map = map.keySet().iterator();
		while(it_map.hasNext())
		{
			Object obj = it_map.next();
			if(obj.getClass().getName().endsWith("String"))
				map_key_lower.put(((String)obj).toUpperCase(), map.get(obj));
			else
				map_key_lower.put(obj, map.get(obj));
		}
		return map_key_lower;
	}

	/**
	 * 
	 * 源Map合并到目标Map,不修改目标Map数据
	 * add by xiaof 110819
	 */
	public static Map mergeMap(Map sm, Map tm) {
		if (sm == null || sm.isEmpty() || tm == null || tm.isEmpty())
			return new HashMap();
		Map retmp = new HashMap();
		retmp.putAll(tm);
		retmp.putAll(mergeMapFromMap(sm,tm));
		return retmp;
	}

	/**
	 * 
	 * 根据源Map合并到目标Map,不返回源Map以外的数据,并不修改目标Map数据
	 * add by xiaof 110926
	 */
	public static Map mergeMapFromMap(Map sm, Map tm) {
		if (sm == null || sm.isEmpty() || tm == null || tm.isEmpty())
			return new HashMap();
		Map retmp = new HashMap();
		
		for (Iterator it = sm.keySet().iterator(); it.hasNext();) {
			String n = (String) it.next();
			if (tm.get(n) != null&&!"".equals(tm.get(n))) {
				retmp.put(n, (String) tm.get(n));
			} else {
				retmp.put(n, (String) sm.get(n));
			}
		}
		return retmp;
	}
	/**
	 * 
	 * 源Map合并到目标Map,直接修改目标Map数据
	 * add by xiaof 110926
	 */
	public static Map mapToTargetMap(Map sm, Map tm) {
		if (sm == null || sm.isEmpty() || tm == null || tm.isEmpty())
			return new HashMap();
	
		for (Iterator it = sm.keySet().iterator(); it.hasNext();) {
			String n = (String) it.next();
			if (tm.get(n) == null||"".equals(tm.get(n))) {
				tm.put(n, (String) sm.get(n));
			}
		}
 
		return tm;
	}
	/**
	 *
	 * 对比变动生成变动的Map 
	 * add by xiaof 111207
	 */
	public static Map compareToMap(Map newMap, Map oldMap) {
		if (newMap == null || newMap.isEmpty() || oldMap == null || oldMap.isEmpty())
			return new HashMap();
		Map retmp = new HashMap();
		
		for (Iterator it = newMap.keySet().iterator(); it.hasNext();) {
			String n = (String) it.next();
			
			if (newMap.get(n) != null&&newMap.get(n).equals(oldMap.get(n))) {
				continue;
			} else if(newMap.get(n)==null&&oldMap.get(n)==null ) {
				continue;
			}
			retmp.put(n, (String) newMap.get(n));
		}
		return retmp;
	}
	/**
	 *  对比两个Map是否相等 add by xiaof 111009
	 */
	public static boolean isEqual(Map o, Map c){
		if(o==null||c==null)return false;
		
		return o.equals(c); 
		/**逻辑重复 modify by xiaof
		 * if(o.equals(c)){return true;}
		Iterator <Entry>it=o.entrySet().iterator();
		
		int k = 0;
		int o_num = o.entrySet().size();
		int c_num = c.entrySet().size();
		if(o_num!=c_num){
			return false;
		}
        
		while(it.hasNext()){
        	Entry<String,Object> entry=it.next();
        	String key = entry.getKey();
        	Object o_value=entry.getValue();
        	Object c_value=c.get(key);
        	if(o_value==null&&o_value==c_value){
        		k+=1;
        		continue;
        	}else if(o_value!=null&&o_value.equals(c_value)){
        		k+=1;
        		continue;
        	}
        	return false;
        }
		if(c_num==k){
			return true;
		}
		return false;*/
	}
	/**
	 *  根据keys对比两个Map是否相等 add by xiaof 111009
	 */	
	public static boolean isEqual(Map o, Map c,String[] keys){
		if(o==null||c==null)return false;
		if (o == c)
		    return true;
		if(keys==null||keys.length==0){
			 o.equals(c);
		}
		if (!(o instanceof Map))
		    return false;

	        try {
	        	for (String mkey : keys) {
					Object oVal=o.get(mkey);
					if(oVal==null){
						if (!(c.get(mkey)==null && c.containsKey(mkey)))
	                        return false;
					}else{
	                    if (!oVal.equals(c.get(mkey)))
	                        return false;
					}
				} 
	        	
	        } catch (ClassCastException unused) {
	            return false;
	        } catch (NullPointerException unused) {
	            return false;
	        }

		return true;

	}

 

	public static boolean isObjEqual(Object o, Map c, String[] keys) {
		
		if(o==null||c==null)return false;
		if (o == c)
		    return true;
		if(keys==null||keys.length==0){
			 o.equals(c);
		}
	        try {
	        	for (String mkey : keys) {
					Object oVal=StringUtils.getObjectValue(o, mkey);
					if(oVal==null){
						if (!(c.get(mkey)==null && c.containsKey(mkey)))
	                        return false;
					}else{
	                    if (!oVal.equals(c.get(mkey)))
	                        return false;
					}
				} 
	        	
	        } catch (ClassCastException unused) {
	            return false;
	        } catch (NullPointerException unused) {
	            return false;
	        }

		return true;
	}

 
	public static Map safe(Map compParams) {
		if(compParams==null){
			return new HashMap();
		}
		
		return compParams;
	}

}
