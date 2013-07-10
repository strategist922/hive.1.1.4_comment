add jar ../build/contrib/hive_contrib.jar;

CREATE TEMPORARY FUNCTION example_max AS 'org.apache.hadoop.hive.contrib.udaf.example.UDAFExampleMax';

DESCRIBE FUNCTION EXTENDED example_max;

EXPLAIN
SELECT example_max(substr(value,5)),
       example_max(IF(substr(value,5) > 250, NULL, substr(value,5)))
FROM src;

SELECT example_max(substr(value,5)),
       example_max(IF(substr(value,5) > 250, NULL, substr(value,5)))
FROM src;

DROP TEMPORARY FUNCTION example_max;
