package com.lqs.mall.order.web;

import com.alipay.api.AlipayApiException;
import com.lqs.mall.common.constant.REnum;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.order.config.AlipayTemplate;
import com.lqs.mall.order.entity.OrderEntity;
import com.lqs.mall.order.service.OrderService;
import com.lqs.mall.order.vo.PayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author 李奇凇
 * @moduleName OrderPayWebController
 * @date 2023/1/12 上午10:05
 * @do 订单支付控制器
 */

@Controller
public class OrderPayWebController {

    @Autowired
    private AlipayTemplate alipayTemplate;

    @Autowired
    private OrderService orderService;

    /**
     * 支付功能,整合alipay使用
     * @param orderSn
     * @param redirectAttributes
     * @return 支付页面
     * @throws AlipayApiException
     */
    @GetMapping(value = "order/pay/{orderSn}", produces = "text/html")
    @ResponseBody
    public String orderPay(@PathVariable String orderSn, RedirectAttributes redirectAttributes) throws AlipayApiException {

        PayVo payVo = orderService.constructOrderPayData(orderSn);

        String aliPayPage = alipayTemplate.pay(payVo);

        // System.out.println(alipayTemplate.getAlipay_public_key());

        return aliPayPage;
    }


    /**
     * 订单支付完成以后去到member服务 member服务调用这个api进行订单得获取 和状态得修改
     * @param orderSn
     * @return
     */
    @PostMapping("order/{orderSn}/requestAndAlterOrder")
    @ResponseBody
    public R requestAndAlterOrder(@PathVariable String orderSn){

        try{
            // 程序代码
            OrderEntity order = orderService.requestAndAlterOrderByOrderSn(orderSn);

            return R.ok(REnum.REQUEST_AND_ALTER_ORDER_SUCCESS.getStatusCode(),
                    REnum.REQUEST_AND_ALTER_ORDER_SUCCESS.getStatusMsg())
                    .put("order", order);
        }catch(Exception e){
            e.printStackTrace();
            // 程序代码

            return R.error(REnum.REQUEST_AND_ALTER_ORDER_FAIL.getStatusCode(),
                    REnum.REQUEST_AND_ALTER_ORDER_FAIL.getStatusMsg());
        }finally{
            // 程序代码

        }

    }

}
