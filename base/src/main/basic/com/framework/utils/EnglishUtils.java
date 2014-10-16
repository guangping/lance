package com.framework.utils;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-20 22:11
 * To change this template use File | Settings | File Templates.
 */
public class EnglishUtils {

    /*
    * 获取随机英文
    * */
    public static char getRandomEngilish(){
        return (char) (Math.random ()*26+'A');
    }
    /*
   * 获取随机英文 小写
   * */
    public static char getRandomEngilishLower(){
        return (char) (Math.random ()*26+'a');
    }
}
