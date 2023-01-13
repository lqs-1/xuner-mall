package com.lqs.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.common.utils.pagination.QueryPage;
import com.lqs.mall.product.dao.ProductAttrValueDao;
import com.lqs.mall.product.entity.ProductAttrValueEntity;
import com.lqs.mall.product.service.ProductAttrValueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity> implements ProductAttrValueService {

    @Transactional(readOnly = true)
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductAttrValueEntity> page = this.page(
                new QueryPage<ProductAttrValueEntity>().getPage(params),
                new QueryWrapper<ProductAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    // 查询出对应的spu的基本属性（规格参数）
    @Transactional(readOnly = true)
    @Override
    public List<ProductAttrValueEntity> baseAttrListforspu(Long spuId) {
        List<ProductAttrValueEntity> productAttrValueEntityList = this.baseMapper.selectList(new LambdaQueryWrapper<ProductAttrValueEntity>()
                .eq(spuId != null, ProductAttrValueEntity::getSpuId, spuId));
        return productAttrValueEntityList;
    }

    // 更新spu的规格参数
    @Transactional(readOnly = false)
    @Override
    public void updateSpuAttr(Long spuId, List<ProductAttrValueEntity> productAttrValueEntityList) {
        // 删除这个spuId之前对应的所有属性
        this.baseMapper.delete(new LambdaQueryWrapper<ProductAttrValueEntity>()
                .eq(spuId != null, ProductAttrValueEntity::getSpuId, spuId));

        // 重新插入新的数据
        List<ProductAttrValueEntity> attrValueEntityList = productAttrValueEntityList.stream().map(item -> {
            item.setSpuId(spuId);
            return item;
        }).collect(Collectors.toList());

        this.saveBatch(attrValueEntityList);
    }

}