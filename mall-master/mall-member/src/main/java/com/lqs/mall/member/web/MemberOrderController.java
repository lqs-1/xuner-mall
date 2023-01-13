package com.lqs.mall.member.web;

import com.lqs.mall.common.constant.Constant;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.member.fiegn.OrderOpenFeignClientService;
import com.lqs.mall.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 李奇凇
 * @moduleName MemberOrderController
 * @date 2023/1/12 下午12:48
 * @do 用户订单controller
 */

@Controller
public class MemberOrderController {


    @Autowired
    private MemberService memberService;

    @Autowired
    private OrderOpenFeignClientService orderOpenFeignClientService;

    /**
     * 支付成功之后修改完订单和库存信息之后 或者  用户点击我的订单跳转页面
     * @return
     */
    @GetMapping("orderList")
    public String orderList(@RequestParam(value = "page", defaultValue = "1") String page, Model model){

        Map<String, Object> param = new HashMap<>();
        param.put(Constant.PAGE, page);
        param.put(Constant.LIMIT, Constant.ORDER_LIST_PAGE_LIMIT);

        R orderListPageResponse = orderOpenFeignClientService.requestOrderPage(param);

        if (orderListPageResponse.parseCode() < 20000){

            model.addAttribute("orderListPage", orderListPageResponse);

            System.out.println(orderListPageResponse);

        }

        return "orderList";

    }


    /**
     * 支付成功得回调同步模式
     *  订单支付成功之后修改订单状态和库存锁定 还有删除对应购物车数据 积分保存等信息
     *  重定向到订单列表页 不然容易暴露消息
     * @param orderSn
     * @param totalAmount 冗余数据用不到
     * @param payDate
     * @return
     */
    @GetMapping("payReturn")
    public String payReturn(@RequestParam("out_trade_no") String orderSn, @RequestParam("total_amount") String totalAmount, @RequestParam("timestamp") String payDate){

        memberService.confirmOrder(orderSn, payDate);

        return "redirect:http://member.mall.com/orderList";
    }

}
