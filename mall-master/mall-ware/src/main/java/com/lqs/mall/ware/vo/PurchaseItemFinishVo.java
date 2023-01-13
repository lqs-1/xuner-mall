package com.lqs.mall.ware.vo;

import lombok.Data;

/**
 * @author 李奇凇
 * @moduleName PurchaseItemFinishVo
 * @date 2022/9/24 上午10:02
 * @do 采购完成的采购项的收集视图对象
 */


@Data
public class PurchaseItemFinishVo {

    private Long itemId;

    private Integer status;

    private String reason;

}
