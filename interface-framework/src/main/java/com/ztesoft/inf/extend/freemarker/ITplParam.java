package com.ztesoft.inf.extend.freemarker;

import com.ztesoft.inf.communication.client.Invoker;
import freemarker.template.Template;
import java.util.Map;

public abstract interface ITplParam
{
  public abstract void addUtils(Map paramMap);

  public abstract Template createTemplate(String paramString)
    throws Exception;

  public abstract void addInvokerInfo(Map paramMap, Invoker paramInvoker);
}

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.extend.freemarker.ITplParam
 * JD-Core Version:    0.6.2
 */