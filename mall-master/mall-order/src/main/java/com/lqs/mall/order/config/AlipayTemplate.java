package com.lqs.mall.order.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.lqs.mall.order.vo.PayVo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "alipay")
@Component
@Data
public class AlipayTemplate {

    //在支付宝创建的应用的id
    private   String app_id;

    // 商户私钥，您的PKCS8格式RSA2私钥
    private  String merchant_private_key;
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    private  String alipay_public_key;

    // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝通知使用POST方式
    // notify_url为服务器通知，支付宝可以保证99.9999%的通知到达率，前提是您的网络通畅
    // 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
    // 异步通知: 其实是双保险机制, 如果同步通知后没有跳转到你的网址, 可能用户关了
    // 可能网速慢, 即无法触发你更新订单状态为已支付的controller, 这时候异步通知就有作用了
    // 不过你要判断一下, 如果订单已经变为已支付, 则不必再更新一次了, 只返回给支付宝success即可, 否则他会一直异步通知你
    private  String notify_url;

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝通知使用GET方式  返回得有用数据 out_trade_no=202301121108113981613372238814502914&total_amount=10000.00&timestamp=2023-01-12+11%3A09%3A33
    // 为网页重定向通知，是由客户的浏览器触发的一个通知，若客户去网银支付，也会受银行接口影响，由于各种影响因素特别多，所以该种类型的通知支付宝不保证其到达率
    //同步通知，支付成功，一般跳转到成功页
    private  String return_url;


    // 签名方式
    private  String sign_type;

    // 字符编码格式
    private  String charset;

    // 支付宝网关； https://openapi.alipaydev.com/gateway.do
    private  String gateway_url;

    public  String pay(PayVo vo) throws AlipayApiException {

        //AlipayClient alipayClient = new DefaultAlipayClient(AlipayTemplate.gatewayUrl, AlipayTemplate.app_id, AlipayTemplate.merchant_private_key, "json", AlipayTemplate.charset, AlipayTemplate.alipay_public_key, AlipayTemplate.sign_type);
        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(gateway_url,
                app_id, merchant_private_key, "json",
                charset, alipay_public_key, sign_type);

        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = vo.getOut_trade_no();
        //付款金额，必填
        String total_amount = vo.getTotal_amount();
        //订单名称，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String desc = vo.getOrderDesc();
        /**
         * 收单:
         * 为了防止用户在订单支付页面一直不支付 导致订单被系统自动取消 库存被系统自动解锁 设置了这个之后 订单页面停留到了这个时间支付直接失败
         */
        String timeout_express = vo.getTimeout_express()    ;

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ desc +"\","
                + "\"timeout_express\":\""+ timeout_express +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(alipayRequest).getBody();

        // 会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        // System.out.println("支付宝的响应："+result);

        return result;

    }
}
