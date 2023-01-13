package com.lqs.mall.cart.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lqs.mall.cart.feign.ProductFeignClientService;
import com.lqs.mall.cart.feign.WareOpenFeignClientService;
import com.lqs.mall.cart.interceptor.CartUserAuthInterceptor;
import com.lqs.mall.cart.service.CartService;
import com.lqs.mall.cart.to.UserTo;
import com.lqs.mall.cart.vo.CartCheckSkuHasStockVo;
import com.lqs.mall.cart.vo.CartItemVo;
import com.lqs.mall.cart.vo.CartVo;
import com.lqs.mall.cart.vo.SkuInfoVo;
import com.lqs.mall.common.constant.Constant;
import com.lqs.mall.common.exception.InvokeOriginRequestFailException;
import com.lqs.mall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @author 李奇凇
 * @moduleName CartServiceImpl
 * @date 2022/10/24 下午1:26
 * @do 购物车服务层接口的实现
 */

@Slf4j
@Service("CartService")
public class CartServiceImpl implements CartService {

    // 根据视频，使用StringRedisTemplate
    // @Autowired
    // private JedisPool jedisPool;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ProductFeignClientService productFeignClientService;

    @Autowired
    private WareOpenFeignClientService wareOpenFeignClientService;

    // 线程池
    @Autowired
    private ThreadPoolExecutor executor;


    // 往购物车添加数据
    @Override
    public void appendGoodsToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException {
        // 获取用户的购物车 好做修改
        BoundHashOperations<String, Object, Object> cartRedisOps = getCartOps();

        // 尝试从redis中获取对应的商品
        String redisCartItem = (String) cartRedisOps.get(skuId.toString());

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        // 购物车没有这个商品就是添加这个和桑品
        if (StringUtils.isEmpty(redisCartItem)){
            /**
             * 添加新商品到购物车
             */
            // 第一个任务 远程查询当前要添加的商品的信息
            CartItemVo cartItemVo = new CartItemVo();

            CompletableFuture<Void> getSkuInfoTask = CompletableFuture.runAsync(() -> {

                RequestContextHolder.setRequestAttributes(requestAttributes);

                R skuInfoResponse = productFeignClientService.getSkuInfo(skuId);

                if (skuInfoResponse.parseCode() >= 10000 && skuInfoResponse.parseCode() < 20000){
                    SkuInfoVo skuInfo = skuInfoResponse.parseType(new TypeReference<SkuInfoVo>() {}, "skuInfo");
                    // 商品添加到购物车
                    cartItemVo.setCheck(true);
                    cartItemVo.setCount(num);
                    cartItemVo.setImage(skuInfo.getSkuDefaultImg());
                    cartItemVo.setTitle(skuInfo.getSkuTitle());
                    cartItemVo.setSkuId(skuId);
                    cartItemVo.setPrice(skuInfo.getPrice());
                }


            }, executor);

            // 第二个任务 远程查询sku的组合信息
            CompletableFuture<Void> getSkuSaleAttrValuesListTask = CompletableFuture.runAsync(() -> {

                RequestContextHolder.setRequestAttributes(requestAttributes);

                R skuSaleAttrValuesResponse = productFeignClientService.getSkuSaleAttrValues(skuId);
                if (skuSaleAttrValuesResponse.parseCode() >= 10000 && skuSaleAttrValuesResponse.parseCode() < 20000) {
                    List<String> skuSaleAttrValuesList = skuSaleAttrValuesResponse.parseType(new TypeReference<List<String>>() {
                    }, "skuSaleAttrValuesList");
                    cartItemVo.setSkuAttr(skuSaleAttrValuesList);
                }
            }, executor);

            // 等两个任务完成
            CompletableFuture.allOf(getSkuInfoTask, getSkuSaleAttrValuesListTask).get();

            // 添加购物车
            cartRedisOps.put(skuId.toString(), JSON.toJSONString(cartItemVo));

        }else{
            // 如果购物车存在这个商品，只是数量更改就可以
            CartItemVo cartItemVo = JSON.parseObject(redisCartItem, CartItemVo.class);

            cartItemVo.setCount(cartItemVo.getCount() + num);

            cartRedisOps.put(skuId.toString(), JSON.toJSONString(cartItemVo));

        }
    }

