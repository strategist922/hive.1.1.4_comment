-- test for describe extended table
-- test for describe extended table partition
-- test for alter table drop partition
DROP TABLE INPUTDDL6;
CREATE TABLE INPUTDDL6(KEY STRING, VALUE STRING) PARTITIONED BY(ds STRING) STORED AS TEXTFILE;
LOAD DATA LOCAL INPATH '../data/files/kv1.txt' INTO TABLE INPUTDDL6 PARTITION (ds='2008-04-09');
LOAD DATA LOCAL INPATH '../data/files/kv1.txt' INTO TABLE INPUTDDL6 PARTITION (ds='2008-04-08');
DESCRIBE EXTENDED INPUTDDL6;
DESCRIBE EXTENDED INPUTDDL6 PARTITION (ds='2008-04-08');
SHOW PARTITIONS INPUTDDL6;
ALTER TABLE INPUTDDL6 DROP PARTITION (ds='2008-04-08');
SHOW PARTITIONS INPUTDDL6;
DROP TABLE INPUTDDL6;
EXPLAIN
DESCRIBE EXTENDED INPUTDDL6 PARTITION (ds='2008-04-09');
