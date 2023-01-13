package com.lqs.mall.ware.exception;

/**
 * @author 李奇凇
 * @moduleName NoStockException
 * @date 2023/1/6 上午10:00
 * @do 没有库存时候得异常
 */
public class NoStockException extends RuntimeException{

    private Long skuId;

    public NoStockException(Long skuId){
        super(skuId + "没有足够得库存");
    }


    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
}
