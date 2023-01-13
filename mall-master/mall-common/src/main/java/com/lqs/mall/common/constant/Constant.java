package com.lqs.mall.common.constant;

/**
 * @author 李奇凇
 * @date 2022年07月30日 下午10:49
 * @do 这里配置程序中需要使用的工具常量
 */
public class Constant {
    // 分页参数常量
    public static final String PAGE = "page";
    public static final String LIMIT = "limit";
    public static final String ORDERFILED = "orderFiled";
    public static final String ORDERTYPE = "orderType";


    // 从R中直接获取的时候的keyName
    public static final String RESPONSE_DATA_KEY = "data";
    // 从R中获取code时候的keyName
    public static final String RESPONSE_CODE_KEY = "code";
    // 从R中获取msg时候的keyName
    public static final String RESPONSE_MSG_KEY = "msg";



    // sku数据在es中的索引
    public static final String PRODUCT_INDEX = "product";
    // es搜索的每页数据大小
    public static final Integer PRODUCT_PAGESIZE = 1;
    // 搜索页面显示页码个数
    public static final Integer SEARCH_PAGE_MARK_SIZE = 5;


    // 注册验证码的key前缀
    public static final String REGISTER_SMS_PREFIX = "register_sms";
    // 注册验证码的过期时间
    public static final Long REGISTER_SMSCODE_INVALID_TIME = 300L;


    // 缓存分组名字catalog,在做www.mall.com的时候首页的三级数据缓存
    public static final String CATALOG_GROUP =  "catalog";
    // catalog下的缓存key字如下
    public static final String CATALOG_LEVEL_ONE_CACHE_KEY = "'catalogLevelOneList'";
    public static final String CATALOG_CACHE_KEY = "'catalogList'";

    // 用户登录之后存cookie的keyName
    public static final String ACCOUNT_SESSION_KEY = "account";

    // 普通注册登录生成的nickName前缀
    public static final String COMMON_REGISTER_NICKNAME_PREFIX = "Somg_";

    // 社交微博登录的常量,请求的域名
    public static final String OAUTH2_WEIBO_HOST = "https://api.weibo.com";
    public static final String OAUTH2_WEIBO_REQUEST_ACCESS_TOKEN_PATH = "/oauth2/access_token";
    public static final String OAUTH2_WEIBO_REQUEST_USER_INFO_PATH = "/2/users/show.json";
    // 社交微博登录的时候换取access_token的参数名常量
    public static final String WEIBO_CLIENT_ID = "client_id";
    public static final String WEIBO_CLIENT_SECRET = "client_secret";
    public static final String WEIBO_GRANT_TYPE = "grant_type";
    public static final String WEIBO_REDIRECT_URI = "redirect_uri";
    public static final String WEIBO_CODE = "code";
    // 社交微博登录的时候换取用户信息的参数常量
    public static final String WEIBO_ACCESS_TOKEN = "access_token";
    public static final String WEIBO_U_ID = "uid";

    // 用户在访问购物车的时候，如果没有登录，临时分配的用户的user-key
    public static final String TEMP_USER_KEY = "user-key";
    // 临时分配的用户的user-key这个cookie的过期时间
    public static final Integer TEMP_USER_KEY_TIMEOUT = 60*60*24*30;
    // 购物车的前缀，因为是存放在redis中的，所有用前缀作为区分
    public static final String CART_PREFIX = "mall:cart";


    // 延时队列-商品库存交换机
    public static final String WARE_STOCK_EXCHANGE = "ware-stock-exchange";
    // 延时队列-商品库存延时队列
    public static final String WARE_STOCK_TIME_DELAY_QUEUE = "ware-stock-time-delay-queue";
    // 延时队列-商品库存延时队列routeKey
    public static final String WARE_STOCK_TIME_DELAY_QUEUE_ROUTE_KEY = "ware-stock-time-delay-key";
    // 延时队列-商品库存解锁队列
    public static final String WARE_STOCK_RELEASE_QUEUE = "ware-stock-release-queue";
    // 延时队列-商品库存解锁队列routeKey
    public static final String WARE_STOCK_RELEASE_QUEUE_ROUTE_KEY = "ware-stock-release-queue-key";
    // 延时时间 解锁库存应该比订单取消时间更快 这个时间用于自动解锁
    public static final Integer WARE_STOCK_TIME_DELAY_QUEUE_TTL_MILLI = 180000;


    // 延时队列-订单交换机
    public static final String ORDER_CANCEL_EXCHANGE = "order-cancel-exchange";
    // 延时队列-订单取消延时队列
    public static final String ORDER_CANCEL_TIME_DELAY_QUEUE = "order-cancel-time-delay-queue";
    // 延时队列-订单取消延时队列routeKey
    public static final String ORDER_CANCEL_TIME_DELAY_QUEUE_ROUTE_KEY = "order-cancel-time-delay-queue-key";
    // 延时队列-订单取消执行队列
    public static final String ORDER_CANCEL_INVOKE_QUEUE = "order-cancel-invoke-queue";
    // 延时队列-订单取消执行routeKey
    public static final String ORDER_CANCEL_INVOKE_QUEUE_ROUTE_KEY = "order-cancel-invoke-queue-key";

