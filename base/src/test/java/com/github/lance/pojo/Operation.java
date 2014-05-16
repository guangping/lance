package com.github.lance.pojo;

import com.framework.database.pojo.AutoPK;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-16 11:12
 * To change this template use File | Settings | File Templates.
 */
public class Operation implements Serializable{
    @AutoPK
    private String id;//主键

    private String code;//操作编码
    private String endpoint_id;
    private String request_id;
    private String response_id;
    private String description;
    private String flag;//是 true 否 false

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code=code;
    }
    public String getEndpoint_id(){
        return this.endpoint_id;
    }
    public void setEndpoint_id(String endpoint_id){
        this.endpoint_id=endpoint_id;
    }
    public String getRequest_id(){
        return this.request_id;
    }
    public void setRequest_id(String request_id){
        this.request_id=request_id;
    }
    public String getResponse_id(){
        return this.response_id;
    }
    public void setResponse_id(String response_id){
        this.response_id=response_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFlag(){
        return this.flag;
    }
    public void setFlag(String flag){
        this.flag=flag;
    }
}
