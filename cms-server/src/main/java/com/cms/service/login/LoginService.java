package com.cms.service.login;

import com.cms.pojo.user.SysUser;
import com.framework.database.IBaseDAO;
import com.framework.spring.SpringContextHolder;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-11-04 16:34
 * To change this template use File | Settings | File Templates.
 * 登录服务类
 */

public class LoginService {

    private IBaseDAO defaultDAO;


    public SysUser findByUserName(String userName) {
        if(null==defaultDAO){
            defaultDAO= SpringContextHolder.getBean("defaultDAO");
        }
        String sql="SELECT * FROM sys_user t WHERE t.USERNAME=?";
        return defaultDAO.queryForObject(sql,SysUser.class,userName);
    }

    public void setDefaultDAO(IBaseDAO defaultDAO) {
        this.defaultDAO = defaultDAO;
    }
}
