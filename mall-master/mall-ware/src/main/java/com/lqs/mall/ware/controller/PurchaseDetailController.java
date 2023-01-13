package com.lqs.mall.ware.controller;

import com.lqs.mall.common.constant.REnum;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.ware.entity.PurchaseDetailEntity;
import com.lqs.mall.ware.service.PurchaseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 22:00:14
 */
@RestController
@RequestMapping("ware/purchasedetail")
public class PurchaseDetailController {
    @Autowired
    private PurchaseDetailService purchaseDetailService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){

        try{
            // 程序代码
            PageUtils purchaseNeedListByPage = purchaseDetailService.queryPage(params);

            return R.ok(REnum.REQUEST_PURCHASE_NEED_LIST_BY_PAGE_SUCCESS.getStatusCode(),
                    REnum.REQUEST_PURCHASE_NEED_LIST_BY_PAGE_SUCCESS.getStatusMsg())
                    .put("purchaseNeedList", purchaseNeedListByPage);
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.REQUEST_PURCHASE_NEED_LIST_BY_PAGE_FAIL.getStatusCode(),
                    REnum.REQUEST_PURCHASE_NEED_LIST_BY_PAGE_FAIL.getStatusMsg());
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
            PurchaseDetailEntity purchaseNeed = purchaseDetailService.getById(id);

            return R.ok(REnum.REQUEST_PURCHASE_NEED_SUCCESS.getStatusCode(),
                            REnum.REQUEST_PURCHASE_NEED_SUCCESS.getStatusMsg())
                    .put("purchaseNeed", purchaseNeed);
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.REQUEST_PURCHASE_NEED_FAIL.getStatusCode(),
                    REnum.REQUEST_PURCHASE_NEED_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }

    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody PurchaseDetailEntity purchaseDetail){

        try{
            // 程序代码
            purchaseDetailService.save(purchaseDetail);

            return R.ok(REnum.APPEND_PURCHASE_NEED_SUCCESS.getStatusCode(),
                    REnum.APPEND_PURCHASE_NEED_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.APPEND_PURCHASE_NEED_FAIL.getStatusCode(),
                    REnum.APPEND_PURCHASE_NEED_FAIL.getStatusMsg());
        }finally{
            // 程序代码
        }

    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody PurchaseDetailEntity purchaseDetail){

        try{
            // 程序代码
            purchaseDetailService.updateById(purchaseDetail);

            return R.ok(REnum.ALERT_PURCHASE_NEED_SUCCESS.getStatusCode(),
                    REnum.ALERT_PURCHASE_NEED_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.ALERT_PURCHASE_NEED_FAIL.getStatusCode(),
                    REnum.ALERT_PURCHASE_NEED_FAIL.getStatusMsg());
        }finally{
            // 程序代码
        }

    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){

        try{
            // 程序代码
            purchaseDetailService.removeByIds(Arrays.asList(ids));

            return R.ok(REnum.DELETE_PURCHASE_NEED_SUCCESS.getStatusCode(),
                    REnum.DELETE_PURCHASE_NEED_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.DELETE_PURCHASE_NEED_FAIL.getStatusCode(),
                    REnum.DELETE_PURCHASE_NEED_FAIL.getStatusMsg());
        }finally{
            // 程序代码
        }

    }

}
