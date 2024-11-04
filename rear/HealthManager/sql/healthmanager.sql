-- 用户管理模块
drop table if exists health_user;
create table health_user
(
    user_id         bigint(20)   not null auto_increment comment '用户ID',
    user_name       varchar(30)  not null comment '用户账号',
    email           varchar(50)  not null comment '用户邮箱',
    password        varchar(100) not null comment '密码',
    name            varchar(30)  null comment '用户昵称',
    phone_number    varchar(15)  default null comment '手机号码',
    gender          char(1)      default '2' comment '性别（0男 1女 2未知）',
    birth_date      date         default null comment '出生日期',
    avatar          varchar(100) default null comment '头像地址',
    status          char(1)      default '0' comment '账号状态（0正常 1停用）',
    last_login_time datetime comment '上次登录时间',
    create_time     datetime     default current_timestamp comment '创建时间',
    update_time     datetime comment '更新时间',
    primary key (user_id),
    unique key (email),
    unique key (user_name)
) engine = innodb
  auto_increment = 100 comment = '用户信息表';

-- 健身管理模块
drop table if exists health_fitness_plan;
create table health_fitness_plan
(
    plan_id        bigint(20) not null auto_increment comment '计划ID',
    user_id        bigint(20) not null comment '用户ID',
    age            tinyint unsigned comment '年龄',
    height         decimal(5, 2) comment '身高',
    weight         decimal(5, 2) comment '体重',
    gender         char(1) comment '性别',
    goals          text comment '健身目标',
    initial_status text comment '最初身体状况',
    current_status text comment '当前身体状况',
    progress       tinyint unsigned default 0 comment '完成进度',
    create_time    datetime         default current_timestamp comment '创建时间',
    update_time    datetime comment '更新时间',
    primary key (plan_id),
    foreign key (user_id) references health_user (user_id)
) engine = innodb
  auto_increment = 100 comment = '健身计划表';

drop table if exists health_exercise;
create table health_exercise
(
    exercise_id bigint(20)   not null auto_increment comment '锻炼项目ID',
    name        varchar(100) not null comment '锻炼项目名称',
    description text comment '锻炼项目描述',
    primary key (exercise_id)
) engine = innodb
  auto_increment = 100 comment = '锻炼项目表';

drop table if exists health_fitness_plan_detail;
create table health_fitness_plan_detail
(
    detail_id   bigint(20) not null auto_increment comment '计划详情ID',
    plan_id     bigint(20) not null comment '计划ID',
    exercise_id bigint(20) not null comment '锻炼项目ID',
    duration    int        not null comment '锻炼时长（分钟）',
    intensity   char(1) default '0' comment '锻炼强度（0低 1中 2高）',
    primary key (detail_id),
    foreign key (plan_id) references health_fitness_plan (plan_id),
    foreign key (exercise_id) references health_exercise (exercise_id)
) engine = innodb
  auto_increment = 100 comment = '健身计划详情表';

drop table if exists health_fitness_record;
create table health_fitness_record
(
    record_id   bigint(20)   not null auto_increment comment '记录ID',
    user_id     bigint(20)   not null comment '用户ID',
    exercise    varchar(100) not null comment '活动类型',
    duration    int          not null comment '活动时长（分钟）',
    calories    decimal(5, 2) comment '消耗的卡路里',
    record_date date         not null comment '记录日期',
    create_time datetime default current_timestamp comment '创建时间',
    update_time datetime comment '更新时间',
    primary key (record_id),
    foreign key (user_id) references health_user (user_id)
) engine = innodb
  auto_increment = 100 comment = '健身记录表';

-- 心理咨询模块
drop table if exists health_psychology_test;
create table health_psychology_test
(
    test_id     bigint(20) not null auto_increment comment '测试ID',
    user_id     bigint(20) not null comment '用户ID',
    answers     text comment '测试答案',
    result      text comment '测试结果',
    test_date   date       not null comment '测试日期',
    create_time datetime default current_timestamp comment '创建时间',
    update_time datetime comment '更新时间',
    primary key (test_id),
    foreign key (user_id) references health_user (user_id)
) engine = innodb
  auto_increment = 100 comment = '心理测试表';

drop table if exists health_psychology_appointment;
create table health_psychology_appointment
(
    appointment_id   bigint(20) not null auto_increment comment '预约ID',
    user_id          bigint(20) not null comment '用户ID',
    appointment_date date       not null comment '预约日期',
    appointment_time time       not null comment '预约时间',
    status           char(1)  default '0' comment '预约状态（0正常 1取消）',
    create_time      datetime default current_timestamp comment '创建时间',
    update_time      datetime comment '更新时间',
    primary key (appointment_id),
    foreign key (user_id) references health_user (user_id)
) engine = innodb
  auto_increment = 100 comment = '心理咨询预约表';

-- 医疗服务模块
drop table if exists health_medical_advice;
create table health_medical_advice
(
    advice_id   bigint(20) not null auto_increment comment '建议ID',
    user_id     bigint(20) not null comment '用户ID',
    health_data text comment '健康数据',
    advice      text comment '医疗建议',
    advice_date date       not null comment '建议日期',
    create_time datetime default current_timestamp comment '创建时间',
    update_time datetime comment '更新时间',
    primary key (advice_id),
    foreign key (user_id) references health_user (user_id)
) engine = innodb
  auto_increment = 100 comment = '医疗建议表';

drop table if exists health_medical_appointment;
create table health_medical_appointment
(
    appointment_id   bigint(20) not null auto_increment comment '预约ID',
    user_id          bigint(20) not null comment '用户ID',
    doctor_id        bigint(20) comment '医生ID',
    appointment_date date       not null comment '预约日期',
    status           char(1)  default '0' comment '预约状态（0正常 1取消）',
    create_time      datetime default current_timestamp comment '创建时间',
    update_time      datetime comment '更新时间',
    primary key (appointment_id),
    foreign key (user_id) references health_user (user_id)
) engine = innodb
  auto_increment = 100 comment = '医疗预约表';

-- 通知系统模块
drop table if exists health_notification;
create table health_notification
(
    notification_id bigint(20)  not null auto_increment comment '通知ID',
    user_id         bigint(20)  not null comment '用户ID',
    type            varchar(50) not null comment '通知类型',
    content         text comment '通知内容',
    send_date       date        not null comment '发送日期',
    create_time     datetime default current_timestamp comment '创建时间',
    update_time     datetime comment '更新时间',
    primary key (notification_id),
    foreign key (user_id) references health_user (user_id)
) engine = innodb
  auto_increment = 100 comment = '通知表';