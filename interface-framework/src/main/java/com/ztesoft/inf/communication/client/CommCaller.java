package com.ztesoft.inf.communication.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztesoft.common.util.StringUtils;
import com.ztesoft.config.ParamsConfig;
import com.ztesoft.inf.communication.client.bo.CommClientBO;
import com.ztesoft.inf.framework.cache.Cache;
import com.ztesoft.inf.framework.cache.CacheConstants;
import com.ztesoft.inf.framework.cache.CacheItemCreateCallback;
import com.ztesoft.inf.framework.cache.CacheManager;
import com.ztesoft.inf.framework.cache.LRUMap;
import com.ztesoft.inf.framework.commons.CodedException;
import com.ztesoft.inf.framework.logger.AppLogContext;
import com.ztesoft.inf.framework.logger.AppLogger;
import com.ztesoft.inf.framework.logger.LogThread;

/**
 * 通用客户端
 */

@SuppressWarnings("unchecked")
public class CommCaller {
	private static Logger logger = Logger.getLogger(CommCaller.class);
	static{
		ParamsConfig.initParams(null);
	}
	private static final String LOGLEVEL_NONE = "NONE";
	private static final String LOGLEVEL_ERROR = "ERROR";
	private static final String LOGLEVEL_ALL_NOXML = "ALL_NOXML";
	//
	private static Cache<String, Invoker> invokers = CacheManager.getCache(CacheConstants.WSCLIENT_INVOKER, LRUMap.class);

	private static InvokerBuilder invokerBuilder = new InvokerBuilder();


	/**
	 * @param operationCode
	 *            要调用的操作(在INF_WS_CLIENT_OPERATION配置)
	 * @param params
	 *            调用操作的参数,该参数为一个Bean或Map对象
	 * @return
	 */
	public Object invoke(String operationCode, Object params) {
		return invoke(operationCode, params, null);
	}
//	public Object invokeToXml(String operationCode, Object params) {
//		return invokeToXml(operationCode, params, null);
//	}
	/**
	 * @param operationCode
	 *            要调用的操作(在INF_WS_CLIENT_OPERATION配置)
	 * @param params
	 *            调用操作的参数,该参数为一个Bean或Map对象
	 * @param endpointAddress
	 *            外部传入的请求地址
	 * @return
	 */
	public Object invoke(String operationCode, Object params,
			String endpointAddress) {
		Invoker invoker = getInvoker(operationCode);

		if (invoker == null) {
			StringUtils.printInfo("operation [" + operationCode
					+ "] is closed !");
			return InfCloed.getInstance();
		}
		// 外部请求地也址
		if (!StringUtils.isEmpty(endpointAddress)) {
			invoker.endpoint = endpointAddress;
		}
		InvokeContext context;

		if (invoker instanceof WsInvoker) {
			context = new WsInvokeContext();
		} else if (invoker instanceof StringObjectInvoker) {
			context = new WsInvokeContext();
		} else if (invoker instanceof AsmxWebInvoker) {
			context = new AsmxWebInvokeContext();
		} else {
			context = new InvokeContext();
		}

		context.setParameters(params);
		context.setOperationCode(operationCode);
		Object retObj;

		try {
			if (ParamsConfig.isDebug()) {
				retObj = invoker.invokeTest(context);
			} else {
				retObj = invoker.invoke(context);
			}
			logger.debug("req= "+ context.getRequestString());
			logger.debug("rsp= "+ context.getResultString());
			logger.debug("rsp= "+ context.getResponeString());

		} catch (Exception e) {
			context.setFailure(e.getMessage());
			throw new CodedException("9999", "调用服务[" + operationCode + "]出错:"
					+ e.getMessage(), e);
		} finally {
			try {
				logServiceCall(invoker.getLogLevel(), invoker.getLogColMap(),
						context);
			} catch (Exception e) {
				logger.error("记录调用日志出错[" + context.getOperationCode() + "]", e);
			}

		}
		return retObj;
	}
	
