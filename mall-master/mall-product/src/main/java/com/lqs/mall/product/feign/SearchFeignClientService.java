package com.lqs.mall.product.feign;

import com.lqs.mall.common.to.es.SkuEsModelTo;
import com.lqs.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author 李奇凇
 * @moduleName SearchFeignClientService
 * @date 2022/9/28 下午6:32
 * @do 调用search服务的api
 */

@FeignClient("mall-search")
public interface SearchFeignClientService {

    @PostMapping("search/save/productStatusUp")
    R productStatusUp(@RequestBody List<SkuEsModelTo> skuEsModelToList);


}
