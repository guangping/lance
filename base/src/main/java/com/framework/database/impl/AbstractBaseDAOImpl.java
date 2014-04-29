package com.framework.database.impl;


import com.framework.database.IBaseDAO;
import com.framework.database.pojo.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-04-24 22:12
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractBaseDAOImpl<T> implements IBaseDAO<T> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected JdbcTemplate jdbcTemplate;

    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void insert(String table, Object po) {

    }

    protected String removeOrders(String hql) {
        Assert.hasText(hql);
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(hql);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    protected String buildPageSql(String sql, int page, int pageSize) {
        StringBuilder builder = new StringBuilder(500);
        builder.append(sql);
        builder.append(" LIMIT " + (page - 1) * pageSize + "," + pageSize);
        return builder.toString();
    }


    @Override
    public Page queryForPage(String sql, int pageNo, int pageSize, Object... args) {
        Assert.hasText(sql, "SQL语句不能为空");
        Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
        String listSql = buildPageSql(sql, pageNo, pageSize);
        String countSql = "SELECT COUNT(1) " + removeOrders(sql);
        List list = queryForList(listSql, args);
        int totalCount = queryForInt(countSql, args);
        return new Page(0, totalCount, pageSize, list);
    }

    @Override
    public Page queryForPage(String sql, String countSql, int pageNo, int pageSize, Object... args) {
        Assert.hasText(sql, "SQL语句不能为空");
        Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
        String listSql = buildPageSql(sql, pageNo, pageSize);
        List list = queryForList(listSql, args);
        int totalCount = queryForInt(countSql, args);
        return new Page(0, totalCount, pageSize, list);
    }


    @Override
    public List<Map> queryForList(String sql, Object... args) {
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql, args);
        List<Map> mysqlMap = new ArrayList<Map>();
        for (Map m : list) {
            Iterator it = m.keySet().iterator();
            Map newMap = new HashMap();
            while (it.hasNext()) {
                String key = (String) it.next();
                if (key != null)
                    newMap.put(key.toLowerCase(), m.get(key));
            }
            mysqlMap.add(newMap);
        }
        return mysqlMap;
    }

    @Override
    public List<T> queryForList(String sql, RowMapper mapper, Object... args) {
        return this.jdbcTemplate.query(sql, args, mapper);
    }

    @Override
    public List<T> queryForList(String sql, Class clazz, Object... args) {
        return this.jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(clazz), args);
    }


    @Override
    public T queryForObject(String sql, Class clazz, Object... args) {
        try {
            Object object = this.jdbcTemplate.queryForObject(sql, ParameterizedBeanPropertyRowMapper.newInstance(clazz), args);
            return (T) object;
        } catch (RuntimeException e) {
            if (logger.isDebugEnabled()) {
                logger.error("查询出错:{},出错sql:{}", new Object[]{e, sql});
            }
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public T queryForObject(String sql, ParameterizedRowMapper mapper, Object... args) {
        try {
            Object obj = this.jdbcTemplate.queryForObject(sql, mapper, args);
            return (T) obj;
        } catch (RuntimeException e) {
            if (logger.isDebugEnabled()) {
                logger.error("查询出错:{},出错sql:{}", new Object[]{e, sql});
            }
            return null;
        }
    }

    @Override
    public Map queryForMap(String sql, Object... args) {
        return this.jdbcTemplate.queryForMap(sql, args);
    }

    @Override
    public void execute(String sql, Object... args) {
        this.jdbcTemplate.update(sql, args);
    }

    @Override
    public long queryForLong(String sql, Object... args) {
        return jdbcTemplate.queryForObject(sql, Long.class, args).longValue();
    }

    @Override
    public int queryForInt(String sql, Object... args) {
        return jdbcTemplate.queryForObject(sql, Integer.class, args).intValue();
    }

    @Override
    public String queryForString(String sql, Object... args) {
        return jdbcTemplate.queryForObject(sql, String.class, args);
    }

    @Override
    public Serializable getLastId() {
        String sql = "SELECT LAST_INSERT_ID() AS ID";
        return jdbcTemplate.queryForObject(sql, String.class);
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
}
