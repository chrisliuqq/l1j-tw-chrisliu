SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for account
-- ----------------------------
CREATE TABLE `account` (
  `username` char(16) NOT NULL COMMENT '使用者的帳號稱名',
  `password` char(64) NOT NULL COMMENT '號帳的密碼',
  `last_login` datetime default NULL COMMENT '帳號登入的最後時間',
  `permission` tinyint(4) default '1' COMMENT '帳號的權限：0停權、1一般、10小GM、20大GM',
  `ip` char(15) default NULL COMMENT '帳號登入的IP',
  `character_slot` tinyint(2) unsigned default '6' COMMENT '帳號的角色數量',
  `online_status` tinyint(2) unsigned default '0' COMMENT '帳號的登入狀態',
  PRIMARY KEY  (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for connection_test_table
-- ----------------------------
CREATE TABLE `connection_test_table` (
  `just_test` char(1) character set latin1 NOT NULL default '',
  PRIMARY KEY  (`just_test`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