    @Override
    public CartItemVo getCartItem(Long skuId) {

        BoundHashOperations<String, Object, Object> cartRedisOps = getCartOps();

        String redisCartItem = (String) cartRedisOps.get(skuId.toString());

        return JSON.parseObject(redisCartItem, CartItemVo.class);
    }

    // 获取购物车的所有数据 如果用户之前是临时用户 现在登录了 就合并购物车数据（userId和user-key同时存在）
    @Override
    public CartVo getCart() throws ExecutionException, InterruptedException {

        UserTo userTo = CartUserAuthInterceptor.userToThreadLocal.get();


        // 用户的整合购物车
        CartVo cart = new CartVo();

        // 登录和没登录做两个处理(userId和user-key只存在一个) 登录了和登录了且有临时用户数据的再做合并处理(userId和user-key同时存在)
        if (userTo.getUserId() != null){
            // 登录了的处理

            // 如果用户不仅登录了，且还有临时用户的数据，那么就合并
            List<CartItemVo> tempCartItemList = getCartItemVoList(Constant.CART_PREFIX + userTo.getUserKey());

            if (tempCartItemList != null){
                // 临时购物车有数据 合并数据
                for (CartItemVo cartItem : tempCartItemList) {

                    appendGoodsToCart(cartItem.getSkuId(), cartItem.getCount());
                    
                }

                // 清空购物车
                cleanCart(Constant.CART_PREFIX + userTo.getUserKey());

            }

            // 获取所有的购物车购物项(现在获取的就是合并之后的购物车)
            List<CartItemVo> cartItemList = getCartItemVoList(Constant.CART_PREFIX + userTo.getUserId());

            cart.setItems(cartItemList);

        }else{
            // 没登录的处理 临时用户的购物车

            // 获取所有的购物车购物项
            List<CartItemVo> tempCartItemList = getCartItemVoList(Constant.CART_PREFIX + userTo.getUserKey());

            cart.setItems(tempCartItemList);

        }

        return cart;
    }

    // 清空指定购物车
    @Override
    public void cleanCart(String cartKey) {

        stringRedisTemplate.delete(cartKey);


    }


    // 更新购物项的选中状态
    @Override
    public void alterCheckItem(Long skuId, Integer check) {
        // 获取当前购物项
        CartItemVo cartItem = getCartItem(skuId);

        cartItem.setCheck(check == 1 ? true : false);

        BoundHashOperations<String, Object, Object> cartRedisOps = getCartOps();

        cartRedisOps.put(skuId.toString(), JSON.toJSONString(cartItem));
    }

    // 更新购物项的数量
    @Override
    public void alterCountItem(Long skuId, Integer num) {
        // 获取当前购物项
        CartItemVo cartItem = getCartItem(skuId);

        cartItem.setCount(num);

        BoundHashOperations<String, Object, Object> cartRedisOps = getCartOps();

        cartRedisOps.put(skuId.toString(), JSON.toJSONString(cartItem));

    }

    // 删除购物项
    @Override
    public void deleteItem(Long skuId) {

        BoundHashOperations<String, Object, Object> cartRedisOps = getCartOps();

        cartRedisOps.delete(skuId.toString());

    }



