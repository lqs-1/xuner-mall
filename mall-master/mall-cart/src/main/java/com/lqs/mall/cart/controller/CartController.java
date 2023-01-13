package com.lqs.mall.cart.controller;

import com.alibaba.fastjson.JSON;
import com.lqs.mall.cart.service.CartService;
import com.lqs.mall.cart.vo.CartItemVo;
import com.lqs.mall.cart.vo.CartVo;
import com.lqs.mall.common.constant.Constant;
import com.lqs.mall.common.constant.REnum;
import com.lqs.mall.common.exception.InvokeOriginRequestFailException;
import com.lqs.mall.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 李奇凇
 * @moduleName CartController
 * @date 2022/10/26 上午9:06
 * @do 购物车的controller
 */

@Controller
public class CartController {


    @Autowired
    private CartService cartService;


    /**
     * 获取当前用户的选中的购物项目列表 因为有threadLocal存在，所有不用传递请求参数
     * @return
     */
    @GetMapping("currentUserCartItem")
    @ResponseBody
    public R currentUserCartItem(){

        try{
            // 程序代码
            List<CartItemVo> cartItemVoList = cartService.currentUserCartItem();

            return R.ok(REnum.REQUEST_CURRENT_USER_CART_ITEM_LIST_SUCCESS.getStatusCode(),
                            REnum.REQUEST_CURRENT_USER_CART_ITEM_LIST_SUCCESS.getStatusMsg())
                    .put("cartItemList", cartItemVoList);

        } catch (Exception e){
            // 判断异常的类型
            e.printStackTrace();
            return  e.getClass().isAssignableFrom(InvokeOriginRequestFailException.class) ?
            R.error(REnum.REQUEST_CURRENT_USER_CART_ITEM_LIST_FAIL.getStatusCode(), new InvokeOriginRequestFailException().getMessage()) :
            R.error(REnum.REQUEST_CURRENT_USER_CART_ITEM_LIST_FAIL.getStatusCode(), REnum.REQUEST_CURRENT_USER_CART_ITEM_LIST_FAIL.getStatusMsg());
        } finally{
            // 程序代码

        }


    }


    /**
     * 利用拦截其传递过来的数据，判断用户是否是临时用户，是临时用户就用user-key,不是临时用户就用userI
     *
     * ThreadLocal： 同一个线程共享数据
     *
     * 获取购物车的所有数据 如果用户之前是临时用户 现在登录了 就合并购物车数据 然后清空购物车
     *
     * @return
     */

    @GetMapping("cart")
    public String cartListPage(Model model){

        try{
            // 程序代码
            CartVo cartVo = cartService.getCart();

            model.addAttribute("cart", cartVo);
            return "cartList";
        }catch(Exception e){
            e.printStackTrace();
            // 程序代码

            model.addAttribute("cart", null);
            return "cartList";
        }finally{
            // 程序代码

        }







    }


    /**
     * 利用拦截其传递过来的数据，判断用户是否是临时用户，是临时用户就用user-key,不是临时用户就用userId
     *
     * 将商品添加到购物车,只做添加，返回数据交给重定向后的处理
     * @param skuId
     * @param num
     * @param redirectAttributes
     * @return
     */
    @GetMapping("appendToCart")
    public String appendGoodsToCart(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num, RedirectAttributes redirectAttributes){

        try{
            // 程序代码
            cartService.appendGoodsToCart(skuId, num);

            /**
             * 重定向到成功页面，在成功页面查询刚刚添加的购物项，因为在这里
             * 如果直接返回success页面的话，地址就是http://cart.mall.com/appendToCart?skuId=32&num=2
             * 在这个时候如果反复刷新页面，就会重复添加这个购物项
             *
             * 可以重定向到另一个请求，在另一个请求中查询到指定的购物项再返回数据，这样无论怎么刷新，都不会重复添加
             * 地址变成http://cart.mall.com/appendToCartSuccess?skuId=32 这个请求没有添加操作，所以不会重复添加
             */
            redirectAttributes.addAttribute("skuId", skuId);
            return "redirect:http://cart.mall.com/appendToCartSuccess";

        }catch(Exception e){
            e.printStackTrace();
            // 程序代码

            // 如果添加失败，就这么处理
            CartItemVo cartItemVo = new CartItemVo();
            cartItemVo.setCount(0);
            cartItemVo.setPrice(new BigDecimal(0));
            cartItemVo.setTitle("添加失败");
            cartItemVo.setImage("添加失败");
            cartItemVo.setSkuAttr(null);

            redirectAttributes.addAttribute("cartItemFail", JSON.toJSONString(cartItemVo));

            redirectAttributes.addAttribute("skuId", skuId);
            return "redirect:http://cart.mall.com/appendToCartSuccess";
        }finally{
            // 程序代码

        }
    }


