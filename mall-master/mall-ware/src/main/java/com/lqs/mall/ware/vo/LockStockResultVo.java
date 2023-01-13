package com.lqs.mall.ware.vo;

import lombok.Data;

/**
 * @author 李奇凇
 * @moduleName LockStockResult
 * @date 2023/1/5 下午6:37
 * @do 库存锁定结果
 */

@Data
public class LockStockResultVo {

    private Long skuId; // 锁哪个商品

    private Integer num; // 锁几个

    private Boolean locked; // 是否锁住


}
