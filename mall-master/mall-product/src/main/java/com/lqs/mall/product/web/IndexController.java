package com.lqs.mall.product.web;

import com.lqs.mall.product.entity.CategoryEntity;
import com.lqs.mall.product.service.CategoryService;
import com.lqs.mall.product.vo.Catelog2WebVo;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author 李奇凇
 * @moduleName IndexController
 * @date 2022/9/29 下午5:58
 * @do 网站首页controller
 */

@Controller
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedissonClient redisson;

    /**
     * 查询页面需要的一级分类列表
     * @param model
     * @return
     */
    @GetMapping({"", "index.html", "index"})
    public String indexPage(Model model){


        List<CategoryEntity> catelogLevelOneList = null;

        try{
            // 程序代码
            catelogLevelOneList = categoryService.findCatelogLevelOneList();

        }catch(Exception e){
            e.printStackTrace();
            // 程序代码

        }finally{
            // 程序代码
        }
        model.addAttribute("catelogLevelOneList", catelogLevelOneList);

        return "index";
    }


    /**
     * 查询前端需要的catalog三级分类的json数据
     * @return
     */
    @GetMapping("index/catalog.json")
    @ResponseBody
    public Map<String, List<Catelog2WebVo>> getCatalogJson(){

        Map<String, List<Catelog2WebVo>> catalogJson = null;

        try{
            // 程序代码
            catalogJson = categoryService.getCatalogJson();

        }catch(Exception e){
            e.printStackTrace();
            // 程序代码

        }finally{
            // 程序代码
        }

        return catalogJson;
    }


}
