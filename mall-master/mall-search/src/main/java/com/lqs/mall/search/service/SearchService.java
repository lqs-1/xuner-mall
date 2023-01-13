package com.lqs.mall.search.service;

import com.lqs.mall.search.vo.SearchParamVo;
import com.lqs.mall.search.vo.SearchRespVo;

/**
 * @author 李奇凇
 * @moduleName SearchService
 * @date 2022/10/4 下午1:58
 * @do 搜索功能的service接口
 */
public interface SearchService {
    SearchRespVo search(SearchParamVo searchParamVo);
}
