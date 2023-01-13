package com.lqs.mall.ware.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.common.utils.pagination.QueryPage;
import com.lqs.mall.ware.dao.PurchaseDetailDao;
import com.lqs.mall.ware.entity.PurchaseDetailEntity;
import com.lqs.mall.ware.service.PurchaseDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    // key=ee&status=1&wareId=1
    @Transactional(readOnly = true)
    public PageUtils queryPage(Map<String, Object> params) {
//        System.out.println(params);
        IPage<PurchaseDetailEntity> page = this.page(
                new QueryPage<PurchaseDetailEntity>().getPage(params),
                new LambdaQueryWrapper<PurchaseDetailEntity>()
                        .eq(!StringUtils.isEmpty((String) params.get("status")), PurchaseDetailEntity::getStatus, params.get("status"))
                        .eq(!StringUtils.isEmpty((String) params.get("wareId")), PurchaseDetailEntity::getWareId, params.get("wareId"))
                        .and(!StringUtils.isEmpty((String) params.get("key")), query1 -> {
                            query1.eq(PurchaseDetailEntity::getId, params.get("key"));
                        })
        );

        return new PageUtils(page);
    }

}