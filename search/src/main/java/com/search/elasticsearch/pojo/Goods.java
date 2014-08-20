package com.search.elasticsearch.pojo;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-08-19 16:32
 * To change this template use File | Settings | File Templates.
 */
public class Goods implements Serializable {
    private String id;

    private String name;

    private String intro;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
