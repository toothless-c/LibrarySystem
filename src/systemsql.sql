/*
 Navicat MySQL Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : systemsql

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 09/09/2023 21:36:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bk_books
-- ----------------------------
DROP TABLE IF EXISTS `bk_books`;
CREATE TABLE `bk_books`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT ' 图书id',
  `name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图书名称',
  `book_place_num` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '书架号',
  `s_m` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书编号',
  `author` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书作者',
  `price` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书价格',
  `number` int(30) NULL DEFAULT NULL COMMENT '图书数量',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `classify_id` int(11) NULL DEFAULT NULL COMMENT '分类id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 40 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bk_books
-- ----------------------------
INSERT INTO `bk_books` VALUES (38, 'CTF特训营', 'K001', 'K001', 'FlappyPig战队', '89', 1, '2022-10-24 02:44:12', NULL, 9);
INSERT INTO `bk_books` VALUES (36, '西游记', 'A001', 'A001', '吴承恩', '99', 13, '2022-10-22 05:09:27', NULL, 1);
INSERT INTO `bk_books` VALUES (37, '红楼梦', 'A002', 'A002', '曹雪芹', '98', 19, '2022-10-22 05:10:12', NULL, 1);
INSERT INTO `bk_books` VALUES (39, 'AndroidStudio应用开发', 'K002', 'K002', '王翠萍', '69', 19, '2022-10-24 02:45:20', NULL, 9);

-- ----------------------------
-- Table structure for bk_classify
-- ----------------------------
DROP TABLE IF EXISTS `bk_classify`;
CREATE TABLE `bk_classify`  (
  `classify_Id` int(10) NOT NULL AUTO_INCREMENT COMMENT '图书分类',
  `classify_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '分类名称',
  PRIMARY KEY (`classify_Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bk_classify
-- ----------------------------
INSERT INTO `bk_classify` VALUES (1, '文学');
INSERT INTO `bk_classify` VALUES (9, '科技');
INSERT INTO `bk_classify` VALUES (10, '科普');

-- ----------------------------
-- Table structure for bk_user
-- ----------------------------
DROP TABLE IF EXISTS `bk_user`;
CREATE TABLE `bk_user`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id ',
  `username` varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '账号',
  `name` varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '昵称',
  `password` varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `sex` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '手机号',
  `student_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '学号',
  `type` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '类型',
  `class_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '班级',
  `check_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '审核状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bk_user
-- ----------------------------
INSERT INTO `bk_user` VALUES (5, '王二', NULL, 'b9e79361b4040a3f3a71668163d2f058', '0', '51448648', '1561561', '1', '156', '0');
INSERT INTO `bk_user` VALUES (7, '王五', NULL, 'b9e79361b4040a3f3a71668163d2f058', '0', '1215146', '156161', '1', '213', '1');
INSERT INTO `bk_user` VALUES (8, 'chenxuehai', NULL, 'f379eaf3c831b04de153469d1bec345e', '0', '128361782', '121318', '1', '123', '1');

-- ----------------------------
-- Table structure for bk_user_role
-- ----------------------------
DROP TABLE IF EXISTS `bk_user_role`;
CREATE TABLE `bk_user_role`  (
  `reader_role_id` int(10) NOT NULL AUTO_INCREMENT COMMENT ' ',
  `reader_id` int(10) NULL DEFAULT NULL,
  `role_id` int(10) NULL DEFAULT NULL,
  PRIMARY KEY (`reader_role_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = FIXED;

-- ----------------------------
-- Records of bk_user_role
-- ----------------------------
INSERT INTO `bk_user_role` VALUES (3, 7, 12);
INSERT INTO `bk_user_role` VALUES (4, 8, 12);

-- ----------------------------
-- Table structure for boorow_book
-- ----------------------------
DROP TABLE IF EXISTS `boorow_book`;
CREATE TABLE `boorow_book`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `book_id` int(10) NULL DEFAULT NULL COMMENT '图书id',
  `reader_id` int(10) NULL DEFAULT NULL COMMENT '读者id',
  `borrow_time` datetime NULL DEFAULT NULL COMMENT '借书时间',
  `return_time` datetime NULL DEFAULT NULL COMMENT '还书时间',
  `check_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `borrow_status` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `excepion_text` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '异常说明',
  `return_status` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of boorow_book
-- ----------------------------
INSERT INTO `boorow_book` VALUES (1, 36, 1, '2022-09-20 14:33:46', '2022-10-18 14:27:38', '1', '2', '', NULL);
INSERT INTO `boorow_book` VALUES (2, 38, 2, '2022-10-24 14:11:09', '2022-10-26 09:13:03', '0', '1', NULL, NULL);
INSERT INTO `boorow_book` VALUES (19, 36, 1, '2022-10-26 02:47:16', '2022-10-28 16:00:00', '1', '2', NULL, NULL);
INSERT INTO `boorow_book` VALUES (21, 39, 1, '2022-10-26 02:47:49', '2022-10-30 16:00:00', '1', '1', NULL, NULL);
INSERT INTO `boorow_book` VALUES (20, 37, 1, '2022-10-26 02:47:49', '2022-10-30 16:00:00', '1', '2', '忘记还书时间', '2');
INSERT INTO `boorow_book` VALUES (22, 38, 8, '2022-10-27 05:05:47', '2022-10-27 16:00:00', '1', '3', NULL, NULL);
INSERT INTO `boorow_book` VALUES (23, 36, 8, '2022-10-27 05:05:47', '2022-10-27 16:00:00', '1', '3', NULL, NULL);
INSERT INTO `boorow_book` VALUES (24, 37, 8, '2022-10-27 05:05:47', '2022-10-27 16:00:00', '1', '3', NULL, NULL);
INSERT INTO `boorow_book` VALUES (25, 38, 7, '2022-10-27 05:16:11', '2022-10-27 16:00:00', '1', '1', NULL, NULL);
INSERT INTO `boorow_book` VALUES (26, 38, 7, '2022-10-27 05:17:39', '2022-10-25 16:00:00', '1', '1', NULL, NULL);
INSERT INTO `boorow_book` VALUES (27, 38, 1, '2023-01-01 03:02:38', '2023-01-26 16:00:00', NULL, '1', NULL, NULL);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父级菜单id',
  `title` varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `code` varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '权限字段',
  `name` varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '路由name',
  `path` varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '路由path',
  `url` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '组件路径',
  `type` varchar(2) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '类型(0 目录 1菜单)',
  `icon` varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `parent_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '上级菜单名称',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '序号',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '系统管理', '', 'system', '/system', '', '0', 'el-icon-set-up', '顶级菜单', 1, '2022-10-23 07:46:07', '2022-10-23 09:06:27');
INSERT INTO `sys_menu` VALUES (2, 1, '用户管理', '', 'sysUserList', '/sysUserList', '/system/sysUserList', '1', 'el-icon-user-solid', '系统管理', 1, '2022-10-23 07:47:23', '2022-10-23 09:17:13');
INSERT INTO `sys_menu` VALUES (3, 1, '角色管理', '', 'sysRoleList', '/sysRoleList', '/system/sysRoleList', '1', 'el-icon-s-order', '系统管理', 2, '2022-10-23 08:22:40', NULL);
INSERT INTO `sys_menu` VALUES (4, 1, '菜单管理', '', 'sysMenuList', '/sysMenuList', '/system/sysMenuList', '1', 'el-icon-s-promotion', '系统管理', 3, '2022-10-23 08:24:17', '2022-10-23 09:17:24');
INSERT INTO `sys_menu` VALUES (5, 0, '图书管理', '', 'books', '/books', '', '0', 'el-icon-reading', '顶级菜单', 2, '2022-10-23 09:02:18', '2022-10-23 09:16:55');
INSERT INTO `sys_menu` VALUES (6, 1, '公告管理', '', 'sysNotice', '/sysNotice', '/system/sysNotice', '1', 'el-icon-chat-line-square', '系统管理', 4, '2022-10-23 09:07:03', '2022-10-23 09:17:30');
INSERT INTO `sys_menu` VALUES (7, 1, '读者管理', '', 'bkUser', '/bkUser', '/system/bkUser', '1', 'el-icon-goblet', '系统管理', 5, '2022-10-23 09:08:22', '2022-10-23 09:17:38');
INSERT INTO `sys_menu` VALUES (8, 5, '图书列表', '', 'bookAdd', '/bookAdd', '/books/bookAdd', '1', 'el-icon-collection', '图书管理', 2, '2022-10-23 09:08:57', '2022-10-23 09:17:45');
INSERT INTO `sys_menu` VALUES (9, 5, '图书分类', '', 'bookFl', '/bookFl', '/books/bookFl', '1', 'el-icon-folder-opened', '图书管理', 2, '2022-10-23 09:09:21', '2022-10-23 09:17:59');
INSERT INTO `sys_menu` VALUES (10, 0, '借阅管理', '', 'borrow', '/borrow', '', '0', 'el-icon-ice-cream-round', '顶级菜单', 3, '2022-10-23 09:10:02', '2022-10-23 09:16:59');
INSERT INTO `sys_menu` VALUES (11, 10, '借书管理', '', 'BorrowBook', '/borrowBook', '/borrow/BorrowBook', '1', 'el-icon-grape', '借阅管理', 1, '2022-10-23 09:10:30', '2022-10-23 09:17:51');
INSERT INTO `sys_menu` VALUES (12, 10, '还书管理', '', 'returnBook', '/returnBook', '/borrow/ReturnBook', '1', 'el-icon-goblet', '借阅管理', 2, '2022-10-23 09:10:51', '2022-10-23 09:17:55');
INSERT INTO `sys_menu` VALUES (13, 10, '借阅查看', '', 'borrowLook', '/borrowLook', '/borrow/borrowLook', '1', 'el-icon-s-cooperation', '借阅管理', 3, '2022-10-26 12:25:30', NULL);

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `notice_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `notice_title` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `notice_content` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (9, '测试', '这是一条测试公告', '2022-10-22 04:42:55');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `role_type` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '2' COMMENT '角色类型 1：系统管理员  2：普通用户',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (11, '图书管理员', '1', '图书管理员', '2022-08-25 15:23:41', '2022-10-26 06:11:53');
INSERT INTO `sys_role` VALUES (12, '读者', '2', '图书读者', '2022-10-26 02:19:10', NULL);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  `menu_id` int(11) NULL DEFAULT NULL COMMENT '菜单id',
  PRIMARY KEY (`role_menu_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 292 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = FIXED;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (247, 12, 13);
INSERT INTO `sys_role_menu` VALUES (291, 11, 13);
INSERT INTO `sys_role_menu` VALUES (246, 12, 12);
INSERT INTO `sys_role_menu` VALUES (245, 12, 11);
INSERT INTO `sys_role_menu` VALUES (244, 12, 10);
INSERT INTO `sys_role_menu` VALUES (290, 11, 12);
INSERT INTO `sys_role_menu` VALUES (289, 11, 11);
INSERT INTO `sys_role_menu` VALUES (288, 11, 10);
INSERT INTO `sys_role_menu` VALUES (287, 11, 9);
INSERT INTO `sys_role_menu` VALUES (286, 11, 8);
INSERT INTO `sys_role_menu` VALUES (285, 11, 5);
INSERT INTO `sys_role_menu` VALUES (284, 11, 7);
INSERT INTO `sys_role_menu` VALUES (283, 11, 6);
INSERT INTO `sys_role_menu` VALUES (282, 11, 4);
INSERT INTO `sys_role_menu` VALUES (281, 11, 3);
INSERT INTO `sys_role_menu` VALUES (280, 11, 2);
INSERT INTO `sys_role_menu` VALUES (279, 11, 1);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `nick_name` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录账号',
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录密码',
  `phone` varchar(13) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `is_admin` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '0:男 1女',
  `is_enabled` tinyint(2) NULL DEFAULT 1 COMMENT '帐户是否可用(1 可用，0 删除用户)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 43 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '宇神', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '18888888888', 'bnessy666@qq.com', '1', '0', 1, '2022-08-22 21:38:12', '2023-09-09 10:08:50');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`user_role_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = FIXED;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (3, 42, 11);
INSERT INTO `sys_user_role` VALUES (1, 1, 11);

SET FOREIGN_KEY_CHECKS = 1;
