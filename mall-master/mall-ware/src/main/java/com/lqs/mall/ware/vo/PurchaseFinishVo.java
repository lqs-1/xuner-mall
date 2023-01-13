package com.lqs.mall.ware.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 李奇凇
 * @moduleName PurchaseFinishVo
 * @date 2022/9/24 上午9:59
 * @do 采购完成收集数据视图对象
 */

@Data
public class PurchaseFinishVo {

    @NotNull(message = "修改必须指定采购单id")
    private Long id; // 采购单id

    private List<PurchaseItemFinishVo> items;

}
