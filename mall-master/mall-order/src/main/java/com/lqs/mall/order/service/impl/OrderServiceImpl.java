package com.lqs.mall.order.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lqs.mall.common.constant.Constant;
import com.lqs.mall.common.to.auth.AccountRespTo;
import com.lqs.mall.common.to.mq.order.OrderSnTO;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.common.utils.pagination.QueryPage;
import com.lqs.mall.order.dao.OrderDao;
import com.lqs.mall.order.dao.OrderItemDao;
import com.lqs.mall.order.entity.OrderEntity;
import com.lqs.mall.order.entity.OrderItemEntity;
import com.lqs.mall.order.feign.CartOpenFeignClientService;
import com.lqs.mall.order.feign.MemberOpenFeignClientService;
import com.lqs.mall.order.feign.ProductOpenFeignClientService;
import com.lqs.mall.order.feign.WareOpenFeignClientService;
import com.lqs.mall.order.interceptor.UserLoginInterceptor;
import com.lqs.mall.order.mq.publisher.OrderAutoCancelPublisher;
import com.lqs.mall.order.service.OrderService;
import com.lqs.mall.order.to.OrderCreateTo;
import com.lqs.mall.order.vo.*;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    private MemberOpenFeignClientService memberOpenFeignClientService;

    @Autowired
    private CartOpenFeignClientService cartOpenFeignClientService;

    @Autowired
    private ProductOpenFeignClientService productOpenFeignClientService;

    @Autowired
    private WareOpenFeignClientService wareOpenFeignClientService;

    @Autowired
    private ThreadPoolExecutor executor;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private OrderAutoCancelPublisher orderAutoCancelPublisher;


    private ThreadLocal<OrderSubmitVo> orderSubmitVoThreadLocal = new ThreadLocal<>();

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new QueryPage<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
        );

        return new PageUtils(page);
    }


    /**
     * 获取订单列表得分页数据
     * @param param
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public PageUtils requestOrderPage(Map<String, Object> param) {

        // 得到创建好得分页对象
        IPage<OrderEntity> orderPage = this.page(
                new QueryPage<OrderEntity>().getPage(param),
                new LambdaQueryWrapper<OrderEntity>()
                        .eq(OrderEntity::getMemberId, UserLoginInterceptor.user.get().getId())
                        .eq(OrderEntity::getDeleteStatus, Constant.orderDeleteStatus.N_DEL.getCode())
                        .orderByDesc(OrderEntity::getId)
        );

        // 获取到订单信息
        List<OrderEntity> orderList = orderPage.getRecords();

        // 遍历封装订单项
        List<OrderEntity> latestOrderList = orderList.stream().map(order -> {

            List<OrderItemEntity> orderItemList = orderItemDao.selectList(new LambdaQueryWrapper<OrderItemEntity>().eq(OrderItemEntity::getOrderId, order.getId()));

            order.setOrderItemList(orderItemList);

            return order;
        }).collect(Collectors.toList());

        // 再将封装好得数据 设置进去
        orderPage.setRecords(latestOrderList);

        return new PageUtils(orderPage);
    }

    /**
     * 逻辑删除订单
     * @param orderSn
     */
    @Transactional(readOnly = false)
    @Override
    public void deleteOder(String orderSn) {
        OrderEntity order = this.baseMapper.selectOne(new LambdaQueryWrapper<OrderEntity>().eq(OrderEntity::getOrderSn, orderSn));

        order.setDeleteStatus(Constant.orderDeleteStatus.DEL.getCode());

        this.baseMapper.updateById(order);
    }


    /**
     * 获取订单确认页的数据
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public OrderConfirmVo getConfirmOrder() throws ExecutionException, InterruptedException {

        AccountRespTo account = UserLoginInterceptor.user.get();

        OrderConfirmVo orderConfirmVo = new OrderConfirmVo();

        // 获取老请求
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();


        // 第一个异步
        CompletableFuture<Void> getAddressReceiveFuture = CompletableFuture.runAsync(() -> {
            // 给这个县城设置共享数据
            RequestContextHolder.setRequestAttributes(requestAttributes);

            // 远程调用用户的所有的收获地址
            R memberReceiveAddrResponse = memberOpenFeignClientService.getAddressList(account.getId());

            if (memberReceiveAddrResponse.parseCode() >= 10000 && memberReceiveAddrResponse.parseCode() < 20000) {
                List<MemberAddrVo> memberReceiveAddressList = memberReceiveAddrResponse.parseType(new TypeReference<List<MemberAddrVo>>() {
                                                                                                  },
                        "memberReceiveAddressList");


                orderConfirmVo.setAddrList(memberReceiveAddressList);
            }
        }, executor);


        // 第二个异步
        CompletableFuture<Void> getCartItemFuture = CompletableFuture.runAsync(() -> {
            // 给这个县城设置共享数据
            RequestContextHolder.setRequestAttributes(requestAttributes);

            // 远程获取用户的所有要结算的购物项
            R currentUserCartItemResponse = cartOpenFeignClientService.currentUserCartItem();

            List<OrderItemVo> cartItemCheckedList = new ArrayList<>();

            if (currentUserCartItemResponse.parseCode() >= 10000 && currentUserCartItemResponse.parseCode() < 20000) {
                cartItemCheckedList = currentUserCartItemResponse.parseType(new TypeReference<List<OrderItemVo>>() {}, "cartItemList");

                orderConfirmVo.setOrderItemList(cartItemCheckedList);

                // 设置运费
                BigDecimal originTotal = orderConfirmVo.getTotal();

                // 将应付费用转换为字符串
                String originStringPrice = originTotal.toString();
                // 再将字符串价格截取整数部分
                String stringPrice = originStringPrice.substring(0, originStringPrice.indexOf("."));
                // 将截取后得到得字符串转换为整形
                Integer IntPrice = new Integer(stringPrice);
                // 计算出运费
                Integer freightPrice  = IntPrice % 2 + stringPrice.length() * 2;
                orderConfirmVo.setFreightPrice(new BigDecimal(freightPrice.toString()));

                // 设置优惠价格 TODO 优惠系统
                orderConfirmVo.setDisCountPrice(new BigDecimal("0.0"));

                List<OrderItemVo> orderItemList = orderConfirmVo.getOrderItemList();
            }
        }, executor);




        // 设置用户积分
        orderConfirmVo.setIntegration(account.getIntegration());

        CompletableFuture.allOf(getAddressReceiveFuture, getCartItemFuture).get();


        // 设置防重令牌 解决幂等性问题
        String token = UUID.randomUUID().toString();
        // 将token保存到redis
        jedisPool.getResource().setex(Constant.USER_ORDER_TOKEN_PREFIX + account.getId(), 1800, token);
        orderConfirmVo.setOrderToken(token);

        // 设置订单库存
        List<OrderItemVo> orderItemList = orderConfirmVo.getOrderItemList();
        Boolean orderAllHasStock = true;
        for (OrderItemVo orderItemVo : orderItemList) {
            Boolean orderItemHasStock = orderItemVo.getHasStock();
            if (orderItemHasStock == false){
                orderAllHasStock = false;
                break;
            }
        }
        orderConfirmVo.setHasStock(orderAllHasStock);
        return orderConfirmVo;
    }


    /**
     * 下单功能得实现
     * 下单：创建订单、验证令牌、验证价格、锁库存
     * @param orderSubmitVo
     * @return
     */
    @Transactional
    @Override
    public SubmitOrderResponseVo submitOrder(OrderSubmitVo orderSubmitVo) {

        SubmitOrderResponseVo submitOrderResponse = new SubmitOrderResponseVo();

        orderSubmitVoThreadLocal.set(orderSubmitVo);

        AccountRespTo account = UserLoginInterceptor.user.get();

        // 验证令牌
        String orderToken = orderSubmitVo.getOrderToken();

        // 保证原子性得redis脚本，用于原子删token，保证幂等性 TODO 根据token来解决幂等问题
        //  意思是 通过get方式获取传递得第一个参数得值,是否等于传递得第二个参数 如果是就通过del方式来删除 然后返回1(应该是删除得条数), 如果不是就返回0
        String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";

        // 对比令牌
        Jedis jedis = jedisPool.getResource();
        // 原子验证令牌和删除 0验证失败 1验证成功
        Long result = (Long) jedis.evalsha(jedis.scriptLoad(script), Arrays.asList(Constant.USER_ORDER_TOKEN_PREFIX + account.getId()), Arrays.asList(orderToken));

        if (result == 0){
            // 防重令牌 验证失败
            submitOrderResponse.setStatusCode(Constant.subMitOrderStatus.TOKEN_VALIDATE_FAIL.getCode());
            // 返回
            return submitOrderResponse;
        }else {
            // 验证成功,创建订单 、验证价格、锁库存
            // 创建订单订单项
            OrderCreateTo order = createOrder();

            // 页面价格验证
            BigDecimal payPrice = order.getOrder().getPayAmount();
            BigDecimal pagePayPrice = orderSubmitVo.getPayPrice();
            // 页面价格没有问题
            if (Math.abs(pagePayPrice.subtract(pagePayPrice).intValue()) < 0.01){

                // 保存订单 TODO
                saveOrder(order);

                orderAutoCancelPublisher.sendOrderCancelTimeDelayMessage(order.getOrder());


                // 锁定库存  只要有异常 立刻回滚数据
                // 订单号
                // 订单项信息（skuId、skuName、num）
                WareSkuLockVo wareSkuLockVo = new WareSkuLockVo();
                // 封装订单号
                wareSkuLockVo.setOrderSn(order.getOrder().getOrderSn());
                // 封装订单项
                List<OrderItemEntity> orderItems = order.getOrderItems();
                List<OrderItemLockStockVo> lockSkus = orderItems.stream().map(orderItemEntity -> {
                    OrderItemLockStockVo orderItemVo = new OrderItemLockStockVo();
                    orderItemVo.setSkuId(orderItemEntity.getSkuId());
                    orderItemVo.setCount(orderItemEntity.getSkuQuantity());

                    return orderItemVo;
                }).collect(Collectors.toList());
                // 封装锁库存对象
                wareSkuLockVo.setLockSkus(lockSkus);

                R lockStockResponse = wareOpenFeignClientService.orderLockStock(wareSkuLockVo);

                // System.out.println(lockStockResponse);
                // int i = 10/0;

                if (lockStockResponse.parseCode() < 20000){
                    // 锁成功了
                    // System.out.println(lockStockResponse.parseType(new TypeReference<Boolean>(){}, "lockStockResult"));
                    Boolean lockStockResult = lockStockResponse.parseType(new TypeReference<Boolean>() {}, "lockStockResult");


                    if (lockStockResult == false){
                        // 锁失败了 直接抛出异常
                        // System.out.println(lockStockResponse.parseMsg());
                        // throw new RuntimeException();
                        submitOrderResponse.setStatusCode(Constant.subMitOrderStatus.SKU_WARE_STOCK_LOCK_FAIL.getCode());
                        return submitOrderResponse;
                    }
                    submitOrderResponse.setStatusCode(Constant.subMitOrderStatus.SUCCESS.getCode());

                } else {
                    // 锁失败了
                    submitOrderResponse.setStatusCode(Constant.subMitOrderStatus.SKU_WARE_STOCK_LOCK_FAIL.getCode());
                    return submitOrderResponse;
                }

                // 封装响应对象
                submitOrderResponse.setOrder(order.getOrder());
                submitOrderResponse.setStatusCode(Constant.subMitOrderStatus.SUCCESS.getCode());

            }else {
                submitOrderResponse.setStatusCode(Constant.subMitOrderStatus.PRICE_CHECK_FAIL.getCode());
                return submitOrderResponse;
            }

        }

        // 一切正常返回对象
        return submitOrderResponse;
    }

    /**
     * 获取订单状态
     * @param orderSn
     * @return
     */
    @Override
    public OrderEntity requestOrderStatus(String orderSn) {
        OrderEntity order = this.baseMapper.selectOne(new LambdaQueryWrapper<OrderEntity>().eq(OrderEntity::getOrderSn, orderSn));

        return order;
    }

    /**
     * 系统自动取消订单
     * @param order
     */
    @Transactional(readOnly = false)
    @Override
    public void orderAutoCancel(OrderEntity order) {
        // 查询订单得最新状态
        OrderEntity latestOrder = this.baseMapper.selectById(order.getId());
        // 如果是null 那就代表创建订单之后出现异常回滚了 什么都不用做
        if (latestOrder != null){
            // 根据订单得状态判断是否要取消订单 只有没有支付得订单才需要被取消
            if (latestOrder.getStatus() == Constant.orderStatus.NEW.getCode()){
                // 取消订单
                latestOrder.setStatus(Constant.orderStatus.CANCEL.getCode());
                this.baseMapper.updateById(latestOrder);
                // 为了防止 下订单得时候 库存模块执行较快 订单模块执行慢 从而导致订单并为在自动解锁库存之前取消得问题
                // 在系统自动取消订单之后 再次发送消息给mq 让库存解锁模块可以直接监听到
                // 将订单号码传递过去
                OrderSnTO orderSn = new OrderSnTO();
                orderSn.setOrderSn(latestOrder.getOrderSn());
                orderAutoCancelPublisher.sendOrderCancelEdTimeDelayMessage(orderSn);
            }
        }

    }

    /**
     * 组织订单支付数据
     * @param orderSn
     * @return
     */
    @Override
    public PayVo constructOrderPayData(String orderSn) {

        PayVo payVo = new PayVo();

        OrderEntity order = this.baseMapper.selectOne(new LambdaQueryWrapper<OrderEntity>().eq(OrderEntity::getOrderSn, orderSn));

        if (order != null && order.getStatus() == Constant.orderStatus.NEW.getCode()){
            payVo.setOut_trade_no(orderSn);
            payVo.setTotal_amount(order.getPayAmount().setScale(2, BigDecimal.ROUND_UP).toString());
            payVo.setSubject(Constant.ORDER_PAY_SUBJECT);
            payVo.setOrderDesc(Constant.ORDER_PAY_DESC);
        }
        // 设置订单支付自动收单时间
        payVo.setTime_expire(Constant.ORDER_PAY_TIMEOUT_EXPIRE);

        return payVo;
    }

    /**
     * 根据订单号获取订单和修改状态
     * @param orderSn
     * @return
     */
    @Transactional(readOnly = false)
    @Override
    public OrderEntity requestAndAlterOrderByOrderSn(String orderSn) {
        OrderEntity order = this.baseMapper.selectOne(new LambdaQueryWrapper<OrderEntity>().eq(OrderEntity::getOrderSn, orderSn));
        order.setStatus(Constant.orderStatus.PAY_ED.getCode());
        order.setModifyTime(new Date());
        this.baseMapper.updateById(order);

        return order;
    }



    // 大订单创建
    private OrderCreateTo createOrder(){

        OrderCreateTo orderCreate = new OrderCreateTo();

        // 生成订单号
        String orderSn = IdWorker.getTimeId();

        // 创建订单基本信息
        OrderEntity order = buildOrder(orderSn);

        // 获取订单项信息 获取所有得订单项
        List<OrderItemEntity> orderItems = buildOrderItems(orderSn);

        // 验价 计算价格相关
        computePrice(order, orderItems);

        orderCreate.setOrder(order);

        orderCreate.setOrderItems(orderItems);

        return orderCreate;

    }

    // 验价 计算价格相关
    private void computePrice(OrderEntity order, List<OrderItemEntity> orderItems) {
        // 总价
        BigDecimal totalPrice = new BigDecimal("0.0");
        // 积分总优惠价格
        BigDecimal integration = new BigDecimal("0.0");
        // 打折总优惠价格
        BigDecimal promotion = new BigDecimal("0.0");
        // 优惠卷优惠总价
        BigDecimal coupon = new BigDecimal("0.0");
        // 积分
        BigDecimal gift = new BigDecimal("0.0");
        // 成长值
        BigDecimal growth = new BigDecimal("0.0");


        // 订单得总额，叠加每一个订单项得总额信息
        for (OrderItemEntity orderItem : orderItems) {

            // 计算每一个商品得总价
            BigDecimal realAmount = orderItem.getRealAmount();
            totalPrice = totalPrice.add(realAmount);

            // 设置优惠价格 优惠卷
            BigDecimal couponAmount = orderItem.getCouponAmount();
            coupon = coupon.add(couponAmount);
            // 设置优惠价格 积分
            BigDecimal integrationAmount = orderItem.getIntegrationAmount();
            integration = integration.add(integrationAmount);
            // 设置优惠价格 打折
            BigDecimal promotionAmount = orderItem.getPromotionAmount();
            promotion = promotion.add(promotionAmount);
            // 积分和成长值
            gift = gift.add(new BigDecimal(orderItem.getGiftIntegration()));
            growth = growth.add(new BigDecimal(orderItem.getGiftGrowth()));

        }
        // 订单价格相关
        order.setTotalAmount(totalPrice);
        // 应付价格
        order.setPayAmount(totalPrice.add(order.getFreightAmount()));
        // 优惠价格
        order.setPromotionAmount(promotion);
        order.setCouponAmount(coupon);
        order.setIntegrationAmount(integration);
        // 设置积分和成长值信息
        order.setGrowth(growth.intValue());
        order.setIntegration(gift.intValue());
        // 订单删除状态
        order.setDeleteStatus(Constant.orderDeleteStatus.N_DEL.getCode());

    }


    // 构建订单基本信息
    private OrderEntity buildOrder(String orderSn){


        OrderEntity order = new OrderEntity();

        order.setOrderSn(orderSn);

        // 获取收货地址
        R receiveAddressResponse = memberOpenFeignClientService.getReceiveAddress(orderSubmitVoThreadLocal.get().getAddrId());
        // 解析地址
        MemberAddrVo memberAddrVo = receiveAddressResponse.parseType(new TypeReference<MemberAddrVo>() {});

        // 设置收货人信息
        order.setReceiverDetailAddress(memberAddrVo.getDetailAddress());
        order.setReceiverName(memberAddrVo.getName());
        order.setReceiverCity(memberAddrVo.getCity());
        order.setReceiverPhone(memberAddrVo.getPhone());
        order.setReceiverPostCode(memberAddrVo.getPostCode());
        order.setReceiverProvince(memberAddrVo.getProvince());
        order.setReceiverRegion(memberAddrVo.getRegion());
        order.setMemberId(UserLoginInterceptor.user.get().getId());
        order.setMemberUsername(UserLoginInterceptor.user.get().getNickname());
        // 设置运费
        BigDecimal payPrice = orderSubmitVoThreadLocal.get().getPayPrice();
        // 将应付费用转换为字符串
        String originStringPrice = payPrice.toString();
        // 再将字符串价格截取整数部分
        String stringPrice = originStringPrice.substring(0, originStringPrice.indexOf("."));
        // 将截取后得到得字符串转换为整形
        Integer IntPrice = new Integer(stringPrice);
        // 计算出运费
        Integer freightPrice  = IntPrice % 2 + stringPrice.length() * 2;

        order.setFreightAmount(new BigDecimal(freightPrice.toString()));

        // 设置订单状态
        order.setStatus(Constant.orderStatus.NEW.getCode());
        // 几天自动收货
        order.setAutoConfirmDay(7);


        return order;
    }


    // 构建订单项数据
    private List<OrderItemEntity> buildOrderItems(String orderSn) {
        R currentUserCartItemResponse = cartOpenFeignClientService.currentUserCartItem();

        if(currentUserCartItemResponse.parseCode() < 20000){

            List<OrderItemVo> cartItemCheckedList = currentUserCartItemResponse.parseType(new TypeReference<List<OrderItemVo>>() {}, "cartItemList");

            if(cartItemCheckedList != null){

                // 最后确定每个购物项得价格
                List<OrderItemEntity> orderItemEntityList = cartItemCheckedList.stream().map(cartItem -> {
                    OrderItemEntity orderItemEntity = buildOrderItem(cartItem);

                    orderItemEntity.setOrderSn(orderSn);

                    return orderItemEntity;

                }).collect(Collectors.toList());

                return orderItemEntityList;
            }
        }

        return null;

    }



    // 构建某一个订单项
    private OrderItemEntity buildOrderItem(OrderItemVo cartItem) {

        OrderItemEntity orderItemEntity = new OrderItemEntity();

        // 订单信息， 订单号

        // 商品得spu信息 远程查询
        R spuInfoResponse = productOpenFeignClientService.getSpuInfoBySkuId(cartItem.getSkuId());
        if (spuInfoResponse.parseCode() < 20000){
            SpuInfoVo spu = spuInfoResponse.parseType(new TypeReference<SpuInfoVo>() {}, "spu");

            // 封装spu信息
            orderItemEntity.setSpuId(spu.getId());
            orderItemEntity.setSpuName(spu.getSpuName());
            orderItemEntity.setCategoryId(spu.getCatalogId());
            // 远程查询商品品牌
            R brandNameResponse = productOpenFeignClientService.getBrandNameByBrandId(spu.getBrandId());
            if (brandNameResponse.parseCode() < 20000){
                orderItemEntity.setSpuBrand(brandNameResponse.parseType(new TypeReference<String>(){}));
            }
        }

        // 商品得sku信息
        orderItemEntity.setSkuId(cartItem.getSkuId());
        orderItemEntity.setSkuName(cartItem.getTitle());
        orderItemEntity.setSkuPic(cartItem.getImage());
        orderItemEntity.setSkuPrice(cartItem.getPrice());
        orderItemEntity.setSkuAttrsVals(StringUtils.collectionToDelimitedString(cartItem.getSkuAttr(), ";"));
        orderItemEntity.setSkuQuantity(cartItem.getCount());

        // 优惠信息（不做）

        // 积分信息 价格多少就是多少积分
        orderItemEntity.setGiftGrowth(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount().toString())).intValue());
        orderItemEntity.setGiftIntegration(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount().toString())).intValue());

        // 订单项得价格信息 优惠默认0 没有做 TODO 优惠系统
        orderItemEntity.setPromotionAmount(new BigDecimal("0"));
        orderItemEntity.setCouponAmount(new BigDecimal("0"));
        orderItemEntity.setIntegrationAmount(new BigDecimal("0"));
        // 当前订单项得实际金额 总额减去优惠金额
        BigDecimal originPrice = orderItemEntity.getSkuPrice().multiply(new BigDecimal(orderItemEntity.getSkuQuantity().toString()));
        BigDecimal total = originPrice.subtract(orderItemEntity.getCouponAmount())
                .subtract(orderItemEntity.getPromotionAmount())
                .subtract(orderItemEntity.getIntegrationAmount());
        orderItemEntity.setRealAmount(total);

        return orderItemEntity;
    }


    // 保存订单
    private void saveOrder(OrderCreateTo order) {
        OrderEntity orderEntity = order.getOrder();

        // 封装要保存得订单信息
        BeanUtils.copyProperties(order, orderEntity);
        orderEntity.setModifyTime(new Date());
        this.baseMapper.insert(orderEntity);


        // 封装所有包保存得订单项
        List<OrderItemEntity> orderItems = order.getOrderItems();

        orderItems.stream().map(orderItem -> {
            orderItem.setOrderId(orderEntity.getId());
            return orderItem;
        }).forEach(orderItemDao::insert);

    }


    /**
     * 本地事务注解@Transactional
     *
     * 事务隔离级别设置
     * @Transactional(isolatfion = Isolation.REPEATABLE_READ)
     *
     *  事务隔离级别(从小到大)：
     *      READ_UNCOMMITTED(未读提交)：可以读到其他没有提交得事务得数据，这个现象也称之为脏数据，别人可能异常回滚，那么读到得就是无效数据
     *           公司发工资了，领导把5000元打到singo的账号上，但是该事务并未提交，
     *           而singo正好去查看账户，发现工资已经到账，是5000元整，非常高 兴。
     *           可是不幸的是，领导发现发给singo的工资金额不对，是2000元，于是迅速回滚了事务，
     *           修改金额后，将事务提交，最后singo实际的工资只有 2000元，singo空欢喜一场
     *
     *      READ_COMMITTED(读提交)：一个事务可以读取另一个已经提交得事务，多次读取会造成不一样得结果，这个现象称为不可重复读问题
     *           singo拿着工资卡去消费，系统读取到卡里确实有2000元，而此时她的老婆也正好在网上转账，
     *           把singo工资卡的2000元转到另一账户，并在 singo之前提交了事务，当singo扣款时，
     *           系统检查到singo的工资卡已经没有钱，扣款失败，singo十分纳闷，明明卡里有钱
     *
     *      REPEATABLE_READ(可重复读)：MySQL得默认隔离级别，在同一个事务里，select得结果是事务开始时间点状态，因此同样得select操作读到得结果是一样得，但是会有幻读现象
     *          当隔离级别设置为Repeatable read 时，可以避免不可重复读。当singo拿着工资卡去消费时，
     *          一旦系统开始读取工资卡信息（即事务开始），singo的老婆就不可能对该记录进行修改，也就是singo的老婆不能在此时转账
     *
     *          事务A在执行读取操作，需要两次统计数据的总量，前一次查询数据总量后，此时事务B执行了新增数据的操作并提交后，
     *          这个时候事务A读取的数据总量和之前统计的不一样，就像产生了幻觉一样，平白无故的多了几条数据，成为幻读
     *      SERIALIZABLE(序列化)：在这个隔离级别下事务都是串行顺序执行得
     *
     * 事务得传播行为设置
     * @Transactional(propagation = Propagation.REQUIRED)
     *
     *  事务得传播行为：
     *      PROPAGATION_REQUIRED:如果当前没有事务，就创建一个新的事务，如果当前存在事务，就加入该事务，使用当前事务得配置 最常用 默认
     *      PROPAGATION_SUPPORTS:支持当前事务，如果当前存在事务，就加入该事务，如果当前不存在事务，就以非事物执行
     *      PROPAGATION_MANDATORY:支持当前事务，如果当前存在事务，就加入该事务，如果当前不存在事务，就以抛出异常
     *      PROPAGATION_REQUIRED_NEW:创建新事务，无论当前村不存在事务，都创新事务,使用新得配置
     *      PROPAGATION_NOT_SUPPORTED:以非事务方式执行操作，如果当前存在事务，就吧当前事务挂起
     *      PROPAGATION_NEVER:以非事务方式执行操作，如果当前存在事务，则抛出异常
     *      PROPAGATION_NESTED:如果当前存在事务，则在嵌套事务内执行，如果当前没有事务，则执行和PROPAGATION_REQUIRED相似得操作
     *
     */

    // 事务隔离级别设置
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void a(){
        /**
         * b();
         * c();
         * 如果a，b，c三个事务是在同一个类中，那么b，c做的人和设置都没用，都是和a共用一个事务 直接这样调用就是吧c，b得代码放到了a中
         *
         * 因为事务是使用代理对象来控制得，所以调用本类得其他事务，那么其他事务失效，但是可以调用其他类得事务
         * 如果要调用本类得事务，解决办法如下:
         *      引入spring-boot-starter-aop,要使用aspectjweaver代理
         *      开启aspectjweaver动态代理功能@EnableAspectJAutoProxy(exposeProxy = true) 配置是对外暴露代理对象 以后所有得动态代理都是aspectjweaver来创建(没有接口也能创建)
         *      用代理对象调用
         */


        OrderServiceImpl orderService = (OrderServiceImpl) AopContext.currentProxy();
        orderService.b();
        orderService.c();

        // a和b都在一个事务里面
        // c是一个新得事务
        // 出现异常之后，如果a或者b出现异常，那么a和b都会回滚 c不回滚   如果是c出现异常，只是c回滚
    }

    // 事务得传播行为设置
    @Transactional(propagation = Propagation.REQUIRED)
    public void b(){

    }

    // 事务得传播行为设置
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void c(){

    }


    public static void main(String[] args) {
        BigDecimal price = new BigDecimal("3232.32");

        String originStringPrice = price.toString();


            String stringPrice = originStringPrice.substring(0, originStringPrice.indexOf("."));
            Integer IntPrice = new Integer(stringPrice);


            System.out.println(IntPrice % 2 + stringPrice.length() * 2);

    }


}