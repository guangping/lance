package com.rop.sys;

import com.alibaba.fastjson.JSONObject;
import com.rop.client.CommonResponse;
import com.rop.client.DefaultRopHttpClient;
import com.rop.params.request.LoginRequest;
import com.rop.params.request.SessionRequest;
import com.rop.params.response.LoginResponse;
import com.rop.params.response.SessionResponse;
import com.rop.service.SessionOpenService;
import com.rop.utils.HttpUtils;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-06-30 17:10
 * To change this template use File | Settings | File Templates.
 */
public class SysHttpTest {
    public static final String SERVER_URL = "http://localhost:8080/router";
    public static final String APP_KEY = "00001";
    public static final String APP_SECRET = "123";
    private DefaultRopHttpClient ropClient = new DefaultRopHttpClient(SERVER_URL, APP_KEY, APP_SECRET);

    @Test
    public void createSession() {
        SessionRequest ropRequest = new SessionRequest();
        ropRequest.setUserName("tomson");
        ropRequest.setPassword("123");
        // CommonResponse response=ropClient.buildClientRequest().get(ropRequest, SessionResponse.class);
        //CommonResponse response=ropClient.buildClientRequest().get(ropRequest,SessionResponse.class,"2.0");

        CommonResponse response = ropClient.buildClientRequest().get(SessionResponse.class, "user.getSession1", "1.0");

        System.out.println("response:" + response);
    }

    @Test
    public void createSessionPost() {
        SessionRequest ropRequest = new SessionRequest();
        ropRequest.setUserName("tomson");
        ropRequest.setPassword("123");
        CommonResponse response = ropClient.buildClientRequest().post(ropRequest, SessionResponse.class);

        System.out.println("response:" + JSONObject.toJSONString(response));
    }

    @Test
    public void login() {
        LoginRequest ropRequest = new LoginRequest();
        ropRequest.setUserName("tomson");
        ropRequest.setPassword("123");
        CommonResponse response = ropClient.buildClientRequest().post(ropRequest, LoginResponse.class);

        System.out.println("response:" + response);
    }

    @Test
    public void test() {
        System.out.println(HttpUtils.get("http://localhost:8080/router"));
    }

    @Test
    public void method(){
        SessionOpenService sessionOpenService=new SessionOpenService();
        System.out.println("书册:"+0x00000080);
    }

    @Test
    public void sync(){
        ExecutorService executorService= Executors.newFixedThreadPool(10);
        for(int i=0;i<100;i++){
            executorService.submit(new TestSession());
        }

        while (true){}
    }

    private class TestSession implements Runnable{

        @Override
        public void run() {
           for(int i=0;i<5000;i++){
               SessionRequest ropRequest = new SessionRequest();
               ropRequest.setUserName("tomson");
               ropRequest.setPassword("123");
               CommonResponse response = ropClient.buildClientRequest().post(ropRequest, SessionResponse.class);

               // System.out.println(Thread.currentThread().getName()+":"+ JSONObject.toJSONString(response));
           }
        }
    }

    @Test
    public void runTime(){

    }


}
