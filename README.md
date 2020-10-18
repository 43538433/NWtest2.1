# Tutor reservation system
------
### 项目描述：
导师在日常工作中会需要抽出时间和学生见面，帮助学生解决一些实际问题。通过该项目，学生可以查询导师未来一段的空闲时间，并且可以提交预约请求预约导师见面；导师可以在平台添加自己的空闲时间，并且审批预约；
系统管理员可以看到所有的注册用户和预约请求，在日常使用中可以冻结一些提交恶意预约的用户。
### 实现的功能：（其实就是基本的数据库的增删改查，哈哈哈哈......)
* 学生端：
    * 可以查询导师的时间
    * 查询自己的预约请求
* 导师端：
    * 添加空闲时间    
    * 处理学生的预约请求
* 管理员端：
    * 查看所有的用户基本信息
    * 查看所有的预约请求
### 数据库表结构：
* `user`（用于记录所有登录的用户信息）
```mysql
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '姓名',
  `sid` varchar(100) NOT NULL COMMENT '工学号',
  `password` varchar(100) NOT NULL DEFAULT '123456' COMMENT '密码',
  `tel` varchar(100) NOT NULL COMMENT '电话',
  `colloge` varchar(100) NOT NULL COMMENT '学院',
  `grade` varchar(100) NOT NULL COMMENT '专业',
  `status` int(5) NOT NULL COMMENT '权限状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8
```
* `time`（用于记录导师的添加的时间和该时间的预约状态）
```mysql
CREATE TABLE `time` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `time` varchar(100) NOT NULL COMMENT '时间',
  `t_status` int(5) NOT NULL DEFAULT '0' COMMENT '预约状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8
```
* `result`（用于记录学生的预约信息和预约状态）
```mysql
CREATE TABLE `result` (
  `rid` int(11) NOT NULL AUTO_INCREMENT,
  `user_sid` varchar(100) NOT NULL COMMENT '学生sid',
  `time_id` int(11) NOT NULL COMMENT '导师时间表id',
  `stu_status` int(5) NOT NULL COMMENT '预约结果状态',
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8
```
### 代码模块分析：
##### 登录页面：
   这里没有注册页面，所有的用户信息都是先存入数据库的`user`表中，默认的起始密码为123456。登录的用户通过填写姓名、对应的学号和密码再由后端去数据库查找对应学号的用户状态（用户权限status：0代表学生，1代表导师，2代表管理员）从而进入不同的主页面。
##### 导师添加时间：
   将导师添加的时间插入到`time`表中，预约状态（`t_status`）初始化为0。对于每条时间的状态：学生预约但导师还未审批时，状态为1；学生预约且导师审批通过，状态为2；学生预约但导师审批不通过，状态回归为0。
##### 学生查询导师时间并预约
   后端查看`time`表中状态为0和1的时间并展示在前端页面，学生可以进行预约；学生进行预约时，会先在`result`表中查看预约结果状态（`stu_status`），（已预约未审批，状态为1；已预约且通过审批，状态为2；审批为通过，状态为0），如果`result`表中有该学生的学号记录并且状态`stu_status`为1，则会抛出自定义异常“您已有预约申请，待老师审核后才可继续预约！”，其他情况均可进行预约。
##### 导师处理学生的预约信息
   导师可以查看有哪些学生预约了自己，然后进行审批，审批成功后会将`time`和`result`表的状态进行修改
### 存在的问题：
* 登录后修改密码的功能未实现，安全性极低。。。
* 导师添加的时间可以的已经过去的时间，不合逻辑。。。
* 导师审批学生的预约，只有通过审批功能，没有取消功能
* 对于没有通过或没有预约的时间，定时清理
* 还有一大堆，以后有时间有能力再搞了......
   
