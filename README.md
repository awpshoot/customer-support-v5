# customer-support-v5
visit information collect
## 建表语句
CREATE TABLE visitinfo (
	id INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
	sessionid VARCHAR(50) COMMENT '会话ID',
	cookie VARCHAR(50) COMMENT 'cookie页面UUID',
	account VARCHAR(10) COMMENT 'openid',
	clientip VARCHAR(20) COMMENT '客户端ip地址',
	systemname VARCHAR(10) COMMENT '系统模块名称',
	title VARCHAR(30) COMMENT '网页标题',
	url VARCHAR(50) COMMENT '网页地址',
	referer VARCHAR(50) COMMENT '来源地址',
	operateid INT COMMENT '操作id',
	operatetype VARCHAR(10) COMMENT '操作类型',
	target VARCHAR(100) COMMENT '点击目标',
	clickelement VARCHAR(100) COMMENT '点击元素信息',
	collectmark VARCHAR(20) COMMENT '点击标记信息',
	clientimt TIMESTAMP COMMENT '客户端时间',
	disp VARCHAR(100) COMMENT '进入系统后延时毫秒',
	servertime TIMESTAMP COMMENT '服务器时间',
	cd VARCHAR(5) COMMENT '客户端屏幕颜色',
	sw VARCHAR(5) COMMENT '客户端屏幕宽度',
    sh VARCHAR(5) COMMENT '客户端屏幕高度',
	lang VARCHAR(10) COMMENT '客户端语言'
) COMMENT='访问信息表' DEFAULT CHARSET=utf8;