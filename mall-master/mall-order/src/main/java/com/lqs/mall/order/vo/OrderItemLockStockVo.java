package com.lqs.mall.order.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 李奇凇
 * @moduleName OrderItemLockStockVo
 * @date 2023/1/6 上午10:51
 * @do 锁定库存使用得对象
 */


@Data
public class OrderItemLockStockVo implements Serializable {


    private Long skuId;

    private Integer count;

}
