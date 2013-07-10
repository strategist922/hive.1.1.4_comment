-- see HIVE-1342

create table if not exists dm_fact_buyer_prd_info_d (
category_id string
,gmv_trade_num int
,user_id int
)
PARTITIONED BY (ds int);

set hive.optimize.ppd=true;
set hive.map.aggr=true;

explain select category_id1,category_id2,assoc_idx
from (
select 
category_id1
, category_id2
, count(distinct user_id) as assoc_idx
from (
select 
t1.category_id as category_id1
, t2.category_id as category_id2
, t1.user_id
from (
select category_id, user_id
from dm_fact_buyer_prd_info_d
group by category_id, user_id ) t1
join (
select category_id, user_id
from dm_fact_buyer_prd_info_d
group by category_id, user_id ) t2 on t1.user_id=t2.user_id 
) t11
group by category_id1, category_id2 ) t_o
where category_id1 <> category_id2
and assoc_idx > 2;

drop table dm_fact_buyer_prd_info_d;