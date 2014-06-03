package com.ztesoft.common.util;

/**
 * This class is the central location to store the internal
 * JNDI names of various entities. Any change here should
 * also be reflected in the deployment descriptors.
 */
public class JNDINames {
    private JNDINames(){} // prevent instanciation

    /** JNDI name of the ibss database source */
    public static final String CRM_DATASOURCE = "CRMDB" ;

    public static final String DEFAULT_DATASOURCE = "DEFAULT" ;
    public static final String LOG_DATASOURCE = "LOGDB" ;
    
    public static final String PPM_DATASOURCE = "CRMDB" ;//订单模型数据源
    public static final String PRODDB_DATASOURCE = "CRMDB" ;// 销售品规格模型数据源

    public static final String PPMHB_DATASOURCE = "PPMHB";//    计费相关模型数据源

    /*重庆门户数据源*/
    
    public static final String CQOA_DATASOURCE = "CQOADB";//同步需求及时完成率
    
    public static final String CQ_EIP_DATASOURCE = "CQEIPDB";//同步需求及时完成率
    public static final String CQCRM_DATASOURCE = "CQCRM";//同步需求及时完成率
    
    
    /*云南一点配置数据源*/
    public static final String YN_CRM_DATASOURCE="YNCRM";//CRM一点配置插数据表
}

