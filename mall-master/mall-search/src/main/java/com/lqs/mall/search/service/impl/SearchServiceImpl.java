package com.lqs.mall.search.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.lqs.mall.common.constant.Constant;
import com.lqs.mall.common.to.es.SkuEsModelTo;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.search.config.MallElasticSearchConfig;
import com.lqs.mall.search.feign.ProductFeignClientService;
import com.lqs.mall.search.service.SearchService;
import com.lqs.mall.search.vo.SearchParamVo;
import com.lqs.mall.search.vo.SearchRespVo;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 李奇凇
 * @moduleName SearchServiceImpl
 * @date 2022/10/4 下午1:59
 * @do 搜索服务接口的实现类
 */
@Service("SearchService")
public class SearchServiceImpl implements SearchService {

    @Autowired
    private RestHighLevelClient elasticSearchClient;

    @Autowired
    private ProductFeignClientService productFeignClientService;

    /**
     * 搜索商品
     * @param searchParamVo
     */
    @Override
    public SearchRespVo search(SearchParamVo searchParamVo) {

        SearchRespVo searchResp = null;
        // 准备检索的请求对象
        SearchRequest searchRequest = buildSearchRequest(searchParamVo);
        // 拼查询条件

        try {

            // 执行检索请求
            SearchResponse response = elasticSearchClient.search(searchRequest, MallElasticSearchConfig.COMMON_OPTIONS);
            // 分析封装数据
            searchResp = buildSearchResponse(response, searchParamVo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return searchResp;
    }

    /**
     * 准备检索请求
     * 模糊匹配，过滤（属性，分类，品牌，价格区间，库存），排序，分页，高亮，聚合分析
     * @return
     */
    private SearchRequest buildSearchRequest(SearchParamVo searchParam){

        // 用于构建DSL语句
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // 开始构建DSL语句
        // 模糊匹配，过滤（属性，分类，品牌，价格区间，库存）
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        // must
        if (!StringUtils.isEmpty(searchParam.getKeyword())){
            queryBuilder.must(QueryBuilders.matchQuery("skuTitle", searchParam.getKeyword()));
        }
        // filter catalogId
        if (searchParam.getCatalog3Id() != null){
            queryBuilder.filter(QueryBuilders.termQuery("catalogId", searchParam.getCatalog3Id()));
        }
        // filter brandId
        if (searchParam.getBrandId() != null){
            queryBuilder.filter(QueryBuilders.termsQuery("brandId", searchParam.getBrandId()));
        }
        // filter skuPrice
        if (!StringUtils.isEmpty(searchParam.getSkuPrice())){

            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("skuPrice");

            String[] price = searchParam.getSkuPrice().split("_");

            if (price.length == 2){

                rangeQueryBuilder.gte(price[0]).lte(price[1]);

            }else if (price.length == 1){

                if (searchParam.getSkuPrice().startsWith("_")){
                    rangeQueryBuilder.lte(price[0]);
                }

                if (searchParam.getSkuPrice().endsWith("_")){
                    rangeQueryBuilder.gte(price[0]);
                }

            }
            queryBuilder.filter(rangeQueryBuilder);
        }
        // filter hasStock
        if (searchParam.getHasStock() != null){
            queryBuilder.filter(QueryBuilders.termQuery("hasStock", searchParam.getHasStock() == 1));
        }
        // filter attrs
        if (searchParam.getAttrs() != null){

            // attrs=attrId_value:value&attrs=attrId_value:value
            for (String attrString : searchParam.getAttrs()) {
                BoolQueryBuilder nestedBoolQuery = QueryBuilders.boolQuery();
                String[] attrIdAndValue = attrString.split("_");
                String attrId = attrIdAndValue[0]; // 获取属性的id
                String[] attrValues = attrIdAndValue[1].split(":"); // 获取这个属性的属性值
                nestedBoolQuery.must(QueryBuilders.termQuery("attrs.attrId", attrId));
                nestedBoolQuery.must(QueryBuilders.termsQuery("attrs.attrValue", attrValues));
                // 没一个属性都要生成一个nested查询
                NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("attrs", nestedBoolQuery, ScoreMode.None);// ScoreMode是否参与评分
                queryBuilder.filter(nestedQuery);
            }
        }

        // 把以前的所有条件放进去
        sourceBuilder.query(queryBuilder);
        // 排序，分页，高亮
        // 排序sort=hostScore_desc/asc
        String sortStr = searchParam.getSort();

        if (!StringUtils.isEmpty(sortStr)){
            // 分割
            String[] sortParam = sortStr.split("_");

            SortOrder order = sortParam[1].equalsIgnoreCase("asc") ? SortOrder.ASC: SortOrder.DESC;
            sourceBuilder.sort(sortParam[0], order);
        }
        // 分页
        sourceBuilder.from((searchParam.getPageNum() - 1) * Constant.PRODUCT_PAGESIZE);
        sourceBuilder.size(Constant.PRODUCT_PAGESIZE);
        // 高亮
        if (!StringUtils.isEmpty(searchParam.getKeyword())){
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("skuTitle");
            highlightBuilder.preTags("<b style='color:red'>");
            highlightBuilder.postTags("</b>");
            sourceBuilder.highlighter(highlightBuilder);
        }


        // 聚合分析
        // 品牌聚合
        TermsAggregationBuilder brandAgg = AggregationBuilders.terms("brand_agg").field("brandId").size(10);
        brandAgg.subAggregation(AggregationBuilders.terms("brandName_agg").field("brandName").size(1));
        brandAgg.subAggregation(AggregationBuilders.terms("brandImg_agg").field("brandImg").size(1));
        sourceBuilder.aggregation(brandAgg);
        // 分类聚合
        TermsAggregationBuilder catalogAgg = AggregationBuilders.terms("catalog_agg").field("catalogId").size(10);
        catalogAgg.subAggregation(AggregationBuilders.terms("catalog_name_agg").field("catalogName").size(1));
        sourceBuilder.aggregation(catalogAgg);
        // 属性聚合
        NestedAggregationBuilder attrNestedAgg = AggregationBuilders.nested("attr_agg", "attrs");
        TermsAggregationBuilder attrIdAgg = AggregationBuilders.terms("attr_id_agg").field("attrs.attrId").size(10);
        attrIdAgg.subAggregation(AggregationBuilders.terms("attr_name_agg").field("attrs.attrName").size(1));
        attrIdAgg.subAggregation(AggregationBuilders.terms("attr_value_agg").field("attrs.attrValue").size(10));
        attrNestedAgg.subAggregation(attrIdAgg);
        sourceBuilder.aggregation(attrNestedAgg);


        // 创建搜索查询对象
        SearchRequest searchRequest = new SearchRequest(new String[]{Constant.PRODUCT_INDEX}, sourceBuilder);

        return searchRequest;
    }

    /**
     * 构建结果数据
     * @return
     */
    private SearchRespVo buildSearchResponse(SearchResponse response, SearchParamVo searchParam){

        SearchRespVo searchResp = new SearchRespVo();

        // 获取命中的记录
        SearchHit[] hits = response.getHits().getHits();

        // 要封装的商品列表
        List<SkuEsModelTo> skuEsModelToList = new ArrayList<>();

        if (hits != null && hits.length > 0){
            // 遍历命中数组
            for (SearchHit hit : hits) {
                // 获取具体数据（String）
                String sku = hit.getSourceAsString();
                // 将String ——> Object
                SkuEsModelTo esModelTo = JSON.parseObject(sku, SkuEsModelTo.class);
                if (!StringUtils.isEmpty(searchParam.getKeyword())){
                    HighlightField highLightSkuTitleObject = hit.getHighlightFields().get("skuTitle");
                    String highLightSkuTitle = highLightSkuTitleObject.getFragments()[0].string();
                    esModelTo.setSkuTitle(highLightSkuTitle);
                }
                // 给商品列表添加数据
                skuEsModelToList.add(esModelTo);
            }
        }

        // 获取总记录数
        long total = response.getHits().getTotalHits().value;

        // 获取所有的聚合数据
        Aggregations aggregations = response.getAggregations();

        // 获取分类聚合数据
        // 要封装的分类信息列表
        List<SearchRespVo.CatalogVo> catalogVoList = new ArrayList<>();
        ParsedLongTerms catalogAgg = aggregations.get("catalog_agg");
        // 获取分类聚合的Buckets
        List<? extends Terms.Bucket> catalogAggBuckets = catalogAgg.getBuckets();
        // 遍历分类聚合的Buckets
        for (Terms.Bucket catalogAggBucket : catalogAggBuckets) {
            // 创建分类对象
            SearchRespVo.CatalogVo catalogVo = new SearchRespVo.CatalogVo();
            // 获取分类聚合的key
            String catalogIdStr = catalogAggBucket.getKeyAsString();
            // 获取分类聚合的子聚合
            ParsedStringTerms catalogNameAgg = catalogAggBucket.getAggregations().get("catalog_name_agg");
            List<? extends Terms.Bucket> catalogNameAggBuckets = catalogNameAgg.getBuckets();
            // 获取分类名字
            String catalogName = catalogNameAggBuckets.get(0).getKeyAsString();
            // 封装数据
            catalogVo.setCatalogId(Long.parseLong(catalogIdStr));
            catalogVo.setCatalogName(catalogName);

            catalogVoList.add(catalogVo);
        }

        // 获取品牌聚合数据
        // 要封装的品牌信息列表
        List<SearchRespVo.BrandVo> brandVoList = new ArrayList<>();
        ParsedLongTerms brandAgg = aggregations.get("brand_agg");
        for (Terms.Bucket bucket : brandAgg.getBuckets()) {
            SearchRespVo.BrandVo brandVo = new SearchRespVo.BrandVo();
            // 获取品牌id
            long brandId = bucket.getKeyAsNumber().longValue();
            // 获取品牌的名字
            ParsedStringTerms brandNameAgg = bucket.getAggregations().get("brandName_agg");
            String brandName = brandNameAgg.getBuckets().get(0).getKeyAsString();
            // 获取品牌的图片
            ParsedStringTerms brandImgAgg = bucket.getAggregations().get("brandImg_agg");
            String brandImg = brandImgAgg.getBuckets().get(0).getKeyAsString();

            brandVo.setBrandId(brandId);
            brandVo.setBrandImg(brandImg);
            brandVo.setBrandName(brandName);
            brandVoList.add(brandVo);
        }

        // 获取属性聚合数据
        // 要封装的属性信息列表
        List<SearchRespVo.AttrVo> attrVoList = new ArrayList<>();

        ParsedNested attrAgg = aggregations.get("attr_agg");
        ParsedLongTerms attrIdAgg = attrAgg.getAggregations().get("attr_id_agg");
        for (Terms.Bucket bucket : attrIdAgg.getBuckets()) {
            SearchRespVo.AttrVo attrVo = new SearchRespVo.AttrVo();
            // 获取属性id
            long attrId = bucket.getKeyAsNumber().longValue();
            // 获取属性名字
            ParsedStringTerms attrNameAgg = bucket.getAggregations().get("attr_name_agg");
            String attrName = attrNameAgg.getBuckets().get(0).getKeyAsString();
            // 获取属性值
            ParsedStringTerms attrValueAgg = bucket.getAggregations().get("attr_value_agg");
            List<String> attrValueList = attrValueAgg.getBuckets().stream().map(item -> item.getKeyAsString()).collect(Collectors.toList());

            attrVo.setAttrId(attrId);
            attrVo.setAttrName(attrName);
            attrVo.setAttrValue(attrValueList);

            attrVoList.add(attrVo);
        }


        // 封装所有商品
        searchResp.setProducts(skuEsModelToList);

        // 封装当前商品的所有属性信息
        searchResp.setAttrs(attrVoList);

        // 封装当前商品的所有品牌信息
        searchResp.setBrands(brandVoList);

        // 封装当前商品的所有分类信息
        searchResp.setCatalogs(catalogVoList);

        // 封装分页信息
        searchResp.setPageNum(searchParam.getPageNum());
        searchResp.setTotalPages((int) total % Constant.PRODUCT_PAGESIZE == 0 ? (int) total / Constant.PRODUCT_PAGESIZE: ((int)total / Constant.PRODUCT_PAGESIZE) + 1);
        searchResp.setTotal(total);

        /**
         *只显示5个页码
         */
        List<Integer> pageNavs = new ArrayList<>();
        // 如果总页码小于等于5，全部显示
        if (searchResp.getTotalPages() <= Constant.SEARCH_PAGE_MARK_SIZE){
            for (Integer i = 1; i <= searchResp.getTotalPages(); ++i) {
                pageNavs.add(i);
            }
        }else if (searchParam.getPageNum() < 3){ // 如果是前三页，显示1-5
            for (Integer i = 1; i <= Constant.SEARCH_PAGE_MARK_SIZE; ++i) {
                pageNavs.add(i);
            }
        } else if (searchResp.getTotalPages() - searchResp.getPageNum() <= 2) { // 如果是后三页，显示后5页
            for (Integer i = searchResp.getTotalPages() - 4; i <= searchResp.getTotalPages(); ++i) {
                pageNavs.add(i);
            }
        }else { // 其他情况，当前页前两页，当前页，当前页后两页
            for (Integer i = searchParam.getPageNum() - 2; i <= searchParam.getPageNum() +2; ++i) {
                pageNavs.add(i);
            }
        }
        searchResp.setPageNavs(pageNavs);

        // 面包屑导航功能,前端没做出效果
        if (searchParam.getAttrs() != null){

            // 分析每一个attrs
            List<SearchRespVo.NavVo> navVos = searchParam.getAttrs().stream().map(attr -> {
                SearchRespVo.NavVo navVo = new SearchRespVo.NavVo();
                String[] attrKeyValue = attr.split("_");
                navVo.setNavValue(attrKeyValue[1]);
                // 远程调用product查询attrName
                R attrName = productFeignClientService.getAttrName(Long.parseLong(attrKeyValue[0]));
                navVo.setNavValue((String) attrName.get("attrName"));
                // 处理面包屑删除链接
                String urlQueryString = searchParam.get_urlQueryString();
                String encodeUrl = null;
                try {
                    encodeUrl = URLEncoder.encode(attr, "UTF-8");
                    encodeUrl = encodeUrl.replace("+", "%20"); // 浏览器和java的编码问题
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                String realUrl = urlQueryString.replace("attrs=" + encodeUrl, "");
                navVo.setLinkString("http://search.mall.com/list?" + realUrl);
                return navVo;

            }).collect(Collectors.toList());

            searchResp.setNavs(navVos);
        }



        return searchResp;
    }


    public static void main(String[] args) {
        for (int i = 1; i <= 10; ++i){
            System.out.println(i);
        }
    }


}
