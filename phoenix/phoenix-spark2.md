����һ��ʱ����о�������spark2.0֧��ͨ��kinit��ʽ���ݷ���hbase���û�����֧�ִ���PhoenixRDD����spark1.6��֧�֡�

ͨ�����±���phoenix-for-cloudera-4.8-HBase-1.2-cdh5.8���������е�phoenix-sparkĿ¼Դ�������Ϊ֧��spark2.0��master��֧���루��������Ŀ¼���䣩
mvn clean package -e -Dspark.version=2.0.0.cloudera1  -Dcdh.flume.version=1.6.0 -Dmaven.test.failure.ignore=true

������������
spark2-shell -v --master yarn-client --jars /opt/phoenix/phoenix-4.8.0-cdh5.8.0-client.jar,/opt/phoenix/lib/phoenix-spark-4.8.0-cdh5.8.0.jar --conf "spark.driver.extraClassPath=/etc/hbase/conf:/opt/phoenix/lib/phoenix-spark-4.8.0-cdh5.8.0.jar:/opt/phoenix/phoenix-4.8.0-cdh5.8.0-client.jar"  --conf "spark.executor.extraClassPath=/etc/hbase/conf:/opt/phoenix/lib/phoenix-spark-4.8.0-cdh5.8.0.jar:/opt/phoenix/phoenix-4.8.0-cdh5.8.0-client.jar"

���´�����spark2-shell������ͨ��
import org.apache.hadoop.hbase.client.HBaseAdmin
import org.apache.hadoop.hbase.{HBaseConfiguration, HTableDescriptor}
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.security.UserGroupInformation
import org.apache.hadoop.hbase.security.User
import org.apache.phoenix.spark._
import java.security.PrivilegedExceptionAction
import org.apache.spark._
val pdd = new PhoenixRDD(sc, "STATISTICS_INFO", Array("ROWKEY","TABLE_NAME"),conf = sc.hadoopConfiguration)
pdd.count
