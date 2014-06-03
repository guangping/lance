package com.powerise.ibss.util;

import com.framework.spring.SpringContextHolder;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 数据库连接的处理
 */
public class DBUtil {

//	private static Logger m_Logger;

	/**
	 * 数据库连接配置: TYPE=1 使用DataSource ，TYPE=2 使用标准的JDBC连接
	 * 确保在函数出错抛出意外之前将已Open的Connection关闭
	 * 
	 * @return
	 * @throws com.powerise.ibss.framework.FrameException
	 */
	public static Connection getConnection(String dBName)
			throws FrameException {
//		 return pureConnection(dBName) ;
		if(StringUtils.isEmpty(dBName)){
			dBName="dataSource";
		}

		DataSource dataSource = SpringContextHolder.getBean(dBName);
		try {
			if (dataSource != null)
				return dataSource.getConnection();
			else
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * 通过指定的名字得到数据库的连接
	 *
	 * @param name
	 *            指定的连接名
	 */
	public static Connection getConnection() throws FrameException {
		return getConnection("dataSource");
	}

	/**
	 * 此方法将被废弃
	 *
	 * @return
	 * @throws com.powerise.ibss.framework.FrameException
	 */
	public static Connection getConnectionThin() throws FrameException {
		return getConnection("BackGround");
	}

	/**
	 * 关闭链接
	 *
	 * @param conn
	 * @throws com.powerise.ibss.framework.FrameException
	 */
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.rollback();
				conn.close();
				conn = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
