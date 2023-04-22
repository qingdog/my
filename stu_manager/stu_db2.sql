/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : stu_db2

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 25/08/2020 18:01:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `cardId` int(10) NOT NULL,
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `balance` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `manager1` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `manager2` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `manager3` bigint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`cardId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (1, 'zs', '10000', NULL, NULL, NULL);
INSERT INTO `account` VALUES (2, 'ls', '10000', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(11) NOT NULL,
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'jack', '123');
INSERT INTO `admin` VALUES (2, 'tom', '456');

-- ----------------------------
-- Table structure for classes
-- ----------------------------
DROP TABLE IF EXISTS `classes`;
CREATE TABLE `classes`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of classes
-- ----------------------------
INSERT INTO `classes` VALUES (1, 'java1班');
INSERT INTO `classes` VALUES (2, 'java2班');
INSERT INTO `classes` VALUES (3, 'java3班');
INSERT INTO `classes` VALUES (4, 'java4班');
INSERT INTO `classes` VALUES (5, '.net班');
INSERT INTO `classes` VALUES (6, '测试班');
INSERT INTO `classes` VALUES (7, '大数据班');
INSERT INTO `classes` VALUES (8, 'IOS班');
INSERT INTO `classes` VALUES (9, '嵌入式班');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `age` int(11) NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `photo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `classesId` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, '张三', 23, '西安', '1', 1);
INSERT INTO `student` VALUES (2, '李四', 24, '安徽合肥', NULL, 2);
INSERT INTO `student` VALUES (3, '王五', 25, '武汉', NULL, 3);
INSERT INTO `student` VALUES (14, '汤米', 14, '美国墨西哥', NULL, 4);
INSERT INTO `student` VALUES (15, '白纸', 15, '日本东京', NULL, 5);
INSERT INTO `student` VALUES (16, '杰克', 16, '美国纽约', '1587397166444', 6);
INSERT INTO `student` VALUES (17, '汤姆', 17, '美国英格兰', '', 7);
INSERT INTO `student` VALUES (21, '行医', 21, '医院', NULL, 1);
INSERT INTO `student` VALUES (25, '王小强', 25, '美国洛杉矶', NULL, 5);
INSERT INTO `student` VALUES (44, '小诗', 44, '洛圣都', '1588053178107', 4);
INSERT INTO `student` VALUES (45, '周明明', 48, '安徽淮南', NULL, 8);
INSERT INTO `student` VALUES (49, '米酒', 49, '圣安地列斯大学', '1594457356378', 9);
INSERT INTO `student` VALUES (54, '545', 4, '45', '1595160666516', 1);
INSERT INTO `student` VALUES (55, '55', 55, '55', NULL, 5);
INSERT INTO `student` VALUES (56, '56', 56, '56', NULL, 2);
INSERT INTO `student` VALUES (65, '6', 65, '56', NULL, 3);

-- ----------------------------
-- Procedure structure for proc_student
-- ----------------------------
DROP PROCEDURE IF EXISTS `proc_student`;
delimiter ;;
CREATE PROCEDURE `proc_student`()
BEGIN
	select * from student;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
