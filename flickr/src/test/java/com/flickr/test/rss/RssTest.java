package com.flickr.test.rss;

import com.flickr.db.pool.DBExecutors;
import com.flickr.db.pool.IDBExecutors;
import com.flickr.test.pojo.Rss;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-10-30 15:35
 * To change this template use File | Settings | File Templates.
 */
public class RssTest {

    private Rss rss = null;

    private IDBExecutors executors = null;

    @BeforeMethod
    public void setUp() {
        executors = new DBExecutors();
        System.setProperty("DataConfig", "F:\\git\\lance\\trunk\\flickr\\src\\main\\resources\\jdbc.properties");
    }

    @Test
    public void run() {
        String sql = "select * from rss where id=?";
        rss=executors.queryForObject(sql,Rss.class,"75353");
        System.out.println("获取对象:"+rss.getTitle());
    }


}
