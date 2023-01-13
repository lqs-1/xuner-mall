package com.lqs.mall.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 李奇凇
 * @date 2022/8/31 下午5:35
 * @do 保存商品的时候传递会员价格的对象
 */
@Data
public class MemberPrice {

    private Long id;
    private String name;
    private BigDecimal price;


}
