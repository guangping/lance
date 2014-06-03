package com.ztesoft.inf.framework.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.DefaultAttribute;

import java.util.*;

/**
 * @author AyaKoizumi
 * @date 101026
 * @desc xml串->HashMap的转换,HashMap->xml串的转换
 * 
 * */
public class ParseXml {
	public String test_adsl_create = "<?xml version=\"1.0\" encoding=\"GB2312\"?><Root><Domain>WSS</Domain><Passwd>123456</Passwd><SrvCode>ServOrder</SrvCode><RespCode>0000</RespCode><Content><![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?><ServOrder><OrdSerial>1002031I0140035504</OrdSerial><ProdType>1000143</ProdType><ServNo>10000</ServNo><BureauCode>1100"
        + "</BureauCode><UserName>李晟</UserName><AccNbr>3214399</AccNbr><CustCardType>A</CustCardType><CustCardNo>45030519830216101X</CustCardNo><RelaName>李晟</RelaName><RelaTel>1111111</RelaTel><RelaAdder>南宁市西乡塘区大学东路160号瑞士花园翠湖苑6栋B座301房</RelaAdder><Adder>南宁市西乡塘区大学路160号瑞士花园翠湖苑6栋B座301房</Adder><ServDate></ServDate><XdslPassWord>131312</XdslPassWord><XdslLimit1>20</XdslLimit1"
        +"><XdslLimit2>1</XdslLimit2><ServID>3214399</ServID><InstallFlag>1</InstallFlag><InstallType>1</InstallType><Notes>新装ADSL宽带</Notes><SubProdInfo/><Book_flag></Book_flag><CustId></CustId><IMSI>201002030000062</IMSI><UIMCODE>2010020390000000062</UIMCODE><OrderOrigino>01</OrderOrigino><AutoOrder>1</AutoOrder></ServOrder>]]></Content><SeqId"
        + ">201002030000027282</SeqId></Root>";

public String test_mobile_create = "<?xml version=\"1.0\" encoding=\"GB2312\"?><Root><Domain>WSS</Domain><Passwd>123456</Passwd><SrvCode>ServOrder</SrvCode><RespCode>0000</RespCode><Content><![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?><ServOrder><OrdSerial>100203YS050035525</OrdSerial><ProdType>1000008</ProdType><ServNo>10000</ServNo><BureauCode>1100</BureauCod"
          + "e><UserName>欧骞</UserName><AccNbr>66</AccNbr><CustCardType>A</CustCardType><CustCardNo>430103197711164033</CustCardNo><RelaName></RelaName><RelaTel>1111111</RelaTel><RelaAdder>南宁市民主路35号</RelaAdder><Adder></Adder><ServDate></ServDate><XdslPassWord></XdslPassWord><XdslLimit1></XdslLimit1><XdslLimit2></XdslLimit2><ServID>3523587112</ServID>"
          + "<InstallFlag>1</InstallFlag><InstallType>1</InstallType><Notes>装 国内长途&#13;小灵通预约入网登记</Notes><SubProdInfo><ProdID>2000020</ProdID><OpType>0</OpType></SubProdInfo><Book_flag></Book_flag><CustId>3519333648</CustId><IMSI>201002030000077</IMSI><UIMCODE>2010020390000000077</UIMCODE><OrderOrigino>01</OrderOrigino><AutoOrder>1</AutoOrder></ServOrder>"
          + "]]></Content><SeqId>201002030000027448</SeqId></Root>";

public String test_self_create = "<?xml version=\"1.0\" encoding=\"GB2312\"?><Root><Domain>WSS</Domain><Passwd>123456</Passwd><SrvCode>SelfComm</SrvCode><RespCode>0000</RespCode><Content><![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?><SelfComm><CustId>2602219192</CustId><ServID>1101703607</ServID><OrdSerial>100204XXZL0035567</OrdSerial><SubProdInfo><ProdID>2020051</ProdID><OpType>0</OpType><PROPS><ID>10149</ID><VALUE>123"
        + "456</VALUE></PROPS><PROPS><ID>100236</ID><VALUE>07712840139</VALUE></PROPS><PROPS><ID>100244</ID><VALUE>18977164449</VALUE></PROPS><PROPS><ID>100242</ID><VALUE>13397706518</VALUE></PROPS><PROPS><ID>457</ID><VALUE>07712840139</VALUE></PROPS><PROPS><ID>100235</ID><VALUE>500</VALUE></PROPS><PROPS><ID>350</ID><VALUE>5</VALUE></PROPS><PROPS><ID>100121</ID"
        + "><VALUE>号簿容量在500个号码以下的5元/月</VALUE></PROPS></SubProdInfo></SelfComm>]]></Content><SeqId>201002040000027972</SeqId></Root>";

public String test_UserInfoQry =
"<?xml version=\"1.0\" encoding=\"GB2312\"?>"
+"<Root><Domain>WSS</Domain><Passwd>123456</Passwd><SrvCode>UserInfoQry</SrvCode>"
+"<Content><![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?>"
+"<UserInfoQry><LoginType>20</LoginType><AccNbr>bggaz081744</AccNbr>"
+"<PassWord>111111</PassWord><ProdType></ProdType><BureauCode>2500</BureauCode></UserInfoQry>]]>"
+"</Content><RespCode>0000</RespCode></Root>";

public String REQUEST_FINDBUNUS =
"<?xml version=\"1.0\" encoding=\"GB2312\"?>"
+ "<Root><Domain>WSS</Domain><Passwd>123456</Passwd>"
+ "<SrvCode>FindBunus</SrvCode><RespCode>0000</RespCode>"
+ "<Content><![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?>"
+ "<FindBunus><AccNbr>2600216291</AccNbr>"
+ "<AcctMonth>2010</AcctMonth><SearchMonth>0</SearchMonth></FindBunus>]]></Content></Root>";

public String REQUEST_staffpasswdqry= "<?xml version=\"1.0\" encoding=\"GB2312\"?><Root><Domain>WSS</Domain><Passwd>123456</Passwd><SrvCode>StaffPasswdQry</SrvCode><RespCode>0000</RespCode><Content>"
           +"<![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?><StaffPasswdQry><StaffCode>771110001</StaffCode><Password>111aaa</Password></StaffPasswdQry>]]></Content></Root>";


public String REQUEST_tksaleqry=  "<?xml version=\"1.0\" encoding=\"GB2312\"?><Root><Domain>WSS</Domain><Passwd>123456</Passwd><SrvCode>TkSaleQry</SrvCode><RespCode>0000</RespCode><Content>"
            +"<![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?><TkSaleQry><TkNo>7710000005470900</TkNo><Password>28640038</Password></TkSaleQry>]]></Content></Root>";

public String REQUEST_tksalechkqry= "<?xml version=\"1.0\" encoding=\"GB2312\"?><Root><Domain>WSS</Domain><Passwd>123456</Passwd><SrvCode>TkSaleChkQry</SrvCode><RespCode>0000</RespCode><Content>"
        +"<![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?><TkSaleChkQry><TkNo>7710000005470900</TkNo><Password>28640038</Password><TermNo>FA4624F1</TermNo><ChkOperId>771110001</ChkOperId></TkSaleChkQry>]]></Content></Root> ";

public String REQUEST_tksalechk= "<?xml version=\"1.0\" encoding=\"GB2312\"?><Root><Domain>WSS</Domain><Passwd>123456</Passwd><SrvCode>TkSaleChk</SrvCode><RespCode>0000</RespCode><Content>"
        +"<![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?><TkSaleChk><TkNo>7710000002170900</TkNo><Password>24905746</Password><TermNo>FA463C7F</TermNo><ChkOperId>771110001</ChkOperId><StaffCode>771110001</StaffCode></TkSaleChk>]]></Content></Root>";

public String REQUEST_MinimumOfferQry="<?xml version=\"1.0\" encoding=\"GB2312\"?><Root><Domain>WSS</Domain><Passwd>123456</Passwd><SrvCode>MinimumOfferQry</SrvCode><RespCode>0000</RespCode><Content>"
             +"<![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?><MinimumOfferQry><ProductId>40000684877</ProductId><BureauCode>1100</BureauCode><AccNbr>18977141525</AccNbr></MinimumOfferQry>]]></Content><SeqId>2010031900000311601</SeqId></Root>";

public String REQUEST_BuySaleQry="<?xml version=\"1.0\" encoding=\"GB2312\"?><Root><Domain>SMTS</Domain><Passwd>123456</Passwd><SrvCode>BuySaleQry</SrvCode><RespCode>0000</RespCode><Content>"
        +"<![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?><BuySaleQry><OrdSerial>100test2010033001002</OrdSerial><BureauCode>1100</BureauCode><AccNbr>18977164449</AccNbr><SubjectId>260017422</SubjectId><TimeLen>0</TimeLen></BuySaleQry>]]></Content></Root>";

public String REQUEST_CustStopReSume= "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
"<Root><Passwd>123456</Passwd><Domain>CSC</Domain><SrvCode>CustStopReSume</SrvCode><RespCode>0000</RespCode><Content><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
"<CustStopReSume><OperType>0</OperType><AccNbr>3983640</AccNbr><OrdSerial>csc000000000000000000000001302</OrdSerial><BureauCode>1100</BureauCode></CustStopReSume>]]></Content></Root>";

public String REQUEST_OfferResign="<?xml version=\"1.0\" encoding=\"GB2312\"?><Root><Domain>WSS</Domain><Passwd>123456</Passwd><SrvCode>OfferResign</SrvCode><RespCode>0000</RespCode><Content>"+
         "<![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?><OfferResign><OrdSerial>100420OFRG0035914</OrdSerial><BureauCode>1500</BureauCode><AccNbr>8632717</AccNbr><CompId>301000131</CompId><CompInstId>600113686749</CompInstId><CompKind>J</CompKind><ResignList><PrefId>11000950</PrefId><PrefInstId>600113688741</PrefInstId></ResignList><ChargeFlag>Y2</ChargeFlag></OfferResign>]]></Content><SeqId>201004200000035329</SeqId></Root>";
public String REQUEST_OfferResignQry="<?xml version=\"1.0\" encoding=\"GB2312\"?><Root><Domain>WSS</Domain><Passwd>123456</Passwd><SrvCode>OfferResignQry</SrvCode><RespCode>0000</RespCode><Content><![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?><OfferResignQry><AccNbr>5221209</AccNbr><BureauCode>1100</BureauCode></OfferResignQry>]]></Content><SeqId>201004260000637456</SeqId></Root>";

public String REQUEST_DemandOfResource="<?xml version=\"1.0\" encoding=\"GB2312\"?><Root><Domain>WSS</Domain><Passwd>123456</Passwd><SrvCode>DemandOfResourceQry</SrvCode><RespCode>0000</RespCode><Content><![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?><DemandOfResourceRequest><Amount>15</Amount><BureauCode>1100</BureauCode></DemandOfResourceRequest>]]></Content></Root>";

public String REQUEST_ResourceUserRelease="<?xml version=\"1.0\" encoding=\"GB2312\"?><Root><Domain>WSS</Domain><Passwd>123456</Passwd><SrvCode>ResourceUserReleaseQry</SrvCode><RespCode>0000</RespCode><Content><![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?><ResourceUserReleaseRequest><ResourceInstanceCode>18907713433</ResourceInstanceCode><BureauCode>1100</BureauCode><ActionCode>1</ActionCode></ResourceUserReleaseRequest>]]></Content></Root>";

public String testSendXml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
							"<SubAuthFromCRMReq>"+
							"<StreamingNo>{streamingNo}</StreamingNo>"+
							"<TimeStamp>{yyyymmdd}</TimeStamp>"+
							"<ProductOfferId>{productOfferId}</ProductOfferId>"+
							"<SystemId>{systemId1}</SystemId>"+
							"<SystemId>{systemId2}</SystemId>"+
							"<ProductInfo>"+
								"<ProdSpecCode>{cProductId}</ProdSpecCode>"+
								"<ProductNo>{productNo}</ProductNo>"+
								"<VProductInfo>"+
									"<ActionType>{actionId}</ActionType>"+
									"<VProductID>{cServProductId}</VProductID>"+
								"</VProductInfo>"+
							"</ProductInfo>"+
							
