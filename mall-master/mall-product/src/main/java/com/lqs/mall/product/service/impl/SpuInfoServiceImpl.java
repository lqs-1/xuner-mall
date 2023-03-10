package com.lqs.mall.product.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqs.mall.common.constant.Constant;
import com.lqs.mall.common.to.SkuReductionTo;
import com.lqs.mall.common.to.SpuBoundsTo;
import com.lqs.mall.common.to.es.SkuEsModelTo;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.common.utils.pagination.QueryPage;
import com.lqs.mall.product.dao.*;
import com.lqs.mall.product.entity.*;
import com.lqs.mall.product.feign.CouponFeignClientService;
import com.lqs.mall.product.feign.SearchFeignClientService;
import com.lqs.mall.product.feign.WareFeignClientService;
import com.lqs.mall.product.service.SpuInfoService;
import com.lqs.mall.product.vo.spu.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private SpuInfoDescDao spuInfoDescDao;

    @Autowired
    private SpuImagesDao spuImagesDao;

    @Autowired
    private AttrDao attrDao;

    @Autowired
    private ProductAttrValueDao productAttrValueDao;

    @Autowired
    private SkuInfoDao skuInfoDao;

    @Autowired
    private SkuImagesDao skuImagesDao;

    @Autowired
    private SkuSaleAttrValueDao skuSaleAttrValueDao;

    @Autowired
    private CouponFeignClientService couponFeignClientService;
    
    @Autowired
    private BrandDao brandDao;
    
    @Autowired
    private CategoryDao categoryDao;
    
    @Autowired
    private WareFeignClientService wareFeignClientService;

    @Autowired
    private SearchFeignClientService searchFeignClientService;


    @Override
    @Transactional(readOnly = true)
    // status=1&key=12&brandId=10&catelogId=225&page=1&limit=10
    public PageUtils queryPage(Map<String, Object> params) {
        // System.out.println(params);

        IPage<SpuInfoEntity> page = this.page(
                new QueryPage<SpuInfoEntity>().getPage(params),
                new LambdaQueryWrapper<SpuInfoEntity>()
                        .eq(!StringUtils.isEmpty((String)params.get("catelogId")), SpuInfoEntity::getCatalogId, params.get("catelogId"))
                        .eq(!StringUtils.isEmpty((String)params.get("brandId")) && !params.get("brandId").equals("0"),
                                SpuInfoEntity::getBrandId, params.get("brandId"))
                        .eq(!StringUtils.isEmpty((String)params.get("status")), SpuInfoEntity::getPublishStatus, params.get("status"))
                        .and(params.get("key") != null, query1 -> {
                            query1.like(SpuInfoEntity::getSpuDescription, params.get("key"))
                                    .or()
                                    .like(SpuInfoEntity::getSpuName, params.get("key"));
                        })
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional(readOnly = false)
    // ??????spu??????
    public void saveSpuInfo(SpuRespVo spuRespVo) {
        // ??????spu???????????? pms_spu_info
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(spuRespVo, spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        this.baseMapper.insert(spuInfoEntity);

        // ??????spu???????????????  pms_spu_info_desc
        List<String> decriptImgs = spuRespVo.getDecript();
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        // System.out.println(spuInfoEntity.getId());
        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
        spuInfoDescEntity.setDecript(String.join(",", decriptImgs));
        spuInfoDescDao.saveSpuInfoDesc(spuInfoDescEntity);

        // ??????spu????????????  pms_spu_images
        List<String> spuImages = spuRespVo.getImages();
        List<SpuImagesEntity> spuImagesEntityList = spuImages.stream().map((item) -> {
            SpuImagesEntity spuImagesEntity = new SpuImagesEntity();
            spuImagesEntity.setSpuId(spuInfoEntity.getId());
            spuImagesEntity.setImgUrl(item);
            return spuImagesEntity;
        }).filter(itemEntity -> {
            // ??????????????????????????????
            return !StringUtils.isEmpty(itemEntity.getImgUrl());
        }).collect(Collectors.toList());


        for (SpuImagesEntity spuImagesEntity : spuImagesEntityList) {
            spuImagesDao.insert(spuImagesEntity);
        }

        // spuImagesEntityList.stream().forEach(spuImagesDao::insert);

        // ??????spu???????????????   pms_product_attr_value
        List<BaseAttrs> baseAttrs = spuRespVo.getBaseAttrs();
        List<ProductAttrValueEntity> productAttrValueEntityList = baseAttrs.stream().map(item -> {
            // ????????????id??????????????????
            Long attrId = item.getAttrId();
            AttrEntity attrEntity = attrDao.selectById(attrId);
            ProductAttrValueEntity productAttrValueEntity = new ProductAttrValueEntity();
            productAttrValueEntity.setAttrId(attrEntity.getAttrId());
            productAttrValueEntity.setAttrValue(item.getAttrValues());
            productAttrValueEntity.setSpuId(spuInfoEntity.getId());
            productAttrValueEntity.setAttrName(attrEntity.getAttrName());
            productAttrValueEntity.setQuickShow(item.getShowDesc());
            return productAttrValueEntity;
        }).collect(Collectors.toList());

        for (ProductAttrValueEntity productAttrValueEntity : productAttrValueEntityList) {
            productAttrValueDao.insert(productAttrValueEntity);
        }


        // ??????spu???????????????  mall_sms.sms_spu_bounds
        Bounds bounds = spuRespVo.getBounds();
        SpuBoundsTo spuBoundsTo = new SpuBoundsTo();
        BeanUtils.copyProperties(bounds, spuBoundsTo);
        couponFeignClientService.spuBoundsSave(spuBoundsTo);

        // ????????????spu?????????sku??????,pms_sku_info
        List<Skus> skus = spuRespVo.getSkus();
        if (skus != null && skus.size() > 0) {
            skus.forEach(item -> {
                // ??????sku????????????   pms_sku_info
                // ?????????????????????sku??????
                String defaultImg = "";
                for (Images image : item.getImages()) {
                    if (image.getDefaultImg() == 1) {
                        defaultImg = image.getImgUrl();
                    }
                }

                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(item, skuInfoEntity);

                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
                skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setSpuId(spuInfoEntity.getId());
                skuInfoEntity.setSkuDefaultImg(defaultImg);

                skuInfoDao.insert(skuInfoEntity);

                // ??????sku????????????   pms_sku_images
                Long skuId = skuInfoEntity.getSkuId();
                List<SkuImagesEntity> skuImagesEntityList = item.getImages().stream().map(img -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setImgUrl(img.getImgUrl());
                    skuImagesEntity.setSkuId(skuId);
                    skuImagesEntity.setDefaultImg(img.getDefaultImg());
                    return skuImagesEntity;
                }).filter(entity ->
                    !StringUtils.isEmpty(entity.getImgUrl())
                ).collect(Collectors.toList());

                for (SkuImagesEntity skuImagesEntity : skuImagesEntityList) {
                    skuImagesDao.insert(skuImagesEntity);
                }

                // ??????sku???????????????  pms_sku_sale_attr_value
                List<Attr> attr = item.getAttr();
                List<SkuSaleAttrValueEntity> skuSaleAttrValueEntityList = attr.stream().map(atr -> {
                    SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                    BeanUtils.copyProperties(atr, skuSaleAttrValueEntity);
                    skuSaleAttrValueEntity.setSkuId(skuId);
                    return skuSaleAttrValueEntity;
                }).collect(Collectors.toList());

                for (SkuSaleAttrValueEntity skuSaleAttrValueEntity : skuSaleAttrValueEntityList) {
                    skuSaleAttrValueDao.insert(skuSaleAttrValueEntity);
                }

                // ??????sku???????????????  mall_sms.sms_sku_ladder,msm_sku_full_reduction,sms_member_price
                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtils.copyProperties(item, skuReductionTo);
                skuReductionTo.setSkuId(skuId);
                if (skuReductionTo.getFullCount() > 0 || skuReductionTo.getFullPrice().compareTo(new BigDecimal("0")) == 1) {
                    couponFeignClientService.saveSkuReduction(skuReductionTo);
                }
            });
        }
    }

    // spu??????
    @Transactional(readOnly = false)
    @Override
    public void spuUp(Long spuId) {
        
        // ?????????????????????
        List<SkuEsModelTo> skuEsModelToList = new ArrayList<>();

        // ????????????sku?????????????????????????????????????????????
        // ????????????spu?????????????????????
        List<ProductAttrValueEntity> baseAttrs = productAttrValueDao.selectList(new LambdaQueryWrapper<ProductAttrValueEntity>()
                .eq(spuId != null, ProductAttrValueEntity::getSpuId, spuId));
        
        // ???????????????????????????id
        List<Long> attrIds = baseAttrs.stream().map(attr -> attr.getAttrId()).collect(Collectors.toList());
        
        // ??????????????????????????????id?????????????????????????????????id
        List<Long> searchAttrIds = attrDao.selectSearchAttrByAttrId(attrIds);
        
        // ????????????????????????????????????id?????????????????????
        Set<Long> attrIdSet = new HashSet<>(searchAttrIds);
        
        // ????????????????????????????????????????????????????????????????????????
        List<SkuEsModelTo.Attr> attrs = baseAttrs.stream().filter(item -> attrIdSet.contains(item.getAttrId()) ? true : false)
                .map(item -> {
                    SkuEsModelTo.Attr attr = new SkuEsModelTo.Attr();
                    BeanUtils.copyProperties(item, attr);
                    return attr;
                }).collect(Collectors.toList());


        // ????????????spuId?????????sku??????
        List<SkuInfoEntity> skus = skuInfoDao.selectList(new LambdaQueryWrapper<SkuInfoEntity>()
                .eq(spuId != null, SkuInfoEntity::getSpuId, spuId)
        );

        // ???????????????skuId
        List<Long> skuIds = skus.stream().map(SkuInfoEntity::getSkuId).collect(Collectors.toList());

        // ???????????????????????????????????????hasStock
        Map<Long, Boolean> skuHasStockMap = new HashMap<>();
        try{
            // ????????????
            skuHasStockMap = wareFeignClientService.getSkuHasStock(skuIds);


        }catch(Exception e){
            e.printStackTrace();
            // ????????????

            log.error("????????????????????????");
        }finally{
            // ????????????

        }


        Map<Long, Boolean> finalSkuHasStockMap = skuHasStockMap;
        skuEsModelToList = skus.stream().map(sku -> {
            // ??????????????????????????????????????????????????????
            SkuEsModelTo esModelTo = new SkuEsModelTo();
            BeanUtils.copyProperties(sku, esModelTo);

            // skuPrice skuImg ????????????????????????????????????
            esModelTo.setSkuPrice(sku.getPrice());
            esModelTo.setSkuImg(sku.getSkuDefaultImg());

            // ???????????????????????????????????????????????????
            if (finalSkuHasStockMap == null){
                esModelTo.setHasStock(true);
            }else {
                esModelTo.setHasStock(finalSkuHasStockMap.get(sku.getSkuId()));
            }

            // ????????????hotScore 0 TODO ????????????
            esModelTo.setHotScore(0L);
            
            // ?????????????????????????????? brandName catalogName
            BrandEntity brand = brandDao.selectById(esModelTo.getBrandId());
            esModelTo.setBrandName(brand.getName());
            esModelTo.setBrandImg(brand.getLogo());

            CategoryEntity category= categoryDao.selectById(esModelTo.getCatalogId());
            esModelTo.setCatalogName(category.getName());
            
            // ??????????????????
            esModelTo.setAttrs(attrs);
            
            return esModelTo;
        }).collect(Collectors.toList());

        // ??????????????????es?????? ????????????
        try{
            // ????????????
            R result = searchFeignClientService.productStatusUp(skuEsModelToList);
            if (result.get("code") != null){
                // ??????
                SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
                spuInfoEntity.setId(spuId);
                spuInfoEntity.setPublishStatus(Constant.spuStatus.UP.getCode());
                spuInfoEntity.setUpdateTime(new Date());
                this.baseMapper.updateById(spuInfoEntity);
            }else {
                // ?????? TODO ????????????????????????????????????
                return;
            }
        }catch(Exception e){
            e.printStackTrace();
            // ????????????
            return;

        }finally{
            // ????????????

        }

    }

    // ??????skuId??????????????????spu??????
    @Transactional(readOnly = true)
    @Override
    public SpuInfoEntity findSpuInfoBySkuId(Long skuId) {

        // ??????sku??????
        SkuInfoEntity skuInfoEntity = skuInfoDao.selectById(skuId);
        Long spuId = skuInfoEntity.getSpuId();

        // ??????spu??????
        SpuInfoEntity spuInfo = this.baseMapper.selectById(spuId);

        return spuInfo;
    }

}