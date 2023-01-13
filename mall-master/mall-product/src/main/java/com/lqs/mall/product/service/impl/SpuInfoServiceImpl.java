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
    // 保存spu信息
    public void saveSpuInfo(SpuRespVo spuRespVo) {
        // 保存spu基本信息 pms_spu_info
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(spuRespVo, spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        this.baseMapper.insert(spuInfoEntity);

        // 保存spu的描述图片  pms_spu_info_desc
        List<String> decriptImgs = spuRespVo.getDecript();
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        // System.out.println(spuInfoEntity.getId());
        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
        spuInfoDescEntity.setDecript(String.join(",", decriptImgs));
        spuInfoDescDao.saveSpuInfoDesc(spuInfoDescEntity);

        // 保存spu的图片集  pms_spu_images
        List<String> spuImages = spuRespVo.getImages();
        List<SpuImagesEntity> spuImagesEntityList = spuImages.stream().map((item) -> {
            SpuImagesEntity spuImagesEntity = new SpuImagesEntity();
            spuImagesEntity.setSpuId(spuInfoEntity.getId());
            spuImagesEntity.setImgUrl(item);
            return spuImagesEntity;
        }).filter(itemEntity -> {
            // 空列表的图片放弃保存
            return !StringUtils.isEmpty(itemEntity.getImgUrl());
        }).collect(Collectors.toList());


        for (SpuImagesEntity spuImagesEntity : spuImagesEntityList) {
            spuImagesDao.insert(spuImagesEntity);
        }

        // spuImagesEntityList.stream().forEach(spuImagesDao::insert);

        // 保存spu的规格参数   pms_product_attr_value
        List<BaseAttrs> baseAttrs = spuRespVo.getBaseAttrs();
        List<ProductAttrValueEntity> productAttrValueEntityList = baseAttrs.stream().map(item -> {
            // 根据属性id查处对应属性
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


        // 保存spu的积分信息  mall_sms.sms_spu_bounds
        Bounds bounds = spuRespVo.getBounds();
        SpuBoundsTo spuBoundsTo = new SpuBoundsTo();
        BeanUtils.copyProperties(bounds, spuBoundsTo);
        couponFeignClientService.spuBoundsSave(spuBoundsTo);

        // 保存当前spu的所有sku信息,pms_sku_info
        List<Skus> skus = spuRespVo.getSkus();
        if (skus != null && skus.size() > 0) {
            skus.forEach(item -> {
                // 保存sku基本信息   pms_sku_info
                // 获取冗余字段，sku图片
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

                // 保存sku图片信息   pms_sku_images
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

                // 保存sku的销售属性  pms_sku_sale_attr_value
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

                // 保存sku的满减信息  mall_sms.sms_sku_ladder,msm_sku_full_reduction,sms_member_price
                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtils.copyProperties(item, skuReductionTo);
                skuReductionTo.setSkuId(skuId);
                if (skuReductionTo.getFullCount() > 0 || skuReductionTo.getFullPrice().compareTo(new BigDecimal("0")) == 1) {
                    couponFeignClientService.saveSkuReduction(skuReductionTo);
                }
            });
        }
    }

    // spu上架
    @Transactional(readOnly = false)
    @Override
    public void spuUp(Long spuId) {
        
        // 组装需要的数据
        List<SkuEsModelTo> skuEsModelToList = new ArrayList<>();

        // 查询当前sku的所有可以被用来检索的规格属性
        // 查处对应spu关联的规格参数
        List<ProductAttrValueEntity> baseAttrs = productAttrValueDao.selectList(new LambdaQueryWrapper<ProductAttrValueEntity>()
                .eq(spuId != null, ProductAttrValueEntity::getSpuId, spuId));
        
        // 收集这些规格参数的id
        List<Long> attrIds = baseAttrs.stream().map(attr -> attr.getAttrId()).collect(Collectors.toList());
        
        // 根据查出的规格参数的id去查询可以被检索的属性id
        List<Long> searchAttrIds = attrDao.selectSearchAttrByAttrId(attrIds);
        
        // 将查到的可以检索的属性的id列表转换成集合
        Set<Long> attrIdSet = new HashSet<>(searchAttrIds);
        
        // 从基本属性中筛选出可以被检索的属性并进行属性拷贝
        List<SkuEsModelTo.Attr> attrs = baseAttrs.stream().filter(item -> attrIdSet.contains(item.getAttrId()) ? true : false)
                .map(item -> {
                    SkuEsModelTo.Attr attr = new SkuEsModelTo.Attr();
                    BeanUtils.copyProperties(item, attr);
                    return attr;
                }).collect(Collectors.toList());


        // 查出当前spuId对应的sku信息
        List<SkuInfoEntity> skus = skuInfoDao.selectList(new LambdaQueryWrapper<SkuInfoEntity>()
                .eq(spuId != null, SkuInfoEntity::getSpuId, spuId)
        );

        // 获取所有的skuId
        List<Long> skuIds = skus.stream().map(SkuInfoEntity::getSkuId).collect(Collectors.toList());

        // 发送远程调用查询是否有库存hasStock
        Map<Long, Boolean> skuHasStockMap = new HashMap<>();
        try{
            // 程序代码
            skuHasStockMap = wareFeignClientService.getSkuHasStock(skuIds);


        }catch(Exception e){
            e.printStackTrace();
            // 程序代码

            log.error("库存服务获取失败");
        }finally{
            // 程序代码

        }


        Map<Long, Boolean> finalSkuHasStockMap = skuHasStockMap;
        skuEsModelToList = skus.stream().map(sku -> {
            // 先进行属性拷贝，没有的属性再手动添加
            SkuEsModelTo esModelTo = new SkuEsModelTo();
            BeanUtils.copyProperties(sku, esModelTo);

            // skuPrice skuImg 手动添加，字段名字不一样
            esModelTo.setSkuPrice(sku.getPrice());
            esModelTo.setSkuImg(sku.getSkuDefaultImg());

            // 如果远程调用失败了，那么默认有库存
            if (finalSkuHasStockMap == null){
                esModelTo.setHasStock(true);
            }else {
                esModelTo.setHasStock(finalSkuHasStockMap.get(sku.getSkuId()));
            }

            // 热度评分hotScore 0 TODO 比较复杂
            esModelTo.setHotScore(0L);
            
            // 查询品牌和分类的名字 brandName catalogName
            BrandEntity brand = brandDao.selectById(esModelTo.getBrandId());
            esModelTo.setBrandName(brand.getName());
            esModelTo.setBrandImg(brand.getLogo());

            CategoryEntity category= categoryDao.selectById(esModelTo.getCatalogId());
            esModelTo.setCatalogName(category.getName());
            
            // 设置检索属性
            esModelTo.setAttrs(attrs);
            
            return esModelTo;
        }).collect(Collectors.toList());

        // 将数据发送给es保存 远程调用
        try{
            // 程序代码
            R result = searchFeignClientService.productStatusUp(skuEsModelToList);
            if (result.get("code") != null){
                // 成功
                SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
                spuInfoEntity.setId(spuId);
                spuInfoEntity.setPublishStatus(Constant.spuStatus.UP.getCode());
                spuInfoEntity.setUpdateTime(new Date());
                this.baseMapper.updateById(spuInfoEntity);
            }else {
                // 失败 TODO 再次发送请求，接口幂等性
                return;
            }
        }catch(Exception e){
            e.printStackTrace();
            // 程序代码
            return;

        }finally{
            // 程序代码

        }

    }

    // 根据skuId查询出商品得spu信息
    @Transactional(readOnly = true)
    @Override
    public SpuInfoEntity findSpuInfoBySkuId(Long skuId) {

        // 获取sku信息
        SkuInfoEntity skuInfoEntity = skuInfoDao.selectById(skuId);
        Long spuId = skuInfoEntity.getSpuId();

        // 获取spu信息
        SpuInfoEntity spuInfo = this.baseMapper.selectById(spuId);

        return spuInfo;
    }

}