package com.ztesoft.inf.framework.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.ibss.common.util.CrmConstants;
import com.ztesoft.ibss.common.util.DateFormatUtils;

/**
 * @author ReasonYea
 */
public class SeqUtil  {
	private SqlExe sqlExe = new SqlExe();
	/**
	 * 根据表名和字段名获取序列
	 *
	 * @param tableCode
	 * @param fieldCode
	 * @return
	 */
	public String getNextSequence(String tableCode,String fieldCode) {
		String result=null;
		String GET_SEQUENCE_CODE = "SELECT sequence_code FROM sequence_management "
				+ " WHERE table_code=? AND field_code=?";
		try {
			String sequenceCode = sqlExe.queryForString(GET_SEQUENCE_CODE, new String[]{tableCode,fieldCode});
			return next(sequenceCode);
		} catch (FrameException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

        /**
         * 根据表名和字段名获取序列,序列格式为“YYYYMMDD"+流水号长度
         *
         * @param tableCode
         * @param fieldCode
         * @param seqNum
         * @return
         */
        public String getNextSequenceFormat(String tableCode,String fieldCode,int seqNum) {

        	String result=null;
    		String GET_SEQUENCE_CODE = "SELECT sequence_code FROM sequence_management "
    				+ " WHERE table_code=? AND field_code=?";
    		try {
    			String sequenceCode = sqlExe.queryForString(GET_SEQUENCE_CODE, new String[]{tableCode,fieldCode});
    			String GET_SEQUENCE =  "select LPAD( "+sequenceCode+ ".nextval,"+ seqNum + ",'0') seq_value FROM dual";
    			result = sqlExe.queryForString(GET_SEQUENCE);
    		} catch (FrameException e) {
    			e.printStackTrace();
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    		return result;
        }


	public synchronized String next(String sequenceCode) {
		String GET_SEQUENCE = "SELECT "+sequenceCode+ ".nextval seq_value FROM dual";
		String result=null;
		try {
			result = sqlExe.queryForString(GET_SEQUENCE);
		} catch (FrameException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}


    /**
    * @param 序列名称
    * return 返回当前时间+序列值
    */
    public String getTimeSequence(String seq_name){
        String currentTime= DateFormatUtils.getFormatedDate();
        NumberFormat format = new DecimalFormat("0000000000");
        try{
           String rval = next(seq_name);
           if(StringUtils.isNotEmpty(rval)){
               currentTime+=format.format(new Long(rval).longValue());
           }
        }catch (Exception e){
            currentTime= DateFormatUtils.formatDate(new Date(System.currentTimeMillis()), CrmConstants.DATE_TIME_FORMAT_14);
            e.printStackTrace();
        }
        return currentTime;
    }
	    public String getSequenceLen(String strSeqName,String strSeqType,int intSeqLen){
	    	String sql="";
			if(strSeqType.trim().equals("0")){		//按长度取序列值
				sql = "SELECT LPAD(getseq('"+strSeqName+"'),"+intSeqLen+",'0') SEQ FROM DUAL";
			}else if(strSeqType.trim().equals("1")){   //在前面加8位年月日
				sql="SELECT to_char2(getdate(),'yyyymmdd')||LPAD(getseq('"+strSeqName+"'),"+intSeqLen+"-8,'0')  seq  FROM DUAL";
			}else if(strSeqType.trim().equals("2")){   //在前面加14位年月日时分秒
				sql="select to_char2(getdate(),'yyyymmddhh24miss')||LPAD(getseq('"+strSeqName+"'),"+intSeqLen+"-14,'0') seq from dual ";
			}else{		//直接取序列
				sql="select getseq('"+strSeqName+"') from dual";
			}
			String ret=null;
			try {
				ret = sqlExe.queryForString(sql);
			} catch (FrameException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return ret;
	    }
}
