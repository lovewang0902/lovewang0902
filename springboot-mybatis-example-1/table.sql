


-- 创建学生表

CREATE TABLE `student` (
  `id` int(11) NOT NULL DEFAULT '0' COMMENT 'id',
  `studentName` varchar(100) DEFAULT NULL COMMENT '学生姓名',
  `studentSex` varchar(10) DEFAULT NULL COMMENT '学生性别',
  `studentAge` int(100) DEFAULT NULL COMMENT '学生年龄',
  `studentAddress` varchar(100) DEFAULT NULL COMMENT '学生地址',
  `creatTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 插入数据
INSERT INTO `student` VALUES (1, 'xiaotutu', '男', 21, '深圳龙华区观澜新田', '2021-12-11 23:27:12');
INSERT INTO `student` VALUES (2, 'xiaotutu1', '女', 22, '深圳龙华区观澜新田1', '2021-12-11 23:27:13');
INSERT INTO `student` VALUES (3, 'xiaotutu2', '男', 23, '深圳龙华区观澜新田2', '2021-12-11 23:27:14');
INSERT INTO `student` VALUES (4, 'xiaotutu3', '女', 24, '深圳龙华区观澜新田3', '2021-12-11 23:27:15');
INSERT INTO `student` VALUES (5, 'xiaotutu4', '男', 25, '深圳龙华区观澜新田4', '2021-12-11 23:27:16');

-- 运单表
CREATE TABLE `ts_waybiilno` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `waybillNo` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '运单号',
  `count` int DEFAULT NULL COMMENT '数量',
  `waybillFormat` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '格式',
  `label` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '标签',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

