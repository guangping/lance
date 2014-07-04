package com.rop.listener;

import com.alibaba.fastjson.JSONObject;
import com.rop.RopRequestContext;
import com.rop.database.IBaseDAO;
import com.rop.event.AfterDoServiceEvent;
import com.rop.event.RopEventListener;
import com.rop.pojo.RopLogger;
import com.rop.spring.SpringRopContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * <pre>
 * 功能说明：
 * </pre>
 *
 * @author 陈雄华
 * @version 1.0
 *          <p/>
 *          记录调用日志
 */
public class LoggerDoServiceEventListener implements RopEventListener<AfterDoServiceEvent> {

    private static Logger log = LoggerFactory.getLogger(LoggerDoServiceEventListener.class);

    private Queue<RopLogger> queue = null;
    private Timer timer = null;

    {
        queue = new LinkedBlockingQueue<RopLogger>();
        log.debug("日志队列的大小:{}", queue.size());
        timer = new Timer(true);
        for(int i=0;i<10;i++){
            timer.schedule(new RunnableLogger(), 30000, 1000);
        }
    }


    @Override
    public void onRopEvent(AfterDoServiceEvent ropEvent) {
        RopRequestContext ropRequestContext = ropEvent.getRopRequestContext();

        if (ropRequestContext != null) {
            RopLogger logger = new RopLogger();
            logger.setAppKey(ropRequestContext.getAppKey());
            logger.setMethod(ropRequestContext.getMethod());
            logger.setIp(ropRequestContext.getIp());
            logger.setServiceBeginTime(new Date(ropRequestContext.getServiceBeginTime()));
            logger.setServiceEndTime(new Date(ropRequestContext.getServiceEndTime()));
            logger.setVersion(ropRequestContext.getVersion());
            logger.setRequestContent(JSONObject.toJSONString(ropRequestContext.getAllParams()));
            logger.setResponseContent(JSONObject.toJSONString(ropRequestContext.getRopResponse()));
            logger.setSessionId(ropRequestContext.getSessionId());
            /*
            * 记录调用日志
            * */
            queue.offer(logger);
            /*
            *记录调用次数(总调用次数、某个方法的调用次数)
            * **/


        }
    }


    private class RunnableLogger extends TimerTask {
        private IBaseDAO ropDefaultDAO;
        private int Max = 1000;

        private RunnableLogger() {

        }

        @Override
        public void run() {
            ropDefaultDAO = SpringRopContextHolder.getBean("ropDefaultDAO");
            if (queue.size() > 0 && queue.size() <= Max) Max = queue.size();
            List<RopLogger> list = new ArrayList<RopLogger>();
            RopLogger ropLogger = null;
            if (queue.size() > 0) {
                for (int i = 0; i < Max; i++) {
                    ropLogger = queue.poll();
                    if (null != ropLogger) {
                        list.add(ropLogger);
                    }
                }
                if (list.size() > 0) {
                    ropDefaultDAO.batchInsert("rop_log", list);
                }

            }
        }
    }

   /* private class RunnableLogger extends TimerTask {
        private List<RopLogger> list = null;
        private IBaseDAO ropDefaultDAO;

        private RunnableLogger(List<RopLogger> list) {
            this.list = list;
            ropDefaultDAO = SpringRopContextHolder.getBean("ropDefaultDAO");
        }

        @Override
        public void run() {
            ropDefaultDAO.batchInsert("rop_log", list);
        }
    }*/

    @Override
    public int getOrder() {
        return 0;
    }

}

