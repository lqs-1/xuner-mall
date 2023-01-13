package com.lqs.mall.ware.controller;

import com.lqs.mall.common.constant.REnum;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.ware.entity.PurchaseEntity;
import com.lqs.mall.ware.service.PurchaseService;
import com.lqs.mall.ware.vo.MergeVo;
import com.lqs.mall.ware.vo.PurchaseFinishVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;



/**
 * 采购信息
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 22:00:14
 */
@RestController
@RequestMapping("ware/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){

        try{
            // 程序代码
            PageUtils purchaseList = purchaseService.queryPage(params);

            return R.ok(REnum.REQUEST_PURCHASE_LIST_BY_PAGE_SUCCESS.getStatusCode(),
                            REnum.REQUEST_PURCHASE_LIST_BY_PAGE_SUCCESS.getStatusMsg())
                    .put("purchaseList", purchaseList);
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.REQUEST_PURCHASE_LIST_BY_PAGE_FAIL.getStatusCode(),
                            REnum.REQUEST_PURCHASE_LIST_BY_PAGE_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }

    }

    // ware/purchase/unreceive/
    // 查询出已经分配了还没采购的但还没领取的获取还没分配的采购单，提供采购需求合并采购使用
    // 查询出还没有被领取的采购单，刚创建的和已经分配的（分配是分配到人头）
    @GetMapping(value = "unreceive/list")
    public R getUnreceive(@RequestParam Map<String, Object> params){

        try{
            // 程序代码
            List<PurchaseEntity> purchaseList = purchaseService.queryUnreceive(params);

            return R.ok(REnum.REQUEST_UNRECEIVE_PURCHASE_LIST_SUCCESS.getStatusCode(),
                            REnum.REQUEST_UNRECEIVE_PURCHASE_LIST_SUCCESS.getStatusMsg())
                    .put("purchaseList", purchaseList);
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.REQUEST_UNRECEIVE_PURCHASE_LIST_FAIL.getStatusCode(),
                            REnum.REQUEST_UNRECEIVE_PURCHASE_LIST_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }

    }


    /**
     * 合并采购单
     * @param mergeVo
     * @return
     */
    @PostMapping("merge")
    public R merge(@RequestBody MergeVo mergeVo){
        try{
            // 程序代码
            purchaseService.mergePurchase(mergeVo);

            return R.ok(REnum.PUARCHASE_NEED_MERGE_SUCCESS.getStatusCode(),
                    REnum.PUARCHASE_NEED_MERGE_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.PUARCHASE_NEED_MERGE_FAIL.getStatusCode(),
                    REnum.PUARCHASE_NEED_MERGE_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }
    }


    /**
     * 领取采购单,只有状态是已经分配的才可以领取
     * @param purchaseIds
     * @return
     */
    @PostMapping("received")
    public R received(@RequestBody List<Long> purchaseIds){

        try{
            // 程序代码
            purchaseService.receivePurchase(purchaseIds);

            return R.ok(REnum.PUARCHASE_RECEIVED_SUCCESS.getStatusCode(),
                    REnum.PUARCHASE_RECEIVED_SUCCESS.getStatusMsg());
        }catch(Exception e){
            e.printStackTrace();
            // 程序代码

            return R.error(REnum.PUARCHASE_RECEIVED_FAIL.getStatusCode(),
                    REnum.PUARCHASE_RECEIVED_FAIL.getStatusMsg());
        }finally{
            // 程序代码

        }

    }

    @PostMapping("finishPurchase")
    public R finishPurchase(@Valid @RequestBody PurchaseFinishVo purchaseFinishVo){

        try{
            // 程序代码
            purchaseService.finishPurchase(purchaseFinishVo);

            return R.ok(REnum.SUBMIT_PUARCHASE_SUCCESS.getStatusCode(),
                    REnum.SUBMIT_PUARCHASE_SUCCESS.getStatusMsg());
        }catch(Exception e){
            e.printStackTrace();
            // 程序代码

            return R.error(REnum.SUBMIT_PUARCHASE_FAIL.getStatusCode(),
                    REnum.SUBMIT_PUARCHASE_FAIL.getStatusMsg());
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
            PurchaseEntity purchase = purchaseService.getById(id);

            return R.ok(REnum.REQUEST_PURCHASE_SUCCESS.getStatusCode(),
                    REnum.REQUEST_PURCHASE_SUCCESS.getStatusMsg())
                    .put("purchase", purchase);
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.REQUEST_PURCHASE_FAIL.getStatusCode(),
                    REnum.REQUEST_PURCHASE_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }

    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody PurchaseEntity purchase){

        try{
            // 程序代码
            purchase.setUpdateTime(new Date());
            purchase.setCreateTime(new Date());
            purchaseService.save(purchase);

            return R.ok(REnum.APPEND_PURCHASE_SUCCESS.getStatusCode(),
                    REnum.APPEND_PURCHASE_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.APPEND_PURCHASE_FAIL.getStatusCode(),
                    REnum.APPEND_PURCHASE_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }

    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody PurchaseEntity purchase){

        try{
            // 程序代码
            purchase.setUpdateTime(new Date());
            purchaseService.updateById(purchase);

            return R.ok(REnum.ALERT_PURCHASE_SUCCESS.getStatusCode(),
                    REnum.ALERT_PURCHASE_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.ALERT_PURCHASE_FAIL.getStatusCode(),
                    REnum.ALERT_PURCHASE_FAIL.getStatusMsg());
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
            purchaseService.removeByIds(Arrays.asList(ids));

            return R.ok(REnum.DELETE_PURCHASE_SUCCESS.getStatusCode(),
                    REnum.DELETE_PURCHASE_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.DELETE_PURCHASE_FAIL.getStatusCode(),
                    REnum.DELETE_PURCHASE_FAIL.getStatusMsg());
        }finally{
            // 程序代码
        }

    }

}
