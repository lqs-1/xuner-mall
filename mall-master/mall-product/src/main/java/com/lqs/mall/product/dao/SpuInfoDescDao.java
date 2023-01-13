package com.lqs.mall.product.dao;

import com.lqs.mall.product.entity.SpuInfoDescEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * spu信息介绍
 * 
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:59:36
 */
@Mapper
public interface SpuInfoDescDao extends BaseMapper<SpuInfoDescEntity> {

    void saveSpuInfoDesc(SpuInfoDescEntity spuInfoDescEntity);
}
