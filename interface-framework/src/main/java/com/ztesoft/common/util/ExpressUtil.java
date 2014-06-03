package com.ztesoft.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ztesoft.common.dao.DAOSystemException;

/**
 * 表达式处理工具
 * 
 *
 */
public class ExpressUtil {

	public static ExpreeResult parse(String sourceExpress, Map params)
			throws Exception {
		List varList = new ArrayList(); // 记录SQL语句中的变量

		try {
			// 分析可选URI语句
			sourceExpress = replaceOptionSqlByParam(sourceExpress, params);

			// 分析直接替换的变量
			sourceExpress = replaceSqlByParam(sourceExpress, params);

			// 用正则解析出变量
			Pattern p = Pattern.compile(Constraint.FIND_QUERY_VARIABLE_REXP);
			Matcher m = p.matcher(sourceExpress);
			
			while (m.find()) {
				int start = m.start();
				int end = m.end();
				String variable = sourceExpress.substring(start, end);

				// 去掉首尾的多余字符串
				varList.add(variable.substring(2, variable.length() - 1));
			}

			// 取得变量的值，供查询使用
			// List<String> queryParamsList = new ArrayList<String>();
			List queryParamsList = new ArrayList();

			// for (String varName : varList) {
			for (int i = 0; i < varList.size(); i++) {
				String varName = (String) varList.get(i);
				String varNameBuf = varName;

				if (varName.startsWith("%") || varName.endsWith("%")) {
					// varNameBuf = varName.replace("%", "");
					varNameBuf = varName.replaceAll("%", "");
				}

				if (!params.containsKey(varNameBuf.toLowerCase())&&!params.containsKey(varNameBuf)) {
					throw new DAOSystemException("未能找到入参变量："
							+ varName.toUpperCase());
				}

				// queryParamsList.add(varName.replace(varNameBuf,
				// params.get(varNameBuf.toLowerCase()).toString()));
				queryParamsList.add(varName.replaceAll(varNameBuf, StringUtils.isEmptyDefault(
						StringUtils.safe((String)params.get(varNameBuf.toLowerCase())),
						StringUtils.safe((String)params.get(varNameBuf)))));
			}

			ExpreeResult expreeResult=new ExpreeResult();
			// 将查询语句中的变量替换成问号
			expreeResult.setExpree(sourceExpress.replaceAll(Constraint.FIND_QUERY_VARIABLE_REXP, "?"));
			// 解析后的查询变量值
			expreeResult.setParam(queryParamsList);
			

			return expreeResult;
		} catch (Exception e) {
			// e.printStackTrace();
			// return new HashMap();
			throw e;
		}
	}
	
	public static class ExpreeResult{
		private String expree;
		private List param;
		public String getExpree() {
			return expree;
		}
		public void setExpree(String expree) {
			this.expree = expree;
		}
		public List getParam() {
			return param;
		}
		public void setParam(List param) {
			this.param = param;
		}
	}
	/**
	 * 找出可选SQL语句，如果里面的变量为空，去掉此段可选SQL语句
	 * @param queryContent
	 * @param params
	 * @return
	 */
	private static String replaceOptionSqlByParam(String queryContent, Map params){
		String queryContentBuf = queryContent.toString();
		// 用正则解析出变量
		Pattern p = Pattern.compile(Constraint.FIND_SQL_OPTIONAL_REXP);
		Matcher m = p.matcher(queryContent);
		while (m.find()) {
			int start = m.start();
			int end = m.end();
			String optionSql = queryContent.substring(start, end);

//			// 去掉首尾的多余字符串
//			optionSql = optionSql.substring(1, optionSql.length() - 1);

			boolean delete =false;

			Pattern pBuf = Pattern.compile(Constraint.FIND_ALL_QUERY_VARIABLE_REXP);
			Matcher mBuf = pBuf.matcher(optionSql);
			while (mBuf.find()) {
				int startBuf = mBuf.start();
				int endBuf = mBuf.end();

				String variable = optionSql.substring(startBuf, endBuf);
				// 去掉首尾的多余字符串
				variable = variable.substring(1, variable.length() - 1);
				if(variable.startsWith("%")||variable.endsWith("%")){
					//variable = variable.replace("%", "");
					variable = variable.replaceAll("%", "");
				}
                //判断里面的变量是否存在，不存在则不需要这个查询语句
				if((params.get(variable.toLowerCase())==null||"".equals(params.get(variable.toLowerCase())))
				  &&(params.get(variable)==null||"".equals(params.get(variable)))){
					delete =true;
					break;
				}
			}

			//删除可选SQL语句
			if(delete){
				//queryContentBuf = queryContentBuf.replace(optionSql, "");
			    queryContentBuf = ExpressUtil.replaceAll(queryContentBuf, optionSql, "");
			}else{
				//queryContentBuf = queryContentBuf.replace(optionSql, optionSql.substring(1, optionSql.length() - 1));
			    queryContentBuf = ExpressUtil.replaceAll(queryContentBuf, optionSql, optionSql.substring(1, optionSql.length() - 1));
			}
		}

		return queryContentBuf;
	}
	
