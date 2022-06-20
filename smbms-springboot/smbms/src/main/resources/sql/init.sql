create database if not exists smbms;
use smbms;

drop table if exists coupon;
drop table if exists provider;
drop table if exists shopping_cart;
drop table if exists sys_goods;
drop table if exists sys_order;
drop table if exists sys_role;
drop table if exists sys_user;
drop table if exists sys_user_role;

create table coupon
(
    id      bigint      not null auto_increment
        primary key,
    price   float       not null,
    comment varchar(40) not null,
    user_id bigint      not null
);
insert into coupon
(id, price, comment, user_id) VALUES
(1,10,'减免10元',1),
(2,20,'减免20元',2),
(3,30,'减免30元',3),
(5,40,'减免40元',4),
(6,50,'减免50元',5),
(7,60,'减免60元',6),
(8,70,'减免70元',7),
(9,80,'减免80元',8);


create table provider
(
    id      bigint      not null auto_increment
        primary key,
    code    varchar(40) not null,
    name    varchar(40) not null,
    `desc`  varchar(40) not null,
    phone   varchar(40) not null,
    address varchar(40) not null
);
insert into provider
(id, code, name, `desc`, phone, address) VALUES
(1,'code','小明商城','小明家开的商场',13770808114,10084),
(2,'code','小王商城','小王家开的商场',13770808115,10085),
(3,'code','小东商城','小东家开的商场',13770808116,10086),
(4,'code','小西商城','小西家开的商场',13770808117,10087),
(5,'code','小南商城','小南家开的商场',13770808118,10088),
(6,'code','小北商城','小北家开的商场',13770808119,10089),
(7,'code','小红商城','小红家开的商场',13770808110,10080);


create table shopping_cart
(
    id       bigint not null auto_increment
        primary key,
    user_id  bigint not null,
    goods_id bigint not null
);

create table sys_goods
(
    id          bigint      not null auto_increment
        primary key,
    name        varchar(20) not null,
    price       float       not null,
    cover_pic   varchar(255) not null,
    comment     varchar(40) not null,
    brand       varchar(40) not null,
    provider_id bigint      not null,
    stock       bigint      not null
);

insert into sys_goods
(id, name, price, cover_pic, comment, brand, provider_id, stock)
values
(1,'大米',10,'http://pixel-revolve.test.upcdn.net/images/%E4%B8%9C%E7%99%BE%E5%A4%A7%E7%B1%B3.jpg','东百大米','和米饭',1,1000),
(2,'薯片',100,'http://pixel-revolve.test.upcdn.net/images/%E4%B9%90%E4%BA%8B%E8%96%AF%E7%89%87.jpg','德克萨斯烤肉味','乐事',2,1000),
(3,'T恤',17,'http://pixel-revolve.test.upcdn.net/images/T%E6%81%A4.png','很时髦','小毛衣物',1,1000),
(4,'乒乓球拍',15,'http://pixel-revolve.test.upcdn.net/images/%E7%BA%A2%E5%8F%8C%E5%96%9C%E4%B9%92%E4%B9%93%E7%90%83%E6%8B%8D.jpg','质量好','红双喜',1,1000),
(5,'羽毛球',3,'http://pixel-revolve.test.upcdn.net/images/%E7%BE%BD%E6%AF%9B%E7%90%83.png','3个装','优尼科斯',1,1000),
(6,'毛巾',14,'http://pixel-revolve.test.upcdn.net/images/%E6%AF%9B%E5%B7%BE.jpg','软且柔顺','小明毛巾',3,1000),
(7,'德芙巧克力',7,'http://pixel-revolve.test.upcdn.net/images/%E5%BE%B7%E8%8A%99%E5%B7%A7%E5%85%8B%E5%8A%9B.jpg','纵享新丝滑','德芙',3,1000);


create table sys_order
(
    id             bigint      not null auto_increment
        primary key,
    user_id        bigint      not null,
    goods_id       bigint      not null,
    order_date     datetime    not null,
    number         bigint      not null,
    date_needed    datetime    not null,
    pay_method     varchar(40) not null,
    deliver_method varchar(40) not null
);

insert into sys_order
(id, user_id, goods_id, order_date,number, date_needed, pay_method, deliver_method) values
(1,100001,10010001,'2020-02-18 12:20:00',2,'2020-02-20','支付宝','客户自提'),
(2,100001,30010001,'2020-02-10 12:30:00',10,'2020-02-20','网银转账','送货上门'),
(3,100002,10010001,'2020-02-18 13:00:00',1,'2020-02-21','微信支付','客户自提'),
(4,100002,50020001,'2020-02-18 13:20:00',1,'2020-02-21','微信支付','客户自提'),
(5,100004,020180002,'2020-02-19 10:00:00',1,'2020-02-28','信用卡','送货上门'),
(6,100004,50020002,'2020-02-19 10:40:00',2,'2020-02-28','信用卡','送货上门'),
(7,100004,30010002,'2020-02-19 11:00:00',10,'2020-02-28','信用卡','送货上门'),
(8,100005,40010001,'2020-02-20 08:00:00',2,'2020-02-27','支付宝','送货上门'),
(9,100005,40010002,'2020-02-20 08:20:00',3,'2020-02-27','支付宝','送货上门'),
(10,100006,10020001,'2020-02-23 09:00:00',5,'2020-02-26','信用卡','送货上门');

create table sys_role
(
    role_id     bigint      not null auto_increment
        primary key,
    role_name   varchar(40) not null,
    description varchar(40)
);
insert into sys_role
(role_id, role_name,description)
values
(1,'admin','系统管理员'),
(2,'user','普通用户');


create table sys_user
(
    id       bigint      not null auto_increment
        primary key,
    username varchar(40) not null,
    password varchar(40) not null,
    nickname varchar(40) not null,
    phone    varchar(40) not null,
    email    varchar(40) not null,
    avatar   varchar(255) not null default 'http://pixel-revolve.test.upcdn.net/images/avatar/default.png'
);

insert into sys_user
(id,username, password, nickname, phone, email, avatar)
values
(1,'dyh123456',123456,'pixel-revolve',13770808113,'pixel-revolve@qq.com','http://pixel-revolve.test.upcdn.net/images/avatar/%E5%90%83%E9%A5%BC.png'),
(2,'chy123456',123456,'chy',13770808113,'chy@qq.com','http://pixel-revolve.test.upcdn.net/images/avatar/682E28F2ED13A4BD3BC4876B8FD3EFA8.jpg'),
(3,'shr123456',123456,'shr',13770808113,'shr@qq.com','http://pixel-revolve.test.upcdn.net/images/avatar/38CJX%7DS%5B4N1SQV1Z%609)Q%241G.png');

create table sys_user_role
(
    user_id     bigint                             not null comment '用户ID',
    role_id     bigint                             not null comment '角色ID',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
);
insert into sys_user_role
(user_id, role_id) VALUES
(1,1),
(1,2),
(2,2),
(3,2);