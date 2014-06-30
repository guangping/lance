package com.rop.params.base;

import com.rop.RopResponse;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-06-27 16:42
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseRopResponse implements RopResponse {

    private boolean result = false;

    private String code;

    private String msg;


    @Override
    public boolean isResult() {
        return result;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
