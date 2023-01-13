package com.lqs.mall.order.web;

import com.lqs.mall.common.constant.Constant;
import com.lqs.mall.common.constant.REnum;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.order.entity.OrderEntity;
import com.lqs.mall.order.service.OrderService;
import com.lqs.mall.order.vo.OrderConfirmVo;
import com.lqs.mall.order.vo.OrderSubmitVo;
import com.lqs.mall.order.vo.SubmitOrderResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author 李奇凇
 * @moduleName OrderWebController
 * @date 2022/10/28 上午10:08
 * @do 订单相关前端controller
 */


@Controller
public class OrderWebController {


    @Autowired
    private OrderService orderService;


    /**
     * 删除订单 逻辑删除 修改为已删除状态
     * @param orderSn
     * @return
     */
    @GetMapping("orderDelete/{orderSn}")
    public String orderDelete(@PathVariable("orderSn") String orderSn){
        System.out.println(orderSn);
        orderService.deleteOder(orderSn);
        return "redirect:http://member.mall.com/orderList";
    }


    /**
     * 获取订单列表得分页数据
     * @param param
     * @return
     */
    @PostMapping("order/orderList")
    @ResponseBody
    public R requestOrderPage(@RequestBody Map<String, Object> param){

        try{
            // 程序代码

            PageUtils orderListPage = orderService.requestOrderPage(param);


            return R.ok(REnum.REQUEST_ORDER_PAGE_LIST_SUCCESS.getStatusCode(),
                            REnum.REQUEST_ORDER_PAGE_LIST_SUCCESS.getStatusMsg())
                    .put("orderList", orderListPage);

        }catch(Exception e){
            e.printStackTrace();
            // 程序代码

            return R.error(REnum.REQUEST_ORDER_PAGE_LIST_FAIL.getStatusCode(),
                    REnum.REQUEST_ORDER_PAGE_LIST_FAIL.getStatusMsg());

        }finally {
            // 程序代码

        }

    }



    /**
     * 获取订单状态
     * @param orderSn
     * @return
     */
    @GetMapping("order/status/{orderSn}")
    @ResponseBody
    public R requestOrderStatus(@PathVariable String orderSn){
        try{
            // 程序代码
            OrderEntity order = orderService.requestOrderStatus(orderSn);

            return R.ok(REnum.REQUEST_ORDER_STATUS_SUCCESS.getStatusCode(),
                    REnum.REQUEST_ORDER_STATUS_SUCCESS.getStatusMsg())
                    .put("order", order);
        }catch(Exception e){
            e.printStackTrace();
            // 程序代码
            return R.error(REnum.REQUEST_ORDER_STATUS_FAIL.getStatusCode(),
                    REnum.REQUEST_ORDER_STATUS_FAIL.getStatusMsg());
        }finally{
            // 程序代码

        }
    }


    /**
     * 在购物车点击结算跳转过来 获取数据 渲染页面
     * @return
     */
    @GetMapping("toTrade")
    public String toTrade(Model model){

        // 获取确认订单页需要的数据
        OrderConfirmVo orderConfirm = null;
        try {
            // 获取要下单的商品信息
            orderConfirm = orderService.getConfirmOrder();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

         // System.out.println(orderConfirm);

        model.addAttribute("orderConfirm", orderConfirm);

        // 展示订单确认的数据
        return "toTrade";
    }


    /**
     * 下单功能
     * @return
     */
    @PostMapping("/submitOrder")
    public String submitOrder(OrderSubmitVo orderSubmitVo, Model model, RedirectAttributes redirectAttributes){

        try{
            // 程序代码
            // 订单信息是否存在没货商品

            Boolean hasStock = orderSubmitVo.getHasStock();
            if (!hasStock){
                // 没有货返回订单确认页
                redirectAttributes.addFlashAttribute("msg", Constant.subMitOrderStatus.ORDER_SKU_WARE_STOCK_UNENGHT.getMsg());
                return "redirect:http://order.mall.com/toTrade";
            }

            SubmitOrderResponseVo submitOrderResponse = orderService.submitOrder(orderSubmitVo);

            if (submitOrderResponse.getStatusCode() == Constant.subMitOrderStatus.SUCCESS.getCode()){
                model.addAttribute("submitOrderResponse", submitOrderResponse);
                // 下单成功来到支付选择页
                return "pay";
            }else {
                // 下单失败返回订单确认页面重新确认订单信息
                redirectAttributes.addFlashAttribute("msg", Constant.subMitOrderStatus.SELECTOR.getDetailMessage(submitOrderResponse.getStatusCode()));
                return "redirect:http://order.mall.com/toTrade";
            }

        }catch(RuntimeException e){
            e.printStackTrace();
            // 程序代码

            // 出现库存锁定失败异常返回订单确认页面重新确认订单信息
            redirectAttributes.addFlashAttribute("msg", Constant.subMitOrderStatus.ORDER_EXCEPTION.getMsg());
            return "redirect:http://order.mall.com/toTrade";

        }catch (Exception e){
            // 出现其他败异常返回订单确认页面重新确认订单信息
            redirectAttributes.addFlashAttribute("msg", Constant.subMitOrderStatus.ORDER_EXCEPTION.getMsg());
            return "redirect:http://order.mall.com/toTrade";
        } finally{
            // 程序代码

        }
    }


}
