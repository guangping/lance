package com.github.lance;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-06-12 14:40
 * To change this template use File | Settings | File Templates.
 */
public class StringMainTest {

    @Test
    public void run(){
        String exp="[,\\s]+";
        String str="classpath*:spring/*.xml,classpath*:dubbo/service/*.xml";

        String items[]=str.split(exp);
        System.out.println(items.length);

    }
}
