package com.lqs.mall.search.service;

import com.lqs.mall.common.to.es.SkuEsModelTo;

import java.io.IOException;
import java.util.List;

/**
 * @author 李奇凇
 * @moduleName ElasticSaveService
 * @date 2022/9/28 下午6:11
 * @do elasticsearch保存服务接口
 */


public interface ElasticSaveService {

    void productStatusUp(List<SkuEsModelTo> skuEsModelToList) throws IOException;
}
