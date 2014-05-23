package com.rop.pojo;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-23 14:02
 * To change this template use File | Settings | File Templates.
 */
public class RopResponse implements Serializable {

    /*
    *结果
    * */
    private boolean result=false;

    /*
    * 编码
    * */
    private String code;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
