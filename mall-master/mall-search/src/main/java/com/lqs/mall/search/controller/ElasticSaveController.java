package com.lqs.mall.search.controller;

import com.lqs.mall.common.constant.REnum;
import com.lqs.mall.common.to.es.SkuEsModelTo;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.search.service.ElasticSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 李奇凇
 * @moduleName ElasticSaveController
 * @date 2022/9/28 下午6:08
 * @do elasticsearch保存
 */

@RestController
@RequestMapping("search/save")
public class ElasticSaveController {

    @Autowired
    private ElasticSaveService elasticSaveService;

    /**
     * 商品状态保存到es
     * @param skuEsModelToList
     * @return
     */
    @PostMapping("productStatusUp")
    public R productStatusUp(@RequestBody List<SkuEsModelTo> skuEsModelToList){
        try{
            // 程序代码
            elasticSaveService.productStatusUp(skuEsModelToList);

            return R.ok(REnum.UP_PRODUCT_TO_ELASTICSEARCH_SUCCESS.getStatusCode(),
                    REnum.UP_PRODUCT_TO_ELASTICSEARCH_SUCCESS.getStatusMsg());
        }catch(Exception e){
            e.printStackTrace();
            // 程序代码

            return R.error(REnum.UP_PRODUCT_TO_ELASTICSEARCH_FAIL.getStatusCode(),
                    REnum.UP_PRODUCT_TO_ELASTICSEARCH_FAIL.getStatusMsg());

        }finally{
            // 程序代码

        }
    }

}
