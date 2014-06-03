package com.ztesoft.inf.communication.client;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ztesoft.common.util.StringUtils;
import com.ztesoft.inf.communication.client.bo.CommClientBO;
import com.ztesoft.inf.communication.client.vo.ClientEndPoint;
import com.ztesoft.inf.communication.client.vo.ClientGlobalVar;
import com.ztesoft.inf.communication.client.vo.ClientOperation;
import com.ztesoft.inf.communication.client.vo.ClientRequest;
import com.ztesoft.inf.communication.client.vo.ClientRequestUser;
import com.ztesoft.inf.communication.client.vo.ClientResponse;
import com.ztesoft.inf.extend.freemarker.TemplateUtils;
import com.ztesoft.inf.extend.xstream.mapper.MapperContext;
import com.ztesoft.inf.extend.xstream.mapper.MapperContextBuilder;
import com.ztesoft.inf.framework.commons.CodedException;

import freemarker.template.Template;

public class InvokerBuilder {

	private CommClientBO commBO = new CommClientBO();
	private MapperContextBuilder mapperCtxBuilder = new MapperContextBuilder();
	private Pattern nsPattern = Pattern.compile("^namespace\\.(\\w+)=(.+)");

	public Invoker buildInvoker(String operationCode) throws Exception {
		ClientOperation operation = commBO.getOperationByCode(operationCode);
		if (operation == null) {
			throw new CodedException("9001", "该操作调用[" + operationCode
					+ "]未进行配置,请检查!");
		}
		return buildInvoker(operation);
	}

	private Invoker buildInvoker(ClientOperation operation) throws Exception {

		if (operation.isClosed()) {
			return null;
		}

		ClientEndPoint ep = commBO.getEndPoint(operation.getEndpointId());

		ClientRequestUser reqUser = commBO.getRequestUser(operation
				.getReqUserId());

		if (ep == null) {
			throw new CodedException("", "该操作[" + operation.getOperationCode()
					+ "]调用请求地址[" + operation.getEndpointId() + "]未进行配置,请检查!");
		}

		ClientRequest request = commBO.getRequest(operation.getRequestId());

		if (request == null) {
			throw new CodedException("", "该操作[" + operation.getOperationCode()
					+ "]调用请求报文[" + operation.getRequestId() + "]未进行配置,请检查!");
		}

		ClientResponse response = commBO.getResponse(operation.getResponseId());
		if (response == null) {
			throw new CodedException("", "该操作[" + operation.getOperationCode()
					+ "]调用反馈报文[" + operation.getResponseId() + "]未进行配置,请检查!");
		}

		ClientGlobalVar gvars = commBO.getGlobalVars(request.getGlobalVarsId());

		Invoker invoker = null;

		if ("SOCKET".equalsIgnoreCase(ep.getType())) {
			invoker = new SocketInvoker();
		} else if ("HttpUrl".equalsIgnoreCase(ep.getType())) {
			invoker = new HttpUrlInvoker();
			invoker.reqTmpStr=request.getTemplate();
		} else if ("HttpClient".equalsIgnoreCase(ep.getType())) {
			invoker = new HttpClientInvoker();
		} else if ("HttpJson".equalsIgnoreCase(ep.getType())) {
			invoker = new HttpJsonInvoker();
		} else if ("ASMXWEB".equalsIgnoreCase(ep.getType())) {
			invoker = new AsmxWebInvoker();
		} else if ("RMI".equalsIgnoreCase(ep.getType())) {
			invoker = new RMIInvoker();
		} else if ("Object".equalsIgnoreCase(ep.getType())) {
			invoker = new ObjectInvoker();
		} else if ("Axis".equalsIgnoreCase(ep.getType())) {
			invoker = new AxisInvoker();
			AxisInvoker objectInvoker = (AxisInvoker) invoker;
			objectInvoker.reqPath = request.getClassPath();
			objectInvoker.operClassPath = request.getOperClassName();
			objectInvoker.operMethodName = request.getOperMethod();
			objectInvoker.qname = request.getQname();
			objectInvoker.qnameEncoding = request.getQnameEncode();
			objectInvoker.rspPath = response.getRspPath();
			objectInvoker.endpoint = ep.getEndpointAddress();
		} else if ("StringObject".equalsIgnoreCase(ep.getType())) {
			invoker = new StringObjectInvoker();
		} else {
			invoker = new WsInvoker();
		}

		invoker.endpoint = ep.getEndpointAddress();
		invoker.timeout = ep.getTimeout();
		invoker.transformFault = response.isTransformFault();
		invoker.logColMap = commBO.getLogColsByOpId(operation.getOperationId());
		invoker.requestTemplate = buildRequestTemplate(request);
		invoker.transformTemplate = buildTransformTemplate(response);
		invoker.mapperContext = buildMapperContext(response);
		invoker.globalVars = buildGlobalVars(gvars);
		invoker.setLogLevel(operation.getLogLevel());
		invoker.setReqUser(reqUser);
		invoker.setOperation(operation);
		
		if ("SOCKET".equalsIgnoreCase(ep.getType())) {
			SocketInvoker socketInvoker = (SocketInvoker) invoker;
			String endpoint = ep.getEndpointAddress();

			try {
				String[] parts = endpoint.split(":");
				socketInvoker.endpoint = parts[0];
				socketInvoker.port = Integer.parseInt(parts[1]);
			} catch (Exception e) {
				throw new CodedException("9999", "SOCKET地址错误，地址格式为IP:PORT");
			}
		} else if ("ASMXWEB".equalsIgnoreCase(ep.getType())) {
			invoker.endpoint = ep.getEndpointAddress();
			buildCdataInfo(response, (AsmxWebInvoker) invoker);
		} else if ("RMI".equalsIgnoreCase(ep.getType())) {
			RMIInvoker rmiInvoker = (RMIInvoker) invoker;
		} else if ("Axis".equalsIgnoreCase(ep.getType())) {
			AxisInvoker rmiInvoker = (AxisInvoker) invoker;
		} else if ("Object".equalsIgnoreCase(ep.getType())) {
			ObjectInvoker objectInvoker = (ObjectInvoker) invoker;
			objectInvoker.reqPath = request.getClassPath();
			objectInvoker.operClassPath = request.getOperClassName();
			objectInvoker.operMethodName = request.getOperMethod();
			objectInvoker.qname = request.getQname();
			objectInvoker.qnameEncoding = request.getQnameEncode();
			objectInvoker.rspPath = response.getRspPath();
			invoker.endpoint = ep.getEndpointAddress();
			objectInvoker.rspType = response.getRspType(); // add by wen.jinyang
															// 2012-08-23
		} else if ("StringObject".equalsIgnoreCase(ep.getType())) {
			buildCdataInfo(response, (StringObjectInvoker) invoker);
		} else if ("WsInvoker".equalsIgnoreCase(ep.getType())) {
			buildCdataInfo(response, (WsInvoker) invoker);

		}
		return invoker;
	}

