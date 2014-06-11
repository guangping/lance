package com.github.lance.string;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-06-11 16:05
 * To change this template use File | Settings | File Templates.
 */
public class RandomStringTest {

    @Test
    public void run() {
        System.out.println(RandomStringUtils.randomNumeric(4));
        System.out.println(RandomStringUtils.randomAlphabetic(4));
        System.out.println(RandomStringUtils.randomAlphanumeric(5));
    }

    @Test
    public void join(){
        String [] arrays=new String[]{"11","22","ss"};
        String rval=StringUtils.join(arrays,",");
        System.out.println(StringUtils.split(rval,"\\,").length);
        System.out.println("length："+arrays.length+";连接字符串:"+rval);
    }

}
