package com.flickr.db.pool;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONObject;
import com.flickr.db.pojo.Page;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-08-27 16:07
 * To change this template use File | Settings | File Templates.
 * <p/>
 * 数据操作封装类
 */
public class DBExecutors implements IDBExecutors {

    @Override
    public void executeProc(String sql, Object... params) {
        CallableStatement callableStatement = null;
        DruidPooledConnection connection = null;
        try {
            connection = DruidPool.instance().getConnection();
            callableStatement = connection.prepareCall(sql);
            if (null != params) {
                for (int i = 0; i < params.length; i++) {
                    callableStatement.setObject((i + 1), params[i]);
                }
            }
            callableStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStatement(callableStatement);
            closeConnection(connection);
        }
    }

    @Override
    public boolean execute(String sql) {
        boolean rval = false;
        DruidPooledConnection connection = null;
        Statement statement = null;
        try {
            connection = DruidPool.instance().getConnection();
            statement = connection.createStatement();
            rval = statement.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
            closeConnection(connection);
            return rval;
        }
    }


    @Override
    public void executeBatch(List<String> sqls) {
        DruidPooledConnection connection = null;
        Statement statement = null;
        try {
            connection = DruidPool.instance().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            for (String sql : sqls) {
                statement.addBatch(sql);
            }
            statement.executeBatch();
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }

    /*
         * 执行一句查询的sql
         */
    public Map queryForMap(String sql, Object... params) {
        Map result = new HashMap();
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        DruidPooledConnection connection = null;
        try {
            connection = DruidPool.instance().getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (null != params) {
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setObject((i + 1), params[i]);
                }
            }
            rs = preparedStatement.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            rs.first();
            for (int i = 1; i <= cols; i++) {
                result.put(rsmd.getColumnName(i).toLowerCase(), rs.getString(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
            closeStatement(preparedStatement);
            closeConnection(connection);
            return result;
        }
    }

    /*
    * 执行一句查询的sql
    */
    public List queryForList(String sql, Object... params) {
        List list = new ArrayList();
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        DruidPooledConnection connection = null;
        try {
            connection = DruidPool.instance().getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (null != params) {
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setObject((i + 1), params[i]);
                }
            }
            rs = preparedStatement.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            Map result = null;
            while (rs.next()) {
                result = new HashMap();
                for (int i = 1; i <= cols; i++) {
                    result.put(rsmd.getColumnName(i).toLowerCase(), rs.getString(i));
                }
                list.add(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
            closeStatement(preparedStatement);
            closeConnection(connection);
            return list;
        }
    }

    public int getInt(String sql, Object... params) {
        int rval = 0;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        DruidPooledConnection connection = null;
        try {
            connection = DruidPool.instance().getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (null != params) {
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setObject((i + 1), params[i]);
                }
            }
            rs = preparedStatement.executeQuery();
            if (rs.first()) {
                rval = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
            closeStatement(preparedStatement);
            closeConnection(connection);
            return rval;
        }
    }


    public long getLong(String sql, Object... params) {
        long rval = 0;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        DruidPooledConnection connection = null;
        try {
            connection = DruidPool.instance().getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (null != params) {
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setObject((i + 1), params[i]);
                }
            }
            rs = preparedStatement.executeQuery();
            if (rs.first()) {
                rval = rs.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
            closeStatement(preparedStatement);
            closeConnection(connection);
            return rval;
        }
    }

    public float getFloat(String sql, Object... params) {
        float rval = 0;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        DruidPooledConnection connection = null;
        try {
            connection = DruidPool.instance().getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (null != params) {
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setObject((i + 1), params[i]);
                }
            }
            rs = preparedStatement.executeQuery();
            if (rs.first()) {
                rval = rs.getFloat(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
            closeStatement(preparedStatement);
            closeConnection(connection);
            return rval;
        }
    }

    public double getDouble(String sql, Object... params) {
        double rval = 0;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        DruidPooledConnection connection = null;
        try {
            connection = DruidPool.instance().getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (null != params) {
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setObject((i + 1), params[i]);
                }
            }
            rs = preparedStatement.executeQuery();
            if (rs.first()) {
                rval = rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
            closeStatement(preparedStatement);
            closeConnection(connection);
            return rval;
        }
    }


    public String getString(String sql, Object... params) {
        String rval = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        DruidPooledConnection connection = null;
        try {
            connection = DruidPool.instance().getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (null != params) {
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setObject((i + 1), params[i]);
                }
            }
            rs = preparedStatement.executeQuery();
            if (rs.first()) {
                rval = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
            closeStatement(preparedStatement);
            closeConnection(connection);
            return rval;
        }
    }

    @Override
    public void delete(String sql, Object... params) {
        DruidPooledConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DruidPool.instance().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            if (null != params) {
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setObject((i + 1), params[i]);
                }
            }
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    @Override
    public void update(String sql, Object... params) {
        DruidPooledConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DruidPool.instance().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            if (null != params) {
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setObject((i + 1), params[i]);
                }
            }
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    @Override
    public String insert(String sql, Object... params) {
        String id = null;
        ResultSet rs = null;
        DruidPooledConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DruidPool.instance().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            if (null != params) {
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setObject((i + 1), params[i]);
                }
            }
            preparedStatement.executeUpdate();
            String last_id = "SELECT LAST_INSERT_ID()";
            rs = preparedStatement.executeQuery(last_id);
            rs.first();
            id = rs.getString(1);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
            closeStatement(preparedStatement);
            closeConnection(connection);

            return id;
        }
    }

    @Override
    public void insertBatch(String sql, List<Object[]> params) {
        DruidPooledConnection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DruidPool.instance().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            for (Object[] obj : params) {
                for (int i = 0; i < obj.length; i++) {
                    preparedStatement.setObject((i + 1), obj[i]);
                }
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    @Override
    public <T> T queryForObject(String sql, Class<T> clazz, Object... args) {
        Map map = this.queryForMap(sql, args);
        return JSONObject.parseObject(JSONObject.toJSONString(map), clazz);
    }

    @Override
    public <T> List<T> queryForList(String sql, Class<T> clazz, Object... args) {
        List list = this.queryForList(sql, args);

        return JSONObject.parseArray(JSONObject.toJSONString(list), clazz);
    }

    @Override
    public Page queryForMapPage(String sql, String countSql, int pageNo, int pageSize, Object... args) {
        if (pageNo < 1) {
            throw new RuntimeException("pageNo 必须大于等于1!");
        }
        List list = this.queryForList(buildPageSql(sql, pageNo, pageSize), args);
        int totalCount = getInt(countSql, args);

        return new Page(pageNo, totalCount, pageSize, list);
    }

    @Override
    public Page queryForObjectPage(String sql, String countSql, int pageNo, int pageSize, Class clazz, Object... args) {
        List list = this.queryForList(buildPageSql(sql, pageNo, pageSize), clazz, args);
        int totalCount = getInt(countSql, args);

        return new Page(pageNo, totalCount, pageSize, list);
    }

    private String buildPageSql(String sql, int page, int pageSize) {
        StringBuilder builder = new StringBuilder(500);
        builder.append(sql);
        builder.append(" LIMIT " + (page - 1) * pageSize + "," + pageSize);
        return builder.toString();
    }

    private void closeResultSet(ResultSet set) {
        try {
            if (null != set && !set.isClosed()) {
                set.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void closeStatement(Statement statement) {
        try {
            if (null != statement && !statement.isClosed()) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection(DruidPooledConnection connection) {
        try {
            if (null != connection && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
