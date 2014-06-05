package com.ztesoft.inf.communication.client.bo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.ibss.common.dao.DAOUtils;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.config.JNDINames;
import com.ztesoft.framework.sqls.SF;
import com.ztesoft.inf.communication.client.vo.ClientEndPoint;
import com.ztesoft.inf.communication.client.vo.ClientGlobalVar;
import com.ztesoft.inf.communication.client.vo.ClientOperation;
import com.ztesoft.inf.communication.client.vo.ClientRequest;
import com.ztesoft.inf.communication.client.vo.ClientRequestUser;
import com.ztesoft.inf.communication.client.vo.ClientResponse;
import com.ztesoft.inf.framework.dao.SeqUtil;
import com.ztesoft.inf.framework.dao.SqlExe;

public class CommClientBO {

	private static final String SELECT_OPERATION_BY_CODE = SF
			.frameworkSql("SELECT_OPERATION_BY_CODE");
	private static final String SELECT_ENDPOINT = SF
			.frameworkSql("SELECT_ENDPOINT");
	private static final String SELECT_REQUEST = SF
			.frameworkSql("SELECT_REQUEST");
	private static final String SELECT_RESPONSE = SF
			.frameworkSql("SELECT_RESPONSE");
	private static final String SELECT_GLOBAL_VARS = SF
			.frameworkSql("SELECT_GLOBAL_VARS");
	private static final String SELECT_LOG_COLS = SF
			.frameworkSql("SELECT_LOG_COLS");
	private static final String SELECT_REQUEST_USER = SF
			.frameworkSql("SELECT_REQUEST_USER");

	//
	private static final String INSERT_CLIENT_CALLLOG = SF
			.frameworkSql("INSERT_CLIENT_CALLLOG");

	private static final String INSERT_INF_CRM_SPS_LOG = SF
			.frameworkSql("INSERT_INF_CRM_SPS_LOG");

	 private SqlExe sqlExec = new SqlExe();

	public Map getLogColsByOpId(String opId) throws FrameException {
		Map logCols = new HashMap();
		List list = null;
		try {
			list = sqlExec.queryForList(SELECT_LOG_COLS, opId);
		} catch (Exception e) {

		}
		if (list != null) {
			for (Object obj : list) {
				Map item = (Map) obj;
				logCols.put(item.get("col_name"), item.get("param_key"));
			}
		}
		return logCols;
	}

