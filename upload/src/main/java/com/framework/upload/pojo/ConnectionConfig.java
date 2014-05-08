package com.framework.upload.pojo;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-07 10:25
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionConfig implements Serializable {

    private String host;

    private int port;

    private String user;

    private String password;

    /*
    * 协议,默认ftp
    * */
    private Protocol protocol=Protocol.FTP;

    /*
    * 文件上传路径,不设置,则采用默认目录,不为空则切换工作目录
    * */
    private String path;


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }
}
