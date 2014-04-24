package com.framework.database;


import com.framework.database.pojo.Page;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 789
 * Date: 12-5-2
 * Time: 上午9:49
 * To change this template use File | Settings | File Templates.
 * 数据操作接口
 */
public interface IBaseDAO<T> {
    /**执行sql语句**/
    public void execute(String sql, Object... args) ;

    /**
     * 批量执行sql语句
     * @param sql
     * @param args
     */
    public void batchExecute(String sql, List<Object[]> batchArgs);

    /**
     * 查询单一结果集<br/>
     * 并将结果转为<code>int</code>型返回
     * @param sql 查询的sql语句，确定结果为一行一列，且为数字型
     * @param args 对应sql语句中的参数值
     * @return
     */
    public int queryForInt(String sql, Object... args);

    /**
     * 查询单一结果集<br/>
     * 并将结果转为<code>int</code>型返回
     * @param sql 查询的sql语句，使用:name占位符  确定结果为一行一列，且为数字型
     * @param args K-V 键值对
     * @return
     */
    public int queryForIntByMap(String sql, Map args);

    /**
     * 查询单一结果集<br/>
     * 并将结果转为<code>long</code>型返回
     * @param sql 查询的sql语句，确保结果为一行一列，且为数字型
     * @param args 对应sql语句中的参数值
     * @return
     */
    public long queryForLong(String sql, Object... args);

    public String queryForString(String sql,Object...args);

    /**
     * 查询单一结果集<br/>
     * 并将结果转为<code>T</code>对象返回
     * @param sql 查询的sql语句,确保结果列名和对象属性对应
     * @param clazz  <code>T</code>的Class对象
     * @param args 对应sql语句中的参数值
     * @return
     */
    public T queryForObject(String sql, Class clazz, Object... args);

    public T queryForObject(String sql, ParameterizedRowMapper mapper, Object... args) ;

    /**
     * 查询单一结果集<br/>
     * 并将结果转为<code>Map</code>对象返回
     * @param sql 查询的sql语句
     * @param args 对应sql语句中的参数值
     * @return 以结果集中的列为key，值为value的<code>Map</code>
     */
    @SuppressWarnings("unchecked")
    public Map queryForMap(String sql, Object... args) ;

    /**
     * 查询多行结果集<br/>
     * 并将结果转为<code>List<Map></code>
     * @param sql 查询的sql语句
     * @param args 对应sql语句中的参数值
     * @return  列表中元素为<code>Map</code>的<code>List</code>,<br/>Map结构：以结果集中的列为key，值为value,
     */
    @SuppressWarnings("unchecked")
    public List<Map> queryForList(String sql, Object... args);


    /**
     * 查询多行结果集<br/>
     * 并将结果转为<code>List<T></code>
     * @param sql  查询的sql语句
     * @param mapper 列和对象属性的Mapper
     * @param args 对应sql语句中的参数值
     * @return 列表中元素为<code>T</code>的<code>List</code>
     */
    public List<T> queryForList(String sql, RowMapper mapper, Object... args) ;

    /**
     * 查询多行结果集<br/>
     * 并将结果转为<code>List<T></code>
     * @param sql 查询的sql语句
     * @param clazz <code><T></code>的Class对象
     * @param args 对应sql语句中的参数值
     * @return  列表中元素为<code>T</code>的<code>List</code>
     */
    public List<T> queryForList(String sql, Class clazz, Object... args);


    /**
     * 分页查询
     * @param sql  查询的sql语句
     * @param pageNo 查询的起始页
     * @param pageSize  每页数量
     * @param args  对应sql语句中的参数值
     * @return 分页结果集对象
     */
    public Page queryForPage(String sql, int pageNo, int pageSize, Object... args) ;

    public Page queryForPage(String sql,String countSql,int pageNo, int pageSize, Object... args) ;


    /**
     * 新增数据
     * @param table 表名
     * @param po 要新增的对象，保证对象的属性名和字段名对应
     */
    public void insert(String table, Object po);


    //提供namedjdbctemplate操作功能
    public void update(String sql, Map args) ;

    /**
     * 更新数据
     * @param table 表名
     * @param fields 字段-值Map
     * @param where 更新条件(字段-值Map)
     */
    @SuppressWarnings("unchecked")
    public void update(String table, Map fields, Map where);

    /**
     * 更新数据
     * @param table  表名
     * @param fields 字段-值Map
     * @param where 更新条件,如"a='1' AND b='2'"
     */
    @SuppressWarnings("unchecked")
    public int update(String table, Map fields, String where);

    /*
    * 删除数据
    * */
    public void delete(String sql,Serializable...args);


    /**
     * 获取当前事务最后一次更新的主键值
     * @return
     */
    public Serializable getLastId();
}
