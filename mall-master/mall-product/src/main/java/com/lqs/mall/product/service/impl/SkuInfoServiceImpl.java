package com.lqs.mall.product.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqs.mall.common.to.SaleNumTo;
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

    // ?????????????????????????????????
    @Transactional(readOnly = true)
    @Override
    public SkuItemVo item(Long skuId) {
        SkuItemVo skuItemVo = new SkuItemVo();

        // ??????????????????thenAcceptAsync????????????????????????????????????????????????
        CompletableFuture<SkuInfoEntity> skuInfoFuture = CompletableFuture.supplyAsync(() -> {
            // sku???????????? pms_sku_info
            SkuInfoEntity skuInfo = this.baseMapper.selectById(skuId);
            skuItemVo.setSkuInfo(skuInfo);
            return skuInfo;
        }, threadPoolExecutor);

        CompletableFuture<Void> saleAttrFuture = skuInfoFuture.thenAcceptAsync((skuInfo) -> {
            // spu???????????????
            // ???????????????spu??????????????????sku
            List<SkuInfoEntity> skuInfoList = this.baseMapper.selectList(new LambdaQueryWrapper<SkuInfoEntity>().eq(skuInfo.getSpuId() != null, SkuInfoEntity::getSpuId, skuInfo.getSpuId()));

            List<Long> skuIds = skuInfoList.stream().map(sku -> sku.getSkuId()).collect(Collectors.toList());

            List<SkuItemSaleAttrVo> skuItemSaleAttrVos = skuSaleAttrValueDao.selectSaleAttrsBySkuIds(skuIds);
            skuItemVo.setSkuItemSaleAttrs(skuItemSaleAttrVos);
        }, threadPoolExecutor);


        CompletableFuture<Void> descFuture = skuInfoFuture.thenAcceptAsync((skuInfo) -> {
            // spu???????????????????????? pms_spu_info_desc
            SpuInfoDescEntity spuInfoDesc = spuInfoDescDao.selectById(skuInfo.getSpuId());
            skuItemVo.setSpuInfoDesc(spuInfoDesc);
        }, threadPoolExecutor);


        CompletableFuture<Void> baseAttrFuture = skuInfoFuture.thenAcceptAsync((skuInfo) -> {
            // spu?????????????????????
            List<SkuItemVo.SpuItemBaseAttrVo> groupAttrs = new ArrayList<>();
            List<AttrGroupEntity> attrGroupList = attrGroupDao.getAttrGroupByCatalogId(skuInfo.getCatalogId()); // ????????????spu?????????????????????
            // ????????????????????????
            attrGroupList.stream().forEach(attrGroup -> {
                SkuItemVo.SpuItemBaseAttrVo spuItemBaseAttrVo = new SkuItemVo.SpuItemBaseAttrVo();
                spuItemBaseAttrVo.setGroupName(attrGroup.getAttrGroupName());

                List<SkuItemVo.SpuBaseAttrVo> spuBaseAttrVos = new ArrayList<>();
                SkuItemVo.SpuBaseAttrVo spuBaseAttrVo = new SkuItemVo.SpuBaseAttrVo();

                // ?????????????????????id?????????????????????????????????????????????
                List<AttrAttrgroupRelationEntity> attrAttrgroupRelationList = attrAttrgroupRelationDao.getRelationByAttrGroupId(attrGroup.getAttrGroupId());

                // ???????????????????????????
                attrAttrgroupRelationList.stream().forEach(attrAttrgroupRelation -> {
                    // ??????????????????????????????
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

        // ???????????????????????????????????????????????????????????????????????????
        CompletableFuture<Void> skuImagesFuture = CompletableFuture.runAsync(() -> {
            // sku???????????? pms_sku_images
            List<SkuImagesEntity> skuImages = skuImagesDao.getSkuImagesBySkuId(skuId);
            skuItemVo.setSkuImages(skuImages);
        }, threadPoolExecutor);

        // ???????????????????????????
        try {
            CompletableFuture.allOf(saleAttrFuture, descFuture, skuImagesFuture, skuInfoFuture).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        return skuItemVo;
    }

    // ???????????????????????????????????????
    @Override
    public BigDecimal getLatestPrice(Long skuId) {

        SkuInfoEntity skuInfoEntity = this.baseMapper.selectOne(new LambdaQueryWrapper<SkuInfoEntity>().eq(SkuInfoEntity::getSkuId, skuId));

        return skuInfoEntity.getPrice();
    }

    /**
     * ????????????skuId?????????????????????
     * @param saleNumList
     */
    @Transactional(readOnly = false)
    @Override
    public void updateSkuSaleNum(List<SaleNumTo> saleNumList) {

        for (SaleNumTo saleNumTo : saleNumList) {
            SkuInfoEntity skuInfoEntity = this.baseMapper.selectById(saleNumTo.getSkuId());
            skuInfoEntity.setSaleCount(skuInfoEntity.getSaleCount() + saleNumTo.getNum());
            this.baseMapper.updateById(skuInfoEntity);
        }

    }

}