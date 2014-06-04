package com.github.test.spring;

import com.framework.database.IBaseDAO;
import com.framework.spring.SpringContextHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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
        String configs[]=new String[]{"classpath*:spring/dataAccessContext-jdbc.xml"};
        context=new ClassPathXmlApplicationContext(configs);
    }

    @Test(enabled = false)
    public void runContext(){
        System.out.println("spring测试"+context);
    }

    @Test
    public void runConnection(){
        IBaseDAO daoSupport= SpringContextHolder.getBean("jdbcDaoSupport");
        System.out.println("数据操作对象:"+daoSupport);
    }

}
