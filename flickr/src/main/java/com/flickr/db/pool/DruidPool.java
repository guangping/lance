package com.flickr.db.pool;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.flickr.db.utils.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-08-27 15:22
 * To change this template use File | Settings | File Templates.
 */
public class DruidPool {

    private static String DATA_CONFIG = "DataConfig";

    private static DruidDataSource dataSource = null;

    private static class DruidPoolClassLoader {
        protected static DruidPool instance = new DruidPool();
    }

    private DruidPool() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                close();
                System.out.println("application will exit ....");
            }
        });
    }

    static {
        String data_config = System.getProperty(DATA_CONFIG);
        try {
            if (StringUtils.isBlank(data_config)) {
                throw new RuntimeException("数据库配置参数不存在!");
            }
            File file = new File(data_config);
            Properties ps = new Properties();
            ps.load(new InputStreamReader(new FileInputStream(file)));
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(ps);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void init(){
       try{
           DruidPooledConnection connection=dataSource.getConnection();
           connection.createStatement();

       }catch (SQLException e){
           e.printStackTrace();
       }

    }

    public static DruidPool instance() {
        return DruidPoolClassLoader.instance;
    }

    public DruidPooledConnection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close() {
        dataSource.close();
    }
}