	/**
	 * 根据将SQL语句中#{var1}替换成对应变量，直接替换，不使用？预编译字符
	 * @param queryContent
	 * @param params
	 */
	private static String replaceSqlByParam(String queryContent, Map params){
		String queryContentBuf = queryContent.toString();
		// 用正则解析出变量
		Pattern p = Pattern.compile(Constraint.FIND_SQL_REPLACE_REXP);
		Matcher m = p.matcher(queryContent);
		while (m.find()) {
			int start = m.start();
			int end = m.end();
			String variable = queryContent.substring(start, end);

			// 去掉首尾的多余字符串
			variable = variable.substring(2, variable.length() - 1);
			String variableBuf = variable;
			if(variable.startsWith("%")||variable.endsWith("%")){
				//variableBuf = variable.replace("%", "");
				variableBuf = variable.replaceAll("%", "");
			}

			if (!params.containsKey(variableBuf.toLowerCase())&&!params.containsKey(variableBuf)) {
				throw new DAOSystemException("未能找到入参变量："
						+ variableBuf.toUpperCase());
			}

			//直接替换字符
			queryContentBuf = queryContentBuf.replaceAll("#\\u007B"+variable+"}", StringUtils.isEmptyDefault(
					StringUtils.safe((String)params.get(variableBuf.toLowerCase())),
					StringUtils.safe((String)params.get(variableBuf))));
		}

		return queryContentBuf;
	}
	public static String replaceAll(String des, String optionStr, String replaceStr) {
	        String result = des.substring(0, des.indexOf(optionStr)) + replaceStr;
	        //System.out.println("@@@" + result);
	        result += des.substring(des.indexOf(optionStr) + optionStr.length(), des.length());
	        return result;
	}
	
	public class Constraint {

		public static final String TAG_FORMAT = "<{0} {2}>{1}</{0}>";

		public static final String STYLE_CLASS = "class";

		public static final String QUERY_ITEM_PREFIX = "query_item_";

		public static final String QUERY_ITEM_CONTENT_PREFIX = "query_item_content_";

		// 查找变量的正则表达式，如${var1}这样的格式
		public static final String FIND_QUERY_VARIABLE_REXP = "\\u0024\\u007B\\S+?}";

		// 查找SQL中需要直接替换的地方，如#{var1}，与${var1}的区别为，${var1}替换成问号，#{var1}直接替换成具体的字符
		public static final String FIND_SQL_REPLACE_REXP = "#\\u007B\\S+?}";

		// 使用“@@”括起来的为排序SQL语句，当使用分页统计时，屏蔽此排序语句
		public static final String FIND_SQL_ORDER_BY_REXP = "@[\\S\\s]*?@";

		// 使用中括号“[]”括起来的为可选SQL语句，当可选语句里面的#{var1}或者${var1}为空时，则认为是不需要此查询条件，直接去掉
		public static final String FIND_SQL_OPTIONAL_REXP = "\\[[\\S\\s]*?\\]";
		

		// 找出所有变量，包括#{var1}、${var1}
		public static final String FIND_ALL_QUERY_VARIABLE_REXP = "\\u007B\\S+?}";
		
	}
	
	
	public static void main(String[] args) {
		
		String sourceExpress="/land/websource.nsf/selectDepartPerson?openform[&fName=#{fieldName}][&sValue=#{selValues}][&kWord=#{keyWord}][&part=#{part}]";
		Map param=new HashMap();
		param.put("selectRange", "depart");
		param.put("fieldName", "DepManager");
		param.put("selValues", "1");
		param.put("keyWord", "黑龙江-办公室（安全保卫部）");
		param.put("part", "depart");
		
		try {
			ExpreeResult expreeResult =ExpressUtil.parse(sourceExpress, param);
			
			System.out.println(expreeResult.getExpree());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		                                                                                                              
		                                                                                                              
		                                                                                                              
	}
}
