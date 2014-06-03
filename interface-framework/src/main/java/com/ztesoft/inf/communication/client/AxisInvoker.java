package com.ztesoft.inf.communication.client;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.ztesoft.common.util.Clazz;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.inf.framework.commons.CodedException;


public class AxisInvoker extends Invoker{

	String qnameEncoding;
	String qname;
	String operClassPath;
	String operMethodName;
	String reqPath;
	String rspPath;
	@Override
	public Object invoke(InvokeContext context) throws Exception {
		// TODO Auto-generated method stub
		String operationCode = context.getOperationCode();
		Object param = context.getParameters();
		context.setEndpoint(endpoint);
		context.setRequestTime(DateUtil.currentTime());
		Object result = new Object();
		try {
			Object object = this.getClassObject(operClassPath);
			
			org.apache.axis.client.Stub _stub=(org.apache.axis.client.Stub)object;
		
			
			   if (timeout != null){
					_stub.setTimeout(timeout*1000);
			   }else{
					_stub.setTimeout(30*1000);
			   }
			   
            StringUtils.setObjectValue(_stub, "cachedEndpoint",new java.net.URL(endpoint));
            Method method=Clazz.findMethod(object.getClass(), operMethodName);
	        if(method==null){
	        	throw new CodedException("9999", "操作编码["+operationCode+"] 对应的["+reqPath+"]类方法["+operMethodName+"]");
	        }
	        result= method.invoke(object, param);
	        
	        context.setResponseTime(DateUtil.currentTime());
	        context.setRequestString(new XStream().toXML(param));
	        context.setResponeString(new XStream().toXML(result));
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	
	
	/**
	 * 获取请求对象
	 * @param operationCode
	 * @param context
	 * @return
	 * @throws Exception
	 */
	private Object getReqeust(String operationCode ,Map context) throws Exception{
		Object object = new Object();
		try {
			object = this.getClassObject(operationCode);
			if(object != null){
				Method [] methods = object.getClass().getMethods();
				for(Method method : methods){
					String setFieldName  = method.getName();
					if(setFieldName == null || !setFieldName.startsWith("set")){
						continue;
					}
					Iterator itor= context.entrySet().iterator();
					while(itor.hasNext()){
						Map.Entry map=(Map.Entry)itor.next();
						String key =String.valueOf(map.getKey());
						String setKey = "set"+key.substring(0,1).toUpperCase()+key.substring(1);
						if(setKey.equals(setFieldName)){
							method.invoke(object, map.getValue());
						}
					}
				}
			}
			
		}catch(Exception e){
			throw e;
		}
		return object;
	}
	
	private Object getClassObject(String classPath) throws Exception{
		Object object = new Object();
		try {
			if(classPath !=null && !"".equals(classPath)){
				object = Class.forName(classPath).newInstance();
			}else {
				throw new CodedException("9999", "操作编码["+classPath+"] 未能找到对应的类");
			}
		}catch(Exception e){
			throw e;
		}
		return object;
	}

	
}
