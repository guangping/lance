package com.github.lance.spring;

import com.alibaba.fastjson.JSONObject;
import com.framework.database.IBaseDAO;
import com.framework.spring.SpringContextHolder;
import com.framework.utils.ReflectionUtil;
import com.github.lance.pojo.Operation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-05-05 20:52
 * To change this template use File | Settings | File Templates.
 */
public class MainSpringTest {

    private ApplicationContext context = null;
    private IBaseDAO defaultDAO = null;
    private JdbcTemplate jdbcTemplate = null;

    @BeforeMethod
    public void setUp() {
        //-DCONFIG=F:\git\lance\trunk\base\src\main\resources
        System.setProperty("CONFIG", "F:\\git\\lance\\trunk\\base\\src\\main\\resources");
        String[] configs = new String[]{"classpath*:spring/*.xml"};
        context = new ClassPathXmlApplicationContext(configs);
        jdbcTemplate = SpringContextHolder.getBean("jdbcTemplate");
        defaultDAO = SpringContextHolder.getBean("defaultDAO");
    }

    @Test
    public void run() {
        System.out.println("spring:===>" + context);
    }

    @Test
    public void jdbc() {

        System.out.println("jdbcTemplate:中文====>" + jdbcTemplate);
    }

    @Test
    public void inf_operation() {
        String sql = "SELECT * FROM INF_COMM_CLIENT_OPERATION";
        jdbcTemplate = SpringContextHolder.getBean("jdbcTemplate");
        List list = jdbcTemplate.queryForList(sql);
        System.out.println(list.size());
    }


    @Test
    public void defaultDao() {
        String sql = "SELECT * FROM INF_COMM_CLIENT_OPERATION";
        List list = defaultDAO.queryForList(sql);
        System.out.println(JSONObject.toJSONString(list));
    }


    @Test
    public void defaultDaoSave() {
        Operation operation = new Operation();
        operation.setCode("ESB");
        operation.setDescription("测试");
        operation.setFlag("true");
        operation.setEndpoint_id("1");
        operation.setRequest_id("1");
        operation.setResponse_id("1");

        String id=defaultDAO.insert("INF_COMM_CLIENT_OPERATION",operation);
        System.out.println("返还ID:"+id);
    }

    @Test
    public void defaultDaoMapSave() {
        Operation operation = new Operation();
        operation.setCode("ESB");
        operation.setDescription("测试");
        operation.setFlag("true");
        operation.setEndpoint_id("1");
        operation.setRequest_id("1");
        operation.setResponse_id("1");


        String id=defaultDAO.insert("INF_COMM_CLIENT_OPERATION",JSONObject.parseObject(JSONObject.toJSONString(operation),Map.class));
        System.out.println("返还ID:"+id);
    }

    @Test
    public void defaultDaoBatchInsert() {
        Operation operation = null;
        List list=new ArrayList();
        for(int i=0;i<1000000;i++){
            operation=new Operation();
            operation.setCode("ESB"+i);
            operation.setDescription("测试"+1);
            operation.setFlag("true");
            operation.setEndpoint_id("1"+i);
            operation.setRequest_id("1"+i);
            operation.setResponse_id("1"+i);
            list.add(operation);
        }
        defaultDAO.batchInsert("INF_COMM_CLIENT_OPERATION",list);
    }

    @Test
    public void updateMap(){
        Map fields=new HashMap();
        fields.put("code","sms");
        fields.put("description","短信发送");
        fields.put("flag","143214433232");
        Map where=new HashMap();
        where.put("id","2147483647");

        defaultDAO.update("INF_COMM_CLIENT_OPERATION",fields,where);
    }



    @Test
    public void getFileds() {
        List<String> list = ReflectionUtil.getFields(Operation.class);
        System.out.println(list.size());
    }

    @Test
    public void runAutoPk() {
        Operation operation = new Operation();
        operation.setCode("ESB");
        operation.setDescription("测试");
        operation.setFlag("true");
        operation.setEndpoint_id("1");
        operation.setRequest_id("1");
        operation.setResponse_id("1");
        Map<String, Object> map = ReflectionUtil.po2Map(operation);
        System.out.println(map);
    }

    @Test
    public void pingSql() {
        Operation operation = new Operation();
        operation.setCode("ESB");
        operation.setDescription("测试");
        operation.setFlag("true");
        operation.setEndpoint_id("1");
        operation.setRequest_id("1");
        operation.setResponse_id("1");

        handle("client", operation);
    }

    private void handle(String table, Object arg) {
        Map<String, Object> map = ReflectionUtil.po2Map(arg);
        String item[] = map.keySet().toArray(new String[]{});
        System.out.println(item.length);
        StringBuffer buffer = new StringBuffer(1000);
        buffer.append("insert into ");
        buffer.append(table);
        buffer.append(" values(");
        for (String key : item) {
            buffer.append(":");
            buffer.append(key);
            buffer.append(",");
        }
        buffer.deleteCharAt(buffer.length() - 1);
        buffer.append(")");
        System.out.println("sql===>" + buffer.toString());
        //SqlParameterSource parameterSource=new BeanPropertySqlParameterSource(arg);
    }

}
