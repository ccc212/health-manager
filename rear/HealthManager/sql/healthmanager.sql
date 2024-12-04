create database if not exists health_manager;

use health_manager;

# -- 用户管理模块
# drop table if exists health_user;
# create table health_user
# (
#     user_id         bigint(20)   not null auto_increment comment '用户ID',
#     username        varchar(30)  not null comment '用户账号',
#     email           varchar(50)  comment '用户邮箱',
#     password        varchar(100) not null comment '密码',
#     name            varchar(30)  null comment '用户昵称',
#     phone_number    varchar(15)  default null comment '手机号码',
#     birth_date      date         default null comment '出生日期',
#     avatar          varchar(100) default null comment '头像地址',
#     status          char(1)      default '0' comment '账号状态（0正常 1停用）',
#     last_login_time datetime comment '上次登录时间',
#     create_time     datetime     default current_timestamp comment '创建时间',
#     update_time     datetime comment '更新时间',
#     primary key (user_id),
#     unique key (email),
#     unique key (username)
# ) engine = innodb
#   auto_increment = 100 comment = '用户信息表';
#
# INSERT INTO health_user (user_id, username, email, password, name, phone_number, birth_date, create_time)
# VALUES (1, 'ccc212account', 'ccc212@ccc212.cn', '745da0d242bb1c2817b1f7957e32e5f0', 'ccc212', '13000000000',
#         '2024-11-06', NOW());
#
# create table health_user_body_data
# (
#     user_id bigint(20)       not null comment '用户ID',
#     age     tinyint unsigned not null comment '年龄',
#     height  decimal(5, 2)    not null comment '身高',
#     weight  decimal(5, 2)    not null comment '体重',
#     gender  char(1) default '2' comment '性别（0男 1女 2未知）',
#     primary key (user_id),
#     foreign key (user_id) references health_user (user_id) on delete cascade
# ) engine = innodb comment = '用户身体数据表';
#
# INSERT INTO health_user_body_data (user_id, age, height, weight, gender)
# VALUES (1, 20, 175.00, 65.00, '0');

