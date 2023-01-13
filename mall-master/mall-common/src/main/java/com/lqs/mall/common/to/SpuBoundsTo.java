package com.lqs.mall.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 李奇凇
 * @date 2022/8/31 下午5:17
 * @do 添加商品的时候的积分的传递对象
 */
@Data
public class SpuBoundsTo {
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
