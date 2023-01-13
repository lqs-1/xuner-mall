package com.lqs.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.order.entity.MqMessageEntity;

import java.util.Map;

/**
 * 
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:58:45
 */
public interface MqMessageService extends IService<MqMessageEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

