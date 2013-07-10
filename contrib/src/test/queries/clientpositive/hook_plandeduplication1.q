set hive.semantic.analyzer.hook=com.taobao.data.hive.hook.PlanDeDuplicateSemanticAnalyzerHook;
set hive.clean.hooks=com.taobao.data.hive.hook.PlanDeDuplicateCleanHook;

EXPLAIN
SELECT src1.key as k1, src1.value as v1, 
       src2.key as k2, src2.value as v2 FROM 
  (SELECT * FROM src WHERE src.key < 10) src1 
    JOIN 
  (SELECT * FROM src WHERE src.key < 10) src2
  SORT BY k1, v1, k2, v2;

SELECT src1.key as k1, src1.value as v1, 
       src2.key as k2, src2.value as v2 FROM 
  (SELECT * FROM src WHERE src.key < 10) src1 
    JOIN 
  (SELECT * FROM src WHERE src.key < 10) src2
  SORT BY k1, v1, k2, v2;

-- duplicated query
EXPLAIN 
SELECT src1.key as k1, src1.value as v1, 
       src2.key as k2, src2.value as v2 FROM 
  (SELECT * FROM src WHERE src.key < 10) src1 
    JOIN 
  (SELECT * FROM src WHERE src.key < 10) src2
  SORT BY k1, v1, k2, v2;

SELECT src1.key as k1, src1.value as v1, 
       src2.key as k2, src2.value as v2 FROM 
  (SELECT * FROM src WHERE src.key < 10) src1 
    JOIN 
  (SELECT * FROM src WHERE src.key < 10) src2
  SORT BY k1, v1, k2, v2;

-- duplicated partially, switched the sequence of select expressions
EXPLAIN 
SELECT src1.value as v1, src1.key as k1, 
       src2.key as k2, src2.value as v2 FROM 
  (SELECT * FROM src WHERE src.key < 10) src1 
    JOIN 
  (SELECT * FROM src WHERE src.key < 10) src2
  SORT BY k1, v1, k2, v2;

SELECT src1.value as v1, src1.key as k1, 
       src2.key as k2, src2.value as v2 FROM 
  (SELECT * FROM src WHERE src.key < 10) src1 
    JOIN 
  (SELECT * FROM src WHERE src.key < 10) src2
  SORT BY k1, v1, k2, v2;

ADD FILE ../data/files/kv1.txt;
-- duplicated plan, modified binary
EXPLAIN 
SELECT src1.key as k1, src1.value as v1, 
       src2.key as k2, src2.value as v2 FROM 
  (SELECT * FROM src WHERE src.key < 10) src1 
    JOIN 
  (SELECT * FROM src WHERE src.key < 10) src2
  SORT BY k1, v1, k2, v2;

SELECT src1.value as v1, src1.key as k1, 
       src2.key as k2, src2.value as v2 FROM 
  (SELECT * FROM src WHERE src.key < 10) src1 
    JOIN 
  (SELECT * FROM src WHERE src.key < 10) src2
  SORT BY k1, v1, k2, v2;

ALTER TABLE src ADD COLUMNS (extra int);
-- duplicated plan, modified metadta
EXPLAIN 
SELECT src1.key as k1, src1.value as v1, 
       src2.key as k2, src2.value as v2 FROM 
  (SELECT * FROM src WHERE src.key < 10) src1 
    JOIN 
  (SELECT * FROM src WHERE src.key < 10) src2
  SORT BY k1, v1, k2, v2;

SELECT src1.value as k1, src1.key as v1, 
       src2.key as k2, src2.value as v2 FROM 
  (SELECT * FROM src WHERE src.key < 10) src1 
    JOIN 
  (SELECT * FROM src WHERE src.key < 10) src2
  SORT BY k1, v1, k2, v2;

