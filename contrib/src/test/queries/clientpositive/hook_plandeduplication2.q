set hive.semantic.analyzer.hook=com.taobao.data.hive.hook.PlanDeDuplicateSemanticAnalyzerHook;
set hive.clean.hooks=com.taobao.data.hive.hook.PlanDeDuplicateCleanHook;
set hive.merge.mapfiles=false;

CREATE TABLE IF NOT EXISTS srcins like src;
CREATE TABLE IF NOT EXISTS srcins1 like src;

EXPLAIN 
INSERT OVERWRITE TABLE srcins
SELECT concat('ins ', key), value from src;

INSERT OVERWRITE TABLE srcins
SELECT concat('ins ', key), value from src;

SELECT * FROM srcins;

-- duplicated
EXPLAIN 
INSERT OVERWRITE TABLE srcins1
SELECT concat('ins ', key), value from src;

INSERT OVERWRITE TABLE srcins1
SELECT concat('ins ', key), value from src;

SELECT * FROM srcins1;
