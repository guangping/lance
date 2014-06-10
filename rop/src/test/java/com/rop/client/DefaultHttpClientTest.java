package com.rop.client;

import com.rop.utils.HttpUtils;
import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-23 15:27
 * To change this template use File | Settings | File Templates.
 */
public class DefaultHttpClientTest {
    String url="http://localhost:8010/router";

    @Test
    public void run(){
        String msg= HttpUtils.post(url,new HashMap<String, String>());
        System.out.println("msg====>"+msg);
    }
}
