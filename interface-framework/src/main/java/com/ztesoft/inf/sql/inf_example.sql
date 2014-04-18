
select * from inf_comm_client_request_user where user_id=-1;
select * from inf_comm_client_endpoint where ep_id=3001;
select * from inf_comm_client_operation where op_code='INF_GetCustBasicByProduct' ;
select * from inf_comm_client_request where req_id='INF_GetCustBasicByProduct_REQ';
select * from inf_comm_client_response  where rsp_id='INF_GetCustBasicByProduct_RSP';


---1���ýӿڷ����û�,����һ�μ���

insert into inf_comm_client_request_user (USER_ID, USER_CODE, USER_NAME, USER_PWD, USER_PARAM, USER_DESC)
values ('-1', 'mmarkt', 'CRM�ӿ��û�', 'mmarkt', '', 'Ĭ���û�');


---2���ýӿڷ��ʵ�ַ,һ������,�Ժ���
insert into inf_comm_client_endpoint (EP_ID, EP_ADDRESS, EP_DESC, TIMEOUT, EP_TYPE)
values ('3001', 'http://10.45.47.190:7010/CrmWeb/services/exchangeSOAP', '����CRM�Է���ӿڵ�ַ--����', 30, '');


----3�ӿ�����
insert into inf_comm_client_operation (OP_ID, OP_CODE, EP_ID, REQ_ID, RSP_ID, OP_DESC, LOG_LEVEL, CLOSE_FLAG, REQ_USER_ID, DEAL_SUCCESS_FLAG)
values ('1230', 'INF_GetCustBasicByProduct', '3001', 'INF_GetCustBasicByProduct_REQ', 'INF_GetCustBasicByProduct_RSP', '�����Է���-��ݲ�Ʒ�����ѯ�ͻ�����Ϣ', '', 'F', '', '');


---4 ������,�����±�����blob�ֶ�

insert into inf_comm_client_request (REQ_ID, GVAR_ID, REQ_TPL, CLASS_PATH, QNAME_ENCODE, QNAME, OPER_CLASSNAME, OPER_METHOD)
values ('INF_GetCustBasicByProduct_REQ', '', '<BLOB>', '', '', '', '', '');



<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:exc="http://ws.inf.crm.ztesoft.com/exchange/">
   <soapenv:Header/>
   <soapenv:Body>
      <exc:exchange>
         <in><![CDATA[
<Root><Header>
<ExchangeId>${inf.exchangeidgen()}</ExchangeId>
<BizCode>Query</BizCode>
<ClientId>${inf.user_code}</ClientId>
<Password>${inf.user_pwd}</Password>
</Header>
<Query>
<Code>INF_GetCustBasicByProduct</Code>
<QueryParam>
<acc_nbr>${accNbr}</acc_nbr>
<lan_Code>${lan_code}</lan_Code>
</QueryParam>
</Query>
</Root>]]></in>
      </exc:exchange>
   </soapenv:Body>
</soapenv:Envelope>


-----5 ���ر���,�����±�����blob�ֶ�
insert into inf_comm_client_response (RSP_ID, CDATA_PATH, TRANS_TPL, XML_MAPPER, TRANS_FAULT, RSP_CLASSPATH)
values ('INF_GetCustBasicByProduct_RSP', 'namespace.soap=http://schemas.xmlsoap.org/soap/envelope/
namespace.ns=http://ws.inf.crm.ztesoft.com/exchange/
soap:Envelope/soap:Body/ns:exchangeResponse/out', '<BLOB>', '<MapperContext>
    <XpathImplicitCollection xpath=''/QueryResults'' nodeName=''CustBasicInfo'' fieldName=''CustBasicInfo''/>
</MapperContext>', '', '');




<#assign rsp=doc.Root.QueryResults>
<#if  rsp?has_content>
    ${rsp["@@markup"]}
<#else>
    <QueryResults />
</#if>









