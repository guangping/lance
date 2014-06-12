package com.framework.dubbo.init;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-06-12 13:58
 * To change this template use File | Settings | File Templates.
 */
public class DubboMain {

    public static void main(String args[])throws IOException{
        String []config=new String[]{"classpath*:spring/*.xml","classpath*:dubbo/service/*.xml"};
        ClassPathXmlApplicationContext  context=new ClassPathXmlApplicationContext(config);
        context.start();
        System.in.read();

    }
}