	public ClientOperation getOperationByCode(String operationCode)
			throws FrameException {
		Map map = null;
		try {
			map = sqlExec.queryForMap(SELECT_OPERATION_BY_CODE, operationCode);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (map == null)
			return null;
		return new ClientOperation(map);
	}

	public ClientEndPoint getEndPoint(String endpointId) throws FrameException {
		Map map = null;
		try {
			map = sqlExec.queryForMap(SELECT_ENDPOINT, endpointId);

		} catch (Exception e) {

		}
		if (map == null)
			return null;
		return new ClientEndPoint(map);
	}

	public ClientRequest getRequest(String requestId) throws FrameException {

		Map map = null;
		try {
			map = sqlExec.queryForMap(SELECT_REQUEST, requestId);
		} catch (Exception e) {

		}
		if (map == null)
			return null;

		ClientRequest cl = new ClientRequest(map);
		// String SQL = SF.frameworkSql("CommClientBO_getRequest");
		// String tpl = getBlob(SQL, requestId);
		//
		// cl.setTemplate(tpl);
		return cl;
	}

	public ClientResponse getResponse(String responseId) throws FrameException {
		Map map = null;
		try {
			map = sqlExec.queryForMap(SELECT_RESPONSE, responseId);

		} catch (Exception e) {

		}
		if (map == null)
			return null;
		ClientResponse cl = new ClientResponse(map);
		// String SQL = SF.frameworkSql("CommClientBO_getResponse");
		// String tpl = getBlob(SQL, responseId);
		// cl.setTransform(tpl);

		return cl;
	}

	public ClientRequestUser getRequestUser(String userId)
			throws FrameException {
		/*Map map = null;
		try {
			map = sqlExec.queryForMap(SELECT_REQUEST_USER, userId);
		} catch (Exception e) {

		}
		if (map == null)
			return new ClientRequestUser();
		return new ClientRequestUser(map);*/

        return new ClientRequestUser();
	}

	public void logCall(final List args) throws Exception {
		if (args == null || args.size() != 15) {
			// throw new IllegalArgumentException("参数为空，或参数的个数不符合要求");
		}
		String reqXml = (String) args.get(4);
		if (args.get(4) != null) {
			reqXml = args.get(4).toString();
		} else {
			reqXml = "";
		}
		final ByteArrayInputStream reqStream = new ByteArrayInputStream(
				reqXml.getBytes("GBK"));
		args.set(4, reqStream);

		String rsqXml = (String) args.get(5);
		if (args.get(5) != null) {
			rsqXml = args.get(5).toString();
		} else {
			rsqXml = "";
		}
		final ByteArrayInputStream rspStream = new ByteArrayInputStream(
				rsqXml.getBytes("GBK"));
		args.set(5, rspStream);
		String seq = new SeqUtil().getNextSequence("INF_COMM_CLIENT_CALLLOG", "LOG_ID");
		args.add(seq);

		final int reqLength = reqXml.getBytes("GBK").length;
		final int rsqLength = rsqXml.getBytes("GBK").length;
		try {
			sqlExec.update(INSERT_CLIENT_CALLLOG,
					new PreparedStatementSetter() {
						public void setValues(PreparedStatement ps)
								throws SQLException {
							int i = 0;
							ps.setTimestamp(++i, (Timestamp) args.get(i - 1));
							ps.setTimestamp(++i, (Timestamp) args.get(i - 1));
							ps.setString(++i, (String) args.get(i - 1));
							ps.setString(++i, (String) args.get(i - 1));
							ps.setBinaryStream(++i, reqStream, reqLength);
							ps.setBinaryStream(++i, rspStream, rsqLength);
							ps.setString(++i, (String) args.get(i - 1));
							ps.setString(++i, StringUtils.substr(
									(String) args.get(i - 1), 2000));
							ps.setString(++i, StringUtils.substr(
									(String) args.get(i - 1), 2000));
							ps.setString(++i, StringUtils.substr(
									(String) args.get(i - 1), 2000));
							ps.setString(++i, StringUtils.substr(
									(String) args.get(i - 1), 2000));
							ps.setString(++i, StringUtils.substr(
									(String) args.get(i - 1), 2000));
							ps.setString(++i, StringUtils.substr(
									(String) args.get(i - 1), 2000));
							ps.setString(++i, StringUtils.substr(
									(String) args.get(i - 1), 2000));
							ps.setString(++i, StringUtils.substr(
									(String) args.get(i - 1), 2000));
							ps.setString(++i, (String) args.get(i - 1));
						}
					});
		} catch (Exception e) {

		}
	}

	public ClientGlobalVar getGlobalVars(String globalVarsId) {

		if (StringUtils.isEmpty(globalVarsId)
				|| StringUtils.isEmpty(globalVarsId.trim()))
			return null;
		Map map = null;
		try {
			map = sqlExec.queryForMap(SELECT_GLOBAL_VARS, globalVarsId);
		} catch (Exception e) {

		}
		if (map == null)
			return null;
		return new ClientGlobalVar(map);
	}

	public void logINF_CRM_SPS_LOG(final List args) throws Exception {

		String content = (String) args.get(4);
		if (content != null && !"".endsWith(content)) {
			InputStream xml = new ByteArrayInputStream(content.getBytes("GBK"));
			args.add(4, xml);
		}
		try {
			sqlExec.update(INSERT_INF_CRM_SPS_LOG,
					new PreparedStatementSetter() {
						public void setValues(PreparedStatement ps)
								throws SQLException {
							int i = 0;
							ps.setString(++i, (String) args.get(i - 1));
							ps.setString(++i, (String) args.get(i - 1));
							ps.setString(++i, (String) args.get(i - 1));
							ps.setString(++i, (String) args.get(i - 1));
							InputStream content = (InputStream) args.get(i);
							int islen;
							try {
								if (content != null) {
									islen = content.available();
								} else {
									islen = 0;
								}

							} catch (IOException e) {
								throw new RuntimeException(e.getMessage());
							}
							ps.setBinaryStream(++i, content, islen);
						}
					});
		} catch (Exception e) {

		}
	}

	/**
	 * 取得大字段信息
	 * 
	 * @param queryCode
	 * @return
	 */
	private String getBlob(String strSql, String id) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			conn = ConnectionContext.getContext().getConnection(
					JNDINames.INF_DATASOURCE);

			stmt = conn.prepareStatement(strSql);
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				java.sql.Blob blob = rs.getBlob(1);
				if (blob != null) {
					byte[] bdata = blob.getBytes(1, (int) blob.length());
					String queryContent = new String(bdata);
					return queryContent;
				}
			}

			return "";
		} catch (Exception se) {

			throw new DAOSystemException("SQLException while getting "
					+ "blob:\n" + se.getMessage(), se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
		}
	}
}
