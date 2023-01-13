package com.lqs.mall.ware.dao;

import com.lqs.mall.ware.entity.PurchaseDetailEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 22:00:14
 */
@Mapper
public interface PurchaseDetailDao extends BaseMapper<PurchaseDetailEntity> {

    List<PurchaseDetailEntity> selectListPurchaseNeedByPurchaseId(@Param("purchaseId") Long purchaseId);
}
