package com.basic.database.util;


import com.basic.spring.SpringContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

import javax.sql.DataSource;
import java.sql.Connection;


/*
* 链接获取类
* */
public class DBConnection {
    private static final String DEFAULT = "dataSource";

    private static Logger logger= LoggerFactory.getLogger(DBConnection.class);

    public static Connection getConnection(String dBName)
            throws Exception {

        if (StringUtils.isBlank(dBName)) {
            dBName = DEFAULT;
        }
        DataSource dataSource = SpringContextHolder.getBean(dBName);
        try {
            if (dataSource != null) {
                return dataSource.getConnection();
            }
        } catch (CannotGetJdbcConnectionException e) {
            if(logger.isErrorEnabled()){
                logger.info("获取数据库连接出错:{}",new Object[]{e.getMessage()});
            }
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getConnection() throws Exception {
        return getConnection(null);
    }
}
