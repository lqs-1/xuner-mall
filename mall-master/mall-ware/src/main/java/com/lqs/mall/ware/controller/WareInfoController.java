package com.lqs.mall.ware.controller;

import com.lqs.mall.common.constant.REnum;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.ware.entity.WareInfoEntity;
import com.lqs.mall.ware.service.WareInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 仓库信息
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 22:00:14
 */
@RestController
@RequestMapping("ware/wareinfo")
public class WareInfoController {
    @Autowired
    private WareInfoService wareInfoService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        try{
            // 程序代码
            PageUtils wareListByPage = wareInfoService.queryPage(params);

            return R.ok(REnum.REQUEST_WARE_LIST_BY_PAGE_SUCCESS.getStatusCode(),
                    REnum.REQUEST_WARE_LIST_BY_PAGE_SUCCESS.getStatusMsg())
                    .put("wareList", wareListByPage);
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.REQUEST_WARE_LIST_BY_PAGE_FAIL.getStatusCode(),
                    REnum.REQUEST_WARE_LIST_BY_PAGE_FAIL.getStatusMsg());
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
            WareInfoEntity wareInfo = wareInfoService.getById(id);

            return R.ok(REnum.REQUEST_WARE_INFO_BY_ID_SUCCESS.getStatusCode(),
                            REnum.REQUEST_WARE_INFO_BY_ID_SUCCESS.getStatusMsg())
                    .put("wareInfo", wareInfo);
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.REQUEST_WARE_INFO_BY_ID_FAIL.getStatusCode(),
                    REnum.REQUEST_WARE_INFO_BY_ID_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }

    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody WareInfoEntity wareInfo){

        try{
            // 程序代码
            wareInfoService.save(wareInfo);

            return R.ok(REnum.APPEND_WARE_SUCCESS.getStatusCode(),
                    REnum.APPEND_WARE_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.APPEND_WARE_FAIL.getStatusCode(),
                    REnum.APPEND_WARE_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }

    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody WareInfoEntity wareInfo){

        try{
            // 程序代码
            wareInfoService.updateById(wareInfo);

            return R.ok(REnum.ALERT_WARE_SUCCESS.getStatusCode(),
                    REnum.ALERT_WARE_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.ALERT_WARE_FAIL.getStatusCode(),
                    REnum.ALERT_WARE_FAIL.getStatusMsg());
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
            wareInfoService.removeByIds(Arrays.asList(ids));

            return R.ok(REnum.DELETE_WARE_SUCCESS.getStatusCode(),
                    REnum.DELETE_WARE_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.DELETE_WARE_FAIL.getStatusCode(),
                    REnum.DELETE_WARE_FAIL.getStatusMsg());
        }finally{
            // 程序代码
        }

    }

}
