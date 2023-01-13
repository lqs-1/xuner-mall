package com.lqs.mall.common.to.mq.ware;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 李奇凇
 * @moduleName WareStockLockTo
 * @date 2023/1/11 下午2:00
 * @do 库存消息对象
 */

@Data
public class WareStockLockTo implements Serializable {

    private Long wareOrderTaskId;

    private StockDetailTo stockDetail;


}
