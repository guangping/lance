package com.basic.database;

import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-02-12 15:38
 * To change this template use File | Settings | File Templates.
 */
public interface IDaoSupport<T> {
    /**执行sql语句**/
    public void execute(String sql) ;

    /**
     * 查询单一结果集<br/>
     * 并将结果转为<code>int</code>型返回
     * @param sql 查询的sql语句，确定结果为一行一列，且为数字型
     * @param args 对应sql语句中的参数值
     * @return
     */
    public int queryForInt(String sql, Object... args);

    public String queryForString(String sql);

    public String queryForString(String sql, Object... args);

    public List queryForList(String sql, Object... args);

    public List<T> queryForList(String sql, RowMapper mapper, Object... args);

    public List<T> queryForList(String sql, Class clazz, Object... args);

    public long queryForLong(String sql, Object... args);

    public Map queryForMap(String sql, Object... args);

    public T queryForObject(String sql, Class clazz, Object... args);

    public void update(String sql, Map args);

    public void update(String sql, Object... args);

    public void insert(String table, Class clazz);

    public void insert(String sql, Object... args);
}
