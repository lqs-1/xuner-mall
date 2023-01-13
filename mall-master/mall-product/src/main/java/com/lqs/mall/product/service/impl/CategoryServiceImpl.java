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
import com.lqs.mall.product.dao.CategoryBrandRelationDao;
import com.lqs.mall.product.dao.CategoryDao;
import com.lqs.mall.product.entity.CategoryBrandRelationEntity;
import com.lqs.mall.product.entity.CategoryEntity;
import com.lqs.mall.product.service.CategoryService;
import com.lqs.mall.product.vo.Catelog2WebVo;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.*;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {


    @Autowired
    private CategoryBrandRelationDao categoryBrandRelationDao;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private RedissonClient redisson;

    @Transactional(readOnly = true)
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new QueryPage<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 用于递归的找到所有的下级分类
     * @return
     */
    private List<CategoryEntity> getChildrens(CategoryEntity root, List<CategoryEntity> allCategory){
        List<CategoryEntity> categoryEntityList = allCategory
                .stream()
                .filter(allMenu -> allMenu.getParentCid() == root.getCatId())
                .map( levelMenu2-> {
                    // 在这里找出三级菜单
                    levelMenu2.setCategoryChilds(getChildrens(levelMenu2, allCategory));
                    return levelMenu2;
                })
                .sorted(Comparator.comparing(CategoryEntity::getSort).reversed())
                .collect(Collectors.toList());

        return categoryEntityList;
    }

    /**
     * 查出所有分类,组装成父子的树形结构
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<CategoryEntity> listWithTree() {
        // 查出所有分类
        List<CategoryEntity> entities = this.baseMapper.selectList(null);

        // 组装树形结构
        // 找到一级菜单
        /**
         * 把查出的所有分类转成流，再从中筛选出一级菜单，在map中，对每一个一级菜单操作，找出属于自己的二级菜单
         */
        List<CategoryEntity> menuListTree = entities
                .stream()
                .filter(categoryEntity -> categoryEntity.getParentCid() == 0)
                .map(menuOne -> {
                    // 这里找到二级菜单
                    menuOne.setCategoryChilds(getChildrens(menuOne, entities));
                    return menuOne;
                })
                .sorted(Comparator.comparing(CategoryEntity::getSort).reversed()) // 默认升序,这样就是降序
                .collect(Collectors.toList());

        return menuListTree;
    }

    /**
     * 删除选中的菜单项,删除之前检测有没有被引用
     * @param cateIds
     */
    @Transactional(readOnly = false)
    // 组装多个删除操作，使用的是失效模式，保证数据一致性
    @Caching(evict = {
            @CacheEvict(cacheNames = Constant.CATALOG_GROUP, key = Constant.CATALOG_LEVEL_ONE_CACHE_KEY),
            @CacheEvict(cacheNames = Constant.CATALOG_GROUP, key = Constant.CATALOG_CACHE_KEY),
    })
    @Override
    public void removeMenuByIds(List<Long> cateIds) {
        // TODO 删除之前检测有没有被引用

        for (Long cateId : cateIds) {
            List<CategoryBrandRelationEntity> categoryBrandRelationEntityList = categoryBrandRelationDao.selectList(
                    new LambdaQueryWrapper<CategoryBrandRelationEntity>().eq(CategoryBrandRelationEntity::getCatelogId, cateId)
            );
            // System.out.println(categoryBrandRelationEntityList);

            if (categoryBrandRelationEntityList.size() == 0){
                this.removeById(cateId);
            }

        }


    }

    /**
     * 细节更新,级联更新
     * @param category
     */
    @Transactional(readOnly = false)
    // 组装多个删除操作，使用的是实效模式，保证数据一致性
    @Caching(evict = {
            @CacheEvict(cacheNames = Constant.CATALOG_GROUP, key = Constant.CATALOG_LEVEL_ONE_CACHE_KEY),
            @CacheEvict(cacheNames = Constant.CATALOG_GROUP, key = Constant.CATALOG_CACHE_KEY),
    })
    @Override
    public void updateDetailById(CategoryEntity category) {

        this.baseMapper.updateById(category);

        if (!StringUtils.isEmpty(category.getName())){
            CategoryBrandRelationEntity categoryBrandRelationEntity = new CategoryBrandRelationEntity();
            categoryBrandRelationEntity.setCatelogId(category.getCatId());
            categoryBrandRelationEntity.setCatelogName(category.getName());
            // 级联更新
            LambdaUpdateWrapper<CategoryBrandRelationEntity> categoryBrandRelationEntityLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            categoryBrandRelationEntityLambdaUpdateWrapper.eq(true, CategoryBrandRelationEntity::getCatelogId, category.getCatId());
            categoryBrandRelationDao.update(categoryBrandRelationEntity, categoryBrandRelationEntityLambdaUpdateWrapper);

        }




    }

    /**
     * 找三级分类的全路径
     * @param catelogId
     * @return
     */

    @Override
    @Transactional(readOnly = true)
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        // TODO   好好欣赏 ,列表到数组，集合反转
        List<Long> parentPath = findParentPath(catelogId, paths);
        Collections.reverse(parentPath);
        return  parentPath.toArray(new Long[parentPath.size()]);
    }

    // 查询一级分类列表
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = Constant.CATALOG_GROUP, key = Constant.CATALOG_LEVEL_ONE_CACHE_KEY, sync = true) // 指定要放到哪个名字的缓存,这个名字只是在spring中作为一个区分
    // key表示使用的key名字，可以是一个字符串，或者这种pel表达式，如果是字符串要再用单引号
    @Override
    public List<CategoryEntity> findCatelogLevelOneList() {

        List<CategoryEntity> categoryLevelOneList = this.baseMapper.selectList(
                new LambdaQueryWrapper<CategoryEntity>()
                        .eq(CategoryEntity::getCatLevel, 1)
        );

        return categoryLevelOneList;
    }


    // 去数据库中查询catalog三级分类JSON数据
    private Map<String, List<Catelog2WebVo>> getCatalogJsonFormDb(){

        // 查出所有的分类数据,只查一次数据，减轻数据库压力
        List<CategoryEntity> categoryAllData = this.baseMapper.selectList(null);

        // 查出所有一级分类
        List<CategoryEntity> catelogLevelOneList = categoryAllData.stream().filter(catelog -> catelog.getCatLevel() == 1).collect(Collectors.toList());

        // 封装数据,封装成一个map类型的对象
        Map<String, List<Catelog2WebVo>> catalogJson = catelogLevelOneList.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {

            // 查到一级分类的二级分类
            List<CategoryEntity> catelogLevelTwoList = categoryAllData.stream().filter(catelog -> catelog.getParentCid() == v.getCatId()).collect(Collectors.toList());

            // 封装上面的结果
            List<Catelog2WebVo> catelog2WebVoList = null;

            // 如果查到的二级分类列表不是空的
            if (catelogLevelTwoList != null) {

                // 遍历这个二级分类列表
                catelog2WebVoList = catelogLevelTwoList.stream().map(l2 -> {
                    // 封装二级的vo对象，三级分类默认为null
                    Catelog2WebVo catelog2WebVo = new Catelog2WebVo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName());

                    // 找当前二级分类的三级分类
                    List<CategoryEntity> catelogLevelThreeList = categoryAllData.stream().filter(catelog -> catelog.getParentCid() == l2.getCatId()).collect(Collectors.toList());

                    // 如果查到的三级分类列表不是空的
                    if (catelogLevelThreeList != null){

                        // 遍历这个三级分类列表
                        List<Catelog2WebVo.Catelog3WebVo> catelog3WebVoList = catelogLevelThreeList.stream().map(l3 -> {
                            // 封装成指定格式的数据，封装三级的vo对象
                            Catelog2WebVo.Catelog3WebVo catelog3WebVo = new Catelog2WebVo.Catelog3WebVo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());

                            return catelog3WebVo;
                        }).collect(Collectors.toList());
                        // 如果三级分类存在数据，就设置上
                        catelog2WebVo.setCatalog3List(catelog3WebVoList);
                    }
                    return catelog2WebVo;
                }).collect(Collectors.toList());
            }
            // 这就是返回的map的value值
            return catelog2WebVoList;
        }));


        return catalogJson;

    }

    // 返回首页的三级分类Json数据,为了减轻数据库的压力，使用缓存技术
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = Constant.CATALOG_GROUP, key = Constant.CATALOG_CACHE_KEY, sync = true)
    @Override
    public Map<String, List<Catelog2WebVo>> getCatalogJson() {
        return this.getCatalogJsonFormDb();

    }

    // 递归查找三级路径
    private List<Long> findParentPath(Long catelogId, List<Long> paths){
        paths.add(catelogId);
        // IService接口中的方法
        CategoryEntity byId = this.getById(catelogId);
        if (byId.getParentCid() != 0){
            findParentPath(byId.getParentCid(), paths);
        }
        return paths;
    }

}