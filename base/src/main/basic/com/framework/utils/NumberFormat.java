package com.framework.utils;

import java.text.DecimalFormat;

/**
 * Created with IntelliJ IDEA.
 * User: lance
 * Date: 2014-12-11 19:38
 * To change this template use File | Settings | File Templates.
 *
 * 数字格式化类
 */
public class NumberFormat {
    public static String decimalFormat(double value) {
        return new DecimalFormat("0.00").format(value);
    }


    public static String decimalFormat(double value, String pattern) {
        return new DecimalFormat(pattern).format(value);
    }

    public static String decimalBlankFormat(double value) {
        return new DecimalFormat("0").format(value);
    }
}
