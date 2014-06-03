package com.ztesoft.inf.extend.freemarker;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import com.ztesoft.inf.communication.client.Invoker;

import freemarker.template.Configuration;
import freemarker.template.Template;

@SuppressWarnings("unchecked")
public class TplParamInstEx implements ITplParam {

	private static Configuration conf;
	private static int tempSeq = 1;
	private static Map utils = new HashMap();

	public Template createTemplate(String content) throws Exception {
		return new Template("ID:" + (tempSeq++), new StringReader(content),
				conf);
	}

	public static Map getUtilMethods() {
		return utils;
	}

	public void addUtils(Map root) {

		root.put("ut", utils);
	}

	public void addInvokerInfo(Map root, Invoker invoker) {
		Map invoke = new HashMap();
//		invoke.put("transactionIdGen", new ExchangeIdGenMethod(invoker
//				.getReqUser().getUser_code()));
		invoke.put("reqTimeGen", new ReqTimeGenMethod());
		invoke.put("appKey", invoker.getReqUser().getUser_code());
		
		root.put("inf", invoke);
	}
}
