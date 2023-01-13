package com.lqs.mall.product.vo;

import com.lqs.mall.product.entity.AttrEntity;
import lombok.Data;

/**
 * @author 李奇凇
 * @date 2022/8/12 上午11:14
 * @do 前端传递的页面数据,保存attr使用
 */

@Data
public class AttrVo extends AttrEntity {

    private Long attrGroupId;

}
