package com.lqs.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.order.entity.OrderReturnApplyEntity;

import java.util.Map;

/**
 * 订单退货申请
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:58:45
 */
public interface OrderReturnApplyService extends IService<OrderReturnApplyEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

