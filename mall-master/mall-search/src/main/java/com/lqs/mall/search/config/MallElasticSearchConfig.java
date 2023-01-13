package com.lqs.mall.search.config;

import com.lqs.mall.common.utils.R;
import org.apache.http.HttpHost;
import org.elasticsearch.client.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 李奇凇
 * @moduleName MallElasticSearchConfig
 * @date 2022/9/28 下午1:42
 * @do elasticsearch客户端配置
 */

@Configuration
public class MallElasticSearchConfig {


    // 注入elasticsearch地址信息
    @Value("${mall.elasticsearch.hostname}")
    private String hostName;

    @Value("${mall.elasticsearch.port}")
    private String port;

    @Value("${mall.elasticsearch.scheme}")
    private String scheme;


    public static final RequestOptions COMMON_OPTIONS;
    static {
        // 通用设置项，根据默认规则添加的
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();

//        builder.addHeader("Authorization", "Bearer" + TOKEN);
//        builder.setHttpAsyncResponseConsumerFactory(
//                new HttpAsyncResponseConsumerFactory
//                        .HeapBufferedResponseConsumerFactory(30*1024*1024*1024)
//        );
        COMMON_OPTIONS = builder.build();
    }


    // 用了立马关闭客户端，注入容器
    @Bean(destroyMethod = "close")
    public RestHighLevelClient elasticSearchRestClient(){

        // String hostname, int port, String scheme
        RestClientBuilder builder = RestClient.builder(
                new HttpHost(
                        hostName,
                        Integer.parseInt(port),
                        scheme)
                );

        RestHighLevelClient elasticSearchClient = new RestHighLevelClient(builder);

        return elasticSearchClient;
    }
}