    // 订单自动取消之后,立刻发送消息给mq 发给order-cancel-exchange  ----->  ware-stock-release-queue
    public static final String ORDER_CANCEL_ED_RELEASE_WARE_STOCK_ROUTE_KEY = "order-cancel-ed-release-ware-stock-key";
    // 延时时间 这个时间用于自动取消订单
    public static final Integer ORDER_CANCEL_TIME_DELAY_QUEUE_TTL = 120000;


    // 支付主题 和 描述
    public static final String ORDER_PAY_SUBJECT = "勋儿商城-支付宝";
    public static final String ORDER_PAY_DESC = "勋儿商城-支付宝";


    // 订单支付自动收单时间
    public static final String ORDER_PAY_TIMEOUT_EXPIRE = "1m";


    // 订单列表页每页多少条数据
    public static final String ORDER_LIST_PAGE_LIMIT = "4";



    // 用户的订单令牌前缀
    public static final String USER_ORDER_TOKEN_PREFIX = "order:token:";



    public static void main(String[] args) {
        System.out.println("haha");
    }


    // 基本属性和销售属性管理的时候使用
    public enum attrType{
        ATTR_BASE_TYPE(1, "基本属性", "base"),
        ATTR_SALE_TYPE(0, "销售属性", "sale");

        private Integer code;
        private String msg;
        private String desc;

        attrType(Integer code, String msg, String desc){
            this.code = code;
            this.msg = msg;
            this.desc = desc;
        }

        public String getMsg() {
            return msg;
        }

        public Integer getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }

    // spu状态
    public static enum spuStatus{
        CREATE(0, "新建"),
        UP(1, "上架"),
        DOWN(2, "下架");

        private Integer code;
        private String msg;

        spuStatus(Integer code, String msg){
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }


    // 采购单的状态
    public static enum purchaseStatus{
        CREATE(0, "新建"),
        ASSIGNED(1, "已分配"),
        RECIVE(2, "已领取"),
        FINISH(3, "已完成"),
        HASERROR(4, "出现异常");


        private Integer code;
        private String msg;

        purchaseStatus(Integer code, String msg){
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

    // 采购项的状态
    public static enum purchaseDetail{
        NEW(0, "新建"),
        RECIVE(1, "已分配"),
        RECIVEING(2, "正在采购"),
        FINISH(3, "已完成"),
        FAIL(4, "采购失败");


        private Integer code;
        private String msg;

        purchaseDetail(Integer code, String msg){
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }



    // 采购项的状态
    public static enum subMitOrderStatus{
        SUCCESS(0, "订单创建成功"),
        PRICE_CHECK_FAIL(1, "页面价格验证失败"),
        SKU_WARE_STOCK_LOCK_FAIL(2, "商品库存锁定失败"),
        TOKEN_VALIDATE_FAIL(3, "防重令牌验证失败"),
        ORDER_SKU_WARE_STOCK_UNENGHT(4, "订单商品库存不足"),
        ORDER_EXCEPTION(5, "订单异常"),
        SELECTOR(6, "这是一个选择器"),;

        private Integer code;
        private String msg;

        subMitOrderStatus(Integer code, String msg){
            this.code = code;
            this.msg = msg;
        }

        public String getDetailMessage(Integer code){
            if (code == 1){
                return PRICE_CHECK_FAIL.getMsg();
            } else if (code == 2) {
                return SKU_WARE_STOCK_LOCK_FAIL.getMsg();
            }

            return TOKEN_VALIDATE_FAIL.getMsg();
        }

        public Integer getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }


    // 订单状态
    public static enum orderStatus{
        NEW(0, "待支付"),
        PAY_ED(1, "已支付"),
        WAIT_SEND(2, "待发货"),
        SEND(3, "已发货"),
        FINISH(4, "已完成"),
        CANCEL(5, "已取消");


        private Integer code;
        private String msg;

        orderStatus(Integer code, String msg){
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }


    // 订单删除状态
    public static enum orderDeleteStatus{
        N_DEL(0, "未删除"),
        DEL(1, "已删除");


        private Integer code;
        private String msg;

        orderDeleteStatus(Integer code, String msg){
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

    // 库存锁定状态
    public static enum wareStockStatus{
        LOCK(0, "已锁定"),
        RELEASE(1, "已解锁"),
        DEDUCTION(2, "已扣除");


        private Integer code;
        private String msg;

        wareStockStatus(Integer code, String msg){
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

}





