package com.lqs.mall.cart.service;

import com.lqs.mall.cart.vo.CartItemVo;
import com.lqs.mall.cart.vo.CartVo;
import com.lqs.mall.common.exception.InvokeOriginRequestFailException;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author 李奇凇
 * @moduleName CartService
 * @date 2022/10/24 下午1:25
 * @do 购物车服务层接口
 */
public interface CartService {
    void appendGoodsToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException;

    CartItemVo getCartItem(Long skuId);

    CartVo getCart() throws ExecutionException, InterruptedException;

    void cleanCart(String cartKey);

    void alterCheckItem(Long skuId, Integer check);

    void alterCountItem(Long skuId, Integer num);

    void deleteItem(Long skuId);

    List<CartItemVo> currentUserCartItem() throws InvokeOriginRequestFailException;

    void deleteItemBySkuIdUseUserId(String userId, List<Long> skuIds);
}
