package com.lqs.mall.common.to;

import lombok.Data;

/**
 * @author 李奇凇
 * @moduleName SaleNumTo
 * @date 2023/1/14 下午1:43
 * @do 销售数量得To
 */

@Data
public class SaleNumTo {

    private Long skuId; // 商品Id

    private Long num; // 该商品得销售数量

}
