package com.lqs.mall.ware.controller;

import com.lqs.mall.common.constant.REnum;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.ware.entity.WareSkuEntity;
import com.lqs.mall.ware.exception.NoStockException;
import com.lqs.mall.ware.service.WareSkuService;
import com.lqs.mall.ware.vo.CartCheckSkuHasStockVo;
import com.lqs.mall.ware.vo.WareSkuLockVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 商品库存
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 22:00:14
 */
@RestController
@RequestMapping("ware/waresku")
public class WareSkuController {
    @Autowired
    private WareSkuService wareSkuService;


    /**
     * 扣除库存并且 返回skuIds
     * @param orderSn
     * @return
     */
    @PostMapping("ware/{orderSn}/deduction/stock")
    public R deductionWareStock(@PathVariable String orderSn){

        try{
            // 程序代码
            List<Long> skuIds = wareSkuService.deductionWareStock(orderSn);

            return R.ok(REnum.WARE_STOCK_DEDUCTION_SUCCESS.getStatusCode(),
                    REnum.WARE_STOCK_DEDUCTION_SUCCESS.getStatusMsg())
                    .put("skuIds", skuIds);
        }catch(Exception e){
            e.printStackTrace();
            // 程序代码

            return R.error(REnum.WARE_STOCK_DEDUCTION_FAIL.getStatusCode(),
                    REnum.WARE_STOCK_DEDUCTION_FAIL.getStatusMsg());
        }finally{
            // 程序代码

        }

    }


    /**
     * 下订单锁库存
     * @param wareSkuLock
     * @return
     */
    @PostMapping("lock/stock")
    public R orderLockStock(@RequestBody WareSkuLockVo wareSkuLock){
        try{
            // System.out.println(wareSkuLock);
            // 程序代码
            Boolean lockStockResult = wareSkuService.orderLockStock(wareSkuLock);
            return R.ok(REnum.LOCK_SKU_STOCK_SUCCESS.getStatusCode(),
                    REnum.LOCK_SKU_STOCK_SUCCESS.getStatusMsg())
                    .put("lockStockResult", lockStockResult);
        }catch(NoStockException e){
            e.printStackTrace();
            // 程序代码
            // 库存不足
            return R.error(REnum.LOCK_SKU_STOCK_NUM_FAIL.getStatusCode(),
                    REnum.LOCK_SKU_STOCK_NUM_FAIL.getStatusMsg());
        }catch (Exception e){
            // 其他异常
            return R.error(REnum.LOCK_SKU_STOCK_FAIL.getStatusCode(),
                    REnum.LOCK_SKU_STOCK_FAIL.getStatusMsg());
        }finally{
            // 程序代码

        }

    }


    /**
     * 根据skuId查询商品得库存
     * @param skuId
     * @return
     */
    @PostMapping("ware/stock")
    public R orderSkuHasStock(@RequestBody Long skuId){
        try{
            // 程序代码
            CartCheckSkuHasStockVo skuHasStock = wareSkuService.orderSkuHasStock(skuId);
            // System.out.println(skuHasStock);

            return R.ok(REnum.REQUEST_SKU_STOCK_SUCCESS.getStatusCode(),
                    REnum.REQUEST_SKU_STOCK_SUCCESS.getStatusMsg())
                    .put("skuHasStock", skuHasStock);
        }catch(Exception e){
            e.printStackTrace();
            // 程序代码

            return R.error(REnum.REQUEST_SKU_STOCK_FAIL.getStatusCode(),
                    REnum.REQUEST_SKU_STOCK_FAIL.getStatusMsg());

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
            PageUtils goodsListByPage = wareSkuService.queryPage(params);

            return R.ok(REnum.REQUEST_GOODS_WARE_LIST_BY_PAGE_SUCCESS.getStatusCode(),
                            REnum.REQUEST_GOODS_WARE_LIST_BY_PAGE_SUCCESS.getStatusMsg())
                    .put("goodsWareList", goodsListByPage);
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.REQUEST_GOODS_WARE_LIST_BY_PAGE_FAIL.getStatusCode(),
                    REnum.REQUEST_GOODS_WARE_LIST_BY_PAGE_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){

        try{
            // 程序代码
            WareSkuEntity wareSku = wareSkuService.getById(id);

            return R.ok(REnum.REQUEST_GOODS_WARE_SUCCESS.getStatusCode(),
                    REnum.REQUEST_GOODS_WARE_SUCCESS.getStatusMsg())
                    .put("wareSku", wareSku);
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.REQUEST_GOODS_WARE_FAIL.getStatusCode(),
                    REnum.REQUEST_GOODS_WARE_FAIL.getStatusMsg());
        }finally{
            // 程序代码
        }

    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody WareSkuEntity wareSku){

        try{
            // 程序代码
            wareSkuService.save(wareSku);

            return R.ok(REnum.APPEND_GOODS_WARE_SUCCESS.getStatusCode(),
                            REnum.APPEND_GOODS_WARE_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.APPEND_GOODS_WARE_FAIL.getStatusCode(),
                    REnum.APPEND_GOODS_WARE_FAIL.getStatusMsg());
        }finally{
            // 程序代码
        }

    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody WareSkuEntity wareSku){

        try{
            // 程序代码
            wareSkuService.updateById(wareSku);

            return R.ok(REnum.ALERT_GOODS_WARE_SUCCESS.getStatusCode(),
                    REnum.ALERT_GOODS_WARE_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.ALERT_GOODS_WARE_FAIL.getStatusCode(),
                    REnum.ALERT_GOODS_WARE_FAIL.getStatusMsg());
        }finally{
            // 程序代码
        }

    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){

        try{
            // 程序代码
            wareSkuService.removeByIds(Arrays.asList(ids));

            return R.ok(REnum.DELETE_GOODS_WARE_SUCCESS.getStatusCode(),
                    REnum.DELETE_GOODS_WARE_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.DELETE_GOODS_WARE_FAIL.getStatusCode(),
                    REnum.DELETE_GOODS_WARE_FAIL.getStatusMsg());
        }finally{
            // 程序代码
        }

    }


    /**
     * 查询是否有库存
     * @param skuIds
     * @return
     */
    @PostMapping("/hasstock")
    public Map<Long, Boolean> getSkuHasStock(@RequestBody List<Long> skuIds){

        Map<Long, Boolean> skuHasStockToMap = new HashMap<>();

        try{
            // 程序代码
            skuHasStockToMap = wareSkuService.getSkuHasStock(skuIds);

            return skuHasStockToMap;
        }catch(Exception e){
            e.printStackTrace();
            // 程序代码

            return skuHasStockToMap;
        }finally{
            // 程序代码

        }
    }

}
