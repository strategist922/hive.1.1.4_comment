set hive.exec.pre.hooks=;
set hive.exec.post.hooks=;
set hive.exec.dynamic.partition.mode=nonstrict;
set hive.exec.dynamic.partition=true;
set mapred.reduce.tasks=1;
set hive.exec.max.dynamic.partitions.pernode=1;
set hive.task.progress=true;

show partitions srcpart;

drop table nzhang_part1;

create table if not exists nzhang_part1 like srcpart;
describe extended nzhang_part1;

explain
from srcpart
insert overwrite table nzhang_part1 partition (ds, hr) select key, value, ds, hr;

from srcpart
insert overwrite table nzhang_part1 partition (ds, hr) select key, value, ds, hr;

show partitions nzhang_part1;

select * from nzhang_part1 where ds is not null and hr is not null;

drop table nzhang_part1;
