-- 创建数据库和用户
CREATE DATABASE IF NOT EXISTS `ad-data` DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci;

CREATE USER 'ad-user'@'%' IDENTIFIED BY 'adPassword123';
GRANT ALL PRIVILEGES ON `ad-data`.* TO 'ad-user'@'%';
FLUSH PRIVILEGES;

--

use `ad-data`;

-- 用户表
CREATE TABLE `ad_user`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `username`    varchar(128) NOT NULL DEFAULT '' COMMENT '用户名',
    `token`       varchar(256) NOT NULL DEFAULT '' COMMENT '给用户生成的 token',
    `user_status` tinyint(4)   NOT NULL DEFAULT '0' COMMENT '用户状态',
    `create_time` datetime     NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
    `update_time` datetime     NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `username` (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户信息表';


-- 推广计划表
CREATE TABLE `ad_plan`
(
    `id`          bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `user_id`     bigint(20)  NOT NULL DEFAULT '0' COMMENT '标记当前记录所属用户',
    `plan_name`   varchar(48) NOT NULL COMMENT '推广计划名称',
    `plan_status` tinyint(4)  NOT NULL DEFAULT '0' COMMENT '推广计划状态',
    `start_date`  datetime    NOT NULL COMMENT '推广计划开始时间；',
    `end_date`    datetime    NOT NULL COMMENT '推广计划结束时间；',
    `create_time` datetime    NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
    `update_time` datetime    NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='推广计划表';


-- 推广单元表
CREATE TABLE `ad_unit`
(
    `id`            bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `plan_id`       bigint(20)  NOT NULL DEFAULT '0' COMMENT '关联推广计划 id',
    `unit_name`     varchar(48) NOT NULL COMMENT '推广单元名称',
    `unit_status`   tinyint(4)  NOT NULL DEFAULT '0' COMMENT '推广单元状态',
    `position_type` tinyint(4)  NOT NULL DEFAULT '0' COMMENT '广告位类型(开屏, 贴片, 中贴, 暂停帖, 后贴)',
    `budget`        bigint(20)  NOT NULL COMMENT '预算',
    `create_time`   datetime    NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
    `update_time`   datetime    NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='推广单元表';


-- 创意表
CREATE TABLE `ad_creative`
(
    `id`            bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `name`          varchar(48)  NOT NULL COMMENT '创意名称',
    `type`          tinyint(4)   NOT NULL DEFAULT '0' COMMENT '物料类型(图片, 视频)',
    `material_type` tinyint(4)   NOT NULL DEFAULT '0' COMMENT '物料子类型(图片: bmp, jpg 等等)',
    `height`        int(10)      NOT NULL DEFAULT '0' COMMENT '高度',
    `width`         int(10)      NOT NULL DEFAULT '0' COMMENT '宽度',
    `size`          bigint(20)   NOT NULL DEFAULT '0' COMMENT '物料大小, 单位是 KB',
    `duration`      int(10)      NOT NULL DEFAULT '0' COMMENT '持续时长, 只有视频才不为 0',
    `audit_status`  tinyint(4)   NOT NULL DEFAULT '0' COMMENT '审核状态',
    `user_id`       bigint(20)   NOT NULL DEFAULT '0' COMMENT '标记当前记录所属用户',
    `url`           varchar(256) NOT NULL COMMENT '物料地址',
    `create_time`   datetime     NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
    `update_time`   datetime     NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='创意表';


-- 创意与推广单元的关联表
CREATE TABLE `creative_unit`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `creative_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创意 id',
    `unit_id`     bigint(20) NOT NULL DEFAULT '0' COMMENT '推广单元 id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='创意和推广单元关联表';


-- 推广单元关键词 Feature
CREATE TABLE `ad_unit_keyword`
(
    `id`      int(11)     NOT NULL AUTO_INCREMENT,
    `unit_id` int(11)     NOT NULL COMMENT '推广单元 id',
    `keyword` varchar(30) NOT NULL COMMENT '关键词',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='推广单元关键词 Feature';


-- 推广单元兴趣 Feature
CREATE TABLE `ad_unit_it`
(
    `id`      int(11)     NOT NULL AUTO_INCREMENT,
    `unit_id` int(11)     NOT NULL COMMENT '推广单元 id',
    `it_tag`  varchar(30) NOT NULL COMMENT '兴趣标签',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='推广单元兴趣 Feature';


-- 推广单元地域 Feature
CREATE TABLE `ad_unit_district`
(
    `id`       int(11)     NOT NULL AUTO_INCREMENT,
    `unit_id`  int(11)     NOT NULL COMMENT '推广单元 id',
    `province` varchar(30) NOT NULL COMMENT '省',
    `city`     varchar(30) NOT NULL COMMENT '市',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='推广单元地域 Feature';


--  插入数据

INSERT INTO `ad_user`
VALUES (15, 'qinyi', 'B2E56F2420D73FEC125D2D51641C5713', 1, '2018-11-19 20:29:01', '2018-11-19 20:29:01');
INSERT INTO `ad_creative`
VALUES (10, '第一个创意', 1, 1, 720, 1080, 1024, 0, 1, 15, 'https://www.imooc.com', '2018-11-19 21:31:31',
        '2018-11-19 21:31:31');
INSERT INTO `ad_plan`
VALUES (10, 15, '推广计划名称', 1, '2018-11-28 00:00:00', '2019-11-20 00:00:00', '2018-11-19 20:42:27',
        '2018-11-19 20:57:12');
INSERT INTO `ad_unit`
VALUES (10, 10, '第一个推广单元', 1, 1, 10000000, '2018-11-20 11:43:26', '2018-11-20 11:43:26'),
       (12, 10, '第二个推广单元', 1, 1, 15000000, '2018-01-01 00:00:00', '2018-01-01 00:00:00');
INSERT INTO `ad_unit_district`
VALUES (10, 10, '安徽省', '淮北市'),
       (11, 10, '安徽省', '宿州市'),
       (12, 10, '安徽省', '合肥市'),
       (14, 10, '辽宁省', '大连市');
INSERT INTO `ad_unit_it`
VALUES (10, 10, '台球'),
       (11, 10, '游泳'),
       (12, 10, '乒乓球');
INSERT INTO `ad_unit_keyword`
VALUES (10, 10, '宝马'),
       (11, 10, '奥迪'),
       (12, 10, '大众');
INSERT INTO `creative_unit`
VALUES (10, 10, 10);
