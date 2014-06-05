package com.github.test.spring;

import com.framework.database.IBaseDAO;
import com.framework.spring.SpringContextHolder;
import com.ztesoft.inf.communication.client.CommCaller;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-03-31 17:38
 * To change this template use File | Settings | File Templates.
 */
public class SpringMainTest {

    private ApplicationContext context;

    @BeforeClass
    public void setUp(){
        System.setProperty("CONFIG","F:\\git\\lance\\trunk\\interface-framework\\src\\main\\resources\\config\\");

        String configs[]=new String[]{"classpath*:spring/dataAccessContext-jdbc.xml"};
        context=new ClassPathXmlApplicationContext(configs);
    }

    @Test(enabled = false)
    public void runContext(){
        System.out.println("spring测试"+context);
    }

    @Test
    public void runConnection(){
        IBaseDAO daoSupport= SpringContextHolder.getBean("defaultDAO");
        System.out.println("数据操作对象:"+daoSupport);
    }

    @Test
    public void runSms(){
        CommCaller caller=new CommCaller();
        Map params=new HashMap();
        params.put("time",System.currentTimeMillis());
        params.put("acc_nbr","18620975381");
        params.put("content",System.currentTimeMillis());


        caller.invoke("ESB.SMS",params);
    }

}
