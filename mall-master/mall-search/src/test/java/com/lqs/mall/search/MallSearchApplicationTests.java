package com.lqs.mall.search;

import com.alibaba.fastjson.JSON;
import com.lqs.mall.search.config.MallElasticSearchConfig;
import lombok.Data;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MallSearchApplication.class)
class MallSearchApplicationTests {

    @Autowired
    private RestHighLevelClient client;

    @Data
    class User{
        private String userName;
        private String gender;
        private Integer age;
    }

    // 给es存储数据,保存更新而和一
    @Test
    public void index() throws IOException {
        // 创建新增请求
        IndexRequest indexRequest = new IndexRequest("java");

        // 第一种方式，直接写键值对
        // indexRequest.id("1").source("userName", "liqisong", "age", 12);

        // 第二种方式，对象转json,多用常用
        User user = new User();
        user.setUserName("liqisong");
        user.setAge(12);
        user.setGender("F");
        indexRequest.id("1").source(JSON.toJSONString(user), XContentType.JSON);

        // 执行添加操作
        IndexResponse index = client.index(indexRequest, MallElasticSearchConfig.COMMON_OPTIONS);

        System.out.println(index);

    }


    // 复杂的检索操作
    @Test
    public void search() throws IOException {
        // 创建检索请求
        SearchRequest searchRequest = new SearchRequest("bank");

        // 创建检索条件对象 DSL
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 构造检索条件
        searchSourceBuilder.query(QueryBuilders.matchQuery("address", "mill"));
        // 按照年龄聚合
        searchSourceBuilder.aggregation(AggregationBuilders.terms("ageAgg").field("age"));
        // 求平均值
        searchSourceBuilder.aggregation(AggregationBuilders.avg("balanceAvg").field("balance"));

//        searchSourceBuilder.from();
//        searchSourceBuilder.size(10);

        searchRequest.source(searchSourceBuilder);


        // 执行检索
        SearchResponse search = client.search(searchRequest, MallElasticSearchConfig.COMMON_OPTIONS);


        // 分析结果

        System.out.println(searchSourceBuilder.toString());

        System.out.println(search.toString());

        // 获取命中数据
        SearchHit[] hits = search.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit);
        }

        // 获取聚合数据
        Aggregations aggregations = search.getAggregations();
        // 第一个聚合
        Terms ageAgg = aggregations.get("ageAgg");
        for (Terms.Bucket bucket : ageAgg.getBuckets()) {

            System.out.println(ageAgg.getName() + ":" + bucket.getKeyAsString() + ":" + bucket.getDocCount());
        }

        // 第二个聚合
        Avg balanceAvg = aggregations.get("balanceAvg");
        System.out.println(balanceAvg.getName() + ":" + balanceAvg.getValue());
    }

}
