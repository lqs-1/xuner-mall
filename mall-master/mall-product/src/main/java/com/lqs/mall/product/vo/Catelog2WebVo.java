package com.lqs.mall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author 李奇凇
 * @moduleName Catelog2WebVo
 * @date 2022/9/30 下午4:00
 * @do 商城首页分类的视图对象
 */

/**
 * 二级分类webVo
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Catelog2WebVo implements Serializable{

    private String catalog1Id; // 这个二级分类的父id

    private List<Catelog3WebVo> catalog3List; // 这个二级分类的子分类

    private String id; // 这个二级分类的id

    private String name; // 这个二级分类的名字


    /**
     * 三级分类webVo
     */
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Catelog3WebVo implements Serializable {

        private String catalog2Id; //这个三级分类的父id

        private String id; // 这个三级分类的id

        private String name; // 这个三级分类的名字
    }


}
