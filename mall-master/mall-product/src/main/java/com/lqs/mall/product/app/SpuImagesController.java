package com.lqs.mall.product.app;

import com.lqs.mall.common.utils.R;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.product.entity.SpuImagesEntity;
import com.lqs.mall.product.feign.ThirdPartFeignClientService;
import com.lqs.mall.product.service.SpuImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;



/**
 * spu图片
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:59:36
 */
@RestController
@RequestMapping("product/spuimages")
public class SpuImagesController {
    @Autowired
    private SpuImagesService spuImagesService;


    @Autowired
    private ThirdPartFeignClientService thirdPartFeignClientService;


    /**
     * 上传spu下面的图集,在sku添加的时候还可以继续添加
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("spuImagesUpload")
    public R spuImagesUpload(@RequestBody MultipartFile file) throws IOException {
        R spuImages = thirdPartFeignClientService.spuImagesUpload(file);
//        System.out.println(file.getOriginalFilename());
        return spuImages;
    }



    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = spuImagesService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		SpuImagesEntity spuImages = spuImagesService.getById(id);

        return R.ok().put("spuImages", spuImages);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody SpuImagesEntity spuImages){
		spuImagesService.save(spuImages);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SpuImagesEntity spuImages){
		spuImagesService.updateById(spuImages);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		spuImagesService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
