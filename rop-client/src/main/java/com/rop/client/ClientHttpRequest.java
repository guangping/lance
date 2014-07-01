package com.rop.client;

import com.rop.params.base.BaseRopRequest;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-07-01 10:20
 * To change this template use File | Settings | File Templates.
 */
public interface ClientHttpRequest {
    /**
     * 添加请求参数,默认需要签名，如果类已经标注了{@link com.rop.annotation.IgnoreSign}则始终不加入签名
     * @param paramName
     * @param paramValue
     * @return
     */
    ClientHttpRequest addParam(String paramName,Object paramValue);

    /**
     * 添加请求参数,如果needSign=false表示不参于签名
     * @param paramName
     * @param paramValue
     * @param needSign
     * @return
     */
    ClientHttpRequest addParam(String paramName,Object paramValue,boolean needSign);

    /**
     * 清除参数列表
     * @return
     */
    ClientHttpRequest clearParam();

    /**
     * 使用POST发起请求
     * @param ropResponseClass
     * @param <T>
     * @return
     */
    <T> CommonResponse  post(BaseRopRequest ropRequest,Class<T> ropResponseClass);

    /**
     * 直接使用 ropRequest发送请求
     * @param ropRequest
     * @param ropResponseClass
     * @param version
     * @param <T>
     * @return
     */
    <T> CommonResponse post(BaseRopRequest ropRequest, Class<T> ropResponseClass, String version);

    /**
     * 直接使用 ropRequest发送请求
     * @param ropRequest
     * @param ropResponseClass
     * @param methodName
     * @param version
     * @param <T>
     * @return
     */
    <T> CommonResponse post(Class<T> ropResponseClass,String methodName ,String version);

    /**
     * 使用GET发送服务请求
     * @param ropResponseClass
     * @param <T>
     * @return
     */
    <T> CommonResponse get(BaseRopRequest ropRequest,Class<T> ropResponseClass);

    /**
     * 使用GET发送ropRequest的请求
     * @param ropRequest
     * @param ropResponseClass
     * @param version
     * @param <T>
     * @return
     */
    <T> CommonResponse get(BaseRopRequest ropRequest, Class<T> ropResponseClass, String version);

    /**
     * 直接使用 ropRequest发送请求
     * @param ropRequest
     * @param ropResponseClass
     * @param methodName
     * @param version
     * @param <T>
     * @return
     */
    <T> CommonResponse get(Class<T> ropResponseClass,String methodName ,String version);

}
