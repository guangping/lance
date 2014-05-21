package com.github.lance.pojo;

import com.framework.database.pojo.AutoPK;
import com.framework.database.pojo.NotDbField;

import java.io.Serializable;
import java.sql.Clob;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-21 14:29
 * To change this template use File | Settings | File Templates.
 */
public class Request implements Serializable {
    @AutoPK
    private String id;

    private String grav_id;

    private Clob request_templete;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGrav_id() {
        return grav_id;
    }

    public void setGrav_id(String grav_id) {
        this.grav_id = grav_id;
    }

    public Clob getRequest_templete() {
        return request_templete;
    }

    public void setRequest_templete(Clob request_templete) {
        this.request_templete = request_templete;
    }
}
