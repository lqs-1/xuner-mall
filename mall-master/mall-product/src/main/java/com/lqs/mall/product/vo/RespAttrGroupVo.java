package com.lqs.mall.product.vo;

import com.lqs.mall.product.entity.AttrEntity;
import com.lqs.mall.product.entity.AttrGroupEntity;
import lombok.Data;

import java.util.List;

/**
 * @author 李奇凇
 * @date 2022/8/30 下午5:29
 * @do 发布商品的时候使用到的试图渲染数据(基本属性)
 */

@Data
public class RespAttrGroupVo extends AttrGroupEntity {

    private List<AttrEntity> attrs;

}
