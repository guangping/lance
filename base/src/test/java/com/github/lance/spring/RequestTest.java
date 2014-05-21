package com.github.lance.spring;

import com.framework.database.IBaseDAO;
import com.framework.spring.SpringContextHolder;
import com.github.lance.pojo.Request;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-21 14:29
 * To change this template use File | Settings | File Templates.
 */
public class RequestTest {

    private ApplicationContext context = null;
    private IBaseDAO defaultDAO = null;

    @BeforeMethod
    public void setUp() {
        System.setProperty("CONFIG", "F:\\git\\lance\\trunk\\base\\src\\main\\resources");
        String[] configs = new String[]{"classpath*:spring/*.xml"};
        context = new ClassPathXmlApplicationContext(configs);
        defaultDAO = SpringContextHolder.getBean("defaultDAO");
    }


    @Test
    public void save(){
        Request request=new Request();
        request.setGrav_id("1");
        //request.setRequest_templete();

        defaultDAO.insert("inf_comm_client_request",request);
    }
}
