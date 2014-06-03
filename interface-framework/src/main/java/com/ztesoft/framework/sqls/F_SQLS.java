package com.ztesoft.framework.sqls;

import java.util.Map;

public class F_SQLS extends Sqls {
	// static class SingletonHolder {
	// static Sqls sqlsManager = new F_SQLS();
	// }
	static class SingletonHolder {
		static Sqls sqlsManager = new F_SQLS();
		static {
			try {
				Map<String, String> mysqls = sqlsManager.sqls;
				// 以_Hn(省份标示)或_LOCAL结尾
				// String system_tag =
				// StrTools.isEmptyDefault(DcSystemParamUtil.getSysParamByCache("SYSTEM_TAG"),"LOCAL");
				// Class f_sql_local_class =
				// Class.forName(sqlsManager.getClass().getName() + "_" +
				// system_tag);
//				Class f_sql_local_class = Class.forName(sqlsManager.getClass()
//						.getName());
//				Sqls f_sql_local = (Sqls) f_sql_local_class.newInstance();
				
				
			/*	Sqls f_sql_local = (Sqls)PlatService.getPlatClass(sqlsManager.getClass());
				Class f_sql_local_class=f_sql_local.getClass();
				SqlUtil.initSqls(f_sql_local_class, f_sql_local, mysqls);*/
			} catch (Exception ex) {
				System.err.println(ex);
				throw new RuntimeException(ex);
			}
		}
	}

	public static Sqls getInstance() {
		return SingletonHolder.sqlsManager;
	}

	// 相关的SQL放置到集合map中
	public F_SQLS() {
		SqlUtil.initSqls(F_SQLS.class, this, sqls);
	}

	public void main(String[] args) {
		Sqls f = F_SQLS.getInstance();

	}

	private static String SELECT_CORE_B_BF_POINTCUT = "select a.pointcut_id,a.class_path,a.comments from CORE_B_POINTCUT a ,CORE_B_SERVICE_POINTCUT b where a.pointcut_id=b.bf_point_cut_id and a.status_cd='00A' and b.busi_module_code=?";
	private static String SELECT_CORE_B_AF_POINTCUT = "select a.pointcut_id,a.class_path,a.comments from CORE_B_POINTCUT a ,CORE_B_SERVICE_POINTCUT b  where a.pointcut_id=b.af_point_cut_id and a.status_cd='00A' and b.busi_module_code=?";
	private static String INSERT_LOG_RECORED_TABLE = "insert into LOG_RECORED_TABLE(LOG_ID,LOG_TYPE_ID,LOG_LEVEL,OP_ACTION,LOG_NAME,COMMENTS,IN_PARAMS,OUT_PARAMS,CREATOR,system_flag,CREATE_DATE) values(SEQ_LOG_RECORED_TABLE_LOG_ID.NEXTVAL,?,?,?,?,?,?,?,?,?,SYSDATE)";

	private static String SELECT_LOG_RECORD_TABLE = "SELECT LOG_ID,LOG_TYPE_ID,LOG_LEVEL,OP_ACTION,LOG_NAME,COMMENTS,IN_PARAMS,OUT_PARAMS,CREATOR,CREATE_DATE FROM LOG_RECORED_TABLE WHERE 1=1 ";

	private static String SELECT_ATOM_L_REGISTER = "SELECT a.* FROM ATOM_L_REGISTER  a where a.SERVICE_CODE=? and  a.ATOM_CODE=? ";
	private static String SELECT_AN_QUERY_CONDITION = "SELECT a.* FROM AN_QUERY_CONDITION a where a.query_code=?";
	private static String SELECT_AN_QUERY_RESULT = "SELECT a.* FROM AN_QUERY_RESULT a where a.query_code=?";
	private static String SELECT_AN_QUERY_RESULT_FIELD = "SELECT a.* FROM AN_QUERY_RESULT_FIELD a where a.query_code=?";
	private static String SELECT_AN_QUERY_RESULT_GRAPH = "SELECT a.* FROM AN_QUERY_RESULT_GRAPH a where a.query_code=?";

	/**
	 * 以下报表数据
	 */
	//
	// private static final String RTP_QUERY_CONDITION =
	// "select param_code,param_name,query_code,PARAM_TYPE,is_display,def_value,def_sql_code,sub_query_code,table_alias_name from AN_QUERY_CONDITION where query_code=? order by seq";
	private static final String RTP_QUERY_CONDITION = "select * from AN_QUERY_CONDITION where query_code=? order by seq";
	//private static final String RTP_QUERY_RESULT = "select query_code,query_name,query_type,content,query_comments,data_source,is_graph,grapth_type from AN_QUERY_RESULT where query_code=?";
	private static final String RTP_QUERY_RESULT = "select * from AN_QUERY_RESULT where query_code=?";
	// private static final String RTP_QUERY_RESULT_FIELD =
	// "select query_code,field_code,field_name,field_type,comments,is_display,seq,field_resolution,cond_field_code,params_code,page_flag,field_char from AN_QUERY_RESULT_FIELD where query_code=? order by seq";
	private static final String RTP_QUERY_RESULT_FIELD = "select * from AN_QUERY_RESULT_FIELD where query_code=? order by seq";
	private static final String RTP_QUERY_RESULT_GRAPH = "select query_code,graph_code,title,subtitle,xaxis,yaxis,series_name,series_data,graph_type from AN_QUERY_RESULT_GRAPH where query_code=?";
	private static final String RTP_QUERY_CONDITION_REP = "select param_code,query_code,parent_query_code,parent_param_code,param_rel_type,parent_param_name from AN_QUERY_CONDITION_REP where  query_code=?";
	private static final String RTP_QUERY_CONDITION_SYS = "SELECT query_code,param_code,param_type,sql_param_code,table_alias_name,def_value FROM AN_QUERY_CONDITION_SYS WHERE query_code=?";

