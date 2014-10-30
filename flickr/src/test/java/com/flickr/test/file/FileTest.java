package com.flickr.test.file;

import com.flickr.db.pool.DBExecutors;
import com.flickr.db.pool.IDBExecutors;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-09-23 11:00
 * To change this template use File | Settings | File Templates.
 */
public class FileTest {
    String path = "F:\\data.txt";
    private IDBExecutors executors = null;


    @BeforeClass
    public void setUp() {
        System.setProperty("DataConfig", "F:\\git\\lance\\trunk\\flickr\\src\\main\\resources\\jdbc.properties");
        executors = new DBExecutors();
    }

    @Test
    public void init() {
        File file = new File(path);
        String sql = "insert into news (title)  values(?)";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                executors.insert(sql, line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void delete() {
        String delete_sql = "DELETE FROM news t WHERE t.title=? ";//AND t.delete_flg=? AND t.RELEASE_FLG=?
        String select_sql = "select * from data2";
        List list = executors.queryForList(select_sql);
        Map map = null;
        String title=null;
        boolean rval=false;
        for (Object obj : list) {
            map = (HashMap) obj;
            title=String.valueOf(map.get("title"));
            rval= executors.delete(delete_sql,title);
            if(!rval){
                System.out.println("error===>"+title);
            }
        }
    }

}
