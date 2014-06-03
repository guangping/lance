package com.ztesoft.inf.communication.client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ztesoft.common.util.ExpressUtil;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.common.util.web.HttpUtils;
import com.ztesoft.inf.framework.commons.CodedException;

public class HttpUrlInvoker extends Invoker {

	@Override
	public Object invoke(InvokeContext context) throws Exception {
		try {
			context.setEndpoint(endpoint);
			context.setRequestTime(DateUtil.currentTime());			
			String reqString = generateRequestString(context);
			context.setRequestString(reqString);
			String url = context.getEndpoint();
			if (!StringUtils.isEmpty(reqString)) {
				url = url + "?" + reqString;
			}
			context.setResponeString(HttpUtils.getContentByUrl(url, timeout));
			StringUtils.printInfo("\n\n reqStr=\n" + reqString
					+ "\n\n rspStr=\n" + context.getResponeString());
		} catch (Exception e) {
			context.setFailure(e.getMessage());
			throw new CodedException("9003", "HTTP请求["
					+ context.getOperationCode() + "]失败", e);
		} finally {
			context.setResponseTime(DateUtil.currentTime());
		}
		return context.getResponeString();
	}

	@Override
	protected String generateRequestString(InvokeContext context) {

		String uri = StringUtils.isEmptyDefault(reqTmpStr, "");
		Map encodedParams = dealURLEncoded((Map) context.getParameters(),
				"utf-8");
		String newUri = uri;
		try {
			newUri = ExpressUtil.parse(uri, encodedParams).getExpree();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newUri;

	}

	private Map dealURLEncoded(Map<String, String> params,
			String request_charset) {
		Map retMap = new HashMap();
		if (params == null) {
			return retMap;
		}

		for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			String value = params.get(key);
			try {

				retMap.put(key, URLEncoder.encode(StringUtils.safe(value),
						request_charset));
			} catch (UnsupportedEncodingException e) {
				retMap.put(key, value);
				e.printStackTrace();
			}
		}

		return retMap;
	}

}
