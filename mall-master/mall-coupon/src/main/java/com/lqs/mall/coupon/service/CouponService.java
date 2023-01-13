package com.lqs.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.coupon.entity.CouponEntity;

import java.util.Map;

/**
 * 优惠券信息
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:55:55
 */
public interface CouponService extends IService<CouponEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

