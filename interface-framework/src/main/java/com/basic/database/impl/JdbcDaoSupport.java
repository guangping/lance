package com.basic.database.impl;


import com.basic.database.IDaoSupport;
import com.basic.database.exception.DBRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-02-12 15:39
 * To change this template use File | Settings | File Templates.
 */
public class JdbcDaoSupport<T> implements IDaoSupport<T> {
    private int DB_DEFAULT = 1;//数据库类型 1 mysql 2 oracle

    private Logger logger = LoggerFactory.getLogger(DBRuntimeException.class);



    private JdbcTemplate jdbcTemplate;


    public void execute(String sql) {
        try {
            this.jdbcTemplate.execute(sql);
        } catch (Exception e) {
            throw new DBRuntimeException(e, sql);
        }
    }

    @Override
    public int queryForInt(String sql, Object... args) {
        try {
            return this.jdbcTemplate.queryForInt(sql, args);
        } catch (RuntimeException e) {
            this.logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public String queryForString(String sql) {
        String s = "";
        try {
            s = (String) this.jdbcTemplate.queryForObject(sql, String.class);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return s;
    }

    public String queryForString(String sql, Object... args) {
        String s = "";
        try {
            s = (String) this.jdbcTemplate.queryForObject(sql, String.class, args);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return s;
    }

    @SuppressWarnings("unchecked")
    public List queryForList(String sql, Object... args) {
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql, args);

        return list;
    }

    public List<T> queryForList(String sql, RowMapper mapper, Object... args) {
        try {
            return this.jdbcTemplate.query(sql, args, mapper);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new DBRuntimeException(ex, sql);
        }
    }

    public List<T> queryForList(String sql, Class clazz, Object... args) {
        return this.jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(clazz), args);
    }

    public long queryForLong(String sql, Object... args) {
        return this.jdbcTemplate.queryForLong(sql, args);
    }

    public Map queryForMap(String sql, Object... args) {
        try {
            Map map = this.jdbcTemplate.queryForMap(sql, args);
            Map newMap = new HashMap();
            Iterator keyItr = map.keySet().iterator();
            while (keyItr.hasNext()) {
                String key = (String) keyItr.next();
                Object value = map.get(key);
                newMap.put(key.toLowerCase(), value);
            }
            return newMap;
        } catch (Exception ex) {
            ex.printStackTrace();
            //throw new ObjectNotFoundException(ex, sql);
            return null;
        }
    }

    public T queryForObject(String sql, Class clazz, Object... args) {
        try {
            return (T) jdbcTemplate.queryForObject(sql, ParameterizedBeanPropertyRowMapper.newInstance(clazz), args);
        } catch (Exception ex) {
            this.logger.error("查询出错", ex);
            return null;
        }
    }

    public void update(String sql, Map args) {
        try {
            this.jdbcTemplate.update(sql, args);
        } catch (RuntimeException e) {
            throw new DBRuntimeException(e, sql);
        }
    }

    public void update(String sql, Object... args) {
        try {
            this.jdbcTemplate.update(sql, args);
        } catch (RuntimeException e) {
            throw new DBRuntimeException(e, sql);
        }
    }

    @Override
    public void insert(String table, Class clazz) {

    }

    @Override
    public void insert(String sql, Object... args) {
        try {
            this.jdbcTemplate.update(sql, args);
        } catch (RuntimeException e) {
            throw new DBRuntimeException(e, sql);
        }
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
