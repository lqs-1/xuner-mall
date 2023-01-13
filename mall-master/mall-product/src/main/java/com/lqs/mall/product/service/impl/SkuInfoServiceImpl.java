package com.lqs.mall.product.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.common.utils.pagination.QueryPage;
import com.lqs.mall.product.dao.*;
import com.lqs.mall.product.entity.*;
import com.lqs.mall.product.service.SkuInfoService;
import com.lqs.mall.product.vo.item.SkuItemSaleAttrVo;
import com.lqs.mall.product.vo.item.SkuItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Autowired
    private SkuImagesDao skuImagesDao;

    @Autowired
    private SpuInfoDescDao spuInfoDescDao;

    @Autowired
    private AttrGroupDao attrGroupDao;

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;


    @Autowired
    private ProductAttrValueDao productAttrValueDao;

    @Autowired
    private SkuSaleAttrValueDao skuSaleAttrValueDao;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    @Transactional(readOnly = true)
    // key=&catelogId=0&brandId=0&min=0&max=0
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new QueryPage<SkuInfoEntity>().getPage(params),
                new LambdaQueryWrapper<SkuInfoEntity>()
                        .eq(!StringUtils.equals((String) params.get("catelogId"), "0") &&
                                        !StringUtils.isEmpty((String) params.get("catelogId")),
                                SkuInfoEntity::getCatalogId, params.get("catelogId"))

                        .eq(!StringUtils.equals((String) params.get("brandId"), "0") &&
                                !StringUtils.isEmpty((String) params.get("brandId")),
                                SkuInfoEntity::getBrandId, params.get("brandId"))

                        .in(!StringUtils.equals((String) params.get("min"), "0") &&
                                !StringUtils.equals((String) params.get("max"), "0") &&
                                Long.parseLong((String) params.get("min")) < Long.parseLong((String) params.get("max")),
                                SkuInfoEntity::getPrice, params.get("min"),params.get("max"))

                        .gt(!StringUtils.equals((String) params.get("min"), "0") &&
                                StringUtils.equals((String) params.get("max"), "0"),
                                SkuInfoEntity::getPrice, params.get("min"))

                        .lt(StringUtils.equals((String) params.get("min"), "0") &&
                                        !StringUtils.equals((String) params.get("max"), "0"),
                                SkuInfoEntity::getPrice, params.get("max"))

                        .and(params.get("key") != null, query1 -> {
                            query1.like(SkuInfoEntity::getSkuDesc, params.get("key"))
                                    .or()
                                    .like(SkuInfoEntity::getSkuName, params.get("key"));
                        })
        );

        return new PageUtils(page);
    }

    // 查询页面商品你详情数据
    @Transactional(readOnly = true)
    @Override
    public SkuItemVo item(Long skuId) {
        SkuItemVo skuItemVo = new SkuItemVo();

        // 异步编排下面thenAcceptAsync的任务都要等这一个完成一个再执行
        CompletableFuture<SkuInfoEntity> skuInfoFuture = CompletableFuture.supplyAsync(() -> {
            // sku基本信息 pms_sku_info
            SkuInfoEntity skuInfo = this.baseMapper.selectById(skuId);
            skuItemVo.setSkuInfo(skuInfo);
            return skuInfo;
        }, threadPoolExecutor);

        CompletableFuture<Void> saleAttrFuture = skuInfoFuture.thenAcceptAsync((skuInfo) -> {
            // spu的销售属性
            // 先获取这个spu对应的所有的sku
            List<SkuInfoEntity> skuInfoList = this.baseMapper.selectList(new LambdaQueryWrapper<SkuInfoEntity>().eq(skuInfo.getSpuId() != null, SkuInfoEntity::getSpuId, skuInfo.getSpuId()));

            List<Long> skuIds = skuInfoList.stream().map(sku -> sku.getSkuId()).collect(Collectors.toList());

            List<SkuItemSaleAttrVo> skuItemSaleAttrVos = skuSaleAttrValueDao.selectSaleAttrsBySkuIds(skuIds);
            skuItemVo.setSkuItemSaleAttrs(skuItemSaleAttrVos);
        }, threadPoolExecutor);


        CompletableFuture<Void> descFuture = skuInfoFuture.thenAcceptAsync((skuInfo) -> {
            // spu的基本信息，介绍 pms_spu_info_desc
            SpuInfoDescEntity spuInfoDesc = spuInfoDescDao.selectById(skuInfo.getSpuId());
            skuItemVo.setSpuInfoDesc(spuInfoDesc);
        }, threadPoolExecutor);


        CompletableFuture<Void> baseAttrFuture = skuInfoFuture.thenAcceptAsync((skuInfo) -> {
            // spu的规格参数信息
            List<SkuItemVo.SpuItemBaseAttrVo> groupAttrs = new ArrayList<>();
            List<AttrGroupEntity> attrGroupList = attrGroupDao.getAttrGroupByCatalogId(skuInfo.getCatalogId()); // 获取当前spu对应的属性分组
            // 遍历分组封装信息
            attrGroupList.stream().forEach(attrGroup -> {
                SkuItemVo.SpuItemBaseAttrVo spuItemBaseAttrVo = new SkuItemVo.SpuItemBaseAttrVo();
                spuItemBaseAttrVo.setGroupName(attrGroup.getAttrGroupName());

                List<SkuItemVo.SpuBaseAttrVo> spuBaseAttrVos = new ArrayList<>();
                SkuItemVo.SpuBaseAttrVo spuBaseAttrVo = new SkuItemVo.SpuBaseAttrVo();

                // 根据属性分组的id查询到属性和属性分组关系的列表
                List<AttrAttrgroupRelationEntity> attrAttrgroupRelationList = attrAttrgroupRelationDao.getRelationByAttrGroupId(attrGroup.getAttrGroupId());

                // 遍历查出对应的属性
                attrAttrgroupRelationList.stream().forEach(attrAttrgroupRelation -> {
                    // 查询商品的对应属性值
                    ProductAttrValueEntity productAttrValue = productAttrValueDao.getSpuAttrValueByAttrId(attrAttrgroupRelation.getAttrId(), skuInfo.getSpuId());
                    spuBaseAttrVo.setAttrName(productAttrValue.getAttrName());
                    spuBaseAttrVo.setAttrValue(productAttrValue.getAttrValue());
                });

                spuBaseAttrVos.add(spuBaseAttrVo);

                spuItemBaseAttrVo.setAttrs(spuBaseAttrVos);
                groupAttrs.add(spuItemBaseAttrVo);

            });

            skuItemVo.setGroupAttrs(groupAttrs);
        }, threadPoolExecutor);

        // 异步编排这个异步和别的没什么关系，所以可以再开一个
        CompletableFuture<Void> skuImagesFuture = CompletableFuture.runAsync(() -> {
            // sku图片信息 pms_sku_images
            List<SkuImagesEntity> skuImages = skuImagesDao.getSkuImagesBySkuId(skuId);
            skuItemVo.setSkuImages(skuImages);
        }, threadPoolExecutor);

        // 等待所有任务都完成
        try {
            CompletableFuture.allOf(saleAttrFuture, descFuture, skuImagesFuture, skuInfoFuture).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        return skuItemVo;
    }

    // 获取某个商品的最新价格数据
    @Override
    public BigDecimal getLatestPrice(Long skuId) {

        SkuInfoEntity skuInfoEntity = this.baseMapper.selectOne(new LambdaQueryWrapper<SkuInfoEntity>().eq(SkuInfoEntity::getSkuId, skuId));

        return skuInfoEntity.getPrice();
    }

}