							"<ProductInfo>"+
							    "<ProdSpecCode>{cProductId1}</ProdSpecCode>"+
							    "<ProductNo>{productNo1}</ProductNo>"+
							    "<VProductInfo>"+
								    "<ActionType>{actionId1}</ActionType>"+
								    "<VProductID>{cServProductId1}</VProductID>"+
							    "</VProductInfo>"+
						    "</ProductInfo>"+
						    
						"</SubAuthFromCRMReq>";
	public String testBackXml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
							"<SubscribeAuthResp>" +
								"<StreamingNo>1</StreamingNo>"+
								"<ResultCode>0</ResultCode>"+
								"<ResultCode>1</ResultCode>"+
								"<ResultCode>2</ResultCode>"+
								"<ResultCode>3</ResultCode>"+
								"<ResultDesc>success1</ResultDesc>" +
								"<ResultDesc>success1</ResultDesc>" +
								"<ResultDesc>success1</ResultDesc>" +
								"<ResultDesc>"+
									"<ProdSpecCode>0</ProdSpecCode>"+
									"<ProductNo>13377050499</ProductNo>"+
									"<VProductInfo>"+
										"<VProductID>2021220</VProductID>" +
										"<OPResult>0</OPResult>" +
										"<OPDesc>鉴权通过1</OPDesc>"+
									"</VProductInfo>"+
									"<VProductInfo>"+
										"<VProductID>2021221</VProductID>" +
										"<OPResult>0</OPResult>" +
										"<OPDesc>鉴权通过2</OPDesc>"+
									"</VProductInfo>"+
								"</ResultDesc>"+
							
