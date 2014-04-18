/*     */ package com.ztesoft.inf;
/*     */ 
/*     */ import com.powerise.ibss.framework.FrameException;
/*     */ import com.powerise.ibss.util.DBUtil;
/*     */ import com.ztesoft.ibss.common.util.ListUtil;
/*     */ import com.ztesoft.ibss.common.util.SqlMapExe;
/*     */ import com.ztesoft.inf.communication.client.CommCaller;
/*     */ import com.ztesoft.inf.extend.xstream.XStream;
/*     */ import com.ztesoft.inf.extend.xstream.mapper.MapperContext;
/*     */ import com.ztesoft.inf.extend.xstream.mapper.MapperContextBuilder;
/*     */ import com.ztesoft.inf.framework.commons.CodedException;
/*     */ import com.ztesoft.net.eop.sdk.database.BaseSupport;
/*     */ import java.io.StringReader;
/*     */ import java.sql.Connection;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ import org.xml.sax.InputSource;
/*     */ 
/*     */ public class AbstractService extends BaseSupport
/*     */ {
/*  38 */   protected CommCaller caller = new CommCaller();
/*     */ 
/*     */   protected Map returnMap(Map map)
/*     */   {
/*  45 */     Map body = (Map)map.get("Body");
/*  46 */     Map response = (Map)body.get("exchangeResponse");
/*  47 */     String out = response.get("out").toString();
/*  48 */     XStream stream = new XStream();
/*  49 */     MapperContext mapperCtx = new MapperContext();
/*  50 */     Map retMap = (Map)stream.fromXML(out, mapperCtx);
/*  51 */     return retMap;
/*     */   }
/*     */ 
/*     */   protected Map parseRetMap(Map map)
/*     */   {
/*  60 */     Map body = (Map)map.get("Body");
/*  61 */     Map response = (Map)body.get("handlerResponse");
/*  62 */     String ret = response.get("return").toString();
/*     */ 
/*  66 */     XStream stream = new XStream();
/*  67 */     MapperContext mapperCtx = new MapperContext();
/*  68 */     Map retMap = (Map)stream.fromXML(ret, mapperCtx);
/*  69 */     StringReader read = new StringReader(ret);
/*  70 */     InputSource source = new InputSource(read);
/*     */ 
/*  72 */     SAXReader saxReader = new SAXReader();
/*  73 */     Document document = null;
/*     */     try
/*     */     {
/*  76 */       document = saxReader.read(source);
/*     */     } catch (DocumentException e) {
/*  78 */       e.printStackTrace();
/*     */     }
/*  80 */     if (document != null) {
/*  81 */       List list = document.selectNodes("/Info/ProdOfferList/ProdOfferInfo");
/*  82 */       if (!ListUtil.isEmpty(list)) {
/*  83 */         Element offerList = (Element)list.get(0);
/*  84 */         retMap.put("prodOfferInstId", offerList.elementText("ProdOfferInstId"));
/*  85 */         retMap.put("prodOfferCode", offerList.elementText("ProdOfferCode"));
/*  86 */         retMap.put("prodOfferName", offerList.elementText("ProdOfferName"));
/*     */       }
/*     */     }
/*  89 */     return retMap;
/*     */   }
/*     */ 
/*     */   protected Map getHeader(Map param)
/*     */   {
/*  98 */     HashMap map = new HashMap();
/*  99 */     Map header = (Map)param.get("Header");
/*     */ 
/* 101 */     Map response = (Map)header.get("Response");
/* 102 */     String code = (String)response.get("Code");
/* 103 */     String message = (String)response.get("Message");
/* 104 */     map.put("exchangeId", (String)header.get("ExchangeId"));
/* 105 */     map.put("code", code);
/* 106 */     map.put("message", message);
/* 107 */     return map;
/*     */   }
/*     */   protected String getHeaderValue(Map param, String key) {
/* 110 */     Map header = (Map)param.get("Header");
/* 111 */     String value = (String)header.get(key);
/* 112 */     return value;
/*     */   }
/*     */ 
/*     */   protected Map getResponse(Map param) {
/* 116 */     Map retMap = new HashMap();
/* 117 */     Map header = (Map)param.get("Header");
/* 118 */     Map response = (Map)header.get("Response");
/*     */ 
/* 120 */     String code = (String)response.get("Code");
/*     */ 
/* 123 */     if ((code != null) && (code.equals("0000")))
/*     */     {
/* 125 */       String message = (String)response.get("Message");
/*     */ 
/* 127 */       retMap = getOrderItem(param);
/* 128 */       retMap.put("exchangeId", (String)header.get("ExchangeId"));
/* 129 */       retMap.put("code", code);
/* 130 */       retMap.put("message", message);
/*     */     }
/*     */     else
/*     */     {
/* 135 */       String message = (String)response.get("Message");
/* 136 */       retMap.put("exchangeId", (String)header.get("ExchangeId"));
/* 137 */       retMap.put("code", code);
/* 138 */       retMap.put("message", message);
/*     */       try {
/* 140 */         String log_id = (String)param.get("INF_LOG_ID");
/* 141 */         retMap.put("INF_LOG_ID", log_id); } catch (Exception e) {
/*     */       }
/*     */     }
/* 144 */     return retMap;
/*     */   }
/*     */ 
/*     */   protected Map getOrderItem(Map param)
/*     */   {
/* 154 */     Map retMap = new HashMap();
/* 155 */     Map custOrderMap = (Map)param.get("CustOrder");
/*     */ 
/* 163 */     Object obj = custOrderMap.get("OrderItem");
/* 164 */     List retList = new ArrayList();
/* 165 */     if (obj != null) {
/* 166 */       if (((obj instanceof List)) || (obj.getClass().equals("java.util.List")) || (obj.getClass().equals("java.util.ArrayList")))
/*     */       {
/* 168 */         List list = (List)obj;
/*     */ 
/* 170 */         for (int i = 0; i < list.size(); i++) {
/* 171 */           Map maps = (Map)list.get(i);
/*     */ 
/* 173 */           maps.put("cust_id", custOrderMap.get("cust_id"));
/* 174 */           retList.add(maps);
/*     */         }
/*     */       }
/*     */       else {
/* 178 */         Map map = (Map)obj;
/* 179 */         map.put("cust_id", custOrderMap.get("cust_id"));
/* 180 */         retList.add(map);
/*     */       }
/*     */ 
/* 183 */       retMap.put("OrderItem", retList);
/*     */     }
/*     */ 
/* 198 */     return retMap;
/*     */   }
/*     */ 
/*     */   protected Map getQueryList(Map param, String nodeName, String chileNodeListName)
/*     */   {
/* 211 */     Map retMap = new HashMap();
/* 212 */     Map repMap = (Map)param.get(nodeName);
/*     */ 
/* 215 */     Object obj = repMap.get(chileNodeListName);
/* 216 */     List retList = new ArrayList();
/* 217 */     if (obj != null) {
/* 218 */       if (((obj instanceof List)) || (obj.getClass().equals("java.util.List")) || (obj.getClass().equals("java.util.ArrayList")))
/*     */       {
/* 220 */         List list = (List)obj;
/*     */ 
/* 222 */         for (int i = 0; i < list.size(); i++) {
/* 223 */           Map maps = (Map)list.get(i);
/*     */ 
/* 225 */           retList.add(maps);
/*     */         }
/*     */       }
/*     */       else {
/* 229 */         Map map = (Map)obj;
/*     */ 
/* 231 */         retList.add(map);
/*     */       }
/*     */ 
/* 234 */       retMap.put(chileNodeListName, retList);
/*     */     }
/*     */ 
/* 237 */     return retMap;
/*     */   }
/*     */ 
/*     */   protected Map returnMap(Map map, String operationCode)
/*     */   {
/* 245 */     Map body = (Map)map.get("Body");
/* 246 */     Map response = (Map)body.get("exchangeResponse");
/* 247 */     String out = response.get("out").toString();
/* 248 */     Map retMap = convertToMap(operationCode, out);
/* 249 */     Object inf_log_id = map.get("INF_LOG_ID");
/* 250 */     if ((inf_log_id instanceof String)) {
/* 251 */       retMap.put("INF_LOG_ID", inf_log_id);
/*     */     }
/* 253 */     return retMap;
/*     */   }
/*     */ 
/*     */   protected Map convertToMap(String operationCode, String out)
/*     */   {
/* 258 */     XStream stream = new XStream();
/* 259 */     MapperContext mapperCtx = null;
/* 260 */     Connection conn = null;
/*     */     try {
/* 262 */       String sql = "select b.xml_mapper  from inf_comm_client_operation a, inf_comm_client_response b where a.rsp_id = b.rsp_id  and a.op_code = ?";
/* 263 */       conn = DBUtil.getConnection();
/* 264 */       SqlMapExe sqlexe = new SqlMapExe(conn);
/* 265 */       String xml_mapper = sqlexe.querySingleValue(sql, operationCode);
/* 266 */       mapperCtx = new MapperContextBuilder().build(xml_mapper);
/*     */     } catch (FrameException e) {
/* 268 */       e.printStackTrace();
/*     */     } finally {
/* 270 */       DBUtil.closeConnection(conn);
/*     */     }
/* 272 */     if (mapperCtx == null) {
/* 273 */       mapperCtx = new MapperContext();
/*     */     }
/* 275 */     Map retMap = (Map)stream.fromXML(out, mapperCtx);
/* 276 */     return retMap;
/*     */   }
/*     */ 
/*     */   public String getLanId(String cityCode)
/*     */   {
/* 285 */     String lan_id = "1";
/* 286 */     if (cityCode.equals("0791"))
/* 287 */       lan_id = "1";
/* 288 */     else if (cityCode.equals("0792"))
/* 289 */       lan_id = "9";
/* 290 */     else if (cityCode.equals("0793"))
/* 291 */       lan_id = "8";
/* 292 */     else if (cityCode.equals("0794"))
/* 293 */       lan_id = "5";
/* 294 */     else if (cityCode.equals("0795"))
/* 295 */       lan_id = "7";
/* 296 */     else if (cityCode.equals("0796"))
/* 297 */       lan_id = "10";
/* 298 */     else if (cityCode.equals("0797"))
/* 299 */       lan_id = "11";
/* 300 */     else if (cityCode.equals("0798"))
/* 301 */       lan_id = "4";
/* 302 */     else if (cityCode.equals("0799"))
/* 303 */       lan_id = "6";
/* 304 */     else if (cityCode.equals("0790"))
/* 305 */       lan_id = "3";
/* 306 */     else if (cityCode.equals("0701")) {
/* 307 */       lan_id = "2";
/*     */     }
/* 309 */     return lan_id;
/*     */   }
/*     */ 
/*     */   public String getCityCode(String lanId)
/*     */   {
/* 318 */     String cityCode = "";
/* 319 */     if (lanId.equals("1"))
/* 320 */       cityCode = "0791";
/* 321 */     else if (lanId.equals("9"))
/* 322 */       cityCode = "0792";
/* 323 */     else if (lanId.equals("8"))
/* 324 */       cityCode = "0793";
/* 325 */     else if (lanId.equals("5"))
/* 326 */       cityCode = "0794";
/* 327 */     else if (lanId.equals("7"))
/* 328 */       cityCode = "0795";
/* 329 */     else if (lanId.equals("10"))
/* 330 */       cityCode = "0796";
/* 331 */     else if (lanId.equals("11"))
/* 332 */       cityCode = "0797";
/* 333 */     else if (lanId.equals("4"))
/* 334 */       cityCode = "0798";
/* 335 */     else if (lanId.equals("6"))
/* 336 */       cityCode = "0799";
/* 337 */     else if (lanId.equals("3"))
/* 338 */       cityCode = "0790";
/* 339 */     else if (lanId.equals("2"))
/* 340 */       cityCode = "0701";
/*     */     else {
/* 342 */       cityCode = "";
/*     */     }
/* 344 */     return cityCode;
/*     */   }
/*     */ 
/*     */   public Map excuteInvoke(String operationCode, Map param)
/*     */   {
/* 355 */     Map map = new HashMap();
/* 356 */     boolean failFlag = false;
/*     */     try {
/* 358 */       map = (Map)this.caller.invoke(operationCode, param);
/*     */     } catch (CodedException ex) {
/* 360 */       failFlag = true;
/* 361 */       map.put("code", ex.getCode());
/* 362 */       map.put("msg", ex.getMessage());
/*     */     } catch (Exception ex) {
/* 364 */       failFlag = true;
/* 365 */       map.put("code", "-1");
/* 366 */       map.put("msg", ex.getMessage());
/*     */     } finally {
/* 368 */       if (!failFlag) {
/* 369 */         map.put("code", "0");
/* 370 */         map.put("msg", "执行成功");
/*     */       }
/*     */     }
/* 373 */     return map;
/*     */   }
/*     */ 
/*     */   public CommCaller getCaller() {
/* 377 */     return this.caller;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.AbstractService
 * JD-Core Version:    0.6.2
 */