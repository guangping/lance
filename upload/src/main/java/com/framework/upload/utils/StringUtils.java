package com.framework.upload.utils;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-07 15:09
 * To change this template use File | Settings | File Templates.
 */
public class StringUtils {

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }


    public static boolean isNotBlank(CharSequence cs) {
        return !StringUtils.isBlank(cs);
    }
}
