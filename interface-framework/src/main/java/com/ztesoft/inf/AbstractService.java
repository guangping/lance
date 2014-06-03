package com.ztesoft.inf;

import java.io.StringReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.util.ListUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.util.DBUtil;
import com.ztesoft.ibss.common.util.SqlMapExe;
import com.ztesoft.inf.communication.client.CommCaller;
import com.ztesoft.inf.extend.xstream.XStream;
import com.ztesoft.inf.extend.xstream.mapper.MapperContext;
import com.ztesoft.inf.extend.xstream.mapper.MapperContextBuilder;
import com.ztesoft.inf.framework.commons.CodedException;
import com.ztesoft.inf.service.util.IConst;
/**
 * 公用父类
 * 有公用转换参数的方法都放在父类中实现
 * @author gong.zhiwei
 *
 */
public class AbstractService {
	/**
	 * 通用客户端
	 */
	protected CommCaller caller = new CommCaller();
	/**
	 * 参数类型转换
	 * @param map
	 * @return
	 */
	protected Map returnMap(Map map) {
		Map body = (Map) map.get("Body");
		Map response = (Map) body.get("exchangeResponse");
		String out = response.get("out").toString();
		XStream stream = new XStream();
		MapperContext mapperCtx = new MapperContext();
		Map retMap = (Map) stream.fromXML(out, mapperCtx);
		return retMap;
	}
	
	/**
	 * 参数类型转换
	 * @param map
	 * @return
	 */
	protected Map parseRetMap(Map map) {
		Map body = (Map) map.get("Body");
		Map response = (Map) body.get("handlerResponse");
		String ret = response.get("return").toString();
		
		
		
		XStream stream = new XStream();
		MapperContext mapperCtx = new MapperContext();
		Map retMap = (Map) stream.fromXML(ret, mapperCtx);
		StringReader read = new StringReader(ret);
	    InputSource source = new InputSource(read);

		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			
			document = (Document) saxReader.read(source);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		if(document != null){
		List list= document.selectNodes("/Info/ProdOfferList/ProdOfferInfo"); //解析套餐列表
			if(!ListUtil.isEmpty(list)){
				Element offerList = (Element)list.get(0);
				retMap.put("prodOfferInstId", offerList.elementText("ProdOfferInstId"));
				retMap.put("prodOfferCode", offerList.elementText("ProdOfferCode"));
				retMap.put("prodOfferName", offerList.elementText("ProdOfferName"));
			}
		}
		return retMap;
	}
	
	/*****
	 * 返回报文头处理
	 * @param param
	 * @return
	 */
	protected Map getHeader(Map param) {
		HashMap map=new HashMap();
		Map header = (Map) param.get("Header");
		
		Map response = (Map) header.get("Response");
		String code = (String) response.get("Code");
		String message = (String) response.get("Message");
		map.put("exchangeId", (String)header.get("ExchangeId"));
		map.put("code",code);
		map.put("message",message);
		return map;
	}
	protected String getHeaderValue(Map param,String key) {
		Map header = (Map) param.get("Header");
		String value = (String) header.get(key);
		return value;
	}
	