								"<ProductInfo>"+
									"<ProdSpecCode>0</ProdSpecCode>"+
									"<ProductNo>13377050499</ProductNo>"+
									"<VProductInfo>"+
										"<VProductID>2021220</VProductID>" +
										"<OPResult>0</OPResult>" +
										"<OPDesc>鉴权通过1</OPDesc>"+
									"</VProductInfo>"+
									"<VProductInfo>"+
										"<VProductID>2021221</VProductID>" +
										"<OPResult>0</OPResult>" +
										"<OPDesc>鉴权通过2</OPDesc>"+
									"</VProductInfo>"+
								"</ProductInfo>"+
							"</SubscribeAuthResp>";
	
	public static void main(String[]args) throws DocumentException{
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Root><ErrorInfo>SUCCESS</ErrorInfo><ResultCode>0</ResultCode><Content><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?><InvoicePrintResponse><PrintStr><INVOICE_FEE_ITEM3 /><INVOICE_FEE_ITEM8 /><FEE16 /><FEE3 /><FEE20 /><FEE5 /><INVOICE_FEE_ITEM19 /><CUST_NAME /><ORG_NAME>南宁市分公司</ORG_NAME><INVOICE_FEE_ITEM1>预付费充值</INVOICE_FEE_ITEM1><FEE17 /><CHARGE_FEE>伍拾圆零角零分</CHARGE_FEE><FEE2 /><SURPLUS>预付费小灵通(充值)</SURPLUS><INVOICE_FEE_ITEM16 /><FEE14 /><BILLING_SEGMENT /><INVOICE_FEE_ITEM7 /><INVOICE_FEE_ITEM4 /><FEE9 /><OPER_ID>中兴软创</OPER_ID><FEE6 /><INVOICE_FEE_ITEM12 /><PAY_METHOD /><INVOICE_FEE_ITEM17 /><FEE15 /><CUST_ID>2600111690</CUST_ID><FEE7 /><LAST_SURPLUS /><IMPREST /><INVOICE_FEE_ITEM13 /><FEE13 /><INVOICE_FEE_ITEM20 /><ACCEPT_ID>2009011300000000959327</ACCEPT_ID><INVOICE_FEE_ITEM10 /><FEE10 /><FEE11 /><CHARGE_TIME>2010-06-04 14:40:24</CHARGE_TIME><INVOICE_FEE_ITEM11 /><INVOICE_FEE_ITEM18 /><FEE1>50.0</FEE1><FEE8 /><INVOICE_FEE_ITEM9 /><FEE12 /><INVOICE_FEE_ITEM2 /><INVOICE_ID /><INVOICE_FEE_ITEM15 /><ORDER_ID>200901130026293517</ORDER_ID><FEE4 /><RECEIVABLE>50.0</RECEIVABLE><FEE19 /><FEE18 /><PRODUCT_NO>0771-4238179</PRODUCT_NO><INVOICE_FEE_ITEM5 /><INVOICE_FEE_ITEM14 /><CONTRACT_NO>                             收费时间 2010-06-04 14:40:24</CONTRACT_NO><INVOICE_FEE_ITEM6 /></PrintStr><ErrorInfo>SUCCESS</ErrorInfo><ResultCode>0</ResultCode></InvoicePrintResponse>]]></Content></Root>";
		ParseXml pase=new ParseXml();
		HashMap map = (HashMap)pase.parseXml(xml);
		HashMap mapq = (HashMap)pase.parseXml((String)((HashMap)map.get("Root")).get("Content"));
		HashMap cc=(HashMap)((HashMap)mapq.get("InvoicePrintResponse")).get("PrintStr");
		HashMap vo=new HashMap();
		for(Iterator it=cc.entrySet().iterator();it.hasNext();){
			Map.Entry _map=(Map.Entry)it.next();
			String key=(String)_map.getKey();
//			if("____attr".indexOf(key)>=0)
				if(key.startsWith("____attr"))
					continue;
				vo.put(key, _map.getValue());
				
		}
		System.out.println(vo);
		System.out.println(pase.parseHashMap(map));
	}
	private String xmlHead="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	public void setEncoding(String head){
		this.xmlHead=head;
	}
	public String parseHashMap(HashMap fields){
		StringBuffer _xml=new StringBuffer(this.xmlHead);
		_xml.append(iteratorHashMap(fields));
		return _xml.toString();
	}
	private String iteratorHashMap(HashMap fields){		
		StringBuffer strbXml=new StringBuffer("");
		if(fields!=null && !fields.isEmpty()){
			for(Iterator it=fields.entrySet().iterator();it.hasNext();){
				Map.Entry map =(Map.Entry)it.next();
				String key=(String)map.getKey();
				//如果是属性,那么跳过,属性的判断条件是____attr_开头的
				if(key.length()>10 && key.substring(0,8).equals("____attr"))
					continue;
				Object val=map.getValue();
				if(val instanceof String){//如果是String类型
					strbXml.append("<");
					strbXml.append(key);
					//查看是否有属性
					if(fields.containsKey("____attr"+key)){
						HashMap _attrVo=(HashMap)fields.get("____attr"+key);
						if(_attrVo!=null && !_attrVo.isEmpty()){
							strbXml.append(" ");
							for(Iterator itAttr=_attrVo.entrySet().iterator();itAttr.hasNext();){
								Map.Entry mapAttr =(Map.Entry)itAttr.next();
								String keyAttr=(String)mapAttr.getKey();
								String valAttr=(String)mapAttr.getValue();
								strbXml.append(keyAttr);
								strbXml.append("=\"");
								strbXml.append(valAttr);
								strbXml.append("\"");
							}
						}
					}
					strbXml.append(">");
					strbXml.append((String)val);
					strbXml.append("</");
					strbXml.append(key);
					strbXml.append(">");
				}else if(val instanceof HashMap){//如果是HashMap类型
					HashMap vo=(HashMap)val;
					if(vo==null || vo.isEmpty()){
						strbXml.append("<");
						strbXml.append(key);
						strbXml.append("/>");
					}else{
						strbXml.append("<");
						strbXml.append(key);
						//查看是否有属性
						if(fields.containsKey("____attr"+key)){
							HashMap _attrVo=(HashMap)fields.get("____attr"+key);
							if(_attrVo!=null && !_attrVo.isEmpty()){
								strbXml.append(" ");
								for(Iterator itAttr=_attrVo.entrySet().iterator();itAttr.hasNext();){
									Map.Entry mapAttr =(Map.Entry)itAttr.next();
									String keyAttr=(String)mapAttr.getKey();
									String valAttr=(String)mapAttr.getValue();
									strbXml.append(keyAttr);
									strbXml.append("=\"");
									strbXml.append(valAttr);
									strbXml.append("\"");
								}
							}
						}
						strbXml.append(">");
						strbXml.append(iteratorHashMap(vo));
						strbXml.append("</");
						strbXml.append(key);
						strbXml.append(">");
					}
				}else if(val instanceof ArrayList){//如果是ArrayList类型
					ArrayList _val=((ArrayList)val);
					if(_val==null || _val.isEmpty() ){
						strbXml.append("<");
						strbXml.append(key);
						strbXml.append("/>");
					}else{
						for(int i=0;i<_val.size();i++){
							HashMap vo=(HashMap)_val.get(i);
							if(vo==null || vo.isEmpty()){
								strbXml.append("<");
								strbXml.append(key);
								strbXml.append("/>");
							}else{
								//特殊判断,如果里面的HashMap都是字符串,并且只有一个key,并且要和上一级的key相同,那么直接在这里就把所有的值赋进去
								boolean isAllStr=true;
								StringBuffer _tmpStr=new StringBuffer("");
								for(Iterator it2=vo.entrySet().iterator();it2.hasNext();){
									Map.Entry map2=(Map.Entry)it2.next();
									String key2=(String)map2.getKey();
									Object _val2=map2.getValue();
									if(_val2 instanceof String && key2.equals(key)){
										_tmpStr.append("<");
										_tmpStr.append(key);
										_tmpStr.append(">");
										_tmpStr.append(_val2);
										_tmpStr.append("</");
										_tmpStr.append(key);
										_tmpStr.append(">");
									}else{
										isAllStr=false;
										break;
									}
								}
								if(!isAllStr){
									strbXml.append("<");
									strbXml.append(key);
									//查看是否有属性
									if(vo.containsKey("____attr"+key)){
										HashMap _attrVo=(HashMap)vo.get("____attr"+key);
										if(_attrVo!=null && !_attrVo.isEmpty()){
											strbXml.append(" ");
											for(Iterator itAttr=_attrVo.entrySet().iterator();itAttr.hasNext();){
												Map.Entry mapAttr =(Map.Entry)itAttr.next();
												String keyAttr=(String)mapAttr.getKey();
												String valAttr=(String)mapAttr.getValue();
												strbXml.append(keyAttr);
												strbXml.append("=\"");
												strbXml.append(valAttr);
												strbXml.append("\"");
											}
										}
									}
									strbXml.append(">");
									strbXml.append(iteratorHashMap((HashMap)vo));
									strbXml.append("</");
									strbXml.append(key);
									strbXml.append(">");
								}else{
									strbXml.append(_tmpStr.toString());
								}
							}
						}
					}
				}
			}
		}
		return strbXml.toString();
	}
	public HashMap parseXml(String backXml) throws DocumentException{
		Document document = DocumentHelper.parseText(backXml);
		Element root=document.getRootElement();
		HashMap newVo=new HashMap();
		if(!root.elementIterator().hasNext()){//如果没有子节点例如<root>asdfsdf<root>
			newVo.put(root.getName(), root.getText());
		}else{
			newVo.put(root.getName(), iteratorElement(root));
		}
		//-------------------------attribute------------------------
		//查看是否有属性,如果有,那么用attr__name存放,注意,这里是获取上一级的属性值,因为作为ArrayList,当前这个级不存放属性,都放在list下的每个map里
		HashMap _attrVo=new HashMap();
		for(Iterator it2=root.attributeIterator();it2.hasNext();){
			DefaultAttribute attrElement=(DefaultAttribute) it2.next();
			String name=attrElement.getName();
			String val=attrElement.getText();
			_attrVo.put(name, val);
		}
		newVo.put("____attr"+root.getName(), _attrVo);
		return newVo;
	}
	private HashMap iteratorElement(Element root){
		HashMap vo=new HashMap();
		//首先判断这个list下的结点的名称是否有重复的,如果有,那么把这个名称记录下来,然后用ArrayList来记录,就不用HashMap来记录了
		HashMap multiName=new HashMap();
		for(Iterator it=root.elementIterator();it.hasNext();){
			Element element=(Element)it.next();
			if(multiName.containsKey(element.getName())){
				multiName.put(element.getName(), "true");
			}
			else{
				multiName.put(element.getName(), "false");
			}
		}
		
		for(Iterator it=root.elementIterator();it.hasNext();){
			Element element=(Element)it.next();
			List _list=element.elements();
//			int mSize=_list.size();
			String strName=element.getName();
			if(multiName.get(strName).equals("false")){//如果这个节点下没有重复的名称，那么用HashMap来存放子节点
				
				if(_list.size()==0){
					String strVal=element.getText();
					vo.put(strName, strVal);
				}else{
					HashMap _vo=new HashMap();
					//在这个循环里,每个结点的名称也可能是重复的
					HashMap _multiName=new HashMap();
					for(int i=0;i<_list.size();i++){
						Element _element=(Element)_list.get(i);
						if(_multiName.containsKey(_element.getName())){
							_multiName.put(_element.getName(), "true");
						}
						else{
							_multiName.put(_element.getName(), "false");
						}
					}
					
					for(int i=0;i<_list.size();i++){
						Element _element=(Element)_list.get(i);
						String _elementName=_element.getName();
						if(_multiName.containsKey(_elementName) && ((String)_multiName.get(_elementName)).equals("true")){//如果有重复值,那么做成一个ArrayList
							ArrayList _elementlst=(ArrayList)_vo.get(_elementName);
							if(_elementlst==null)_elementlst=new ArrayList();
							
							if(_element.elements().isEmpty()){//如果是字符串
								_elementlst.add(_element.getText());
							}else{//如果是list
								_elementlst.add(iteratorElement(_element));
							}
							_vo.remove(_element.getName());
							_vo.put(_element.getName(),_elementlst);
						}else{
							if(_element.elements().isEmpty()){//如果是字符串
								_vo.put(_element.getName(), _element.getText());
							}else{//如果是element
								_vo.put(_element.getName(), iteratorElement(_element));
							}
						}
						//查看是否有属性,如果有,那么用attr__name存放
						HashMap _attrVo=new HashMap();
						for(Iterator it2=_element.attributeIterator();it2.hasNext();){
							DefaultAttribute attrElement=(DefaultAttribute) it2.next();
							String name=attrElement.getName();
							String val=attrElement.getText();
							_attrVo.put(name, val);
						}
						_vo.put("____attr"+_element.getName(), _attrVo);
					}
					vo.put(element.getName(), _vo);
					//查看是否有属性,如果有,那么用attr__name存放
					HashMap _attrVo=new HashMap();
					for(Iterator it2=element.attributeIterator();it2.hasNext();){
						DefaultAttribute attrElement=(DefaultAttribute) it2.next();
						String name=attrElement.getName();
						String val=attrElement.getText();
						_attrVo.put(name, val);
					}
					vo.put("____attr"+element.getName(), _attrVo);
				}
			}else{//如果这个节点下有重复的名称，那么用ArrayList来存放子节点
				ArrayList _lst=null;
				if(vo.containsKey(strName)) _lst=(ArrayList)vo.get(strName);
				if(_lst==null)_lst=new ArrayList();
				
				HashMap __vo=new HashMap();
				if(_list.size()==0){//叶子结点
					String strVal=element.getText();
					__vo.put(element.getName(), strVal);
					_lst.add(__vo);
				}else{//非叶子结点
					//在这个循环里,每个结点的名称也可能是重复的
					HashMap _multiName=new HashMap();
					for(int i=0;i<_list.size();i++){
						Element _element=(Element)_list.get(i);
						if(_multiName.containsKey(_element.getName())){
							_multiName.put(_element.getName(), "true");
						}
						else{
							_multiName.put(_element.getName(), "false");
						}
					}
					
					HashMap _vo=new HashMap();
					for(int i=0;i<_list.size();i++){
						Element _element=(Element)_list.get(i);
						//-------------------------context------------------------
						String _elementName=_element.getName();
						if(_multiName.containsKey(_elementName) && ((String)_multiName.get(_elementName)).equals("true") ){
							ArrayList _elementlst=(ArrayList)_vo.get(_elementName);
							if(_elementlst==null)_elementlst=new ArrayList();
							if(_element.elements().isEmpty()){
								_elementlst.add(_element.getText());
							}else{
								_elementlst.add(iteratorElement(_element));
							}
							_vo.remove(_element.getName());
							_vo.put(_element.getName(), _elementlst);
						}else{
							if(_element.elements().isEmpty()){
								_vo.put(_element.getName(), _element.getText());
							}else{//如果是list
								_vo.put(_element.getName(), iteratorElement(_element));
							}
						}
						//-------------------------attribute------------------------
						//查看是否有属性,如果有,那么用attr__name存放,注意,这里是获取上一级的属性值,因为作为ArrayList,当前这个级不存放属性,都放在list下的每个map里
						HashMap _attrVo=new HashMap();
						for(Iterator it2=_element.getParent().attributeIterator();it2.hasNext();){
							DefaultAttribute attrElement=(DefaultAttribute) it2.next();
							String name=attrElement.getName();
							String val=attrElement.getText();
							_attrVo.put(name, val);
						}
						_vo.put("____attr"+_element.getParent().getName(), _attrVo);
					}
					_lst.add(_vo);
				}
				vo.put(strName, _lst);
			}
			//-------------------------attribute------------------------
			//查看是否有属性,如果有,那么用attr__name存放,注意,这里是获取上一级的属性值,因为作为ArrayList,当前这个级不存放属性,都放在list下的每个map里
			HashMap _attrVo=new HashMap();
			for(Iterator it2=element.attributeIterator();it2.hasNext();){
				DefaultAttribute attrElement=(DefaultAttribute) it2.next();
				String name=attrElement.getName();
				String val=attrElement.getText();
				_attrVo.put(name, val);
			}
			vo.put("____attr"+element.getName(), _attrVo);
		}
		//-------------------------attribute------------------------
		//查看是否有属性,如果有,那么用attr__name存放,注意,这里是获取上一级的属性值,因为作为ArrayList,当前这个级不存放属性,都放在list下的每个map里
		HashMap _attrVo=new HashMap();
		for(Iterator it2=root.attributeIterator();it2.hasNext();){
			DefaultAttribute attrElement=(DefaultAttribute) it2.next();
			String name=attrElement.getName();
			String val=attrElement.getText();
			_attrVo.put(name, val);
		}
		vo.put("____attr"+root.getName(), _attrVo);
		return vo;
	}
	//add by AyaKoizumi 101029
	private void filterParam(HashMap methodParam){
		for(Iterator it=methodParam.entrySet().iterator();it.hasNext();){
			Map.Entry map=(Map.Entry)it.next();
			String key=(String)map.getKey();
			if(key.startsWith("____attr"))//忽略属性
				it.remove();
			Object val=map.getValue();
			if(val instanceof HashMap){
				this.filterParam((HashMap)val);
			}else if(val instanceof ArrayList){
				ArrayList lst=(ArrayList)val;
				for(int i=0;i<lst.size();i++){
					Object _val=lst.get(i);
					if(_val instanceof HashMap){
						this.filterParam((HashMap)_val);
					}
				}
			}
		}
	}
}

