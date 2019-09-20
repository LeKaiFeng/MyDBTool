/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : wuhan717

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-09-09 14:40:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ga_locations
-- ----------------------------
DROP TABLE IF EXISTS `ga_locations`;
CREATE TABLE `ga_locations` (
  `level` int(11) NOT NULL,
  `location` int(11) NOT NULL,
  `pos` int(11) DEFAULT NULL,
  `aisle` int(11) NOT NULL,
  `state` int(11) NOT NULL DEFAULT '0',
  `area` varchar(11) NOT NULL,
  `priority` int(11) NOT NULL DEFAULT '0',
  `box_number` varchar(255) NOT NULL DEFAULT '',
  `weight` int(11) NOT NULL DEFAULT '0',
  `lift_area` varchar(20) DEFAULT NULL,
  `color_area` varchar(255) DEFAULT NULL,
  `type` int(255) DEFAULT NULL,
  `Container_status` int(255) DEFAULT NULL,
  PRIMARY KEY (`location`,`level`) USING BTREE,
  KEY `position` (`pos`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
