package com.ztesoft.inf.extend.freemarker;

import java.util.List;

import com.ztesoft.common.util.date.DateUtil;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class ReqTimeGenMethod implements TemplateMethodModel {




	public Object exec(List arg0) throws TemplateModelException {
		return DateUtil.formatCurDate(DateUtil.DATE_TIME_FORMAT_14);
	}

}