	public static class CallLogger {

		static CommClientBO wsClientBO = new CommClientBO();

		public void log(String logLevel, Map logColMap, InvokeContext context)
				throws Exception {
			String fault = context.getFailure();
			if (fault == null && LOGLEVEL_ERROR.equals(logLevel)) {
				return;
			}
			List list = new ArrayList();
			list.add(context.getRequestTime());
			list.add(context.getResponseTime());
			list.add(context.getOperationCode());
			list.add(context.getEndpoint());
			if (fault == null && LOGLEVEL_ALL_NOXML.equals(logLevel)) {
				list.add("");
				list.add("");
			} else {
				list.add(context.getRequestString());
				list.add(context.getResponeString());
			}
			list.add(fault != null ? "1" : "0");
			list.add(fault);
			list.add(context.paramsAsString());
			list.add(context.getResultString());
			list.add(getLogValue(logColMap, "col1", context.getParameters()));
			list.add(getLogValue(logColMap, "col2", context.getParameters()));
			list.add(getLogValue(logColMap, "col3", context.getParameters()));
			list.add(getLogValue(logColMap, "col4", context.getParameters()));
			list.add(getLogValue(logColMap, "col5", context.getParameters()));
			wsClientBO.logCall(list);
		}

		private Object getLogValue(Map logColMap, String colName,
				Object parameters) {
			Object value = null;
			if (parameters instanceof Map) {
				if (logColMap.get(colName) != null)
					value = ((Map) parameters).get(logColMap.get(colName));
			}
			return value != null ? String.valueOf(value) : "";
		}
	}

	// 线程池插入日志 add by xiaof
	private void logServiceCall(final String logLevel, final Map logColMap,
			final InvokeContext context) throws Exception {
		if (LOGLEVEL_NONE.equals(logLevel)) {
			return;
		}
		this.log(logLevel, logColMap, context);
	}

	private void log(String logLevel, Map logColMap, InvokeContext context)
			throws Exception {
		String fault = context.getFailure();
		if (fault == null && LOGLEVEL_ERROR.equals(logLevel)) {
			return;
		}
		final List list = new ArrayList();
		list.add(context.getRequestTime());
		list.add(context.getResponseTime());
		list.add(context.getOperationCode());
		list.add(context.getEndpoint());
		if (fault == null && LOGLEVEL_ALL_NOXML.equals(logLevel)) {
			list.add("");
			list.add("");
		} else {
			list.add(context.getRequestString());
			list.add(context.getResponeString());
		}
		list.add(fault != null ? "1" : "0");
		list.add(fault);
		list.add(context.paramsAsString());
		list.add(context.getResultString());
		list.add(getLogValue(logColMap, "col1", context.getParameters()));
		list.add(getLogValue(logColMap, "col2", context.getParameters()));
		list.add(getLogValue(logColMap, "col3", context.getParameters()));
		list.add(getLogValue(logColMap, "col4", context.getParameters()));
		list.add(getLogValue(logColMap, "col5", context.getParameters()));

		LogThread.addLogQueue(new AppLogContext(new AppLogger() {
			public void log(Object logObj) throws Exception {
				try {
					new CommClientBO().logCall((List) logObj);
				} catch (Throwable e) {
					e.printStackTrace();
					logger.error("记录日志时失败:" + e.getMessage(), e);
				}
			}
		}, list));
	}

	private Object getLogValue(Map logColMap, String colName, Object parameters) {
		Object value = null;
		if (parameters instanceof Map) {
			if (logColMap.get(colName) != null)
				value = ((Map) parameters).get(logColMap.get(colName));
		}
		return value != null ? String.valueOf(value) : "";
	}

