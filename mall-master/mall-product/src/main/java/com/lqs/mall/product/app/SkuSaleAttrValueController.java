package com.lqs.mall.product.app;

import com.lqs.mall.common.constant.REnum;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.product.entity.SkuSaleAttrValueEntity;
import com.lqs.mall.product.service.SkuSaleAttrValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;



/**
 * sku销售属性&值
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:59:36
 */
@RestController
@RequestMapping("product/skusaleattrvalue")
public class SkuSaleAttrValueController {
    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;

    /**
     * 列表
     */


    @GetMapping("saleAttrValues/{skuId}")
    public R getSkuSaleAttrValues(@PathVariable Long skuId){

        try{
            // 程序代码

            List<String> skuSaleAttrValuesList = skuSaleAttrValueService.getSkuSaleAttrValuesAsList(skuId);

            return R.ok(REnum.REQUEST_SKU_SALE_ATTR_VALUES_SUCCESS.getStatusCode(),
                    REnum.REQUEST_SKU_SALE_ATTR_VALUES_SUCCESS.getStatusMsg())
                    .put("skuSaleAttrValuesList", skuSaleAttrValuesList);

        }catch(Exception e){
            e.printStackTrace();
            // 程序代码

            return R.error(REnum.REQUEST_SKU_SALE_ATTR_VALUES_FAIL.getStatusCode(),
                    REnum.REQUEST_SKU_SALE_ATTR_VALUES_FAIL.getStatusMsg());
        }finally{
            // 程序代码

        }


    }


    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = skuSaleAttrValueService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		SkuSaleAttrValueEntity skuSaleAttrValue = skuSaleAttrValueService.getById(id);

        return R.ok().put("skuSaleAttrValue", skuSaleAttrValue);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody SkuSaleAttrValueEntity skuSaleAttrValue){
		skuSaleAttrValueService.save(skuSaleAttrValue);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SkuSaleAttrValueEntity skuSaleAttrValue){
		skuSaleAttrValueService.updateById(skuSaleAttrValue);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		skuSaleAttrValueService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
