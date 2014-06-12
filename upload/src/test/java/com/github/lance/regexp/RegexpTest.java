package com.github.lance.regexp;

import org.testng.annotations.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-12 10:21
 * To change this template use File | Settings | File Templates.
 */
public class RegexpTest {

    @Test
    public void run(){
        String s = "@Shang Hai Hong Qiao Fei Ji Chang";
        String regEx = "a|F"; //表示a或F
        Pattern pat = Pattern.compile(regEx);   //忽略大小写，则可以写成Pattern pat=Pattern.compile(regEx,Pattern.CASE_INSENSITIVE);
        Matcher mat = pat.matcher(s);
        boolean rs = mat.find();
        System.out.println("rs:"+rs);
    }

    @Test
    public void run2(){
        String str="pool-1-thread-3";  //pool-1-thread-3
      //  String str="";
        String regEx = "pool-(\\d+)-thread"; //表示a或F
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(str);
        boolean rs = mat.find();
        System.out.println("rs:"+rs);
    }

    @Test
    public void run3(){
        String str="pool-1-thread-3";
        System.out.println(str.indexOf("pool"));
    }
}
