/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : jiangnan

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-09-01 22:42:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ga_position
-- ----------------------------
DROP TABLE IF EXISTS `ga_position`;
CREATE TABLE `ga_position` (
  `level` int(11) NOT NULL,
  `pos` int(11) NOT NULL,
  `junction` int(11) NOT NULL,
  `direction` int(11) NOT NULL,
  `distance` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `lift_area` varchar(20) DEFAULT NULL,
  `junction2` int(11) NOT NULL,
  `direction2` int(11) NOT NULL,
  `distance2` int(11) NOT NULL,
  `type2` int(11) NOT NULL,
  PRIMARY KEY (`level`,`pos`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
