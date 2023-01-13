package com.lqs.amap.rabbitmp.common;

/**
 * @author 李奇凇
 * @moduleName constant
 * @date 2022/12/30 下午6:12
 * @do 常量类
 */
public class Constant {

    // 交换机
    public static final String EXCHANGE_DIRECT = "ex-direct";
    public static final String EXCHANGE_TOPIC = "ex-topic";
    public static final String EXCHANGE_FANOUT = "ex-fanout";



    // 队列
    public static final String QUEUE_ONE = "queue-direct";
    public static final String QUEUE_TWO = "queue-topic";
    public static final String QUEUE_THREE = "queue-fanout";


    // route-key
    public static final String ROUTE_KEY_ONE = "key-direct";
    public static final String ROUTE_KEY_TWO = "key.#";
    public static final String ROUTE_KEY_THREE = "key-fanout";


}
