package com.action.analysis.log;

import com.opensymphony.clickstream.Clickstream;
import com.opensymphony.clickstream.ClickstreamRequest;
import com.opensymphony.clickstream.logger.ClickstreamLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-06-18 16:43
 * To change this template use File | Settings | File Templates.
 */
public class TouchLogger implements ClickstreamLogger {
    private static Logger logger = LoggerFactory.getLogger(TouchLogger.class);



    /**
     * 由于行为采集将是一个比较消耗资源的操作(用户量大，点击量大)，所以在优化方面
     * 可以考虑以下方法：
     * 1.每web服务器将日志信息记录在本地
     * 2.对于用户每次登录，并不是每作一次操作都要进行一次db的数据插入，而是当该用户的session过期的时候再进行，这样避免
     * 多次的数据库操作。但是考虑到数据采集的真实性需要在每次点击的时候将点击时间进行记录
     *
     */
    @Override
    public void log(Clickstream cs) {
        logger.debug("session超时,记录日志中～～～");
        List list = cs.getStream();
        HttpSession session = cs.getSession();
        String userName=(String)session.getAttribute("username");
        for (Iterator iter = list.iterator(); iter.hasNext();)
        {
            ClickstreamRequest cr = (ClickstreamRequest) iter.next();
            String servletPath = null;
            if(cr.getQueryString()==null){
                servletPath = cr.getRequestURI();
            }else{
                servletPath = cr.getRequestURI()+"?"+cr.getQueryString();
            }
            String ip = cr.getIp();

            logger.debug("记录日志中：userName:"+userName+",IP:"+ip+",servletPath:"+servletPath+"点击时间:"+cr.getTimestamp());
        }
    }
}
