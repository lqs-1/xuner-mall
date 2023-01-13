-- MySQL dump 10.13  Distrib 5.7.39, for Linux (x86_64)
--
-- Host: localhost    Database: mall_oms
-- ------------------------------------------------------
-- Server version	5.7.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `mq_message`
--

DROP TABLE IF EXISTS `mq_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mq_message` (
  `message_id` char(32) NOT NULL,
  `content` text,
  `to_exchane` varchar(255) DEFAULT NULL,
  `routing_key` varchar(255) DEFAULT NULL,
  `class_type` varchar(255) DEFAULT NULL,
  `message_status` int(1) DEFAULT '0' COMMENT '0-新建 1-已发送 2-错误抵达 3-已抵达',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mq_message`
--

LOCK TABLES `mq_message` WRITE;
/*!40000 ALTER TABLE `mq_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `mq_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oms_order`
--

DROP TABLE IF EXISTS `oms_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oms_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(20) DEFAULT NULL COMMENT 'member_id',
  `order_sn` char(64) DEFAULT NULL COMMENT '订单号',
  `coupon_id` bigint(20) DEFAULT NULL COMMENT '使用的优惠券',
  `create_time` datetime DEFAULT NULL COMMENT 'create_time',
  `member_username` varchar(200) DEFAULT NULL COMMENT '用户名',
  `total_amount` decimal(18,4) DEFAULT NULL COMMENT '订单总额',
  `pay_amount` decimal(18,4) DEFAULT NULL COMMENT '应付总额',
  `freight_amount` decimal(18,4) DEFAULT NULL COMMENT '运费金额',
  `promotion_amount` decimal(18,4) DEFAULT NULL COMMENT '促销优化金额（促销价、满减、阶梯价）',
  `integration_amount` decimal(18,4) DEFAULT NULL COMMENT '积分抵扣金额',
  `coupon_amount` decimal(18,4) DEFAULT NULL COMMENT '优惠券抵扣金额',
  `discount_amount` decimal(18,4) DEFAULT NULL COMMENT '后台调整订单使用的折扣金额',
  `pay_type` tinyint(4) DEFAULT NULL COMMENT '支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】',
  `source_type` tinyint(4) DEFAULT NULL COMMENT '订单来源[0->PC订单；1->app订单]',
  `status` tinyint(4) DEFAULT NULL COMMENT '订单状态【0->待付款；1->已支付；2->待发货；3->已发货；4->已完成；5->已取消】',
  `delivery_company` varchar(64) DEFAULT NULL COMMENT '物流公司(配送方式)',
  `delivery_sn` varchar(64) DEFAULT NULL COMMENT '物流单号',
  `auto_confirm_day` int(11) DEFAULT NULL COMMENT '自动确认时间（天）',
  `integration` int(11) DEFAULT NULL COMMENT '可以获得的积分',
  `growth` int(11) DEFAULT NULL COMMENT '可以获得的成长值',
  `bill_type` tinyint(4) DEFAULT NULL COMMENT '发票类型[0->不开发票；1->电子发票；2->纸质发票]',
  `bill_header` varchar(255) DEFAULT NULL COMMENT '发票抬头',
  `bill_content` varchar(255) DEFAULT NULL COMMENT '发票内容',
  `bill_receiver_phone` varchar(32) DEFAULT NULL COMMENT '收票人电话',
  `bill_receiver_email` varchar(64) DEFAULT NULL COMMENT '收票人邮箱',
  `receiver_name` varchar(100) DEFAULT NULL COMMENT '收货人姓名',
  `receiver_phone` varchar(32) DEFAULT NULL COMMENT '收货人电话',
  `receiver_post_code` varchar(32) DEFAULT NULL COMMENT '收货人邮编',
  `receiver_province` varchar(32) DEFAULT NULL COMMENT '省份/直辖市',
  `receiver_city` varchar(32) DEFAULT NULL COMMENT '城市',
  `receiver_region` varchar(32) DEFAULT NULL COMMENT '区',
  `receiver_detail_address` varchar(200) DEFAULT NULL COMMENT '详细地址',
  `note` varchar(500) DEFAULT NULL COMMENT '订单备注',
  `confirm_status` tinyint(4) DEFAULT NULL COMMENT '确认收货状态[0->未确认；1->已确认]',
  `delete_status` tinyint(4) DEFAULT NULL COMMENT '删除状态【0->未删除；1->已删除】',
  `use_integration` int(11) DEFAULT NULL COMMENT '下单时使用的积分',
  `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
  `delivery_time` datetime DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime DEFAULT NULL COMMENT '确认收货时间',
  `comment_time` datetime DEFAULT NULL COMMENT '评价时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_sn` (`order_sn`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=193 DEFAULT CHARSET=utf8mb4 COMMENT='订单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oms_order`
--

LOCK TABLES `oms_order` WRITE;
/*!40000 ALTER TABLE `oms_order` DISABLE KEYS */;
INSERT INTO `oms_order` VALUES (182,21,'202301121635420681613454659702452226',NULL,NULL,'Somg_$2a$10$u',5498.0000,5506.0000,8.0000,0.0000,0.0000,0.0000,NULL,NULL,NULL,1,NULL,NULL,7,5498,5498,NULL,NULL,NULL,NULL,NULL,'lqs','2342424355','342345','sichuan','guagnyuan',NULL,'sichuancangxi',NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,'2023-01-12 08:36:19'),(183,21,'202301121642112971613456292247203841',NULL,NULL,'Somg_$2a$10$u',2499.0000,2507.0000,8.0000,0.0000,0.0000,0.0000,NULL,NULL,NULL,5,NULL,NULL,7,2499,2499,NULL,NULL,NULL,NULL,NULL,'lqs','2342424355','342345','sichuan','guagnyuan',NULL,'sichuancangxi',NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,'2023-01-12 08:42:12'),(184,21,'202301121656131601613459823280713729',NULL,NULL,'Somg_$2a$10$u',2499.0000,2507.0000,8.0000,0.0000,0.0000,0.0000,NULL,NULL,NULL,1,NULL,NULL,7,2499,2499,NULL,NULL,NULL,NULL,NULL,'lqs','2342424355','342345','sichuan','guagnyuan',NULL,'sichuancangxi',NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,'2023-01-12 08:56:54'),(185,21,'202301121658432121613460452644417537',NULL,NULL,'Somg_$2a$10$u',2499.0000,2507.0000,8.0000,0.0000,0.0000,0.0000,NULL,NULL,NULL,1,NULL,NULL,7,2499,2499,NULL,NULL,NULL,NULL,NULL,'lqs','2342424355','342345','sichuan','guagnyuan',NULL,'sichuancangxi',NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,'2023-01-12 08:59:23'),(186,21,'202301121756313901613474999241474050',NULL,NULL,'Somg_$2a$10$u',2499.0000,2507.0000,8.0000,0.0000,0.0000,0.0000,NULL,NULL,NULL,5,NULL,NULL,7,2499,2499,NULL,NULL,NULL,NULL,NULL,'lqs','2342424355','342345','sichuan','guagnyuan',NULL,'sichuancangxi',NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,'2023-01-12 09:56:31'),(187,21,'202301121758047531613475390821695490',NULL,NULL,'Somg_$2a$10$u',2499.0000,2507.0000,8.0000,0.0000,0.0000,0.0000,NULL,NULL,NULL,1,NULL,NULL,7,2499,2499,NULL,NULL,NULL,NULL,NULL,'lqs','2342424355','342345','sichuan','guagnyuan',NULL,'sichuancangxi',NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,'2023-01-12 09:58:33'),(188,21,'202301121946346431613502695287783426',NULL,NULL,'Somg_$2a$10$u',2499.0000,2507.0000,8.0000,0.0000,0.0000,0.0000,NULL,NULL,NULL,5,NULL,NULL,7,2499,2499,NULL,NULL,NULL,NULL,NULL,'lqs','2342424355','342345','sichuan','guagnyuan',NULL,'sichuancangxi',NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,'2023-01-12 11:46:35'),(189,21,'202301122040026611613516150682124290',NULL,NULL,'Somg_$2a$10$u',10996.0000,11006.0000,10.0000,0.0000,0.0000,0.0000,NULL,NULL,NULL,5,NULL,NULL,7,10996,10996,NULL,NULL,NULL,NULL,NULL,'lqs','2342424355','342345','sichuan','guagnyuan',NULL,'sichuancangxi',NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,'2023-01-12 12:40:03'),(190,21,'202301122040475551613516338985402369',NULL,NULL,'Somg_$2a$10$u',8497.0000,8505.0000,8.0000,0.0000,0.0000,0.0000,NULL,NULL,NULL,5,NULL,NULL,7,8497,8497,NULL,NULL,NULL,NULL,NULL,'lqs','2342424355','342345','sichuan','guagnyuan',NULL,'sichuancangxi',NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,'2023-01-12 12:40:48'),(191,21,'202301122041467721613516587355308033',NULL,NULL,'Somg_$2a$10$u',2499.0000,2507.0000,8.0000,0.0000,0.0000,0.0000,NULL,NULL,NULL,5,NULL,NULL,7,2499,2499,NULL,NULL,NULL,NULL,NULL,'lqs','2342424355','342345','sichuan','guagnyuan',NULL,'sichuancangxi',NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,'2023-01-12 12:41:47'),(192,21,'202301131023067131613723282417315842',NULL,NULL,'Somg_$2a$10$u',2499.0000,2507.0000,8.0000,0.0000,0.0000,0.0000,NULL,NULL,NULL,1,NULL,NULL,7,2499,2499,NULL,NULL,NULL,NULL,NULL,'lqs','2342424355','342345','sichuan','guagnyuan',NULL,'sichuancangxi',NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,'2023-01-13 02:25:17');
/*!40000 ALTER TABLE `oms_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oms_order_item`
--

DROP TABLE IF EXISTS `oms_order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oms_order_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) DEFAULT NULL COMMENT 'order_id',
  `order_sn` char(64) DEFAULT NULL COMMENT 'order_sn',
  `spu_id` bigint(20) DEFAULT NULL COMMENT 'spu_id',
  `spu_name` varchar(255) DEFAULT NULL COMMENT 'spu_name',
  `spu_pic` varchar(500) DEFAULT NULL COMMENT 'spu_pic',
  `spu_brand` varchar(200) DEFAULT NULL COMMENT '品牌',
  `category_id` bigint(20) DEFAULT NULL COMMENT '商品分类id',
  `sku_id` bigint(20) DEFAULT NULL COMMENT '商品sku编号',
  `sku_name` varchar(255) DEFAULT NULL COMMENT '商品sku名字',
  `sku_pic` varchar(500) DEFAULT NULL COMMENT '商品sku图片',
  `sku_price` decimal(18,4) DEFAULT NULL COMMENT '商品sku价格',
  `sku_quantity` int(11) DEFAULT NULL COMMENT '商品购买的数量',
  `sku_attrs_vals` varchar(500) DEFAULT NULL COMMENT '商品销售属性组合（JSON）',
  `promotion_amount` decimal(18,4) DEFAULT NULL COMMENT '商品促销分解金额',
  `coupon_amount` decimal(18,4) DEFAULT NULL COMMENT '优惠券优惠分解金额',
  `integration_amount` decimal(18,4) DEFAULT NULL COMMENT '积分优惠分解金额',
  `real_amount` decimal(18,4) DEFAULT NULL COMMENT '该商品经过优惠后的分解金额',
  `gift_integration` int(11) DEFAULT NULL COMMENT '赠送积分',
  `gift_growth` int(11) DEFAULT NULL COMMENT '赠送成长值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=354 DEFAULT CHARSET=utf8mb4 COMMENT='订单项信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oms_order_item`
--

LOCK TABLES `oms_order_item` WRITE;
/*!40000 ALTER TABLE `oms_order_item` DISABLE KEYS */;
INSERT INTO `oms_order_item` VALUES (340,182,'202301121635420681613454659702452226',17,'XiaoMi12x',NULL,'小米',225,35,'XiaoMi12x 黑色 12+256','https://graduation-dsign.oss-cn-chengdu.aliyuncs.com/product/spuImages/2022-09-28/d4cffe8f448c42e7987a747351ec626c.png',2999.0000,1,'颜色:黑色;版本:12+256',0.0000,0.0000,0.0000,2999.0000,2999,2999),(341,182,'202301121635420681613454659702452226',17,'XiaoMi12x',NULL,'小米',225,32,'XiaoMi12x 蓝色 12+128','https://graduation-dsign.oss-cn-chengdu.aliyuncs.com/product/spuImages/2022-09-28/be09d861d559402b94b3ae6f1163e9dd.png',2499.0000,1,'颜色:蓝色;版本:12+128',0.0000,0.0000,0.0000,2499.0000,2499,2499),(342,183,'202301121642112971613456292247203841',17,'XiaoMi12x',NULL,'小米',225,34,'XiaoMi12x 黑色 12+128','https://graduation-dsign.oss-cn-chengdu.aliyuncs.com/product/spuImages/2022-09-28/ba13808871d444389cef64598d9f41d1.png',2499.0000,1,'颜色:黑色;版本:12+128',0.0000,0.0000,0.0000,2499.0000,2499,2499),(343,184,'202301121656131601613459823280713729',17,'XiaoMi12x',NULL,'小米',225,34,'XiaoMi12x 黑色 12+128','https://graduation-dsign.oss-cn-chengdu.aliyuncs.com/product/spuImages/2022-09-28/ba13808871d444389cef64598d9f41d1.png',2499.0000,1,'颜色:黑色;版本:12+128',0.0000,0.0000,0.0000,2499.0000,2499,2499),(344,185,'202301121658432121613460452644417537',17,'XiaoMi12x',NULL,'小米',225,32,'XiaoMi12x 蓝色 12+128','https://graduation-dsign.oss-cn-chengdu.aliyuncs.com/product/spuImages/2022-09-28/be09d861d559402b94b3ae6f1163e9dd.png',2499.0000,1,'颜色:蓝色;版本:12+128',0.0000,0.0000,0.0000,2499.0000,2499,2499),(345,186,'202301121756313901613474999241474050',17,'XiaoMi12x',NULL,'小米',225,34,'XiaoMi12x 黑色 12+128','https://graduation-dsign.oss-cn-chengdu.aliyuncs.com/product/spuImages/2022-09-28/ba13808871d444389cef64598d9f41d1.png',2499.0000,1,'颜色:黑色;版本:12+128',0.0000,0.0000,0.0000,2499.0000,2499,2499),(346,187,'202301121758047531613475390821695490',17,'XiaoMi12x',NULL,'小米',225,34,'XiaoMi12x 黑色 12+128','https://graduation-dsign.oss-cn-chengdu.aliyuncs.com/product/spuImages/2022-09-28/ba13808871d444389cef64598d9f41d1.png',2499.0000,1,'颜色:黑色;版本:12+128',0.0000,0.0000,0.0000,2499.0000,2499,2499),(347,188,'202301121946346431613502695287783426',17,'XiaoMi12x',NULL,'小米',225,32,'XiaoMi12x 蓝色 12+128','https://graduation-dsign.oss-cn-chengdu.aliyuncs.com/product/spuImages/2022-09-28/be09d861d559402b94b3ae6f1163e9dd.png',2499.0000,1,'颜色:蓝色;版本:12+128',0.0000,0.0000,0.0000,2499.0000,2499,2499),(348,189,'202301122040026611613516150682124290',17,'XiaoMi12x',NULL,'小米',225,35,'XiaoMi12x 黑色 12+256','https://graduation-dsign.oss-cn-chengdu.aliyuncs.com/product/spuImages/2022-09-28/d4cffe8f448c42e7987a747351ec626c.png',2999.0000,2,'颜色:黑色;版本:12+256',0.0000,0.0000,0.0000,5998.0000,5998,5998),(349,189,'202301122040026611613516150682124290',17,'XiaoMi12x',NULL,'小米',225,32,'XiaoMi12x 蓝色 12+128','https://graduation-dsign.oss-cn-chengdu.aliyuncs.com/product/spuImages/2022-09-28/be09d861d559402b94b3ae6f1163e9dd.png',2499.0000,2,'颜色:蓝色;版本:12+128',0.0000,0.0000,0.0000,4998.0000,4998,4998),(350,190,'202301122040475551613516338985402369',17,'XiaoMi12x',NULL,'小米',225,35,'XiaoMi12x 黑色 12+256','https://graduation-dsign.oss-cn-chengdu.aliyuncs.com/product/spuImages/2022-09-28/d4cffe8f448c42e7987a747351ec626c.png',2999.0000,2,'颜色:黑色;版本:12+256',0.0000,0.0000,0.0000,5998.0000,5998,5998),(351,190,'202301122040475551613516338985402369',17,'XiaoMi12x',NULL,'小米',225,32,'XiaoMi12x 蓝色 12+128','https://graduation-dsign.oss-cn-chengdu.aliyuncs.com/product/spuImages/2022-09-28/be09d861d559402b94b3ae6f1163e9dd.png',2499.0000,1,'颜色:蓝色;版本:12+128',0.0000,0.0000,0.0000,2499.0000,2499,2499),(352,191,'202301122041467721613516587355308033',17,'XiaoMi12x',NULL,'小米',225,36,'XiaoMi12x 白色 12+128','https://graduation-dsign.oss-cn-chengdu.aliyuncs.com/product/spuImages/2022-09-28/be09d861d559402b94b3ae6f1163e9dd.png',2499.0000,1,'颜色:白色;版本:12+128',0.0000,0.0000,0.0000,2499.0000,2499,2499),(353,192,'202301131023067131613723282417315842',17,'XiaoMi12x',NULL,'小米',225,36,'XiaoMi12x 白色 12+128','https://graduation-dsign.oss-cn-chengdu.aliyuncs.com/product/spuImages/2022-09-28/be09d861d559402b94b3ae6f1163e9dd.png',2499.0000,1,'颜色:白色;版本:12+128',0.0000,0.0000,0.0000,2499.0000,2499,2499);
/*!40000 ALTER TABLE `oms_order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oms_order_operate_history`
--

DROP TABLE IF EXISTS `oms_order_operate_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oms_order_operate_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
  `operate_man` varchar(100) DEFAULT NULL COMMENT '操作人[用户；系统；后台管理员]',
  `create_time` datetime DEFAULT NULL COMMENT '操作时间',
  `order_status` tinyint(4) DEFAULT NULL COMMENT '订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】',
  `note` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单操作历史记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oms_order_operate_history`
--

LOCK TABLES `oms_order_operate_history` WRITE;
/*!40000 ALTER TABLE `oms_order_operate_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `oms_order_operate_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oms_order_return_apply`
--

