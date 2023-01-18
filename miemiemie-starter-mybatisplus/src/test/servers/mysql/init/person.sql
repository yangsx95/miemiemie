/*!40101 SET NAMES utf8 */;
# 告诉mysql使用utf8字符集处理这些脚本而不是系统默认字符集

create table miemiemie.person
(
    id          bigint not null,
    pname        varchar(20) not null comment '姓名',
    gender      tinyint(1) null comment '性别(0 男 1 女)',
    belief      tinyint(1) null comment '信仰类型（0 无  1 基督  2 佛教 3 伊斯兰教 4 道教 5 其他）',
    create_time datetime null comment '创建时间',
    update_time datetime null comment '更新时间',
    deleted     tinyint(1) default 0 comment '删除状态 0未删除，1已删除',
    constraint person_pk primary key (id)
) comment '人';
