
package com.ztesoft.common.dao;

import com.ztesoft.common.util.JNDINames;
import com.ztesoft.ibss.common.dao.DAOUtils;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Connection 管理上下文
 *
 * @author easonwu 2009-12-22
 *
 */
public class ConnectionContext {
    private static final Logger _logger = Logger.getLogger(ConnectionContext.class);
//	public static final String DEFAULT_JNDINAME = "jdbc/CRM";
	public static final String DEFAULT_JNDINAME = JNDINames.DEFAULT_DATASOURCE;

	public static final String RUN_MODE = "develop" ;//develop 开发模式 product 生产模式

	private static ThreadLocal connectionContext = new ConnectionContextThreadLocal();

	static{
		//DAOUtils.systempInit() ;//先进行系统是否初始化处理
	}

	private Map context;

	public ConnectionContext(Map context) {
		this.context = context;
	}

	public void setConnection(String jndiName , Connection anCon){
		put(jndiName, anCon) ;
	}


	public void setConnection(Connection anCon){
		setConnection(DEFAULT_JNDINAME, anCon) ;
	}

	public Connection getConnection()  {
		return getConnection(DEFAULT_JNDINAME) ;
	}

	/**
	 * 获取Connection
	 * @param aDBName
	 * @return
	 * @throws com.powerise.ibss.framework.FrameException
	 */
	public Connection getConnection(String aDBName )  {
		aDBName = (aDBName == null || "".equals(aDBName)) ?
					DEFAULT_JNDINAME : aDBName ;

		if( context.get(aDBName) != null)
			return (Connection)context.get(aDBName) ;

		Connection conn = null;
		try {
			conn = getDBConnection(aDBName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(conn != null){
			setConnection(aDBName, conn) ;
		}else{
			throw new DAOSystemException("根据dbName="+aDBName+",获取数据库连接失败！");
		}
		//System.out.println(aDBName+" get conn ======"+conn) ;
		_logger.debug(aDBName+" get conn ======"+conn);
		return conn ;
	}
	/**
	 * close connection
	 * @param aDBName
	 * @return
	 * @throws java.sql.SQLException
	 * @throws com.powerise.ibss.framework.FrameException
	 */
	public boolean closeConnection(String aDBName ) throws SQLException{
		Connection conn = getConnection( aDBName ) ;

		//System.out.println(aDBName+" close conn ======"+conn) ;
		_logger.debug(aDBName+" close conn ======"+conn);
		if( conn == null )
			return false ;
		conn.close() ;
		this.context.remove(aDBName) ;

		conn = null ;
		return true ;
	}

	/**
	 * commit operation
	 * @param aDBName
	 * @return
	 * @throws java.sql.SQLException
	 * @throws com.powerise.ibss.framework.FrameException
	 */
	public boolean commit(String aDBName ) throws SQLException{
		Connection conn = getConnection( aDBName ) ;
		if( conn == null )
			return false ;
		conn.commit() ;
		_logger.debug(aDBName+" commit conn ======"+conn);
		//System.out.println(aDBName+" commit conn ======"+conn) ;
		return true ;
	}

	/**
	 * batch commit for multi db evn
	 * @return
	 * @throws java.sql.SQLException
	 */
	public boolean allCommit() throws SQLException{
		if(this.context == null || this.context.isEmpty())
			return false ;
		Iterator it = this.context.keySet().iterator() ;
		while ( it.hasNext() ){
			String dbName = (String) it.next() ;
			_logger.debug("db.commit==========="+dbName);
			//System.out.println("db.commit==========="+dbName) ;

			Connection conn = (Connection) context.get(dbName) ;
			conn.commit() ;
		}
		return true ;
	}

	/**
	 * batch rollback for multi db evn
	 * @return
	 * @throws java.sql.SQLException
	 */
	public boolean allRollback() throws SQLException{
		if(this.context == null || this.context.isEmpty())
			return false ;
		Iterator it = this.context.keySet().iterator() ;
		while ( it.hasNext() ){
			String dbName = (String) it.next() ;
			_logger.debug("db.rollback==========="+dbName);
			//System.out.println("db.rollback==========="+dbName) ;
			Connection conn = (Connection) context.get(dbName) ;
			conn.rollback() ;

		}
		return true ;
	}

	/**
	 * batch close conn
	 * @return
	 * @throws java.sql.SQLException
	 */
	public boolean allCloseConnection( ) throws SQLException {
		if(this.context == null || this.context.isEmpty())
			return false ;
		Iterator it = this.context.keySet().iterator() ;
		while ( it.hasNext() ){
			String dbName = (String) it.next() ;
			_logger.debug("db.close==========="+dbName);
			//System.out.println("db.close==========="+dbName) ;
			Connection conn = (Connection) context.get(dbName) ;
			conn.close() ;
			conn = null ;
		}
		this.context.clear() ;
		return true ;
	}

	/**
	 * rollback operation
	 * @param aDBName
	 * @return
	 * @throws java.sql.SQLException
	 * @throws com.powerise.ibss.framework.FrameException
	 */
	public boolean rollback(String aDBName ) throws SQLException {
		Connection conn = getConnection( aDBName ) ;
		if( conn == null )
			return false ;
		conn.rollback() ;
		_logger.debug(aDBName+" rollback conn ======"+conn);
		//System.out.println(aDBName+" rollback conn ======"+conn) ;
		return true ;
	}


	private void put(String key, Object value) {
		context.put(key, value);
	}

	private static class ConnectionContextThreadLocal extends ThreadLocal {
		protected Object initialValue() {
			return new ConnectionContext(new HashMap());
		}
	}

	public static ConnectionContext getContext() {

		ConnectionContext context = (ConnectionContext) connectionContext.get();

		if (context == null) {
			context = new ConnectionContext(new HashMap());
			setContext(context);
		}

		return context;
	}

	public Map getContextMap(){
		return this.context ;
	}

	public static void setContext(ConnectionContext context) {
		connectionContext.set(context);
	}



	/**
	 * @param :dataSourceName
	 *            数据源名字
	 */
	private static DataSource getDataSource(String dataSourceName)
			throws DAOSystemException {
	/*	String jndiName = CrmParamsConfig.getInstance().getParamValue(dataSourceName+"_JNDI")  ;
		_logger.debug("dbName==="+dataSourceName+",jndiName==="+jndiName);
		//System.out.print("dbName==="+dataSourceName+",jndiName==="+jndiName) ;
		DataSource ds =  (DataSource) ServiceLocator.getInstance().getDataSource(jndiName);
		_logger.debug(",ds==="+ds+"\n");

		//System.out.print(",ds==="+ds+"\n") ;*/
		return null ;
	}

	/**
	 * @param :dataSourceName
	 *            数据源名字， callerObj 调用者句柄
	 */
	private static DataSource getDataSource(String dataSourceName,
			Object callerObj) throws DAOSystemException {
		/*return (DataSource) ServiceLocator.getInstance().getDataSource(
				CrmParamsConfig.getInstance().getParamValue(dataSourceName+"_JNDI")
				, callerObj);*/
        return null;
	}

	/**
	 * @param :source
	 *            数据源名字
	 */
	private static Connection getDBConnection(String source)
			throws DAOSystemException {
		Connection conn = null ;
		try {
			// 为了方便测试设置自动切换连接方式，实际环境可以关闭这些判断代码。
			//String DIRECT_CONNECTION = CrmParamsConfig.getInstance().getParamValue("DIRECT_CONNECTION") ;
            String DIRECT_CONNECTION="";
			if (DIRECT_CONNECTION != null && "true".equalsIgnoreCase(DIRECT_CONNECTION)) {
				conn = getDirectConnection(source);
			} else {
				DataSource ds = getDataSource(source);
				if( ds != null ){
					conn = ds.getConnection();
				}else{
					_logger.debug("\n\n\nDataSource == " + ds + "\n\n\n");
					//System.out.println("\n\n\nDataSource == " + ds + "\n\n\n");
				}
				//conn = getDataSource(source).getConnection();
			}
			conn.setAutoCommit(false) ;
		} catch (SQLException se) {
			throw new DAOSystemException("SQL Exception while getting "
					+ "DB connection : \n" + se);
		} catch( Exception e ){
			_logger.debug("\n\n\nconn == " + conn + "\n\n\n");
			//System.out.println("\n\n\nconn == " + conn + "\n\n\n");
			e.printStackTrace();
		}
		return conn ;
	}


	/**
	 * @param :source
	 *            数据源名字
	 */
	public static Connection getQueryConnection(String source)
			throws DAOSystemException {
		Connection conn = null ;
		try {
			// 为了方便测试设置自动切换连接方式，实际环境可以关闭这些判断代码。
			//String DIRECT_CONNECTION = CrmParamsConfig.getInstance().getParamValue("DIRECT_CONNECTION") ;
            String DIRECT_CONNECTION=null;
			if (DIRECT_CONNECTION != null && "true".equalsIgnoreCase(DIRECT_CONNECTION)) {
				conn = getDirectConnection(source);
			} else {
				DataSource ds = getDataSource(source);
				if( ds != null ){
					conn = ds.getConnection();
				}else{
					_logger.debug("\n\n\nDataSource == " + ds + "\n\n\n");
					//System.out.println("\n\n\nDataSource == " + ds + "\n\n\n");
				}
			}
		} catch (SQLException se) {
			throw new DAOSystemException("SQL Exception while getting "
					+ "DB connection : \n" + se);
		} catch( Exception e ){
			_logger.debug("\n\n\nconn == " + conn + "\n\n\n");
			//System.out.println("\n\n\nconn == " + conn + "\n\n\n");
			e.printStackTrace();
		}
		return conn ;
	}

	public static void closeQueryConnection (Connection conn ){
		try{
			if(conn != null )
				conn.close() ;
		}catch(Exception e ){
			e.printStackTrace() ;
		}
	}
	/**
	 * 直接使用JDBC的方式获取当前数据库的连接.
	 *
	 * @return
	 */
	public static Connection getDirectConnection(String dbName) throws DAOSystemException {
		Connection connection = null;
		/*try {
			dbName = dbName.toUpperCase() ;
			String driver = CrmParamsConfig.getInstance().getParamValue(
					dbName+"_Driver") ;


			String url = CrmParamsConfig.getInstance().getParamValue(
					dbName+"_DBUrl");
			String username = CrmParamsConfig.getInstance().getParamValue(
					dbName+"_DBUser");
			String password = CrmParamsConfig.getInstance().getParamValue(
					dbName+"_DBPwd");

			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, password);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DAOSystemException(
					"SQL Exception while init connection \n" + e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOSystemException(
					"SQL Exception while get connection \n" + e);
		}
*/
		return connection;
	}

	/**
	 * 判断是否存在上下文，true : 存在 , false :不存在
	 * @return
	 */
	public static boolean checkConnectionContext(){
		return connectionContext.get() != null &&
		((ConnectionContext) connectionContext.get()).getContextMap() != null;
	}

}
