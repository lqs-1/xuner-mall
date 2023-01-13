package com.lqs.mall.search.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 李奇凇
 * @moduleName SearchParamVo
 * @date 2022/10/4 下午1:46
 * @do 接收页面的搜索参数,页面可能传递的所有可能的查询条件
 */

@Data
public class SearchParamVo {
    /**
     * keyword=xx&catalog3Id=xx&sort=xx&hasStock=0/1&brandId=xx&brandId=xx&attrs=attrId_value:value&attrs=attrId_value:value&skuPrice=xx&pageNum=xx
     */

    private String keyword; // 页面传递的全文匹配关键字

    private Long catalog3Id; // 三级分类id

    private String sort; // 排序 sort=saleCount_asc/desc sort=skuPrice_asc/desc sort=hotScore_asc/desc

    private Integer hasStock; // 是否只显示有货

    private String skuPrice; // 价格区间 _500,1_500, 500_

    private List<Long> brandId; // 品牌id,允许多选

    private List<String> attrs; // 按照属性筛选

    private Integer pageNum = 1; // 页码

    private String _urlQueryString; // 获取当前请求链接中的查询字符串
}
