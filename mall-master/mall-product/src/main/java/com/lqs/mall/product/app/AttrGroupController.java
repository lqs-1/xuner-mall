package com.lqs.mall.product.app;

import com.lqs.mall.common.constant.REnum;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.product.entity.AttrEntity;
import com.lqs.mall.product.entity.AttrGroupEntity;
import com.lqs.mall.product.feign.ThirdPartFeignClientService;
import com.lqs.mall.product.service.AttrAttrgroupRelationService;
import com.lqs.mall.product.service.AttrGroupService;
import com.lqs.mall.product.service.AttrService;
import com.lqs.mall.product.vo.RespAttrGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;



/**
 * 属性分组
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:59:36
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;


    @Autowired
    private AttrService attrService;

    @Autowired
    private ThirdPartFeignClientService thirdPartFeignClientService;

    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;


    @PostMapping(value = "attrGroupIconUpload")
    public R attrGroupIconUpload(@RequestBody MultipartFile file) throws IOException {
        R attrgourp = thirdPartFeignClientService.attrGroupIconUpload(file);
//        System.out.println(file.getOriginalFilename());
        return attrgourp;
    }


    // + this.attrGroupId + "/attr/relation

    /**
     * 获取属性分组的关联属性
     * @return
     */
    @GetMapping("{attrGroupId}/attr/relation")
    public R getAttrAttrGroupRelation(@PathVariable("attrGroupId") Long attrGroupId){

        try{
            // 程序代码

            List<AttrEntity> data = attrGroupService.findRelationAttr(attrGroupId);
            return R.ok(REnum.REQUEST_ATTR_ATTRGROUP_RELATION_SUCCESS.getStatusCode(),
                    REnum.REQUEST_ATTR_ATTRGROUP_RELATION_SUCCESS.getStatusMsg()).put("data", data);
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.REQUEST_ATTR_ATTRGROUP_RELATION_FAIL.getStatusCode(),
                    REnum.REQUEST_ATTR_ATTRGROUP_RELATION_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }
    }





    /**
     * 获取属性分组的非关联属性,获取的是当前category下面的所有分组里面没有被关联的属性
     * @return
     */
    @GetMapping("{attrGroupId}/noattr/relation")
    public R getAttrAttrGroupNoRelation(@PathVariable("attrGroupId") Long attrGroupId, @RequestParam Map<String, Object> params){

        try{
            // 程序代码

            PageUtils page = attrService.findNoRelationAttr(attrGroupId, params);
            return R.ok(REnum.REQUEST_ATTR_ATTRGROUP_NO_RELATION_SUCCESS.getStatusCode(),
                    REnum.REQUEST_ATTR_ATTRGROUP_NO_RELATION_SUCCESS.getStatusMsg()).put("page", page);
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.REQUEST_ATTR_ATTRGROUP_NO_RELATION_FAIL.getStatusCode(),
                    REnum.REQUEST_ATTR_ATTRGROUP_NO_RELATION_FAIL.getStatusMsg());
        }finally{
            // 程序代码
        }
    }


    /**
     * 更新属性分组和属性的关联关系
     * @param params
     * @return
     */

    @PostMapping("/attr/relation")
    public R updateAttrAttrGroupRelation(@RequestBody List<Map<String, Long>> params){


        try{
            // 程序代码
            attrAttrgroupRelationService.updateAttrAttrGroupRelation(params);

            return R.ok(REnum.UPDATE_ATTR_ATTRGROUP_RELATION_SUCCESS.getStatusCode(),
                    REnum.UPDATE_ATTR_ATTRGROUP_RELATION_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.UPDATE_ATTR_ATTRGROUP_RELATION_FAIL.getStatusCode(),
                    REnum.UPDATE_ATTR_ATTRGROUP_RELATION_FAIL.getStatusMsg());
        }finally{
            // 程序代码
        }
    }

    /**
     * 删除属性分组和属性的关联关系
     * @param params
     * @return
     */

    @PostMapping("/attr/relation/delete")
    public R attrRelationDelete(@RequestBody List<Map<String, Long>> params){


        try{
            // 程序代码
            attrAttrgroupRelationService.removeAttrAttrGroupRelation(params);

            return R.ok(REnum.REMOVE_ATTR_ATTRGROUP_RELATION_SUCCESS.getStatusCode(),
                    REnum.REMOVE_ATTR_ATTRGROUP_RELATION_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.REMOVE_ATTR_ATTRGROUP_RELATION_FAIL.getStatusCode(),
                    REnum.REMOVE_ATTR_ATTRGROUP_RELATION_FAIL.getStatusMsg());
        }finally{
            // 程序代码
        }
    }





    @GetMapping("list")
    public R list(){
        try{
            // 程序代码
            List<AttrGroupEntity> attrGroupList = attrGroupService.list();

            return R.ok(REnum.REQUEST_ATTRGROUP_SUCCESS.getStatusCode(),
                            REnum.REQUEST_ATTRGROUP_SUCCESS.getStatusMsg())
                    .put("attrGroupList", attrGroupList);
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.REQUEST_ATTRGROUP_FAIL.getStatusCode(),
                    REnum.REQUEST_ATTRGROUP_FAIL.getStatusMsg());
        }finally{
            // 程序代码
        }
    }



    /**
     * 根据分id类获取属性分组列表,分页版本
     */
    @GetMapping("/list/{categoryId}")
    public R listByCategoryId(@RequestParam Map<String, Object> params, @PathVariable Long categoryId){
        try{
            // 程序代码
            PageUtils page = attrGroupService.findAttrGroupByCategoryId(params, categoryId);

            return R.ok(REnum.REQUEST_ATTRGROUP_BYID_SUCCESS.getStatusCode(),
                    REnum.REQUEST_ATTRGROUP_BYID_SUCCESS.getStatusMsg())
                    .put("page", page);
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.REQUEST_ATTRGROUP_BYID_FAIL.getStatusCode(),
                    REnum.REQUEST_ATTRGROUP_BYID_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }
    }



    /**
     * 根据分id类获取属性分组列表,不分页版本
     */
    @GetMapping("/list/nolimet/{categoryId}")
    public R listByCategoryIdNoLimit(@PathVariable Long categoryId){
        try{
            // 程序代码
            List<AttrGroupEntity> attrGroupList = attrGroupService.findAttrGroupByCategoryIdNoLimit(categoryId);

            return R.ok(REnum.REQUEST_ATTRGROUP_BYID_SUCCESS.getStatusCode(),
                            REnum.REQUEST_ATTRGROUP_BYID_SUCCESS.getStatusMsg())
                    .put("attrGroupList", attrGroupList);
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.REQUEST_ATTRGROUP_BYID_FAIL.getStatusCode(),
                    REnum.REQUEST_ATTRGROUP_BYID_FAIL.getStatusMsg());
        }finally{
            // 程序代码
        }
    }


    /**
     * 信息
     */
    @GetMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
        try{
            // 程序代码
            AttrGroupEntity attrGroup = attrGroupService.getAttrGroupInfoById(attrGroupId);

            return R.ok(REnum.REQUEST_REBACK_ATTRGROUP_DATA_SUCCESS.getStatusCode(),
                    REnum.REQUEST_REBACK_ATTRGROUP_DATA_SUCCESS.getStatusMsg())
                    .put("attrGroup", attrGroup);
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();


            return R.error(REnum.REQUEST_REBACK_ATTRGROUP_DATA_FAIL.getStatusCode(),
                    REnum.REQUEST_REBACK_ATTRGROUP_DATA_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }

    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup){

        try{
            // 程序代码
            attrGroupService.save(attrGroup);

            return R.ok(REnum.APPEND_ATTRGOUP_SUCCESS.getStatusCode(),
                    REnum.APPEND_ATTRGOUP_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.APPEND_ATTRGOUP_FAIL.getStatusCode(),
                    REnum.APPEND_ATTRGOUP_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }

    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup){

        try{
            // 程序代码
            attrGroupService.updateById(attrGroup);
            return R.ok(REnum.EDIT_ATTRGOUP_SUCCESS.getStatusCode(),
                    REnum.EDIT_ATTRGOUP_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.EDIT_ATTRGOUP_FAIL.getStatusCode(),
                    REnum.EDIT_ATTRGOUP_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }

    }

    /**
     * 删除,细节删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds){
        try{
            // 程序代码
            attrGroupService.removeDetailByIds(Arrays.asList(attrGroupIds));

            return R.ok(REnum.DELETE_ATTRGOUP_SUCCESS.getStatusCode(),
                    REnum.DELETE_ATTRGOUP_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.DELETE_ATTRGOUP_FAIL.getStatusCode(),
                    REnum.DELETE_ATTRGOUP_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }

    }


    /**
     * 在发布山商品的时候,根据商品的三级分类的id来获取对应的基本属性
     * 查到分组,再根据分组查到对应的属性
     * @param catId
     * @return
     */
    @GetMapping("{catId}/withAttr")
    public R requestBaseAttrByCatId(@PathVariable Long catId){
        try{
            // 程序代码
            List<RespAttrGroupVo> respAttrGroupVoList = attrGroupService.findAttrGroupAndBaseAttrByCatId(catId);

            return R.ok(REnum.REQUEST_ATTRGROUP_AND_ATTR_OBJECT_LIST_BY_CATEGORYID_SUCCESS.getStatusCode(),
                    REnum.REQUEST_ATTRGROUP_AND_ATTR_OBJECT_LIST_BY_CATEGORYID_SUCCESS.getStatusMsg())
                    .put("baseAttrObjectList", respAttrGroupVoList);
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.REQUEST_ATTRGROUP_AND_ATTR_OBJECT_LIST_BY_CATEGORYID_FAIL.getStatusCode(),
                            REnum.REQUEST_ATTRGROUP_AND_ATTR_OBJECT_LIST_BY_CATEGORYID_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }


    }

}