	private synchronized Invoker getInvoker(final String operationCode) {
		try {
			Invoker invoker = invokers.get(operationCode,
					new CacheItemCreateCallback<Invoker>() {
						public Invoker create() throws Exception {
							return invokerBuilder.buildInvoker(operationCode);
						}
					});
			return invoker;
		} catch (Exception e) {
			throw new CodedException("9999", "创建Service[" + operationCode
					+ "]调用客户端失败", e);
		}
	}
	public Object invokeCreateResXml(String operationCode, Object params,
			String endpointAddress) {
		new ParamsConfig().initParams("");
		Invoker invoker = getInvoker(operationCode);

		if (invoker == null) {
			StringUtils.printInfo("operation [" + operationCode
					+ "] is closed !");
			return InfCloed.getInstance();
		}
		// 外部请求地也址
		if (!StringUtils.isEmpty(endpointAddress)) {
			invoker.endpoint = endpointAddress;
		}
		InvokeContext context;

		if (invoker instanceof WsInvoker) {
			context = new WsInvokeContext();
		} else if (invoker instanceof StringObjectInvoker) {
			context = new WsInvokeContext();
		} else if (invoker instanceof AsmxWebInvoker) {
			context = new AsmxWebInvokeContext();
		} else {
			context = new InvokeContext();
		}

		context.setParameters(params);
		context.setOperationCode(operationCode);
		Object retObj;

		try {
			if (ParamsConfig.isDebug()) {
				retObj = invoker.invokeTestXml(context);
			} else {
				retObj = invoker.invokeTestXml(context);
			}
			logger.debug("req= "+ context.getRequestString());
			logger.debug("rsp= "+ context.getResultString());
			logger.debug("rsp= "+ context.getResponeString());

		} catch (Exception e) {
			context.setFailure(e.getMessage());
			throw new CodedException("9999", "调用服务[" + operationCode + "]出错:"
					+ e.getMessage(), e);
		}
//		} finally {
//			try {
//				logServiceCall(invoker.getLogLevel(), invoker.getLogColMap(),
//						context);
//			} catch (Exception e) {
//				logger.error("记录调用日志出错[" + context.getOperationCode() + "]", e);
//			}
//
//		}
		return retObj;
	}
	public Object invokeCreateResMap(String operationCode,String xml,
			String endpointAddress) {
		new ParamsConfig().initParams("");
		Invoker invoker = getInvoker(operationCode);

		if (invoker == null) {
			StringUtils.printInfo("operation [" + operationCode
					+ "] is closed !");
			return InfCloed.getInstance();
		}
		// 外部请求地也址
		if (!StringUtils.isEmpty(endpointAddress)) {
			invoker.endpoint = endpointAddress;
		}
		InvokeContext context;

		if (invoker instanceof WsInvoker) {
			context = new WsInvokeContext();
		} else if (invoker instanceof StringObjectInvoker) {
			context = new WsInvokeContext();
		} else if (invoker instanceof AsmxWebInvoker) {
			context = new AsmxWebInvokeContext();
		} else {
			context = new InvokeContext();
		}

		//context.setParameters(params);
		context.setResponeString(xml);
		context.setOperationCode(operationCode);
		Object retObj;

		try {
			if (ParamsConfig.isDebug()) {
				retObj = invoker.invokeTestToMap(context);
			} else {
				retObj = invoker.invokeTestToMap(context);
			}
			logger.debug("req= "+ context.getRequestString());
			logger.debug("rsp= "+ context.getResultString());
			logger.debug("rsp= "+ context.getResponeString());

		} catch (Exception e) {
			context.setFailure(e.getMessage());
			throw new CodedException("9999", "调用服务[" + operationCode + "]出错:"
					+ e.getMessage(), e);
		}
//		} finally {
//			try {
//				logServiceCall(invoker.getLogLevel(), invoker.getLogColMap(),
//						context);
//			} catch (Exception e) {
//				logger.error("记录调用日志出错[" + context.getOperationCode() + "]", e);
//			}
//
//		}
		return retObj;
	}

	
}
