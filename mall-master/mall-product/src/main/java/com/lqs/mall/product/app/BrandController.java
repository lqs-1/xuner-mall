package com.lqs.mall.product.app;

import com.lqs.mall.common.constant.REnum;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.common.valid.AddGroup;
import com.lqs.mall.common.valid.UpdateGroup;
import com.lqs.mall.product.entity.BrandEntity;
import com.lqs.mall.product.feign.ThirdPartFeignClientService;
import com.lqs.mall.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;



/**
 * 品牌
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:59:36
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @Autowired
    private ThirdPartFeignClientService thirdPartFeignClientService;

    /**
     * 根据品牌id查询品牌名
     * @param brandId
     * @return
     */
    @GetMapping("brandName/{brandId}")
    public R getBrandNameByBrandId(@PathVariable Long brandId){

        try{
            // 程序代码

            String brandName = brandService.findBrandNameByBrandId(brandId);

            return R.ok(REnum.REQUEST_BRANDNAME_BY_BRANDID_SUCCESS.getStatusCode(),
                    REnum.REQUEST_BRANDNAME_BY_BRANDID_SUCCESS.getStatusMsg())
                    .put(brandName);
        }catch(Exception e){
            e.printStackTrace();
            // 程序代码

            return R.error(REnum.REQUEST_BRANDNAME_BY_BRANDID_FAIL.getStatusCode(),
                    REnum.REQUEST_BRANDNAME_BY_BRANDID_FAIL.getStatusMsg());
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
            PageUtils page = brandService.queryPage(params);

            return R.ok(REnum.REQUEST_BRAND_LIST_SUCCESS.getStatusCode(),
                        REnum.REQUEST_BRAND_LIST_SUCCESS.getStatusMsg())
                            .put("page", page);
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.REQUEST_BRAND_LIST_FAIL.getStatusCode(),
                            REnum.REQUEST_BRAND_LIST_FAIL.getStatusMsg());

        }finally{
           // 程序代码
        }
    }


    /**
     * brandLogo upload
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping(value = "brandUpload")
    public R brandUpload(@RequestBody MultipartFile file) throws IOException {
        R brand = thirdPartFeignClientService.brandLogoUpload(file);
//        System.out.println(file.getOriginalFilename());
        return brand;
    }


    /**
     * 信息
     */
    @GetMapping("/info/{brandId}")
    public R info(@PathVariable("brandId") Long brandId){
        try{
            // 程序代码
            BrandEntity brand = brandService.getById(brandId);

            return R.ok(REnum.REQUEST_BRAND_SUCCESS.getStatusCode(),
                    REnum.REQUEST_BRAND_SUCCESS.getStatusMsg())
                    .put("brand", brand);
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.REQUEST_BRAND_FAIL.getStatusCode(),
                    REnum.REQUEST_BRAND_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@Validated(value = {AddGroup.class}) @RequestBody BrandEntity brand/*, BindingResult bindingResult*/){

//        if (bindingResult.hasErrors()){
//            HashMap<String, String> stringStringHashMap = new HashMap<>();
//
//            for (FieldError fieldError : bindingResult.getFieldErrors()) {
//                String field = fieldError.getField();
//                String defaultMessage = fieldError.getDefaultMessage();
//                stringStringHashMap.put(field, defaultMessage);
//            }
//
//            return R.error().put("data", stringStringHashMap);
//        }




        try{
            // 程序代码
            brandService.save(brand);

            return R.ok(REnum.BRAND_APPEND_SUCCESS.getStatusCode(),
                    REnum.BRAND_APPEND_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.BRAND_APPEND_FAIL.getStatusCode(),
                    REnum.BRAND_APPEND_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }

    }

    /**
     * 修改,更新细节
     */
    @PostMapping("/update")
    public R update(@Validated(value = {UpdateGroup.class}) @RequestBody BrandEntity brand){

        try{
            // 程序代码
            brandService.updateDetailById(brand);

            return R.ok(REnum.BRAND_EDIT_SUCCESS.getStatusCode(),
                    REnum.BRAND_EDIT_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();
            return R.error(REnum.BRAND_EDIT_FAIL.getStatusCode(),
                    REnum.BRAND_EDIT_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }

    }

    /**
     * 删除,被引用不允许删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] brandIds){
        try{
            // 程序代码
            brandService.removeDetailByIds(Arrays.asList(brandIds));

            return R.ok(REnum.BRAND_DELETE_SUCCESS.getStatusCode(),
                    REnum.BRAND_DELETE_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.BRAND_DELETE_FAIL.getStatusCode(),
                    REnum.BRAND_DELETE_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }

    }

}
