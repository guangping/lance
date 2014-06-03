package com.ztesoft.ibss.common.util;

import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.common.util.page.PageModel;
import org.apache.commons.beanutils.BeanUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Const {
	
	public final static String SYSDATE = DBTUtil.current(); //设置时间为系统时间
	/*
	 * 规则审核状态
	 * 00N 新建
		00X 无效
		00A 有效
		00M 审核中
	 */
	public static final String RULE_STATUS_00N = "00N";
	public static final String RULE_STATUS_00X = "00X";
	public static final String RULE_STATUS_00A = "00A";
	public static final String RULE_STATUS_00M = "00M";
	
	/*分额控制类型 NO “无份额控制“，
	MO “按金额控制”  ，
	PN “按产品数量控制 'CO'按地市控制
	不同份额控制方式，处理逻辑有区别*/
	public static final String CONTROL_NO = "NO";
	public static final String CONTROL_MO = "MO";
	public static final String CONTROL_PN = "PN";
	public static final String CONTROL_CO = "CO";
	
	public static final int USER_AUDIT_STATUS_NOT = -1;//不通过
	public static final int USER_AUDIT_STATUS_0 = 0;//未审核
	public static final int USER_AUDIT_STATUS_1 = 1;//通过
	
	//类型：0按月 1按周 2按天(暂时只做按月的规则)
	public static final int MEMBER_RULE_KIND_0 = 0;
	public static final int MEMBER_RULE_KIND_1 = 1;
	public static final int MEMBER_RULE_KIND_2 = 2;
	
	/**
	 * 供货商管理员
	 */
	public static final String ADMIN_RELTYPE_SUPPER = "SUPPER";
	/**
	 * 供货商员工
	 */
	public static final String ADMIN_RELTYPE_SUPPER_STAFF = "SUPPER_STAFF";
	/**
	 * 电信部门
	 */
	public static final String ADMIN_CHINA_TELECOM_SUPPLIER_ID = "-1";
	
	/**
	 * 普通会员
	 */
	public static final String MEMBER_LV_COMMON = "0";
	/**
	 * 经销商
	 */
	public static final String MEMBER_LV_SUPPLIER = "2";
	/**
	 * 电信部门
	 */
	public static final String MEMBER_LV_CHINA_TELECOM_DEP = "3";
	/**
	 * 电信员工
	 */
	public static final String MEMBER_LV_CHINA_TELECOM_STAFF = "1";
	
	public static final String SESSION_MEMBER_LV = "SESSION_MEMBER_LV";
	public static final String COMMONAGE_LV = MEMBER_LV_COMMON;
	
	public static final String ACTION_METHOD = "execMethod" ;
	
	public static final String ACTION_RESULT = "RESULT" ;

	public static final String ACTION_PARAMETER = "PARAMETER" ;
	
	public static final String ACTION_ERROR = "ERROR" ;
	
	
	public static final String PAGE_PAGESIZE = "page_size" ;
	public static final String PAGE_PAGEINDEX = "page_index" ;
	public static final String PAGE_PAGECOUNT = "page_count" ;
	public static final String PAGE_TOTALCOUNT = "total_count" ;
	public static final String PAGE_DATALIST = "data_list" ;
	public static final String PAGE_MODEL = "PAGE_MODEL" ;
	
	public static final String PAGE_MAX_SIZE = "100" ;//每页最大数量
	public static final int UN_JUDGE_ERROR = -999999999 ;  //这种异常就跳过，不需要处理。
	
	//系统项目编码如yn,gx等  
	public static String YN_PROJECT_CODE = "yn";
	public static String GX_PROJECT_CODE = "gx";
	public static String GZ_PROJECT_CODE = "gz";
	public static String HA_PROJECT_CODE = "ha";
	public static String JX_PROJECT_CODE = "jx";
	
	//错误级别，弹出到页面上：10000表示提示信息，9999表示异常信息
	public static final int ALERT_LEVEL = 10000;
	public static final int ERROR_LEVEL = 9999;
	/**
	 * 系统省份编码
	 * @return
	 */
	public static String getProjectCode(){
		return getSystemInfo("PROVINCE_CODE");
	}
	/**
	 * 得到系统配置文件ibss.xml中system节点中配置的系统信息
	 * @param str
	 * @return
	 */
	public static String getSystemInfo(String str){

		return "";
	}

	
	private static Map getEmptyMap(){
		return new HashMap() ;
	}
	
	public static int getPageSize(Map m ){
		return ((Integer)m.get("pageSize")).intValue() ;
	}
	
	public static int getPageIndex(Map m ){
		return ((Integer)m.get("pageIndex")).intValue() ;
	}
	
	/**
	 * 根据tStr字段 构建新map
	 * @param sm  原map
	 * @param tStr 从原map，根据tStr字段作为key，构建需要的map
	 * @return
	 */
	public static Map getMapForTargetStr(Map sm , String tStr){
		if(sm == null || sm.isEmpty() ||
				tStr == null || "".equals(tStr.trim()))
			return getEmptyMap() ; 
		Map tm = new HashMap() ;
		Iterator it = sm.keySet().iterator() ;
		String[] tStrArray = tStr.split(",") ;
		for(int i = 0 , j= tStrArray.length ; i< j ; i++) {
			String n = tStrArray[i];
			if (null != n && sm.get(n.trim()) != null) {
				tm.put(n, (String)sm.get(n));
			}
		}
		return tm ;
	}
	
	public static boolean containValue(Map m , String name ){
		return m.get(name) != null ;
	}
	
	public static  boolean containStrValue(Map m , String name) {
		if( !containValue( m , name ) )
			return false ;
		String t = (String)m.get(name) ;
		return t != null && !"".equals(t.trim()) ;
	}
	
	public static  String getStrValue(Map m , String name) {
		
		if (null == m || m.isEmpty()) return "";
		
		Object t = m.get(name) ;
		if(t == null )
			return "" ;
		return (m.get(name)+"").trim() ;
	}
	
	public static  int getIntValue(Map m , String name) {
		Object t = m.get(name) ;
		if(t == null )
			return 0 ;
		String ret = ((String)m.get(name)).trim() ;
		return Integer.parseInt(ret) ;
	}
	


	
	/**
	 * PageModel对象转Map
	 * @param pm
	 * @return
	 * @throws com.powerise.ibss.framework.FrameException
	 */
	public static Map getPM(PageModel pm) throws FrameException {
		Map ret = new HashMap();
		ret.put(PAGE_TOTALCOUNT, "" + pm.getTotalCount());
		ret.put(PAGE_PAGEINDEX, "" + pm.getPageIndex());
		ret.put(PAGE_PAGESIZE, "" + pm.getPageSize());
		ret.put(PAGE_PAGECOUNT, "" + pm.getPageCount());
		ret.put(PAGE_DATALIST, pm.getList());
		return ret;
	}

	/**
	 * 一个bean以map的形式展示
	 * @param obj
	 * @return
	 * @throws com.powerise.ibss.framework.FrameException
	 */
	public static Map beanToMap(Object obj) throws FrameException {
		try {
			return BeanUtils.describe(obj);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * 把map的值设给bean，map的key须与bean的属性一一对应。
	 * @param obj
	 * @return
	 * @throws com.powerise.ibss.framework.FrameException
	 */
	public static void mapToBean(Object obj, Map param) throws FrameException {
		try {
			BeanUtils.populate(obj, param);
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
	}
	
	public static final Map<String,String> POINT_VALUE = new HashMap<String,String>();
	static{
		POINT_VALUE.put("buygoods", "购买商品");
		POINT_VALUE.put("email_check", "邮箱认证");
		POINT_VALUE.put("onlinepay", "在线支付");
		POINT_VALUE.put("comment_img", "带照片评论");
		POINT_VALUE.put("register", "注册会员");
		POINT_VALUE.put("register_link", "通过链接注册");
		POINT_VALUE.put("comment", "文字评论");
		POINT_VALUE.put("login", "登录");
		POINT_VALUE.put("exchange_coupon", "优惠券兑换");
		POINT_VALUE.put("order_pay_get", "订单支付");
		POINT_VALUE.put("operator_adjust", "管理员调整");
	}
	
	/**
	 * 获取积分获得途径的中文
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-5 
	 * @param key
	 * @return
	 */
	public static String getPointCNValue(String key){
		String value = POINT_VALUE.get(key);
		if(value==null){
			return key;
		}else{
			return value;
		}
	}
	
}
