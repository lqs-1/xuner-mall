-- MySQL dump 10.13  Distrib 5.7.39, for Linux (x86_64)
--
-- Host: localhost    Database: mall_admin
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
-- Table structure for table `QRTZ_BLOB_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_BLOB_TRIGGERS`
--

LOCK TABLES `QRTZ_BLOB_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_BLOB_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_BLOB_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_CALENDARS`
--

DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_CALENDARS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_CALENDARS`
--

LOCK TABLES `QRTZ_CALENDARS` WRITE;
/*!40000 ALTER TABLE `QRTZ_CALENDARS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_CALENDARS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_CRON_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_CRON_TRIGGERS`
--

LOCK TABLES `QRTZ_CRON_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_CRON_TRIGGERS` DISABLE KEYS */;
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('RenrenScheduler','TASK_1','DEFAULT','0 0/30 * * * ?','Asia/Shanghai');
/*!40000 ALTER TABLE `QRTZ_CRON_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_FIRED_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_FIRED_TRIGGERS`
--

LOCK TABLES `QRTZ_FIRED_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_FIRED_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_FIRED_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_JOB_DETAILS`
--

DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_JOB_DETAILS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_JOB_DETAILS`
--

LOCK TABLES `QRTZ_JOB_DETAILS` WRITE;
/*!40000 ALTER TABLE `QRTZ_JOB_DETAILS` DISABLE KEYS */;
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('RenrenScheduler','TASK_1','DEFAULT',NULL,'io.renren.modules.job.utils.ScheduleJob','0','0','0','0',_binary '�\�\0sr\0org.quartz.JobDataMap���迩�\�\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap�\�\��\�](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\�.�(v\n\�\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\��\�`\�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\rJOB_PARAM_KEYsr\0.io.renren.modules.job.entity.ScheduleJobEntity\0\0\0\0\0\0\0\0L\0beanNamet\0Ljava/lang/String;L\0\ncreateTimet\0Ljava/util/Date;L\0cronExpressionq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0paramsq\0~\0	L\0remarkq\0~\0	L\0statust\0Ljava/lang/Integer;xpt\0testTasksr\0java.util.Datehj�KYt\0\0xpw\0\0m~Xwpxt\00 0/30 * * * ?sr\0java.lang.Long;�\�̏#\�\0J\0valuexr\0java.lang.Number����\��\0\0xp\0\0\0\0\0\0\0t\0renrent\0参数测试sr\0java.lang.Integer⠤���8\0I\0valuexq\0~\0\0\0\0\0x\0');
/*!40000 ALTER TABLE `QRTZ_JOB_DETAILS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_LOCKS`
--

DROP TABLE IF EXISTS `QRTZ_LOCKS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_LOCKS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_LOCKS`
--

LOCK TABLES `QRTZ_LOCKS` WRITE;
/*!40000 ALTER TABLE `QRTZ_LOCKS` DISABLE KEYS */;
INSERT INTO `QRTZ_LOCKS` VALUES ('RenrenScheduler','STATE_ACCESS'),('RenrenScheduler','TRIGGER_ACCESS');
/*!40000 ALTER TABLE `QRTZ_LOCKS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_PAUSED_TRIGGER_GRPS`
--

DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_PAUSED_TRIGGER_GRPS`
--

LOCK TABLES `QRTZ_PAUSED_TRIGGER_GRPS` WRITE;
/*!40000 ALTER TABLE `QRTZ_PAUSED_TRIGGER_GRPS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_PAUSED_TRIGGER_GRPS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_SCHEDULER_STATE`
--

DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_SCHEDULER_STATE`
--

LOCK TABLES `QRTZ_SCHEDULER_STATE` WRITE;
/*!40000 ALTER TABLE `QRTZ_SCHEDULER_STATE` DISABLE KEYS */;
INSERT INTO `QRTZ_SCHEDULER_STATE` VALUES ('RenrenScheduler','grade-TUF-Gaming-FX505GT-FX95GT1673426358047',1673435797819,15000);
/*!40000 ALTER TABLE `QRTZ_SCHEDULER_STATE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_SIMPLE_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_SIMPLE_TRIGGERS`
--

LOCK TABLES `QRTZ_SIMPLE_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_SIMPLE_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_SIMPLE_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_SIMPROP_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_SIMPROP_TRIGGERS`
--

LOCK TABLES `QRTZ_SIMPROP_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_SIMPROP_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_SIMPROP_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_TRIGGERS`
--

LOCK TABLES `QRTZ_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_TRIGGERS` DISABLE KEYS */;
INSERT INTO `QRTZ_TRIGGERS` VALUES ('RenrenScheduler','TASK_1','DEFAULT','TASK_1','DEFAULT',NULL,1673436600000,1673434800000,5,'WAITING','CRON',1569813024000,0,NULL,2,_binary '�\�\0sr\0org.quartz.JobDataMap���迩�\�\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap�\�\��\�](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\�.�(v\n\�\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\��\�`\�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\rJOB_PARAM_KEYsr\0.io.renren.modules.job.entity.ScheduleJobEntity\0\0\0\0\0\0\0\0L\0beanNamet\0Ljava/lang/String;L\0\ncreateTimet\0Ljava/util/Date;L\0cronExpressionq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0paramsq\0~\0	L\0remarkq\0~\0	L\0statust\0Ljava/lang/Integer;xpt\0testTasksr\0java.util.Datehj�KYt\0\0xpw\0\0m~Xwpxt\00 0/30 * * * ?sr\0java.lang.Long;�\�̏#\�\0J\0valuexr\0java.lang.Number����\��\0\0xp\0\0\0\0\0\0\0t\0renrent\0参数测试sr\0java.lang.Integer⠤���8\0I\0valuexq\0~\0\0\0\0\0x\0');
/*!40000 ALTER TABLE `QRTZ_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule_job`
--

DROP TABLE IF EXISTS `schedule_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedule_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(4) DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='定时任务';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule_job`
--

LOCK TABLES `schedule_job` WRITE;
/*!40000 ALTER TABLE `schedule_job` DISABLE KEYS */;
INSERT INTO `schedule_job` VALUES (1,'testTask','renren','0 0/30 * * * ?',0,'参数测试','2019-09-30 02:46:30');
/*!40000 ALTER TABLE `schedule_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule_job_log`
--

DROP TABLE IF EXISTS `schedule_job_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedule_job_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` bigint(20) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) DEFAULT NULL COMMENT '失败信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`),
  KEY `job_id` (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1408 DEFAULT CHARSET=utf8mb4 COMMENT='定时任务日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule_job_log`
--

LOCK TABLES `schedule_job_log` WRITE;
/*!40000 ALTER TABLE `schedule_job_log` DISABLE KEYS */;
INSERT INTO `schedule_job_log` VALUES (1,1,'testTask','renren',0,NULL,0,'2019-09-30 11:30:00'),(2,1,'testTask','renren',0,NULL,0,'2019-09-30 12:00:00'),(3,1,'testTask','renren',0,NULL,1,'2019-09-30 12:30:00'),(4,1,'testTask','renren',0,NULL,1,'2019-09-30 13:00:00'),(5,1,'testTask','renren',0,NULL,1,'2019-09-30 13:30:00'),(6,1,'testTask','renren',0,NULL,1,'2019-09-30 14:00:00'),(7,1,'testTask','renren',0,NULL,1,'2019-09-30 14:30:00'),(8,1,'testTask','renren',0,NULL,1,'2019-09-30 15:00:00'),(9,1,'testTask','renren',0,NULL,0,'2019-09-30 15:30:00'),(10,1,'testTask','renren',0,NULL,1,'2019-09-30 16:00:00'),(11,1,'testTask','renren',0,NULL,0,'2019-09-30 16:30:00'),(12,1,'testTask','renren',0,NULL,1,'2019-09-30 17:00:00'),(13,1,'testTask','renren',0,NULL,1,'2019-09-30 17:30:00'),(14,1,'testTask','renren',0,NULL,1,'2019-09-30 18:00:00'),(15,1,'testTask','renren',0,NULL,1,'2019-09-30 18:30:00'),(16,1,'testTask','renren',0,NULL,1,'2019-09-30 19:00:00'),(17,1,'testTask','renren',0,NULL,1,'2019-09-30 19:30:00'),(18,1,'testTask','renren',0,NULL,0,'2019-09-30 20:00:00'),(19,1,'testTask','renren',0,NULL,0,'2019-09-30 20:30:00'),(20,1,'testTask','renren',0,NULL,1,'2019-09-30 21:00:00'),(21,1,'testTask','renren',0,NULL,1,'2019-09-30 21:30:00'),(22,1,'testTask','renren',0,NULL,1,'2019-09-30 22:00:00'),(23,1,'testTask','renren',0,NULL,1,'2019-09-30 22:30:00'),(24,1,'testTask','renren',0,NULL,1,'2019-09-30 23:00:00'),(25,1,'testTask','renren',0,NULL,0,'2019-09-30 23:30:00'),(26,1,'testTask','renren',0,NULL,9,'2019-10-01 00:00:00'),(27,1,'testTask','renren',0,NULL,1,'2019-10-01 00:30:00'),(28,1,'testTask','renren',0,NULL,0,'2019-10-01 01:00:00'),(29,1,'testTask','renren',0,NULL,0,'2019-10-01 01:30:00'),(30,1,'testTask','renren',0,NULL,0,'2019-10-01 02:00:00'),(31,1,'testTask','renren',0,NULL,1,'2019-10-01 02:30:00'),(32,1,'testTask','renren',0,NULL,0,'2019-10-01 03:00:00'),(33,1,'testTask','renren',0,NULL,0,'2019-10-01 03:30:00'),(34,1,'testTask','renren',0,NULL,0,'2019-10-01 04:00:00'),(35,1,'testTask','renren',0,NULL,0,'2019-10-01 04:30:00'),(36,1,'testTask','renren',0,NULL,0,'2019-10-01 05:00:00'),(37,1,'testTask','renren',0,NULL,0,'2019-10-01 05:30:00'),(38,1,'testTask','renren',0,NULL,0,'2019-10-01 06:00:00'),(39,1,'testTask','renren',0,NULL,1,'2019-10-01 06:30:00'),(40,1,'testTask','renren',0,NULL,1,'2019-10-01 07:00:00'),(41,1,'testTask','renren',0,NULL,1,'2019-10-01 07:30:00'),(42,1,'testTask','renren',0,NULL,1,'2019-10-01 08:00:00'),(43,1,'testTask','renren',0,NULL,0,'2019-10-01 08:30:00'),(44,1,'testTask','renren',0,NULL,0,'2019-10-01 09:00:00'),(45,1,'testTask','renren',0,NULL,0,'2019-10-01 09:30:00'),(46,1,'testTask','renren',0,NULL,0,'2019-10-01 10:00:00'),(47,1,'testTask','renren',0,NULL,1,'2019-10-01 10:30:00'),(48,1,'testTask','renren',0,NULL,0,'2019-10-01 11:00:00'),(49,1,'testTask','renren',0,NULL,0,'2019-10-01 11:30:00'),(50,1,'testTask','renren',0,NULL,0,'2019-10-01 12:00:00'),(51,1,'testTask','renren',0,NULL,0,'2019-10-01 12:30:00'),(52,1,'testTask','renren',0,NULL,0,'2019-10-01 13:00:00'),(53,1,'testTask','renren',0,NULL,0,'2019-10-01 13:30:00'),(54,1,'testTask','renren',0,NULL,1,'2019-10-01 14:00:00'),(55,1,'testTask','renren',0,NULL,0,'2019-10-01 14:30:00'),(56,1,'testTask','renren',0,NULL,1,'2019-10-01 19:00:00'),(57,1,'testTask','renren',0,NULL,0,'2019-10-01 19:30:00'),(58,1,'testTask','renren',0,NULL,0,'2019-10-01 20:00:00'),(59,1,'testTask','renren',0,NULL,0,'2019-10-01 20:30:00'),(60,1,'testTask','renren',0,NULL,0,'2019-10-01 21:00:00'),(61,1,'testTask','renren',0,NULL,0,'2019-10-01 21:30:00'),(62,1,'testTask','renren',0,NULL,1,'2019-10-01 22:00:00'),(63,1,'testTask','renren',0,NULL,0,'2019-10-01 22:30:00'),(64,1,'testTask','renren',0,NULL,1,'2019-10-01 23:00:00'),(65,1,'testTask','renren',0,NULL,1,'2019-10-01 23:30:00'),(66,1,'testTask','renren',0,NULL,5,'2019-10-02 00:00:00'),(67,1,'testTask','renren',0,NULL,1,'2019-10-02 00:30:00'),(68,1,'testTask','renren',0,NULL,0,'2019-10-02 10:30:00'),(69,1,'testTask','renren',0,NULL,1,'2019-10-02 11:00:00'),(70,1,'testTask','renren',0,NULL,1,'2019-10-02 11:30:00'),(71,1,'testTask','renren',0,NULL,1,'2019-10-02 12:00:00'),(72,1,'testTask','renren',0,NULL,1,'2019-10-02 12:30:00'),(73,1,'testTask','renren',0,NULL,1,'2019-10-02 13:00:00'),(74,1,'testTask','renren',0,NULL,1,'2019-10-28 22:00:00'),(75,1,'testTask','renren',0,NULL,1,'2019-10-28 22:30:00'),(76,1,'testTask','renren',0,NULL,1,'2019-10-28 23:30:00'),(77,1,'testTask','renren',0,NULL,5,'2019-10-29 00:00:00'),(78,1,'testTask','renren',0,NULL,2,'2019-10-29 00:30:00'),(79,1,'testTask','renren',0,NULL,1,'2019-10-29 01:00:00'),(80,1,'testTask','renren',0,NULL,1,'2019-10-29 01:30:00'),(81,1,'testTask','renren',0,NULL,1,'2019-10-29 02:00:00'),(82,1,'testTask','renren',0,NULL,1,'2019-10-29 02:30:00'),(83,1,'testTask','renren',0,NULL,0,'2019-10-29 03:00:00'),(84,1,'testTask','renren',0,NULL,1,'2019-10-29 03:30:00'),(85,1,'testTask','renren',0,NULL,0,'2019-10-29 04:00:00'),(86,1,'testTask','renren',0,NULL,1,'2019-10-29 04:30:00'),(87,1,'testTask','renren',0,NULL,1,'2019-10-29 05:00:00'),(88,1,'testTask','renren',0,NULL,1,'2019-10-29 05:30:00'),(89,1,'testTask','renren',0,NULL,1,'2019-10-29 06:00:00'),(90,1,'testTask','renren',0,NULL,1,'2019-10-29 06:30:00'),(91,1,'testTask','renren',0,NULL,1,'2019-10-29 07:00:00'),(92,1,'testTask','renren',0,NULL,1,'2019-10-29 07:30:00'),(93,1,'testTask','renren',0,NULL,0,'2019-10-29 08:00:00'),(94,1,'testTask','renren',0,NULL,1,'2019-10-29 08:30:00'),(95,1,'testTask','renren',0,NULL,4,'2019-10-29 09:00:00'),(96,1,'testTask','renren',0,NULL,1,'2019-10-29 09:30:00'),(97,1,'testTask','renren',0,NULL,1,'2019-10-29 10:00:00'),(98,1,'testTask','renren',0,NULL,1,'2019-10-29 10:30:00'),(99,1,'testTask','renren',0,NULL,1,'2019-10-29 11:00:00'),(100,1,'testTask','renren',0,NULL,0,'2019-10-29 11:30:00'),(101,1,'testTask','renren',0,NULL,1,'2019-10-29 12:00:00'),(102,1,'testTask','renren',0,NULL,1,'2019-10-29 12:30:00'),(103,1,'testTask','renren',0,NULL,1,'2019-10-29 13:00:00'),(104,1,'testTask','renren',0,NULL,1,'2019-10-29 13:30:00'),(105,1,'testTask','renren',0,NULL,1,'2019-10-29 14:00:00'),(106,1,'testTask','renren',0,NULL,1,'2019-10-29 14:30:00'),(107,1,'testTask','renren',0,NULL,1,'2019-10-29 15:00:00'),(108,1,'testTask','renren',0,NULL,1,'2019-10-29 15:30:00'),(109,1,'testTask','renren',0,NULL,1,'2019-10-29 16:00:00'),(110,1,'testTask','renren',0,NULL,1,'2019-10-29 16:30:00'),(111,1,'testTask','renren',0,NULL,1,'2019-10-29 17:00:00'),(112,1,'testTask','renren',0,NULL,1,'2019-10-29 17:30:00'),(113,1,'testTask','renren',0,NULL,1,'2019-10-29 18:00:00'),(114,1,'testTask','renren',0,NULL,1,'2019-10-29 18:30:00'),(115,1,'testTask','renren',0,NULL,1,'2019-10-29 19:00:00'),(116,1,'testTask','renren',0,NULL,1,'2019-10-29 19:30:00'),(117,1,'testTask','renren',0,NULL,1,'2019-10-29 20:00:00'),(118,1,'testTask','renren',0,NULL,1,'2019-10-29 20:30:00'),(119,1,'testTask','renren',0,NULL,1,'2019-10-29 21:30:03'),(120,1,'testTask','renren',0,NULL,0,'2019-10-29 22:00:00'),(121,1,'testTask','renren',0,NULL,1,'2019-10-29 22:30:00'),(122,1,'testTask','renren',0,NULL,1,'2019-10-29 23:00:00'),(123,1,'testTask','renren',0,NULL,1,'2019-10-29 23:30:00'),(124,1,'testTask','renren',0,NULL,14,'2019-10-30 00:00:00'),(125,1,'testTask','renren',0,NULL,1,'2019-10-30 00:30:00'),(126,1,'testTask','renren',0,NULL,0,'2019-10-30 01:00:00'),(127,1,'testTask','renren',0,NULL,1,'2019-10-30 01:30:00'),(128,1,'testTask','renren',0,NULL,1,'2019-10-30 02:00:00'),(129,1,'testTask','renren',0,NULL,0,'2019-10-30 02:30:00'),(130,1,'testTask','renren',0,NULL,1,'2019-10-30 03:00:00'),(131,1,'testTask','renren',0,NULL,1,'2019-10-30 03:30:00'),(132,1,'testTask','renren',0,NULL,1,'2019-10-30 04:00:00'),(133,1,'testTask','renren',0,NULL,0,'2019-10-30 04:30:00'),(134,1,'testTask','renren',0,NULL,1,'2019-10-30 05:00:00'),(135,1,'testTask','renren',0,NULL,1,'2019-10-30 05:30:00'),(136,1,'testTask','renren',0,NULL,1,'2019-10-30 06:00:00'),(137,1,'testTask','renren',0,NULL,1,'2019-10-30 06:30:00'),(138,1,'testTask','renren',0,NULL,0,'2019-10-30 07:00:00'),(139,1,'testTask','renren',0,NULL,1,'2019-10-30 07:30:00'),(140,1,'testTask','renren',0,NULL,1,'2019-10-30 08:00:00'),(141,1,'testTask','renren',0,NULL,0,'2019-10-30 08:30:00'),(142,1,'testTask','renren',0,NULL,1,'2019-10-30 09:00:00'),(143,1,'testTask','renren',0,NULL,0,'2019-10-30 09:30:00'),(144,1,'testTask','renren',0,NULL,0,'2019-10-30 10:00:00'),(145,1,'testTask','renren',0,NULL,1,'2019-10-30 10:30:00'),(146,1,'testTask','renren',0,NULL,0,'2019-10-30 11:00:00'),(147,1,'testTask','renren',0,NULL,2,'2019-10-30 11:30:00'),(148,1,'testTask','renren',0,NULL,1,'2019-10-30 12:00:00'),(149,1,'testTask','renren',0,NULL,1,'2019-10-30 12:30:00'),(150,1,'testTask','renren',0,NULL,0,'2019-10-30 13:00:00'),(151,1,'testTask','renren',0,NULL,0,'2019-10-30 13:30:00'),(152,1,'testTask','renren',0,NULL,1,'2019-10-30 14:00:00'),(153,1,'testTask','renren',0,NULL,1,'2019-10-30 14:30:00'),(154,1,'testTask','renren',0,NULL,1,'2019-10-30 15:00:00'),(155,1,'testTask','renren',0,NULL,1,'2019-10-30 15:30:00'),(156,1,'testTask','renren',0,NULL,2,'2019-10-30 16:00:00'),(157,1,'testTask','renren',0,NULL,1,'2019-10-30 16:30:00'),(158,1,'testTask','renren',0,NULL,6,'2019-10-30 17:00:00'),(159,1,'testTask','renren',0,NULL,1,'2019-10-30 17:30:00'),(160,1,'testTask','renren',0,NULL,0,'2019-10-30 18:00:00'),(161,1,'testTask','renren',0,NULL,0,'2019-10-30 18:30:00'),(162,1,'testTask','renren',0,NULL,1,'2019-10-30 19:00:00'),(163,1,'testTask','renren',0,NULL,1,'2019-10-30 19:30:00'),(164,1,'testTask','renren',0,NULL,1,'2019-10-30 20:00:00'),(165,1,'testTask','renren',0,NULL,1,'2019-10-30 20:30:00'),(166,1,'testTask','renren',0,NULL,1,'2019-10-30 21:00:00'),(167,1,'testTask','renren',0,NULL,0,'2019-10-30 21:30:00'),(168,1,'testTask','renren',0,NULL,0,'2019-10-30 22:00:00'),(169,1,'testTask','renren',0,NULL,1,'2019-10-30 22:30:00'),(170,1,'testTask','renren',0,NULL,0,'2019-10-30 23:00:00'),(171,1,'testTask','renren',0,NULL,0,'2019-10-30 23:30:00'),(172,1,'testTask','renren',0,NULL,5,'2019-10-31 00:00:00'),(173,1,'testTask','renren',0,NULL,1,'2019-10-31 00:30:00'),(174,1,'testTask','renren',0,NULL,0,'2019-10-31 01:00:00'),(175,1,'testTask','renren',0,NULL,0,'2019-10-31 01:30:00'),(176,1,'testTask','renren',0,NULL,1,'2019-10-31 02:00:00'),(177,1,'testTask','renren',0,NULL,1,'2019-10-31 02:30:00'),(178,1,'testTask','renren',0,NULL,1,'2019-10-31 03:00:00'),(179,1,'testTask','renren',0,NULL,1,'2019-10-31 03:30:00'),(180,1,'testTask','renren',0,NULL,0,'2019-10-31 04:00:00'),(181,1,'testTask','renren',0,NULL,0,'2019-10-31 04:30:00'),(182,1,'testTask','renren',0,NULL,1,'2019-10-31 05:00:00'),(183,1,'testTask','renren',0,NULL,1,'2019-10-31 05:30:00'),(184,1,'testTask','renren',0,NULL,1,'2019-10-31 06:00:00'),(185,1,'testTask','renren',0,NULL,1,'2019-10-31 06:30:00'),(186,1,'testTask','renren',0,NULL,0,'2019-10-31 07:00:00'),(187,1,'testTask','renren',0,NULL,0,'2019-10-31 07:30:00'),(188,1,'testTask','renren',0,NULL,1,'2019-10-31 08:00:00'),(189,1,'testTask','renren',0,NULL,1,'2019-10-31 08:30:00'),(190,1,'testTask','renren',0,NULL,0,'2019-10-31 09:00:00'),(191,1,'testTask','renren',0,NULL,1,'2019-10-31 09:30:00'),(192,1,'testTask','renren',0,NULL,0,'2019-10-31 10:00:00'),(193,1,'testTask','renren',0,NULL,0,'2019-10-31 10:30:00'),(194,1,'testTask','renren',0,NULL,1,'2019-10-31 11:00:00'),(195,1,'testTask','renren',0,NULL,0,'2019-10-31 11:30:00'),(196,1,'testTask','renren',0,NULL,1,'2019-10-31 12:00:00'),(197,1,'testTask','renren',0,NULL,0,'2019-10-31 12:30:00'),(198,1,'testTask','renren',0,NULL,1,'2019-10-31 13:00:00'),(199,1,'testTask','renren',0,NULL,2,'2019-10-31 13:30:00'),(200,1,'testTask','renren',0,NULL,1,'2019-10-31 14:00:00'),(201,1,'testTask','renren',0,NULL,1,'2019-10-31 14:30:00'),(202,1,'testTask','renren',0,NULL,1,'2019-10-31 15:00:00'),(203,1,'testTask','renren',0,NULL,0,'2019-10-31 15:30:00'),(204,1,'testTask','renren',0,NULL,1,'2019-10-31 16:00:00'),(205,1,'testTask','renren',0,NULL,0,'2019-10-31 16:30:00'),(206,1,'testTask','renren',0,NULL,1,'2019-10-31 17:00:00'),(207,1,'testTask','renren',0,NULL,1,'2019-10-31 17:30:00'),(208,1,'testTask','renren',0,NULL,0,'2019-10-31 18:00:00'),(209,1,'testTask','renren',0,NULL,1,'2019-10-31 18:30:00'),(210,1,'testTask','renren',0,NULL,1,'2019-10-31 19:00:00'),(211,1,'testTask','renren',0,NULL,1,'2019-10-31 19:30:00'),(212,1,'testTask','renren',0,NULL,0,'2019-10-31 20:00:00'),(213,1,'testTask','renren',0,NULL,1,'2019-10-31 20:30:00'),(214,1,'testTask','renren',0,NULL,1,'2019-10-31 21:00:00'),(215,1,'testTask','renren',0,NULL,0,'2019-10-31 21:30:00'),(216,1,'testTask','renren',0,NULL,0,'2019-10-31 22:00:00'),(217,1,'testTask','renren',0,NULL,0,'2019-10-31 22:30:00'),(218,1,'testTask','renren',0,NULL,1,'2019-10-31 23:00:00'),(219,1,'testTask','renren',0,NULL,1,'2019-10-31 23:30:00'),(220,1,'testTask','renren',0,NULL,6,'2019-11-01 00:00:00'),(221,1,'testTask','renren',0,NULL,2,'2019-11-01 00:30:00'),(222,1,'testTask','renren',0,NULL,1,'2019-11-01 01:00:00'),(223,1,'testTask','renren',0,NULL,1,'2019-11-01 01:30:00'),(224,1,'testTask','renren',0,NULL,0,'2019-11-01 02:00:00'),(225,1,'testTask','renren',0,NULL,1,'2019-11-01 02:30:00'),(226,1,'testTask','renren',0,NULL,0,'2019-11-01 03:00:00'),(227,1,'testTask','renren',0,NULL,1,'2019-11-01 03:30:00'),(228,1,'testTask','renren',0,NULL,1,'2019-11-01 04:00:00'),(229,1,'testTask','renren',0,NULL,1,'2019-11-01 04:30:00'),(230,1,'testTask','renren',0,NULL,0,'2019-11-01 05:00:00'),(231,1,'testTask','renren',0,NULL,0,'2019-11-01 05:30:00'),(232,1,'testTask','renren',0,NULL,1,'2019-11-01 06:00:00'),(233,1,'testTask','renren',0,NULL,1,'2019-11-01 06:30:00'),(234,1,'testTask','renren',0,NULL,0,'2019-11-01 07:00:00'),(235,1,'testTask','renren',0,NULL,0,'2019-11-01 07:30:00'),(236,1,'testTask','renren',0,NULL,0,'2019-11-01 08:00:00'),(237,1,'testTask','renren',0,NULL,0,'2019-11-01 08:30:00'),(238,1,'testTask','renren',0,NULL,1,'2019-11-01 09:00:00'),(239,1,'testTask','renren',0,NULL,1,'2019-11-01 09:30:00'),(240,1,'testTask','renren',0,NULL,1,'2019-11-01 10:00:00'),(241,1,'testTask','renren',0,NULL,1,'2019-11-01 10:30:00'),(242,1,'testTask','renren',0,NULL,0,'2019-11-01 11:00:00'),(243,1,'testTask','renren',0,NULL,1,'2019-11-01 11:30:00'),(244,1,'testTask','renren',0,NULL,1,'2019-11-01 12:00:00'),(245,1,'testTask','renren',0,NULL,0,'2019-11-01 12:30:00'),(246,1,'testTask','renren',0,NULL,1,'2019-11-01 13:00:00'),(247,1,'testTask','renren',0,NULL,0,'2019-11-01 13:30:00'),(248,1,'testTask','renren',0,NULL,1,'2019-11-01 14:00:00'),(249,1,'testTask','renren',0,NULL,0,'2019-11-01 14:30:00'),(250,1,'testTask','renren',0,NULL,1,'2019-11-01 15:00:00'),(251,1,'testTask','renren',0,NULL,1,'2019-11-01 15:30:00'),(252,1,'testTask','renren',0,NULL,0,'2019-11-01 16:00:00'),(253,1,'testTask','renren',0,NULL,1,'2019-11-01 16:30:00'),(254,1,'testTask','renren',0,NULL,1,'2019-11-01 17:00:00'),(255,1,'testTask','renren',0,NULL,1,'2019-11-01 17:30:00'),(256,1,'testTask','renren',0,NULL,1,'2019-11-01 18:00:00'),(257,1,'testTask','renren',0,NULL,1,'2019-11-01 18:30:00'),(258,1,'testTask','renren',0,NULL,0,'2019-11-01 19:00:00'),(259,1,'testTask','renren',0,NULL,1,'2019-11-01 19:30:00'),(260,1,'testTask','renren',0,NULL,1,'2019-11-01 20:00:00'),(261,1,'testTask','renren',0,NULL,0,'2019-11-01 20:30:00'),(262,1,'testTask','renren',0,NULL,1,'2019-11-01 21:00:00'),(263,1,'testTask','renren',0,NULL,0,'2019-11-01 21:30:00'),(264,1,'testTask','renren',0,NULL,0,'2019-11-01 22:30:00'),(265,1,'testTask','renren',0,NULL,1,'2019-11-01 23:00:00'),(266,1,'testTask','renren',0,NULL,1,'2019-11-01 23:30:00'),(267,1,'testTask','renren',0,NULL,12,'2019-11-02 00:00:00'),(268,1,'testTask','renren',0,NULL,4,'2019-11-02 00:30:00'),(269,1,'testTask','renren',0,NULL,1,'2019-11-02 10:30:00'),(270,1,'testTask','renren',0,NULL,0,'2019-11-02 11:00:00'),(271,1,'testTask','renren',0,NULL,0,'2019-11-02 11:30:00'),(272,1,'testTask','renren',0,NULL,1,'2019-11-02 12:00:00'),(273,1,'testTask','renren',0,NULL,1,'2019-11-02 12:30:00'),(274,1,'testTask','renren',0,NULL,0,'2019-11-02 13:00:00'),(275,1,'testTask','renren',0,NULL,0,'2019-11-02 13:30:00'),(276,1,'testTask','renren',0,NULL,1,'2019-11-02 14:00:00'),(277,1,'testTask','renren',0,NULL,1,'2019-11-02 14:30:00'),(278,1,'testTask','renren',0,NULL,1,'2019-11-02 15:00:00'),(279,1,'testTask','renren',0,NULL,1,'2019-11-02 15:30:00'),(280,1,'testTask','renren',0,NULL,1,'2019-11-02 16:00:00'),(281,1,'testTask','renren',0,NULL,1,'2019-11-02 16:30:00'),(282,1,'testTask','renren',0,NULL,1,'2019-11-02 17:00:00'),(283,1,'testTask','renren',0,NULL,1,'2019-11-02 17:30:00'),(284,1,'testTask','renren',0,NULL,1,'2019-11-02 18:00:00'),(285,1,'testTask','renren',0,NULL,1,'2019-11-02 18:30:00'),(286,1,'testTask','renren',0,NULL,1,'2019-11-02 19:00:00'),(287,1,'testTask','renren',0,NULL,1,'2019-11-02 19:30:00'),(288,1,'testTask','renren',0,NULL,0,'2019-11-02 20:00:00'),(289,1,'testTask','renren',0,NULL,0,'2019-11-02 20:30:00'),(290,1,'testTask','renren',0,NULL,0,'2019-11-04 10:00:00'),(291,1,'testTask','renren',0,NULL,0,'2019-11-04 10:30:00'),(292,1,'testTask','renren',0,NULL,1,'2019-11-04 11:00:00'),(293,1,'testTask','renren',0,NULL,0,'2019-11-04 11:30:00'),(294,1,'testTask','renren',0,NULL,0,'2019-11-04 12:00:00'),(295,1,'testTask','renren',0,NULL,1,'2019-11-04 12:30:00'),(296,1,'testTask','renren',0,NULL,1,'2019-11-04 13:00:00'),(297,1,'testTask','renren',0,NULL,1,'2019-11-04 13:30:00'),(298,1,'testTask','renren',0,NULL,1,'2019-11-04 14:00:00'),(299,1,'testTask','renren',0,NULL,0,'2019-11-04 14:30:00'),(300,1,'testTask','renren',0,NULL,1,'2019-11-04 15:00:00'),(301,1,'testTask','renren',0,NULL,0,'2019-11-04 15:30:00'),(302,1,'testTask','renren',0,NULL,1,'2019-11-04 16:00:00'),(303,1,'testTask','renren',0,NULL,1,'2019-11-04 16:30:00'),(304,1,'testTask','renren',0,NULL,1,'2019-11-04 17:00:00'),(305,1,'testTask','renren',0,NULL,1,'2019-11-04 17:30:00'),(306,1,'testTask','renren',0,NULL,0,'2019-11-04 18:00:00'),(307,1,'testTask','renren',0,NULL,0,'2019-11-04 18:30:00'),(308,1,'testTask','renren',0,NULL,0,'2019-11-04 19:00:00'),(309,1,'testTask','renren',0,NULL,1,'2019-11-04 19:30:00'),(310,1,'testTask','renren',0,NULL,1,'2019-11-04 20:00:00'),(311,1,'testTask','renren',0,NULL,1,'2019-11-04 20:30:00'),(312,1,'testTask','renren',0,NULL,1,'2019-11-04 21:00:00'),(313,1,'testTask','renren',0,NULL,1,'2019-11-04 21:30:00'),(314,1,'testTask','renren',0,NULL,1,'2019-11-04 22:00:00'),(315,1,'testTask','renren',0,NULL,1,'2019-11-04 22:30:00'),(316,1,'testTask','renren',0,NULL,1,'2019-11-04 23:00:00'),(317,1,'testTask','renren',0,NULL,1,'2019-11-04 23:30:00'),(318,1,'testTask','renren',0,NULL,7,'2019-11-05 00:00:00'),(319,1,'testTask','renren',0,NULL,1,'2019-11-05 00:30:00'),(320,1,'testTask','renren',0,NULL,1,'2019-11-05 01:00:00'),(321,1,'testTask','renren',0,NULL,0,'2019-11-05 01:30:00'),(322,1,'testTask','renren',0,NULL,0,'2019-11-05 02:00:00'),(323,1,'testTask','renren',0,NULL,0,'2019-11-05 02:30:00'),(324,1,'testTask','renren',0,NULL,1,'2019-11-05 03:00:00'),(325,1,'testTask','renren',0,NULL,1,'2019-11-05 03:30:00'),(326,1,'testTask','renren',0,NULL,0,'2019-11-05 04:00:00'),(327,1,'testTask','renren',0,NULL,0,'2019-11-05 04:30:00'),(328,1,'testTask','renren',0,NULL,0,'2019-11-05 05:00:00'),(329,1,'testTask','renren',0,NULL,0,'2019-11-05 05:30:00'),(330,1,'testTask','renren',0,NULL,1,'2019-11-05 06:00:00'),(331,1,'testTask','renren',0,NULL,1,'2019-11-05 06:30:00'),(332,1,'testTask','renren',0,NULL,1,'2019-11-05 07:00:00'),(333,1,'testTask','renren',0,NULL,1,'2019-11-05 07:30:00'),(334,1,'testTask','renren',0,NULL,1,'2019-11-05 08:00:00'),(335,1,'testTask','renren',0,NULL,0,'2019-11-05 08:30:00'),(336,1,'testTask','renren',0,NULL,0,'2019-11-05 09:00:00'),(337,1,'testTask','renren',0,NULL,0,'2019-11-05 09:30:00'),(338,1,'testTask','renren',0,NULL,1,'2019-11-05 10:00:00'),(339,1,'testTask','renren',0,NULL,1,'2019-11-05 10:30:00'),(340,1,'testTask','renren',0,NULL,1,'2019-11-05 11:00:00'),(341,1,'testTask','renren',0,NULL,1,'2019-11-05 11:30:00'),(342,1,'testTask','renren',0,NULL,1,'2019-11-05 12:00:00'),(343,1,'testTask','renren',0,NULL,0,'2019-11-05 12:30:00'),(344,1,'testTask','renren',0,NULL,0,'2019-11-05 13:00:00'),(345,1,'testTask','renren',0,NULL,1,'2019-11-05 13:30:00'),(346,1,'testTask','renren',0,NULL,1,'2019-11-05 14:00:00'),(347,1,'testTask','renren',0,NULL,0,'2019-11-05 14:30:00'),(348,1,'testTask','renren',0,NULL,1,'2019-11-05 15:00:00'),(349,1,'testTask','renren',0,NULL,1,'2019-11-05 15:30:00'),(350,1,'testTask','renren',0,NULL,1,'2019-11-05 16:00:00'),(351,1,'testTask','renren',0,NULL,1,'2019-11-05 16:30:00'),(352,1,'testTask','renren',0,NULL,1,'2019-11-05 17:00:00'),(353,1,'testTask','renren',0,NULL,1,'2019-11-05 17:30:02'),(354,1,'testTask','renren',0,NULL,0,'2019-11-05 18:00:00'),(355,1,'testTask','renren',0,NULL,1,'2019-11-05 18:30:00'),(356,1,'testTask','renren',0,NULL,0,'2019-11-05 19:00:00'),(357,1,'testTask','renren',0,NULL,0,'2019-11-05 19:30:00'),(358,1,'testTask','renren',0,NULL,0,'2019-11-05 20:00:00'),(359,1,'testTask','renren',0,NULL,0,'2019-11-05 20:30:00'),(360,1,'testTask','renren',0,NULL,1,'2019-11-05 21:00:00'),(361,1,'testTask','renren',0,NULL,1,'2019-11-05 21:30:00'),(362,1,'testTask','renren',0,NULL,1,'2019-11-05 22:00:00'),(363,1,'testTask','renren',0,NULL,0,'2019-11-05 22:30:00'),(364,1,'testTask','renren',0,NULL,1,'2019-11-05 23:00:00'),(365,1,'testTask','renren',0,NULL,1,'2019-11-05 23:30:00'),(366,1,'testTask','renren',0,NULL,4,'2019-11-06 00:00:00'),(367,1,'testTask','renren',0,NULL,1,'2019-11-06 00:30:00'),(368,1,'testTask','renren',0,NULL,1,'2019-11-06 01:00:00'),(369,1,'testTask','renren',0,NULL,1,'2019-11-06 01:30:00'),(370,1,'testTask','renren',0,NULL,1,'2019-11-06 02:00:00'),(371,1,'testTask','renren',0,NULL,1,'2019-11-06 02:30:00'),(372,1,'testTask','renren',0,NULL,0,'2019-11-06 03:00:00'),(373,1,'testTask','renren',0,NULL,0,'2019-11-06 03:30:00'),(374,1,'testTask','renren',0,NULL,0,'2019-11-06 04:00:00'),(375,1,'testTask','renren',0,NULL,0,'2019-11-06 04:30:00'),(376,1,'testTask','renren',0,NULL,0,'2019-11-06 05:00:00'),(377,1,'testTask','renren',0,NULL,1,'2019-11-06 05:30:00'),(378,1,'testTask','renren',0,NULL,0,'2019-11-06 06:00:00'),(379,1,'testTask','renren',0,NULL,0,'2019-11-06 06:30:00'),(380,1,'testTask','renren',0,NULL,1,'2019-11-06 07:00:00'),(381,1,'testTask','renren',0,NULL,0,'2019-11-06 07:30:00'),(382,1,'testTask','renren',0,NULL,0,'2019-11-06 08:00:00'),(383,1,'testTask','renren',0,NULL,0,'2019-11-06 08:30:00'),(384,1,'testTask','renren',0,NULL,1,'2019-11-06 09:00:00'),(385,1,'testTask','renren',0,NULL,0,'2019-11-06 09:30:00'),(386,1,'testTask','renren',0,NULL,0,'2019-11-06 10:00:00'),(387,1,'testTask','renren',0,NULL,0,'2019-11-06 10:30:00'),(388,1,'testTask','renren',0,NULL,1,'2019-11-06 11:00:00'),(389,1,'testTask','renren',0,NULL,0,'2019-11-06 11:30:00'),(390,1,'testTask','renren',0,NULL,1,'2019-11-06 12:00:00'),(391,1,'testTask','renren',0,NULL,0,'2019-11-06 12:30:00'),(392,1,'testTask','renren',0,NULL,1,'2019-11-06 13:00:00'),(393,1,'testTask','renren',0,NULL,0,'2019-11-06 13:30:00'),(394,1,'testTask','renren',0,NULL,1,'2019-11-06 14:00:00'),(395,1,'testTask','renren',0,NULL,1,'2019-11-06 14:30:00'),(396,1,'testTask','renren',0,NULL,1,'2019-11-06 15:00:00'),(397,1,'testTask','renren',0,NULL,1,'2019-11-06 15:30:00'),(398,1,'testTask','renren',0,NULL,1,'2019-11-06 16:00:00'),(399,1,'testTask','renren',0,NULL,1,'2019-11-06 16:30:00'),(400,1,'testTask','renren',0,NULL,0,'2019-11-06 17:00:00'),(401,1,'testTask','renren',0,NULL,1,'2019-11-06 17:30:00'),(402,1,'testTask','renren',0,NULL,1,'2019-11-06 18:00:00'),(403,1,'testTask','renren',0,NULL,1,'2019-11-06 18:30:00'),(404,1,'testTask','renren',0,NULL,1,'2019-11-06 19:00:00'),(405,1,'testTask','renren',0,NULL,1,'2019-11-06 19:30:00'),(406,1,'testTask','renren',0,NULL,1,'2019-11-06 20:00:00'),(407,1,'testTask','renren',0,NULL,1,'2019-11-06 20:30:00'),(408,1,'testTask','renren',0,NULL,1,'2019-11-06 21:00:00'),(409,1,'testTask','renren',0,NULL,1,'2019-11-06 21:30:00'),(410,1,'testTask','renren',0,NULL,1,'2019-11-06 22:00:00'),(411,1,'testTask','renren',0,NULL,1,'2019-11-06 22:30:00'),(412,1,'testTask','renren',0,NULL,0,'2019-11-06 23:00:00'),(413,1,'testTask','renren',0,NULL,1,'2019-11-06 23:30:00'),(414,1,'testTask','renren',0,NULL,5,'2019-11-07 00:00:00'),(415,1,'testTask','renren',0,NULL,2,'2019-11-07 00:30:00'),(416,1,'testTask','renren',0,NULL,0,'2019-11-07 01:00:00'),(417,1,'testTask','renren',0,NULL,1,'2019-11-07 01:30:00'),(418,1,'testTask','renren',0,NULL,1,'2019-11-07 02:00:00'),(419,1,'testTask','renren',0,NULL,0,'2019-11-07 02:30:00'),(420,1,'testTask','renren',0,NULL,1,'2019-11-07 03:00:00'),(421,1,'testTask','renren',0,NULL,1,'2019-11-07 03:30:00'),(422,1,'testTask','renren',0,NULL,1,'2019-11-07 04:00:00'),(423,1,'testTask','renren',0,NULL,1,'2019-11-07 04:30:00'),(424,1,'testTask','renren',0,NULL,0,'2019-11-07 05:00:00'),(425,1,'testTask','renren',0,NULL,1,'2019-11-07 05:30:00'),(426,1,'testTask','renren',0,NULL,1,'2019-11-07 06:00:00'),(427,1,'testTask','renren',0,NULL,0,'2019-11-07 06:30:00'),(428,1,'testTask','renren',0,NULL,1,'2019-11-07 07:00:00'),(429,1,'testTask','renren',0,NULL,1,'2019-11-07 07:30:00'),(430,1,'testTask','renren',0,NULL,0,'2019-11-07 08:00:00'),(431,1,'testTask','renren',0,NULL,1,'2019-11-07 08:30:00'),(432,1,'testTask','renren',0,NULL,1,'2019-11-07 09:00:00'),(433,1,'testTask','renren',0,NULL,0,'2019-11-07 09:30:00'),(434,1,'testTask','renren',0,NULL,1,'2019-11-07 10:00:00'),(435,1,'testTask','renren',0,NULL,0,'2019-11-07 10:30:00'),(436,1,'testTask','renren',0,NULL,1,'2019-11-07 11:00:00'),(437,1,'testTask','renren',0,NULL,0,'2019-11-07 11:30:00'),(438,1,'testTask','renren',0,NULL,1,'2019-11-07 12:00:00'),(439,1,'testTask','renren',0,NULL,1,'2019-11-07 12:30:00'),(440,1,'testTask','renren',0,NULL,1,'2019-11-07 13:00:00'),(441,1,'testTask','renren',0,NULL,1,'2019-11-07 13:30:00'),(442,1,'testTask','renren',0,NULL,0,'2019-11-07 14:00:00'),(443,1,'testTask','renren',0,NULL,1,'2019-11-07 14:30:00'),(444,1,'testTask','renren',0,NULL,1,'2019-11-07 15:00:00'),(445,1,'testTask','renren',0,NULL,1,'2019-11-07 15:30:00'),(446,1,'testTask','renren',0,NULL,1,'2019-11-07 16:00:00'),(447,1,'testTask','renren',0,NULL,0,'2019-11-07 16:30:00'),(448,1,'testTask','renren',0,NULL,1,'2019-11-07 17:00:00'),(449,1,'testTask','renren',0,NULL,1,'2019-11-07 17:30:00'),(450,1,'testTask','renren',0,NULL,1,'2019-11-07 18:00:00'),(451,1,'testTask','renren',0,NULL,1,'2019-11-07 18:30:00'),(452,1,'testTask','renren',0,NULL,1,'2019-11-07 19:00:00'),(453,1,'testTask','renren',0,NULL,1,'2019-11-07 19:30:00'),(454,1,'testTask','renren',0,NULL,1,'2019-11-07 20:00:00'),(455,1,'testTask','renren',0,NULL,1,'2019-11-07 20:30:00'),(456,1,'testTask','renren',0,NULL,0,'2019-11-07 21:00:00'),(457,1,'testTask','renren',0,NULL,1,'2019-11-07 21:30:00'),(458,1,'testTask','renren',0,NULL,1,'2019-11-07 22:00:00'),(459,1,'testTask','renren',0,NULL,1,'2019-11-07 22:30:00'),(460,1,'testTask','renren',0,NULL,1,'2019-11-07 23:00:00'),(461,1,'testTask','renren',0,NULL,0,'2019-11-07 23:30:00'),(462,1,'testTask','renren',0,NULL,7,'2019-11-08 11:00:00'),(463,1,'testTask','renren',0,NULL,1,'2019-11-08 11:30:00'),(464,1,'testTask','renren',0,NULL,0,'2019-11-08 12:00:00'),(465,1,'testTask','renren',0,NULL,1,'2019-11-08 12:30:00'),(466,1,'testTask','renren',0,NULL,0,'2019-11-08 13:00:00'),(467,1,'testTask','renren',0,NULL,0,'2019-11-08 13:30:00'),(468,1,'testTask','renren',0,NULL,0,'2019-11-08 14:00:00'),(469,1,'testTask','renren',0,NULL,1,'2019-11-08 14:30:00'),(470,1,'testTask','renren',0,NULL,1,'2019-11-08 15:00:00'),(471,1,'testTask','renren',0,NULL,0,'2019-11-08 15:30:00'),(472,1,'testTask','renren',0,NULL,1,'2019-11-11 09:30:00'),(473,1,'testTask','renren',0,NULL,1,'2019-11-11 10:00:00'),(474,1,'testTask','renren',0,NULL,1,'2019-11-11 10:30:00'),(475,1,'testTask','renren',0,NULL,0,'2019-11-11 11:00:00'),(476,1,'testTask','renren',0,NULL,0,'2019-11-11 11:30:00'),(477,1,'testTask','renren',0,NULL,1,'2019-11-11 12:00:00'),(478,1,'testTask','renren',0,NULL,0,'2019-11-11 12:30:00'),(479,1,'testTask','renren',0,NULL,0,'2019-11-11 13:00:00'),(480,1,'testTask','renren',0,NULL,1,'2019-11-11 13:30:00'),(481,1,'testTask','renren',0,NULL,1,'2019-11-11 14:00:00'),(482,1,'testTask','renren',0,NULL,1,'2019-11-11 14:30:00'),(483,1,'testTask','renren',0,NULL,1,'2019-11-11 15:00:00'),(484,1,'testTask','renren',0,NULL,0,'2019-11-11 15:30:00'),(485,1,'testTask','renren',0,NULL,1,'2019-11-11 16:00:00'),(486,1,'testTask','renren',0,NULL,1,'2019-11-11 16:30:00'),(487,1,'testTask','renren',0,NULL,1,'2019-11-11 17:00:00'),(488,1,'testTask','renren',0,NULL,1,'2019-11-11 17:30:00'),(489,1,'testTask','renren',0,NULL,1,'2019-11-11 18:00:00'),(490,1,'testTask','renren',0,NULL,1,'2019-11-11 18:30:00'),(491,1,'testTask','renren',0,NULL,1,'2019-11-11 20:00:00'),(492,1,'testTask','renren',0,NULL,1,'2019-11-11 20:30:00'),(493,1,'testTask','renren',0,NULL,0,'2019-11-11 21:00:00'),(494,1,'testTask','renren',0,NULL,1,'2019-11-11 21:30:00'),(495,1,'testTask','renren',0,NULL,0,'2019-11-11 22:00:00'),(496,1,'testTask','renren',0,NULL,1,'2019-11-11 22:30:00'),(497,1,'testTask','renren',0,NULL,0,'2019-11-11 23:00:00'),(498,1,'testTask','renren',0,NULL,1,'2019-11-11 23:30:00'),(499,1,'testTask','renren',0,NULL,11,'2019-11-12 00:00:00'),(500,1,'testTask','renren',0,NULL,2,'2019-11-12 09:30:00'),(501,1,'testTask','renren',0,NULL,1,'2019-11-12 10:00:00'),(502,1,'testTask','renren',0,NULL,1,'2019-11-12 10:30:00'),(503,1,'testTask','renren',0,NULL,1,'2019-11-12 11:00:00'),(504,1,'testTask','renren',0,NULL,0,'2019-11-12 11:30:00'),(505,1,'testTask','renren',0,NULL,1,'2019-11-12 12:00:00'),(506,1,'testTask','renren',0,NULL,1,'2019-11-12 12:30:00'),(507,1,'testTask','renren',0,NULL,1,'2019-11-12 13:00:00'),(508,1,'testTask','renren',0,NULL,0,'2019-11-12 13:30:00'),(509,1,'testTask','renren',0,NULL,0,'2019-11-12 14:00:00'),(510,1,'testTask','renren',0,NULL,1,'2019-11-12 14:30:00'),(511,1,'testTask','renren',0,NULL,1,'2019-11-12 15:00:00'),(512,1,'testTask','renren',0,NULL,1,'2019-11-12 15:30:00'),(513,1,'testTask','renren',0,NULL,1,'2019-11-12 16:00:00'),(514,1,'testTask','renren',0,NULL,0,'2019-11-12 16:30:00'),(515,1,'testTask','renren',0,NULL,1,'2019-11-12 17:00:00'),(516,1,'testTask','renren',0,NULL,1,'2019-11-12 17:30:00'),(517,1,'testTask','renren',0,NULL,1,'2019-11-12 18:00:00'),(518,1,'testTask','renren',0,NULL,0,'2019-11-12 18:30:00'),(519,1,'testTask','renren',0,NULL,0,'2019-11-12 19:00:00'),(520,1,'testTask','renren',0,NULL,1,'2019-11-12 19:30:00'),(521,1,'testTask','renren',0,NULL,0,'2019-11-12 20:00:00'),(522,1,'testTask','renren',0,NULL,0,'2019-11-12 20:30:00'),(523,1,'testTask','renren',0,NULL,0,'2019-11-12 21:00:00'),(524,1,'testTask','renren',0,NULL,1,'2019-11-12 21:30:00'),(525,1,'testTask','renren',0,NULL,1,'2019-11-12 22:00:00'),(526,1,'testTask','renren',0,NULL,1,'2019-11-12 22:30:00'),(527,1,'testTask','renren',0,NULL,1,'2019-11-12 23:00:00'),(528,1,'testTask','renren',0,NULL,1,'2019-11-12 23:30:00'),(529,1,'testTask','renren',0,NULL,1,'2019-11-13 09:30:00'),(530,1,'testTask','renren',0,NULL,1,'2019-11-13 10:00:00'),(531,1,'testTask','renren',0,NULL,0,'2019-11-13 10:30:00'),(532,1,'testTask','renren',0,NULL,1,'2019-11-13 11:00:00'),(533,1,'testTask','renren',0,NULL,1,'2019-11-13 11:30:00'),(534,1,'testTask','renren',0,NULL,0,'2019-11-13 12:00:00'),(535,1,'testTask','renren',0,NULL,4,'2019-11-13 12:30:00'),(536,1,'testTask','renren',0,NULL,0,'2019-11-13 13:00:00'),(537,1,'testTask','renren',0,NULL,3,'2019-11-13 13:30:00'),(538,1,'testTask','renren',0,NULL,0,'2019-11-13 14:00:00'),(539,1,'testTask','renren',0,NULL,1,'2019-11-13 14:30:00'),(540,1,'testTask','renren',0,NULL,0,'2019-11-13 15:00:00'),(541,1,'testTask','renren',0,NULL,0,'2019-11-13 15:30:00'),(542,1,'testTask','renren',0,NULL,1,'2019-11-13 16:00:00'),(543,1,'testTask','renren',0,NULL,1,'2019-11-13 16:30:00'),(544,1,'testTask','renren',0,NULL,1,'2019-11-13 17:00:00'),(545,1,'testTask','renren',0,NULL,1,'2019-11-13 17:30:00'),(546,1,'testTask','renren',0,NULL,1,'2019-11-13 18:00:00'),(547,1,'testTask','renren',0,NULL,0,'2019-11-13 18:30:00'),(548,1,'testTask','renren',0,NULL,1,'2019-11-13 19:00:00'),(549,1,'testTask','renren',0,NULL,0,'2019-11-13 19:30:00'),(550,1,'testTask','renren',0,NULL,3,'2019-11-13 20:00:00'),(551,1,'testTask','renren',0,NULL,1,'2019-11-13 20:30:00'),(552,1,'testTask','renren',0,NULL,0,'2019-11-13 21:00:00'),(553,1,'testTask','renren',0,NULL,1,'2019-11-13 21:30:00'),(554,1,'testTask','renren',0,NULL,1,'2019-11-13 22:00:00'),(555,1,'testTask','renren',0,NULL,0,'2019-11-13 22:30:00'),(556,1,'testTask','renren',0,NULL,1,'2019-11-13 23:00:00'),(557,1,'testTask','renren',0,NULL,1,'2019-11-13 23:30:00'),(558,1,'testTask','renren',0,NULL,10,'2019-11-14 00:00:00'),(559,1,'testTask','renren',0,NULL,0,'2019-11-14 11:00:00'),(560,1,'testTask','renren',0,NULL,1,'2019-11-14 11:30:00'),(561,1,'testTask','renren',0,NULL,1,'2019-11-14 12:00:00'),(562,1,'testTask','renren',0,NULL,1,'2019-11-14 12:30:00'),(563,1,'testTask','renren',0,NULL,1,'2019-11-14 13:00:00'),(564,1,'testTask','renren',0,NULL,0,'2019-11-14 13:30:00'),(565,1,'testTask','renren',0,NULL,1,'2019-11-14 14:00:00'),(566,1,'testTask','renren',0,NULL,1,'2019-11-14 14:30:00'),(567,1,'testTask','renren',0,NULL,1,'2019-11-14 15:00:00'),(568,1,'testTask','renren',0,NULL,1,'2019-11-14 15:30:00'),(569,1,'testTask','renren',0,NULL,1,'2019-11-14 16:00:00'),(570,1,'testTask','renren',0,NULL,0,'2019-11-14 16:30:00'),(571,1,'testTask','renren',0,NULL,1,'2019-11-14 17:00:00'),(572,1,'testTask','renren',0,NULL,1,'2019-11-14 17:30:00'),(573,1,'testTask','renren',0,NULL,0,'2019-11-14 18:00:00'),(574,1,'testTask','renren',0,NULL,1,'2019-11-14 18:30:00'),(575,1,'testTask','renren',0,NULL,0,'2019-11-14 19:00:00'),(576,1,'testTask','renren',0,NULL,1,'2019-11-14 19:30:00'),(577,1,'testTask','renren',0,NULL,1,'2019-11-14 20:00:00'),(578,1,'testTask','renren',0,NULL,1,'2019-11-14 20:30:00'),(579,1,'testTask','renren',0,NULL,0,'2019-11-14 21:00:00'),(580,1,'testTask','renren',0,NULL,1,'2019-11-14 21:30:00'),(581,1,'testTask','renren',0,NULL,1,'2019-11-14 22:00:00'),(582,1,'testTask','renren',0,NULL,1,'2019-11-14 22:30:00'),(583,1,'testTask','renren',0,NULL,1,'2019-11-14 23:00:00'),(584,1,'testTask','renren',0,NULL,1,'2019-11-14 23:30:00'),(585,1,'testTask','renren',0,NULL,15,'2019-11-15 00:00:00'),(586,1,'testTask','renren',0,NULL,1,'2019-11-15 01:00:00'),(587,1,'testTask','renren',0,NULL,1,'2019-11-15 01:30:00'),(588,1,'testTask','renren',0,NULL,1,'2019-11-15 02:00:00'),(589,1,'testTask','renren',0,NULL,0,'2019-11-15 02:30:00'),(590,1,'testTask','renren',0,NULL,1,'2019-11-15 03:00:00'),(591,1,'testTask','renren',0,NULL,1,'2019-11-15 03:30:00'),(592,1,'testTask','renren',0,NULL,1,'2019-11-15 04:00:00'),(593,1,'testTask','renren',0,NULL,0,'2019-11-15 04:30:00'),(594,1,'testTask','renren',0,NULL,0,'2019-11-15 05:00:00'),(595,1,'testTask','renren',0,NULL,0,'2019-11-15 05:30:00'),(596,1,'testTask','renren',0,NULL,1,'2019-11-15 06:00:00'),(597,1,'testTask','renren',0,NULL,0,'2019-11-15 06:30:00'),(598,1,'testTask','renren',0,NULL,1,'2019-11-15 07:00:00'),(599,1,'testTask','renren',0,NULL,0,'2019-11-15 07:30:00'),(600,1,'testTask','renren',0,NULL,1,'2019-11-15 08:00:00'),(601,1,'testTask','renren',0,NULL,0,'2019-11-15 08:30:00'),(602,1,'testTask','renren',0,NULL,0,'2019-11-15 09:00:00'),(603,1,'testTask','renren',0,NULL,1,'2019-11-15 09:30:00'),(604,1,'testTask','renren',0,NULL,1,'2019-11-15 10:00:00'),(605,1,'testTask','renren',0,NULL,1,'2019-11-15 11:00:00'),(606,1,'testTask','renren',0,NULL,1,'2019-11-15 11:30:00'),(607,1,'testTask','renren',0,NULL,1,'2019-11-15 12:00:00'),(608,1,'testTask','renren',0,NULL,1,'2019-11-15 12:30:00'),(609,1,'testTask','renren',0,NULL,4,'2019-11-15 13:00:00'),(610,1,'testTask','renren',0,NULL,0,'2019-11-15 13:30:00'),(611,1,'testTask','renren',0,NULL,1,'2019-11-15 14:00:00'),(612,1,'testTask','renren',0,NULL,1,'2019-11-15 14:30:00'),(613,1,'testTask','renren',0,NULL,1,'2019-11-15 15:00:00'),(614,1,'testTask','renren',0,NULL,1,'2019-11-15 15:30:00'),(615,1,'testTask','renren',0,NULL,1,'2019-11-15 16:00:00'),(616,1,'testTask','renren',0,NULL,1,'2019-11-15 16:30:00'),(617,1,'testTask','renren',0,NULL,1,'2019-11-15 17:00:00'),(618,1,'testTask','renren',0,NULL,1,'2019-11-15 17:30:00'),(619,1,'testTask','renren',0,NULL,0,'2019-11-15 18:00:00'),(620,1,'testTask','renren',0,NULL,1,'2019-11-15 21:00:00'),(621,1,'testTask','renren',0,NULL,1,'2019-11-15 21:30:00'),(622,1,'testTask','renren',0,NULL,1,'2019-11-15 22:00:00'),(623,1,'testTask','renren',0,NULL,1,'2019-11-15 22:30:00'),(624,1,'testTask','renren',0,NULL,1,'2019-11-15 23:00:00'),(625,1,'testTask','renren',0,NULL,1,'2019-11-15 23:30:00'),(626,1,'testTask','renren',0,NULL,1,'2019-11-16 09:30:00'),(627,1,'testTask','renren',0,NULL,1,'2019-11-16 10:00:00'),(628,1,'testTask','renren',0,NULL,1,'2019-11-16 10:30:00'),(629,1,'testTask','renren',0,NULL,0,'2019-11-16 11:00:00'),(630,1,'testTask','renren',0,NULL,0,'2019-11-16 11:30:00'),(631,1,'testTask','renren',0,NULL,2,'2019-11-16 12:00:00'),(632,1,'testTask','renren',0,NULL,1,'2019-11-16 12:30:00'),(633,1,'testTask','renren',0,NULL,1,'2019-11-16 13:00:00'),(634,1,'testTask','renren',0,NULL,1,'2019-11-16 13:30:00'),(635,1,'testTask','renren',0,NULL,2,'2019-11-16 14:00:00'),(636,1,'testTask','renren',0,NULL,1,'2019-11-16 14:30:00'),(637,1,'testTask','renren',0,NULL,1,'2019-11-16 15:00:00'),(638,1,'testTask','renren',0,NULL,1,'2019-11-16 15:30:00'),(639,1,'testTask','renren',0,NULL,1,'2019-11-16 16:00:00'),(640,1,'testTask','renren',0,NULL,1,'2019-11-16 16:30:00'),(641,1,'testTask','renren',0,NULL,1,'2019-11-16 17:00:00'),(642,1,'testTask','renren',0,NULL,0,'2019-11-16 17:30:00'),(643,1,'testTask','renren',0,NULL,1,'2019-11-16 18:00:00'),(644,1,'testTask','renren',0,NULL,1,'2019-11-16 18:30:00'),(645,1,'testTask','renren',0,NULL,1,'2019-11-16 19:00:00'),(646,1,'testTask','renren',0,NULL,0,'2019-11-16 19:30:00'),(647,1,'testTask','renren',0,NULL,1,'2019-11-16 20:00:00'),(648,1,'testTask','renren',0,NULL,0,'2019-11-16 20:30:00'),(649,1,'testTask','renren',0,NULL,1,'2019-11-16 21:00:00'),(650,1,'testTask','renren',0,NULL,1,'2019-11-16 21:30:00'),(651,1,'testTask','renren',0,NULL,1,'2019-11-16 22:00:00'),(652,1,'testTask','renren',0,NULL,1,'2019-11-16 22:30:00'),(653,1,'testTask','renren',0,NULL,1,'2019-11-16 23:00:00'),(654,1,'testTask','renren',0,NULL,1,'2019-11-16 23:30:00'),(655,1,'testTask','renren',0,NULL,4,'2019-11-17 00:00:00'),(656,1,'testTask','renren',0,NULL,0,'2019-11-17 00:30:00'),(657,1,'testTask','renren',0,NULL,1,'2019-11-17 12:00:00'),(658,1,'testTask','renren',0,NULL,1,'2019-11-17 12:30:00'),(659,1,'testTask','renren',0,NULL,0,'2019-11-17 13:00:00'),(660,1,'testTask','renren',0,NULL,0,'2019-11-17 13:30:00'),(661,1,'testTask','renren',0,NULL,1,'2019-11-17 14:00:00'),(662,1,'testTask','renren',0,NULL,1,'2019-11-17 14:30:00'),(663,1,'testTask','renren',0,NULL,0,'2019-11-17 15:00:00'),(664,1,'testTask','renren',0,NULL,1,'2019-11-17 15:30:00'),(665,1,'testTask','renren',0,NULL,1,'2019-11-17 16:00:00'),(666,1,'testTask','renren',0,NULL,1,'2019-11-17 16:30:00'),(667,1,'testTask','renren',0,NULL,1,'2019-11-17 17:00:00'),(668,1,'testTask','renren',0,NULL,1,'2019-11-17 17:30:00'),(669,1,'testTask','renren',0,NULL,1,'2019-11-17 18:00:00'),(670,1,'testTask','renren',0,NULL,1,'2019-11-17 18:30:00'),(671,1,'testTask','renren',0,NULL,1,'2019-11-17 19:00:00'),(672,1,'testTask','renren',0,NULL,1,'2019-11-17 19:30:00'),(673,1,'testTask','renren',0,NULL,1,'2019-11-17 20:00:00'),(674,1,'testTask','renren',0,NULL,1,'2019-11-17 20:30:00'),(675,1,'testTask','renren',0,NULL,1,'2019-11-17 21:00:00'),(676,1,'testTask','renren',0,NULL,1,'2019-11-17 22:00:00'),(677,1,'testTask','renren',0,NULL,0,'2019-11-17 22:30:00'),(678,1,'testTask','renren',0,NULL,1,'2019-11-18 09:30:00'),(679,1,'testTask','renren',0,NULL,1,'2019-11-18 10:00:00'),(680,1,'testTask','renren',0,NULL,1,'2019-11-18 10:30:00'),(681,1,'testTask','renren',0,NULL,1,'2019-11-18 11:00:00'),(682,1,'testTask','renren',0,NULL,1,'2019-11-18 11:30:00'),(683,1,'testTask','renren',0,NULL,0,'2019-11-18 12:00:00'),(684,1,'testTask','renren',0,NULL,1,'2019-11-18 12:30:00'),(685,1,'testTask','renren',0,NULL,0,'2019-11-18 13:00:00'),(686,1,'testTask','renren',0,NULL,1,'2019-11-18 13:30:00'),(687,1,'testTask','renren',0,NULL,1,'2019-11-18 14:00:00'),(688,1,'testTask','renren',0,NULL,0,'2019-11-18 14:30:00'),(689,1,'testTask','renren',0,NULL,1,'2019-11-18 15:00:00'),(690,1,'testTask','renren',0,NULL,0,'2019-11-18 15:30:00'),(691,1,'testTask','renren',0,NULL,1,'2019-11-18 16:00:00'),(692,1,'testTask','renren',0,NULL,1,'2019-11-18 16:30:00'),(693,1,'testTask','renren',0,NULL,1,'2019-11-18 17:00:00'),(694,1,'testTask','renren',0,NULL,2,'2019-11-18 17:30:00'),(695,1,'testTask','renren',0,NULL,1,'2019-11-18 18:00:00'),(696,1,'testTask','renren',0,NULL,0,'2019-11-18 18:30:00'),(697,1,'testTask','renren',0,NULL,1,'2019-11-18 19:00:00'),(698,1,'testTask','renren',0,NULL,1,'2019-11-18 19:30:00'),(699,1,'testTask','renren',0,NULL,1,'2019-11-18 20:30:00'),(700,1,'testTask','renren',0,NULL,1,'2019-11-18 21:00:00'),(701,1,'testTask','renren',0,NULL,0,'2019-11-18 21:30:00'),(702,1,'testTask','renren',0,NULL,1,'2019-11-18 22:00:00'),(703,1,'testTask','renren',0,NULL,1,'2019-11-18 22:30:00'),(704,1,'testTask','renren',0,NULL,0,'2019-11-18 23:00:00'),(705,1,'testTask','renren',0,NULL,0,'2019-11-18 23:30:00'),(706,1,'testTask','renren',0,NULL,14,'2019-11-19 00:00:00'),(707,1,'testTask','renren',0,NULL,1,'2019-11-19 09:30:00'),(708,1,'testTask','renren',0,NULL,1,'2019-11-19 10:00:00'),(709,1,'testTask','renren',0,NULL,1,'2019-11-19 10:30:00'),(710,1,'testTask','renren',0,NULL,1,'2019-11-19 11:00:00'),(711,1,'testTask','renren',0,NULL,0,'2019-11-19 11:30:00'),(712,1,'testTask','renren',0,NULL,1,'2019-11-19 12:00:00'),(713,1,'testTask','renren',0,NULL,1,'2019-11-19 12:30:00'),(714,1,'testTask','renren',0,NULL,1,'2019-11-19 13:00:00'),(715,1,'testTask','renren',0,NULL,1,'2019-11-19 13:30:00'),(716,1,'testTask','renren',0,NULL,1,'2019-11-19 14:00:00'),(717,1,'testTask','renren',0,NULL,1,'2019-11-19 14:30:00'),(718,1,'testTask','renren',0,NULL,0,'2019-11-19 15:00:00'),(719,1,'testTask','renren',0,NULL,1,'2019-11-19 15:30:00'),(720,1,'testTask','renren',0,NULL,0,'2019-11-19 16:00:00'),(721,1,'testTask','renren',0,NULL,1,'2019-11-19 16:30:00'),(722,1,'testTask','renren',0,NULL,1,'2019-11-19 17:00:00'),(723,1,'testTask','renren',0,NULL,0,'2019-11-19 17:30:00'),(724,1,'testTask','renren',0,NULL,0,'2019-11-19 18:00:00'),(725,1,'testTask','renren',0,NULL,0,'2019-11-19 18:30:00'),(726,1,'testTask','renren',0,NULL,0,'2019-11-19 19:00:00'),(727,1,'testTask','renren',0,NULL,0,'2019-11-19 19:30:00'),(728,1,'testTask','renren',0,NULL,1,'2019-11-19 20:00:00'),(729,1,'testTask','renren',0,NULL,1,'2019-11-19 20:30:00'),(730,1,'testTask','renren',0,NULL,0,'2019-11-19 21:00:00'),(731,1,'testTask','renren',0,NULL,0,'2019-11-19 21:30:00'),(732,1,'testTask','renren',0,NULL,0,'2019-11-19 22:00:00'),(733,1,'testTask','renren',0,NULL,1,'2019-11-19 22:30:00'),(734,1,'testTask','renren',0,NULL,1,'2019-11-19 23:00:00'),(735,1,'testTask','renren',0,NULL,0,'2019-11-19 23:30:00'),(736,1,'testTask','renren',0,NULL,4,'2019-11-20 00:00:00'),(737,1,'testTask','renren',0,NULL,1,'2019-11-20 09:30:00'),(738,1,'testTask','renren',0,NULL,0,'2019-11-20 10:00:00'),(739,1,'testTask','renren',0,NULL,1,'2019-11-20 10:30:00'),(740,1,'testTask','renren',0,NULL,1,'2019-11-20 11:00:00'),(741,1,'testTask','renren',0,NULL,0,'2019-11-20 11:30:00'),(742,1,'testTask','renren',0,NULL,1,'2019-11-20 12:00:00'),(743,1,'testTask','renren',0,NULL,1,'2019-11-20 12:30:00'),(744,1,'testTask','renren',0,NULL,1,'2019-11-20 13:00:00'),(745,1,'testTask','renren',0,NULL,1,'2019-11-20 13:30:00'),(746,1,'testTask','renren',0,NULL,1,'2019-11-20 14:00:00'),(747,1,'testTask','renren',0,NULL,0,'2019-11-20 15:00:00'),(748,1,'testTask','renren',0,NULL,1,'2019-11-20 15:30:00'),(749,1,'testTask','renren',0,NULL,1,'2019-11-20 16:00:00'),(750,1,'testTask','renren',0,NULL,0,'2019-11-20 16:30:00'),(751,1,'testTask','renren',0,NULL,1,'2019-11-20 17:00:00'),(752,1,'testTask','renren',0,NULL,1,'2019-11-20 17:30:00'),(753,1,'testTask','renren',0,NULL,1,'2019-11-20 18:00:00'),(754,1,'testTask','renren',0,NULL,1,'2019-11-20 18:30:00'),(755,1,'testTask','renren',0,NULL,1,'2019-11-20 19:00:00'),(756,1,'testTask','renren',0,NULL,0,'2019-11-20 19:30:00'),(757,1,'testTask','renren',0,NULL,1,'2019-11-20 20:00:00'),(758,1,'testTask','renren',0,NULL,0,'2019-11-20 20:30:00'),(759,1,'testTask','renren',0,NULL,0,'2019-11-20 21:00:00'),(760,1,'testTask','renren',0,NULL,0,'2019-11-20 21:30:00'),(761,1,'testTask','renren',0,NULL,1,'2019-11-20 22:00:00'),(762,1,'testTask','renren',0,NULL,1,'2019-11-20 22:30:00'),(763,1,'testTask','renren',0,NULL,1,'2019-11-20 23:00:00'),(764,1,'testTask','renren',0,NULL,0,'2019-11-20 23:30:00'),(765,1,'testTask','renren',0,NULL,1,'2019-11-21 09:30:00'),(766,1,'testTask','renren',0,NULL,1,'2019-11-21 10:00:00'),(767,1,'testTask','renren',0,NULL,1,'2019-11-21 10:30:00'),(768,1,'testTask','renren',0,NULL,1,'2019-11-21 11:00:00'),(769,1,'testTask','renren',0,NULL,0,'2019-11-21 11:30:00'),(770,1,'testTask','renren',0,NULL,1,'2019-11-21 12:00:00'),(771,1,'testTask','renren',0,NULL,1,'2019-11-21 12:30:00'),(772,1,'testTask','renren',0,NULL,1,'2019-11-21 13:00:00'),(773,1,'testTask','renren',0,NULL,1,'2019-11-21 13:30:00'),(774,1,'testTask','renren',0,NULL,0,'2019-11-21 14:00:00'),(775,1,'testTask','renren',0,NULL,1,'2019-11-21 14:30:00'),(776,1,'testTask','renren',0,NULL,1,'2019-11-21 15:00:00'),(777,1,'testTask','renren',0,NULL,0,'2019-11-21 15:30:00'),(778,1,'testTask','renren',0,NULL,0,'2019-11-21 16:00:00'),(779,1,'testTask','renren',0,NULL,0,'2019-11-21 16:30:00'),(780,1,'testTask','renren',0,NULL,1,'2019-11-21 17:00:00'),(781,1,'testTask','renren',0,NULL,1,'2019-11-21 17:30:00'),(782,1,'testTask','renren',0,NULL,1,'2019-11-21 18:00:00'),(783,1,'testTask','renren',0,NULL,1,'2019-11-21 18:30:00'),(784,1,'testTask','renren',0,NULL,1,'2019-11-21 19:00:00'),(785,1,'testTask','renren',0,NULL,1,'2019-11-21 19:30:00'),(786,1,'testTask','renren',0,NULL,1,'2019-11-21 20:00:00'),(787,1,'testTask','renren',0,NULL,0,'2019-11-21 20:30:00'),(788,1,'testTask','renren',0,NULL,1,'2019-11-21 21:00:00'),(789,1,'testTask','renren',0,NULL,1,'2019-11-21 21:30:00'),(790,1,'testTask','renren',0,NULL,1,'2019-11-21 22:00:00'),(791,1,'testTask','renren',0,NULL,1,'2019-11-21 22:30:00'),(792,1,'testTask','renren',0,NULL,1,'2019-11-22 12:00:00'),(793,1,'testTask','renren',0,NULL,0,'2019-11-22 12:30:00'),(794,1,'testTask','renren',0,NULL,0,'2019-11-22 13:00:00'),(795,1,'testTask','renren',0,NULL,1,'2019-11-22 13:30:00'),(796,1,'testTask','renren',0,NULL,0,'2019-11-22 14:00:00'),(797,1,'testTask','renren',0,NULL,1,'2019-11-22 14:30:00'),(798,1,'testTask','renren',0,NULL,0,'2019-11-22 15:00:00'),(799,1,'testTask','renren',0,NULL,1,'2019-11-22 15:30:00'),(800,1,'testTask','renren',0,NULL,1,'2019-11-22 16:00:00'),(801,1,'testTask','renren',0,NULL,1,'2019-11-22 16:30:00'),(802,1,'testTask','renren',0,NULL,1,'2019-11-22 17:00:00'),(803,1,'testTask','renren',0,NULL,1,'2019-11-22 17:30:00'),(804,1,'testTask','renren',0,NULL,1,'2019-11-22 18:00:00'),(805,1,'testTask','renren',0,NULL,1,'2019-11-22 18:30:00'),(806,1,'testTask','renren',0,NULL,4,'2019-11-22 19:00:00'),(807,1,'testTask','renren',0,NULL,9,'2019-11-22 19:30:00'),(808,1,'testTask','renren',0,NULL,2,'2019-11-22 20:00:00'),(809,1,'testTask','renren',0,NULL,1,'2019-11-22 20:30:00'),(810,1,'testTask','renren',0,NULL,2,'2019-11-22 21:00:00'),(811,1,'testTask','renren',0,NULL,1,'2019-11-22 21:30:00'),(812,1,'testTask','renren',0,NULL,1,'2019-11-22 22:00:00'),(813,1,'testTask','renren',0,NULL,2,'2019-11-22 22:30:00'),(814,1,'testTask','renren',0,NULL,2,'2019-11-22 23:00:00'),(815,1,'testTask','renren',0,NULL,2,'2019-11-22 23:30:00'),(816,1,'testTask','renren',0,NULL,29,'2019-11-23 00:00:00'),(817,1,'testTask','renren',0,NULL,1,'2019-11-24 14:00:00'),(818,1,'testTask','renren',0,NULL,0,'2019-11-24 14:30:00'),(819,1,'testTask','renren',0,NULL,1,'2019-11-24 15:00:00'),(820,1,'testTask','renren',0,NULL,1,'2019-11-24 15:30:00'),(821,1,'testTask','renren',0,NULL,1,'2019-11-24 16:00:00'),(822,1,'testTask','renren',0,NULL,1,'2019-11-24 16:30:00'),(823,1,'testTask','renren',0,NULL,1,'2019-11-24 17:00:00'),(824,1,'testTask','renren',0,NULL,1,'2019-11-24 17:30:00'),(825,1,'testTask','renren',0,NULL,1,'2019-11-24 18:00:00'),(826,1,'testTask','renren',0,NULL,1,'2019-11-24 18:30:00'),(827,1,'testTask','renren',0,NULL,1,'2019-11-24 19:00:00'),(828,1,'testTask','renren',0,NULL,1,'2019-11-24 19:30:00'),(829,1,'testTask','renren',0,NULL,1,'2019-11-24 20:00:00'),(830,1,'testTask','renren',0,NULL,1,'2019-11-24 21:00:00'),(831,1,'testTask','renren',0,NULL,2,'2019-11-25 10:00:00'),(832,1,'testTask','renren',0,NULL,0,'2019-11-25 10:30:00'),(833,1,'testTask','renren',0,NULL,1,'2019-11-25 11:00:00'),(834,1,'testTask','renren',0,NULL,1,'2019-11-25 11:30:00'),(835,1,'testTask','renren',0,NULL,1,'2019-11-25 12:00:00'),(836,1,'testTask','renren',0,NULL,1,'2019-11-25 12:30:00'),(837,1,'testTask','renren',0,NULL,1,'2019-11-25 13:00:00'),(838,1,'testTask','renren',0,NULL,1,'2019-11-25 13:30:00'),(839,1,'testTask','renren',0,NULL,1,'2019-11-25 14:00:00'),(840,1,'testTask','renren',0,NULL,1,'2019-11-25 14:30:00'),(841,1,'testTask','renren',0,NULL,1,'2019-11-25 15:00:00'),(842,1,'testTask','renren',0,NULL,1,'2019-11-25 15:30:00'),(843,1,'testTask','renren',0,NULL,4,'2019-11-25 16:00:00'),(844,1,'testTask','renren',0,NULL,2,'2019-11-25 16:30:00'),(845,1,'testTask','renren',0,NULL,2,'2019-11-25 17:00:00'),(846,1,'testTask','renren',0,NULL,2,'2019-11-25 17:30:00'),(847,1,'testTask','renren',0,NULL,1,'2019-11-25 18:00:00'),(848,1,'testTask','renren',0,NULL,1,'2019-11-25 18:30:00'),(849,1,'testTask','renren',0,NULL,1,'2019-11-25 19:00:00'),(850,1,'testTask','renren',0,NULL,2,'2019-11-25 22:30:00'),(851,1,'testTask','renren',0,NULL,1,'2019-11-25 23:00:00'),(852,1,'testTask','renren',0,NULL,2,'2019-11-25 23:30:00'),(853,1,'testTask','renren',0,NULL,1,'2019-11-26 10:00:00'),(854,1,'testTask','renren',0,NULL,1,'2019-11-26 10:30:00'),(855,1,'testTask','renren',0,NULL,1,'2019-11-26 11:00:00'),(856,1,'testTask','renren',0,NULL,6,'2019-11-26 11:30:00'),(857,1,'testTask','renren',0,NULL,1,'2019-11-26 12:00:00'),(858,1,'testTask','renren',0,NULL,0,'2019-11-26 12:30:00'),(859,1,'testTask','renren',0,NULL,1,'2019-11-26 13:00:00'),(860,1,'testTask','renren',0,NULL,0,'2019-11-26 13:30:00'),(861,1,'testTask','renren',0,NULL,0,'2019-11-26 14:00:00'),(862,1,'testTask','renren',0,NULL,0,'2019-11-26 14:30:00'),(863,1,'testTask','renren',0,NULL,0,'2019-11-26 15:00:00'),(864,1,'testTask','renren',0,NULL,1,'2019-11-26 15:30:00'),(865,1,'testTask','renren',0,NULL,5,'2019-11-26 16:00:00'),(866,1,'testTask','renren',0,NULL,1,'2019-11-26 16:30:00'),(867,1,'testTask','renren',0,NULL,4,'2019-11-26 17:00:00'),(868,1,'testTask','renren',0,NULL,1,'2019-11-26 17:30:00'),(869,1,'testTask','renren',0,NULL,0,'2019-11-26 18:00:00'),(870,1,'testTask','renren',0,NULL,2,'2019-11-26 18:30:00'),(871,1,'testTask','renren',0,NULL,1,'2019-11-26 19:00:00'),(872,1,'testTask','renren',0,NULL,1,'2019-11-26 19:30:00'),(873,1,'testTask','renren',0,NULL,0,'2019-11-26 20:00:00'),(874,1,'testTask','renren',0,NULL,1,'2019-11-26 20:30:00'),(875,1,'testTask','renren',0,NULL,1,'2019-11-26 21:00:00'),(876,1,'testTask','renren',0,NULL,1,'2019-11-27 11:00:00'),(877,1,'testTask','renren',0,NULL,1,'2019-11-27 11:30:00'),(878,1,'testTask','renren',0,NULL,1,'2019-11-27 12:00:00'),(879,1,'testTask','renren',0,NULL,4,'2019-11-27 12:30:00'),(880,1,'testTask','renren',0,NULL,1,'2019-11-27 13:00:00'),(881,1,'testTask','renren',0,NULL,1,'2019-11-27 13:30:00'),(882,1,'testTask','renren',0,NULL,1,'2019-11-27 14:00:00'),(883,1,'testTask','renren',0,NULL,2,'2019-11-27 14:30:00'),(884,1,'testTask','renren',0,NULL,0,'2019-11-27 15:00:00'),(885,1,'testTask','renren',0,NULL,1,'2019-11-27 15:30:00'),(886,1,'testTask','renren',0,NULL,1,'2019-11-27 16:00:00'),(887,1,'testTask','renren',0,NULL,1,'2019-11-27 16:30:00'),(888,1,'testTask','renren',0,NULL,2,'2019-11-27 18:00:00'),(889,1,'testTask','renren',0,NULL,1,'2019-11-27 18:30:00'),(890,1,'testTask','renren',0,NULL,1,'2019-11-27 19:00:00'),(891,1,'testTask','renren',0,NULL,0,'2019-11-27 19:30:00'),(892,1,'testTask','renren',0,NULL,0,'2019-11-27 20:00:00'),(893,1,'testTask','renren',0,NULL,0,'2019-11-27 20:30:00'),(894,1,'testTask','renren',0,NULL,1,'2019-11-27 21:00:00'),(895,1,'testTask','renren',0,NULL,1,'2019-11-27 21:30:00'),(896,1,'testTask','renren',0,NULL,1,'2019-11-27 22:00:00'),(897,1,'testTask','renren',0,NULL,1,'2019-11-27 22:30:00'),(898,1,'testTask','renren',0,NULL,1,'2019-11-27 23:00:00'),(899,1,'testTask','renren',0,NULL,37,'2019-11-27 23:30:00'),(900,1,'testTask','renren',0,NULL,508,'2019-11-28 00:00:00'),(901,1,'testTask','renren',0,NULL,20,'2019-11-28 00:30:00'),(902,1,'testTask','renren',0,NULL,1,'2019-11-28 10:00:00'),(903,1,'testTask','renren',0,NULL,1,'2019-11-28 10:30:00'),(904,1,'testTask','renren',0,NULL,2,'2019-11-28 11:00:00'),(905,1,'testTask','renren',0,NULL,0,'2019-11-28 11:30:00'),(906,1,'testTask','renren',0,NULL,1,'2019-11-28 12:00:00'),(907,1,'testTask','renren',0,NULL,0,'2019-11-28 12:30:00'),(908,1,'testTask','renren',0,NULL,1,'2019-11-28 13:00:00'),(909,1,'testTask','renren',0,NULL,1,'2019-11-28 13:30:00'),(910,1,'testTask','renren',0,NULL,1,'2019-11-28 14:00:00'),(911,1,'testTask','renren',0,NULL,0,'2019-11-28 14:30:00'),(912,1,'testTask','renren',0,NULL,1,'2019-11-28 15:00:00'),(913,1,'testTask','renren',0,NULL,1,'2019-11-28 15:30:00'),(914,1,'testTask','renren',0,NULL,1,'2019-11-28 16:00:00'),(915,1,'testTask','renren',0,NULL,1,'2019-11-28 16:30:00'),(916,1,'testTask','renren',0,NULL,0,'2019-11-28 17:00:00'),(917,1,'testTask','renren',0,NULL,1,'2019-11-28 17:30:00'),(918,1,'testTask','renren',0,NULL,1,'2019-11-28 18:00:00'),(919,1,'testTask','renren',0,NULL,1,'2019-11-28 18:30:00'),(920,1,'testTask','renren',0,NULL,1,'2019-11-28 19:00:00'),(921,1,'testTask','renren',0,NULL,1,'2019-11-28 19:30:00'),(922,1,'testTask','renren',0,NULL,1,'2019-11-28 20:00:00'),(923,1,'testTask','renren',0,NULL,1,'2019-11-28 20:30:00'),(924,1,'testTask','renren',0,NULL,0,'2019-11-28 21:00:00'),(925,1,'testTask','renren',0,NULL,0,'2019-11-28 21:30:00'),(926,1,'testTask','renren',0,NULL,1,'2019-11-28 22:00:00'),(927,1,'testTask','renren',0,NULL,0,'2019-11-28 22:30:00'),(928,1,'testTask','renren',0,NULL,1,'2019-11-29 09:30:00'),(929,1,'testTask','renren',0,NULL,0,'2019-11-29 10:00:00'),(930,1,'testTask','renren',0,NULL,0,'2019-11-29 10:30:00'),(931,1,'testTask','renren',0,NULL,0,'2019-11-29 11:00:00'),(932,1,'testTask','renren',0,NULL,1,'2019-11-29 11:30:00'),(933,1,'testTask','renren',0,NULL,0,'2019-11-29 12:00:00'),(934,1,'testTask','renren',0,NULL,1,'2019-11-29 12:30:00'),(935,1,'testTask','renren',0,NULL,0,'2019-11-29 13:00:00'),(936,1,'testTask','renren',0,NULL,1,'2019-11-29 13:30:00'),(937,1,'testTask','renren',0,NULL,0,'2019-11-29 14:00:00'),(938,1,'testTask','renren',0,NULL,0,'2019-11-29 14:30:00'),(939,1,'testTask','renren',0,NULL,1,'2019-11-29 15:00:00'),(940,1,'testTask','renren',0,NULL,1,'2019-11-29 15:30:00'),(941,1,'testTask','renren',0,NULL,0,'2019-11-29 16:00:00'),(942,1,'testTask','renren',0,NULL,3,'2019-11-29 16:30:00'),(943,1,'testTask','renren',0,NULL,1,'2019-11-29 17:00:00'),(944,1,'testTask','renren',0,NULL,1,'2019-11-29 17:30:00'),(945,1,'testTask','renren',0,NULL,1,'2019-11-29 18:00:00'),(946,1,'testTask','renren',0,NULL,1,'2019-11-29 18:30:00'),(947,1,'testTask','renren',0,NULL,1,'2019-11-29 19:00:00'),(948,1,'testTask','renren',0,NULL,1,'2019-11-29 19:30:00'),(949,1,'testTask','renren',0,NULL,0,'2019-11-29 20:00:00'),(950,1,'testTask','renren',0,NULL,1,'2019-11-30 17:00:00'),(951,1,'testTask','renren',0,NULL,1,'2019-11-30 17:30:00'),(952,1,'testTask','renren',0,NULL,1,'2019-11-30 18:00:00'),(953,1,'testTask','renren',0,NULL,1,'2019-11-30 18:30:00'),(954,1,'testTask','renren',0,NULL,1,'2019-11-30 19:00:00'),(955,1,'testTask','renren',0,NULL,1,'2019-11-30 19:30:00'),(956,1,'testTask','renren',0,NULL,0,'2019-11-30 20:00:00'),(957,1,'testTask','renren',0,NULL,1,'2019-11-30 20:30:00'),(958,1,'testTask','renren',0,NULL,1,'2019-11-30 21:00:00'),(959,1,'testTask','renren',0,NULL,3,'2019-12-01 17:00:00'),(960,1,'testTask','renren',0,NULL,1,'2019-12-01 17:30:00'),(961,1,'testTask','renren',0,NULL,2,'2019-12-01 18:00:00'),(962,1,'testTask','renren',0,NULL,1,'2019-12-01 18:30:00'),(963,1,'testTask','renren',0,NULL,1,'2019-12-01 19:00:00'),(964,1,'testTask','renren',0,NULL,0,'2019-12-01 19:30:00'),(965,1,'testTask','renren',0,NULL,1,'2019-12-01 20:00:00'),(966,1,'testTask','renren',0,NULL,1,'2019-12-01 22:00:00'),(967,1,'testTask','renren',0,NULL,1,'2019-12-01 22:30:00'),(968,1,'testTask','renren',0,NULL,1,'2019-12-01 23:00:00'),(969,1,'testTask','renren',0,NULL,0,'2019-12-01 23:30:00'),(970,1,'testTask','renren',0,NULL,7,'2019-12-02 00:00:00'),(971,1,'testTask','renren',0,NULL,1,'2019-12-02 00:30:00'),(972,1,'testTask','renren',0,NULL,1,'2019-12-02 01:00:00'),(973,1,'testTask','renren',0,NULL,1,'2019-12-02 01:30:00'),(974,1,'testTask','renren',0,NULL,0,'2019-12-02 02:00:00'),(975,1,'testTask','renren',0,NULL,1,'2019-12-02 02:30:00'),(976,1,'testTask','renren',0,NULL,1,'2019-12-02 03:00:00'),(977,1,'testTask','renren',0,NULL,1,'2019-12-02 03:30:00'),(978,1,'testTask','renren',0,NULL,0,'2019-12-02 04:00:00'),(979,1,'testTask','renren',0,NULL,0,'2019-12-02 04:30:00'),(980,1,'testTask','renren',0,NULL,0,'2019-12-02 05:00:00'),(981,1,'testTask','renren',0,NULL,1,'2019-12-02 05:30:00'),(982,1,'testTask','renren',0,NULL,1,'2019-12-02 06:00:00'),(983,1,'testTask','renren',0,NULL,0,'2019-12-02 06:30:00'),(984,1,'testTask','renren',0,NULL,0,'2019-12-02 07:00:00'),(985,1,'testTask','renren',0,NULL,1,'2019-12-02 07:30:00'),(986,1,'testTask','renren',0,NULL,1,'2019-12-02 08:00:00'),(987,1,'testTask','renren',0,NULL,0,'2019-12-02 08:30:00'),(988,1,'testTask','renren',0,NULL,1,'2019-12-02 09:00:00'),(989,1,'testTask','renren',0,NULL,1,'2019-12-02 09:30:00'),(990,1,'testTask','renren',0,NULL,0,'2019-12-02 10:00:00'),(991,1,'testTask','renren',0,NULL,1,'2019-12-02 10:30:00'),(992,1,'testTask','renren',0,NULL,1,'2019-12-02 11:00:00'),(993,1,'testTask','renren',0,NULL,1,'2019-12-02 11:30:00'),(994,1,'testTask','renren',0,NULL,1,'2019-12-02 12:00:00'),(995,1,'testTask','renren',0,NULL,0,'2019-12-02 12:30:00'),(996,1,'testTask','renren',0,NULL,0,'2019-12-02 13:00:00'),(997,1,'testTask','renren',0,NULL,1,'2019-12-02 13:30:00'),(998,1,'testTask','renren',0,NULL,1,'2019-12-02 14:00:00'),(999,1,'testTask','renren',0,NULL,1,'2019-12-02 14:30:00'),(1000,1,'testTask','renren',0,NULL,1,'2019-12-02 15:00:00'),(1001,1,'testTask','renren',0,NULL,0,'2019-12-02 15:30:00'),(1002,1,'testTask','renren',0,NULL,1,'2019-12-02 16:00:00'),(1003,1,'testTask','renren',0,NULL,1,'2019-12-02 16:30:00'),(1004,1,'testTask','renren',0,NULL,2,'2019-12-02 17:00:00'),(1005,1,'testTask','renren',0,NULL,0,'2019-12-02 17:30:00'),(1006,1,'testTask','renren',0,NULL,1,'2019-12-11 13:30:00'),(1007,1,'testTask','renren',0,NULL,1,'2019-12-11 14:00:00'),(1008,1,'testTask','renren',0,NULL,1,'2019-12-11 14:30:00'),(1009,1,'testTask','renren',0,NULL,1,'2019-12-11 15:00:00'),(1010,1,'testTask','renren',0,NULL,1,'2019-12-11 15:30:00'),(1011,1,'testTask','renren',0,NULL,0,'2019-12-11 16:00:00'),(1012,1,'testTask','renren',0,NULL,0,'2019-12-11 16:30:00'),(1013,1,'testTask','renren',0,NULL,1,'2019-12-11 17:00:00'),(1014,1,'testTask','renren',0,NULL,1,'2019-12-11 17:30:00'),(1015,1,'testTask','renren',0,NULL,0,'2019-12-11 18:00:00'),(1016,1,'testTask','renren',0,NULL,1,'2019-12-11 18:30:00'),(1017,1,'testTask','renren',0,NULL,0,'2019-12-11 19:00:00'),(1018,1,'testTask','renren',0,NULL,0,'2019-12-11 19:30:00'),(1019,1,'testTask','renren',0,NULL,1,'2019-12-11 20:00:00'),(1020,1,'testTask','renren',0,NULL,1,'2019-12-11 20:30:00'),(1021,1,'testTask','renren',0,NULL,1,'2019-12-11 21:00:00'),(1022,1,'testTask','renren',0,NULL,1,'2019-12-11 21:30:00'),(1023,1,'testTask','renren',0,NULL,1,'2019-12-12 18:00:00'),(1024,1,'testTask','renren',0,NULL,0,'2019-12-13 11:00:00'),(1025,1,'testTask','renren',0,NULL,1,'2019-12-13 11:30:00'),(1026,1,'testTask','renren',0,NULL,1,'2019-12-13 12:00:00'),(1027,1,'testTask','renren',0,NULL,0,'2019-12-13 12:30:00'),(1028,1,'testTask','renren',0,NULL,1,'2019-12-13 13:00:00'),(1029,1,'testTask','renren',0,NULL,2,'2019-12-13 13:30:00'),(1030,1,'testTask','renren',0,NULL,1,'2019-12-13 14:00:00'),(1031,1,'testTask','renren',0,NULL,1,'2019-12-13 14:30:00'),(1032,1,'testTask','renren',0,NULL,1,'2019-12-13 15:00:00'),(1033,1,'testTask','renren',0,NULL,1,'2019-12-13 15:30:00'),(1034,1,'testTask','renren',0,NULL,1,'2019-12-13 16:00:00'),(1035,1,'testTask','renren',0,NULL,1,'2019-12-13 16:30:00'),(1036,1,'testTask','renren',0,NULL,0,'2019-12-13 17:00:00'),(1037,1,'testTask','renren',0,NULL,1,'2019-12-13 17:30:00'),(1038,1,'testTask','renren',0,NULL,0,'2019-12-13 18:00:00'),(1039,1,'testTask','renren',0,NULL,1,'2019-12-13 18:30:00'),(1040,1,'testTask','renren',0,NULL,2,'2019-12-13 19:00:00'),(1041,1,'testTask','renren',0,NULL,1,'2019-12-13 19:30:00'),(1042,1,'testTask','renren',0,NULL,0,'2019-12-13 20:00:00'),(1043,1,'testTask','renren',0,NULL,1,'2019-12-13 20:30:00'),(1044,1,'testTask','renren',0,NULL,1,'2019-12-13 21:00:00'),(1045,1,'testTask','renren',0,NULL,1,'2019-12-15 23:30:00'),(1046,1,'testTask','renren',0,NULL,5,'2019-12-16 00:00:00'),(1047,1,'testTask','renren',0,NULL,1,'2019-12-16 00:30:00'),(1048,1,'testTask','renren',0,NULL,0,'2019-12-16 09:30:00'),(1049,1,'testTask','renren',0,NULL,1,'2019-12-16 10:00:00'),(1050,1,'testTask','renren',0,NULL,1,'2019-12-16 10:30:00'),(1051,1,'testTask','renren',0,NULL,0,'2019-12-16 11:00:00'),(1052,1,'testTask','renren',0,NULL,1,'2019-12-16 11:30:00'),(1053,1,'testTask','renren',0,NULL,1,'2019-12-16 12:00:00'),(1054,1,'testTask','renren',0,NULL,1,'2019-12-16 12:30:00'),(1055,1,'testTask','renren',0,NULL,1,'2019-12-16 13:00:00'),(1056,1,'testTask','renren',0,NULL,1,'2019-12-16 13:30:00'),(1057,1,'testTask','renren',0,NULL,1,'2019-12-16 14:00:00'),(1058,1,'testTask','renren',0,NULL,0,'2019-12-16 14:30:00'),(1059,1,'testTask','renren',0,NULL,0,'2019-12-16 15:00:00'),(1060,1,'testTask','renren',0,NULL,3,'2019-12-16 15:30:00'),(1061,1,'testTask','renren',0,NULL,1,'2019-12-16 16:00:00'),(1062,1,'testTask','renren',0,NULL,1,'2019-12-16 16:30:00'),(1063,1,'testTask','renren',0,NULL,1,'2019-12-16 17:00:00'),(1064,1,'testTask','renren',0,NULL,1,'2019-12-18 23:00:00'),(1065,1,'testTask','renren',0,NULL,1,'2019-12-19 10:00:00'),(1066,1,'testTask','renren',0,NULL,0,'2019-12-19 10:30:00'),(1067,1,'testTask','renren',0,NULL,1,'2019-12-19 11:00:00'),(1068,1,'testTask','renren',0,NULL,2,'2019-12-19 11:30:00'),(1069,1,'testTask','renren',0,NULL,0,'2019-12-19 12:00:00'),(1070,1,'testTask','renren',0,NULL,1,'2019-12-19 12:30:00'),(1071,1,'testTask','renren',0,NULL,1,'2019-12-19 13:00:00'),(1072,1,'testTask','renren',0,NULL,1,'2019-12-19 13:30:00'),(1073,1,'testTask','renren',0,NULL,1,'2019-12-19 14:00:00'),(1074,1,'testTask','renren',0,NULL,0,'2019-12-19 14:30:00'),(1075,1,'testTask','renren',0,NULL,1,'2019-12-19 15:00:00'),(1076,1,'testTask','renren',0,NULL,0,'2019-12-19 15:30:00'),(1077,1,'testTask','renren',0,NULL,1,'2019-12-19 16:00:00'),(1078,1,'testTask','renren',0,NULL,0,'2019-12-19 16:30:00'),(1079,1,'testTask','renren',0,NULL,2,'2019-12-19 17:00:00'),(1080,1,'testTask','renren',0,NULL,1,'2019-12-19 17:30:00'),(1081,1,'testTask','renren',0,NULL,1,'2019-12-19 18:00:00'),(1082,1,'testTask','renren',0,NULL,1,'2019-12-19 18:30:00'),(1083,1,'testTask','renren',0,NULL,1,'2019-12-19 19:00:00'),(1084,1,'testTask','renren',0,NULL,2,'2019-12-19 19:30:00'),(1085,1,'testTask','renren',0,NULL,1,'2019-12-19 20:00:00'),(1086,1,'testTask','renren',0,NULL,1,'2019-12-19 20:30:00'),(1087,1,'testTask','renren',0,NULL,2,'2019-12-19 21:00:00'),(1088,1,'testTask','renren',0,NULL,1,'2019-12-19 21:30:00'),(1089,1,'testTask','renren',0,NULL,3,'2019-12-19 22:00:00'),(1090,1,'testTask','renren',0,NULL,0,'2019-12-19 22:30:00'),(1091,1,'testTask','renren',0,NULL,1,'2019-12-19 23:00:00'),(1092,1,'testTask','renren',0,NULL,1,'2019-12-19 23:30:00'),(1093,1,'testTask','renren',0,NULL,14,'2019-12-20 00:00:00'),(1094,1,'testTask','renren',0,NULL,1,'2019-12-20 11:00:00'),(1095,1,'testTask','renren',0,NULL,1,'2019-12-20 11:30:00'),(1096,1,'testTask','renren',0,NULL,1,'2019-12-20 12:00:00'),(1097,1,'testTask','renren',0,NULL,1,'2019-12-20 12:30:00'),(1098,1,'testTask','renren',0,NULL,0,'2019-12-20 13:00:00'),(1099,1,'testTask','renren',0,NULL,0,'2019-12-20 13:30:00'),(1100,1,'testTask','renren',0,NULL,1,'2019-12-20 14:00:00'),(1101,1,'testTask','renren',0,NULL,1,'2019-12-20 14:30:00'),(1102,1,'testTask','renren',0,NULL,1,'2019-12-20 15:00:00'),(1103,1,'testTask','renren',0,NULL,0,'2020-01-09 15:00:00'),(1104,1,'testTask','renren',0,NULL,1,'2020-01-09 15:30:00'),(1105,1,'testTask','renren',0,NULL,1,'2020-01-09 16:00:00'),(1106,1,'testTask','renren',0,NULL,1,'2020-01-09 16:30:00'),(1107,1,'testTask','renren',0,NULL,2,'2020-01-09 17:00:00'),(1108,1,'testTask','renren',0,NULL,1,'2020-01-09 17:30:00'),(1109,1,'testTask','renren',0,NULL,0,'2020-01-09 18:00:00'),(1110,1,'testTask','renren',0,NULL,1,'2020-01-09 18:30:00'),(1111,1,'testTask','renren',0,NULL,1,'2020-01-09 19:00:00'),(1112,1,'testTask','renren',0,NULL,0,'2020-01-09 19:30:00'),(1113,1,'testTask','renren',0,NULL,1,'2020-01-09 20:00:00'),(1114,1,'testTask','renren',0,NULL,1,'2020-01-09 20:30:00'),(1115,1,'testTask','renren',0,NULL,2,'2020-01-09 21:00:00'),(1116,1,'testTask','renren',0,NULL,1,'2020-01-09 21:30:00'),(1117,1,'testTask','renren',0,NULL,1,'2020-01-09 22:00:00'),(1118,1,'testTask','renren',0,NULL,2,'2020-01-09 22:30:00'),(1119,1,'testTask','renren',0,NULL,2,'2020-01-09 23:00:00'),(1120,1,'testTask','renren',0,NULL,1,'2020-01-09 23:30:00'),(1121,1,'testTask','renren',0,NULL,14,'2020-01-10 00:00:00'),(1122,1,'testTask','renren',0,NULL,1,'2020-01-27 12:00:00'),(1123,1,'testTask','renren',0,NULL,1,'2020-01-27 12:30:00'),(1124,1,'testTask','renren',0,NULL,1,'2020-01-27 13:00:00'),(1125,1,'testTask','renren',0,NULL,1,'2020-01-27 13:30:00'),(1126,1,'testTask','renren',0,NULL,1,'2020-01-27 14:00:00'),(1127,1,'testTask','renren',0,NULL,1,'2020-01-27 14:30:00'),(1128,1,'testTask','renren',0,NULL,0,'2020-01-27 15:00:00'),(1129,1,'testTask','renren',0,NULL,2,'2020-01-28 11:30:00'),(1130,1,'testTask','renren',0,NULL,1,'2020-01-28 12:00:00'),(1131,1,'testTask','renren',0,NULL,1,'2020-01-28 12:30:00'),(1132,1,'testTask','renren',0,NULL,1,'2020-01-28 13:00:00'),(1133,1,'testTask','renren',0,NULL,0,'2020-01-28 13:30:00'),(1134,1,'testTask','renren',0,NULL,10,'2020-01-28 14:00:00'),(1135,1,'testTask','renren',0,NULL,1,'2020-01-28 14:30:00'),(1136,1,'testTask','renren',0,NULL,1,'2020-01-28 15:00:00'),(1137,1,'testTask','renren',0,NULL,1,'2020-01-28 15:30:00'),(1138,1,'testTask','renren',0,NULL,1,'2020-01-28 16:00:00'),(1139,1,'testTask','renren',0,NULL,1,'2020-01-28 16:30:00'),(1140,1,'testTask','renren',0,NULL,0,'2020-01-28 17:00:00'),(1141,1,'testTask','renren',0,NULL,0,'2020-01-28 17:30:00'),(1142,1,'testTask','renren',0,NULL,1,'2020-01-28 18:00:00'),(1143,1,'testTask','renren',0,NULL,1,'2020-01-28 18:30:00'),(1144,1,'testTask','renren',0,NULL,1,'2020-01-28 19:00:00'),(1145,1,'testTask','renren',0,NULL,0,'2020-01-28 19:30:00'),(1146,1,'testTask','renren',0,NULL,0,'2020-01-28 20:00:00'),(1147,1,'testTask','renren',0,NULL,1,'2020-01-29 11:30:00'),(1148,1,'testTask','renren',0,NULL,1,'2020-01-29 12:30:00'),(1149,1,'testTask','renren',0,NULL,12,'2020-01-29 13:00:00'),(1150,1,'testTask','renren',0,NULL,1,'2020-01-29 13:30:00'),(1151,1,'testTask','renren',0,NULL,1,'2020-01-29 14:00:00'),(1152,1,'testTask','renren',0,NULL,1,'2020-01-29 14:30:00'),(1153,1,'testTask','renren',0,NULL,1,'2020-01-29 15:00:00'),(1154,1,'testTask','renren',0,NULL,1,'2020-01-29 15:30:00'),(1155,1,'testTask','renren',0,NULL,0,'2020-01-29 16:00:00'),(1156,1,'testTask','renren',0,NULL,1,'2020-01-29 16:30:00'),(1157,1,'testTask','renren',0,NULL,0,'2020-01-29 17:00:00'),(1158,1,'testTask','renren',0,NULL,1,'2020-01-30 11:00:00'),(1159,1,'testTask','renren',0,NULL,2,'2020-01-30 11:30:00'),(1160,1,'testTask','renren',0,NULL,1,'2020-01-30 12:00:00'),(1161,1,'testTask','renren',0,NULL,1,'2020-01-30 12:30:00'),(1162,1,'testTask','renren',0,NULL,1,'2020-01-30 13:00:00'),(1163,1,'testTask','renren',0,NULL,3,'2020-01-30 13:30:00'),(1164,1,'testTask','renren',0,NULL,2,'2020-01-30 14:00:00'),(1165,1,'testTask','renren',0,NULL,1,'2020-01-30 14:30:00'),(1166,1,'testTask','renren',0,NULL,1,'2020-01-30 15:00:00'),(1167,1,'testTask','renren',0,NULL,1,'2020-01-30 15:30:00'),(1168,1,'testTask','renren',0,NULL,1,'2020-01-30 16:00:00'),(1169,1,'testTask','renren',0,NULL,1,'2020-01-30 16:30:00'),(1170,1,'testTask','renren',0,NULL,0,'2020-01-30 17:00:00'),(1171,1,'testTask','renren',0,NULL,1,'2020-01-30 17:30:00'),(1172,1,'testTask','renren',0,NULL,1,'2020-01-30 18:00:00'),(1173,1,'testTask','renren',0,NULL,1,'2020-01-30 18:30:00'),(1174,1,'testTask','renren',0,NULL,1,'2020-01-30 19:00:00'),(1175,1,'testTask','renren',0,NULL,1,'2020-01-30 19:30:00'),(1176,1,'testTask','renren',0,NULL,0,'2020-01-30 20:00:00'),(1177,1,'testTask','renren',0,NULL,1,'2020-01-30 20:30:00'),(1178,1,'testTask','renren',0,NULL,3,'2020-02-01 10:30:00'),(1179,1,'testTask','renren',0,NULL,1,'2020-02-01 11:00:00'),(1180,1,'testTask','renren',0,NULL,1,'2020-02-01 11:30:00'),(1181,1,'testTask','renren',0,NULL,1,'2020-02-01 12:00:00'),(1182,1,'testTask','renren',0,NULL,1,'2020-02-01 12:30:00'),(1183,1,'testTask','renren',0,NULL,0,'2020-02-18 15:00:00'),(1184,1,'testTask','renren',0,NULL,1,'2020-02-18 15:30:00'),(1185,1,'testTask','renren',0,NULL,10,'2020-02-18 16:00:00'),(1186,1,'testTask','renren',0,NULL,0,'2020-02-18 18:00:00'),(1187,1,'testTask','renren',0,NULL,1,'2020-02-18 18:30:00'),(1188,1,'testTask','renren',0,NULL,1,'2020-02-18 19:00:00'),(1189,1,'testTask','renren',0,NULL,1,'2020-02-18 19:30:00'),(1190,1,'testTask','renren',0,NULL,1,'2020-02-18 20:00:00'),(1191,1,'testTask','renren',0,NULL,0,'2020-02-18 20:30:00'),(1192,1,'testTask','renren',0,NULL,1,'2020-02-18 21:00:00'),(1193,1,'testTask','renren',0,NULL,1,'2020-02-18 21:30:00'),(1194,1,'testTask','renren',0,NULL,1,'2020-02-18 22:00:00'),(1195,1,'testTask','renren',0,NULL,1,'2020-02-18 22:30:00'),(1196,1,'testTask','renren',0,NULL,1,'2020-02-18 23:00:00'),(1197,1,'testTask','renren',0,NULL,1,'2020-02-18 23:30:00'),(1198,1,'testTask','renren',0,NULL,1,'2020-02-19 15:30:00'),(1199,1,'testTask','renren',0,NULL,1,'2020-02-19 16:00:00'),(1200,1,'testTask','renren',0,NULL,1,'2020-02-19 16:30:00'),(1201,1,'testTask','renren',0,NULL,1,'2020-02-19 17:00:00'),(1202,1,'testTask','renren',0,NULL,1,'2020-02-19 17:30:00'),(1203,1,'testTask','renren',0,NULL,1,'2020-02-19 18:00:00'),(1204,1,'testTask','renren',0,NULL,1,'2020-02-19 18:30:00'),(1205,1,'testTask','renren',0,NULL,1,'2020-02-19 19:00:00'),(1206,1,'testTask','renren',0,NULL,0,'2020-02-19 19:30:00'),(1207,1,'testTask','renren',0,NULL,1,'2020-02-19 20:00:00'),(1208,1,'testTask','renren',0,NULL,0,'2020-02-19 20:30:00'),(1209,1,'testTask','renren',0,NULL,1,'2020-02-20 12:30:00'),(1210,1,'testTask','renren',0,NULL,1,'2020-02-20 13:00:00'),(1211,1,'testTask','renren',0,NULL,1,'2020-02-20 13:30:00'),(1212,1,'testTask','renren',0,NULL,1,'2020-02-20 14:00:00'),(1213,1,'testTask','renren',0,NULL,1,'2020-02-20 14:30:00'),(1214,1,'testTask','renren',0,NULL,0,'2020-02-20 15:00:00'),(1215,1,'testTask','renren',0,NULL,0,'2020-02-20 15:30:00'),(1216,1,'testTask','renren',0,NULL,1,'2020-02-20 16:00:00'),(1217,1,'testTask','renren',0,NULL,1,'2020-02-20 16:30:00'),(1218,1,'testTask','renren',0,NULL,1,'2020-02-20 17:00:00'),(1219,1,'testTask','renren',0,NULL,1,'2020-02-20 17:30:00'),(1220,1,'testTask','renren',0,NULL,2,'2020-02-20 21:00:00'),(1221,1,'testTask','renren',0,NULL,1,'2020-02-21 11:00:00'),(1222,1,'testTask','renren',0,NULL,1,'2020-02-21 11:30:00'),(1223,1,'testTask','renren',0,NULL,1,'2020-02-21 12:00:00'),(1224,1,'testTask','renren',0,NULL,0,'2020-02-21 12:30:00'),(1225,1,'testTask','renren',0,NULL,1,'2020-02-22 11:30:00'),(1226,1,'testTask','renren',0,NULL,0,'2020-02-22 12:00:00'),(1227,1,'testTask','renren',0,NULL,0,'2020-02-22 12:30:00'),(1228,1,'testTask','renren',0,NULL,1,'2020-02-22 13:00:00'),(1229,1,'testTask','renren',0,NULL,1,'2020-02-22 13:30:00'),(1230,1,'testTask','renren',0,NULL,3,'2020-02-22 14:00:00'),(1231,1,'testTask','renren',0,NULL,2,'2020-02-22 14:30:00'),(1232,1,'testTask','renren',0,NULL,1,'2020-02-22 15:00:00'),(1233,1,'testTask','renren',0,NULL,1,'2020-02-22 15:30:00'),(1234,1,'testTask','renren',0,NULL,0,'2020-02-22 16:00:00'),(1235,1,'testTask','renren',0,NULL,4,'2020-02-22 16:30:00'),(1236,1,'testTask','renren',0,NULL,0,'2020-02-22 17:00:00'),(1237,1,'testTask','renren',0,NULL,1,'2020-02-22 17:30:00'),(1238,1,'testTask','renren',0,NULL,1,'2020-02-24 16:00:00'),(1239,1,'testTask','renren',0,NULL,1,'2020-02-24 16:30:00'),(1240,1,'testTask','renren',0,NULL,0,'2020-02-24 17:00:00'),(1241,1,'testTask','renren',0,NULL,0,'2020-02-24 17:30:00'),(1242,1,'testTask','renren',0,NULL,0,'2020-02-24 18:00:00'),(1243,1,'testTask','renren',0,NULL,1,'2020-02-24 18:30:00'),(1244,1,'testTask','renren',0,NULL,1,'2020-02-24 19:00:00'),(1245,1,'testTask','renren',0,NULL,1,'2020-02-24 19:30:00'),(1246,1,'testTask','renren',0,NULL,3,'2020-02-24 20:00:00'),(1247,1,'testTask','renren',0,NULL,1,'2020-02-24 20:30:00'),(1248,1,'testTask','renren',0,NULL,1,'2020-02-24 21:00:00'),(1249,1,'testTask','renren',0,NULL,0,'2020-02-24 21:30:00'),(1250,1,'testTask','renren',0,NULL,1,'2020-02-24 22:00:00'),(1251,1,'testTask','renren',0,NULL,1,'2020-02-24 22:30:00'),(1252,1,'testTask','renren',0,NULL,1,'2020-02-24 23:00:00'),(1253,1,'testTask','renren',0,NULL,1,'2020-02-24 23:30:00'),(1254,1,'testTask','renren',0,NULL,10,'2020-02-25 00:00:00'),(1255,1,'testTask','renren',0,NULL,0,'2020-02-25 12:00:00'),(1256,1,'testTask','renren',0,NULL,1,'2020-02-25 12:30:00'),(1257,1,'testTask','renren',0,NULL,1,'2020-02-25 13:00:00'),(1258,1,'testTask','renren',0,NULL,1,'2020-02-25 13:30:00'),(1259,1,'testTask','renren',0,NULL,0,'2020-02-25 14:00:00'),(1260,1,'testTask','renren',0,NULL,1,'2020-02-25 14:30:00'),(1261,1,'testTask','renren',0,NULL,1,'2020-02-25 15:00:00'),(1262,1,'testTask','renren',0,NULL,1,'2020-02-25 15:30:00'),(1263,1,'testTask','renren',0,NULL,1,'2020-02-25 16:00:00'),(1264,1,'testTask','renren',0,NULL,1,'2020-02-25 16:30:00'),(1265,1,'testTask','renren',0,NULL,1,'2020-02-25 17:00:00'),(1266,1,'testTask','renren',0,NULL,1,'2020-02-25 17:30:00'),(1267,1,'testTask','renren',0,NULL,2,'2020-02-25 18:00:00'),(1268,1,'testTask','renren',0,NULL,3,'2020-02-25 18:30:00'),(1269,1,'testTask','renren',0,NULL,1,'2020-02-25 19:00:00'),(1270,1,'testTask','renren',0,NULL,2,'2020-02-25 19:30:00'),(1271,1,'testTask','renren',0,NULL,1,'2020-02-25 20:00:00'),(1272,1,'testTask','renren',0,NULL,1,'2020-02-25 20:30:00'),(1273,1,'testTask','renren',0,NULL,1,'2020-02-25 21:00:00'),(1274,1,'testTask','renren',0,NULL,2,'2020-02-25 21:30:00'),(1275,1,'testTask','renren',0,NULL,3,'2020-02-25 22:00:00'),(1276,1,'testTask','renren',0,NULL,1,'2020-03-04 09:30:00'),(1277,1,'testTask','renren',0,NULL,1,'2020-03-04 22:30:00'),(1278,1,'testTask','renren',0,NULL,2,'2020-03-04 23:00:00'),(1279,1,'testTask','renren',0,NULL,1,'2020-03-04 23:30:00'),(1280,1,'testTask','renren',0,NULL,9,'2020-03-05 00:00:00'),(1281,1,'testTask','renren',0,NULL,2,'2020-03-05 10:00:00'),(1282,1,'testTask','renren',0,NULL,2,'2020-03-05 10:30:00'),(1283,1,'testTask','renren',0,NULL,1,'2020-03-05 11:00:00'),(1284,1,'testTask','renren',0,NULL,1,'2020-03-05 11:30:00'),(1285,1,'testTask','renren',0,NULL,3,'2020-03-05 12:00:00'),(1286,1,'testTask','renren',0,NULL,1,'2020-03-05 14:00:00'),(1287,1,'testTask','renren',0,NULL,1,'2020-03-05 14:30:00'),(1288,1,'testTask','renren',0,NULL,0,'2020-03-05 15:00:00'),(1289,1,'testTask','renren',0,NULL,0,'2020-03-05 15:30:00'),(1290,1,'testTask','renren',0,NULL,0,'2020-03-05 16:00:00'),(1291,1,'testTask','renren',0,NULL,1,'2020-03-05 16:30:00'),(1292,1,'testTask','renren',0,NULL,1,'2020-03-05 17:00:00'),(1293,1,'testTask','renren',0,NULL,1,'2020-03-05 17:30:00'),(1294,1,'testTask','renren',0,NULL,1,'2020-03-05 18:00:00'),(1295,1,'testTask','renren',0,NULL,4,'2020-03-05 18:30:00'),(1296,1,'testTask','renren',0,NULL,1,'2020-03-05 19:00:00'),(1297,1,'testTask','renren',0,NULL,4,'2020-03-05 19:30:00'),(1298,1,'testTask','renren',0,NULL,3,'2020-03-05 20:00:00'),(1299,1,'testTask','renren',0,NULL,1,'2020-03-05 20:30:00'),(1300,1,'testTask','renren',0,NULL,2,'2020-03-05 21:00:00'),(1301,1,'testTask','renren',0,NULL,1,'2020-03-05 21:30:00'),(1302,1,'testTask','renren',0,NULL,1,'2020-03-07 21:30:00'),(1303,1,'testTask','renren',0,NULL,10,'2020-03-10 22:30:00'),(1304,1,'testTask','renren',0,NULL,0,'2020-03-10 23:00:00'),(1305,1,'testTask','renren',0,NULL,1,'2020-03-10 23:30:00'),(1306,1,'testTask','renren',0,NULL,0,'2020-03-11 00:00:00'),(1307,1,'testTask','renren',0,NULL,0,'2022-09-23 16:00:00'),(1308,1,'testTask','renren',0,NULL,1,'2022-09-23 16:30:00'),(1309,1,'testTask','renren',0,NULL,1,'2022-09-23 17:00:00'),(1310,1,'testTask','renren',0,NULL,0,'2022-09-23 17:30:00'),(1311,1,'testTask','renren',0,NULL,1,'2022-09-23 18:00:00'),(1312,1,'testTask','renren',0,NULL,0,'2022-09-23 18:30:00'),(1313,1,'testTask','renren',0,NULL,1,'2022-09-23 19:00:00'),(1314,1,'testTask','renren',0,NULL,0,'2022-09-23 19:30:00'),(1315,1,'testTask','renren',0,NULL,0,'2022-09-23 20:00:00'),(1316,1,'testTask','renren',0,NULL,1,'2022-09-23 20:30:00'),(1317,1,'testTask','renren',0,NULL,0,'2022-09-23 21:00:00'),(1318,1,'testTask','renren',0,NULL,1,'2022-09-24 10:00:00'),(1319,1,'testTask','renren',0,NULL,0,'2022-09-24 10:30:00'),(1320,1,'testTask','renren',0,NULL,1,'2022-09-24 11:00:00'),(1321,1,'testTask','renren',0,NULL,1,'2022-09-24 11:30:00'),(1322,1,'testTask','renren',0,NULL,0,'2022-09-24 12:00:00'),(1323,1,'testTask','renren',0,NULL,1,'2022-09-24 12:30:00'),(1324,1,'testTask','renren',0,NULL,0,'2022-09-24 13:00:00'),(1325,1,'testTask','renren',0,NULL,0,'2022-09-24 13:30:00'),(1326,1,'testTask','renren',0,NULL,1,'2022-09-24 14:00:00'),(1327,1,'testTask','renren',0,NULL,1,'2022-09-24 14:30:00'),(1328,1,'testTask','renren',0,NULL,0,'2022-09-28 19:00:00'),(1329,1,'testTask','renren',0,NULL,1,'2022-09-28 19:30:00'),(1330,1,'testTask','renren',0,NULL,1,'2022-09-28 20:00:00'),(1331,1,'testTask','renren',0,NULL,0,'2022-09-28 20:30:00'),(1332,1,'testTask','renren',0,NULL,0,'2022-09-28 21:00:00'),(1333,1,'testTask','renren',0,NULL,0,'2022-09-30 17:30:00'),(1334,1,'testTask','renren',0,NULL,0,'2022-09-30 18:00:00'),(1335,1,'testTask','renren',0,NULL,0,'2022-10-03 17:30:00'),(1336,1,'testTask','renren',0,NULL,1,'2022-10-04 11:30:00'),(1337,1,'testTask','renren',0,NULL,0,'2022-10-05 14:30:00'),(1338,1,'testTask','renren',0,NULL,1,'2022-10-05 15:00:00'),(1339,1,'testTask','renren',0,NULL,0,'2022-10-05 15:30:00'),(1340,1,'testTask','renren',0,NULL,0,'2022-10-05 16:00:00'),(1341,1,'testTask','renren',0,NULL,1,'2022-10-05 16:30:00'),(1342,1,'testTask','renren',0,NULL,0,'2022-10-05 17:00:00'),(1343,1,'testTask','renren',0,NULL,0,'2022-10-05 17:30:00'),(1344,1,'testTask','renren',0,NULL,0,'2022-10-05 18:00:00'),(1345,1,'testTask','renren',0,NULL,0,'2022-10-13 19:00:00'),(1346,1,'testTask','renren',0,NULL,0,'2022-10-13 19:30:00'),(1347,1,'testTask','renren',0,NULL,1,'2022-10-19 13:30:00'),(1348,1,'testTask','renren',0,NULL,1,'2022-10-31 13:30:00'),(1349,1,'testTask','renren',0,NULL,0,'2022-10-31 14:00:00'),(1350,1,'testTask','renren',0,NULL,1,'2022-10-31 14:30:00'),(1351,1,'testTask','renren',0,NULL,0,'2022-10-31 15:00:00'),(1352,1,'testTask','renren',0,NULL,0,'2022-11-01 10:00:00'),(1353,1,'testTask','renren',0,NULL,0,'2022-11-08 17:30:00'),(1354,1,'testTask','renren',0,NULL,0,'2022-11-15 10:30:00'),(1355,1,'testTask','renren',0,NULL,1,'2022-11-15 13:37:31'),(1356,1,'testTask','renren',0,NULL,1,'2022-11-15 15:00:00'),(1357,1,'testTask','renren',0,NULL,0,'2022-12-29 08:00:00'),(1358,1,'testTask','renren',0,NULL,0,'2022-12-29 10:30:00'),(1359,1,'testTask','renren',0,NULL,1,'2023-01-02 14:30:00'),(1360,1,'testTask','renren',0,NULL,0,'2023-01-02 15:00:00'),(1361,1,'testTask','renren',0,NULL,0,'2023-01-02 15:30:00'),(1362,1,'testTask','renren',0,NULL,0,'2023-01-02 16:00:00'),(1363,1,'testTask','renren',0,NULL,1,'2023-01-02 16:30:00'),(1364,1,'testTask','renren',0,NULL,0,'2023-01-02 17:00:00'),(1365,1,'testTask','renren',0,NULL,0,'2023-01-02 17:30:00'),(1366,1,'testTask','renren',0,NULL,1,'2023-01-02 18:00:00'),(1367,1,'testTask','renren',0,NULL,0,'2023-01-02 18:30:00'),(1368,1,'testTask','renren',0,NULL,0,'2023-01-02 19:00:00'),(1369,1,'testTask','renren',0,NULL,0,'2023-01-05 09:30:00'),(1370,1,'testTask','renren',0,NULL,2,'2023-01-05 10:00:00'),(1371,1,'testTask','renren',0,NULL,1,'2023-01-05 10:30:00'),(1372,1,'testTask','renren',0,NULL,0,'2023-01-05 11:00:00'),(1373,1,'testTask','renren',0,NULL,0,'2023-01-05 11:30:00'),(1374,1,'testTask','renren',0,NULL,1,'2023-01-05 12:00:00'),(1375,1,'testTask','renren',0,NULL,0,'2023-01-05 12:30:00'),(1376,1,'testTask','renren',0,NULL,1,'2023-01-05 13:00:00'),(1377,1,'testTask','renren',0,NULL,1,'2023-01-05 13:30:00'),(1378,1,'testTask','renren',0,NULL,0,'2023-01-05 14:00:00'),(1379,1,'testTask','renren',0,NULL,1,'2023-01-05 14:30:00'),(1380,1,'testTask','renren',0,NULL,1,'2023-01-05 15:00:00'),(1381,1,'testTask','renren',0,NULL,1,'2023-01-05 20:00:00'),(1382,1,'testTask','renren',0,NULL,0,'2023-01-05 20:30:00'),(1383,1,'testTask','renren',0,NULL,3,'2023-01-05 21:00:00'),(1384,1,'testTask','renren',0,NULL,1,'2023-01-05 21:30:00'),(1385,1,'testTask','renren',0,NULL,1,'2023-01-06 09:30:00'),(1386,1,'testTask','renren',0,NULL,0,'2023-01-06 10:00:00'),(1387,1,'testTask','renren',0,NULL,1,'2023-01-06 10:30:00'),(1388,1,'testTask','renren',0,NULL,0,'2023-01-06 11:00:00'),(1389,1,'testTask','renren',0,NULL,0,'2023-01-06 11:30:00'),(1390,1,'testTask','renren',0,NULL,1,'2023-01-06 15:00:00'),(1391,1,'testTask','renren',0,NULL,0,'2023-01-06 15:30:00'),(1392,1,'testTask','renren',0,NULL,0,'2023-01-06 16:00:00'),(1393,1,'testTask','renren',0,NULL,1,'2023-01-06 16:30:00'),(1394,1,'testTask','renren',0,NULL,1,'2023-01-06 17:00:00'),(1395,1,'testTask','renren',0,NULL,1,'2023-01-06 17:30:00'),(1396,1,'testTask','renren',0,NULL,0,'2023-01-06 18:00:00'),(1397,1,'testTask','renren',0,NULL,0,'2023-01-06 18:30:00'),(1398,1,'testTask','renren',0,NULL,0,'2023-01-11 14:30:00'),(1399,1,'testTask','renren',0,NULL,1,'2023-01-11 15:00:00'),(1400,1,'testTask','renren',0,NULL,0,'2023-01-11 15:30:00'),(1401,1,'testTask','renren',0,NULL,0,'2023-01-11 16:00:00'),(1402,1,'testTask','renren',0,NULL,1,'2023-01-11 16:30:00'),(1403,1,'testTask','renren',0,NULL,1,'2023-01-11 17:00:00'),(1404,1,'testTask','renren',0,NULL,0,'2023-01-11 17:30:00'),(1405,1,'testTask','renren',0,NULL,0,'2023-01-11 18:00:00'),(1406,1,'testTask','renren',0,NULL,0,'2023-01-11 18:30:00'),(1407,1,'testTask','renren',0,NULL,0,'2023-01-11 19:00:00');
/*!40000 ALTER TABLE `schedule_job_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_captcha`
--

DROP TABLE IF EXISTS `sys_captcha`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_captcha` (
  `uuid` char(36) NOT NULL COMMENT 'uuid',
  `code` varchar(6) NOT NULL COMMENT '验证码',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统验证码';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_captcha`
--

LOCK TABLES `sys_captcha` WRITE;
/*!40000 ALTER TABLE `sys_captcha` DISABLE KEYS */;
INSERT INTO `sys_captcha` VALUES ('05c69d0b-1ba0-41f0-8944-475ab4274986','fmb86','2020-03-07 20:47:16'),('071ccd85-732c-4812-8e9d-7c29faf8a01f','f8ge3','2019-09-30 11:38:08'),('08fa63a8-9d73-4d4e-8717-b5b89795ede5','b2cwy','2020-03-07 20:51:57'),('123eeb2b-9032-4154-87fc-ffcaadf552b6','7e33g','2019-12-19 09:57:22'),('144b1d72-d40b-480e-8533-eca4c69888c8','dfene','2020-03-07 20:52:14'),('180c1579-0cdc-4caf-8c1a-0a741680cc0b','355nd','2019-10-29 11:30:28'),('20bfa304-b049-4850-8873-91bec5406416','787nn','2019-10-29 10:23:53'),('22439312-a5d8-4af4-84bf-03c54022c453','dpnpb','2019-10-29 11:30:26'),('27a9ab10-263c-4018-8e8a-b29910250f67','4y32y','2019-09-30 11:38:00'),('2800b4e3-2911-4a56-8841-91e1c786162e','nan5c','2020-02-22 11:45:23'),('290d13bf-52a0-4ef0-8c4d-6bacd089d743','bcc5e','2022-09-21 17:29:34'),('2c4ad84d-73b0-4ad3-83d2-238f40657a49','dmyp6','2019-09-30 11:38:13'),('38bf7980-41dc-478c-8bec-51476ee55d8b','fnda8','2019-10-28 23:33:57'),('40520a5f-5516-4b35-83e7-897901d0819c','cw2mf','2019-10-29 10:22:31'),('45c192b3-bb8d-4fe0-8903-1b5e0e2876ed','w53ym','2019-10-29 10:18:19'),('4fffb991-9d5c-4af3-8033-bbdb70c69976','app6d','2020-03-04 09:19:01'),('57c3a23b-3931-4cec-89fe-695c03d661e0','865y4','2020-02-21 15:39:45'),('5a0ea466-40b1-426f-8c95-406c372efafd','m2gpx','2019-10-29 12:10:55'),('6097823b-7a6d-4b9a-8aff-2f32f1202ea0','4adp3','2019-10-29 10:23:57'),('63314ba2-a03b-4118-82e7-da54f00213d9','b567x','2019-10-29 10:46:24'),('678118fa-1a9a-4bc4-8cab-d568070542e9','3ey5a','2019-10-28 23:16:36'),('6f664f22-22cb-4ede-8799-4b8cebfaa258','bp5mg','2019-10-28 23:18:39'),('6f82a9f4-221b-4486-848e-3a83f00252c7','pamw5','2020-02-22 11:45:20'),('7123ebc0-3ec3-4c73-8461-f8a240ab9858','72f5y','2019-10-29 10:56:10'),('72846ba8-afff-4f74-891e-de56255c5d37','a42an','2019-10-29 10:22:35'),('733e465e-ff41-4d8d-85a0-7265b34b711e','8pbeg','2020-03-07 20:51:43'),('7a611a55-b45f-43c4-8632-60d78055ddd8','n8fcg','2019-10-28 23:26:12'),('8a8482fa-0bab-4d8d-83f2-00050f58ddf3','6c22w','2019-10-29 10:21:59'),('8abf953f-bb4e-4a25-8515-468e58ee6554','pnp3n','2020-02-22 12:01:29'),('8fcff20f-d518-43e1-84a8-6f859cd76d3c','wxx2n','2020-03-07 21:27:14'),('96c9d01e-7cf9-4ecb-8817-a5c6030c5c8f','nxb4m','2019-10-28 23:22:26'),('a040ba17-6641-4e71-8052-8a75881e5280','8ney4','2022-09-30 17:31:07'),('a40f0707-8853-4944-8895-15276df7f4a1','mmdan','2020-03-07 20:51:28'),('a6dd10cb-3db3-4ac0-8d72-7c1244681631','cx6a2','2019-10-28 23:12:59'),('a801ecf8-1602-47e0-8a07-436b0136cc89','yygyx','2020-03-07 20:45:43'),('a94444c3-6cb0-4662-8ca6-47e7bb3c99c6','w72aa','2019-10-28 23:11:06'),('aad10794-43b6-4546-849d-d401848dd972','pnmmg','2019-09-30 11:38:14'),('af32a584-dfea-433d-8275-633d19a85b7e','ebf65','2020-03-07 20:53:10'),('bc7e0494-4f9e-4262-8b1d-e87580ae8834','e5cnp','2020-02-21 15:39:23'),('c624ad95-1349-495b-85aa-41a7b11fc9ba','fg62b','2019-10-28 21:37:30'),('c868dc0a-d6b9-45aa-8081-771ab87807ea','m3na3','2020-03-07 20:43:03'),('cd46d32b-172c-48d9-8db2-861246571e2f','6wxcf','2019-10-29 10:21:55'),('d155122f-d753-4bca-8977-f38ce6cecdb2','edgp7','2019-10-29 10:22:24'),('d7a93a64-a420-40be-8b8e-c449b53d8862','ddnew','2019-10-29 10:52:53'),('df31e84c-420d-44a2-8093-ce53c801235c','cpd5n','2019-10-29 10:19:32'),('e12e08fa-dce7-41dc-8d12-36d3d2f14113','np8p8','2022-12-29 10:11:40'),('e410a7c2-3409-4fde-8217-d824035d0f1b','cn2bg','2019-10-29 12:10:55'),('eb756ff9-5300-4969-885b-4248e58e43c9','eecxf','2020-03-07 20:51:32'),('edcb5be1-97da-4b7d-8fd5-9861f4e2653d','nfad5','2019-10-28 23:15:07'),('f6992138-55ce-4d90-8876-a9fd99287f88','d8xpp','2019-11-04 21:52:50'),('fcdc0016-ac09-4a4b-8c4c-376c1ca8c801','mw85n','2019-10-29 11:30:26'),('fee96fac-a4b8-46ab-89bb-93d3673163e8','2b4nd','2020-02-21 21:46:43');
/*!40000 ALTER TABLE `sys_captcha` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_config`
--

DROP TABLE IF EXISTS `sys_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) DEFAULT NULL COMMENT 'key',
  `param_value` varchar(2000) DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `param_key` (`param_key`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='系统配置信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_config`
--

LOCK TABLES `sys_config` WRITE;
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;
INSERT INTO `sys_config` VALUES (1,'CLOUD_STORAGE_CONFIG_KEY','{\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"aliyunDomain\":\"\",\"aliyunEndPoint\":\"\",\"aliyunPrefix\":\"\",\"qcloudBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qiniuAccessKey\":\"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ\",\"qiniuBucketName\":\"ios-app\",\"qiniuDomain\":\"http://7xqbwh.dl1.z0.glb.clouddn.com\",\"qiniuPrefix\":\"upload\",\"qiniuSecretKey\":\"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV\",\"type\":1}',0,'云存储配置信息');
/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_log`
--

DROP TABLE IF EXISTS `sys_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COMMENT='系统日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_log`
--

LOCK TABLES `sys_log` WRITE;
/*!40000 ALTER TABLE `sys_log` DISABLE KEYS */;
INSERT INTO `sys_log` VALUES (1,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":31,\"parentId\":0,\"name\":\"商品系统\",\"url\":\"\",\"perms\":\"\",\"type\":0,\"icon\":\"editor\",\"orderNum\":0}]',9,'0:0:0:0:0:0:0:1','2019-10-28 21:34:21'),(2,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":32,\"parentId\":31,\"name\":\"分类维护\",\"url\":\"product/category\",\"perms\":\"\",\"type\":1,\"icon\":\"menu\",\"orderNum\":0}]',8,'0:0:0:0:0:0:0:1','2019-10-28 21:35:21'),(3,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":33,\"parentId\":31,\"name\":\"品牌管理\",\"url\":\"product/brand\",\"perms\":\"\",\"type\":1,\"icon\":\"editor\",\"orderNum\":0}]',17,'0:0:0:0:0:0:0:1','2019-11-06 10:19:56'),(4,'admin','删除菜单','io.renren.modules.sys.controller.SysMenuController.delete()','[33]',322,'0:0:0:0:0:0:0:1','2019-11-06 10:40:06'),(5,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":34,\"parentId\":31,\"name\":\"品牌管理\",\"url\":\"product/brand\",\"perms\":\"\",\"type\":1,\"icon\":\"editor\",\"orderNum\":0}]',8,'0:0:0:0:0:0:0:1','2019-11-06 10:52:28'),(6,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":35,\"parentId\":31,\"name\":\"属性维护\",\"url\":\"\",\"perms\":\"\",\"type\":0,\"icon\":\"system\",\"orderNum\":0}]',11,'0:0:0:0:0:0:0:1','2019-11-13 11:59:27'),(7,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":36,\"parentId\":35,\"name\":\"基本属性\",\"url\":\"product/baseatr\",\"perms\":\"\",\"type\":1,\"icon\":\"\",\"orderNum\":0}]',8,'0:0:0:0:0:0:0:1','2019-11-13 11:59:56'),(8,'admin','删除菜单','io.renren.modules.sys.controller.SysMenuController.delete()','[35]',4,'0:0:0:0:0:0:0:1','2019-11-13 12:08:23'),(9,'admin','删除菜单','io.renren.modules.sys.controller.SysMenuController.delete()','[36]',311,'0:0:0:0:0:0:0:1','2019-11-13 12:08:28'),(10,'admin','删除菜单','io.renren.modules.sys.controller.SysMenuController.delete()','[35]',11,'0:0:0:0:0:0:0:1','2019-11-13 12:08:34'),(11,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":37,\"parentId\":31,\"name\":\"平台属性\",\"url\":\"\",\"perms\":\"\",\"type\":0,\"icon\":\"system\",\"orderNum\":0}]',11,'0:0:0:0:0:0:0:1','2019-11-13 20:05:22'),(12,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":38,\"parentId\":37,\"name\":\"属性分组\",\"url\":\"product/attrgroup\",\"perms\":\"\",\"type\":1,\"icon\":\"tubiao\",\"orderNum\":0}]',7,'0:0:0:0:0:0:0:1','2019-11-13 20:06:12'),(13,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":39,\"parentId\":37,\"name\":\"规格参数\",\"url\":\"product/baseattr\",\"perms\":\"\",\"type\":1,\"icon\":\"log\",\"orderNum\":0}]',6,'0:0:0:0:0:0:0:1','2019-11-13 20:07:29'),(14,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":40,\"parentId\":37,\"name\":\"销售属性\",\"url\":\"product/saleattr\",\"perms\":\"\",\"type\":1,\"icon\":\"zonghe\",\"orderNum\":0}]',8,'0:0:0:0:0:0:0:1','2019-11-13 20:08:00'),(15,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":41,\"parentId\":31,\"name\":\"商品维护\",\"url\":\"product/spu\",\"perms\":\"\",\"type\":1,\"icon\":\"zonghe\",\"orderNum\":0}]',7,'0:0:0:0:0:0:0:1','2019-11-13 20:13:12'),(16,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":42,\"parentId\":0,\"name\":\"优惠营销\",\"url\":\"\",\"perms\":\"\",\"type\":0,\"icon\":\"mudedi\",\"orderNum\":0}]',5,'0:0:0:0:0:0:0:1','2019-11-13 20:14:52'),(17,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":43,\"parentId\":0,\"name\":\"库存系统\",\"url\":\"\",\"perms\":\"\",\"type\":0,\"icon\":\"shouye\",\"orderNum\":0}]',7,'0:0:0:0:0:0:0:1','2019-11-13 20:15:20'),(18,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":44,\"parentId\":0,\"name\":\"订单系统\",\"url\":\"\",\"perms\":\"\",\"type\":0,\"icon\":\"config\",\"orderNum\":0}]',5,'0:0:0:0:0:0:0:1','2019-11-13 20:15:48'),(19,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":45,\"parentId\":0,\"name\":\"用户系统\",\"url\":\"\",\"perms\":\"\",\"type\":0,\"icon\":\"admin\",\"orderNum\":0}]',5,'0:0:0:0:0:0:0:1','2019-11-13 20:16:12'),(20,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":46,\"parentId\":0,\"name\":\"内容管理\",\"url\":\"\",\"perms\":\"\",\"type\":0,\"icon\":\"sousuo\",\"orderNum\":0}]',3,'0:0:0:0:0:0:0:1','2019-11-13 20:16:56'),(21,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":47,\"parentId\":42,\"name\":\"优惠券管理\",\"url\":\"coupon/coupon\",\"perms\":\"\",\"type\":1,\"icon\":\"zhedie\",\"orderNum\":0}]',7,'0:0:0:0:0:0:0:1','2019-11-13 20:19:59'),(22,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":48,\"parentId\":42,\"name\":\"发放记录\",\"url\":\"coupon/history\",\"perms\":\"\",\"type\":1,\"icon\":\"sql\",\"orderNum\":0}]',15,'0:0:0:0:0:0:0:1','2019-11-13 20:20:52'),(23,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":49,\"parentId\":42,\"name\":\"专题活动\",\"url\":\"coupon/subject\",\"perms\":\"\",\"type\":1,\"icon\":\"tixing\",\"orderNum\":0}]',7,'0:0:0:0:0:0:0:1','2019-11-13 20:21:58'),(24,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":50,\"parentId\":42,\"name\":\"秒杀活动\",\"url\":\"coupon/seckill\",\"perms\":\"\",\"type\":1,\"icon\":\"daohang\",\"orderNum\":0}]',7,'0:0:0:0:0:0:0:1','2019-11-13 20:22:32'),(25,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":51,\"parentId\":42,\"name\":\"积分维护\",\"url\":\"coupon/bounds\",\"perms\":\"\",\"type\":1,\"icon\":\"geren\",\"orderNum\":0}]',5,'0:0:0:0:0:0:0:1','2019-11-13 20:25:13'),(26,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":52,\"parentId\":42,\"name\":\"满减折扣\",\"url\":\"coupon/full\",\"perms\":\"\",\"type\":1,\"icon\":\"shoucang\",\"orderNum\":0}]',9,'0:0:0:0:0:0:0:1','2019-11-13 20:26:21'),(27,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":53,\"parentId\":43,\"name\":\"仓库维护\",\"url\":\"ware/wareinfo\",\"perms\":\"\",\"type\":1,\"icon\":\"shouye\",\"orderNum\":0}]',5,'0:0:0:0:0:0:0:1','2019-11-13 20:27:51'),(28,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":54,\"parentId\":43,\"name\":\"库存工作单\",\"url\":\"ware/task\",\"perms\":\"\",\"type\":1,\"icon\":\"log\",\"orderNum\":0}]',14,'0:0:0:0:0:0:0:1','2019-11-13 20:28:55'),(29,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":55,\"parentId\":43,\"name\":\"商品库存\",\"url\":\"ware/sku\",\"perms\":\"\",\"type\":1,\"icon\":\"jiesuo\",\"orderNum\":0}]',5,'0:0:0:0:0:0:0:1','2019-11-13 20:29:31'),(30,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":56,\"parentId\":44,\"name\":\"订单查询\",\"url\":\"order/order\",\"perms\":\"\",\"type\":1,\"icon\":\"zhedie\",\"orderNum\":0}]',6,'0:0:0:0:0:0:0:1','2019-11-13 20:31:55'),(31,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":57,\"parentId\":44,\"name\":\"退货单处理\",\"url\":\"order/return\",\"perms\":\"\",\"type\":1,\"icon\":\"shanchu\",\"orderNum\":0}]',5,'0:0:0:0:0:0:0:1','2019-11-13 20:33:04'),(32,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":58,\"parentId\":44,\"name\":\"等级规则\",\"url\":\"order/settings\",\"perms\":\"\",\"type\":1,\"icon\":\"system\",\"orderNum\":0}]',5,'0:0:0:0:0:0:0:1','2019-11-13 20:34:34'),(33,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":59,\"parentId\":44,\"name\":\"支付流水查询\",\"url\":\"order/payment\",\"perms\":\"\",\"type\":1,\"icon\":\"job\",\"orderNum\":0}]',5,'0:0:0:0:0:0:0:1','2019-11-13 20:37:41'),(34,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":60,\"parentId\":44,\"name\":\"退款流水查询\",\"url\":\"order/refund\",\"perms\":\"\",\"type\":1,\"icon\":\"mudedi\",\"orderNum\":0}]',6,'0:0:0:0:0:0:0:1','2019-11-13 20:38:16'),(35,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":61,\"parentId\":45,\"name\":\"会员列表\",\"url\":\"member/member\",\"perms\":\"\",\"type\":1,\"icon\":\"geren\",\"orderNum\":0}]',7,'0:0:0:0:0:0:0:1','2019-11-13 20:40:14'),(36,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":62,\"parentId\":45,\"name\":\"会员等级\",\"url\":\"member/level\",\"perms\":\"\",\"type\":1,\"icon\":\"tubiao\",\"orderNum\":0}]',14,'0:0:0:0:0:0:0:1','2019-11-13 20:40:34'),(37,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":63,\"parentId\":45,\"name\":\"积分变化\",\"url\":\"member/growth\",\"perms\":\"\",\"type\":1,\"icon\":\"bianji\",\"orderNum\":0}]',6,'0:0:0:0:0:0:0:1','2019-11-13 20:43:14'),(38,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":64,\"parentId\":45,\"name\":\"统计信息\",\"url\":\"member/statistics\",\"perms\":\"\",\"type\":1,\"icon\":\"sql\",\"orderNum\":0}]',4,'0:0:0:0:0:0:0:1','2019-11-13 20:43:52'),(39,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":65,\"parentId\":46,\"name\":\"首页推荐\",\"url\":\"content/index\",\"perms\":\"\",\"type\":1,\"icon\":\"shouye\",\"orderNum\":0}]',6,'0:0:0:0:0:0:0:1','2019-11-13 20:44:47'),(40,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":66,\"parentId\":46,\"name\":\"分类热门\",\"url\":\"content/category\",\"perms\":\"\",\"type\":1,\"icon\":\"zhedie\",\"orderNum\":0}]',7,'0:0:0:0:0:0:0:1','2019-11-13 20:45:24'),(41,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":67,\"parentId\":46,\"name\":\"评论管理\",\"url\":\"content/comments\",\"perms\":\"\",\"type\":1,\"icon\":\"pinglun\",\"orderNum\":0}]',20,'0:0:0:0:0:0:0:1','2019-11-13 20:48:21'),(42,'admin','修改菜单','io.renren.modules.sys.controller.SysMenuController.update()','[{\"menuId\":41,\"parentId\":31,\"name\":\"商品维护\",\"url\":\"product/spu\",\"perms\":\"\",\"type\":0,\"icon\":\"zonghe\",\"orderNum\":0}]',11,'0:0:0:0:0:0:0:1','2019-11-17 12:18:52'),(43,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":68,\"parentId\":41,\"name\":\"spu管理\",\"url\":\"product/spu\",\"perms\":\"\",\"type\":1,\"icon\":\"config\",\"orderNum\":0}]',9,'0:0:0:0:0:0:0:1','2019-11-17 12:19:52'),(44,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":69,\"parentId\":41,\"name\":\"发布商品\",\"url\":\"product/spuadd\",\"perms\":\"\",\"type\":1,\"icon\":\"bianji\",\"orderNum\":0}]',14,'0:0:0:0:0:0:0:1','2019-11-17 12:49:04'),(45,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":70,\"parentId\":43,\"name\":\"采购单维护\",\"url\":\"\",\"perms\":\"\",\"type\":0,\"icon\":\"tubiao\",\"orderNum\":0}]',12,'0:0:0:0:0:0:0:1','2019-11-17 13:29:35'),(46,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":71,\"parentId\":70,\"name\":\"发布采购项\",\"url\":\"ware/purchaseitem\",\"perms\":\"\",\"type\":1,\"icon\":\"editor\",\"orderNum\":0}]',7,'0:0:0:0:0:0:0:1','2019-11-17 13:30:23'),(47,'admin','修改菜单','io.renren.modules.sys.controller.SysMenuController.update()','[{\"menuId\":71,\"parentId\":70,\"name\":\"采购需求\",\"url\":\"ware/purchaseitem\",\"perms\":\"\",\"type\":1,\"icon\":\"editor\",\"orderNum\":0}]',6,'0:0:0:0:0:0:0:1','2019-11-17 13:30:57'),(48,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":72,\"parentId\":70,\"name\":\"采购单\",\"url\":\"ware/purchase\",\"perms\":\"\",\"type\":1,\"icon\":\"menu\",\"orderNum\":0}]',5,'0:0:0:0:0:0:0:1','2019-11-17 13:31:32'),(49,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":73,\"parentId\":41,\"name\":\"商品管理\",\"url\":\"product/manager\",\"perms\":\"\",\"type\":1,\"icon\":\"zonghe\",\"orderNum\":0}]',8,'0:0:0:0:0:0:0:1','2019-11-17 13:36:18'),(50,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":74,\"parentId\":42,\"name\":\"会员价格\",\"url\":\"coupon/memberprice\",\"perms\":\"\",\"type\":1,\"icon\":\"\",\"orderNum\":0}]',11,'0:0:0:0:0:0:0:1','2019-11-24 16:23:33'),(51,'admin','修改菜单','io.renren.modules.sys.controller.SysMenuController.update()','[{\"menuId\":74,\"parentId\":42,\"name\":\"会员价格\",\"url\":\"coupon/memberprice\",\"perms\":\"\",\"type\":1,\"icon\":\"admin\",\"orderNum\":0}]',9,'0:0:0:0:0:0:0:1','2019-11-24 16:23:48'),(52,'admin','保存用户','io.renren.modules.sys.controller.SysUserController.save()','[{\"userId\":2,\"username\":\"leifengyang\",\"password\":\"ed1b7fbd834332e5395d8823be934478141c3285ddccae1c55b192306571b886\",\"salt\":\"M78W7WGGh2RD0QGKm86j\",\"email\":\"aaaa@qq.com\",\"mobile\":\"12345678912\",\"status\":1,\"roleIdList\":[],\"createUserId\":1,\"createTime\":\"Nov 29, 2019 9:46:09 AM\"}]',179,'0:0:0:0:0:0:0:1','2019-11-29 09:46:09'),(53,'admin','保存菜单','io.renren.modules.sys.controller.SysMenuController.save()','[{\"menuId\":75,\"parentId\":42,\"name\":\"每日秒杀\",\"url\":\"aaaaaaaa\",\"perms\":\"\",\"type\":1,\"icon\":\"job\",\"orderNum\":0}]',8,'0:0:0:0:0:0:0:1','2020-02-18 18:42:37'),(54,'admin','修改菜单','io.renren.modules.sys.controller.SysMenuController.update()','[{\"menuId\":75,\"parentId\":42,\"name\":\"每日秒杀\",\"url\":\"coupon/seckillsession\",\"perms\":\"\",\"type\":1,\"icon\":\"job\",\"orderNum\":0}]',11,'0:0:0:0:0:0:0:1','2020-02-18 18:43:10'),(55,'admin','保存用户','io.renren.modules.sys.controller.SysUserController.save()','[{\"userId\":3,\"username\":\"lqs\",\"password\":\"9e98e35b7d0f00af9f56ce86189857dd447afd0f04c39a5fb93050e65d29576f\",\"salt\":\"STX54IT9qYyaS5KfD897\",\"email\":\"749062870@qq.com\",\"mobile\":\"17398827615\",\"status\":1,\"roleIdList\":[],\"createUserId\":1,\"createTime\":\"Sep 28, 2022 7:47:59 PM\"}]',80,'0:0:0:0:0:0:0:1%0','2022-09-28 19:47:59'),(56,'admin','保存用户','io.renren.modules.sys.controller.SysUserController.save()','[{\"userId\":4,\"username\":\"liqisong\",\"password\":\"bf10274fcc305be2feecd55b03a00a45a8895b7f22ca0aac1654076fc1c750bf\",\"salt\":\"vpGgJrpP5c5lt1WmgLGS\",\"email\":\"749062870@qq.com\",\"mobile\":\"17398827615\",\"status\":1,\"roleIdList\":[],\"createUserId\":1,\"createTime\":\"Oct 1, 2022 8:56:29 PM\"}]',88,'172.17.0.2','2022-10-01 20:56:30');
/*!40000 ALTER TABLE `sys_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb4 COMMENT='菜单管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,0,'系统管理',NULL,NULL,0,'system',0),(2,1,'管理员列表','sys/user',NULL,1,'admin',1),(3,1,'角色管理','sys/role',NULL,1,'role',2),(4,1,'菜单管理','sys/menu',NULL,1,'menu',3),(5,1,'SQL监控','http://localhost:8080/renren-fast/druid/sql.html',NULL,1,'sql',4),(6,1,'定时任务','job/schedule',NULL,1,'job',5),(7,6,'查看',NULL,'sys:schedule:list,sys:schedule:info',2,NULL,0),(8,6,'新增',NULL,'sys:schedule:save',2,NULL,0),(9,6,'修改',NULL,'sys:schedule:update',2,NULL,0),(10,6,'删除',NULL,'sys:schedule:delete',2,NULL,0),(11,6,'暂停',NULL,'sys:schedule:pause',2,NULL,0),(12,6,'恢复',NULL,'sys:schedule:resume',2,NULL,0),(13,6,'立即执行',NULL,'sys:schedule:run',2,NULL,0),(14,6,'日志列表',NULL,'sys:schedule:log',2,NULL,0),(15,2,'查看',NULL,'sys:user:list,sys:user:info',2,NULL,0),(16,2,'新增',NULL,'sys:user:save,sys:role:select',2,NULL,0),(17,2,'修改',NULL,'sys:user:update,sys:role:select',2,NULL,0),(18,2,'删除',NULL,'sys:user:delete',2,NULL,0),(19,3,'查看',NULL,'sys:role:list,sys:role:info',2,NULL,0),(20,3,'新增',NULL,'sys:role:save,sys:menu:list',2,NULL,0),(21,3,'修改',NULL,'sys:role:update,sys:menu:list',2,NULL,0),(22,3,'删除',NULL,'sys:role:delete',2,NULL,0),(23,4,'查看',NULL,'sys:menu:list,sys:menu:info',2,NULL,0),(24,4,'新增',NULL,'sys:menu:save,sys:menu:select',2,NULL,0),(25,4,'修改',NULL,'sys:menu:update,sys:menu:select',2,NULL,0),(26,4,'删除',NULL,'sys:menu:delete',2,NULL,0),(27,1,'参数管理','sys/config','sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete',1,'config',6),(29,1,'系统日志','sys/log','sys:log:list',1,'log',7),(30,1,'文件上传','oss/oss','sys:oss:all',1,'oss',6),(31,0,'商品系统','','',0,'editor',0),(32,31,'分类维护','product/category','',1,'menu',0),(34,31,'品牌管理','product/brand','',1,'editor',0),(37,31,'平台属性','','',0,'system',0),(38,37,'属性分组','product/attrgroup','',1,'tubiao',0),(39,37,'规格参数','product/baseattr','',1,'log',0),(40,37,'销售属性','product/saleattr','',1,'zonghe',0),(41,31,'商品维护','product/spu','',0,'zonghe',0),(42,0,'优惠营销','','',0,'mudedi',0),(43,0,'库存系统','','',0,'shouye',0),(44,0,'订单系统','','',0,'config',0),(45,0,'用户系统','','',0,'admin',0),(46,0,'内容管理','','',0,'sousuo',0),(47,42,'优惠券管理','coupon/coupon','',1,'zhedie',0),(48,42,'发放记录','coupon/history','',1,'sql',0),(49,42,'专题活动','coupon/subject','',1,'tixing',0),(50,42,'秒杀活动','coupon/seckill','',1,'daohang',0),(51,42,'积分维护','coupon/bounds','',1,'geren',0),(52,42,'满减折扣','coupon/full','',1,'shoucang',0),(53,43,'仓库维护','ware/wareinfo','',1,'shouye',0),(54,43,'库存工作单','ware/task','',1,'log',0),(55,43,'商品库存','ware/sku','',1,'jiesuo',0),(56,44,'订单查询','order/order','',1,'zhedie',0),(57,44,'退货单处理','order/return','',1,'shanchu',0),(58,44,'等级规则','order/settings','',1,'system',0),(59,44,'支付流水查询','order/payment','',1,'job',0),(60,44,'退款流水查询','order/refund','',1,'mudedi',0),(61,45,'会员列表','member/member','',1,'geren',0),(62,45,'会员等级','member/level','',1,'tubiao',0),(63,45,'积分变化','member/growth','',1,'bianji',0),(64,45,'统计信息','member/statistics','',1,'sql',0),(65,46,'首页推荐','content/index','',1,'shouye',0),(66,46,'分类热门','content/category','',1,'zhedie',0),(67,46,'评论管理','content/comments','',1,'pinglun',0),(68,41,'spu管理','product/spu','',1,'config',0),(69,41,'发布商品','product/spuadd','',1,'bianji',0),(70,43,'采购单维护','','',0,'tubiao',0),(71,70,'采购需求','ware/purchaseitem','',1,'editor',0),(72,70,'采购单','ware/purchase','',1,'menu',0),(73,41,'商品管理','product/manager','',1,'zonghe',0),(74,42,'会员价格','coupon/memberprice','',1,'admin',0),(75,42,'每日秒杀','coupon/seckillsession','',1,'job',0);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_oss`
--

DROP TABLE IF EXISTS `sys_oss`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_oss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件上传';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_oss`
--

LOCK TABLES `sys_oss` WRITE;
/*!40000 ALTER TABLE `sys_oss` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_oss` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色与菜单对应关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) DEFAULT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'admin','9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d','YzcmCZNvbXocrsz9dm8e','root@renren.io','13612345678',1,1,'2016-11-11 11:11:11'),(2,'leifengyang','ed1b7fbd834332e5395d8823be934478141c3285ddccae1c55b192306571b886','M78W7WGGh2RD0QGKm86j','aaaa@qq.com','12345678912',1,1,'2019-11-29 09:46:09'),(3,'lqs','9e98e35b7d0f00af9f56ce86189857dd447afd0f04c39a5fb93050e65d29576f','STX54IT9qYyaS5KfD897','749062870@qq.com','17398827615',1,1,'2022-09-28 19:47:59'),(4,'liqisong','bf10274fcc305be2feecd55b03a00a45a8895b7f22ca0aac1654076fc1c750bf','vpGgJrpP5c5lt1WmgLGS','749062870@qq.com','17398827615',1,1,'2022-10-01 20:56:30');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户与角色对应关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_token`
--

DROP TABLE IF EXISTS `sys_user_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_token` (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) NOT NULL COMMENT 'token',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户Token';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_token`
--

LOCK TABLES `sys_user_token` WRITE;
/*!40000 ALTER TABLE `sys_user_token` DISABLE KEYS */;
INSERT INTO `sys_user_token` VALUES (1,'7c1e850812f652c63434f5088539c91f','2022-12-29 22:11:50','2022-12-29 10:11:50');
/*!40000 ALTER TABLE `sys_user_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user`
--

DROP TABLE IF EXISTS `tb_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `mobile` varchar(20) NOT NULL COMMENT '手机号',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user`
--

LOCK TABLES `tb_user` WRITE;
/*!40000 ALTER TABLE `tb_user` DISABLE KEYS */;
INSERT INTO `tb_user` VALUES (1,'mark','13612345678','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','2017-03-23 22:37:41');
/*!40000 ALTER TABLE `tb_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `undo_log`
--

DROP TABLE IF EXISTS `undo_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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

-- Dump completed on 2023-01-13  8:00:37
