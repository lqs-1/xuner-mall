package com.lqs.mall.search.controller;

import com.lqs.mall.search.service.SearchService;
import com.lqs.mall.search.vo.SearchParamVo;
import com.lqs.mall.search.vo.SearchRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 李奇凇
 * @moduleName SearchController
 * @date 2022/10/4 下午1:23
 * @do Search搜索的controller
 */

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;


    /**
     * 商品搜索
     * @param searchParamVo
     * @return
     */
    @GetMapping({"", "list", "list.html"})
    public String search(SearchParamVo searchParamVo, Model model, HttpServletRequest request){

        SearchRespVo searchResult = null;
        try{
            // 程序代码
            searchParamVo.set_urlQueryString(request.getQueryString());

            searchResult = searchService.search(searchParamVo);
            // System.out.println(searchResult);
            model.addAttribute("searchResult", searchResult);
        }catch(Exception e){
            e.printStackTrace();
            // 程序代码

        }finally{
            // 程序代码

        }

        return "index";
    }


}
