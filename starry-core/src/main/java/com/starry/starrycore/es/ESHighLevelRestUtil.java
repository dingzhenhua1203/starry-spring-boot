package com.starry.starrycore.es;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ESHighLevelRestUtil {

    /**
     * 静态属性无法注入，不能用static修饰，spring只能注入实例对象。配合@PostConstruct
     */
    @Autowired
    private RestHighLevelClient client;

    private static ESHighLevelRestUtil esHighLevelRestUtil;

    /**
     * 引入javax.annotation；执行顺序 Constructor(构造方法) -> @Autowired(依赖注入) -> @PostConstruct(注释的方法)
     * 注：@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器执行一次。PostConstruct在构造函数之后执行,init()方法之前执行。
     */
    @PostConstruct
    public void init() {
        esHighLevelRestUtil = this;
        esHighLevelRestUtil.client = this.client;
    }


    public static String indexCreate(String index, String jsonData) throws Exception {

        /**
         * 一般我们初学时会把这些与数据库进行对照方便理解
         *
         * Index->Database
         * Type->Table （最新版本已经不使用Type了，所以很多人会奇怪为什么去掉了？ES并非和数据库是相同的，所以不要完全按数据库的方式来看ES）
         * Document->Row
         */
        IndexRequest indexRequest = new IndexRequest(index).id("");
        indexRequest.source(jsonData, XContentType.JSON);
        indexRequest.timeout("1s");
        IndexResponse indexResponse = esHighLevelRestUtil.client.index(indexRequest,
                RequestOptions.DEFAULT);
        System.out.println(indexResponse.getResult().toString());
        return indexResponse.getResult().toString();
    }

    public static String indexCreate(String index, Map<String, Object> properties) throws Exception {
        String jsonData = JSON.toJSONString(properties);
        return indexCreate(index, jsonData);
    }

    public static String indexCreate(String index, Object data) throws Exception {
        String jsonData = JSON.toJSONString(data);
        return indexCreate(index, jsonData);
    }

    /**
     * 批量插入
     *
     * @param index
     * @param contents
     * @return
     * @throws Exception
     */
    public static <T> Boolean batchIndexCreate(String index, List<T> contents) throws Exception {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("2m");
        for (int i = 0; i < contents.size(); i++) {
            bulkRequest.add(
                    new IndexRequest(index)
                            .source(JSON.toJSONString(contents.get(i)), XContentType.JSON)
            );
        }
        System.out.println(esHighLevelRestUtil.client.toString());
        BulkResponse bulk = esHighLevelRestUtil.client.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulk.hasFailures();
    }

    public static boolean indexDelete(String index) throws IOException {
        DeleteIndexRequest deleteRequest = new DeleteIndexRequest(index);
        AcknowledgedResponse resp = esHighLevelRestUtil.client.indices().delete(deleteRequest, RequestOptions.DEFAULT);
        return resp.isAcknowledged();
    }

    /**
     * 查询所有matchAllQuery
     *
     * @throws IOException
     */
    void search(String index) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        searchResponse.getHits().forEach(res -> {
            // log.info(res.toString());
        });

    }

    /**
     * 精确查询termQuery
     *
     * @throws IOException
     */
    void termQuery(String index, String key, String value) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // termQuery有两种写法
        // sourceBuilder.query(QueryBuilders.termQuery(key, value));
        TermQueryBuilder termQuery = new TermQueryBuilder(key, value);
        sourceBuilder.query(termQuery);
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        searchResponse.getHits().forEach(res -> {
            // log.info(res.toString());
        });
    }

    /**
     * 模糊查询matchQuery
     *
     * @throws IOException
     */
    void matchQuery(String index, String key, String value) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchQuery(key, value));
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        searchResponse.getHits().forEach(res -> {
            // log.info(res.toString());
        });
    }

    /**
     * 模糊查询boolQuery
     *
     * @throws IOException
     */
    public static Map boolQuery(String index, String key, String value, String aggrkey) throws IOException {
        Map result = new HashMap();

        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        /**
         * QueryBuilders.termQuery(“key”, obj) 完全匹配，输入的查询内容是什么，就会按照什么去查询，并不会解析查询内容，对它分词。
         * QueryBuilders.termsQuery(“key”, obj1, obj2…) 一次匹配多个值
         * QueryBuilders. matchQuery(“key”, Obj) 单个匹配,match查询，会将搜索词分词，再与目标查询字段进行匹配，若分词中的任意一个词与目标字段匹配上，则可查询到。
         * QueryBuilders. multiMatchQuery(“text”, “field1”, “field2”…); 匹配多个字段, field有通配符忒行
         */
        boolQueryBuilder.must(QueryBuilders.matchQuery(key, value));

        // 模糊查询
        // boolQueryBuilder.filter(QueryBuilders.wildcardQuery("itemDesc", "*手机*"));
        // 范围查询 from:相当于闭区间; gt:相当于开区间(>) gte:相当于闭区间 (>=) lt:开区间(<) lte:闭区间 (<=)
        // boolQueryBuilder.filter(QueryBuilders.rangeQuery("itemPrice").from(4500).to(8899));
        // boolQueryBuilder.filter(QueryBuilders.termQuery("mobile", ""));
        // boolQueryBuilder.filter(QueryBuilders.rangeQuery("num").gte(1));
        sourceBuilder.query(boolQueryBuilder);
        searchRequest.source(sourceBuilder);

        // 聚合查询 搜索中的商品分类
        if (aggrkey != null) {
            TermsAggregationBuilder aggrBuilder = AggregationBuilders.terms(aggrkey).field(aggrkey);
            sourceBuilder.aggregation(aggrBuilder);
        }

        SearchResponse searchResponse = esHighLevelRestUtil.client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        long totals = hits.getTotalHits().value;
        List<Map<String, Object>> hitList = new ArrayList<>();
        hits.forEach(res -> {
            System.out.println(res.getSourceAsString());
            hitList.add((res.getSourceAsMap()));
        });

        result.put("totals", totals);
        result.put("rows", hitList);

        // 获取聚合查询结果
        Terms terms = (Terms) searchResponse.getAggregations().getAsMap().get(aggrkey);
        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        List<String> categoryList = buckets.stream().map(x -> x.getKeyAsString()).collect(Collectors.toList());
        result.put("categoryList", categoryList);
        return result;
    }

    /**
     * 模糊查询boolQuery
     *
     * @throws IOException
     */
    public static Map boolQuery(String index, SearchSourceBuilder sourceBuilder, String aggrekey) throws IOException {
        Map result = new HashMap();

        SearchRequest searchRequest = new SearchRequest(index);

        // 聚合查询 搜索中的商品分类
        if (aggrekey != null) {
            TermsAggregationBuilder aggrBuilder = AggregationBuilders.terms(aggrekey).field(aggrekey);
            sourceBuilder.aggregation(aggrBuilder);
        }

        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = esHighLevelRestUtil.client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        long totals = hits.getTotalHits().value;
        List<Map<String, Object>> hitList = new ArrayList<>();
        hits.forEach(res -> {
            System.out.println(res.getSourceAsString());
            Map<String, Object> item = res.getSourceAsMap();

            // 处理要高亮的内容，替换到item中
            Map<String, HighlightField> highlightFields = res.getHighlightFields();
            highlightFields.forEach((key, value) -> {
                if (item.containsKey(key)) {
                    Text[] fragments = value.fragments();
                    item.put(key, fragments[0].toString());
                }
            });
            hitList.add((res.getSourceAsMap()));
        });

        result.put("totals", totals);
        result.put("rows", hitList);

        // 获取聚合查询结果
        if (aggrekey != null) {
            Terms terms = (Terms) searchResponse.getAggregations().getAsMap().get(aggrekey);
            List<? extends Terms.Bucket> buckets = terms.getBuckets();
            List<String> categoryList = buckets.stream().map(x -> x.getKeyAsString()).collect(Collectors.toList());
            result.put("categoryList", categoryList);
        }
        return result;
    }


    void countQuery(String index, String key, String value) {
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        boolBuilder.filter(QueryBuilders.rangeQuery("num").gte(1));
        sourceBuilder.query(boolBuilder);

        CountRequest countRequest = new CountRequest(index);
        countRequest.query(boolBuilder);
        CountResponse count = null;
        try {
            count = client.count(countRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            // logger.error("[ElasticSearchService] search error!", e);
        }
    }

    /**
     * 高亮查询
     *
     * @throws IOException
     */
    void highlight() throws IOException {
        SearchRequest searchRequest = new SearchRequest("high-index01");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.highlighter(highlightBuilder());
        searchSourceBuilder.query(new MatchQueryBuilder("title", "你好"));

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        printHits(searchResponse);
    }


    /**
     * 词频统计aggregate
     */
    public void aggregate() throws IOException {

    }


    /**
     * 词频统计term vectors
     */
    public void term_vectors() throws IOException {

    }

    /* 私有方法*/
    private HighlightBuilder highlightBuilder() {
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        HighlightBuilder.Field highlightTitle =
                new HighlightBuilder.Field("title");
        highlightBuilder.field(highlightTitle);
        highlightBuilder.preTags("<font size=\"3\" color=\"red\">");
        highlightBuilder.postTags("</font>");
        return highlightBuilder;
    }

    private void printHits(SearchResponse searchResponse) {
        SearchHits hits = searchResponse.getHits();
        for (SearchHit hit : hits.getHits()) {
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField highlight = highlightFields.get("title");
            //Text[] fragments = highlight.fragments();
            //String fragmentString = fragments[0].string();
            //log.info(fragmentString);
        }
    }
}
