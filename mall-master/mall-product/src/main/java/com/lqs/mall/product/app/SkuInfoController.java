package com.lqs.mall.product.app;

import com.lqs.mall.common.constant.REnum;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.product.entity.SkuInfoEntity;
import com.lqs.mall.product.service.SkuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;



/**
 * sku信息
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:59:36
 */
@RestController
@RequestMapping("product/skuinfo")
public class SkuInfoController {
    @Autowired
    private SkuInfoService skuInfoService;


    /**
     * 获取某个商品的最新价格 用于页面展示
     * @param skuId
     * @return
     */
    @GetMapping("{skuId}/price")
    public R getLatestPrice(@PathVariable Long skuId){

        try{
            // 程序代码
            BigDecimal skuPrice = skuInfoService.getLatestPrice(skuId);

            return R.ok(REnum.REQUEST_SKU_LATEST_PRICE_SUCCESS.getStatusCode(),
                            REnum.REQUEST_SKU_LATEST_PRICE_SUCCESS.getStatusMsg())
                    .put(skuPrice);

        }catch(Exception e){
            e.printStackTrace();
            // 程序代码

            return R.error(REnum.REQUEST_SKU_LATEST_PRICE_FAIL.getStatusCode(),
                    REnum.REQUEST_SKU_LATEST_PRICE_FAIL.getStatusMsg());
        }finally{
            // 程序代码

        }

    }


    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        try{
            // 程序代码
            PageUtils skuListByPage = skuInfoService.queryPage(params);

            return R.ok(REnum.REQUEST_SKU_LIST_BY_PAGE_SUCCESS.getStatusCode(),
                            REnum.REQUEST_SKU_LIST_BY_PAGE_SUCCESS.getStatusMsg())
                    .put("skuList", skuListByPage);
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.REQUEST_SKU_LIST_BY_PAGE_FAIL.getStatusCode(),
                            REnum.REQUEST_SKU_LIST_BY_PAGE_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }

    }


    /**
     * 信息
     */
    @GetMapping("/info/{skuId}")
    public R Info(@PathVariable("skuId") Long skuId){

        try{
            // 程序代码
            SkuInfoEntity skuInfo = skuInfoService.getById(skuId);

            return R.ok(REnum.REQUEST_SKU_INFO_SUCCESS.getStatusCode(),
                    REnum.REQUEST_SKU_INFO_SUCCESS.getStatusMsg())
                    .put("skuInfo", skuInfo);
        }catch(Exception e){
            e.printStackTrace();
            // 程序代码

            return R.error(REnum.REQUEST_SKU_INFO_FAIL.getStatusCode(),
                    REnum.REQUEST_SKU_INFO_FAIL.getStatusMsg());
        }finally{
            // 程序代码

        }

    }

    /**
     * 获取skuName
     */
    @GetMapping("/skuName/{skuId}")
    public R skuName(@PathVariable("skuId") Long skuId){

        try{
            // 程序代码
            SkuInfoEntity skuInfo = skuInfoService.getById(skuId);

            return R.ok()
                    .put("skuName", skuInfo.getSkuName());
        }catch(Exception e){
            e.printStackTrace();
            // 程序代码

            return R.error();
        }finally{
            // 程序代码

        }

    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody SkuInfoEntity skuInfo){
		skuInfoService.save(skuInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SkuInfoEntity skuInfo){
		skuInfoService.updateById(skuInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] skuIds){
		skuInfoService.removeByIds(Arrays.asList(skuIds));

        return R.ok();
    }

}
