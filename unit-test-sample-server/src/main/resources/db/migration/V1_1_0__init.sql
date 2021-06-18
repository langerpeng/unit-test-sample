SET NAMES utf8mb4;

CREATE TABLE `wx_app_bind_relation`
(
    `id`             bigint   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `wx_app_from_id` int      NOT NULL DEFAULT '-1' COMMENT 'from',
    `wx_app_to_id`   int      NOT NULL DEFAULT '-1' COMMENT 'to',
    `create_user_id` bigint   NOT NULL DEFAULT '-1' COMMENT '用户id',
    `create_time`    datetime NOT NULL COMMENT '创建时间',
    `status`         tinyint  NOT NULL DEFAULT '1' COMMENT '0正常 -1已删除',
    PRIMARY KEY (`id`),
    KEY `idx_wx_app_from_id` (`wx_app_from_id`),
    KEY `idx_wx_app_to_id` (`wx_app_to_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
