/*
 Navicat Premium Data Transfer

 Source Server         : mysql5.6
 Source Server Type    : MySQL
 Source Server Version : 50620
 Source Host           : localhost:3306
 Source Schema         : file_db

 Target Server Type    : MySQL
 Target Server Version : 50620
 File Encoding         : 65001

 Date: 15/10/2021 20:15:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for fileclass
-- ----------------------------
DROP TABLE IF EXISTS `fileclass`;
CREATE TABLE `fileclass`  (
  `classId` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `className` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名称',
  PRIMARY KEY (`classId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of fileclass
-- ----------------------------
INSERT INTO `fileclass` VALUES (1, '编程技术');
INSERT INTO `fileclass` VALUES (2, '学习课件');

-- ----------------------------
-- Table structure for fileinfo
-- ----------------------------
DROP TABLE IF EXISTS `fileinfo`;
CREATE TABLE `fileinfo`  (
  `fileId` int(11) NOT NULL AUTO_INCREMENT COMMENT '文档id',
  `fileClassObj` int(11) NOT NULL COMMENT '文档分类',
  `fileName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文档名称',
  `filePhoto` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文档图片',
  `fileDesc` varchar(800) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文档描述',
  `privateFlag` int(11) NOT NULL COMMENT '是否公开',
  `docFile` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文档文件',
  `userObj` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '上传用户',
  `upTime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`fileId`) USING BTREE,
  INDEX `FKD7FE146AF096DDB8`(`privateFlag`) USING BTREE,
  INDEX `FKD7FE146AC80FC67`(`userObj`) USING BTREE,
  INDEX `FKD7FE146AA440F95`(`fileClassObj`) USING BTREE,
  CONSTRAINT `FKD7FE146AA440F95` FOREIGN KEY (`fileClassObj`) REFERENCES `fileclass` (`classId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKD7FE146AC80FC67` FOREIGN KEY (`userObj`) REFERENCES `userinfo` (`user_name`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKD7FE146AF096DDB8` FOREIGN KEY (`privateFlag`) REFERENCES `yesorno` (`yesNoId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fileinfo_ibfk_1` FOREIGN KEY (`fileClassObj`) REFERENCES `fileclass` (`classId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fileinfo_ibfk_2` FOREIGN KEY (`privateFlag`) REFERENCES `yesorno` (`yesNoId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fileinfo_ibfk_3` FOREIGN KEY (`userObj`) REFERENCES `userinfo` (`user_name`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of fileinfo
-- ----------------------------
INSERT INTO `fileinfo` VALUES (2, 1, '关于java的一些技术分享', 'upload/72AAC9C9AC69187309B29ACFFF49188B.jpg', '文件里面是一些java技术视频的分享地址，需要的朋友拿去看看', 1, 'upload/337F735A4F870E92F1F55E42E29D711E.txt', 'user1', '2021-05-13');
INSERT INTO `fileinfo` VALUES (3, 2, '基于vue的教学课件', 'upload/25B8EF7F363BB73A244014616A461C7B.jpg', '基于vue移动组件化开发课件资料', 1, 'upload/91A80D6C99153C586B12E5FFEBE4AB28.ppt', 'user1', '2021-10-15 13:11:33');
INSERT INTO `fileinfo` VALUES (4, 2, '基于Python的教学课件', 'upload/EB89A27FB62E0CBFD02C205D1766F7D4.jpg', 'python课件，都是python的知识', 1, 'upload/72D5F86F6860F4E87257F1CDAB0FBBDE.ppt', 'user2', '2021-10-15 14:12:47');

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `noticeId` int(11) NOT NULL AUTO_INCREMENT COMMENT '公告id',
  `title` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `content` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公告内容',
  `publishDate` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发布时间',
  PRIMARY KEY (`noticeId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (1, '基于安卓网盘系统上线了', '朋友们可以来这里发布共享资料了哦！', '2021-05-13');
INSERT INTO `notice` VALUES (2, '2222', '3333', '2021-10-15');

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo`  (
  `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'user_name',
  `password` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录密码',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `gender` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '性别',
  `birthDate` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出生日期',
  `userPhoto` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户照片',
  `telephone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `address` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '家庭地址',
  `regTime` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`user_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('user1', '123', '张熙桐', '女', '2021-05-12 00:00:00', 'upload/A77875084BE7460DC102DA2D4306736F.jpg', '13508129034', 'xitong@163.com', '成都红星路10号', '2021-05-30 12:11:52');
INSERT INTO `userinfo` VALUES ('user2', '123', '李小涛', '女', '2021-05-11 00:00:00', 'upload/D80876EB0A27928DEB9913FE963F8E60.jpg', '13508108234', 'xiaotao@126.com', '四川省南充市滨江路', '2021-06-02 14:12:12');

-- ----------------------------
-- Table structure for yesorno
-- ----------------------------
DROP TABLE IF EXISTS `yesorno`;
CREATE TABLE `yesorno`  (
  `yesNoId` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `yesNoName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '记录名称',
  PRIMARY KEY (`yesNoId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of yesorno
-- ----------------------------
INSERT INTO `yesorno` VALUES (1, '是');
INSERT INTO `yesorno` VALUES (2, '否');

SET FOREIGN_KEY_CHECKS = 1;
