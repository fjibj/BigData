用pig loader导出phoenix表         CDH5.4.7

cdh默认是装了pig的，目录是/opt/cloudera/parcels/CDH/lib/pig

$pig

grunt>   --出现这个就对了

grunt>quit --退出

$vi staff.pig

REGISTER /opt/phoenix/phoenix-4.5.2-HBase-1.0-client.jar;

rows = load 'hbase://table/"STAFF"' USING org.apache.phoenix.pig.PhoenixHBaseLoader('hd1,hd2,hd3:2181');

STORE rows INTO 'staff' USING PigStorage(',');


$pig -x mapreduce staff.pig

结果保存在hdfs://hd2:8020/user/tcy/staff 目录中

取回本地

hadoop fs -get staff.csv/part-m-00000 staff.csv

