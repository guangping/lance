package com.framework.dubbo.consumer;

import com.framework.dubbo.ILoginService;
import javafx.application.Application;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-06-19 14:38
 * To change this template use File | Settings | File Templates.
 */
public class ConsumerTest {

    private String[] config;
    private ApplicationContext context = null;


    @Before
    public void setUp() {
        System.setProperty("CONFIG","F:\\git\\lance\\trunk\\dubbo-consumer\\src\\main\\resources\\config\\");
        config = new String[]{"classpath*:spring/*.xml", "classpath*:dubbo/reference/*.xml"};
        context = new ClassPathXmlApplicationContext(config);
    }

    @Test
    public void run() {
        ILoginService loginService = (ILoginService) context.getBean("loginService");
        System.out.println("结果:"+ loginService.login("2","22"));
    }
}
