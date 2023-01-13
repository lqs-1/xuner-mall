package com.lqs.mall.order.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 李奇凇
 * @moduleName WareSkuLockVo
 * @date 2023/1/5 下午6:29
 * @do 锁库存得vo
 */

@Data
public class WareSkuLockVo implements Serializable {

    private String orderSn; // 订单号

    private List<OrderItemLockStockVo> lockSkus; // 要锁住得所有库存信息

}
