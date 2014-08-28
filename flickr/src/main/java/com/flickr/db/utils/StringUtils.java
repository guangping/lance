package com.flickr.db.utils;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-08-27 16:00
 * To change this template use File | Settings | File Templates.
 */
public class StringUtils {

    private StringUtils() {
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }


    public static boolean isNotEmpty(CharSequence cs) {
        return !StringUtils.isEmpty(cs);
    }


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

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }


}
