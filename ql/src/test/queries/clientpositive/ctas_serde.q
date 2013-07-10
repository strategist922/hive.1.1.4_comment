create table ctas_serde as
select if(key='',null,key) as key, if(value='',null,value) as value from src1;

select * from ctas_serde;

create table ctas_null_format          
row format serde 'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe'
as
select * from ctas_serde;

select * from ctas_null_format;

create table ctas_null_format1          
row format serde 'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe'
with serdeproperties('serialization.null.format'='NULL_bug')
as
select * from ctas_serde;

ALTER TABLE ctas_null_format1 SET SERDEPROPERTIES ('serialization.null.format'='\\N');

select * from ctas_null_format1;

drop table ctas_serde;
drop table ctas_null_format;
drop table ctas_null_format1;
