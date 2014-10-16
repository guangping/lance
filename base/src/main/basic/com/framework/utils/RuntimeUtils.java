package com.framework.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-03-28 15:40
 * To change this template use File | Settings | File Templates.
 */
public class RuntimeUtils {

    private static int PID = -1;

    /*
    *获取运行pid
    * */
    public static int getPid() {
        if (PID < 0) {
            try {
                RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
                String name = runtime.getName(); // format: "pid@hostname"
                PID = Integer.parseInt(name.substring(0, name.indexOf('@')));
            } catch (Throwable e) {
                PID = 0;
            }
        }
        return PID;
    }


}
