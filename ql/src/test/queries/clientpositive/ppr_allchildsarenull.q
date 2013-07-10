set hive.optimize.ppd=true;

EXPLAIN EXTENDED
SELECT user_id 
FROM (
  SELECT 
  CAST(key AS INT) AS user_id
  ,CASE WHEN (value LIKE 'aaa%' OR value LIKE 'vvv%')
  THEN 1
  ELSE 0 END AS tag_student
  FROM srcpart
) sub
WHERE sub.tag_student > 0;

set hive.optimize.ppd=false;

EXPLAIN EXTENDED
SELECT user_id 
FROM (
  SELECT 
  CAST(key AS INT) AS user_id
  ,CASE WHEN (value LIKE 'aaa%' OR value LIKE 'vvv%')
  THEN 1
  ELSE 0 END AS tag_student
  FROM srcpart
) sub
WHERE sub.tag_student > 0;


set hive.optimize.ppd=true;

EXPLAIN EXTENDED
SELECT 
  CAST(key AS INT) AS user_id, value
  FROM srcpart
  WHERE ds='2008-04-08' and 
  ( CASE WHEN (value LIKE 'aaa%' OR value LIKE 'vvv%')
   THEN 1
   ELSE 0  end ) > 0
;

set hive.optimize.ppd=false;

EXPLAIN EXTENDED
SELECT 
  CAST(key AS INT) AS user_id, value
  FROM srcpart
  WHERE ds='2008-04-08' and 
  ( CASE WHEN (value LIKE 'aaa%' OR value LIKE 'vvv%')
   THEN 1
   ELSE 0  end ) > 0
;
