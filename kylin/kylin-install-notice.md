1. ��kylin�����Ͻ�/opt/cloudera/parcels/CDH/lib/spark/lib/spark-assembly-1.6.0-cdh5.8.0-hadoop2.6.0-cdh5.8.0.jar���ӵ�/opt/cloudera/parcels/CDH/lib/hive/libĿ¼�£���:
cd /opt/cloudera/parcels/CDH/lib/hive/lib
ln -s ../../spark/lib/spark-assembly-1.6.0-cdh5.8.0-hadoop2.6.0-cdh5.8.0.jar spark-assembly.jar
2. ����Caused by: org.apache.kylin.job.exception.ExecuteException: java.lang.NoSuchMethodError: org.apache.hadoop.mapreduce.Job.setJar(Ljava/lang/String;)V���⣺
$cd /opt/cloudera/parcels/CDH/lib
$find . -name '*.jar' -exec bash -c 'jar -tf {} | grep -iH --label {} org/apache/hadoop/mapreduce/Job.class' \;(С���ɣ��ڵ�ǰĿ¼����Ŀ¼��jar���в���ĳ���ࣩ
������������
./hbase/lib/phoenix-4.8.0-cdh5.8.0-client.jar:org/apache/hadoop/mapreduce/Job.class
����������
��kylin�����ű���hbase classpath�а�����/opt/cloudera/parcels/CDH-5.8.0-1.cdh5.8.0.p0.42/lib/hadoop/libexec/../../hadoop/.//*������ʵ������ hadoop�����������Ŀ¼Ҳ������client-0.20Ŀ¼
$cd /opt/cloudera/parcels/CDH-5.8.0-1.cdh5.8.0.p0.42/jars/

ɾ��./hbase/lib/phoenix-4.8.0-cdh5.8.0-client.jar(��Ҫ��

����kylin