DROP TABLE IF EXISTS `oms_order_return_apply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oms_order_return_apply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) DEFAULT NULL COMMENT 'order_id',
  `sku_id` bigint(20) DEFAULT NULL COMMENT '退货商品id',
  `order_sn` char(32) DEFAULT NULL COMMENT '订单编号',
  `create_time` datetime DEFAULT NULL COMMENT '申请时间',
  `member_username` varchar(64) DEFAULT NULL COMMENT '会员用户名',
  `return_amount` decimal(18,4) DEFAULT NULL COMMENT '退款金额',
  `return_name` varchar(100) DEFAULT NULL COMMENT '退货人姓名',
  `return_phone` varchar(20) DEFAULT NULL COMMENT '退货人电话',
  `status` tinyint(1) DEFAULT NULL COMMENT '申请状态[0->待处理；1->退货中；2->已完成；3->已拒绝]',
  `handle_time` datetime DEFAULT NULL COMMENT '处理时间',
  `sku_img` varchar(500) DEFAULT NULL COMMENT '商品图片',
  `sku_name` varchar(200) DEFAULT NULL COMMENT '商品名称',
  `sku_brand` varchar(200) DEFAULT NULL COMMENT '商品品牌',
  `sku_attrs_vals` varchar(500) DEFAULT NULL COMMENT '商品销售属性(JSON)',
  `sku_count` int(11) DEFAULT NULL COMMENT '退货数量',
  `sku_price` decimal(18,4) DEFAULT NULL COMMENT '商品单价',
  `sku_real_price` decimal(18,4) DEFAULT NULL COMMENT '商品实际支付单价',
  `reason` varchar(200) DEFAULT NULL COMMENT '原因',
  `description述` varchar(500) DEFAULT NULL COMMENT '描述',
  `desc_pics` varchar(2000) DEFAULT NULL COMMENT '凭证图片，以逗号隔开',
  `handle_note` varchar(500) DEFAULT NULL COMMENT '处理备注',
  `handle_man` varchar(200) DEFAULT NULL COMMENT '处理人员',
  `receive_man` varchar(100) DEFAULT NULL COMMENT '收货人',
  `receive_time` datetime DEFAULT NULL COMMENT '收货时间',
  `receive_note` varchar(500) DEFAULT NULL COMMENT '收货备注',
  `receive_phone` varchar(20) DEFAULT NULL COMMENT '收货电话',
  `company_address` varchar(500) DEFAULT NULL COMMENT '公司收货地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单退货申请';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oms_order_return_apply`
