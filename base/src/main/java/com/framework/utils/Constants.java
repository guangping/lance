package com.framework.utils;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-04-24 21:29
 * To change this template use File | Settings | File Templates.
 *
 *常用字符变量及基础正则
 */
public class Constants {
    public static final String UTF8 = "UTF-8";

    public static final String GBK = "GBK";

    /*
    * 日期正则
    * */
    public static final String DATE_PATTERN="^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";

    /*
    *邮箱正则
    * */
    public static final String EMAIL_PATTERN="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";

    /*
    * 手机正则
    * */
    public static final String PHONE_PATTERN="^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
 }
