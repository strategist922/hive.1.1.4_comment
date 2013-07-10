set hive.archive.enabled = true;
-- Tests trying to unarchive a non-archived partition
-- EXCLUDE_HADOOP_MAJOR_VERSIONS(0.17, 0.18, 0.19, 0.19.1-dc)

ALTER TABLE srcpart UNARCHIVE PARTITION (ds='2008-04-08', hr='12');
