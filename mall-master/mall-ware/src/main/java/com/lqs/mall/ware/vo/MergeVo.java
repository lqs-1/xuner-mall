package com.lqs.mall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 李奇凇
 * @date 2022/9/7 下午1:29
 * @do 采购需求合并的试图对象
 */
@Data
public class MergeVo {

    private Long purchaseId;
    private List<Long> items;
}
