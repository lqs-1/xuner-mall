package com.lqs.mall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.common.utils.pagination.QueryPage;
import com.lqs.mall.ware.dao.WareInfoDao;
import com.lqs.mall.ware.entity.WareInfoEntity;
import com.lqs.mall.ware.service.WareInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {

    @Override
    @Transactional(readOnly = true)
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareInfoEntity> page = this.page(
                new QueryPage<WareInfoEntity>().getPage(params),
                new LambdaQueryWrapper<WareInfoEntity>()
                        .like(params.get("key") != null, WareInfoEntity::getAddress, params.get("key"))
                        .or()
                        .like(params.get("key") != null, WareInfoEntity::getName, params.get("key"))
        );

        return new PageUtils(page);
    }

}