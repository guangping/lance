package com.github.lance.cache;

import com.framework.cache.CacheFactory;
import com.framework.spring.SpringContextHolder;
import com.framework.utils.ChineseUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-06-24 16:23
 * To change this template use File | Settings | File Templates.
 */
public class CacheTest {
    ApplicationContext context=null;
    CacheFactory cacheFactory;

    @BeforeClass
    public void setUp(){
        System.setProperty("CONFIG", "F:\\git\\lance\\trunk\\base\\src\\main\\resources\\config\\");
        String[] configs = new String[]{"classpath*:spring/*.xml","classpath*:memcached/*.xml"};
        context = new ClassPathXmlApplicationContext(configs);
        cacheFactory= SpringContextHolder.getBean("cacheFactory");
    }

    @Test
    public void setVal(){
        //cacheFactory.getDefaultCache().setObj("title",1000,"测试");
        System.out.println("值:"+cacheFactory.getDefaultCache().getObj("title"));
    }

    @Test
    public void setVals(){
        for(int i=0;i<1000;i++){
            cacheFactory.getDefaultCache().setObj("title"+i,1000, ChineseUtils.getRandomChinese()+ChineseUtils.getRandomChinese());
        }
    }
}
