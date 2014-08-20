package com.search.elasticsearch;

import com.alibaba.fastjson.JSONObject;
import com.framework.database.IBaseDAO;
import com.framework.spring.SpringContextHolder;
import com.search.elasticsearch.pojo.Goods;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-08-19 15:35
 * To change this template use File | Settings | File Templates.
 */
public class SearchTest {
    private Client client = null;
    private IBaseDAO defaultDAO = null;
    private String config[] = {"classpath*:spring/*.xml"};
    private final String DATA_BASE = String.valueOf(ConstsEnum.BIG_DATA).toLowerCase();


    @Before
    public void setUp() {
        /*
        * clienttransport.sniff:true 嗅探整个集群；
        * cluster.name:xx 集群名称，对应于/config/elasticsearch.yml 内部配置
        * 10s内未连接,则超时
        * */
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("client.transport.ping_timeout", "10s")
                .put("client.transport.sniff", true)
                .put("cluster.name", "elasticsearch").build();

        client = new TransportClient(settings).
                addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9300));
        //.addTransportAddress(new InetSocketTransportAddress("192.168.5.1",9300))  多个连接

        /*
        * spring
        * */
        System.setProperty("CONFIG", "F:\\git\\lance\\trunk\\search\\src\\test\\resources\\");
        ApplicationContext context = new ClassPathXmlApplicationContext(config);
        // System.err.println("spring初始化:======>"+context);
        defaultDAO = SpringContextHolder.getBean("defaultDAO");
    }

    @Test
    public void run() {
        System.err.println("搜索连接:" + client);
        System.err.println("数据库连接:" + defaultDAO);
    }

    private IndexRequestBuilder getPrepareIndex() {
        return client.prepareIndex();
    }

    private IndexRequestBuilder getPrepareIndex(String index, String type) {
        return client.prepareIndex(index, type);
    }

    private IndexRequestBuilder getPrepareIndex(String index, String type, String id) {
        return client.prepareIndex(index, type, id);
    }

    @Test
    public void createIndex() {
        Goods goods = new Goods();
        goods.setId("1");
        goods.setName("卡顿莫利 男士牛仔裤直筒修身休闲牛仔裤");
        goods.setIntro("京东商城向您保证所售商品均为正品行货，京东自营商品自带机打发票，与商品一起寄送。凭质保证书及京东商城发票，可享受全国联保服务（奢侈品、钟表除外；奢侈品、钟表由京东联系保修，享受法定三包售后服务），与您亲临商场选购的商品享受相同的质量保证。京东商城还为您提供具有竞争力的商品价格和运费政策，请您放心购买！ ");

        IndexResponse response = createIndex(goods, "goods");
        System.out.println("索引ID:" + response.getId());

    }

    private IndexResponse createIndex(Object obj, String type) {
        IndexResponse response = getPrepareIndex(DATA_BASE, type)
                .setSource(JSONObject.toJSONString(obj))
                .execute().actionGet();
        return response;
    }

    /*
    * 批量创建索引
    * */
    @Test
    public void createBatchIndex() {
        long begin_time = System.currentTimeMillis();
        System.out.println("批量创建索引开始:" + begin_time);
        String sql = "SELECT t.name,t.Gender,t.Address,t.Zip,t.Tel FROM users t limit ?,10000";
        for (int i = 2; i <= 200; i++) {
            List list = defaultDAO.queryForList(sql, (i - 1) * 10000);
            BulkResponse responses = createBatchIndex(list, "users");
            if (responses.hasFailures()) {
                System.out.println("批量创建索引错误信息==>" + responses.buildFailureMessage());
            }
            try {
                Thread.sleep(2500); //暂停2.5秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("end===>" + end + ",耗时:" + (end - begin_time) / 1000);
    }

    private BulkResponse createBatchIndex(List list, String type) {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        if (null != list && list.size() > 0) {
            for (Object obj : list) {
                bulkRequest.add(client.prepareIndex(DATA_BASE, type).setSource(JSONObject.toJSONString(obj)));
            }
        }
        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        return bulkResponse;
    }


    /*
    * 查询
    * */
    @Test
    public void query() {
        GetResponse response = queryById("goods", "4I2s3fgrSxqUjbN6vn5Wmg");
        String json = response.getSourceAsString();
        if (null != json) {
            System.out.println("查询结果:" + json);
        } else {
            System.out.println("未查询到数据!");
        }
    }

    public GetResponse queryById(String type, String id) {
        GetResponse response = client.prepareGet(DATA_BASE, type, id).execute().actionGet();
        return response;
    }

    /*
    * 查询 term must should
    * **/
    @Test
    public void queryTerm() {
        /*
        * termQuery match  bool  multiMatchQuery
        * */
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "毕");

        //分词
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", "马庆立");
        //不分词
        MatchQueryBuilder matchPhraseQuery = QueryBuilders.matchPhraseQuery("name", "马庆立");

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(termQueryBuilder).must(QueryBuilders.termQuery("gender", "m"))
                .must(QueryBuilders.termQuery("address", "广东"));

        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("马庆立", "name");

        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryString("特殊").field("name");

        //filter
        RangeFilterBuilder rangeFilterBuilder = FilterBuilders.rangeFilter("_score").from(5).to(3);
        FilteredQueryBuilder filteredQueryBuilder = QueryBuilders.filteredQuery(queryStringQueryBuilder, FilterBuilders.andFilter(rangeFilterBuilder));


        SearchResponse response = client.prepareSearch(DATA_BASE)
                .setTypes("users")
                .setQuery(queryStringQueryBuilder)
                .setSize(10000).setFrom(0) //setFrom 分页
                .setExplain(true) //按匹配度排序
                .setMinScore(5)
                .execute()
                .actionGet();

        SearchHits hits = response.getHits();
        if (null == hits) {
            System.out.println("未索引到数据!");
        } else {
            System.out.println("搜索结果记录行:" + hits.getTotalHits());
            for (SearchHit obj : hits) {
                System.out.println(obj.getSourceAsString());
            }
        }
    }

    private void queryMust(String type) {

    }


    /*
    *删除索引
    * */
    @Test
    public void deleteById() {
        DeleteResponse response = deteteById("CHgPvzMkQyOX8FAG75enzw", "goods");
        System.out.println("索引删除结果:");

    }

    private DeleteResponse deteteById(String id, String type) {
        return client.prepareDelete(DATA_BASE, type, id).execute().actionGet();
    }

    //批量删除索引
    private BulkResponse deleteBatchById(String type, List<String> ids) {
        BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
        for (String id : ids) {
            bulkRequestBuilder.add(client.prepareDelete(DATA_BASE, type, id).setOperationThreaded(false));
        }
        BulkResponse responses = bulkRequestBuilder.execute().actionGet();
        return responses;
    }

    /*
    * 查询删除索引
    * **/
    @Test
    public void delete() {
        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryString("特殊").field("name");

        DeleteByQueryResponse response = client.prepareDeleteByQuery(DATA_BASE)
                .setTypes("users")
                .setQuery(queryStringQueryBuilder)
                .execute().actionGet();
        System.out.println("删除索引!");
    }


    @After
    public void after() {
        System.out.println("shutdown");
        client.close();
    }

}
