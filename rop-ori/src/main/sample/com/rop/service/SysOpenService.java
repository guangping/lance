package com.rop.service;

import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;
import com.rop.params.request.LogonRequest;
import com.rop.params.response.LogonResponse;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-06-27 15:24
 * To change this template use File | Settings | File Templates.
 */
@ServiceMethodBean(version = "1.0")
public class SysOpenService {

    @ServiceMethod(method = "user.getSession", version = "1.0", needInSession = NeedInSessionType.NO)
    LogonResponse getSession(LogonRequest request) {
        LogonResponse response=new LogonResponse();
        response.setSessionId(UUID.randomUUID().toString());

        return null;
    }
}
