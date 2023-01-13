package com.lqs.mall.product.vo;

import com.lqs.mall.product.entity.AttrEntity;
import lombok.Data;

/**
 * @author 李奇凇
 * @date 2022/8/12 下午10:33
 * @do 前端传递的页面数据,回显更新的
 */

@Data
public class RespAttrVo extends AttrEntity {

    private Long[] catelogIds;
    private Long attrGroupId;

}
