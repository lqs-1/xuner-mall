package com.lqs.mall.product.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqs.mall.common.constant.Constant;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.common.utils.pagination.QueryPage;
import com.lqs.mall.product.dao.AttrAttrgroupRelationDao;
import com.lqs.mall.product.dao.AttrDao;
import com.lqs.mall.product.dao.AttrGroupDao;
import com.lqs.mall.product.dao.CategoryDao;
import com.lqs.mall.product.entity.AttrAttrgroupRelationEntity;
import com.lqs.mall.product.entity.AttrEntity;
import com.lqs.mall.product.entity.AttrGroupEntity;
import com.lqs.mall.product.entity.CategoryEntity;
import com.lqs.mall.product.service.AttrService;
import com.lqs.mall.product.vo.AttrVo;
import com.lqs.mall.product.vo.RespAttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Autowired
    private AttrGroupDao attrGroupDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new QueryPage<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional(readOnly = false)
    @Override
    public void saveAttr(AttrVo attrVo) {
        // save base attr to attr table
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrVo, attrEntity);
        this.save(attrEntity);

        // save relation table

        if (attrVo.getAttrType() == Constant.attrType.ATTR_BASE_TYPE.getCode()) {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();

            attrAttrgroupRelationEntity.setAttrGroupId(attrVo.getAttrGroupId() != null ? attrVo.getAttrGroupId() : -1L);
            attrAttrgroupRelationEntity.setAttrId(attrEntity.getAttrId());

            attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
        }




    }

    @Transactional(readOnly = true)
    @Override
    public PageUtils findAttrList(Map<String, Object> params, String type, Long catId) {
        // ????????????categoryId
        // ????????????????????????,sale??????base
        // ???????????????key
        IPage<AttrEntity> page = this.page(
                new QueryPage<AttrEntity>().getPage(params),
                new LambdaQueryWrapper<AttrEntity>()
                        .eq(catId != 0, AttrEntity::getCatelogId, catId)
                        .eq(type.equals(Constant.attrType.ATTR_BASE_TYPE.getDesc()), AttrEntity::getAttrType, Constant.attrType.ATTR_BASE_TYPE.getCode())
                        .eq(type.equals(Constant.attrType.ATTR_SALE_TYPE.getDesc()), AttrEntity::getAttrType, Constant.attrType.ATTR_SALE_TYPE.getCode())
                        .and(params.get("key") != null, (query2) -> {
                            query2.like(AttrEntity::getAttrName, params.get("key"));
                        })

        );

        // ???????????????attrgroupName???categoryName,?????????????????????????????????,?????????????????????????????????


        List<AttrEntity> records = page.getRecords();

        records.stream().map((item) -> {
            Long attrId = item.getAttrId();
            CategoryEntity categoryEntity = categoryDao.selectById(item.getCatelogId());

            item.setCatelogName(categoryEntity.getName());


            if(type.equals(Constant.attrType.ATTR_BASE_TYPE.getDesc())){
                AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = attrAttrgroupRelationDao.selectOne(
                        new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                                .eq(true, AttrAttrgroupRelationEntity::getAttrId, attrId)
                );
                if (attrAttrgroupRelationEntity != null){
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectOne(
                            new LambdaQueryWrapper<AttrGroupEntity>()
                                    .eq(true,
                                            AttrGroupEntity::getAttrGroupId,
                                            attrAttrgroupRelationEntity.getAttrGroupId())
                    );

                    if (attrAttrgroupRelationEntity.getAttrGroupId() > 0){
                        item.setGroupName(attrGroupEntity.getAttrGroupName());
                    }
                }

            }

            return item;

        }).collect(Collectors.toList());

        // ???????????????
        return new PageUtils(page);
    }


    @Transactional(readOnly = true)
    @Override
    public RespAttrVo getAtrById(Long attrId) {


        RespAttrVo respAttrVo = new RespAttrVo();
        AttrEntity attrEntity = this.baseMapper.selectById(attrId);
        BeanUtils.copyProperties(attrEntity, respAttrVo);


        CategoryEntity categoryEntity = categoryDao.selectById(respAttrVo.getCatelogId());
        respAttrVo.setCatelogName(categoryEntity.getName());


        // ??????????????????
        if (respAttrVo.getAttrType() == Constant.attrType.ATTR_BASE_TYPE.getCode()){
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity
                    = attrAttrgroupRelationDao.selectOne(
                    new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                            .eq(true, AttrAttrgroupRelationEntity::getAttrId, attrId));


            if (attrAttrgroupRelationEntity != null){
                Long attrGroupId = attrAttrgroupRelationEntity.getAttrGroupId();
                if (attrGroupId > 0){
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectOne(new LambdaQueryWrapper<AttrGroupEntity>().eq(true, AttrGroupEntity::getAttrGroupId, attrGroupId));
                    respAttrVo.setGroupName(attrGroupEntity.getAttrGroupName());
                    respAttrVo.setAttrGroupId(attrGroupEntity.getAttrGroupId());
                }
            }
        }

        return respAttrVo;
    }

    @Transactional(readOnly = false)
    @Override
    public void updateAttr(AttrVo attr) {
        Long attrGroupId = attr.getAttrGroupId();
        Long catelogId = attr.getCatelogId();
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        // ????????????
        this.updateById(attrEntity);


        if (attr.getAttrType() == Constant.attrType.ATTR_BASE_TYPE.getCode()){
            // ???????????????????????????
            // ??????????????????????????????????????????????????????
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
            attrAttrgroupRelationEntity.setAttrId(attrEntity.getAttrId());
            attrAttrgroupRelationDao.update(
                    attrAttrgroupRelationEntity,
                    new LambdaUpdateWrapper<AttrAttrgroupRelationEntity>()
                            .eq(true, AttrAttrgroupRelationEntity::getAttrId, attrEntity.getAttrId()));
        }

    }


    @Override
    // ????????????????????? ?????????????????????????????????????????????
    @Transactional(readOnly = true)
    public PageUtils findNoRelationAttr(Long attrGroupId, Map<String, Object> params) {
        // ???????????????????????????????????????
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrGroupId);
        Long catelogId = attrGroupEntity.getCatelogId();
        // ?????????????????????????????????
        List<AttrGroupEntity> attrGroup = attrGroupDao.selectList(
                new LambdaQueryWrapper<AttrGroupEntity>()
                        .eq(catelogId != null, AttrGroupEntity::getCatelogId, catelogId)
                        .ne(attrGroupId != null, AttrGroupEntity::getAttrGroupId, attrGroupId));

        List<Long> collect  = attrGroup.stream().map((item) -> {
            return item.getAttrGroupId();
        }).collect(Collectors.toList());
        // ?????????????????????
        collect.add(attrGroupId);
        // ?????????????????????????????????
        List<AttrAttrgroupRelationEntity> groupIds = attrAttrgroupRelationDao.selectList(
                new LambdaQueryWrapper<AttrAttrgroupRelationEntity>().in(collect.size() > 0, AttrAttrgroupRelationEntity::getAttrGroupId, collect)
        );
        List<Long> attrIds = groupIds.stream().map((item) -> {
            return item.getAttrId();
        }).collect(Collectors.toList());

        // ?????????????????????????????????????????????????????????????????????
        // attrDao.selectList(
        //         new LambdaQueryWrapper<AttrEntity>()
        //                 .eq(catelogId != null, AttrEntity::getCatelogId, catelogId)
        //                 .notIn(attrIds.size() > 0, AttrEntity::getAttrId, attrIds)
        // );

        IPage<AttrEntity> page = this.page(
                new QueryPage<AttrEntity>().getPage(params),
                new LambdaQueryWrapper<AttrEntity>()
                        .eq(catelogId != null, AttrEntity::getCatelogId, catelogId)
                        .notIn(attrIds.size() > 0, AttrEntity::getAttrId, attrIds)
                        .eq(true, AttrEntity::getAttrType, Constant.attrType.ATTR_BASE_TYPE.getCode())
                        .and(!StringUtils.isEmpty((String) params.get("key")), query ->{
                            query.like(true, AttrEntity::getAttrName, (String) params.get("key"));
                        } )
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional(readOnly = true)
    // ??????catId??????????????????,????????????
    public List<AttrEntity> findSaleAttrListByCatId(Long catId) {


        new AttrEntity();

        List<AttrEntity> attrEntityList = this.baseMapper.selectList(
                new LambdaQueryWrapper<AttrEntity>()
                        .eq(catId > 0, AttrEntity::getCatelogId, catId)
                        .and((query1) -> {
                            query1.eq(AttrEntity::getAttrType, Constant.attrType.ATTR_SALE_TYPE.getCode());
                        })
        );

        return attrEntityList;
    }

    @Transactional(readOnly = false)
    @Override
    public void removeAttrByIds(List<Long> attrIds) {
        this.removeByIds(attrIds);

        attrAttrgroupRelationDao.deleteByAttrIds(attrIds);

    }


    public static void main(String[] args) {
        // ???????????????????????????????????????
        List<AttrEntity> attrEntityList = new ArrayList<>();

        for (long i = 0; i < 10; i++){
            AttrEntity attrEntity = new AttrEntity();
            attrEntity.setAttrId(i);
            attrEntityList.add(attrEntity);
        }

        // stream???????????????
        for (AttrEntity attrEntity : attrEntityList) {
            System.out.println(attrEntity);
        }
        System.out.println();



        // ??????stream...toList,???filter???
        attrEntityList.stream().filter(item ->
             item.getAttrId() == 0
        ).map(iter -> {
            iter.setAttrId(iter.getAttrId()+2L);
            return iter;
        }).collect(Collectors.toList());

        // stream???????????????
        for (AttrEntity attrEntity : attrEntityList) {
            System.out.println(attrEntity);
        }


        System.out.println();
        // ??????stream...toList,??????filter???
        attrEntityList.stream().map(iter -> {
            iter.setAttrId(iter.getAttrId()+10L);
            return iter;
        }).collect(Collectors.toList());

        // stream???????????????
        for (AttrEntity attrEntity : attrEntityList) {
            System.out.println(attrEntity);
        }


        // ??????????????????
        List<Integer> integerList = new ArrayList<>();
        // ??????
        for (int i = 1; i < 20; i++ ) {
            integerList.add(i);
        }

        // stream???????????????
        for (Integer integer : integerList) {
            System.out.print(integer);
        }

        System.out.println();

        // ??????stream??????
        integerList.stream().map(item -> {
            return item + 10;
        }).collect(Collectors.toList());

        // stream???????????????
        for (Integer integer : integerList) {
            System.out.print(integer);
        }
    }

}