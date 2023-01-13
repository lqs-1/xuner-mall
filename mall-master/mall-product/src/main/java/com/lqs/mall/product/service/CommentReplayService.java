package com.lqs.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.product.entity.CommentReplayEntity;

import java.util.Map;

/**
 * 商品评价回复关系
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:59:36
 */
public interface CommentReplayService extends IService<CommentReplayEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

