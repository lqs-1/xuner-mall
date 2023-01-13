package com.lqs.mall.product.app;

import com.lqs.mall.common.constant.Constant;
import com.lqs.mall.common.constant.REnum;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.product.entity.AttrEntity;
import com.lqs.mall.product.entity.ProductAttrValueEntity;
import com.lqs.mall.product.feign.ThirdPartFeignClientService;
import com.lqs.mall.product.service.AttrService;
import com.lqs.mall.product.service.CategoryService;
import com.lqs.mall.product.service.ProductAttrValueService;
import com.lqs.mall.product.vo.AttrVo;
import com.lqs.mall.product.vo.RespAttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;



/**
 * 商品属性
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:59:36
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ThirdPartFeignClientService thirdPartFeignClientService;

    @Autowired
    private ProductAttrValueService productAttrValueService;


    @GetMapping("getAttrName/{attrId}")
    public R getAttrName(@PathVariable("attrId") Long attrId){
        try{
            RespAttrVo attr = attrService.getAtrById(attrId);
            String attrName = attr.getAttrName();

            return R.ok(REnum.REQUEST_ATTRNAME_SUCCESS.getStatusCode(),
                    REnum.REQUEST_ATTRNAME_SUCCESS.getStatusMsg())
                    .put("attrName", attrName);
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.REQUEST_ATTRNAME_FAIL.getStatusCode(),
                    REnum.REQUEST_ATTRNAME_FAIL.getStatusMsg());

        }finally{
            // 程序代码
        }
    }


    @PostMapping(value = "attrIconUpload")
    public R attrIconUpload(@RequestBody MultipartFile file) throws IOException {
        R attr = thirdPartFeignClientService.attrIconUpload(file);
//        System.out.println(file.getOriginalFilename());
        return attr;
    }


    /**
     * 列表,获取所有的属性,根据类型获取对应的属性,0是销售属性,1是基本属性,这个是分页的
     */
    @GetMapping("/list/{type}/{catId}")
    public R list(@RequestParam Map<String, Object> params, @PathVariable String type, @PathVariable Long catId){
        try{
            // 程序代码
            PageUtils page = attrService.findAttrList(params, type, catId);

            return R.ok(type.equals(Constant.attrType.ATTR_BASE_TYPE.getDesc()) ?
                                    REnum.REQUEST_BASE_ATTR_SUCCESS.getStatusCode()
                                    : REnum.REQUEST_SALE_ATTR_SUCCESS.getStatusCode(),
                    type.equals(Constant.attrType.ATTR_BASE_TYPE.getDesc()) ?
                            REnum.REQUEST_BASE_ATTR_SUCCESS.getStatusMsg()
                            : REnum.REQUEST_SALE_ATTR_SUCCESS.getStatusMsg())
                    .put("page", page);
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(type.equals(Constant.attrType.ATTR_BASE_TYPE.getDesc()) ?
                            REnum.REQUEST_BASE_ATTR_FAIL.getStatusCode()
                            : REnum.REQUEST_SALE_ATTR_FAIL.getStatusCode(),
                    type.equals(Constant.attrType.ATTR_BASE_TYPE.getDesc()) ?
                            REnum.REQUEST_BASE_ATTR_FAIL.getStatusMsg()
                            : REnum.REQUEST_SALE_ATTR_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }
    }


    /**
     * 获取spu的规格参数，基本属性
     * @param spuId
     * @return
     */
    // /product/attr/base/listforspu/${this.spuId}
    @GetMapping("base/listforspu/{spuId}")
    public R baseAttrListforspu(@PathVariable Long spuId){
        try{
            // 程序代码
            List<ProductAttrValueEntity> productAttrValueList = productAttrValueService.baseAttrListforspu(spuId);

            return R.ok(REnum.REQUEST_BASE_ATTR_SUCCESS.getStatusCode(),
                    REnum.REQUEST_BASE_ATTR_SUCCESS.getStatusMsg())
                    .put("productAttrValueList", productAttrValueList);
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.REQUEST_BASE_ATTR_FAIL.getStatusCode(),
                    REnum.REQUEST_BASE_ATTR_FAIL.getStatusMsg());
        }finally{
            // 程序代码
        }
    }


    /**
     * 根据attrid获取基本属性的信息,回显
     */
    @GetMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId){

        try{
            RespAttrVo attr = attrService.getAtrById(attrId);

            // 查找相关信息
            // 查找关联的三级分类全路径
            Long[] catelogPath = categoryService.findCatelogPath(attr.getCatelogId());
            attr.setCatelogIds(catelogPath);



            return R.ok(REnum.REQUEST_ATTR_SUCCESS.getStatusCode(),
                    REnum.REQUEST_ATTR_SUCCESS.getStatusMsg())
                    .put("attr", attr);
            // 程序代码
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.REQUEST_ATTR_FAIL.getStatusCode(),
                            REnum.REQUEST_ATTR_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }

    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody AttrVo attrVo){
        try{
            // 程序代码

            attrService.saveAttr(attrVo);

            return R.ok(attrVo.getAttrType() == Constant.attrType.ATTR_BASE_TYPE.getCode() ?
                            REnum.APPEND_BASE_ATTR_SUCCESS.getStatusCode()
                            : REnum.APPEND_SALE_ATTR_SUCCESS.getStatusCode(),
                    attrVo.getAttrType() == Constant.attrType.ATTR_BASE_TYPE.getCode() ?
                            REnum.APPEND_BASE_ATTR_SUCCESS.getStatusMsg()
                            : REnum.APPEND_SALE_ATTR_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(attrVo.getAttrType() == Constant.attrType.ATTR_BASE_TYPE.getCode() ?
                            REnum.APPEND_BASE_ATTR_FAIL.getStatusCode()
                            : REnum.APPEND_SALE_ATTR_FAIL.getStatusCode(),
                    attrVo.getAttrType() == Constant.attrType.ATTR_BASE_TYPE.getCode() ?
                            REnum.APPEND_BASE_ATTR_FAIL.getStatusMsg()
                            : REnum.APPEND_SALE_ATTR_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }

    }

    /**
     * 修改属性
     */
    @PostMapping("/update")
    public R update(@RequestBody AttrVo attr){
        try{
            // 程序代码
            attrService.updateAttr(attr);

            return R.ok(attr.getAttrType() == Constant.attrType.ATTR_BASE_TYPE.getCode() ?
                            REnum.EDIT_BASE_ATTR_SUCCESS.getStatusCode()
                            : REnum.EDIT_SALE_ATTR_SUCCESS.getStatusCode(),
                    attr.getAttrType() == Constant.attrType.ATTR_BASE_TYPE.getCode() ?
                            REnum.EDIT_BASE_ATTR_SUCCESS.getStatusMsg()
                            : REnum.EDIT_SALE_ATTR_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(attr.getAttrType() == Constant.attrType.ATTR_BASE_TYPE.getCode() ?
                            REnum.EDIT_BASE_ATTR_FAIL.getStatusCode()
                            : REnum.EDIT_SALE_ATTR_FAIL.getStatusCode(),
                    attr.getAttrType() == Constant.attrType.ATTR_BASE_TYPE.getCode() ?
                            REnum.EDIT_BASE_ATTR_FAIL.getStatusMsg()
                            : REnum.EDIT_SALE_ATTR_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }

    }

    // /product/attr/update/${this.spuId}

    /**
     * 更新spu的属性规格参数
     * @param spuId
     * @param productAttrValueEntityList
     * @return
     */
    @PostMapping("update/{spuId}")
    public R updateSpuAttr(@PathVariable Long spuId,
                           @RequestBody List<ProductAttrValueEntity> productAttrValueEntityList){

        try{
            // 程序代码
            productAttrValueService.updateSpuAttr(spuId, productAttrValueEntityList);

            return R.ok(REnum.ALTER_SPU_ATTR_SUCCESS.getStatusCode(),
                    REnum.ALTER_SPU_ATTR_SUCCESS.getStatusMsg());
        }catch(Exception e){
            e.printStackTrace();
            // 程序代码

            return R.error(REnum.ALTER_SPU_ATTR_FAIL.getStatusCode(),
                    REnum.ALTER_SPU_ATTR_FAIL.getStatusMsg());
        }finally{
            // 程序代码

        }

    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] attrIds){
        try{
            // 程序代码

            attrService.removeAttrByIds(Arrays.asList(attrIds));

            return R.ok(REnum.DELETE_ATTR_SUCCESS.getStatusCode(),
                    REnum.DELETE_ATTR_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.ok(REnum.DELETE_ATTR_FAIL.getStatusCode(),
                    REnum.DELETE_ATTR_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }

    }



    /**
     * 列表,获取销售属性,根据catId,没有分页
     */
    @GetMapping("/sale/list/{catId}")
    public R requestSaleAttrList(@PathVariable Long catId){
        try{
            // 程序代码
            List<AttrEntity> attrEntityList = attrService.findSaleAttrListByCatId(catId);

            return R.ok(REnum.REQUEST_SALE_ATTR_LIST_BY_CATEGORYID_SUCCESS.getStatusCode(),
                    REnum.REQUEST_SALE_ATTR_LIST_BY_CATEGORYID_SUCCESS.getStatusMsg())
                    .put("saleAttrList", attrEntityList);
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.REQUEST_SALE_ATTR_LIST_BY_CATEGORYID_FAIL.getStatusCode(),
                            REnum.REQUEST_SALE_ATTR_LIST_BY_CATEGORYID_FAIL.getStatusMsg());
        }finally{
            // 程序代码
        }
    }


}
