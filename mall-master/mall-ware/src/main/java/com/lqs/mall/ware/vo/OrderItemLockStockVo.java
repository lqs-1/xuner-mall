package com.lqs.mall.ware.vo;

import lombok.Data;

/**
 * @author 李奇凇
 * @moduleName OrderItemLockStockVo
 * @date 2023/1/6 上午10:51
 * @do 锁定库存使用得对象
 */


@Data
public class OrderItemLockStockVo {


    private Long skuId;

    private Integer count;

}
