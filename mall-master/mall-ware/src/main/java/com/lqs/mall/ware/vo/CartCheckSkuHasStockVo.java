package com.lqs.mall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 李奇凇
 * @moduleName CartCheckSkuHasStockVo
 * @date 2023/1/5 下午7:25
 * @do 选中商品是否有库存
 */

@Data
public class CartCheckSkuHasStockVo {


    private Long skuId;

    private List<Long> wareIds; // 在那些仓库有库存

    private Integer lockNum; // 锁库存得时候锁几件

    private Integer skuStockNum;

}
