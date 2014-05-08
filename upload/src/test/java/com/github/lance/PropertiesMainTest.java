package com.github.lance;

import com.alibaba.fastjson.JSONObject;
import com.framework.upload.pojo.ConnectionConfig;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-08 13:57
 * To change this template use File | Settings | File Templates.
 */
public class PropertiesMainTest {
    private Properties properties = null;

    @BeforeMethod
    public void setUp() {
        InputStream inputStream = null;
        try {
            inputStream = PropertiesMainTest.class.getClassLoader().getResourceAsStream("connconfig.properties");
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
           if(null!=inputStream){
               try {
                   inputStream.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
        }
    }

    @Test
    public void run(){
        String config=properties.getProperty("ftp.config");
        List<ConnectionConfig> list=JSONObject.parseArray(config, ConnectionConfig.class);

        System.err.println(list.size());
    }
}
