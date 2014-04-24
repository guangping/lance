package com.framework.web;

import com.framework.utils.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-04-24 21:51
 * To change this template use File | Settings | File Templates.
 */
public class WebUtils {
    /***
     * 获取URI的路径,如路径为http://www.babasport.com/action/post.htm?method=add, 得到的值为"/action/post.htm"
     * @param request
     * @return
     */
    public static String getRequestURI(HttpServletRequest request){
        return request.getRequestURI();
    }
    /**
     * 获取完整请求路径(含内容路径及请求参数)
     * @param request
     * @return
     */
    public static String getRequestURIWithParam(HttpServletRequest request){
        return getRequestURI(request) + (StringUtils.isBlank(request.getQueryString()) ? "" : "?"+ request.getQueryString());
    }
    /**
     * 添加cookie
     * @param response
     * @param name cookie的名称
     * @param value cookie的值
     * @param maxAge cookie存放的时间(以秒为单位,假如存放三天,即3*24*60*60; 如果值为0,cookie将随浏览器关闭而清除)
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxAge>0) cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 获取cookie的值
     * @param request
     * @param name cookie的名称
     * @return
     */
    public static String getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if(cookieMap.containsKey(name)){
            Cookie cookie = (Cookie)cookieMap.get(name);
            return cookie.getValue();
        }
        return null;
    }

    protected static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (int i = 0; i < cookies.length; i++) {
                cookieMap.put(cookies[i].getName(), cookies[i]);
            }
        }
        return cookieMap;
    }


    public static String getRequestIp(HttpServletRequest request) {
        // 取IP地址
        String vIP = request.getHeader("x-forwarded-for");
        if (vIP == null || vIP.length() == 0|| "unknown".equalsIgnoreCase(vIP)) {
            vIP = request.getHeader("X-Forwarded-For");
        }
        if (vIP == null || vIP.length() == 0|| "unknown".equalsIgnoreCase(vIP)) {
            vIP = request.getHeader("Proxy-Client-IP");
        }
        if (vIP == null || vIP.length() == 0|| "unknown".equalsIgnoreCase(vIP)) {
            vIP = request.getHeader("WL-Proxy-Client-IP");
        }
        if (vIP == null || vIP.length() == 0|| "unknown".equalsIgnoreCase(vIP)) {
            vIP = request.getRemoteAddr();
        }
        return vIP;
    }

}
