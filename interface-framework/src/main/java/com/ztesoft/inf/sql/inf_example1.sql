select a.*,a.rowid from inf_comm_client_endpoint a where ep_id=1;
select a.*,a.rowid from inf_comm_client_request_user a where user_id=1 ;
select a.*,a.rowid from inf_comm_client_operation a where op_id=1 ;
select a.*,a.rowid from inf_comm_client_request a where req_id='INF_AddCust_REQ';
select a.*,a.rowid from inf_comm_client_response a where  rsp_id='INF_AddCust_RSP';


---1���ýӿڷ����û�,����һ�μ��� 

insert into inf_comm_client_operation (OP_ID, OP_CODE, EP_ID, REQ_ID, RSP_ID, OP_DESC, LOG_LEVEL, CLOSE_FLAG, REQ_USER_ID, DEAL_SUCCESS_FLAG)
values ('1', 'INF_AddCust', '1', 'INF_AddCust_REQ', 'INF_AddCust_RSP', '�����ͻ�', '', 'F', '1', '');





---2���ýӿڷ��ʵ�ַ,һ������,�Ժ���**************** ��ַ����ֳ��������
insert into inf_comm_client_endpoint (EP_ID, EP_ADDRESS, EP_DESC, TIMEOUT, EP_TYPE)
values ('1', 'http://10.45.47.190:7010/CrmWeb/services/exchangeSOAP', 'EOP�ӿ�', 30, 'HttpClient');


----3�ӿ�����
insert into inf_comm_client_operation (OP_ID, OP_CODE, EP_ID, REQ_ID, RSP_ID, OP_DESC, LOG_LEVEL, CLOSE_FLAG, REQ_USER_ID, DEAL_SUCCESS_FLAG)
values ('1', 'INF_AddCust', '1', 'INF_AddCust_REQ', 'INF_AddCust_RSP', '�����ͻ�', '', 'F', '1', '');



---4 ������,�����±�����blob�ֶ�

insert into inf_comm_client_request (REQ_ID, GVAR_ID, REQ_TPL, CLASS_PATH, QNAME_ENCODE, QNAME, OPER_CLASSNAME, OPER_METHOD)
values ('INF_AddCust_REQ', '', '<BLOB>', '', '', '', '', '');



<?xml version="1.0" encoding="UTF-8"?>
<ContractRoot>
    <TcpCont>
        <AppKey>1000000201</AppKey>
        <Method>orders.addCust</Method>
        <TransactionID>${inf.transactionIdGen()}</TransactionID>
        <ReqTime>${inf.reqTimeGen()}</ReqTime>
        <Sign>${Sign}</Sign>
        <Version>V1.0</Version>
    </TcpCont>
    ${SvcCont}
</ContractRoot>


-----5 ���ر���,�����±�����blob�ֶ�**********���ر��Ļ�û����
insert into inf_comm_client_response (RSP_ID, CDATA_PATH, TRANS_TPL, XML_MAPPER, TRANS_FAULT, RSP_CLASSPATH)
values ('INF_AddCust_RSP', '', '<BLOB>', '', '', '');



<#assign rsp=doc.ContractRoot.SvcCont>
<#if  rsp?has_content>
    ${rsp["@@markup"]}
<#else>
    <ContractRoot/>
</#if>