	protected Map getResponse(Map param) {
		Map retMap =new HashMap();
		Map header = (Map) param.get("Header");
		Map response = (Map) header.get("Response");
		//状态：000成功，其余为失败
		String code = (String) response.get("Code");
		
		//返回成功
		if (code != null && code.equals("0000")) {
			//返回描述信息
			String message = (String) response.get("Message");
			//获取订单信息
			retMap = getOrderItem(param);
			retMap.put("exchangeId", (String)header.get("ExchangeId"));
			retMap.put("code",code);
			retMap.put("message",message);
			
		//返回失败的时候
		}else {
			//返回描述信息
			String message = (String) response.get("Message");
			retMap.put("exchangeId", (String)header.get("ExchangeId"));
			retMap.put("code",code);
			retMap.put("message",message);
			try{
				String log_id = (String)param.get(IConst.LOG_ID);
				retMap.put(IConst.LOG_ID,log_id);
			}catch (Exception e) {}
		}
		return retMap;
	}
	
	
	/*****
	 * 获取订单信息及客户订单信息
	 * @param param
	 * @return
	 */
	protected Map getOrderItem(Map param) {
		Map retMap = new HashMap();
		Map custOrderMap = (Map) param.get("CustOrder");
		/*
		String cust_order_id = (String) custOrderMap.get("cust_order_id");
	
		retMap.put("cust_order_id", cust_order_id);                       //客户订单Id
		retMap.put("cust_id", (String) custOrderMap.get("cust_id"));      //客户ID
		*/
		//判断是否存在多个列表
		Object obj =  custOrderMap.get("OrderItem");
		List retList = new ArrayList();
		if(obj!= null) {
			if (obj instanceof List || obj.getClass().equals("java.util.List")
					|| obj.getClass().equals("java.util.ArrayList")) {
				List list = (List) obj;
				
				for(int i = 0; i < list.size(); i++) {
					Map maps = (Map) list.get(i);
					
					maps.put("cust_id", custOrderMap.get("cust_id"));      //客户ID
					retList.add(maps);
				}
			//Map的情况
			}else {
				Map map = (Map) obj;
				map.put("cust_id", custOrderMap.get("cust_id"));      //客户ID
				retList.add(map);
			}
			
			retMap.put("OrderItem", retList);
		}
		/*
		retMap.put("order_item_id", (String) orderItemMap.get("order_item_id"));  //订单项ID
		retMap.put("ask_id", (String) orderItemMap.get("ask_id"));      
		retMap.put("service_offer_id", (String) orderItemMap.get("service_offer_id"));   //业务服务ID
		retMap.put("status", (String) orderItemMap.get("status"));                //订单状态：200:已审核待确认，500:开通中 ， 800：竣工中 999：已竣工
		retMap.put("status_date", (String) orderItemMap.get("status_date"));      //订单受理时间
		retMap.put("staff_id", (String) orderItemMap.get("staff_id"));            //受理工号
		retMap.put("region_id", (String) orderItemMap.get("region_id"));          //区域ID
		retMap.put("lan_id", (String) orderItemMap.get("lan_id"));               //本地网ID
		retMap.put("order_item_obj_id", (String) orderItemMap.get("order_item_obj_id"));  
		retMap.put("order_item_obj_type_id", (String) orderItemMap.get("order_item_obj_type_id"));  
		 */
		
		return retMap;
	}
	
	
	
