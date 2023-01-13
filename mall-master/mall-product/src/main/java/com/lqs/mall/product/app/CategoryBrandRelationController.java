package com.lqs.mall.product.app;

import com.lqs.mall.common.constant.REnum;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.product.entity.BrandEntity;
import com.lqs.mall.product.entity.CategoryBrandRelationEntity;
import com.lqs.mall.product.service.CategoryBrandRelationService;
import com.lqs.mall.product.vo.CategoryBrandRelationVo;
import com.lqs.mall.product.vo.RespBrandVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 品牌分类关联
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:59:36
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;


    /**
     * 根据catid获取对应的品牌,获取对应分类下的品牌
     * @param catId
     * @return
     */

    @GetMapping("brands/list/{catId}")
    public R getBrandsByCatid(@PathVariable Long catId){

        try{
            // 程序代码
            List<BrandEntity> brandEntityList = categoryBrandRelationService.getBrandsByCatid(catId);

            List<RespBrandVo> brandList = brandEntityList.stream().map((item) -> {
                RespBrandVo respBrandVo = new RespBrandVo();
                BeanUtils.copyProperties(item, respBrandVo);
                respBrandVo.setBrandName(item.getName());
                return respBrandVo;
            }).collect(Collectors.toList());

            return R.ok(REnum.REQUEST_BRAND_INFO_BY_CATEGORYID_SUCCESS.getStatusCode(),
                    REnum.REQUEST_BRAND_INFO_BY_CATEGORYID_SUCCESS.getStatusMsg())
                    .put("brandList", brandList);
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.REQUEST_BRAND_INFO_BY_CATEGORYID_FAIL.getStatusCode(),
                            REnum.REQUEST_BRAND_INFO_BY_CATEGORYID_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }



    }




    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryBrandRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 根据品牌id获取分组和品牌关系的列表
     * @param brandId
     * @return
     */
    @GetMapping("relation/list")
    public R relationList(@RequestParam("brandId") Long brandId){
        try{
            // 程序代码
            List<CategoryBrandRelationEntity> categoryBrandRelationEntityList = categoryBrandRelationService.getCategoryAndBrandRelationByBrandId(brandId);

            return R.ok(REnum.REQUEST_CATEGORY_AND_BRAND_BINDING_RELATION_LIST_UCCESS.getStatusCode(),
                    REnum.REQUEST_CATEGORY_AND_BRAND_BINDING_RELATION_LIST_UCCESS.getStatusMsg())
                    .put("categoryBrandRelationEntityList", categoryBrandRelationEntityList);
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.REQUEST_CATEGORY_AND_BRAND_BINDING_RELATION_LIST_FAIL.getStatusCode(),
                    REnum.REQUEST_CATEGORY_AND_BRAND_BINDING_RELATION_LIST_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存，细节添加分类和品牌的关联关系
     */
    @PostMapping("/save")
    public R save(@RequestBody CategoryBrandRelationVo categoryBrandRelationVo){
        try{
            // 程序代码
            R result = categoryBrandRelationService.saveDetail(categoryBrandRelationVo);

            return result;
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.APPEND_CATEGORY_AND_BRAND_BINDING_RELATION_FAIL.getStatusCode(),
                    REnum.APPEND_CATEGORY_AND_BRAND_BINDING_RELATION_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }


    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        try{
            // 程序代码
            categoryBrandRelationService.removeByIds(Arrays.asList(ids));

            return R.ok(REnum.DELETE_CATEGORY_AND_BRAND_BINDING_RELATION_SUCCESS.getStatusCode(),
                    REnum.DELETE_CATEGORY_AND_BRAND_BINDING_RELATION_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.DELETE_CATEGORY_AND_BRAND_BINDING_RELATION_FAIL.getStatusCode(),
                    REnum.DELETE_CATEGORY_AND_BRAND_BINDING_RELATION_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }

    }

}
