package com.lqs.mall.product.app;

import com.lqs.mall.common.constant.REnum;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.product.entity.SpuInfoEntity;
import com.lqs.mall.product.service.SpuInfoService;
import com.lqs.mall.product.vo.spu.SpuRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * spu信息
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:59:36
 */
@RestController
@RequestMapping("product/spuinfo")
public class SpuInfoController {
    @Autowired
    private SpuInfoService spuInfoService;


    /**
     * 根据skuId查询出对应得spu信息
     * @param skuId
     * @return
     */
    @GetMapping("skuId/{skuId}")
    public R getSpuInfoBySkuId(@PathVariable Long skuId){

        try{
            System.out.println(skuId);
            // 程序代码
            SpuInfoEntity spuInfo = spuInfoService.findSpuInfoBySkuId(skuId);

            System.out.println(spuInfo);
            return R.ok(REnum.REQUEST_SPU_INFO_BY_SKUID_SUCCESS.getStatusCode(),
                    REnum.REQUEST_SPU_INFO_BY_SKUID_SUCCESS.getStatusMsg())
                    .put("spu", spuInfo);
        }catch(Exception e){
            e.printStackTrace();
            // 程序代码

            return R.error(REnum.REQUEST_SPU_INFO_BY_SKUID_FAIL.getStatusCode(),
                            REnum.REQUEST_SPU_INFO_BY_SKUID_FAIL.getStatusMsg());
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
            PageUtils spuListByPage = spuInfoService.queryPage(params);

            return R.ok(REnum.REQUEST_SPU_LIST_BY_PAGE_SUCCESS.getStatusCode(),
                    REnum.REQUEST_SPU_LIST_BY_PAGE_SUCCESS.getStatusMsg())
                    .put("spuList", spuListByPage);
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.REQUEST_SPU_LIST_BY_PAGE_FAIL.getStatusCode(),
                            REnum.REQUEST_SPU_LIST_BY_PAGE_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }

    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		SpuInfoEntity spuInfo = spuInfoService.getById(id);

        return R.ok().put("spuInfo", spuInfo);
    }

    /**
     * 保存添加的商品.多表添加,spu添加,细节到添加sku
     */
    @PostMapping(value = "save")
    public R spuinfoSave(@RequestBody SpuRespVo spuRespVo){
        // System.out.println(spuRespVo);
        try{
            // 程序代码
            spuInfoService.saveSpuInfo(spuRespVo);

            return R.ok(REnum.POST_AN_ITEM_SPU_SUCCESS.getStatusCode(),
                    REnum.POST_AN_ITEM_SPU_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.POST_AN_ITEM_SPU_FAIL.getStatusCode(),
                    REnum.POST_AN_ITEM_SPU_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }

    }


    // /product/spuinfo/" + id + "/up"

    /**
     * spu上架，商品上架
     * @param spuId
     * @return
     */
    @PostMapping("{spuId}/up")
    public R spuUp(@PathVariable Long spuId){

        try{
            // 程序代码
            spuInfoService.spuUp(spuId);

            return R.ok(REnum.UP_SPU_SUCCESS.getStatusCode(),
                    REnum.UP_SPU_SUCCESS.getStatusMsg());
        }catch(Exception e){
            e.printStackTrace();
            // 程序代码

            return R.error(REnum.UP_SPU_FAIL.getStatusCode(),
                    REnum.UP_SPU_FAIL.getStatusMsg());
        }finally{
            // 程序代码

        }

    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SpuInfoEntity spuInfo){
		spuInfoService.updateById(spuInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		spuInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
