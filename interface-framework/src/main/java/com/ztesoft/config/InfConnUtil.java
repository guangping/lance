package com.ztesoft.config;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.framework.spring.SpringContextHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;

/**
 * 接口数据源工具类，支持直连和数据源连接
 * @author ReasonYea
 *
 */
public class InfConnUtil {
	/**
	 * 接口使用的数据源连接
	 */
	private static final String INF_DATASOURCE="dataSource";
	private static ApplicationContext applicationContext;
	private static InfConnUtil InfConnUtil = null;
	static{
		if (InfConnUtil == null) {
			InfConnUtil = new InfConnUtil();
		}
	}

	
	/**
	 * 获取数据源
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		return getDatasourceConn();
	}
	
	/**
	 * 通过spring配置DataSource
	 * @return
	 */
	private static Connection getDatasourceConn(){
		try {
            DataSource dataSource=SpringContextHolder.getBean(INF_DATASOURCE);
            return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
