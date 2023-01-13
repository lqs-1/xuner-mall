package com.lqs.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.product.entity.SpuInfoEntity;
import com.lqs.mall.product.vo.spu.SpuRespVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:59:36
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SpuRespVo spuRespVo);

    void spuUp(Long spuId);

    SpuInfoEntity findSpuInfoBySkuId(Long skuId);
}

