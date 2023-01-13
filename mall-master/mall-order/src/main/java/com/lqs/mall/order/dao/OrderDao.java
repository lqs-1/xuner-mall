package com.lqs.mall.order.dao;

import com.lqs.mall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:58:45
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
