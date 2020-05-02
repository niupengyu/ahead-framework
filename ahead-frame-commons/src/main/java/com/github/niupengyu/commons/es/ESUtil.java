package com.github.niupengyu.commons.es;

import com.github.niupengyu.core.util.DateUtil;
import com.github.niupengyu.core.util.IdGeneratorUtil;
import com.github.niupengyu.core.util.StringUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;


public class ESUtil {

    //private DaoConfig daoConfig=new DaoConfig();

    private String index;

    private TransportClient transportClient;

    private static final Logger logger= LoggerFactory.getLogger(ESUtil.class);

    public ESUtil(String index){
        this.index=index;
    }

    public void connection(String name,String ip,int port,JSONObject param){
        this.transportClient=elasticsearchClient(name,ip,port,param);
    }

    //创建索引（数据库）,相当于创建数据库
    public  void createIndex(String index)
    {
        transportClient.admin().indices().prepareCreate(index).execute().actionGet();
    }
    /**
     * 在指定的index（数据库）创建指定的type（表），相当于在指定的数据库上创建指定的表,并且同时指定表的字段
     * 创建mapping
     * 注：创建的时候，需要 index已经创建才行，要不然会报错
     */
    public void createBangMapping(String type){
        try {
            PutMappingRequest mapping = Requests.putMappingRequest(index).type(type).source(getMapping());
            transportClient.admin().indices().putMapping(mapping).actionGet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加mapping规则，mapping的更新可以参考 elastic 官网
     * 相当于在指定的数据库上创建指定的表的时候指定表字段用的
     */
    public XContentBuilder getMapping() throws Exception{
        XContentBuilder mapping = null;
        mapping = jsonBuilder()
                .startObject()
                //.startObject("_ttl").field("enabled",false).endObject()//es2.2版本支持此字段，es5.0以上不支持这个字段
                .startObject("properties") // type 数据类型； store 是否存储;analyzer 设置分词；search_analyzer 查询时是否分词
                .startObject("name").field("type","string").field("store", "yes").endObject()
                .startObject("address").field("type","string").field("analyzer","ik_smart").field("store", "yes").field("search_analyzer","ik_smart").endObject()
                .startObject("jd").field("type", "string").field("store", "yes").endObject()
                .startObject("wd").field("type", "string").field("store", "yes").endObject()
                .startObject("province").field("type", "string").field("store", "yes").endObject()
                .startObject("city").field("type", "string").field("store", "yes").endObject()
                .endObject()
                .endObject();
        return mapping;
    }
    /**
     * 添加索引数据
     */
    public void addData(String type,Map<String,Object> map,String uuid){
        IndexResponse response = transportClient.prepareIndex().setIndex(index).setId(uuid)//.setId("123"+i) 不设置的话id将自动生成
                .setType(type).setSource(map).execute().actionGet();
        // Index name
        String _index = response.getIndex();
        // Type name
        String _type = response.getType();
        // Document ID (generated or not)
        String _id = response.getId();
        // Version
        long _version = response.getVersion();
        System.out.println(_index+"----"+_type+"---:"+_id+"创建成功！");
//        daoConfig.elasticsearchClient().prepareIndex().setId(index)
//                .setType(type).sets
    }
    /**
     * 添加索引数据
     */
    public void addData(String type,Map<String,Object> map){
        IndexResponse response = transportClient.prepareIndex().setIndex(index)//.setId("123"+i) 不设置的话id将自动生成
                .setType(type).setSource(map).execute().actionGet();
        // Index name
        String _index = response.getIndex();
        // Type name
        String _type = response.getType();
        // Document ID (generated or not)
        String _id = response.getId();
        // Version
        long _version = response.getVersion();
        System.out.println(_index+"----"+_type+"---:"+_id+"创建成功！");
//        daoConfig.elasticsearchClient().prepareIndex().setId(index)
//                .setType(type).sets
    }

    public void addDatas(String type,List<Map<String,String>> datas){
        TransportClient client=transportClient;
        int size=datas.size();
        BulkRequestBuilder bulkRequestBuilder=client.prepareBulk();
        for (int i = 0; i < size; i++) {
            Map<String,String> map = datas.get(i);
            String id=map.get("id");
            //业务对象
            IndexRequestBuilder indexRequest = client.prepareIndex(index, type)
                    .setSource()
                    //指定不重复的ID
                    .setSource(map).setId(StringUtil.isNull(id)? IdGeneratorUtil.uuid32():id);
            //添加到builder中
            bulkRequestBuilder.add(indexRequest);
        }
        BulkResponse bulkResponse = bulkRequestBuilder.execute().actionGet();
        if (bulkResponse.hasFailures()) {
            logger.error(bulkResponse.buildFailureMessage());
        }
    }

    /**
     * 根据数据id删除数据
     */
    public void delDataById(String type,String id) {
        DeleteResponse deleteresponse = transportClient.prepareDelete(index, type,id)
                .execute()
                .actionGet();
        //System.out.println("删除一条数据：" + response.isFound());//es2.2支持的方法
        System.out.println(deleteresponse.getVersion());//es5.5支持的方法
    }
    /**
     * 根据id更新数据
     */
    public void updateDataById(String type,String id,Map<String,String> map) {
        UpdateResponse response = null;
        try {
            //方式一 response = client.prepareUpdate(index,type,id).setDoc(jsonBuilder().startObject().field("province", "山东省").endObject()).get();
            response = transportClient.prepareUpdate(index,type,id).setDoc(map).get();
            System.out.println("修改一条记录：" + response.getVersion());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 精准查询并且是and关系，must为and，多个and就是多个must
     * @param
     * @param
     * @throws Exception
     */
    public  List<Map<String, Object>> myBoolQueryBuilder(String type,Map<String,String> queryList) throws Exception {

        QueryBuilder query = QueryBuilders.queryStringQuery(queryList.get("address"));//将要查询的语句进行分词
        MatchQueryBuilder mustQuery1 = QueryBuilders.matchQuery("province",queryList.get("province"));
        MatchQueryBuilder mustQuery2 = QueryBuilders.matchQuery("city",queryList.get("city"));
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(query).must(mustQuery1).must(mustQuery2);
        SearchResponse response = transportClient.prepareSearch(index).setTypes(type).setQuery(boolQueryBuilder).setMinScore(23f).get();
        List<Map<String, Object>> dataMapList = new ArrayList<>();
        if(response.getHits().getTotalHits() != 0) {
            for (SearchHit hit : response.getHits().getHits()) {
                //System.out.println(hit.getScore()+" --> "+hit.getSourceAsString());
                Map<String, Object> dataMap = hit.getSourceAsMap();
                dataMapList.add(dataMap);
            }
        }
        return dataMapList;
    }
    /**
     * MatchQuery
     * */
    public List<Map<String, Object>> myMatchQuery(String type,Map<String,String> queryList)
    {
        //匹配查询
        /*QueryBuilder qb = matchQuery("address","催警：柳行小区47-2-501");*/
        BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
        if(queryList.size()> 0)
        {
            Set<String> keys = queryList.keySet();
            for (String  key: keys) {
                String value = queryList.get(key);
                mustQuery.must(matchQuery(key,value));
            }
        }
        SearchResponse response = transportClient.prepareSearch(index).setTypes(type).setQuery(mustQuery).get();
        List<Map<String, Object>> dataMapList = new ArrayList<>();
        if(response.getHits().getTotalHits() != 0) {
            for (SearchHit hit : response.getHits().getHits()) {
                //System.out.println(hit.getScore()+" --> "+hit.getSourceAsString());
                Map<String, Object> dataMap = hit.getSourceAsMap();
                dataMapList.add(dataMap);
            }
        }
        return dataMapList;
    }

    public boolean isIndexExist(String index) {
        return this.transportClient.admin().indices().prepareExists(index).execute().actionGet().isExists();
    }

    public PutMappingResponse addIndexAndType(String type) throws Exception {

        // 创建索引映射,相当于创建数据库中的表操作
        /*if(!isIndexExist(index)){
            CreateIndexRequestBuilder cib = this.daoConfig.elasticsearchClient().admin().indices().prepareCreate(index);
        }*/
        XContentBuilder source = XContentFactory.jsonBuilder().startObject().startObject("properties") // 设置自定义字段
                .startObject("goodsName").field("type", "string").endObject() // 商品名称
                .startObject("goodsPrice").field("type", "double").endObject()// 商品价格
                .startObject("goodsUser").field("type", "string").endObject()// 商品主人
                .startObject("goodsTime").field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss").endObject() // 商品上架时间
                .endObject().endObject();
        PutMappingRequest mapping = Requests.putMappingRequest(index).type(type).source(source);
        return transportClient.admin().indices().putMapping(mapping).actionGet();
    }

    public void types(String index){
        GetMappingsResponse res = null;
        try {
            res = transportClient.admin().indices().getMappings(new GetMappingsRequest().indices(index)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        ImmutableOpenMap<String, MappingMetaData> mapping = res.mappings().get(index);
        for (ObjectObjectCursor<String, MappingMetaData> c : mapping)
        {
            System.out.println("type = "+c.key);
            System.out.println("columns = "+c.value.source());
        }
    }

    public void types(){
        GetMappingsResponse res = null;
        try {
            res = transportClient.admin().indices().getMappings(
                    new GetMappingsRequest().indices(index)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        ImmutableOpenMap<String, MappingMetaData> mapping = res.mappings().get(index);
        for (ObjectObjectCursor<String, MappingMetaData> c : mapping)
        {
            System.out.println("type = "+c.key);
            System.out.println("columns = "+c.value.source());
        }
    }

    public void mapping(){
        ImmutableOpenMap<String, MappingMetaData> mappings = transportClient.admin().cluster().prepareState().execute()
                .actionGet().getState().getMetaData().getIndices().get(index).getMappings();
        String  mapping = mappings.get("goods").source().toString();
        System.out.println(mapping);
    }

    public void search() throws Exception {
        JSONArray array=new JSONArray();
        LocalDate yesterday=LocalDate.now().minusDays(6);
        LocalTime endTime=LocalTime.of(23, 59, 59);
        LocalTime startTime=LocalTime.of(0,0,0);
        LocalDateTime endT=LocalDateTime.of(yesterday, endTime);
        LocalDateTime startT=LocalDateTime.of(yesterday, startTime);
        System.out.println(startT+" "+endT);
        //long start=DateUtil.toMilli(startT.minusDays(7 ));
        //long end=DateUtil.toMilli(endT);
        SearchResponse searchResponse = transportClient.prepareSearch(index)
                .setTypes("goods")
                .setQuery(QueryBuilders.boolQuery()
                        // .must(QueryBuilders.matchPhraseQuery("PolicyCode", "5674504720"))
                        .must(QueryBuilders.rangeQuery("goodsTime").from(new Date())
                                .to(new Date()))).setFrom(0).setSize(10)
                .addSort("goodsTime", SortOrder.ASC).execute()
                .get();
        for (SearchHit searchHit : searchResponse.getHits()) {
            try {
                String id = searchHit.getId();
                System.out.println(searchHit.getSourceAsString());
                JSONObject obj=JSONObject.parseObject(searchHit.getSourceAsString());
                obj.put("_id",id);
                array.add(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void searchCount() throws Exception {
        JSONArray array=new JSONArray();
        LocalDate yesterday=LocalDate.now().minusDays(1);
        LocalTime startTime=LocalTime.of(0,0,0);
        LocalTime endTime=LocalTime.of(23, 59, 59);
        LocalDateTime startT=LocalDateTime.of(LocalDate.now().minusDays(1), startTime);
        LocalDateTime endT=LocalDateTime.of(LocalDate.now(), endTime);
        System.out.println(startT+" "+endT);
        SearchResponse searchResponse = transportClient.prepareSearch(index)
                .setTypes("goods")
                .setQuery(QueryBuilders.boolQuery()
                        // .must(QueryBuilders.matchPhraseQuery("PolicyCode", "5674504720"))
                        .must(QueryBuilders.rangeQuery("goodsTime").from(DateUtil.dateFormat(startT))
                                .to(DateUtil.dateFormat(endT)))).setSize(0)
                .addSort("goodsTime", SortOrder.ASC).execute()
                .get();
        System.out.println(searchResponse);
        System.out.println(searchResponse.getHits().totalHits);
    }

    public void searchMax() throws Exception {
        JSONArray array=new JSONArray();
        LocalDate yesterday=LocalDate.now().minusDays(-2);
        LocalTime startTime=LocalTime.of(0,0,0);
        LocalTime endTime=LocalTime.of(23, 59, 59);
        LocalDateTime startT=LocalDateTime.of(LocalDate.now().minusDays(0), startTime);
        LocalDateTime endT=LocalDateTime.of(LocalDate.now(), endTime);
        System.out.println(startT+" "+endT);
        //聚合处理
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 统计个数
        //AbstractAggregationBuilder valueCountAggregationBuilder = AggregationBuilders.count("count").field("name");
        // 总和, 平均值, 最大值，最小值
        //AbstractAggregationBuilder sumAggregationBuilder = AggregationBuilders.sum("sum").field("score");
        //AbstractAggregationBuilder avgAggregationBuilder = AggregationBuilders.avg("avg").field("score");
        AbstractAggregationBuilder maxAggregationBuilder = AggregationBuilders.max("max").field("goodsTime");
        //AbstractAggregationBuilder minAggregationBuilder = AggregationBuilders.min("min").field("score");
        sourceBuilder.query(QueryBuilders.boolQuery()
                // .must(QueryBuilders.matchPhraseQuery("PolicyCode", "5674504720"))
                .must(QueryBuilders.rangeQuery("goodsTime").from(DateUtil.dateFormat(startT))
                        )).size(0)
                //.aggregation(valueCountAggregationBuilder)
                //.aggregation(sumAggregationBuilder)
                //.aggregation(avgAggregationBuilder)
                .aggregation(maxAggregationBuilder)
                //.aggregation(minAggregationBuilder)
                ;
        try {
            //查询索引对象
            SearchRequest searchRequest = new SearchRequest(index);
            searchRequest.types("goods");
            searchRequest.source(sourceBuilder);
            SearchResponse response = transportClient.search(searchRequest).get();
            System.out.println(response);
            Aggregations aggregations=response.getAggregations();
            Aggregation aggregation=aggregations.get("max");
            System.out.println(aggregation);
            System.out.println(aggregation.getMetaData());
            String str=aggregations.get("max").toString();
            System.out.println(str);
            JSONObject obj=JSONObject.parseObject(str);
            String value=obj.getJSONObject("max").getString("value_as_string");
            long value1=obj.getJSONObject("max").getLongValue("value_as_string");
            System.out.println(value);
            System.out.println(value1);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public int executeDelete(List<Map<String, Object>> list) {
        BulkRequest bulkRequest = new BulkRequest();
        for (Map<String,Object> map : list) {
            String id=StringUtil.valueOf(map.get("_id"));
            DeleteRequest deleteRequest = new DeleteRequest(index, "goods", id);
            bulkRequest.add(deleteRequest);
        }
        transportClient.bulk(bulkRequest);
        return list.size();
    }

    public static TransportClient elasticsearchClient(String name,String ip,int port,JSONObject param) {
        TransportClient transportClient = null;
        System.setProperty("es.set.netty.runtime.available.processors","false");
        logger.info("链接elasticsearch服务...");
        try {
            Settings.Builder builder = Settings.builder();
            builder.put("cluster.name", name);
            if(StringUtil.mapNotNull(param)){
                for(String entry:param.keySet()){
                    builder.put(entry,param.getString(entry));
                }
            }
            /*.put("cluster.name", name) //集群名字
            //.put("client.transport.sniff", true)//增加嗅探机制，找到ES集群
            .put("thread_pool.search.size", Integer.parseInt(poolSize))//增加线程池个数，暂时设为*/
            //.build();
            transportClient = new PreBuiltTransportClient(builder.build());
            transportClient.addTransportAddress(
                    new TransportAddress(InetAddress.getByName(ip), port)
            );
        } catch (UnknownHostException e) {
            logger.error("创建elasticsearch客户端失败");
        }
        logger.info("创建elasticsearch客户端成功");

        return transportClient;
    }

    public static void main(String[] args) throws Exception {
        ESUtil esUtil=new ESUtil("");
        //esUtil.createIndex();
        //esUtil.types();
        //esUtil.mapping();
//        esUtil.addIndexAndType("goods1");
        Map<String,Object> map=new HashMap<>();
        int user=6;
        double price=1.56;
        Date timestamp=new Date();
        String name="name"+user;
        map.put("goodsUser",name);
        //map.put("goodsPrice",price+0.1);
        map.put("goodsTime", DateUtil.dateFormat());
        map.put("goodsName","n1"+user);
        esUtil.searchMax();
        //esUtil.addData("goods",map,"AW7LC27IPQBwhP4-CbZb");
        //esUtil.search(args);
        //esUtil.searchCount();
        //esUtil.searchMax();
        /*Map<String,Object> map1=new HashMap<>();
        map1.put("_id", "AW7QL3-MPQBwhP4-CbZj");
        Map<String,Object> map2=new HashMap<>();
        map2.put("_id",  "AW7LC27IPQBwhP4-CbZb");
        List<Map<String, Object>> list=new ArrayList<>();
        list.add(map1);
        list.add(map2);
        esUtil.executeDelete(list);*/
    }

}