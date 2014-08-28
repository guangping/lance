package com.flickr.db.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-08-27 16:19
 * To change this template use File | Settings | File Templates.
 */
public class ArrayUtils {

    public static String[] convert(Object[] params) {
        List<String> list = new ArrayList<String>();
        if (null != params) {
            for (Object obj : params) {
                list.add(obj.toString());
            }
        }
        return (null!=params && params.length>0)?list.toArray(new String[]{}):new String[]{};
    }
}
