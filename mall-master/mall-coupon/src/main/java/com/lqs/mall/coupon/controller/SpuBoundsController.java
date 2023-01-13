package com.lqs.mall.coupon.controller;

import com.lqs.mall.common.constant.REnum;
import com.lqs.mall.common.to.SpuBoundsTo;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.coupon.entity.SpuBoundsEntity;
import com.lqs.mall.coupon.service.SpuBoundsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 商品spu积分设置
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:55:55
 */
@RestController
@RequestMapping("coupon/spubounds")
public class SpuBoundsController {
    @Autowired
    private SpuBoundsService spuBoundsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = spuBoundsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		SpuBoundsEntity spuBounds = spuBoundsService.getById(id);

        return R.ok().put("spuBounds", spuBounds);
    }


    /**
     * 保存
     */
    @PostMapping("/save")
    // @RequiresPermissions("coupon:spubounds:save")
    public R spuBoundsSave(@RequestBody SpuBoundsTo spuBounds){
        try{
            // 程序代码
            SpuBoundsEntity spuBoundsEntity = new SpuBoundsEntity();
            BeanUtils.copyProperties(spuBounds, spuBoundsEntity);
            spuBoundsService.save(spuBoundsEntity);

            return R.ok(REnum.SPU_BOUNDS_SAVE_SUCCESS.getStatusCode(),
                    REnum.SPU_BOUNDS_SAVE_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.SPU_BOUNDS_SAVE_FAIL.getStatusCode(),
                    REnum.SPU_BOUNDS_SAVE_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }

    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SpuBoundsEntity spuBounds){
		spuBoundsService.updateById(spuBounds);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		spuBoundsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
