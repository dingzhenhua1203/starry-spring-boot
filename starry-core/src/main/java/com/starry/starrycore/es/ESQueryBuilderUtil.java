package com.starry.starrycore.es;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * 多例模式 但是需要从最上层开始全部都设为多例， 否则单例Controller中注入多例service，实际还是单例
 * 或者使用sping工具类获取bean
 * 这里在网上看到大部分都是直接@Scope(“prototype”)，这里测试是不生效的，再加上proxyMode才行
 */
@Component
// @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ESQueryBuilderUtil {

    private SearchSourceBuilder sourceBuilder;
    private BoolQueryBuilder boolQueryBuilder;

    public ESQueryBuilderUtil() {
        sourceBuilder = new SearchSourceBuilder();
        boolQueryBuilder = QueryBuilders.boolQuery();
    }

    public ESQueryBuilderUtil matchQuery(String key, String value) throws IOException {

        /**
         * QueryBuilders.termQuery(“key”, obj) 完全匹配，输入的查询内容是什么，就会按照什么去查询，并不会解析查询内容，对它分词。
         * QueryBuilders.termsQuery(“key”, obj1, obj2…) 一次匹配多个值
         * QueryBuilders. matchQuery(“key”, Obj) 单个匹配,match查询，会将搜索词分词，再与目标查询字段进行匹配，若分词中的任意一个词与目标字段匹配上，则可查询到。
         * QueryBuilders. multiMatchQuery(“text”, “field1”, “field2”…); 匹配多个字段, field有通配符忒行
         */
        if (!value.isEmpty()) {
            boolQueryBuilder.must(QueryBuilders.matchQuery(key, value));
        }
        // 模糊查询
        // boolQueryBuilder.filter(QueryBuilders.wildcardQuery("itemDesc", "*手机*"));

        return this;
    }

    public ESQueryBuilderUtil termQuery(String key, String value) throws IOException {
        if (!value.isEmpty()) {
            boolQueryBuilder.filter(QueryBuilders.termQuery(key, value));
        }
        return this;
    }

    /**
     * 范围查询 from:相当于闭区间; gt:相当于开区间(>) gte:相当于闭区间 (>=) lt:开区间(<) lte:闭区间 (<=)
     *
     * @param key key
     * @param min min
     * @param max max
     * @return
     * @throws IOException
     */
    public ESQueryBuilderUtil rangeQuery(String key, String min, String max) throws IOException {
        // 范围查询 from:相当于闭区间; gt:相当于开区间(>) gte:相当于闭区间 (>=) lt:开区间(<) lte:闭区间 (<=)
        // boolQueryBuilder.filter(QueryBuilders.rangeQuery("num").gte(1));
        if (min != null && max != null) {
            boolQueryBuilder.filter(QueryBuilders.rangeQuery(key).from(min).to(max));
        }
        return this;
    }

    public ESQueryBuilderUtil rangeQuery(String key, String value, ESEnums.ESRangeTypeEnum type) throws IOException {
        // 范围查询 from:相当于闭区间; gt:相当于开区间(>) gte:相当于闭区间 (>=) lt:开区间(<) lte:闭区间 (<=)
        // boolQueryBuilder.filter(QueryBuilders.rangeQuery("num").gte(1));
        switch (type) {
            case gt:
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("num").gt(1));
                break;
            case gte:
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("num").gte(1));
                break;
            case lt:
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("num").lt(1));
                break;
            case lte:
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("num").lte(1));
                break;
        }

        return this;
    }

    public ESQueryBuilderUtil aggregationQuery(String aggrekey) throws IOException {
        // 聚合查询 搜索中的商品分类
        if (aggrekey != null) {
            TermsAggregationBuilder aggrBuilder = AggregationBuilders.terms(aggrekey).field(aggrekey);
            sourceBuilder.aggregation(aggrBuilder);
        }
        return this;
    }

    public ESQueryBuilderUtil sorting(String keyName, SortOrder sortEnum) {
        if (!StringUtils.isEmpty(keyName)) {
            sourceBuilder.sort(keyName, sortEnum);
        }
        return this;
    }

    public ESQueryBuilderUtil paging(Integer pageIndex, Integer pageSize) {
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        sourceBuilder.from((pageIndex - 1) * pageSize);
        sourceBuilder.size(pageSize);
        return this;
    }

    public ESQueryBuilderUtil useHighLight(String key) {
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field(key).preTags("<font style='color:red'>").postTags("</font>");
        sourceBuilder.highlighter(highlightBuilder);
        return this;
    }

    public SearchSourceBuilder execute() {
        sourceBuilder.query(boolQueryBuilder);
        return this.sourceBuilder;
    }
}
