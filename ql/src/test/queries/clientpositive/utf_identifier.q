drop table 中文;
drop table 测试;

create table if not exists 测试 (测试 string comment '测试');
show tables;
desc extended 测试;

alter table 测试 change 测试 中文 string comment '中文';
alter table 测试 rename to `中文`;
show tables;
desc extended 中文;
