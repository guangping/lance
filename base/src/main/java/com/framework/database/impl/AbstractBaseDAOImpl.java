package com.framework.database.impl;


import com.framework.database.IBaseDAO;
import com.framework.database.pojo.Page;
import com.framework.utils.ReflectionUtil;
import com.framework.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;
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
    public String insert(String table, Map arg) {
        Assert.hasText(table, "表名不能为空!");
        Assert.notNull(arg, "参数不能为空!");
        Assert.notEmpty(arg, "参数不能为空!");
        List<String> column = new ArrayList<String>();
        for (Object obj : arg.keySet()) {
            column.add(String.valueOf(obj));
        }
        SqlParameterSource parameterSource = new MapSqlParameterSource(arg);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            this.namedParameterJdbcTemplate.update(getInsertSql(table, column), parameterSource, keyHolder);
            return String.valueOf(keyHolder.getKey());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String insert(String table, Object arg) {
        Assert.hasText(table, "表名不能为空!");
        Assert.notNull(arg, "参数不能为空!");
        String sql = getInsertSql(table, ReflectionUtil.getFields(arg.getClass()));
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(arg);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            this.namedParameterJdbcTemplate.update(sql, parameterSource, keyHolder);
            return String.valueOf(keyHolder.getKey());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /*
    *获取插入sql
    * **/
    private String getInsertSql(String table, List<String> columns) {
        StringBuffer buffer = new StringBuffer(1000);
        buffer.append("INSERT INTO ");
        buffer.append(table);
        buffer.append("(");
        for (String key : columns) {
            buffer.append(key.toUpperCase());
            buffer.append(",");
        }
        buffer.deleteCharAt(buffer.length() - 1);
        buffer.append(") VALUES(");
        for (String key : columns) {
            buffer.append(":");
            buffer.append(key);
            buffer.append(",");
        }
        buffer.deleteCharAt(buffer.length() - 1);
        buffer.append(")");
        return buffer.toString();
    }


    @Transactional
    @Override
    public void batchInsert(String table, List<Object> list) {
        Assert.hasText(table, "表名不能为空!");
        Assert.notNull(list, "参数不能为空!");
        Assert.notEmpty(list, "参数不能为空!");

        List<String> columns = new ArrayList<String>();
        Object param = list.get(0);
        if (param instanceof Map) {
            Map arg = (HashMap) param;
            for (Object obj : arg.keySet()) {
                columns.add(String.valueOf(obj));
            }
        } else {
            columns = ReflectionUtil.getFields(param.getClass());
        }
        String sql = getInsertSql(table, columns);
        List<SqlParameterSource> params = new ArrayList<SqlParameterSource>();
        for (Object obj : list) {
            if (obj instanceof Map) {
                params.add(new MapSqlParameterSource((Map) obj));
            } else {
                params.add(new BeanPropertySqlParameterSource(obj));
            }
        }
        try {
            this.namedParameterJdbcTemplate.batchUpdate(sql, params.toArray(new SqlParameterSource[]{}));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("批量插入数据出错!");
        }
    }

    protected String removeOrders(String hql) {
        Assert.hasText(hql);
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
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
        String countSql = "SELECT COUNT(1) count from (" + removeOrders(sql)+") ty";
        List list = queryForList(listSql, args);
        int totalCount = queryForInt(countSql, args);
        return new Page(pageNo, totalCount, pageSize, list);
    }

    @Override
    public Page queryForPage(String sql, String countSql, int pageNo, int pageSize, Class<T> clazz, Object... args) {
        Assert.hasText(sql, "SQL语句不能为空");
        Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
        String listSql = buildPageSql(sql, pageNo, pageSize);
        List<T> list = queryForList(listSql, clazz,args);
        int totalCount = queryForInt(countSql, args);
        return new Page(pageNo, totalCount, pageSize, list);
    }

    @Override
    public Page queryForPage(String sql, String countSql, int pageNo, int pageSize, Object... args) {
        Assert.hasText(sql, "SQL语句不能为空");
        Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
        String listSql = buildPageSql(sql, pageNo, pageSize);
        List list = queryForList(listSql, args);
        int totalCount = queryForInt(countSql, args);
        return new Page(pageNo, totalCount, pageSize, list);
    }


    @Override
    public Page queryForPage(String sql, int pageNo, int pageSize, Class<T> clazz, Object... args) {
        Assert.hasText(sql, "SQL语句不能为空");
        Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
        String listSql = buildPageSql(sql, pageNo, pageSize);
        String countSql = "SELECT COUNT(1) count from (" + removeOrders(sql)+") ty";
        List<T> list = queryForList(listSql, clazz,args);
        int totalCount = queryForInt(countSql, args);
        return new Page(pageNo, totalCount, pageSize, list);
    }

    @Override
    public Page queryForPage(String sql, String countSql, int pageNo, int pageSize, RowMapper rowMapper, Object... args) {
        Assert.hasText(sql, "SQL语句不能为空");
        Assert.isTrue(pageNo >= 1, "pageNo 必须大于等于1");
        String listSql = buildPageSql(sql, pageNo, pageSize);
        List<T> list = queryForList(listSql, rowMapper,args);
        int totalCount = queryForInt(countSql, args);
        return new Page(pageNo, totalCount, pageSize, list);
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

        try {
            this.jdbcTemplate.update(sql, args);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
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

    @Override
    public void update(String sql, Object... args) {
        Assert.hasText(sql, "sql不能为空!");
        try {
            this.jdbcTemplate.update(sql, args);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    public void updateNamedParameter(String sql, Object arg) {
        Assert.hasText(sql, "sql不能为空!");
        Assert.notNull(arg, "参数不能为空!");
        SqlParameterSource parameterSource = null;
        if (arg instanceof Map) {
            parameterSource = new MapSqlParameterSource((HashMap) arg);
        } else {
            parameterSource = new BeanPropertySqlParameterSource(arg);
        }
        try {
            this.namedParameterJdbcTemplate.update(sql, parameterSource);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void update(String table, Map fields, Map where) {
        Assert.hasText(table, "表名不能为空!");
        Assert.notEmpty(fields, "修改字段不能为空!");
        StringBuffer buffer = new StringBuffer(1000);
        buffer.append("UPDATE ");
        buffer.append(table);
        buffer.append(" SET ");
        for (Object obj : fields.keySet()) {
            buffer.append(" ");
            buffer.append(obj);
            buffer.append("=:");
            buffer.append(obj);
            buffer.append(",");
        }
        buffer.deleteCharAt(buffer.length() - 1);
        buffer.append(" WHERE 1=1");
        for (Object obj : where.keySet()) {
            buffer.append(" AND ");
            buffer.append(obj);
            buffer.append("=:");
            buffer.append(obj);
            buffer.append(",");
        }
        buffer.deleteCharAt(buffer.length() - 1);
        Map params = new HashMap();
        params.putAll(fields);
        params.putAll(where);
        SqlParameterSource parameterSource = new MapSqlParameterSource(params);

        try {
            this.namedParameterJdbcTemplate.update(buffer.toString(), parameterSource);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }


    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * 格式化列名 只适用于Mysql
     *
     * @param col
     * @return
     */
    protected String quoteCol(String col) {
        if (StringUtils.isBlank(col)) {
            return "";
        } else {
            return col;
        }
    }

    /**
     * 格式化值 只适用于Mysql
     *
     * @param value
     * @return
     */
    protected String quoteValue(String value) {
        if (StringUtils.isBlank(value)) {
            return "''";
        } else {
            return "'" + value.replaceAll("'", "''") + "'";
        }
    }
}
