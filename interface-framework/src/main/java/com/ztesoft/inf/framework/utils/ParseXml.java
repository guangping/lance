/*     */ package com.ztesoft.inf.framework.utils;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.DocumentHelper;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.tree.DefaultAttribute;
/*     */ 
/*     */ public class ParseXml
/*     */ {
/*  18 */   public String test_adsl_create = "<?xml version=\"1.0\" encoding=\"GB2312\"?><Root><Domain>WSS</Domain><Passwd>123456</Passwd><SrvCode>ServOrder</SrvCode><RespCode>0000</RespCode><Content><![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?><ServOrder><OrdSerial>1002031I0140035504</OrdSerial><ProdType>1000143</ProdType><ServNo>10000</ServNo><BureauCode>1100</BureauCode><UserName>李晟</UserName><AccNbr>3214399</AccNbr><CustCardType>A</CustCardType><CustCardNo>45030519830216101X</CustCardNo><RelaName>李晟</RelaName><RelaTel>1111111</RelaTel><RelaAdder>南宁市西乡塘区大学东路160号瑞士花园翠湖苑6栋B座301房</RelaAdder><Adder>南宁市西乡塘区大学路160号瑞士花园翠湖苑6栋B座301房</Adder><ServDate></ServDate><XdslPassWord>131312</XdslPassWord><XdslLimit1>20</XdslLimit1><XdslLimit2>1</XdslLimit2><ServID>3214399</ServID><InstallFlag>1</InstallFlag><InstallType>1</InstallType><Notes>新装ADSL宽带</Notes><SubProdInfo/><Book_flag></Book_flag><CustId></CustId><IMSI>201002030000062</IMSI><UIMCODE>2010020390000000062</UIMCODE><OrderOrigino>01</OrderOrigino><AutoOrder>1</AutoOrder></ServOrder>]]></Content><SeqId>201002030000027282</SeqId></Root>";
/*     */ 
/*  23 */   public String test_mobile_create = "<?xml version=\"1.0\" encoding=\"GB2312\"?><Root><Domain>WSS</Domain><Passwd>123456</Passwd><SrvCode>ServOrder</SrvCode><RespCode>0000</RespCode><Content><![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?><ServOrder><OrdSerial>100203YS050035525</OrdSerial><ProdType>1000008</ProdType><ServNo>10000</ServNo><BureauCode>1100</BureauCode><UserName>欧骞</UserName><AccNbr>66</AccNbr><CustCardType>A</CustCardType><CustCardNo>430103197711164033</CustCardNo><RelaName></RelaName><RelaTel>1111111</RelaTel><RelaAdder>南宁市民主路35号</RelaAdder><Adder></Adder><ServDate></ServDate><XdslPassWord></XdslPassWord><XdslLimit1></XdslLimit1><XdslLimit2></XdslLimit2><ServID>3523587112</ServID><InstallFlag>1</InstallFlag><InstallType>1</InstallType><Notes>装 国内长途&#13;小灵通预约入网登记</Notes><SubProdInfo><ProdID>2000020</ProdID><OpType>0</OpType></SubProdInfo><Book_flag></Book_flag><CustId>3519333648</CustId><IMSI>201002030000077</IMSI><UIMCODE>2010020390000000077</UIMCODE><OrderOrigino>01</OrderOrigino><AutoOrder>1</AutoOrder></ServOrder>]]></Content><SeqId>201002030000027448</SeqId></Root>";
/*     */ 
/*  28 */   public String test_self_create = "<?xml version=\"1.0\" encoding=\"GB2312\"?><Root><Domain>WSS</Domain><Passwd>123456</Passwd><SrvCode>SelfComm</SrvCode><RespCode>0000</RespCode><Content><![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?><SelfComm><CustId>2602219192</CustId><ServID>1101703607</ServID><OrdSerial>100204XXZL0035567</OrdSerial><SubProdInfo><ProdID>2020051</ProdID><OpType>0</OpType><PROPS><ID>10149</ID><VALUE>123456</VALUE></PROPS><PROPS><ID>100236</ID><VALUE>07712840139</VALUE></PROPS><PROPS><ID>100244</ID><VALUE>18977164449</VALUE></PROPS><PROPS><ID>100242</ID><VALUE>13397706518</VALUE></PROPS><PROPS><ID>457</ID><VALUE>07712840139</VALUE></PROPS><PROPS><ID>100235</ID><VALUE>500</VALUE></PROPS><PROPS><ID>350</ID><VALUE>5</VALUE></PROPS><PROPS><ID>100121</ID><VALUE>号簿容量在500个号码以下的5元/月</VALUE></PROPS></SubProdInfo></SelfComm>]]></Content><SeqId>201002040000027972</SeqId></Root>";
/*     */ 
/*  32 */   public String test_UserInfoQry = "<?xml version=\"1.0\" encoding=\"GB2312\"?><Root><Domain>WSS</Domain><Passwd>123456</Passwd><SrvCode>UserInfoQry</SrvCode><Content><![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?><UserInfoQry><LoginType>20</LoginType><AccNbr>bggaz081744</AccNbr><PassWord>111111</PassWord><ProdType></ProdType><BureauCode>2500</BureauCode></UserInfoQry>]]></Content><RespCode>0000</RespCode></Root>";
/*     */ 
/*  40 */   public String REQUEST_FINDBUNUS = "<?xml version=\"1.0\" encoding=\"GB2312\"?><Root><Domain>WSS</Domain><Passwd>123456</Passwd><SrvCode>FindBunus</SrvCode><RespCode>0000</RespCode><Content><![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?><FindBunus><AccNbr>2600216291</AccNbr><AcctMonth>2010</AcctMonth><SearchMonth>0</SearchMonth></FindBunus>]]></Content></Root>";
/*     */ 
/*  48 */   public String REQUEST_staffpasswdqry = "<?xml version=\"1.0\" encoding=\"GB2312\"?><Root><Domain>WSS</Domain><Passwd>123456</Passwd><SrvCode>StaffPasswdQry</SrvCode><RespCode>0000</RespCode><Content><![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?><StaffPasswdQry><StaffCode>771110001</StaffCode><Password>111aaa</Password></StaffPasswdQry>]]></Content></Root>";
/*     */ 
/*  52 */   public String REQUEST_tksaleqry = "<?xml version=\"1.0\" encoding=\"GB2312\"?><Root><Domain>WSS</Domain><Passwd>123456</Passwd><SrvCode>TkSaleQry</SrvCode><RespCode>0000</RespCode><Content><![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?><TkSaleQry><TkNo>7710000005470900</TkNo><Password>28640038</Password></TkSaleQry>]]></Content></Root>";
/*     */ 
/*  55 */   public String REQUEST_tksalechkqry = "<?xml version=\"1.0\" encoding=\"GB2312\"?><Root><Domain>WSS</Domain><Passwd>123456</Passwd><SrvCode>TkSaleChkQry</SrvCode><RespCode>0000</RespCode><Content><![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?><TkSaleChkQry><TkNo>7710000005470900</TkNo><Password>28640038</Password><TermNo>FA4624F1</TermNo><ChkOperId>771110001</ChkOperId></TkSaleChkQry>]]></Content></Root> ";
/*     */ 
/*  58 */   public String REQUEST_tksalechk = "<?xml version=\"1.0\" encoding=\"GB2312\"?><Root><Domain>WSS</Domain><Passwd>123456</Passwd><SrvCode>TkSaleChk</SrvCode><RespCode>0000</RespCode><Content><![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?><TkSaleChk><TkNo>7710000002170900</TkNo><Password>24905746</Password><TermNo>FA463C7F</TermNo><ChkOperId>771110001</ChkOperId><StaffCode>771110001</StaffCode></TkSaleChk>]]></Content></Root>";
/*     */ 
/*  61 */   public String REQUEST_MinimumOfferQry = "<?xml version=\"1.0\" encoding=\"GB2312\"?><Root><Domain>WSS</Domain><Passwd>123456</Passwd><SrvCode>MinimumOfferQry</SrvCode><RespCode>0000</RespCode><Content><![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?><MinimumOfferQry><ProductId>40000684877</ProductId><BureauCode>1100</BureauCode><AccNbr>18977141525</AccNbr></MinimumOfferQry>]]></Content><SeqId>2010031900000311601</SeqId></Root>";
/*     */ 
/*  64 */   public String REQUEST_BuySaleQry = "<?xml version=\"1.0\" encoding=\"GB2312\"?><Root><Domain>SMTS</Domain><Passwd>123456</Passwd><SrvCode>BuySaleQry</SrvCode><RespCode>0000</RespCode><Content><![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?><BuySaleQry><OrdSerial>100test2010033001002</OrdSerial><BureauCode>1100</BureauCode><AccNbr>18977164449</AccNbr><SubjectId>260017422</SubjectId><TimeLen>0</TimeLen></BuySaleQry>]]></Content></Root>";
/*     */ 
/*  67 */   public String REQUEST_CustStopReSume = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Root><Passwd>123456</Passwd><Domain>CSC</Domain><SrvCode>CustStopReSume</SrvCode><RespCode>0000</RespCode><Content><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?><CustStopReSume><OperType>0</OperType><AccNbr>3983640</AccNbr><OrdSerial>csc000000000000000000000001302</OrdSerial><BureauCode>1100</BureauCode></CustStopReSume>]]></Content></Root>";
/*     */ 
/*  71 */   public String REQUEST_OfferResign = "<?xml version=\"1.0\" encoding=\"GB2312\"?><Root><Domain>WSS</Domain><Passwd>123456</Passwd><SrvCode>OfferResign</SrvCode><RespCode>0000</RespCode><Content><![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?><OfferResign><OrdSerial>100420OFRG0035914</OrdSerial><BureauCode>1500</BureauCode><AccNbr>8632717</AccNbr><CompId>301000131</CompId><CompInstId>600113686749</CompInstId><CompKind>J</CompKind><ResignList><PrefId>11000950</PrefId><PrefInstId>600113688741</PrefInstId></ResignList><ChargeFlag>Y2</ChargeFlag></OfferResign>]]></Content><SeqId>201004200000035329</SeqId></Root>";
/*     */ 
/*  73 */   public String REQUEST_OfferResignQry = "<?xml version=\"1.0\" encoding=\"GB2312\"?><Root><Domain>WSS</Domain><Passwd>123456</Passwd><SrvCode>OfferResignQry</SrvCode><RespCode>0000</RespCode><Content><![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?><OfferResignQry><AccNbr>5221209</AccNbr><BureauCode>1100</BureauCode></OfferResignQry>]]></Content><SeqId>201004260000637456</SeqId></Root>";
/*     */ 
/*  75 */   public String REQUEST_DemandOfResource = "<?xml version=\"1.0\" encoding=\"GB2312\"?><Root><Domain>WSS</Domain><Passwd>123456</Passwd><SrvCode>DemandOfResourceQry</SrvCode><RespCode>0000</RespCode><Content><![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?><DemandOfResourceRequest><Amount>15</Amount><BureauCode>1100</BureauCode></DemandOfResourceRequest>]]></Content></Root>";
/*     */ 
/*  77 */   public String REQUEST_ResourceUserRelease = "<?xml version=\"1.0\" encoding=\"GB2312\"?><Root><Domain>WSS</Domain><Passwd>123456</Passwd><SrvCode>ResourceUserReleaseQry</SrvCode><RespCode>0000</RespCode><Content><![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?><ResourceUserReleaseRequest><ResourceInstanceCode>18907713433</ResourceInstanceCode><BureauCode>1100</BureauCode><ActionCode>1</ActionCode></ResourceUserReleaseRequest>]]></Content></Root>";
/*     */ 
/*  79 */   public String testSendXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><SubAuthFromCRMReq><StreamingNo>{streamingNo}</StreamingNo><TimeStamp>{yyyymmdd}</TimeStamp><ProductOfferId>{productOfferId}</ProductOfferId><SystemId>{systemId1}</SystemId><SystemId>{systemId2}</SystemId><ProductInfo><ProdSpecCode>{cProductId}</ProdSpecCode><ProductNo>{productNo}</ProductNo><VProductInfo><ActionType>{actionId}</ActionType><VProductID>{cServProductId}</VProductID></VProductInfo></ProductInfo><ProductInfo><ProdSpecCode>{cProductId1}</ProdSpecCode><ProductNo>{productNo1}</ProductNo><VProductInfo><ActionType>{actionId1}</ActionType><VProductID>{cServProductId1}</VProductID></VProductInfo></ProductInfo></SubAuthFromCRMReq>";
/*     */ 
/* 105 */   public String testBackXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><SubscribeAuthResp><StreamingNo>1</StreamingNo><ResultCode>0</ResultCode><ResultCode>1</ResultCode><ResultCode>2</ResultCode><ResultCode>3</ResultCode><ResultDesc>success1</ResultDesc><ResultDesc>success1</ResultDesc><ResultDesc>success1</ResultDesc><ResultDesc><ProdSpecCode>0</ProdSpecCode><ProductNo>13377050499</ProductNo><VProductInfo><VProductID>2021220</VProductID><OPResult>0</OPResult><OPDesc>鉴权通过1</OPDesc></VProductInfo><VProductInfo><VProductID>2021221</VProductID><OPResult>0</OPResult><OPDesc>鉴权通过2</OPDesc></VProductInfo></ResultDesc><ProductInfo><ProdSpecCode>0</ProdSpecCode><ProductNo>13377050499</ProductNo><VProductInfo><VProductID>2021220</VProductID><OPResult>0</OPResult><OPDesc>鉴权通过1</OPDesc></VProductInfo><VProductInfo><VProductID>2021221</VProductID><OPResult>0</OPResult><OPDesc>鉴权通过2</OPDesc></VProductInfo></ProductInfo></SubscribeAuthResp>";
/*     */ 
/* 165 */   private String xmlHead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws DocumentException
/*     */   {
/* 147 */     String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Root><ErrorInfo>SUCCESS</ErrorInfo><ResultCode>0</ResultCode><Content><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?><InvoicePrintResponse><PrintStr><INVOICE_FEE_ITEM3 /><INVOICE_FEE_ITEM8 /><FEE16 /><FEE3 /><FEE20 /><FEE5 /><INVOICE_FEE_ITEM19 /><CUST_NAME /><ORG_NAME>南宁市分公司</ORG_NAME><INVOICE_FEE_ITEM1>预付费充值</INVOICE_FEE_ITEM1><FEE17 /><CHARGE_FEE>伍拾圆零角零分</CHARGE_FEE><FEE2 /><SURPLUS>预付费小灵通(充值)</SURPLUS><INVOICE_FEE_ITEM16 /><FEE14 /><BILLING_SEGMENT /><INVOICE_FEE_ITEM7 /><INVOICE_FEE_ITEM4 /><FEE9 /><OPER_ID>中兴软创</OPER_ID><FEE6 /><INVOICE_FEE_ITEM12 /><PAY_METHOD /><INVOICE_FEE_ITEM17 /><FEE15 /><CUST_ID>2600111690</CUST_ID><FEE7 /><LAST_SURPLUS /><IMPREST /><INVOICE_FEE_ITEM13 /><FEE13 /><INVOICE_FEE_ITEM20 /><ACCEPT_ID>2009011300000000959327</ACCEPT_ID><INVOICE_FEE_ITEM10 /><FEE10 /><FEE11 /><CHARGE_TIME>2010-06-04 14:40:24</CHARGE_TIME><INVOICE_FEE_ITEM11 /><INVOICE_FEE_ITEM18 /><FEE1>50.0</FEE1><FEE8 /><INVOICE_FEE_ITEM9 /><FEE12 /><INVOICE_FEE_ITEM2 /><INVOICE_ID /><INVOICE_FEE_ITEM15 /><ORDER_ID>200901130026293517</ORDER_ID><FEE4 /><RECEIVABLE>50.0</RECEIVABLE><FEE19 /><FEE18 /><PRODUCT_NO>0771-4238179</PRODUCT_NO><INVOICE_FEE_ITEM5 /><INVOICE_FEE_ITEM14 /><CONTRACT_NO>                             收费时间 2010-06-04 14:40:24</CONTRACT_NO><INVOICE_FEE_ITEM6 /></PrintStr><ErrorInfo>SUCCESS</ErrorInfo><ResultCode>0</ResultCode></InvoicePrintResponse>]]></Content></Root>";
/* 148 */     ParseXml pase = new ParseXml();
/* 149 */     HashMap map = pase.parseXml(xml);
/* 150 */     HashMap mapq = pase.parseXml((String)((HashMap)map.get("Root")).get("Content"));
/* 151 */     HashMap cc = (HashMap)((HashMap)mapq.get("InvoicePrintResponse")).get("PrintStr");
/* 152 */     HashMap vo = new HashMap();
/* 153 */     for (Iterator it = cc.entrySet().iterator(); it.hasNext(); ) {
/* 154 */       Map.Entry _map = (Map.Entry)it.next();
/* 155 */       String key = (String)_map.getKey();
/*     */ 
/* 157 */       if (!key.startsWith("____attr"))
/*     */       {
/* 159 */         vo.put(key, _map.getValue());
/*     */       }
/*     */     }
/* 162 */     System.out.println(vo);
/* 163 */     System.out.println(pase.parseHashMap(map));
/*     */   }
/*     */ 
/*     */   public void setEncoding(String head) {
/* 167 */     this.xmlHead = head;
/*     */   }
/*     */   public String parseHashMap(HashMap fields) {
/* 170 */     StringBuffer _xml = new StringBuffer(this.xmlHead);
/* 171 */     _xml.append(iteratorHashMap(fields));
/* 172 */     return _xml.toString();
/*     */   }
/*     */   private String iteratorHashMap(HashMap fields) {
/* 175 */     StringBuffer strbXml = new StringBuffer("");
/*     */     Iterator it;
/* 176 */     if ((fields != null) && (!fields.isEmpty()))
/* 177 */       for (it = fields.entrySet().iterator(); it.hasNext(); ) {
/* 178 */         Map.Entry map = (Map.Entry)it.next();
/* 179 */         String key = (String)map.getKey();
/*     */ 
/* 181 */         if ((key.length() <= 10) || (!key.substring(0, 8).equals("____attr")))
/*     */         {
/* 183 */           Object val = map.getValue();
/* 184 */           if ((val instanceof String)) {
/* 185 */             strbXml.append("<");
/* 186 */             strbXml.append(key);
/*     */             Iterator itAttr;
/* 188 */             if (fields.containsKey("____attr" + key)) {
/* 189 */               HashMap _attrVo = (HashMap)fields.get("____attr" + key);
/* 190 */               if ((_attrVo != null) && (!_attrVo.isEmpty())) {
/* 191 */                 strbXml.append(" ");
/* 192 */                 for (itAttr = _attrVo.entrySet().iterator(); itAttr.hasNext(); ) {
/* 193 */                   Map.Entry mapAttr = (Map.Entry)itAttr.next();
/* 194 */                   String keyAttr = (String)mapAttr.getKey();
/* 195 */                   String valAttr = (String)mapAttr.getValue();
/* 196 */                   strbXml.append(keyAttr);
/* 197 */                   strbXml.append("=\"");
/* 198 */                   strbXml.append(valAttr);
/* 199 */                   strbXml.append("\"");
/*     */                 }
/*     */               }
/*     */             }
/* 203 */             strbXml.append(">");
/* 204 */             strbXml.append((String)val);
/* 205 */             strbXml.append("</");
/* 206 */             strbXml.append(key);
/* 207 */             strbXml.append(">");
/* 208 */           } else if ((val instanceof HashMap)) {
/* 209 */             HashMap vo = (HashMap)val;
/* 210 */             if ((vo == null) || (vo.isEmpty())) {
/* 211 */               strbXml.append("<");
/* 212 */               strbXml.append(key);
/* 213 */               strbXml.append("/>");
/*     */             } else {
/* 215 */               strbXml.append("<");
/* 216 */               strbXml.append(key);
/*     */               Iterator itAttr;
/* 218 */               if (fields.containsKey("____attr" + key)) {
/* 219 */                 HashMap _attrVo = (HashMap)fields.get("____attr" + key);
/* 220 */                 if ((_attrVo != null) && (!_attrVo.isEmpty())) {
/* 221 */                   strbXml.append(" ");
/* 222 */                   for (itAttr = _attrVo.entrySet().iterator(); itAttr.hasNext(); ) {
/* 223 */                     Map.Entry mapAttr = (Map.Entry)itAttr.next();
/* 224 */                     String keyAttr = (String)mapAttr.getKey();
/* 225 */                     String valAttr = (String)mapAttr.getValue();
/* 226 */                     strbXml.append(keyAttr);
/* 227 */                     strbXml.append("=\"");
/* 228 */                     strbXml.append(valAttr);
/* 229 */                     strbXml.append("\"");
/*     */                   }
/*     */                 }
/*     */               }
/* 233 */               strbXml.append(">");
/* 234 */               strbXml.append(iteratorHashMap(vo));
/* 235 */               strbXml.append("</");
/* 236 */               strbXml.append(key);
/* 237 */               strbXml.append(">");
/*     */             }
/* 239 */           } else if ((val instanceof ArrayList)) {
/* 240 */             ArrayList _val = (ArrayList)val;
/* 241 */             if ((_val == null) || (_val.isEmpty())) {
/* 242 */               strbXml.append("<");
/* 243 */               strbXml.append(key);
/* 244 */               strbXml.append("/>");
/*     */             } else {
/* 246 */               for (int i = 0; i < _val.size(); i++) {
/* 247 */                 HashMap vo = (HashMap)_val.get(i);
/* 248 */                 if ((vo == null) || (vo.isEmpty())) {
/* 249 */                   strbXml.append("<");
/* 250 */                   strbXml.append(key);
/* 251 */                   strbXml.append("/>");
/*     */                 }
/*     */                 else {
/* 254 */                   boolean isAllStr = true;
/* 255 */                   StringBuffer _tmpStr = new StringBuffer("");
/* 256 */                   for (Iterator it2 = vo.entrySet().iterator(); it2.hasNext(); ) {
/* 257 */                     Map.Entry map2 = (Map.Entry)it2.next();
/* 258 */                     String key2 = (String)map2.getKey();
/* 259 */                     Object _val2 = map2.getValue();
/* 260 */                     if (((_val2 instanceof String)) && (key2.equals(key))) {
/* 261 */                       _tmpStr.append("<");
/* 262 */                       _tmpStr.append(key);
/* 263 */                       _tmpStr.append(">");
/* 264 */                       _tmpStr.append(_val2);
/* 265 */                       _tmpStr.append("</");
/* 266 */                       _tmpStr.append(key);
/* 267 */                       _tmpStr.append(">");
/*     */                     } else {
/* 269 */                       isAllStr = false;
/* 270 */                       break;
/*     */                     }
/*     */                   }
/* 273 */                   if (!isAllStr) {
/* 274 */                     strbXml.append("<");
/* 275 */                     strbXml.append(key);
/*     */                     Iterator itAttr;
/* 277 */                     if (vo.containsKey("____attr" + key)) {
/* 278 */                       HashMap _attrVo = (HashMap)vo.get("____attr" + key);
/* 279 */                       if ((_attrVo != null) && (!_attrVo.isEmpty())) {
/* 280 */                         strbXml.append(" ");
/* 281 */                         for (itAttr = _attrVo.entrySet().iterator(); itAttr.hasNext(); ) {
/* 282 */                           Map.Entry mapAttr = (Map.Entry)itAttr.next();
/* 283 */                           String keyAttr = (String)mapAttr.getKey();
/* 284 */                           String valAttr = (String)mapAttr.getValue();
/* 285 */                           strbXml.append(keyAttr);
/* 286 */                           strbXml.append("=\"");
/* 287 */                           strbXml.append(valAttr);
/* 288 */                           strbXml.append("\"");
/*     */                         }
/*     */                       }
/*     */                     }
/* 292 */                     strbXml.append(">");
/* 293 */                     strbXml.append(iteratorHashMap(vo));
/* 294 */                     strbXml.append("</");
/* 295 */                     strbXml.append(key);
/* 296 */                     strbXml.append(">");
/*     */                   } else {
/* 298 */                     strbXml.append(_tmpStr.toString());
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 306 */     return strbXml.toString();
/*     */   }
/*     */   public HashMap parseXml(String backXml) throws DocumentException {
/* 309 */     Document document = DocumentHelper.parseText(backXml);
/* 310 */     Element root = document.getRootElement();
/* 311 */     HashMap newVo = new HashMap();
/* 312 */     if (!root.elementIterator().hasNext())
/* 313 */       newVo.put(root.getName(), root.getText());
/*     */     else {
/* 315 */       newVo.put(root.getName(), iteratorElement(root));
/*     */     }
/*     */ 
/* 319 */     HashMap _attrVo = new HashMap();
/* 320 */     for (Iterator it2 = root.attributeIterator(); it2.hasNext(); ) {
/* 321 */       DefaultAttribute attrElement = (DefaultAttribute)it2.next();
/* 322 */       String name = attrElement.getName();
/* 323 */       String val = attrElement.getText();
/* 324 */       _attrVo.put(name, val);
/*     */     }
/* 326 */     newVo.put("____attr" + root.getName(), _attrVo);
/* 327 */     return newVo;
/*     */   }
/*     */   private HashMap iteratorElement(Element root) {
/* 330 */     HashMap vo = new HashMap();
/*     */ 
/* 332 */     HashMap multiName = new HashMap();
/* 333 */     for (Iterator it = root.elementIterator(); it.hasNext(); ) {
/* 334 */       Element element = (Element)it.next();
/* 335 */       if (multiName.containsKey(element.getName())) {
/* 336 */         multiName.put(element.getName(), "true");
/*     */       }
/*     */       else {
/* 339 */         multiName.put(element.getName(), "false");
/*     */       }
/*     */     }
/*     */ 
/* 343 */     for (Iterator it = root.elementIterator(); it.hasNext(); ) {
/* 344 */       Element element = (Element)it.next();
/* 345 */       List _list = element.elements();
/*     */ 
/* 347 */       String strName = element.getName();
/* 348 */       if (multiName.get(strName).equals("false"))
/*     */       {
/* 350 */         if (_list.size() == 0) {
/* 351 */           String strVal = element.getText();
/* 352 */           vo.put(strName, strVal);
/*     */         } else {
/* 354 */           HashMap _vo = new HashMap();
/*     */ 
/* 356 */           HashMap _multiName = new HashMap();
/* 357 */           for (int i = 0; i < _list.size(); i++) {
/* 358 */             Element _element = (Element)_list.get(i);
/* 359 */             if (_multiName.containsKey(_element.getName())) {
/* 360 */               _multiName.put(_element.getName(), "true");
/*     */             }
/*     */             else {
/* 363 */               _multiName.put(_element.getName(), "false");
/*     */             }
/*     */           }
/*     */ 
/* 367 */           for (int i = 0; i < _list.size(); i++) {
/* 368 */             Element _element = (Element)_list.get(i);
/* 369 */             String _elementName = _element.getName();
/* 370 */             if ((_multiName.containsKey(_elementName)) && (((String)_multiName.get(_elementName)).equals("true"))) {
/* 371 */               ArrayList _elementlst = (ArrayList)_vo.get(_elementName);
/* 372 */               if (_elementlst == null) _elementlst = new ArrayList();
/*     */ 
/* 374 */               if (_element.elements().isEmpty())
/* 375 */                 _elementlst.add(_element.getText());
/*     */               else {
/* 377 */                 _elementlst.add(iteratorElement(_element));
/*     */               }
/* 379 */               _vo.remove(_element.getName());
/* 380 */               _vo.put(_element.getName(), _elementlst);
/*     */             }
/* 382 */             else if (_element.elements().isEmpty()) {
/* 383 */               _vo.put(_element.getName(), _element.getText());
/*     */             } else {
/* 385 */               _vo.put(_element.getName(), iteratorElement(_element));
/*     */             }
/*     */ 
/* 389 */             HashMap _attrVo = new HashMap();
/* 390 */             for (Iterator it2 = _element.attributeIterator(); it2.hasNext(); ) {
/* 391 */               DefaultAttribute attrElement = (DefaultAttribute)it2.next();
/* 392 */               String name = attrElement.getName();
/* 393 */               String val = attrElement.getText();
/* 394 */               _attrVo.put(name, val);
/*     */             }
/* 396 */             _vo.put("____attr" + _element.getName(), _attrVo);
/*     */           }
/* 398 */           vo.put(element.getName(), _vo);
/*     */ 
/* 400 */           HashMap _attrVo = new HashMap();
/* 401 */           for (Iterator it2 = element.attributeIterator(); it2.hasNext(); ) {
/* 402 */             DefaultAttribute attrElement = (DefaultAttribute)it2.next();
/* 403 */             String name = attrElement.getName();
/* 404 */             String val = attrElement.getText();
/* 405 */             _attrVo.put(name, val);
/*     */           }
/* 407 */           vo.put("____attr" + element.getName(), _attrVo);
/*     */         }
/*     */       } else {
/* 410 */         ArrayList _lst = null;
/* 411 */         if (vo.containsKey(strName)) _lst = (ArrayList)vo.get(strName);
/* 412 */         if (_lst == null) _lst = new ArrayList();
/*     */ 
/* 414 */         HashMap __vo = new HashMap();
/* 415 */         if (_list.size() == 0) {
/* 416 */           String strVal = element.getText();
/* 417 */           __vo.put(element.getName(), strVal);
/* 418 */           _lst.add(__vo);
/*     */         }
/*     */         else {
/* 421 */           HashMap _multiName = new HashMap();
/* 422 */           for (int i = 0; i < _list.size(); i++) {
/* 423 */             Element _element = (Element)_list.get(i);
/* 424 */             if (_multiName.containsKey(_element.getName())) {
/* 425 */               _multiName.put(_element.getName(), "true");
/*     */             }
/*     */             else {
/* 428 */               _multiName.put(_element.getName(), "false");
/*     */             }
/*     */           }
/*     */ 
/* 432 */           HashMap _vo = new HashMap();
/* 433 */           for (int i = 0; i < _list.size(); i++) {
/* 434 */             Element _element = (Element)_list.get(i);
/*     */ 
/* 436 */             String _elementName = _element.getName();
/* 437 */             if ((_multiName.containsKey(_elementName)) && (((String)_multiName.get(_elementName)).equals("true"))) {
/* 438 */               ArrayList _elementlst = (ArrayList)_vo.get(_elementName);
/* 439 */               if (_elementlst == null) _elementlst = new ArrayList();
/* 440 */               if (_element.elements().isEmpty())
/* 441 */                 _elementlst.add(_element.getText());
/*     */               else {
/* 443 */                 _elementlst.add(iteratorElement(_element));
/*     */               }
/* 445 */               _vo.remove(_element.getName());
/* 446 */               _vo.put(_element.getName(), _elementlst);
/*     */             }
/* 448 */             else if (_element.elements().isEmpty()) {
/* 449 */               _vo.put(_element.getName(), _element.getText());
/*     */             } else {
/* 451 */               _vo.put(_element.getName(), iteratorElement(_element));
/*     */             }
/*     */ 
/* 456 */             HashMap _attrVo = new HashMap();
/* 457 */             for (Iterator it2 = _element.getParent().attributeIterator(); it2.hasNext(); ) {
/* 458 */               DefaultAttribute attrElement = (DefaultAttribute)it2.next();
/* 459 */               String name = attrElement.getName();
/* 460 */               String val = attrElement.getText();
/* 461 */               _attrVo.put(name, val);
/*     */             }
/* 463 */             _vo.put("____attr" + _element.getParent().getName(), _attrVo);
/*     */           }
/* 465 */           _lst.add(_vo);
/*     */         }
/* 467 */         vo.put(strName, _lst);
/*     */       }
/*     */ 
/* 471 */       HashMap _attrVo = new HashMap();
/* 472 */       for (Iterator it2 = element.attributeIterator(); it2.hasNext(); ) {
/* 473 */         DefaultAttribute attrElement = (DefaultAttribute)it2.next();
/* 474 */         String name = attrElement.getName();
/* 475 */         String val = attrElement.getText();
/* 476 */         _attrVo.put(name, val);
/*     */       }
/* 478 */       vo.put("____attr" + element.getName(), _attrVo);
/*     */     }
/*     */ 
/* 482 */     HashMap _attrVo = new HashMap();
/* 483 */     for (Iterator it2 = root.attributeIterator(); it2.hasNext(); ) {
/* 484 */       DefaultAttribute attrElement = (DefaultAttribute)it2.next();
/* 485 */       String name = attrElement.getName();
/* 486 */       String val = attrElement.getText();
/* 487 */       _attrVo.put(name, val);
/*     */     }
/* 489 */     vo.put("____attr" + root.getName(), _attrVo);
/* 490 */     return vo;
/*     */   }
/*     */ 
/*     */   private void filterParam(HashMap methodParam) {
/* 494 */     for (Iterator it = methodParam.entrySet().iterator(); it.hasNext(); ) {
/* 495 */       Map.Entry map = (Map.Entry)it.next();
/* 496 */       String key = (String)map.getKey();
/* 497 */       if (key.startsWith("____attr"))
/* 498 */         it.remove();
/* 499 */       Object val = map.getValue();
/* 500 */       if ((val instanceof HashMap)) {
/* 501 */         filterParam((HashMap)val);
/* 502 */       } else if ((val instanceof ArrayList)) {
/* 503 */         ArrayList lst = (ArrayList)val;
/* 504 */         for (int i = 0; i < lst.size(); i++) {
/* 505 */           Object _val = lst.get(i);
/* 506 */           if ((_val instanceof HashMap))
/* 507 */             filterParam((HashMap)_val);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.inf.framework.utils.ParseXml
 * JD-Core Version:    0.6.2
 */