package com.ztesoft.inf.framework.commons;

import com.ztesoft.common.util.StringUtils;

import java.lang.reflect.InvocationTargetException;

public class CodedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String code;
	private String message;
	private String errStackInfo;
	
	public CodedException(String code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}
	public CodedException(String code, String message, Throwable e) {
		super(refact(message, e), e);
		this.code=code;
		this.message=getMessage();
		if (e instanceof CodedException) {
			CodedException ce = (CodedException) e;
			this.code = ce.code;
		}
	}
	private static String refact(String _message, Throwable e) {
		String message = _message;
		if (e instanceof CodedException) {
			CodedException ce = (CodedException) e;
			if(!StringUtils.equals(message, ce.message)) {
				message = message + "," + ce.message;	
			}
		} else if (e instanceof InvocationTargetException) {
			Throwable te = ((InvocationTargetException) e).getTargetException();
			message = message + te != null ? te.getMessage() : "";
		} else {
			message = message + e.getMessage();
		}
		return message;
	}
	public String getCode() {
		return code;
	}
	public String toString() {
		return "{" + code + "}" + message;
	}
	public String getErrStackInfo() {
		return errStackInfo;
	}
	public void setErrStackInfo(String errStackInfo) {
		this.errStackInfo = errStackInfo;
	}
}

