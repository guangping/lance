package com.ztesoft.inf.operation.bo;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.inf.operation.vo.Operation;
import java.sql.SQLException;

public abstract interface IOperationBO
{
  public abstract Operation getOperationByCode(String paramString)
    throws FrameException, SQLException;
}

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.operation.bo.IOperationBO
 * JD-Core Version:    0.6.2
 */