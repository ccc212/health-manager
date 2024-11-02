-- 健身管理模块
drop table if exists health_fitness_plan;
create table health_fitness_plan (
  plan_id           bigint(20)      not null auto_increment    comment '计划ID',
  user_id           bigint(20)      not null                   comment '用户ID',
  goals             text                                      comment '健身目标',
  current_status    text                                      comment '当前身体状况',
  create_time       datetime         default current_timestamp comment '创建时间',
  update_time       datetime                                   comment '更新时间',
  primary key (plan_id)
) engine=innodb auto_increment=100 comment = '健身计划表';

drop table if exists health_fitness_record;
create table health_fitness_record (
  record_id         bigint(20)      not null auto_increment    comment '记录ID',
  user_id           bigint(20)      not null                   comment '用户ID',
  activity          varchar(100)    not null                   comment '活动类型',
  duration          int             not null                   comment '活动时长（分钟）',
  record_date       date            not null                   comment '记录日期',
  create_time       datetime         default current_timestamp comment '创建时间',
  update_time       datetime                                   comment '更新时间',
  primary key (record_id)
) engine=innodb auto_increment=100 comment = '健身记录表';

-- 心理咨询模块
drop table if exists health_psychology_test;
create table health_psychology_test (
  test_id           bigint(20)      not null auto_increment    comment '测试ID',
  user_id           bigint(20)      not null                   comment '用户ID',
  answers           text                                      comment '测试答案',
  result            text                                      comment '测试结果',
  test_date         date            not null                   comment '测试日期',
  create_time       datetime         default current_timestamp comment '创建时间',
  update_time       datetime                                   comment '更新时间',
  primary key (test_id)
) engine=innodb auto_increment=100 comment = '心理测试表';

drop table if exists health_psychology_appointment;
create table health_psychology_appointment (
  appointment_id    bigint(20)      not null auto_increment    comment '预约ID',
  user_id           bigint(20)      not null                   comment '用户ID',
  appointment_date  date            not null                   comment '预约日期',
  appointment_time  time            not null                   comment '预约时间',
  status            char(1)         default '0'                comment '预约状态（0正常 1取消）',
  create_time       datetime         default current_timestamp comment '创建时间',
  update_time       datetime                                   comment '更新时间',
  primary key (appointment_id)
) engine=innodb auto_increment=100 comment = '心理咨询预约表';

-- 医疗服务模块
drop table if exists health_medical_advice;
create table health_medical_advice (
  advice_id         bigint(20)      not null auto_increment    comment '建议ID',
  user_id           bigint(20)      not null                   comment '用户ID',
  health_data       text                                      comment '健康数据',
  advice            text                                      comment '医疗建议',
  advice_date       date            not null                   comment '建议日期',
  create_time       datetime         default current_timestamp comment '创建时间',
  update_time       datetime                                   comment '更新时间',
  primary key (advice_id)
) engine=innodb auto_increment=100 comment = '医疗建议表';

drop table if exists health_medical_appointment;
create table health_medical_appointment (
  appointment_id    bigint(20)      not null auto_increment    comment '预约ID',
  user_id           bigint(20)      not null                   comment '用户ID',
  doctor_id         bigint(20)                                  comment '医生ID',
  appointment_date  date            not null                   comment '预约日期',
  status            char(1)         default '0'                comment '预约状态（0正常 1取消）',
  create_time       datetime         default current_timestamp comment '创建时间',
  update_time       datetime                                   comment '更新时间',
  primary key (appointment_id)
) engine=innodb auto_increment=100 comment = '医疗预约表';

-- 通知系统模块
drop table if exists health_notification;
create table health_notification (
  notification_id   bigint(20)      not null auto_increment    comment '通知ID',
  user_id           bigint(20)      not null                   comment '用户ID',
  type              varchar(50)     not null                   comment '通知类型',
  content           text                                      comment '通知内容',
  send_date         date            not null                   comment '发送日期',
  create_time       datetime         default current_timestamp comment '创建时间',
  update_time       datetime                                   comment '更新时间',
  primary key (notification_id)
) engine=innodb auto_increment=100 comment = '通知表';