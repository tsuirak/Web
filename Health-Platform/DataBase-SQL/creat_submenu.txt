SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for submenu
-- ----------------------------
DROP TABLE IF EXISTS `submenu`;
CREATE TABLE `submenu` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `path` varchar(255) NOT NULL,
  `mid` int(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `mid` (`mid`),
  CONSTRAINT `submenu_ibfk_1` FOREIGN KEY (`mid`) REFERENCES `mainmenu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=204 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of submenu
-- ----------------------------
INSERT INTO `submenu` VALUES ('101', '用户列表', '/user', '100');
INSERT INTO `submenu` VALUES ('102', '修改权限', '/rights', '100');
INSERT INTO `submenu` VALUES ('103', ' 运动模块', '/sport', '100');
INSERT INTO `submenu` VALUES ('104', '商品模块', '/goods', '100');
INSERT INTO `submenu` VALUES ('201', '运动科普', '/Introduction', '200');
INSERT INTO `submenu` VALUES ('202', '卡路里', '/calories', '200');
INSERT INTO `submenu` VALUES ('203', '营养配餐', '/food', '200');
