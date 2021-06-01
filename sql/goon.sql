/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : goon

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2021-04-07 20:57:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ability_model
-- ----------------------------
DROP TABLE IF EXISTS `ability_model`;
CREATE TABLE `ability_model` (
  `unid` varchar(32) NOT NULL COMMENT '主键标识',
  `model_name` varchar(128) DEFAULT NULL COMMENT '模型名称',
  `model_desc` varchar(512) DEFAULT NULL COMMENT '模型描述',
  `model_status` char(1) DEFAULT '1' COMMENT '模型状态 1、未发布2、待审核3、已发布',
  `model_version` varchar(32) DEFAULT NULL COMMENT '模型版本',
  `version_desc` varchar(512) DEFAULT NULL COMMENT '模型版本迭代内容',
  `delete_flag` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  `dept_code` varchar(32) DEFAULT NULL COMMENT '部门编码',
  `dept_name` varchar(32) DEFAULT NULL COMMENT '部门名称',
  `model_detail_xml` text COMMENT '模型大文本xml',
  `create_unid` varchar(32) DEFAULT NULL COMMENT '创建人标识',
  `create_name` varchar(32) DEFAULT NULL COMMENT '创建人名称',
  `update_unid` varchar(32) DEFAULT NULL COMMENT '更新人标识',
  `update_name` varchar(32) DEFAULT NULL COMMENT '更新名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`unid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模型表';

-- ----------------------------
-- Records of ability_model
-- ----------------------------
INSERT INTO `ability_model` VALUES ('1', '双随机检查综合业务服务模型', '模型描述描述，新增部分信息', '3', '1.6', '1、更新内容1。<br>2、更新内容', '0', '0001', '福建省', 'xml', '1356543863967162369', 'ability-admin', '1356543863967162369', 'ability-admin', '2021-02-18 10:03:45', '2021-02-18 10:03:48');
INSERT INTO `ability_model` VALUES ('2', '双随机检查综合业务服务模型', '模型描述描述', '3', '1.5', '1、更新内容2。<br>2、更新内容', '0', '0001', '福建省', 'xml', '1356543863967162369', 'ability-admin', '1356543863967162369', 'ability-admin', '2021-02-17 10:03:45', '2021-02-17 10:03:48');
INSERT INTO `ability_model` VALUES ('3', '食品安全业务服务模型', '食品', '3', '1.2', '1、更新内容3。<br>2、更新内容', '0', '0001', '福建省', 'xml', '1356543863967162369', 'ability-admin', '1356543863967162369', 'ability-admin', '2021-02-17 10:03:45', '2021-02-17 10:03:48');
INSERT INTO `ability_model` VALUES ('4', '食品安全业务服务模型', '食品', '3', '1.1', '1、更新内容4。<br>2、更新内容', '0', '0001', '福建省', 'xml', '1356543863967162369', 'ability-admin', '1356543863967162369', 'ability-admin', '2021-02-17 10:03:45', '2021-02-17 10:03:48');
INSERT INTO `ability_model` VALUES ('5', '食品安全业务服务模型', '食品', '3', '1.0', '1、更新内容5。<br>2、更新内容', '0', '0001', '福建省', 'xml', '1356543863967162369', 'ability-admin', '1356543863967162369', 'ability-admin', '2021-02-17 10:03:45', '2021-02-17 10:03:48');
INSERT INTO `ability_model` VALUES ('6', '行政检查业务服务模型', '行政检查', '3', '1.0', '1、更新内容5。<br>2、更新内容', '0', '0001', '福建省', 'xml', '1356543863967162369', 'ability-admin', '1356543863967162369', 'ability-admin', '2021-02-19 10:03:45', '2021-02-17 10:03:48');

-- ----------------------------
-- Table structure for gencode_entity
-- ----------------------------
DROP TABLE IF EXISTS `gencode_entity`;
CREATE TABLE `gencode_entity` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) DEFAULT NULL COMMENT '逻辑删除',
  `entity_desc` varchar(255) NOT NULL DEFAULT '' COMMENT '实体描述',
  `entity_name` varchar(32) NOT NULL DEFAULT '' COMMENT '实体名称',
  `is_logic_delete` char(1) DEFAULT NULL COMMENT '是否逻辑删除',
  `template_type` char(2) NOT NULL DEFAULT '' COMMENT '模板类型',
  `create_by_id` varchar(32) DEFAULT '' COMMENT 'ID',
  `update_by_id` varchar(32) DEFAULT '' COMMENT 'ID',
  `module_name` varchar(32) DEFAULT NULL COMMENT '模块名称',
  `parent_menu_id` varchar(32) DEFAULT NULL COMMENT '上级id',
  `parent_menu` varchar(32) DEFAULT NULL COMMENT '上级id',
  PRIMARY KEY (`id`),
  KEY `FKlsbm0lq9qjoflcq2wbna8pyi6` (`parent_menu_id`),
  CONSTRAINT `FKlsbm0lq9qjoflcq2wbna8pyi6` FOREIGN KEY (`parent_menu_id`) REFERENCES `upms_menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of gencode_entity
-- ----------------------------

-- ----------------------------
-- Table structure for gencode_entity_item
-- ----------------------------
DROP TABLE IF EXISTS `gencode_entity_item`;
CREATE TABLE `gencode_entity_item` (
  `entity_id` varchar(32) NOT NULL DEFAULT '' COMMENT 'ID',
  `check_expression` char(255) NOT NULL DEFAULT '' COMMENT '检验字段表达式',
  `dict_id` varchar(32) DEFAULT '' COMMENT '对应字典id',
  `form_type` char(2) NOT NULL DEFAULT '' COMMENT '模板类型',
  `is_list_show` char(1) NOT NULL DEFAULT '' COMMENT '是否列表展示',
  `item_desc` varchar(64) NOT NULL DEFAULT '' COMMENT '字段描述',
  `item_name` varchar(32) NOT NULL DEFAULT '' COMMENT '字段名称',
  `item_type` varchar(32) NOT NULL DEFAULT '' COMMENT 'JAVA类型',
  `querytype` char(2) NOT NULL DEFAULT '' COMMENT '查询类型',
  `dict_code` varchar(32) DEFAULT '' COMMENT '对应字典编码',
  `is_must` char(1) NOT NULL DEFAULT '' COMMENT '是否必填',
  `is_sort` char(1) NOT NULL DEFAULT '' COMMENT '是否可排序',
  `is_total` char(1) NOT NULL DEFAULT '' COMMENT '是否合计',
  `is_unique` char(1) NOT NULL DEFAULT '' COMMENT '是否唯一',
  `list_length` char(4) NOT NULL DEFAULT '' COMMENT '列表长度',
  `query_exp` char(2) NOT NULL DEFAULT '' COMMENT '查询条件',
  `query_type` char(2) NOT NULL DEFAULT '' COMMENT '查询类型',
  `sql_type` varchar(32) NOT NULL DEFAULT '' COMMENT 'SQL类型'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of gencode_entity_item
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'ID',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `code` varchar(32) NOT NULL DEFAULT '' COMMENT '字典编码',
  `del_flag` char(1) DEFAULT NULL COMMENT '逻辑删除',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '字典名称',
  `create_by_id` varchar(32) DEFAULT '' COMMENT 'ID',
  `update_by_id` varchar(32) DEFAULT '' COMMENT 'ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1309910890841247744', '2020-09-27 01:41:08', '2020-09-27 01:47:02', 'common_gender', '0', '性别', '1304088580594995200', '1304088580594995200');
INSERT INTO `sys_dict` VALUES ('1310185078156038144', '2020-09-27 19:50:39', '2020-09-27 20:08:55', 'upms_menu_type', '0', '菜单类型', '1304088580594995200', '1304088580594995200');
INSERT INTO `sys_dict` VALUES ('1310229953501073408', '2020-09-27 22:48:58', '2020-09-27 22:49:56', 'sys_param_type', '0', '参数类型', '1304088580594995200', '1304088580594995200');
INSERT INTO `sys_dict` VALUES ('1310970172076593152', '2020-09-29 23:50:20', '2020-09-29 23:51:46', 'upms_datascope_type', '0', '数据权限类型', '1304088580594995200', '1304088580594995200');
INSERT INTO `sys_dict` VALUES ('1366435170097434624', '2021-03-02 01:08:26', '2021-03-02 01:14:23', 'yes_no', '0', '是否', '1304088580594995200', '1304088580594995200');
INSERT INTO `sys_dict` VALUES ('1366437865533345792', '2021-03-02 01:19:09', '2021-03-02 01:19:45', 'gencode_template_type', '0', '生成代码模板', '1304088580594995200', '1304088580594995200');

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item` (
  `dict_id` varchar(32) NOT NULL DEFAULT '' COMMENT 'ID',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '字典项名称',
  `sort` int(11) NOT NULL COMMENT '字典项排序',
  `value` char(2) NOT NULL COMMENT '字典项值'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
INSERT INTO `sys_dict_item` VALUES ('1309910890841247744', '男', '0', '0');
INSERT INTO `sys_dict_item` VALUES ('1309910890841247744', '女', '1', '1');
INSERT INTO `sys_dict_item` VALUES ('1310185078156038144', '容器', '0', '0');
INSERT INTO `sys_dict_item` VALUES ('1310185078156038144', '列表', '1', '1');
INSERT INTO `sys_dict_item` VALUES ('1310185078156038144', '按钮', '2', '2');
INSERT INTO `sys_dict_item` VALUES ('1310229953501073408', '文本', '0', '0');
INSERT INTO `sys_dict_item` VALUES ('1310229953501073408', '链接', '1', '1');
INSERT INTO `sys_dict_item` VALUES ('1310229953501073408', '富文本', '2', '2');
INSERT INTO `sys_dict_item` VALUES ('1310970172076593152', '全部', '0', '0');
INSERT INTO `sys_dict_item` VALUES ('1310970172076593152', '自己', '1', '1');
INSERT INTO `sys_dict_item` VALUES ('1310970172076593152', '本部门', '2', '2');
INSERT INTO `sys_dict_item` VALUES ('1310970172076593152', '本部门及以下', '3', '3');
INSERT INTO `sys_dict_item` VALUES ('1310970172076593152', '下级部门', '4', '4');
INSERT INTO `sys_dict_item` VALUES ('1310970172076593152', '本部门不含自己', '5', '5');
INSERT INTO `sys_dict_item` VALUES ('1310970172076593152', '本部门及以下不含自己', '6', '6');
INSERT INTO `sys_dict_item` VALUES ('1366435170097434624', '否', '0', '0');
INSERT INTO `sys_dict_item` VALUES ('1366435170097434624', '是', '1', '1');
INSERT INTO `sys_dict_item` VALUES ('1366437865533345792', '单表模板', '0', '0');
INSERT INTO `sys_dict_item` VALUES ('1366437865533345792', '树型模板', '1', '1');
INSERT INTO `sys_dict_item` VALUES ('1366437865533345792', '左树右表', '2', '2');

-- ----------------------------
-- Table structure for sys_param
-- ----------------------------
DROP TABLE IF EXISTS `sys_param`;
CREATE TABLE `sys_param` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'ID',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `code` varchar(32) NOT NULL DEFAULT '' COMMENT '参数编码',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '参数名称',
  `type` char(1) NOT NULL COMMENT '类型',
  `value` longtext NOT NULL COMMENT '参数值',
  `create_by_id` varchar(32) DEFAULT '' COMMENT 'ID',
  `update_by_id` varchar(32) DEFAULT '' COMMENT 'ID',
  `del_flag` char(1) DEFAULT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_param
-- ----------------------------
INSERT INTO `sys_param` VALUES ('1310257544610058240', '2020-09-28 00:38:37', '2021-02-04 15:26:04', '132', '123', '1', '<p>643264526</p><p><span style=\"font-weight: bold;\">643264526426</span></p><p><br></p>', '1304088580594995200', '1304088580594995200', '0');
INSERT INTO `sys_param` VALUES ('1310262443049619456', '2020-09-28 00:58:05', '2021-02-04 15:23:27', '66', '666', '2', '<p><span style=\"font-weight: bold;\">54321423142314213</span></p><p>54325231</p><p><br></p>', '1304088580594995200', '1304088580594995200', '0');

-- ----------------------------
-- Table structure for upms_admin
-- ----------------------------
DROP TABLE IF EXISTS `upms_admin`;
CREATE TABLE `upms_admin` (
  `id` varchar(32) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `account` varchar(32) NOT NULL DEFAULT '' COMMENT '账户',
  `del_flag` char(1) DEFAULT NULL COMMENT '逻辑删除',
  `nickname` varchar(32) DEFAULT '' COMMENT '昵称',
  `password` varchar(32) NOT NULL DEFAULT '' COMMENT '密码',
  `create_by_id` varchar(32) DEFAULT NULL,
  `update_by_id` varchar(32) DEFAULT NULL,
  `dept_id` varchar(32) DEFAULT NULL,
  `salt` varchar(32) NOT NULL DEFAULT '' COMMENT '密码盐',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='后台用户表';

-- ----------------------------
-- Records of upms_admin
-- ----------------------------
INSERT INTO `upms_admin` VALUES ('1304088580594995200', '2020-09-04 19:34:20', '2021-02-18 13:15:49', 'admin', '0', 'Gon', '38b8df59cd025cecc7d717275bedb6a9', '', '', '1308457754750488576', '1599752524395');
INSERT INTO `upms_admin` VALUES ('1306592912028602368', '2020-09-17 21:56:40', '2021-02-18 13:30:30', 'admin1', '0', '昵称', '06d090afa81213b57288b9e7d6316eb9', '1304088580594995200', '1304088580594995200', '1307698224085209088', '1600351000197');

-- ----------------------------
-- Table structure for upms_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `upms_admin_role`;
CREATE TABLE `upms_admin_role` (
  `admin_id` varchar(32) NOT NULL,
  `role_id` varchar(32) NOT NULL,
  PRIMARY KEY (`admin_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of upms_admin_role
-- ----------------------------
INSERT INTO `upms_admin_role` VALUES ('1304088580594995200', '1304088771591016448');
INSERT INTO `upms_admin_role` VALUES ('1306592912028602368', '1304088771591016448');
INSERT INTO `upms_admin_role` VALUES ('1306592912028602368', '1308447280029896704');

-- ----------------------------
-- Table structure for upms_datascope
-- ----------------------------
DROP TABLE IF EXISTS `upms_datascope`;
CREATE TABLE `upms_datascope` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'ID',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `code` varchar(32) NOT NULL DEFAULT '' COMMENT '数据权限编码',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '数据全程描述',
  `create_by_id` varchar(32) DEFAULT '' COMMENT 'ID',
  `update_by_id` varchar(32) DEFAULT '' COMMENT 'ID',
  `value` char(2) NOT NULL DEFAULT '' COMMENT '数据权限值',
  `role_id` varchar(32) DEFAULT '' COMMENT 'ID',
  `del_flag` char(1) DEFAULT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据权限表';

-- ----------------------------
-- Records of upms_datascope
-- ----------------------------
INSERT INTO `upms_datascope` VALUES ('1357573668972138496', '2021-02-05 14:16:00', '2021-02-05 14:30:02', 'SYS:DICT', 'SYS:DICT', '1304088580594995200', '1304088580594995200', '0', null, '0');
INSERT INTO `upms_datascope` VALUES ('1357578192352841728', '2021-02-05 14:33:58', '2021-02-05 16:03:03', 'UPMS:ADMIN', 'UPMS:ADMIN', '1304088580594995200', '1304088580594995200', '0', null, '0');
INSERT INTO `upms_datascope` VALUES ('1357580557340512256', '2021-02-05 14:43:22', '2021-02-08 18:18:42', 'SYS:PARAM', 'SYS:PARAM', '1304088580594995200', '1304088580594995200', '0', null, '0');
INSERT INTO `upms_datascope` VALUES ('1357580601531699200', '2021-02-05 14:43:33', '2021-02-05 14:43:33', 'SYS:PARAM', 'SYS:PARAM', '1304088580594995200', '1304088580594995200', '4', null, '0');

-- ----------------------------
-- Table structure for upms_dept
-- ----------------------------
DROP TABLE IF EXISTS `upms_dept`;
CREATE TABLE `upms_dept` (
  `id` varchar(32) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `code` varchar(32) NOT NULL DEFAULT '' COMMENT '部门编码',
  `del_flag` char(1) DEFAULT NULL COMMENT '逻辑删除',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '部门名称',
  `create_by_id` varchar(32) DEFAULT NULL,
  `update_by_id` varchar(32) DEFAULT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门机构表';

-- ----------------------------
-- Records of upms_dept
-- ----------------------------
INSERT INTO `upms_dept` VALUES ('1307698224085209088', '2020-09-20 23:08:47', '2020-09-20 23:08:47', 'ALL:A', '0', 'A部门', '1304088580594995200', '1304088580594995200', '1308457754750488576');
INSERT INTO `upms_dept` VALUES ('1307698307526692864', '2020-09-20 23:09:07', '2020-09-20 23:09:07', 'ALL:A:1', '0', 'A-1部门', '1304088580594995200', '1304088580594995200', '1307698224085209088');
INSERT INTO `upms_dept` VALUES ('1307698410375221248', '2020-09-20 23:09:32', '2020-09-20 23:09:32', 'ALL:A:2', '0', 'A-2部门', '1304088580594995200', '1304088580594995200', '1307698224085209088');
INSERT INTO `upms_dept` VALUES ('1307698519255158784', '2020-09-20 23:09:58', '2020-09-20 23:09:58', 'ALL:B', '0', 'B部门', '1304088580594995200', '1304088580594995200', '1308457754750488576');
INSERT INTO `upms_dept` VALUES ('1307698576415133696', '2020-09-20 23:10:11', '2020-09-20 23:10:11', 'ALL:B:1', '0', 'B-1部门', '1304088580594995200', '1304088580594995200', '1307698519255158784');
INSERT INTO `upms_dept` VALUES ('1307698607293599744', '2020-09-20 23:10:19', '2020-09-20 23:10:19', 'ALL:B:2', '0', 'B-2部门', '1304088580594995200', '1304088580594995200', '1307698519255158784');
INSERT INTO `upms_dept` VALUES ('1308455366480236544', '2020-09-23 01:17:24', '2020-09-23 01:17:24', 'ALL:A:1:1', '0', 'A-1-1', '1304088580594995200', '1304088580594995200', '1307698307526692864');
INSERT INTO `upms_dept` VALUES ('1308457754750488576', '2020-09-04 19:35:10', '2020-09-04 19:35:10', 'ALL', '0', '总公司', '1304088580594995200', '1304088580594995200', null);

-- ----------------------------
-- Table structure for upms_menu
-- ----------------------------
DROP TABLE IF EXISTS `upms_menu`;
CREATE TABLE `upms_menu` (
  `id` varchar(32) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `code` varchar(32) NOT NULL DEFAULT '' COMMENT '菜单编码',
  `del_flag` char(1) DEFAULT NULL COMMENT '逻辑删除',
  `icon` varchar(32) DEFAULT '' COMMENT '目标',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '菜单名称',
  `permission` varchar(255) DEFAULT '' COMMENT '权限标识',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `target` varchar(16) DEFAULT '' COMMENT '目标',
  `type` varchar(32) DEFAULT '' COMMENT '类型',
  `url` varchar(255) DEFAULT '' COMMENT '链接地址',
  `create_by_id` varchar(32) DEFAULT NULL,
  `update_by_id` varchar(32) DEFAULT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单资源表';

-- ----------------------------
-- Records of upms_menu
-- ----------------------------
INSERT INTO `upms_menu` VALUES ('1304085950850273280', '2020-09-10 23:54:54', '2020-09-10 23:54:54', 'SYS', '0', null, '系统设置', null, '2', null, '0', null, '1304088580594995200', '1304088580594995200', null);
INSERT INTO `upms_menu` VALUES ('1304086454053507072', '2020-09-10 23:56:54', '2021-02-18 13:06:02', 'SYS:DICT', '0', '', '字典配置', 'SYS:DICT:LIST', '0', null, '1', '/sys/dict', '1304088580594995200', '1304088580594995200', '1304085950850273280');
INSERT INTO `upms_menu` VALUES ('1304087336979664896', '2020-09-11 00:00:25', '2020-09-11 00:00:25', 'DATASOURCE', '0', null, '数据管理', null, '3', null, '0', null, '1304088580594995200', '1304088580594995200', null);
INSERT INTO `upms_menu` VALUES ('1304088208937717760', '2020-09-11 00:03:52', '2021-02-18 13:06:11', 'DATASOURCE:EHCACHE', '0', '', 'Ehcache管理', 'DATASOURCE:EHCACHE:LIST', '0', null, '1', '', '1304088580594995200', '1304088580594995200', '1304087336979664896');
INSERT INTO `upms_menu` VALUES ('1304090865509208064', '2020-09-04 19:34:50', '2020-09-04 19:34:50', 'UPMS', '0', null, '权限管理', null, '0', null, '0', null, '1304088580594995200', '1304088580594995200', null);
INSERT INTO `upms_menu` VALUES ('1304090865513402368', '2020-09-04 19:47:18', '2020-09-04 19:47:18', 'SCRUM', '0', null, '敏捷开发', null, '1', null, '0', null, '1304088580594995200', '1304088580594995200', null);
INSERT INTO `upms_menu` VALUES ('1304090865513402369', '2020-09-04 19:48:15', '2020-09-04 19:48:15', 'UPMS:ADMIN', '0', null, '用户管理', 'UPMS:ADMIN:LIST', '0', null, '1', '/upms/admin', '1304088580594995200', '1304088580594995200', '1304090865509208064');
INSERT INTO `upms_menu` VALUES ('1304090865513402370', '2020-09-04 19:48:37', '2020-09-04 19:48:37', 'UPMS:ROLE', '0', null, '角色管理', 'UPMS:ROLE:LIST', '1', null, '1', '/upms/role', '1304088580594995200', '1304088580594995200', '1304090865509208064');
INSERT INTO `upms_menu` VALUES ('1304090865513402371', '2020-09-04 19:48:53', '2020-09-04 19:48:53', 'UPMS:DEPT', '0', null, '部门管理', 'UPMS:DEPT:LIST', '2', null, '1', '/upms/dept', '1304088580594995200', '1304088580594995200', '1304090865509208064');
INSERT INTO `upms_menu` VALUES ('1304090865513402372', '2020-09-04 19:49:13', '2020-09-04 19:49:13', 'UPMS:MENU', '0', null, '菜单管理', 'UPMS:MENU:LIST', '3', null, '1', '/upms/menu', '1304088580594995200', '1304088580594995200', '1304090865509208064');
INSERT INTO `upms_menu` VALUES ('1304090865513402373', '2020-09-04 19:53:56', '2020-09-04 19:53:56', 'UPMS:MENU:ADD', '0', null, '菜单管理-新增', 'UPMS:MENU:ADD', '0', null, '2', null, '1304088580594995200', '1304088580594995200', '1304090865513402372');
INSERT INTO `upms_menu` VALUES ('1309785017022550016', '2020-09-26 17:20:57', '2020-09-26 17:20:57', 'SYS:PARAM', '0', null, '参数配置', 'SYS:PARAM:LIST', '1', null, '1', '/sys/param', '1304088580594995200', '1304088580594995200', '1304085950850273280');
INSERT INTO `upms_menu` VALUES ('1362248713648607232', '2021-02-18 11:52:57', '2021-02-18 11:52:57', 'DATASOURCE:DRUID:LIST', '0', null, 'Druid监控', 'DATASOURCE:DRUID:LIST', '0', null, '1', '/druid', '1304088580594995200', '1304088580594995200', '1304087336979664896');
INSERT INTO `upms_menu` VALUES ('1362320045493063680', '2021-02-18 16:36:24', '2021-02-18 16:36:24', 'UPMS:ADMIN:ADD', '0', '', '用户管理-新增', 'UPMS:ADMIN:ADD', '0', null, '2', '', '1304088580594995200', '1304088580594995200', '1304090865513402369');
INSERT INTO `upms_menu` VALUES ('1362320646083842048', '2021-02-18 16:38:47', '2021-02-18 16:38:47', 'UPMS:ADMIN:EDIT', '0', '', '用户管理-修改', 'UPMS:ADMIN:EDIT', '1', null, '2', '', '1304088580594995200', '1304088580594995200', '1304090865513402369');
INSERT INTO `upms_menu` VALUES ('1362320744893255680', '2021-02-18 16:39:11', '2021-02-18 16:39:11', 'UPMS:ADMIN:DEL', '0', '', '用户管理-删除', 'UPMS:ADMIN:DEL', '2', null, '2', '', '1304088580594995200', '1304088580594995200', '1304090865513402369');
INSERT INTO `upms_menu` VALUES ('1362320910413074432', '2021-02-18 16:39:50', '2021-02-18 16:39:50', 'UPMS:ADMIN:VIEW', '0', '', '用户管理-查看', 'UPMS:ADMIN:VIEW', '3', null, '2', '', '1304088580594995200', '1304088580594995200', '1304090865513402369');
INSERT INTO `upms_menu` VALUES ('1362321076281020416', '2021-02-18 16:40:30', '2021-02-18 16:40:30', 'UPMS:ROLE:ADD', '0', '', '角色管理-新增', 'UPMS:ROLE:ADD', '0', null, '2', '', '1304088580594995200', '1304088580594995200', '1304090865513402370');
INSERT INTO `upms_menu` VALUES ('1362321188352823296', '2021-02-18 16:40:57', '2021-02-18 16:40:57', 'UPMS:ROLE:EDIT', '0', '', '角色管理-修改', 'UPMS:ROLE:EDIT', '1', null, '2', '', '1304088580594995200', '1304088580594995200', '1304090865513402370');
INSERT INTO `upms_menu` VALUES ('1362321278375170048', '2021-02-18 16:41:18', '2021-02-18 16:41:18', 'UPMS:ROLE:DEL', '0', '', '角色管理-删除', 'UPMS:ROLE:DEL', '2', null, '2', '', '1304088580594995200', '1304088580594995200', '1304090865513402370');
INSERT INTO `upms_menu` VALUES ('1362321356523442176', '2021-02-18 16:41:37', '2021-02-18 16:41:37', 'UPMS:ROLE:VIEW', '0', '', '角色管理-查询', 'UPMS:ROLE:VIEW', '3', null, '2', '', '1304088580594995200', '1304088580594995200', '1304090865513402370');
INSERT INTO `upms_menu` VALUES ('1362321754856493056', '2021-02-18 16:43:12', '2021-02-18 16:43:12', 'UPMS:DEPT:ADD', '0', '', '部门管理-新增', 'UPMS:DEPT:ADD', '0', null, '2', '', '1304088580594995200', '1304088580594995200', '1304090865513402371');
INSERT INTO `upms_menu` VALUES ('1362322049422462976', '2021-02-18 16:44:22', '2021-02-18 16:44:22', 'UPMS:DEPT:EDIT', '0', '', '部门管理-修改', 'UPMS:DEPT:EDIT', '1', null, '2', '', '1304088580594995200', '1304088580594995200', '1304090865513402371');
INSERT INTO `upms_menu` VALUES ('1362322111724654592', '2021-02-18 16:44:37', '2021-02-18 16:44:37', 'UPMS:DEPT:DEL', '0', '', '部门管理-删除', 'UPMS:DEPT:DEL', '2', null, '2', '', '1304088580594995200', '1304088580594995200', '1304090865513402371');
INSERT INTO `upms_menu` VALUES ('1362322284802609152', '2021-02-18 16:45:18', '2021-02-18 16:45:18', 'UPMS:DEPT:VIEW', '0', '', '部门管理-查询', 'UPMS:DEPT:VIEW', '3', null, '2', '', '1304088580594995200', '1304088580594995200', '1304090865513402371');
INSERT INTO `upms_menu` VALUES ('1362322443120807936', '2021-02-18 16:45:56', '2021-02-18 16:45:56', 'UPMS:MENU:EDIT', '0', '', '菜单管理-修改', 'UPMS:MENU:EDIT', '1', null, '2', '', '1304088580594995200', '1304088580594995200', '1304090865513402372');
INSERT INTO `upms_menu` VALUES ('1362322520837066752', '2021-02-18 16:46:14', '2021-02-18 16:46:14', 'UPMS:MENU:DEL', '0', '', '菜单管理-删除', 'UPMS:MENU:DEL', '2', null, '2', '', '1304088580594995200', '1304088580594995200', '1304090865513402372');
INSERT INTO `upms_menu` VALUES ('1362322594921058304', '2021-02-18 16:46:32', '2021-02-18 16:46:32', 'UPMS:MENU:VIEW', '0', '', '菜单管理-查询', 'UPMS:MENU:VIEW', '3', null, '2', '', '1304088580594995200', '1304088580594995200', '1304090865513402372');
INSERT INTO `upms_menu` VALUES ('1362322882990051328', '2021-02-18 16:47:41', '2021-02-18 16:47:41', 'SYS:DICT:ADD', '0', '', '字典配置-新增', 'SYS:DICT:ADD', '0', null, '2', '', '1304088580594995200', '1304088580594995200', '1304086454053507072');
INSERT INTO `upms_menu` VALUES ('1362323054113460224', '2021-02-18 16:48:21', '2021-02-18 16:48:21', 'SYS:DICT:EDIT', '0', '', '字典配置-修改', 'SYS:DICT:EDIT', '1', null, '2', '', '1304088580594995200', '1304088580594995200', '1304086454053507072');
INSERT INTO `upms_menu` VALUES ('1362323157205258240', '2021-02-18 16:48:46', '2021-02-18 16:48:46', 'SYS:DICT:DEL', '0', '', '字典配置-删除', 'SYS:DICT:DEL', '2', null, '2', '', '1304088580594995200', '1304088580594995200', '1304086454053507072');
INSERT INTO `upms_menu` VALUES ('1362323274083733504', '2021-02-18 16:49:14', '2021-02-18 16:49:14', 'SYS:DICT:VIEW', '0', '', '字典配置-查询', 'SYS:DICT:VIEW', '3', null, '2', '', '1304088580594995200', '1304088580594995200', '1304086454053507072');
INSERT INTO `upms_menu` VALUES ('1362323793116270592', '2021-02-18 16:51:18', '2021-02-18 16:51:18', 'SYS:PARAM:ADD', '0', '', '参数配置-新增', 'SYS:PARAM:ADD', '0', null, '2', '', '1304088580594995200', '1304088580594995200', '1309785017022550016');
INSERT INTO `upms_menu` VALUES ('1362323863979036672', '2021-02-18 16:51:35', '2021-02-18 16:51:35', 'SYS:PARAM:EDIT', '0', '', '参数配置-修改', 'SYS:PARAM:EDIT', '1', null, '2', '', '1304088580594995200', '1304088580594995200', '1309785017022550016');
INSERT INTO `upms_menu` VALUES ('1362323926016987136', '2021-02-18 16:51:49', '2021-02-18 16:51:49', 'SYS:PARAM:DEL', '0', '', '参数配置-删除', 'SYS:PARAM:DEL', '2', null, '2', '', '1304088580594995200', '1304088580594995200', '1309785017022550016');
INSERT INTO `upms_menu` VALUES ('1362323988709249024', '2021-02-18 16:52:04', '2021-02-18 16:52:04', 'SYS:PARAM:VIEW', '0', '', '参数配置-查询', 'SYS:PARAM:VIEW', '3', null, '2', '', '1304088580594995200', '1304088580594995200', '1309785017022550016');
INSERT INTO `upms_menu` VALUES ('1362695886236422144', '2021-02-19 17:29:52', '2021-02-19 17:29:52', 'ces', '0', '', '404ces', 'ces', '1', null, '1', '/123456', '1304088580594995200', '1304088580594995200', '1304090865509208064');
INSERT INTO `upms_menu` VALUES ('1364061516223090688', '2021-02-23 11:56:23', '2021-02-23 11:56:23', 'GENCODE:ENTITY:LIST', '0', '', '代码生成', 'GENCODE:ENTITY:LIST', '0', null, '1', '/gencode/entity', '1304088580594995200', '1304088580594995200', '1304090865513402368');
INSERT INTO `upms_menu` VALUES ('1364076532997427200', '2021-02-23 12:56:03', '2021-02-23 12:56:13', 'GENCODE:ENTITY:ADD', '0', '', '代码生成-新增', 'GENCODE:ENTITY:ADD', '0', null, '2', '', '1304088580594995200', '1304088580594995200', '1364061516223090688');
INSERT INTO `upms_menu` VALUES ('1364076658918821888', '2021-02-23 12:56:33', '2021-02-23 12:56:33', 'GENCODE:ENTITY:EDIT', '0', '', '代码生成-修改', 'GENCODE:ENTITY:EDIT', '1', null, '2', '', '1304088580594995200', '1304088580594995200', '1364061516223090688');
INSERT INTO `upms_menu` VALUES ('1364076738845478912', '2021-02-23 12:56:53', '2021-02-23 12:56:53', 'GENCODE:ENTITY:DEL', '0', '', '代码生成-删除', 'GENCODE:ENTITY:DEL', '2', null, '2', '', '1304088580594995200', '1304088580594995200', '1364061516223090688');
INSERT INTO `upms_menu` VALUES ('1364076813365678080', '2021-02-23 12:57:10', '2021-02-23 12:57:10', 'GENCODE:ENTITY:VIEW', '0', '', '代码生成-查询', 'GENCODE:ENTITY:VIEW', '3', null, '2', '', '1304088580594995200', '1304088580594995200', '1364061516223090688');

-- ----------------------------
-- Table structure for upms_role
-- ----------------------------
DROP TABLE IF EXISTS `upms_role`;
CREATE TABLE `upms_role` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT 'ID',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `code` varchar(32) NOT NULL DEFAULT '' COMMENT '角色编码',
  `datascope_value` char(2) NOT NULL DEFAULT '0' COMMENT '默认数据权限',
  `del_flag` char(1) DEFAULT NULL COMMENT '逻辑删除',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '角色名称',
  `create_by_id` varchar(32) DEFAULT '' COMMENT 'ID',
  `update_by_id` varchar(32) DEFAULT '' COMMENT 'ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='后台角色表';

-- ----------------------------
-- Records of upms_role
-- ----------------------------
INSERT INTO `upms_role` VALUES ('1304088771591016448', null, '2021-02-20 17:31:07', 'superadmin', '0', '0', '超级管理员', null, '1304088580594995200');
INSERT INTO `upms_role` VALUES ('1308447280029896704', '2021-02-18 13:10:28', '2021-02-18 13:10:28', 'admin', '0', '0', '管理员', '1304088580594995200', '1304088580594995200');

-- ----------------------------
-- Table structure for upms_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `upms_role_menu`;
CREATE TABLE `upms_role_menu` (
  `role_id` varchar(32) NOT NULL,
  `menu_id` varchar(32) NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of upms_role_menu
-- ----------------------------
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1304085950850273280');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1304086454053507072');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1304087336979664896');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1304088208937717760');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1304090865509208064');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1304090865513402368');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1304090865513402369');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1304090865513402370');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1304090865513402371');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1304090865513402372');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1304090865513402373');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1309785017022550016');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1362248713648607232');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1362320045493063680');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1362320646083842048');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1362320744893255680');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1362320910413074432');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1362321076281020416');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1362321188352823296');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1362321278375170048');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1362321356523442176');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1362321754856493056');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1362322049422462976');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1362322111724654592');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1362322284802609152');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1362322443120807936');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1362322520837066752');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1362322594921058304');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1362322882990051328');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1362323054113460224');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1362323157205258240');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1362323274083733504');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1362323793116270592');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1362323863979036672');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1362323926016987136');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1362323988709249024');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1362695886236422144');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1364061516223090688');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1364076532997427200');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1364076658918821888');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1364076738845478912');
INSERT INTO `upms_role_menu` VALUES ('1304088771591016448', '1364076813365678080');
INSERT INTO `upms_role_menu` VALUES ('1308447280029896704', '1304085950850273280');
INSERT INTO `upms_role_menu` VALUES ('1308447280029896704', '1304086454053507072');
INSERT INTO `upms_role_menu` VALUES ('1308447280029896704', '1304087336979664896');
INSERT INTO `upms_role_menu` VALUES ('1308447280029896704', '1304088208937717760');
INSERT INTO `upms_role_menu` VALUES ('1308447280029896704', '1304090865509208064');
INSERT INTO `upms_role_menu` VALUES ('1308447280029896704', '1304090865513402368');
INSERT INTO `upms_role_menu` VALUES ('1308447280029896704', '1304090865513402369');
INSERT INTO `upms_role_menu` VALUES ('1308447280029896704', '1304090865513402370');
INSERT INTO `upms_role_menu` VALUES ('1308447280029896704', '1304090865513402371');
INSERT INTO `upms_role_menu` VALUES ('1308447280029896704', '1304090865513402372');
INSERT INTO `upms_role_menu` VALUES ('1308447280029896704', '1304090865513402373');
