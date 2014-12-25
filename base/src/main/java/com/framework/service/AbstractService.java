package com.framework.service;

import com.framework.database.IBaseDAO;
import com.framework.spring.SpringContextHolder;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: lance
 * Date: 2014-12-11 20:29
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractService {
    @Resource
    protected IBaseDAO defaultDAO;

    public void init() {
        if (null == defaultDAO) {
            defaultDAO = SpringContextHolder.getBean("defaultDAO");
        }
    }
}
