package com.ztesoft.inf.communication.client.vo;

import java.util.Map;

public class ClientOperation {

	private String operationId;
	private String operationCode;
	private String endpointId;
	private String requestId;
	private String responseId;
	private String description;
	private String logLevel;
	private String reqUserId;
	private boolean closed;

	public ClientOperation() {
	}

	public ClientOperation(Map map) {
		setValues(map);
	}

	private void setValues(Map map) {
		operationId = (String) map.get("op_id");
		operationCode = (String) map.get("op_code");
		endpointId = (String) map.get("ep_id");
		requestId = (String) map.get("req_id");
		responseId = (String) map.get("rsp_id");
		description = (String) map.get("op_desc");
		logLevel = (String) map.get("log_level");
		closed = "T".equals(map.get("close_flag"));
		reqUserId = (String) ((map.get("req_user_id")==null||"".equals(map.get("req_user_id")))?"-1":map.get("req_user_id"));
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public String getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}

	public String getEndpointId() {
		return endpointId;
	}

	public void setEndpointId(String endpointId) {
		this.endpointId = endpointId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	public boolean isClosed() {
		return closed;
	}

	public String getReqUserId() {
		return reqUserId;
	}
	
}
