package com.lqs.mall.search.vo;

import com.lqs.mall.common.to.es.SkuEsModelTo;
import lombok.Data;

import java.util.List;

/**
 * @author 李奇凇
 * @moduleName SearchRespVo
 * @date 2022/10/4 下午2:04
 * @do 返回搜索的数据
 */

@Data
public class SearchRespVo {

    private List<SkuEsModelTo> products; // 查询到的所有的商品信息

    private Integer pageNum; // 当前页码

    private Long total; // 总记录数

    private Integer totalPages; // 总页码

    private List<BrandVo> brands; // 当前查询到的结果，涉及到的所有品牌

    private List<AttrVo> attrs; // 当前查询到的结果，涉及到的所有属性

    private List<CatalogVo> catalogs; // 当前查询到的结果，涉及到的所有分类

    private List<Integer> pageNavs; // 导航页吗码

    private List<NavVo> navs; // 面包屑导航

    @Data
    public static class NavVo{
        private String navName;
        private String navValue;
        private String linkString;
    }


    @Data
    public static class BrandVo{

        private Long brandId;

        private String brandName;

        private String brandImg;

    }

    @Data
    public static class AttrVo{

        private Long attrId;

        private String attrName;

        private List<String> attrValue;

    }

    @Data
    public static class CatalogVo{

        private Long catalogId;

        private String catalogName;

    }


}
