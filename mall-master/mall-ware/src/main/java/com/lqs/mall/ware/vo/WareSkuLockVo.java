package com.lqs.mall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 李奇凇
 * @moduleName WareSkuLockVo
 * @date 2023/1/5 下午6:29
 * @do 锁库存得vo
 */

@Data
public class WareSkuLockVo {

    private String orderSn; // 订单号

    private List<OrderItemLockStockVo> lockSkus; // 要锁住得所有库存信息

}
