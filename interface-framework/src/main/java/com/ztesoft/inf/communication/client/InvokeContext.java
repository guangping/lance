package com.ztesoft.inf.communication.client;

import java.sql.Timestamp;

import com.ztesoft.common.util.convert.ObjectUtils;

public class InvokeContext {

	private Object parameters;
	private String resultString;
	private String requestString;
	private String responeString;
	private String failure;
	private String operationCode;
	private Timestamp requestTime;
	private Timestamp responseTime;
	private String endpoint;

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}

	public String getRequestString() {
		return requestString;
	}

	public void setRequestString(String requestString) {
		this.requestString = requestString;
	}

	public String getResponeString() {
		return responeString;
	}

	public void setResponeString(String responeString) {
		this.responeString = responeString;
	}

	public String getFailure() {
		return failure;
	}

	public void setFailure(String failure) {
		if (failure == null) {
			failure = "异常";
		}
		this.failure = failure;
	}

	public Object getParameters() {
		return parameters;
	}

	public void setParameters(Object parameters) {
		this.parameters = parameters;
	}

	public Timestamp getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
	}

	public Timestamp getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Timestamp responseTime) {
		this.responseTime = responseTime;
	}

	public String paramsAsString() {
		return ObjectUtils.toString(parameters);
	}

	public String getResultString() {
		return resultString;
	}

	public void setResultString(String resultString) {
		this.resultString = resultString;
	}
}
