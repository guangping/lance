package com.ztesoft.inf.framework.dao;

import com.ztesoft.ibss.common.dao.DAOUtils;
import org.springframework.jdbc.core.ColumnMapRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class LowwerCaseColumnMapRowMapper extends ColumnMapRowMapper {

	private boolean useStrValue;

	public LowwerCaseColumnMapRowMapper(boolean useStrValue) {
		this.useStrValue = useStrValue;
	}
	protected String getColumnKey(String columnName) {
		return columnName.toLowerCase();
	}
	protected Object getColumnValue(ResultSet rs, int index) throws SQLException {
		Object obj = super.getColumnValue(rs, index);
		if (!useStrValue)
			return obj;
		if (obj instanceof Timestamp) {
			obj = DAOUtils.getFormatedDateTime((Timestamp) obj);
		} else {
			obj = rs.getString(index);
		}
		return obj;
	}
}

