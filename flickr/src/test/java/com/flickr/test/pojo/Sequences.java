package com.flickr.test.pojo;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-08-28 17:16
 * To change this template use File | Settings | File Templates.
 */
public class Sequences implements Serializable {

    private String id;

    private char stub;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public char getStub() {
        return stub;
    }

    public void setStub(char stub) {
        this.stub = stub;
    }
}
