package com.framework.database.impl;

import com.framework.database.IBaseDAO;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-04-25 22:27
 * To change this template use File | Settings | File Templates.
 */
public class DefaultDAOImpl<T> extends AbstractBaseDAOImpl<T> implements IBaseDAO<T> {
    @Override
    public void batchExecute(String sql, List<Object[]> batchArgs) {

    }

    @Override
    public void update(String sql, Object... args) {

    }


    @Override
    public void update(String table, Map fields, Map where) {

    }

    @Override
    public int update(String table, Map fields, String where) {
        return 0;
    }

}
