package com.ztesoft.inf.extend.freemarker;

import java.io.StringReader;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.common.util.Clazz;
import com.ztesoft.config.ParamsConfig;
import com.ztesoft.inf.communication.client.Invoker;

import freemarker.template.Template;

@SuppressWarnings("unchecked")
public class TemplateUtils {
	private static ITplParam tpl = getInstance();

	public static ITplParam getInstance() {

		String clazzStr = ParamsConfig.getInstance().getParamValue(
				"TemplateParamClass");
		if (!StringUtils.isEmpty(clazzStr)) {
			tpl = (ITplParam) Clazz.newInstance(clazzStr);
		}
		if (tpl == null) {
			tpl = new TplParamInstEx();
		}

		return tpl;
	}

	public static void addUtils(Map root) {
		tpl.addUtils(root);
	}

	public static Template createTemplate(String content) throws Exception {
		return tpl.createTemplate(content);
	}

	public static void addInvokerInfo(Map root, Invoker invoker) {
		tpl.addInvokerInfo(root, invoker);
	}
}