	/******
	 * 查询接口处理返回
	 * @param param
	 * nodeName：父节点
	 * chileNodeListName：子节点
	 * @return
	 */
	protected Map getQueryList(Map param,String nodeName,String chileNodeListName) {
		Map retMap = new HashMap();
		Map repMap = (Map) param.get(nodeName);

		//判断是否存在多个列表
		Object obj =  repMap.get(chileNodeListName);
		List retList = new ArrayList();
		if(obj!= null) {
			if (obj instanceof List || obj.getClass().equals("java.util.List")
					|| obj.getClass().equals("java.util.ArrayList")) {
				List list = (List) obj;
				
				for(int i = 0; i < list.size(); i++) {
					Map maps = (Map) list.get(i);
					
					retList.add(maps);
				}
			//Map的情况
			}else {
				Map map = (Map) obj;
				
				retList.add(map);
			}
			
			retMap.put(chileNodeListName, retList);
		}
			
		return retMap;
	}
	/**
	 * 参数类型转换
	 * @param map
	 * @return
	 */
	protected Map returnMap(Map map,String operationCode) {
		Map body = (Map) map.get("Body");
		Map response = (Map) body.get("exchangeResponse");
		String out = response.get("out").toString();
		Map retMap = convertToMap(operationCode, out);
		Object inf_log_id =map.get("INF_LOG_ID");
		if (inf_log_id instanceof String) {
			retMap.put(IConst.LOG_ID, inf_log_id);
		}
		return retMap;
	}
	
	
	protected Map convertToMap(String operationCode, String out) {
		XStream stream = new XStream();
		MapperContext mapperCtx = null;
		Connection conn=null;
		try {
			String sql="select b.xml_mapper  from inf_comm_client_operation a, inf_comm_client_response b where a.rsp_id = b.rsp_id  and a.op_code = ?";
			conn= DBUtil.getConnection();
			SqlMapExe sqlexe=new SqlMapExe(conn);
			String xml_mapper = sqlexe.querySingleValue(sql, operationCode);
			mapperCtx = new MapperContextBuilder().build(xml_mapper);
		} catch (FrameException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
		if (mapperCtx == null) {
			mapperCtx = new MapperContext();
		}
		Map retMap = (Map) stream.fromXML(out, mapperCtx);
		return retMap;
	}
	
	/**
	 * 区号转换为本地网标识
	 * @param cityCode:区号 如：0791
	 * @return lan_id :本地网标识
	 */
	public String getLanId(String cityCode){
		String lan_id = "1";
		if (cityCode.equals("0791")) {                              //南昌
			lan_id = "1";
		} else if (cityCode.equals("0792")) {                       //九江
			lan_id = "9";
		} else if (cityCode.equals("0793")) {                       //上饶
			lan_id = "8";
		} else if (cityCode.equals("0794")) {                       //抚州
			lan_id = "5";
		} else if (cityCode.equals("0795")) {                       //宜春
			lan_id = "7";
		} else if (cityCode.equals("0796")) {                       //吉安
			lan_id = "10";
		} else if (cityCode.equals("0797")) {                       //赣州
			lan_id = "11";
		} else if (cityCode.equals("0798")) {                       //景德镇
			lan_id = "4";
		} else if (cityCode.equals("0799")) {                       //萍乡
			lan_id = "6";
		} else if (cityCode.equals("0790")) {                       //新余
			lan_id = "3";
		} else if (cityCode.equals("0701")) {                       //鹰潭
			lan_id = "2";
		}
		return lan_id;
	}
	
	/**
	 * 根据CRM系统本地网ID获取本地网区号
	 * @param lanId
	 * @return
	 */
	public String getCityCode(String lanId){
		String cityCode = "";
		if (lanId.equals("1")) {                              //南昌
			cityCode = "0791";
		} else if (lanId.equals("9")) {                       //九江
			cityCode = "0792";
		} else if (lanId.equals("8")) {                       //上饶
			cityCode = "0793";
		} else if (lanId.equals("5")) {                       //抚州
			cityCode = "0794";
		} else if (lanId.equals("7")) {                       //宜春
			cityCode = "0795";
		} else if (lanId.equals("10")) {                       //吉安
			cityCode = "0796";
		} else if (lanId.equals("11")) {                       //赣州
			cityCode = "0797";
		} else if (lanId.equals("4")) {                       //景德镇
			cityCode = "0798";
		} else if (lanId.equals("6")) {                       //萍乡
			cityCode = "0799";
		} else if (lanId.equals("3")) {                       //新余
			cityCode = "0790";
		} else if (lanId.equals("2")) {                       //鹰潭
			cityCode = "0701";
		}else {
			cityCode = "";
		}
		return cityCode;
	}
	
	/**
	 * 统一执行相应的服务，在返回Map中增加执行成功或失败的标示	 * 
	 * @param operationCode 配置的接口编码
	 * @param param 传给接口的参数Map
	 * 
	 * @return Map 接口执行的结果Map	
	 */
	public Map excuteInvoke(String operationCode,Map param){
	    Map map = new HashMap();
        boolean failFlag = false;
        try{
            map = (Map) caller.invoke(operationCode, param);
        }catch(CodedException ex){
            failFlag = true;
            map.put("code", ex.getCode());
            map.put("msg", ex.getMessage());
        }catch(Exception ex){
            failFlag = true;
            map.put("code","-1");
            map.put("msg", ex.getMessage());
        }finally{
            if(!failFlag){
                map.put("code","0");
                map.put("msg", "执行成功");
            }
        }
        return map;
	}

    public CommCaller getCaller() {
        return caller;
    }

}

