package com.rop.client;

import com.rop.client.exception.ApiException;
import com.rop.pojo.RopRequest;
import com.rop.pojo.RopResponse;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-23 15:06
 * To change this template use File | Settings | File Templates.
 */
public class DefaultRopApiClient implements RopApiClient {
    @Override
    public <T extends RopResponse> T execute(RopRequest<T> request, Class<T> clazz) throws ApiException {
        return null;
    }

    @Override
    public <T extends RopResponse> T execute(RopRequest<T> request, Class<T> clazz, String session) throws ApiException {
        return null;
    }
}
