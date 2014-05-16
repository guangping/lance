package com.github.lance.pojo;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-16 13:25
 * To change this template use File | Settings | File Templates.
 */
public class InfBase implements Serializable {
    private boolean result=false;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
