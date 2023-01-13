package com.lqs.mall.product.app;

import com.lqs.mall.common.constant.Constant;
import com.lqs.mall.common.constant.REnum;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.product.entity.CategoryEntity;
import com.lqs.mall.product.service.CategoryService;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPool;

import java.util.Arrays;
import java.util.List;



/**
 * 商品三级分类
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:59:36
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedissonClient redisson;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 查出所有的分类以及子分类,以树形结构组装起来
     */
    @GetMapping("list/categoryList/tree")
    public R categoryList(){
        try{
            List<CategoryEntity> categoryList = categoryService.listWithTree();

            return R.ok(REnum.REQUEST_MENU_LIST_TREE_SUCCESS.getStatusCode(),
                    REnum.REQUEST_MENU_LIST_TREE_SUCCESS.getStatusMsg())
                    .put("categoryList", categoryList);
        }catch(Exception e){
            e.printStackTrace();

            return R.error(REnum.REQUEST_MENU_LIST_TREE_FAIL.getStatusCode(),
                    REnum.REQUEST_MENU_LIST_TREE_FAIL.getStatusMsg());
        }finally{
          // 程序代码
        }
    }


    /**
     * 获取分类信息
     */
    @GetMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("category", category);
    }

    /**
     * 添加分类
     */
    // 组装多个删除操作，使用的是实效模式，保证数据一致性
    @Caching(evict = {
            @CacheEvict(cacheNames = Constant.CATALOG_GROUP, key = Constant.CATALOG_LEVEL_ONE_CACHE_KEY),
            @CacheEvict(cacheNames = Constant.CATALOG_GROUP, key = Constant.CATALOG_CACHE_KEY),
    })
    @PostMapping("/add")
    public R save(@RequestBody CategoryEntity category){

        try{
            // 程序代码
            categoryService.save(category);

            return R.ok(REnum.APPEND_SINGLE_CATEGORY_SUCCESS.getStatusCode(),
                    REnum.APPEND_SINGLE_CATEGORY_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.APPEND_SINGLE_CATEGORY_FAIL.getStatusCode(),
                    REnum.APPEND_SINGLE_CATEGORY_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }

    }

    /**
     * 修改分类, 细节更新你
     */
    @PutMapping("/update")
    public R update(@RequestBody CategoryEntity category){

        try{
            // 程序代码
            categoryService.updateDetailById(category);

            return R.ok(REnum.EDIT_SINGLE_CATEGORY_SUCCESS.getStatusCode(),
                    REnum.EDIT_SINGLE_CATEGORY_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.EDIT_SINGLE_CATEGORY_FAIL.getStatusCode(),
                    REnum.EDIT_SINGLE_CATEGORY_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }

    }

    /**
     * 删除分类
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] catIds){

        try{
            // 删除之前 检查当前被删除的菜单 有没有被别的地方引用
            categoryService.removeMenuByIds(Arrays.asList(catIds));

            return R.ok(REnum.DELETE_SINGLE_CATEGORY_SUCCESS.getStatusCode(),
                    REnum.DELETE_SINGLE_CATEGORY_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.DELETE_SINGLE_CATEGORY_FAIL.getStatusCode(),
                    REnum.DELETE_SINGLE_CATEGORY_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }
    }

}