    // 获取当前用户的购物项列表
    @Override
    public List<CartItemVo> currentUserCartItem() throws InvokeOriginRequestFailException{
        List<CartItemVo> cartItemVoList = getCartItemVoList(Constant.CART_PREFIX + CartUserAuthInterceptor.userToThreadLocal.get().getUserId());

        List<CartItemVo> cartItemCheckedList =
                cartItemVoList.stream().filter(cartItemVo -> cartItemVo.getCheck())
                        .map(cartItem -> {
                            // 更新最新价格 远程调用
                            R latestPriceResponse = productFeignClientService.getLatestPrice(cartItem.getSkuId());

                            // TODO 逻辑有问题， 如果没有查到（出现异常）不知到解决了没
                            if (latestPriceResponse.parseCode() >= 10000 && latestPriceResponse.parseCode() < 20000 && latestPriceResponse.parseType(new TypeReference<BigDecimal>(){}) != null){
                                BigDecimal latestPrice = latestPriceResponse.parseType(new TypeReference<BigDecimal>() {});

                                cartItem.setPrice(latestPrice);
                            } else if (latestPriceResponse.parseCode() >= 20000) {

                                throw new InvokeOriginRequestFailException();

                            }

                            // 是否有库存  TODO 库存查询
                            R orderSkuHasStockResponse = wareOpenFeignClientService.orderSkuHasStock(cartItem.getSkuId());

                            if (orderSkuHasStockResponse.parseCode() < 20000){
                                CartCheckSkuHasStockVo skuStock = orderSkuHasStockResponse.parseType(new TypeReference<CartCheckSkuHasStockVo>() {}, "skuHasStock");

                                cartItem.setHasStock(skuStock.getSkuStockNum() - cartItem.getCount() >= 0 ? true : false);

                            }

                            return cartItem;

                        }).collect(Collectors.toList());
        return cartItemCheckedList;

    }

    /**
     * 使用userId和skuId删除购物项
     * @param userId
     * @param skuIds
     */
    @Override
    public void deleteItemBySkuIdUseUserId(String userId, List<Long> skuIds) {
        BoundHashOperations<String, Object, Object> userCartOps = getUserCartOps(userId);

        for (Long skuId : skuIds) {
            userCartOps.delete(skuId.toString());
        }

    }


    // 获取指定key的购物车的所有购物项
    private List<CartItemVo> getCartItemVoList(String cartKey) {
        BoundHashOperations<String, Object, Object> cartRedisOps = stringRedisTemplate.boundHashOps(cartKey);

        List<Object> cartRedisObjectList = cartRedisOps.values();

        List<CartItemVo> cartItemList = cartRedisObjectList.stream().map(cartRedisObject -> {

            String cartStringObject = JSON.toJSONString(cartRedisObject);

            CartItemVo cartItemVo = JSON.parseObject(JSON.parse(cartStringObject).toString(), new TypeReference<CartItemVo>(){});
            return cartItemVo;

        }).collect(Collectors.toList());

        return cartItemList;
    }


    // 获取对应用户的购物车
    // 这个getCartOps只能获取只有userid或者只有user-key的redisHash操作对象 不能获取两个同时存在的redisHash操作对象
    private BoundHashOperations<String, Object, Object> getCartOps() {
        // 用户是否登录了
        UserTo userTo = CartUserAuthInterceptor.userToThreadLocal.get();

        // 如果登录了就放到用户自己的购物车，否则就放到创建的临时用户的购物车
        String cartKey = "";

        if (userTo.getUserId() != null){

            cartKey = Constant.CART_PREFIX + userTo.getUserId();

        }else {

            cartKey = Constant.CART_PREFIX + userTo.getUserKey();

        }

        // 如果购物车已经存在指定商品，那么就是修改数量，如果没有就是添加
        // Object o = stringRedisTemplate.opsForHash().get(cartKey, "1"); // 这种每次都要传这个key，比较麻烦

        // 这种方式，所有的增删改查都是面向这个key的
        BoundHashOperations<String, Object, Object> cartRedisOps = stringRedisTemplate.boundHashOps(cartKey);

        return cartRedisOps;
    }

    // 获取指定用户的购物车
    // 这个getUserCartOps只能获取只有userid的redisHash操作对象 不能获取两个同时存在的redisHash操作对象
    private BoundHashOperations<String, Object, Object> getUserCartOps(String userId) {


        String cartKey = Constant.CART_PREFIX + userId;


        // 这种方式，所有的增删改查都是面向这个key的
        BoundHashOperations<String, Object, Object> cartRedisOps = stringRedisTemplate.boundHashOps(cartKey);

        return cartRedisOps;
    }
}
