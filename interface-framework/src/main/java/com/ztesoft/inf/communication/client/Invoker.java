package com.ztesoft.inf.communication.client;


import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import com.ztesoft.common.util.StringUtils;
import com.ztesoft.common.util.file.FileUtil;
import com.ztesoft.config.ParamsConfig;
import com.ztesoft.inf.communication.client.util.DomUtils;
import com.ztesoft.inf.communication.client.vo.ClientOperation;
import com.ztesoft.inf.communication.client.vo.ClientRequestUser;
import com.ztesoft.inf.extend.freemarker.TemplateUtils;
import com.ztesoft.inf.extend.xstream.XStream;
import com.ztesoft.inf.extend.xstream.mapper.MapperContext;
import com.ztesoft.inf.framework.commons.CachedNamespaceContext;
import com.ztesoft.inf.framework.commons.CodedException;

import freemarker.ext.dom.NodeModel;
import freemarker.template.Template;

public abstract class Invoker {

	String endpoint;
	String logLevel;
	Map globalVars;
	boolean transformFault;
	Integer timeout;
	Template requestTemplate;
	Template transformTemplate;
	MapperContext mapperContext;
	Map logColMap;
	ClientRequestUser reqUser;
	ClientOperation operation;
	String  reqTmpStr;

	private static Logger logger = Logger.getLogger(Invoker.class);

	public abstract Object invoke(InvokeContext context) throws Exception;

	public Object invokeTest(InvokeContext context) throws Exception {
		// 报文存放目录
		String testXmlPath = ParamsConfig.getInstance().getParamValue(
				"TestXmlPath");
		// 请求报文例子
		String reqStr = FileUtil.readFileContent(testXmlPath
				+ operation.getRequestId() + ".xml");
		logger.debug("reqXmlExm====>\n" + reqStr);

		// 参数拼装报文模板
		String reqString = generateRequestString(context);
		logger.debug("genReqXml====>\n" + reqString);
		context.setRequestString(reqString);

		// 返回报文例子
		String rspStr = FileUtil.readFileContent(testXmlPath
				+ operation.getResponseId() + ".xml");
		logger.debug("rspXmlExm====>\n" + rspStr);
		context.setResponeString(rspStr);
		if (StringUtils.isEmpty(rspStr)) {

			throw new Exception("没有返回报文" + testXmlPath
					+ operation.getResponseId() + ".xml");

		}
		// 返回报文例子解析结果
		Object result = dealResult(context);
		logger.debug("result====>\n" + result.toString());
		return result;

	};
	
	protected Object dealResult(InvokeContext context) {

		String resultStr = context.getResponeString();
		Document resultDoc = DomUtils.newDocument(resultStr, false);
		String cdataPath = null;
		Map dataNamespaces = null;
		Map cdataNamespaces = null;
		XStream xstream = XStream.instance();
		XPath xpath = XPathFactory.newInstance().newXPath();

		try {
			if (!StringUtils.isEmpty(cdataPath)) {
				CachedNamespaceContext nsCtx = new CachedNamespaceContext();
				nsCtx.putInCache(cdataNamespaces);
				xpath.setNamespaceContext(nsCtx);
				String cdataContent = xpath.evaluate(cdataPath, resultDoc);

				if (StringUtils.isEmpty(cdataContent)) {
					return new HashMap();
				}
				try {
					resultDoc = DomUtils.newDocument(cdataContent, false);
				} catch (CodedException e) {

					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			throw new CodedException("9002", "根据返回的SOAP报文取业务报文["
					+ context.getOperationCode() + "]时出错.", e);
		}

		Object result;
		StringWriter out = new StringWriter();
		try {
			if (transformTemplate != null) {
				Map rootMap = new HashMap();
				rootMap.put("doc", NodeModel.wrap(resultDoc));
				TemplateUtils.addUtils(rootMap);
				transformTemplate.process(rootMap, out);
				resultStr = out.toString();
				context.setResultString(resultStr);
			}
			result = xstream.fromXML(resultStr, mapperContext);

			System.out.println(result);
		} catch (Exception e) {
			throw new CodedException("9003", "根据反馈的业务报文转为目标格式["
					+ context.getOperationCode() + "]时出错.", e);
		}
		return result;
	}

	public Map getLogColMap() {
		return logColMap;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	public String getLogLevel() {
		return logLevel;
	}

	public ClientRequestUser getReqUser() {
		return reqUser;
	}

	public ClientOperation getOperation() {
		return operation;
	}

	public void setOperation(ClientOperation operation) {
		this.operation = operation;
	}

	public void setReqUser(ClientRequestUser reqUser) {
		this.reqUser = reqUser;
	}

	protected String generateRequestString(InvokeContext context) {

		Object params = context.getParameters();
		StringWriter out = new StringWriter();

		try {
			Map root = new HashMap();
			BeanUtils.copyProperties(root, params);

			if (globalVars != null) {
				root.put("_global", globalVars);
			}

			TemplateUtils.addUtils(root);
			TemplateUtils.addInvokerInfo(root, this);

			requestTemplate.process(root, out);

			String reqString = out.toString();
			context.setRequestString(reqString);

		} catch (Exception e) {
			throw new CodedException("9001", "根据模板["
					+ context.getOperationCode() + "]组装请求报文出错.", e);
		} finally {
			try {
				out.close();
			} catch (IOException e) {
			}
		}
		return out.toString();
	}
	public Object invokeTestXml(InvokeContext context) throws Exception {
		// 报文存放目录
		String testXmlPath = ParamsConfig.getInstance().getParamValue(
				"TestXmlPath");
		// 请求报文例子
		String reqStr = FileUtil.readFileContent(testXmlPath
				+ operation.getRequestId() + ".xml");
		logger.debug("reqXmlExm====>\n" + reqStr);

		// 参数拼装报文模板
		String reqString = generateRequestString(context);
		logger.debug("genReqXml====>\n" + reqString);
		context.setRequestString(reqString);
		return reqString;
	}
	public Object invokeTestToMap(InvokeContext context) throws Exception {
		// 报文存放目录
		String testXmlPath = ParamsConfig.getInstance().getParamValue(
				"TestXmlPath");
		// 请求报文例子
		String reqStr = FileUtil.readFileContent(testXmlPath//getResponeString
				+ operation.getRequestId() + ".xml");
		logger.debug("reqXmlExm====>\n" + reqStr);


		// 返回报文例子解析结果
		Object result = dealResult(context);

		return result;

	};

	
}
