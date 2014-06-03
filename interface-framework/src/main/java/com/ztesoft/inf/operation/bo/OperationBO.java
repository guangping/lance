package com.ztesoft.inf.operation.bo;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.inf.framework.dao.SqlExe;
import com.ztesoft.inf.operation.vo.Operation;
import com.ztesoft.inf.operation.vo.OperationArg;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OperationBO {

	private  SqlExe sqlExe = new SqlExe();
	private static String OPERATION = "select * from inf_operation where operation_code=?";
	private static String OP_ARGS = "select * from inf_operation_arg where operation_code=? and arg_type=? order by arg_index ";

	public Operation getOperationByCode(String operationCode)
			throws FrameException, SQLException {
		Map map = sqlExe.queryForMap(OPERATION, operationCode);
		if (map == null || map.isEmpty())
			return null;
		Operation operation = new Operation(map);
		String[] params = new String[] { operationCode, "IN" };
		List maps = sqlExe.queryForList(OP_ARGS, params);
		List args = new ArrayList();
		for (int i = 0; i < maps.size(); i++) {
			Map map2 = (Map) maps.get(i);
			OperationArg operationArg = new OperationArg(map2);
			args.add(operationArg);
		}
		operation.setOperationInArgs(args);
		params = new String[] { operationCode, "OUT" };
		map = sqlExe.queryForMap(OP_ARGS, params);
		if (map == null || map.isEmpty()) {
			throw new RuntimeException("取INF_OPERATION_ARG(operation_code="
					+ operationCode + ")配置参数时错误，一个OPERATION只能有一个输出参数!");
		} else {
			operation.setResultArg(new OperationArg(map));
		}
		return operation;
	}
}

