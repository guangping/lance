package com.rop.interceptor;

import com.rop.AbstractInterceptor;
import com.rop.RopRequestContext;
import com.rop.converter.DateUtils;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-06-26 17:23
 * To change this template use File | Settings | File Templates.
 */
public class DefaultInterceptor extends AbstractInterceptor {
    @Override
    public void beforeService(RopRequestContext ropRequestContext) {
        if(logger.isDebugEnabled()){
            logger.debug("{}服务调用拦截器:{}",new String[]{DateUtils.getCurrDatetime(),"beforeService"});
        }
    }

    @Override
    public void beforeResponse(RopRequestContext ropRequestContext) {
        if(logger.isDebugEnabled()){
            logger.debug("{}服务调用拦截器:{}",new String[]{DateUtils.getCurrDatetime(),"beforeResponse"});
        }
    }
}
