package com.lqs.mall.product.web;

import com.lqs.mall.product.service.SkuInfoService;
import com.lqs.mall.product.vo.item.SkuItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 李奇凇
 * @moduleName itemController
 * @date 2022/10/16 上午9:31
 * @do 商品详情页Controller
 */

@Controller
public class itemController {

    @Autowired
    private SkuInfoService skuInfoService;

    /**
     * 返回商品详情页面，查出详情页面数据返回给模板
     * @param skuId 商品的id
     * @return 返回item.html视图
     */
    @GetMapping("{skuId}")
    public String item(@PathVariable("skuId") Long skuId, Model model){
        SkuItemVo skuItem = null;
        try{
            // 程序代码
            skuItem = skuInfoService.item(skuId);
            System.out.println(skuItem);
            model.addAttribute("skuItem", skuItem);
        }catch(Exception e){
            e.printStackTrace();
            // 程序代码
            skuItem = null;
        }finally{
            // 程序代码
            model.addAttribute("skuItem", skuItem);
        }
        return "item";
    }

}