    /**
     *
     * @param skuId
     * @param model
     * @param cartItemFail 如果添加失败了（上一个请求添加购物车失败了）返回的对象
     * @return
     */
    @GetMapping("appendToCartSuccess")
    public String appendGoodsToCartSuccess(@RequestParam("skuId") Long skuId, Model model, @RequestParam(value = "cartItemFail", required = false) String cartItemFail){
        if (cartItemFail == null){

            CartItemVo cartItem = cartService.getCartItem(skuId);

            model.addAttribute("cartItem", cartItem);

            return "success";
        }

        model.addAttribute("cartItem", JSON.parseObject(cartItemFail, CartItemVo.class));

        return "success";

    }

    /**
     * 改变购物车的某一项的选中状态
     * @param skuId
     * @param check
     * @return
     */
    @GetMapping("checkItem")
    public String alterCheckItem(@RequestParam("skuId") Long skuId, @RequestParam("check") Integer check){
        cartService.alterCheckItem(skuId, check);

        // 成功了或者失败直接重定向到购物车列表页
        return "redirect:http://cart.mall.com/cart";
    }


    /**
     * 改变购物车的某一项的数量
     * @param skuId
     * @param num
     * @return
     */
    @GetMapping("countItem")
    public String alterCountItem(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num){
        cartService.alterCountItem(skuId, num);

        // 成功了或者失败直接重定向到购物车列表页
        return "redirect:http://cart.mall.com/cart";
    }


    /**
     * 删除购物项
     * @param skuId
     * @return
     */
    @GetMapping("deleteItem")
    public String deleteItem(@RequestParam("skuId") Long skuId){
        cartService.deleteItem(skuId);

        // 成功了或者失败直接重定向到购物车列表页
        return "redirect:http://cart.mall.com/cart";
    }


    /**
     * 清空购物车
     * @param userId
     * @return
     */
    @PostMapping("clear/{userId}/cart")
    @ResponseBody
    public R clearCart(@PathVariable Long userId){

        try{
            // 程序代码
            cartService.cleanCart(Constant.CART_PREFIX + userId.toString());

            return R.ok(REnum.CLEAR_CART_SUCCESS.getStatusCode(),
                    REnum.CLEAR_CART_SUCCESS.getStatusMsg());
        }catch(Exception e){
            e.printStackTrace();
            // 程序代码

            return R.error(REnum.CLEAR_CART_FAIL.getStatusCode(),
                    REnum.CLEAR_CART_FAIL.getStatusMsg());
        }finally{
            // 程序代码

        }

    }


    @PostMapping("delete/{userId}/cartItem")
    @ResponseBody
    public R deleteCartItemBySkuIds(@PathVariable String userId, @RequestBody List<Long> skuIds){


        try{
            // 程序代码
            cartService.deleteItemBySkuIdUseUserId(userId, skuIds);

            return R.ok(REnum.DELETE_CART_ITEM_SUCCESS.getStatusCode(),
                    REnum.DELETE_CART_ITEM_SUCCESS.getStatusMsg());
        }catch(Exception e){
            e.printStackTrace();
            // 程序代码

            return R.error(REnum.DELETE_CART_ITEM_FAIL.getStatusCode(),
                    REnum.DELETE_CART_ITEM_FAIL.getStatusMsg());
        }finally{
            // 程序代码

        }

    }

}
