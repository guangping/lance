package com.rop.client.http;

import com.rop.client.RopApiClient;
import com.rop.client.exception.ApiException;
import com.rop.pojo.RopRequest;
import com.rop.pojo.RopResponse;
import com.rop.utils.RopUtils;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-23 14:56
 * To change this template use File | Settings | File Templates.
 */
public class DefaultHttpClient implements RopApiClient {

    public DefaultHttpClient(String serverUrl, String appKey, String appSecret){
        if(RopUtils.isBlank(serverUrl) || RopUtils.isBlank(appKey) || RopUtils.isBlank(appSecret)){
            throw new  ApiException("-1","serverUrl appKey或appSecret为空!");
        }
    }


    @Override
    public <T extends RopResponse> T execute(RopRequest<T> request, Class<T> clazz) throws ApiException {
        return null;
    }

    @Override
    public <T extends RopResponse> T execute(RopRequest<T> request, Class<T> clazz, String session) throws ApiException {
        return null;
    }
}