	private static final String RTP_QUERY_NAME_DEF = "SELECT a.query_code,a.rpt_name,a.rpt_desc,a.rpt_display_type,b.page_flag FROM AN_RPT_NAME_DEFINE a,AN_QUERY_RESULT b where a.query_code = ? and a.query_code  = b.query_code";
	private static final String RTP_QUERY_CONDITION_CONTROL = "select query_code,control_code,control_name,control_param from AN_QUERY_CONDITION_CONTROL where query_code = ?";
	private static final String RTP_QUERY_RESULT_INTERFACE = "select query_code,get_value_key,col1 from AN_QUERY_RESULT_INTERFACE where query_code = ?";
	private static final String RTP_QUERY_DEF_CONDITION = "select query_code,param_code,param_name,param_value from AN_QUERY_DEF_CONDITION  where query_code = ?";

	private static final String RTP_QUERY_CONDITION_PAGING = "select param_code,param_name,query_code,PARAM_TYPE,is_display,def_value,def_sql_code,sub_query_code from AN_QUERY_CONDITION where query_code=? and param_type='PAGING_INFO' ";
	private static final String RTP_QUERY_CONDITION_PARAM_ALIAS = "select param_code,query_code,table_alias_name from AN_QUERY_CONDITION_PARAM_ALIAS WHERE query_code = ? AND param_code = ? ";
	private static final String RTP_QUERY_CONDITION_CHN_POS_REL = "SELECT a.* FROM AN_SQL_CONDITION_CHN_POS_REL a WHERE a.query_code=?";
	private static final String RTP_QUERY_CONDITION_CHN_POS_REL_2 = "SELECT a.* FROM AN_SQL_CONDITION_CHN_POS_REL a WHERE a.query_code=? and a.field_code=? and a.pos_type_id=?";
	private static final String RTP_QUERY_POS_TYPE_REL = "select a.* from AN_QUERY_POS_TYPE_REL a where a.status_cd='00A' and a.pos_type_id in (-1,?) and a.page_item_code=?";
	private static final String RTP_QUERY_MAIN_ITEM = " select a.* from AN_CONF_MAIN_ITEM a where query_code=? ";
	/**
	 * 以下页加载数据
	 */
	private static String SELECT_PAGE_P_ITEM_REL_BUS = "select t.*,p.busi_module_code from  PAGE_P_ITEM  t ,PAGE_MODUL_REL_SERVICE p where t.page_item_id=p.page_item_id and page_id=? order by t.order_id";
	// private static String SELECT_
	private static String SELECT_PAGE_MODULE_REL_SERVICE = "select a.* from PAGE_MODUL_REL_SERVICE a,CORE_B_SERVICE b   where a.busi_module_code=b.busi_module_code and  a.page_item_id in(select page_item_id from PAGE_P_ITEM where page_id=?)";
	private static String SELECT_CORE_B_SERVICE_REL_ATOM = "select t.* from CORE_B_SERVICE_REL_ATOM a,CORE_B_SERVICE b,ATOM_L_REGISTER t where a.busi_module_code=b.busi_module_code and a.atom_reg_id=t.atom_reg_id and a.busi_module_code=?";

	private static String SLEECT_PAGE_P_ITEM = "select a.page_id, a.page_item_id page_item_code, a.page_m_code, a.status_cd ,a.order_id, a.item_tag,a.col1, a.col2, a.col3, a.page_url, a.load_type,a.page_item from PAGE_P_ITEM a where page_id = ? order by a.order_id";
	private static String SLEECT_PAGE_ITEM_BY_CODE = "select a.page_id, a.page_item_id page_item_code, a.page_m_code, a.status_cd ,a.order_id, a.item_tag,a.col1, a.col2, a.col3, a.page_url, a.load_type,a.page_item from PAGE_P_ITEM a where page_item_id = ?";

	/** CommClientService **/

	public static final		String SELECT_OPERATION_BY_CODE = "select * from inf_comm_client_operation  where op_code=? ";
	public static final	 String SELECT_ENDPOINT = "select * from inf_comm_client_endpoint where ep_id=? ";
	public static final	String SELECT_REQUEST = "select * from inf_comm_client_request where req_id=? ";
	public static final	String SELECT_RESPONSE = "select * from inf_comm_client_response where rsp_id=? ";
	public static final	String SELECT_GLOBAL_VARS = "select * from inf_comm_client_gvar where gvar_id=?";
	public static final	String SELECT_LOG_COLS = "select * from inf_comm_client_log_col where op_id=?";
	//
	public static final	String INSERT_CLIENT_CALLLOG = "insert into inf_comm_client_calllog( req_time,"
			+ " rsp_time, op_code, ep_address, req_xml, rsp_xml, state, state_msg,param_desc, result_desc,"
			+ "col1,col2,col3,col4,col5,log_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	public static final	String SELECT_REQUEST_USER = "select * from inf_comm_client_request_user where user_id=? ";;

	public static final	String INSERT_INF_CRM_SPS_LOG = " INSERT INTO inf_crm_sps_log a "
			+ "(order_item_id,transaction_id,service_code,action,create_date,content)VALUES (?,?,?,?,sysdate,?)";

	public static final	String CommClientBO_getRequest = "SELECT req_tpl FROM inf_comm_client_request a where a.req_id =? ";

	public static final	String CommClientBO_getResponse = "SELECT trans_tpl FROM inf_comm_client_response  where rsp_id = ? ";
	
	public static final String AGREE_MENT_ITEM = "select a.agreement_id, a.agreement_code, a.agreement_name, a.agreement_text from agreement a where a.agreement_id = ?";

}
