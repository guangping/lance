package com.github.test.spring;

import com.basic.database.IDaoSupport;
import com.basic.spring.SpringContextHolder;
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
        String configs[]=new String[]{"classpath*:dataAccessContext-jdbc.xml"};
        context=new ClassPathXmlApplicationContext(configs);
    }

    @Test
    public void runContext(){
        System.out.println("spring测试");
    }

    @Test
    public void runConnection(){
        IDaoSupport daoSupport=SpringContextHolder.getBean("jdbcDaoSupport");
        System.out.println("数据操作对象:"+daoSupport);
    }

}