-- 健身管理模块
drop table if exists health_fitness_plan;
create table health_fitness_plan
(
    plan_id        bigint(20) not null auto_increment comment '计划ID',
    user_id        bigint(20) not null comment '用户ID',
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

INSERT INTO health_fitness_plan (user_id, goals, initial_status, progress, create_time)
VALUES (1, '增肌和减脂', '体脂率较高，肌肉量不足', 0, NOW());

drop table if exists health_exercise;
create table health_exercise
(
    exercise_id   bigint(20)   not null auto_increment comment '锻炼项目ID',
    exercise_name varchar(100) not null comment '锻炼项目名称',
    description   text comment '锻炼项目描述',
    primary key (exercise_id)
) engine = innodb
  auto_increment = 100 comment = '锻炼项目表';

INSERT INTO health_exercise (exercise_id, exercise_name, description)
VALUES (1, '俯卧撑', '主要锻炼胸大肌、三角肌前束和肱三头肌，通过身体的上下运动增强肌肉力量。'),
       (2, '深蹲', '主要锻炼股四头肌、臀大肌和腘绳肌，通过屈膝和伸膝动作增强腿部力量。'),
       (3, '引体向上', '主要锻炼背阔肌、肱二头肌和斜方肌中下部，通过悬挂和拉起身体增强肌肉力量。'),
       (4, '平板支撑', '主要锻炼腹直肌、腹横肌和肩部稳定肌群，通过保持身体平衡增强核心力量。'),
       (5, '仰卧起坐', '主要锻炼腹直肌和腹外斜肌，通过身体的卷曲动作增强腹部肌肉。'),
       (6, '弓步蹲', '主要锻炼股四头肌、臀大肌和腘绳肌，通过前后弓步动作增强腿部力量。'),
       (7, '跳绳', '主要锻炼心肺功能、腓肠肌和比目鱼肌，通过跳跃动作增强心肺耐力和协调性。'),
       (8, '骑自行车', '主要锻炼股四头肌、腘绳肌和心肺功能，通过骑行动作增强耐力和腿部力量。'),
       (9, '游泳', '主要锻炼背阔肌、胸大肌、三角肌和心肺功能，通过游泳动作增强耐力和协调性。'),
       (10, '瑜伽', '主要锻炼全身肌肉的柔韧性和平衡性，通过各种姿势增强柔韧性和平衡性。'),
       (11, '史密斯机深蹲', '在史密斯机上进行的深蹲练习，主要锻炼股四头肌、臀大肌和腘绳肌，提供更好的稳定性。'),
       (12, '高位下拉', '使用高位下拉机进行的练习，主要锻炼背阔肌、肱二头肌和斜方肌中下部，适合增强背部力量。'),
       (13, '坐姿推肩', '使用坐姿推肩机进行的练习，主要锻炼三角肌前束和肱三头肌，适合增强肩部力量。'),
       (14, '罗马椅背部伸展', '在罗马椅上进行的背部伸展练习，主要锻炼竖脊肌和下背部肌肉，适合增强背部力量和稳定性。'),
       (15, '椭圆机', '使用椭圆机进行的有氧运动，主要锻炼心肺功能、股四头肌和腘绳肌，适合增强耐力和协调性。'),
       (16, '跑步机', '使用跑步机进行的有氧运动，主要锻炼心肺功能、股四头肌和腘绳肌，适合增强耐力和心肺健康。'),
       (17, '动感单车', '使用动感单车进行的有氧运动，主要锻炼心肺功能、股四头肌和腘绳肌，适合增强耐力和腿部力量。'),
       (18, '腹肌板', '使用腹肌板进行的练习，主要锻炼腹直肌和腹外斜肌，适合增强腹部肌肉力量。'),
       (19, '绳索下压', '使用绳索进行的下压练习，主要锻炼肱三头肌，适合增强上臂后侧肌肉力量。'),
       (20, '上斜杠铃卧推', '在上斜板上进行的杠铃卧推练习，主要锻炼上胸肌和三角肌前束，增强上胸部力量。'),
       (21, '上斜哑铃飞鸟', '在上斜板上进行的哑铃飞鸟练习，主要锻炼上胸肌和胸中缝，塑造胸部线条。'),
       (22, '平板哑铃卧推', '在平板上进行的哑铃卧推练习，主要锻炼中胸肌和三角肌前束，增强胸部力量。'),
       (23, '下斜杠铃卧推', '在下斜板上进行的杠铃卧推练习，主要锻炼下胸肌和三角肌前束，增强下胸部力量。'),
       (24, '蝴蝶机夹胸', '使用蝴蝶机进行的夹胸练习，主要锻炼胸中缝和胸大肌，塑造胸部线条。'),
       (25, '哑铃前平举', '使用哑铃进行的前平举练习，主要锻炼肩前束，增强肩部力量。'),
       (26, '哑铃侧平举', '使用哑铃进行的侧平举练习，主要锻炼肩中束，增强肩部宽度。'),
       (27, '反向飞鸟', '使用哑铃或器械进行的反向飞鸟练习，主要锻炼肩后束和斜方肌，增强肩部后侧肌肉。'),
       (28, '杠铃耸肩', '使用杠铃进行的耸肩练习，主要锻炼斜方肌上部，增强肩部和颈部肌肉。'),
       (29, '单臂哑铃划船', '使用哑铃进行的单臂划船练习，主要锻炼大圆肌和背阔肌，增强背部厚度。'),
       (30, '腿弯举', '使用腿弯举机进行的练习，主要锻炼腘绳肌，增强腿部后侧肌肉力量。'),
       (31, '腿屈伸', '使用腿屈伸机进行的练习，主要锻炼股四头肌，增强腿部前侧肌肉力量。'),
       (32, '小腿提踵', '使用提踵机进行的练习，主要锻炼腓肠肌，增强小腿肌肉力量。'),
       (33, '坐姿提踵', '在坐姿下进行的提踵练习，主要锻炼比目鱼肌，增强小腿肌肉力量。'),
       (34, '杠铃弯举', '使用杠铃进行的弯举练习，主要锻炼肱二头肌，增强上臂前侧肌肉力量。'),
       (35, '窄距卧推', '使用杠铃进行的窄距卧推练习，主要锻炼肱三头肌，增强上臂后侧肌肉力量。'),
       (36, '锤式弯举', '使用哑铃进行的锤式弯举练习，主要锻炼肱肌和肱二头肌，增强上臂肌肉力量。'),
       (37, '腕弯举', '使用哑铃进行的腕弯举练习，主要锻炼前臂肌肉，增强前臂力量。'),
       (38, '仰卧卷腹', '通过卷曲上半身来锻炼上腹肌，增强腹部肌肉力量。'),
       (39, '悬垂举腿', '通过悬挂和抬腿动作来锻炼下腹肌，增强腹部下侧肌肉力量。'),
       (40, '俄罗斯转体', '通过旋转上半身来锻炼腹斜肌，增强腹部侧面肌肉力量。'),
       (41, '臀桥', '通过抬起臀部来锻炼臀大肌，增强臀部肌肉力量。'),
       (42, '侧卧抬腿', '通过侧卧抬腿动作来锻炼臀中肌，增强臀部侧面肌肉力量。'),
       (43, '站姿髋外展', '通过髋外展动作来锻炼臀小肌，增强臀部外侧肌肉力量。');

drop table if exists health_body_part;
create table health_body_part
(
    part_id   bigint(20)   not null auto_increment comment '部位ID',
    part_name varchar(100) not null comment '部位名称',
    primary key (part_id)
) engine = innodb comment = '锻炼部位表';

INSERT INTO health_body_part (part_id, part_name)
VALUES
-- 胸部细分
(1, '上胸'),
(2, '中胸'),
(3, '下胸'),
(4, '胸中缝'),

-- 肩部细分
(5, '肩前束'),
(6, '肩中束'),
(7, '肩后束'),

-- 背部细分
(8, '斜方肌'),
(9, '大圆肌'),
(10, '背阔肌'),

-- 腿部细分
(11, '腘绳肌'),
(12, '股四头肌'),
(13, '腓肠肌'),
(14, '比目鱼肌'),

-- 手臂细分
(15, '肱二头肌'),
(16, '肱三头肌'),
(17, '肱肌'),
(18, '前臂'),

-- 腹部细分
(19, '上腹'),
(20, '下腹'),
(21, '腹斜肌'),

-- 臀部细分
(22, '臀大肌'),
(23, '臀中肌'),
(24, '臀小肌');

drop table if exists health_exercise_body_part;
create table health_exercise_body_part
(
    exercise_id bigint(20) not null comment '锻炼项目ID',
    part_id     bigint(20) not null comment '部位ID',
    primary key (exercise_id, part_id),
    foreign key (exercise_id) references health_exercise (exercise_id) on delete cascade,
    foreign key (part_id) references health_body_part (part_id) on delete cascade
) engine = innodb comment = '锻炼项目与部位关联表';

INSERT INTO health_exercise_body_part (exercise_id, part_id)
VALUES
-- 俯卧撑
(1, 1),   -- 上胸
(1, 2),   -- 中胸
(1, 16),  -- 肱三头肌

-- 深蹲
(2, 12),  -- 股四头肌
(2, 11),  -- 腘绳肌
(2, 22),  -- 臀大肌

-- 引体向上
(3, 10),  -- 背阔肌
(3, 15),  -- 肱二头肌
(3, 16),  -- 肱三头肌

-- 平板支撑
(4, 19),  -- 上腹
(4, 20),  -- 下腹
(4, 5),   -- 肩前束

-- 仰卧起坐
(5, 19),  -- 上腹
(5, 20),  -- 下腹

-- 弓步蹲
(6, 12),  -- 股四头肌
(6, 11),  -- 腘绳肌
(6, 22),  -- 臀大肌

-- 跳绳
(7, 12),  -- 股四头肌
(7, 11),  -- 腘绳肌
(7, 13),  -- 腓肠肌

-- 骑自行车
(8, 12),  -- 股四头肌
(8, 11),  -- 腘绳肌

-- 游泳
(9, 10),  -- 背阔肌
(9, 1),   -- 上胸
(9, 16),  -- 肱三头肌

-- 瑜伽
(10, 1),  -- 上胸
(10, 5),  -- 肩前束
(10, 19), -- 上腹
(10, 22), -- 臀大肌

-- 史密斯机深蹲
(11, 12), -- 股四头肌
(11, 11), -- 腘绳肌
(11, 22), -- 臀大肌

-- 高位下拉
(12, 10), -- 背阔肌
(12, 15), -- 肱二头肌

-- 坐姿推肩
(13, 5),  -- 肩前束
(13, 16), -- 肱三头肌

-- 罗马椅背部伸展
(14, 8),  -- 斜方肌

-- 椭圆机
(15, 12), -- 股四头肌
(15, 11), -- 腘绳肌

-- 跑步机
(16, 12), -- 股四头肌
(16, 11), -- 腘绳肌

-- 动感单车
(17, 12), -- 股四头肌
(17, 11), -- 腘绳肌

-- 腹肌板
(18, 19), -- 上腹
(18, 20), -- 下腹

-- 绳索下压
(19, 16), -- 肱三头肌

-- 上斜杠铃卧推
(20, 1),  -- 上胸
(20, 5),  -- 肩前束

-- 上斜哑铃飞鸟
(21, 1),  -- 上胸
(21, 4),  -- 胸中缝

-- 平板哑铃卧推
(22, 2),  -- 中胸
(22, 5),  -- 肩前束

-- 下斜杠铃卧推
(23, 3),  -- 下胸
(23, 5),  -- 肩前束

-- 蝴蝶机夹胸
(24, 4),  -- 胸中缝
(24, 2),  -- 中胸

-- 哑铃前平举
(25, 5),  -- 肩前束

-- 哑铃侧平举
(26, 6),  -- 肩中束

-- 反向飞鸟
(27, 8),  -- 斜方肌
(27, 6),  -- 肩中束

-- 杠铃耸肩
(28, 8),  -- 斜方肌

-- 单臂哑铃划船
(29, 10), -- 背阔肌
(29, 8),  -- 斜方肌

-- 腿弯举
(30, 11), -- 腘绳肌

-- 腿屈伸
(31, 12), -- 股四头肌

-- 小腿提踵
(32, 13), -- 腓肠肌

-- 坐姿提踵
(33, 14), -- 比目鱼肌

-- 杠铃弯举
(34, 15), -- 肱二头肌

-- 窄距卧推
(35, 16), -- 肱三头肌

-- 锤式弯举
(36, 17), -- 肱肌

-- 腕弯举
(37, 18), -- 前臂

-- 仰卧卷腹
(38, 19), -- 上腹

-- 悬垂举腿
(39, 20), -- 下腹

-- 俄罗斯转体
(40, 21), -- 腹斜肌

-- 臀桥
(41, 22), -- 臀大肌

-- 侧卧抬腿
(42, 23), -- 臀中肌

-- 站姿髋外展
(43, 24); -- 臀小肌

drop table if exists health_fitness_plan_detail;
create table health_fitness_plan_detail
(
    detail_id   bigint(20)          not null auto_increment comment '计划详情ID',
    plan_id     bigint(20)          not null comment '计划ID',
    exercise_id bigint(20)          not null comment '锻炼项目ID',
    duration    int                 not null comment '锻炼时长（分钟）',
    intensity   char(1) default '0' not null comment '锻炼强度（0低 1中 2高）',
    frequency   int                 not null comment '锻炼频率（天/周）',
    primary key (detail_id),
    foreign key (plan_id) references health_fitness_plan (plan_id),
    foreign key (exercise_id) references health_exercise (exercise_id)
) engine = innodb
  auto_increment = 100 comment = '健身计划详情表';

drop table if exists health_fitness_record;
create table health_fitness_record
(
    record_id   bigint(20) not null auto_increment comment '记录ID',
    user_id     bigint(20) not null comment '用户ID',
    exercise_id bigint(20) not null comment '锻炼项目ID',
    duration    int        not null comment '活动时长（分钟）',
    calories    decimal(5, 2) comment '消耗的卡路里',
    record_date date       not null comment '记录日期',
    create_time datetime default current_timestamp comment '创建时间',
    update_time datetime comment '更新时间',
    primary key (record_id),
    foreign key (user_id) references health_user (user_id)
) engine = innodb
  auto_increment = 100 comment = '健身记录表';

-- 心理咨询模块
drop table if exists health_psychology_question;
create table health_psychology_question
(
    question_id   bigint(20)   not null auto_increment comment '问题ID',
    question_text varchar(500) not null comment '问题内容',
    create_time   datetime     default current_timestamp comment '创建时间',
    update_time   datetime comment '更新时间',
    primary key (question_id)
) engine = innodb
  auto_increment = 100 comment = '心理测试题目表';

INSERT INTO health_psychology_question (question_id, question_text) VALUES 
(1, '我因一些事而烦恼'),
(2, '胃口不好，不大想吃东西'),
(3, '心里觉得苦闷，难以消除'),
(4, '总觉得自己不如别人'),
(5, '做事时无法集中精力'),
(6, '自觉情绪低沉'),
(7, '做任何事情都觉得费力'),
(8, '觉得前途没有希望'),
(9, '觉得自己的生活是失败的'),
(10, '感到害怕'),
(11, '睡眠不好'),
(12, '高兴不起来'),
(13, '说话比往常少了'),
(14, '感到孤单'),
(15, '人们对我不太友好'),
(16, '觉得生活没有意思'),
(17, '曾哭泣过'),
(18, '感到忧愁'),
(19, '觉得人们不喜欢我'),
(20, '无法继续日常工作');

drop table if exists health_psychology_option;
create table health_psychology_option
(
    option_id   bigint(20)   not null auto_increment comment '选项ID',
    option_name varchar(10)  not null comment '选项标识',
    question_id bigint(20)   not null comment '问题ID',
    option_text varchar(500) not null comment '选项内容',
    score       decimal(5,2) not null comment '选项分数',
    create_time datetime     default current_timestamp comment '创建时间',
    update_time datetime comment '更新时间',
    primary key (option_id),
    foreign key (question_id) references health_psychology_question (question_id)
) engine = innodb
  auto_increment = 100 comment = '心理测试选项表';

INSERT INTO health_psychology_option (question_id, option_name, option_text, score)
SELECT 
    q.question_id,
    'A',
    '最近一周内出现这种情况的日子不超过一天',
    0.00
FROM health_psychology_question q
UNION ALL
SELECT 
    q.question_id,
    'B',
    '最近一周内曾有1-2天出现这种情况',
    1.00
FROM health_psychology_question q
UNION ALL
SELECT 
    q.question_id,
    'C',
    '最近一周内曾有3-4天出现这种情况',
    2.00
FROM health_psychology_question q
UNION ALL
SELECT 
    q.question_id,
    'D',
    '最近一周内曾有5-7天出现这种情况',
    3.00
FROM health_psychology_question q;

drop table if exists health_psychology_test;
create table health_psychology_test
(
    test_id     bigint(20)   not null auto_increment comment '测试ID',
    user_id     bigint(20)   not null comment '用户ID',
    total_score decimal(5,2) not null comment '总分',
    ai_analysis text comment 'AI分析报告',
    test_date   date         not null comment '测试日期',
    create_time datetime     default current_timestamp comment '创建时间',
    update_time datetime comment '更新时间',
    primary key (test_id),
    foreign key (user_id) references health_user (user_id)
) engine = innodb
  auto_increment = 100 comment = '心理测试记录表';

# drop table if exists health_counselor;
# create table health_counselor
# (
#     counselor_id   bigint(20)   not null auto_increment comment '咨询师ID',
#     name           varchar(30)  not null comment '咨询师姓名',
#     title          varchar(50)  not null comment '职称',
#     introduction   text comment '个人简介',
#     specialization varchar(200) comment '专长领域',
#     avatar         varchar(100) comment '头像地址',
#     status         char(1)      default '0' comment '状态（0在职 1离职）',
#     create_time    datetime     default current_timestamp comment '创建时间',
#     update_time    datetime comment '更新时间',
#     primary key (counselor_id)
# ) engine = innodb
#   auto_increment = 100 comment = '心理咨询师表';
#
# INSERT INTO health_counselor (name, title, introduction, specialization) VALUES
# ('张医生', '高级心理咨询师', '从事心理咨询工作10年，擅长处理抑郁、焦虑等问题', '抑郁症,焦虑症,人际关系'),
# ('李医生', '心理治疗师', '具有丰富的青少年心理辅导经验', '青少年心理,家庭关系,学习压力');
#
# drop table if exists health_counseling_schedule;
# create table health_counseling_schedule
# (
#     schedule_id  bigint(20) not null auto_increment comment '时段ID',
#     counselor_id bigint(20) not null comment '咨询师ID',
#     date         date       not null comment '日期',
#     time_slot    time       not null comment '时间段',
#     status       char(1)    default '0' comment '状态（0可预约 1已预约 2停诊）',
#     create_time  datetime   default current_timestamp comment '创建时间',
#     update_time  datetime comment '更新时间',
#     primary key (schedule_id),
#     foreign key (counselor_id) references health_counselor (counselor_id)
# ) engine = innodb
#   auto_increment = 100 comment = '咨询时段表';
#
# drop table if exists health_counseling_appointment;
# create table health_counseling_appointment
# (
#     appointment_id bigint(20) not null auto_increment comment '预约ID',
#     user_id       bigint(20) not null comment '用户ID',
#     counselor_id  bigint(20) not null comment '咨询师ID',
#     schedule_id   bigint(20) not null comment '时段ID',
#     topic         varchar(200) comment '咨询主题',
#     status        char(1)    default '0' comment '预约状态（0待确认 1已确认 2已完成 3已取消）',
#     create_time   datetime   default current_timestamp comment '创建时间',
#     update_time   datetime comment '更新时间',
#     primary key (appointment_id),
#     foreign key (user_id) references health_user (user_id),
#     foreign key (counselor_id) references health_counselor (counselor_id),
#     foreign key (schedule_id) references health_counseling_schedule (schedule_id)
# ) engine = innodb
#   auto_increment = 100 comment = '咨询预约表';

drop table if exists health_psychology_ai_counseling;
create table health_psychology_ai_counseling
(
    record_id    bigint(20) not null auto_increment comment '记录ID',
    user_id      bigint(20) not null comment '用户ID',
    question     text       not null comment '用户问题',
    answer       text       comment 'AI回答',
    create_time  datetime   default current_timestamp comment '创建时间',
    update_time  datetime comment '更新时间',
    primary key (record_id),
    foreign key (user_id) references health_user (user_id)
) engine = innodb
  auto_increment = 100 comment = 'AI咨询记录表';