package com.ztesoft.inf.service.util;

public abstract interface IConst
{
  public static final String EXCHANGE_OUT_OBJ = "EXCHANGE_OUT_OBJ";
  public static final String EXCHANGE_OUT_DOC = "EXCHANGE_OUT_DOC";
  public static final String EXCHANGE_OUT_XML = "EXCHANGE_OUT_XML";
  public static final String EXCHANGE_IN_XML = "EXCHANGE_IN_XML";
  public static final String EXCHANGE_IN_DOC = "EXCHANGE_IN_DOC";
  public static final String EXCHANGE_CLIENT_IP = "EXCHANGE_CLIENT_IP";
  public static final String EXCHANGE_CLIENT_PWD = "EXCHANGE_CLIENT_PWD";
  public static final String EXCHANGE_CLIENT_ID = "EXCHANGE_CLIENT_ID";
  public static final String EXCHANGE_BIZ_CODE = "EXCHANGE_BIZ_CODE";
  public static final String EXCHANGE_LOG = "EXCHANGE_LOG";
  public static final String EXCHANGE_BIZ_PROVIDEINFO = "EXCHANGE_BIZ_PROVIDEINFO";
  public static final String FIND_QUERY_VARIABLE_REXP = "\\u0024\\u007B\\S+?}";
  public static final String FIND_SQL_REPLACE_REXP = "#\\u007B\\S+?}";
  public static final String FIND_SQL_OPTIONAL_REXP = "\\[[\\S\\s]*?\\]";
  public static final String FIND_ALL_QUERY_VARIABLE_REXP = "\\u007B\\S+?}";
  public static final String XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
  public static final String LOG_ID = "INF_LOG_ID";
}

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.service.util.IConst
 * JD-Core Version:    0.6.2
 */