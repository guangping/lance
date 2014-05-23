package com.rop.client.exception;

import com.rop.pojo.RopRequest;
import com.rop.pojo.RopResponse;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-22 21:24
 * To change this template use File | Settings | File Templates.
 */
public interface RopApiClient {

    /**
     * 执行TOP公开API请求。
     * @param <T>
     * @param request 具体的TOP请求
     * @return
     * @throws ApiException
     */
    public <T extends RopResponse> T execute(RopRequest<T> request,Class<T> class1) throws ApiException ;
    /**
     * 执行TOP隐私API请求。
     * @param <T>
     * @param request 具体的TOP请求
     * @param session 用户会话授权码
     * @return
     * @throws ApiException
     */
    public <T extends RopResponse> T execute(RopRequest<T> request,Class<T> class1, String session) throws ApiException ;

}
