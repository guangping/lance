package com.framework.database.impl;


import com.framework.database.IBaseDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-04-24 22:12
 * To change this template use File | Settings | File Templates.
 *
 */
public abstract class AbstractBaseDAOImpl<T> implements IBaseDAO<T> {

    protected JdbcTemplate jdbcTemplate;

    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;





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
