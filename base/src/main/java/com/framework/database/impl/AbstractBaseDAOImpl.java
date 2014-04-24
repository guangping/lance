package com.framework.database.impl;


import com.framework.database.IBaseDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.io.Serializable;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-04-24 22:12
 * To change this template use File | Settings | File Templates.
 *
 */
public abstract class AbstractBaseDAOImpl<T> implements IBaseDAO<T> {
    protected Logger logger= LoggerFactory.getLogger(getClass());

    protected JdbcTemplate jdbcTemplate;

    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public T queryForObject(String sql, Class clazz, Object... args) {
       try{
           Object object=this.jdbcTemplate.queryForObject(sql, ParameterizedBeanPropertyRowMapper.newInstance(clazz),args);
           return (T)object;
       }catch (RuntimeException e){
           if(logger.isErrorEnabled()){
               logger.error("查询出错:{}",e);
           }
           e.printStackTrace();
           return null;
       }
    }

    @Override
    public int queryForIntByMap(String sql, Map args) {
        return 0;
    }

    @Override
    public T queryForObject(String sql, ParameterizedRowMapper mapper, Object... args) {
        return null;
    }

    @Override
    public Map queryForMap(String sql, Object... args) {
        return null;
    }

    @Override
    public void execute(String sql, Object... args) {
        this.jdbcTemplate.update(sql,args);
    }

    @Override
    public long queryForLong(String sql, Object... args) {
        return jdbcTemplate.queryForObject(sql,Long.class).longValue();
    }

    @Override
    public int queryForInt(String sql, Object... args) {
        return jdbcTemplate.queryForObject(sql,Integer.class).intValue();
    }

    @Override
    public String queryForString(String sql, Object... args) {
        return jdbcTemplate.queryForObject(sql,String.class);
    }

    @Override
    public Serializable getLastId() {
        String sql="SELECT last_insert_id() as id";
        return jdbcTemplate.queryForObject(sql,String.class);
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
}
