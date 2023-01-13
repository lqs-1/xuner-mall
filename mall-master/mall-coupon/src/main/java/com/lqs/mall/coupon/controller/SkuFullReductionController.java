package com.lqs.mall.coupon.controller;

import com.lqs.mall.common.constant.REnum;
import com.lqs.mall.common.to.SkuReductionTo;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.coupon.entity.SkuFullReductionEntity;
import com.lqs.mall.coupon.service.SkuFullReductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 商品满减信息
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:55:55
 */
@RestController
@RequestMapping("coupon/skufullreduction")
public class SkuFullReductionController {
    @Autowired
    private SkuFullReductionService skuFullReductionService;



    // 保存满减
    @PostMapping("/save")
    public R saveSkuReduction(@RequestBody SkuReductionTo reductionTo){
        try{
            // 程序代码

            skuFullReductionService.saveSkuReduction(reductionTo);

            return R.ok(REnum.SKU_FULL_REDUCTION_SAVE_SUCCESS.getStatusCode(),
                    REnum.SKU_FULL_REDUCTION_SAVE_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.SKU_FULL_REDUCTION_SAVE_FAIL.getStatusCode(),
                    REnum.SKU_FULL_REDUCTION_SAVE_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }

    }



    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = skuFullReductionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		SkuFullReductionEntity skuFullReduction = skuFullReductionService.getById(id);

        return R.ok().put("skuFullReduction", skuFullReduction);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SkuFullReductionEntity skuFullReduction){
		skuFullReductionService.updateById(skuFullReduction);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		skuFullReductionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