--

LOCK TABLES `oms_order_return_apply` WRITE;
/*!40000 ALTER TABLE `oms_order_return_apply` DISABLE KEYS */;
/*!40000 ALTER TABLE `oms_order_return_apply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oms_order_return_reason`
--

DROP TABLE IF EXISTS `oms_order_return_reason`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oms_order_return_reason` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(200) DEFAULT NULL COMMENT '退货原因名',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `status` tinyint(1) DEFAULT NULL COMMENT '启用状态',
  `create_time` datetime DEFAULT NULL COMMENT 'create_time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='退货原因';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oms_order_return_reason`
--

LOCK TABLES `oms_order_return_reason` WRITE;
/*!40000 ALTER TABLE `oms_order_return_reason` DISABLE KEYS */;
/*!40000 ALTER TABLE `oms_order_return_reason` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oms_order_setting`
--

DROP TABLE IF EXISTS `oms_order_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oms_order_setting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `flash_order_overtime` int(11) DEFAULT NULL COMMENT '秒杀订单超时关闭时间(分)',
  `normal_order_overtime` int(11) DEFAULT NULL COMMENT '正常订单超时时间(分)',
  `confirm_overtime` int(11) DEFAULT NULL COMMENT '发货后自动确认收货时间（天）',
  `finish_overtime` int(11) DEFAULT NULL COMMENT '自动完成交易时间，不能申请退货（天）',
  `comment_overtime` int(11) DEFAULT NULL COMMENT '订单完成后自动好评时间（天）',
  `member_level` tinyint(2) DEFAULT NULL COMMENT '会员等级【0-不限会员等级，全部通用；其他-对应的其他会员等级】',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单配置信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oms_order_setting`
--

LOCK TABLES `oms_order_setting` WRITE;
/*!40000 ALTER TABLE `oms_order_setting` DISABLE KEYS */;
/*!40000 ALTER TABLE `oms_order_setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oms_payment_info`
--

DROP TABLE IF EXISTS `oms_payment_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oms_payment_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_sn` char(64) DEFAULT NULL COMMENT '订单号（对外业务号）',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
  `alipay_trade_no` varchar(50) DEFAULT NULL COMMENT '支付宝交易流水号',
  `total_amount` decimal(18,4) DEFAULT NULL COMMENT '支付总金额',
  `subject` varchar(200) DEFAULT NULL COMMENT '交易内容',
  `payment_status` varchar(20) DEFAULT NULL COMMENT '支付状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `confirm_time` datetime DEFAULT NULL COMMENT '确认时间',
  `callback_content` varchar(4000) DEFAULT NULL COMMENT '回调内容',
  `callback_time` datetime DEFAULT NULL COMMENT '回调时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_sn` (`order_sn`) USING BTREE,
  UNIQUE KEY `alipay_trade_no` (`alipay_trade_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oms_payment_info`
--

LOCK TABLES `oms_payment_info` WRITE;
/*!40000 ALTER TABLE `oms_payment_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `oms_payment_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oms_refund_info`
--

DROP TABLE IF EXISTS `oms_refund_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oms_refund_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_return_id` bigint(20) DEFAULT NULL COMMENT '退款的订单',
  `refund` decimal(18,4) DEFAULT NULL COMMENT '退款金额',
  `refund_sn` varchar(64) DEFAULT NULL COMMENT '退款交易流水号',
  `refund_status` tinyint(1) DEFAULT NULL COMMENT '退款状态',
  `refund_channel` tinyint(4) DEFAULT NULL COMMENT '退款渠道[1-支付宝，2-微信，3-银联，4-汇款]',
  `refund_content` varchar(5000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='退款信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oms_refund_info`
--

LOCK TABLES `oms_refund_info` WRITE;
/*!40000 ALTER TABLE `oms_refund_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `oms_refund_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `undo_log`
--

DROP TABLE IF EXISTS `undo_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `undo_log` (
  `branch_id` bigint(20) NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(100) NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int(11) NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='AT transaction mode undo table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `undo_log`
--

LOCK TABLES `undo_log` WRITE;
/*!40000 ALTER TABLE `undo_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `undo_log` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-13  7:55:10
