��pig loader����phoenix��         CDH5.4.7

cdhĬ����װ��pig�ģ�Ŀ¼��/opt/cloudera/parcels/CDH/lib/pig

$pig
grunt>   --��������Ͷ���

grunt>quit --�˳�

$vi staff.pig

REGISTER /opt/phoenix/phoenix-4.5.2-HBase-1.0-client.jar;
rows = load 'hbase://table/"STAFF"' USING org.apache.phoenix.pig.PhoenixHBaseLoader('hd1,hd2,hd3:2181');
STORE rows INTO 'staff' USING PigStorage(',');

$pig -x mapreduce staff.pig

���������hdfs://hd2:8020/user/tcy/staff Ŀ¼��

ȡ�ر���
hadoop fs -get staff.csv/part-m-00000 staff.csv

