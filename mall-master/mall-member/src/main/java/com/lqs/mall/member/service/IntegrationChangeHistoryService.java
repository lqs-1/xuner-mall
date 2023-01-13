package com.lqs.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.member.entity.IntegrationChangeHistoryEntity;

import java.util.Map;

/**
 * 积分变化历史记录
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:58:01
 */
public interface IntegrationChangeHistoryService extends IService<IntegrationChangeHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

