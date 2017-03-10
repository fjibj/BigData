1. 在kylin机器上将/opt/cloudera/parcels/CDH/lib/spark/lib/spark-assembly-1.6.0-cdh5.8.0-hadoop2.6.0-cdh5.8.0.jar链接到/opt/cloudera/parcels/CDH/lib/hive/lib目录下，即:
cd /opt/cloudera/parcels/CDH/lib/hive/lib
ln -s ../../spark/lib/spark-assembly-1.6.0-cdh5.8.0-hadoop2.6.0-cdh5.8.0.jar spark-assembly.jar
2. 遇到Caused by: org.apache.kylin.job.exception.ExecuteException: java.lang.NoSuchMethodError: org.apache.hadoop.mapreduce.Job.setJar(Ljava/lang/String;)V问题：
$cd /opt/cloudera/parcels/CDH/lib
$find . -name '*.jar' -exec bash -c 'jar -tf {} | grep -iH --label {} org/apache/hadoop/mapreduce/Job.class' \;(小技巧：在当前目录及子目录的jar包中查找某个类）
。。。。。。
./hbase/lib/phoenix-4.8.0-cdh5.8.0-client.jar:org/apache/hadoop/mapreduce/Job.class
。。。。。
而kylin启动脚本在hbase classpath中包含了/opt/cloudera/parcels/CDH-5.8.0-1.cdh5.8.0.p0.42/lib/hadoop/libexec/../../hadoop/.//*这里其实包含了 hadoop下面的所有子目录也包含了client-0.20目录
$cd /opt/cloudera/parcels/CDH-5.8.0-1.cdh5.8.0.p0.42/jars/

删除./hbase/lib/phoenix-4.8.0-cdh5.8.0-client.jar(重要）

重启kylin