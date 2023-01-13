package com.lqs.mall.common.to.mq.ware;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 李奇凇
 * @moduleName StockDetailTo
 * @date 2023/1/11 下午2:13
 * @do 锁定库存详情mq对象
 */

@Data
public class StockDetailTo implements Serializable {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * sku_id
     */
    private Long skuId;
    /**
     * sku_name
     */
    private String skuName;
    /**
     * 购买个数
     */
    private Integer skuNum;
    /**
     * 工作单id
     */
    private Long taskId;
    /**
     * 仓库id
     */
    private Long wareId;
    /**
     * 1-已锁定  2-已解锁  3-扣减
     */
    private Integer lockStatus;

}