	private void buildCdataInfo(ClientResponse response, AsmxWebInvoker invoker) {
		String cdata = response.getCdataPath();
		if (StringUtils.isEmpty(cdata)) {
			return;
		}

		String[] lines = StringUtils.splitLines(cdata);
		invoker.cdataNamespaces = new HashMap();

		for (String line : lines) {
			Matcher matcher = nsPattern.matcher(line);
			if (matcher.matches()) {
				invoker.cdataNamespaces.put(matcher.group(1), matcher.group(2));
			} else {
				invoker.cdataPath = line;
			}
		}
	}

	private Map buildGlobalVars(ClientGlobalVar gvars) {
		Map globalVars = new HashMap();
		if (gvars != null && !StringUtils.isEmpty(gvars.getGvarsDefinition())) {
			globalVars = StringUtils.splitLinesIntoMap(
					gvars.getGvarsDefinition(), "=");
		}
		return globalVars;
	}

	private void buildCdataInfo(ClientResponse response, WsInvoker invoker) {
		String cdata = response.getCdataPath();
		if (StringUtils.isEmpty(cdata)) {
			return;
		}

		String[] lines = StringUtils.splitLines(cdata);
		invoker.cdataNamespaces = new HashMap();

		for (String line : lines) {
			Matcher matcher = nsPattern.matcher(line);
			if (matcher.matches()) {
				invoker.cdataNamespaces.put(matcher.group(1), matcher.group(2));
			} else {
				invoker.cdataPath = line;
			}
		}
	}

	private void buildCdataInfo(ClientResponse response,
			StringObjectInvoker invoker) {
		String cdata = response.getCdataPath();
		if (StringUtils.isEmpty(cdata)) {
			return;
		}

		String[] lines = StringUtils.splitLines(cdata);
		invoker.cdataNamespaces = new HashMap();

		for (String line : lines) {
			Matcher matcher = nsPattern.matcher(line);
			if (matcher.matches()) {
				invoker.cdataNamespaces.put(matcher.group(1), matcher.group(2));
			} else {
				invoker.cdataPath = line;
			}
		}
	}

	private MapperContext buildMapperContext(ClientResponse response) {
		if (StringUtils.isEmpty(response
				.getXmlMapper())) {
			return new MapperContext();
		}
		return mapperCtxBuilder.build(response.getXmlMapper());
	}

	private Template buildTransformTemplate(ClientResponse response) {
		if (StringUtils.isEmpty(response.getTransform())) {
			return null;
		}
		try {
			return TemplateUtils.createTemplate(response.getTransform());
		} catch (Exception e) {
			StringUtils.printInfo("buildTransformTemplate=="
					+ response.getTransform());
			throw new CodedException("9999", "创建响应报文转换模板["
					+ response.getResponseId() + "]出错.", e);
		}
	}

	private Template buildRequestTemplate(ClientRequest request) {
		try {
			if (!StringUtils.isEmpty(request.getTemplate())) {
				return TemplateUtils.createTemplate(request.getTemplate());
			} else {
				return null;
			}
		} catch (Exception e) {
			StringUtils.printInfo("buildRequestTemplate="
					+ request.getTemplate());
			throw new CodedException("9999", "创建请求报文模板["
					+ request.getRequestId() + "]出错", e);
		}
	}
}
