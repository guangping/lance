package com.ztesoft.inf.extend.freemarker;

import java.util.List;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.ibss.common.util.DateFormatUtils;
import com.ztesoft.inf.framework.dao.SeqUtil;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class ExchangeIdGenMethod implements TemplateMethodModel {

	public ExchangeIdGenMethod() {
	}

	private String client_id = "mmarkt";

	public ExchangeIdGenMethod(String client_id) {
		super();
		if (!StringUtils.isEmpty(client_id)) {
			this.client_id = client_id;
		}
	}

	public Object exec(List arg0) throws TemplateModelException {
		return client_id+ DateFormatUtils.getFormatedDateTime8()
				+ new SeqUtil().getSequenceLen("seq_crm2to2_id","0",10);
		
	}

}
