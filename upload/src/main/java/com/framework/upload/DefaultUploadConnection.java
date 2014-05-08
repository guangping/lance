package com.framework.upload;

import com.alibaba.fastjson.JSONObject;
import com.framework.upload.pojo.ConnectionConfig;
import com.framework.upload.utils.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-07 10:18
 * To change this template use File | Settings | File Templates.
 */
public class DefaultUploadConnection {

    protected Logger logger= Logger.getLogger(getClass());


    public static List<ConnectionConfig> getConnectionConfig() {
        String config=System.getProperty("ftp.config");
        if(StringUtils.isBlank(config)){
            throw new RuntimeException("ftp信息未配置.");
        }
        List<ConnectionConfig> list= JSONObject.parseArray(config, ConnectionConfig.class);
        return list;
    }

}
