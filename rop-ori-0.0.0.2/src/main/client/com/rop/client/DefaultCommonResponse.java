package com.rop.client;

import com.rop.client.response.ErrorResponse;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-07-01 14:38
 * To change this template use File | Settings | File Templates.
 */
public class DefaultCommonResponse<T> implements CommonResponse {
    private boolean successful;

    private ErrorResponse errorResponse;

    private T successRopResponse;

    public DefaultCommonResponse(boolean successful) {
        this.successful = successful;
    }

    @Override
    public ErrorResponse getErrorResponse() {
        return this.errorResponse;
    }

    @Override
    public T getSuccessResponse() {
        return this.successRopResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public void setSuccessRopResponse(T successRopResponse) {
        this.successRopResponse = successRopResponse;
    }

    @Override
    public boolean isSuccessful() {
        return successful;
    }
}
