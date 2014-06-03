/*    */
package com.powerise.ibss.spring;
/*    */ 
/*    */

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class RowMapperResultReader
        implements ResultReader {
    private final List results;
    private final RowMapper rowMapper;
    private int rowNum = 0;

    public RowMapperResultReader(RowMapper paramRowMapper) {
        this(paramRowMapper, 0);
    }

    public RowMapperResultReader(RowMapper paramRowMapper, int paramInt) {
        this.results = (paramInt > 0 ? new ArrayList(paramInt) : new LinkedList());
        this.rowMapper = paramRowMapper;
    }

    public void processRow(ResultSet paramResultSet) throws SQLException {
        this.results.add(this.rowMapper.mapRow(paramResultSet, this.rowNum++));
    }

    public List getResults() {
        return this.results;
    }
}

/* Location:           C:\Users\Administrator\Desktop\spring-1.2.6.jar
 * Qualified Name:     org.springframework.jdbc.core.RowMapperResultReader
 * JD-Core Version:    0.6.0
 */