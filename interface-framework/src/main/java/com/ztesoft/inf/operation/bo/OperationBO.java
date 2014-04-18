/*    */ package com.ztesoft.inf.operation.bo;
/*    */ 
/*    */ import com.powerise.ibss.framework.FrameException;
/*    */ import com.ztesoft.inf.framework.dao.SqlExe;
/*    */ import com.ztesoft.inf.operation.vo.Operation;
/*    */ import com.ztesoft.inf.operation.vo.OperationArg;
/*    */ import java.sql.SQLException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class OperationBO
/*    */ {
/* 15 */   private SqlExe sqlExe = new SqlExe();
/* 16 */   private static String OPERATION = "select * from inf_operation where operation_code=?";
/* 17 */   private static String OP_ARGS = "select * from inf_operation_arg where operation_code=? and arg_type=? order by arg_index ";
/*    */ 
/*    */   public Operation getOperationByCode(String operationCode) throws FrameException, SQLException
/*    */   {
/* 21 */     Map map = this.sqlExe.queryForMap(OPERATION, operationCode);
/* 22 */     if ((map == null) || (map.isEmpty()))
/* 23 */       return null;
/* 24 */     Operation operation = new Operation(map);
/* 25 */     String[] params = { operationCode, "IN" };
/* 26 */     List maps = this.sqlExe.queryForList(OP_ARGS, params);
/* 27 */     List args = new ArrayList();
/* 28 */     for (int i = 0; i < maps.size(); i++) {
/* 29 */       Map map2 = (Map)maps.get(i);
/* 30 */       OperationArg operationArg = new OperationArg(map2);
/* 31 */       args.add(operationArg);
/*    */     }
/* 33 */     operation.setOperationInArgs(args);
/* 34 */     params = new String[] { operationCode, "OUT" };
/* 35 */     map = this.sqlExe.queryForMap(OP_ARGS, params);
/* 36 */     if ((map == null) || (map.isEmpty())) {
/* 37 */       throw new RuntimeException("取INF_OPERATION_ARG(operation_code=" + operationCode + ")配置参数时错误，一个OPERATION只能有一个输出参数!");
/*    */     }
/*    */ 
/* 40 */     operation.setResultArg(new OperationArg(map));
/*    */ 
/* 42 */     return operation;
/*    */   }
/*    */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.operation.bo.OperationBO
 * JD-Core Version:    0.6.2
 */