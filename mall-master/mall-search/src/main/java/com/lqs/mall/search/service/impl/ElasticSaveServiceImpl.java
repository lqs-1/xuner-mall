package com.lqs.mall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.lqs.mall.common.constant.Constant;
import com.lqs.mall.common.to.es.SkuEsModelTo;
import com.lqs.mall.search.config.MallElasticSearchConfig;
import com.lqs.mall.search.service.ElasticSaveService;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author 李奇凇
 * @moduleName ElasticSaveServiceImpl
 * @date 2022/9/28 下午6:13
 * @do elasticsearch保存服务接口的实现类
 */
@Service("ElasticSaveService")
public class ElasticSaveServiceImpl implements ElasticSaveService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    // 将sku信息保存到es
    @Override
    public void productStatusUp(List<SkuEsModelTo> skuEsModelToList) throws IOException {
        // 建立索引
        IndexRequest productIndexRequest = new IndexRequest(Constant.PRODUCT_INDEX);
        // 保存数据
        BulkRequest bulkRequest = new BulkRequest();
        for (SkuEsModelTo esModelTo : skuEsModelToList) {

            IndexRequest indexRequest = new IndexRequest(Constant.PRODUCT_INDEX);

            String resource = JSON.toJSONString(esModelTo);
            indexRequest.id(esModelTo.getSkuId().toString()).source(resource, XContentType.JSON);

            bulkRequest.add(indexRequest);
        }

        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, MallElasticSearchConfig.COMMON_OPTIONS);

        // 如果出错了 TODO 后续添加

    }
}
