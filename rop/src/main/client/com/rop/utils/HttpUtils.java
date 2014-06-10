package com.rop.utils;

import com.rop.http.Configuration;
import com.rop.http.HttpClient;
import com.rop.http.HttpResponse;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-23 15:19
 * To change this template use File | Settings | File Templates.
 */
public class HttpUtils {

    /*
    * post请求
    * **/
    public static String post(String url,Map<String, String> params){
        Configuration configuration=new Configuration();
        configuration.setConnectUrl(url);
        HttpClient client=new HttpClient(configuration,params);
        try {
            HttpResponse response=client.post();
            return response.getMsg();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
