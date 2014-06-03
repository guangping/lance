package com.ztesoft.inf.extend.freemarker;

import java.util.List;

import com.ztesoft.common.util.StringUtils;


import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class ByteLenlimitMethod implements TemplateMethodModel {

	public static final int LEFT_PAD = 1;
	public static final int RIGHT_PAD = 2;
	private int padType;

	public ByteLenlimitMethod(int padType) {
		this.padType = padType;
	}

	public Object exec(List args) throws TemplateModelException {
		if (padType != 0) {
			if (args.size() != 3) {
				throw new TemplateModelException(
						"调用ByteLenlimitMethod参数个数错误，应为3");
			}
		} else if (args.size() != 2) {
			throw new TemplateModelException("调用ByteLenlimitMethod参数个数错误，应为2");
		}
		String str = (String) args.get(0);
		Integer len = Integer.parseInt((String) args.get(1));
		if (str.getBytes().length > len) {
			throw new TemplateModelException("字符串长度(按字节)限制为" + len + ",{" + str
					+ "}不符合!");
		}
		if (padType != 0) {
			String pad = (String) args.get(2);
			if (padType == LEFT_PAD) {
				str = StringUtils.leftPad(str, len, pad.charAt(0));
			} else {
				str = StringUtils.rightPad(str, len, pad.charAt(0));
			}
		}
		return str;
	}
}
