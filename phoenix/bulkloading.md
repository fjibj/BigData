mapreduce方式批量导入数据到phoenix表

事先需要上传一些jar包到/opt/phoenix/lib下，包括：tephra-api-0.8.0-incubating.jar,tephra-core-0.8.0-incubating.jar,tephra-hbase-compat-1.1-0.8.0-incubating.jar,twill-discovery-api-0.6.0-incubating.jar,twill-zookeeper-0.6.0-incubating.jar,joda-time-1.6.jar.

[root@hd1 phoenix]# cd /opt/phoenix

[root@hd1 phoenix]# HADOOP_CLASSPATH=$(hbase mapredcp):/etc/hbase/conf:lib/phoenix-core-4.8.0-cdh5.8.0.jar:lib/tephra-core-0.
8.0-incubating.jar:lib/twill-discovery-api-0.6.0-incubating.jar:lib/twill-zookeeper-0.6.0-incubating.jar:lib/joda-time-1.6.jar hadoop jar phoenix-4.8.0-cdh5.8.0-client.jar org.apache.phoenix.mapreduce.CsvBulkLoadTool --libjars lib/phoenix-core-4.8.0-cdh5.8.0.jar,lib/tephra-api-0.8.0-incubating.jar,lib/tephra-core-0.8.0-incubating.jar,lib/tephra-hbase-compat-1.1-0.8.0-incubating.jar,lib/twill-discovery-api-0.6.0-incubating.jar,lib/twill-zookeeper-0.6.0-incubating.jar,lib/joda-time-1.6.jar,lib/libthrift-0.9.3.jar,lib/commons-csv-1.0.jar --table SPC_REGION --input spc_region.csv 
