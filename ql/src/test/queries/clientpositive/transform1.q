drop table transform1_t1;
create table transform1_t1(a string, b string);

EXPLAIN
SELECT transform(*) USING 'cat' AS (col array<bigint>) FROM transform1_t1;

SELECT transform(*) USING 'cat' AS (col array<bigint>) FROM transform1_t1;

drop table transform1_t1;

drop table transform1_t2;
create table transform1_t2(col array<int>);

insert overwrite table transform1_t2
select array(1,2,3) from src limit 1;

EXPLAIN
SELECT transform('0\0021\0022') USING 'cat' AS (col array<int>) FROM transform1_t2;

SELECT transform('0\0021\0022') USING 'cat' AS (col array<int>) FROM transform1_t2;

drop table transform1_t2;

