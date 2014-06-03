package com.ztesoft.inf.extend.freemarker;

import java.util.List;

import com.seaway.util.UppUtil;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class Base64MD5Method implements TemplateMethodModel {

	public Object exec(List args) throws TemplateModelException {
		if (args.size() != 1) {
			throw new TemplateModelException("调用Base64MD5Method参数个数错误，应为1");
		}
		String content = (String) args.get(0);
		UppUtil upp = new UppUtil();
		return upp.base64Md5(content);
	}

}
