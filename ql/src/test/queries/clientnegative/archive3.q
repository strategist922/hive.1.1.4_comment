set hive.archive.enabled = true;
-- Tests archiving a table
-- EXCLUDE_HADOOP_MAJOR_VERSIONS(0.17, 0.18, 0.19, 0.19.1-dc)

ALTER TABLE srcpart ARCHIVE;
