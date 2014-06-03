package com.ztesoft.inf.communication.client.vo;

import java.util.Map;

public class ClientEndPoint {

	private String endpointId;
	private String endpointAddress;
	private String endpointDesc;
	private Integer timeout;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public ClientEndPoint() {
	}

	public ClientEndPoint(Map map) {
		setValues(map);
	}

	private void setValues(Map map) {
		endpointId = (String) map.get("ep_id");
		endpointAddress = (String)map.get("ep_address");
 			 //"http://localhost:8080/CrmWeb/services/exchangeSOAP";
		endpointDesc = (String) map.get("ep_desc");
		type = (String) map.get("ep_type");
		String _timeout = map.get("timeout").toString();
		if (_timeout != null)
			timeout = Integer.parseInt(_timeout);
	}

	public String getEndpointId() {
		return endpointId;
	}

	public void setEndpointId(String endpointId) {
		this.endpointId = endpointId;
	}

	public String getEndpointAddress() {
		return endpointAddress;
	}

	public void setEndpointAddress(String endpointAddress) {
		this.endpointAddress = endpointAddress;
	}

	public String getEndpointDesc() {
		return endpointDesc;
	}

	public void setEndpointDesc(String endpointDesc) {
		this.endpointDesc = endpointDesc;
	}
}
