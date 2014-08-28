package com.flickr.db.pool;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-08-27 17:46
 * To change this template use File | Settings | File Templates.
 */
public interface IDBExecutors {
    /*
    *查询map
    * */
    public Map queryForMap(String sql, Object... params);

    /*
    * 查询list
    * */
    public List queryForList(String sql, Object... params);

    public <T>T queryForObject(String sql, Class<T> clazz, Object... args);

    public <T> List<T>  queryForList(String sql, Class<T> clazz, Object... args);

    public int getInt(String sql, Object... params);

    public long getLong(String sql, Object... params);

    public float getFloat(String sql, Object... params);

    public double getDouble(String sql, Object... params);

    public String getString(String sql, Object... params);

    /*
    * delete
    * */
    public void delete(String sql, Object... params);

    /*
    * update
    * */
    public void update(String sql, Object... params);

    /*
    * insert
    * */
    public String insert(String sql, Object ...params);

    /*
    *批量插入
    * */
    public void batchInsert(String sql, List<Object[]> params);




}
