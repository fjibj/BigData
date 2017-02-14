经过一段时间的研究，发现spark2.0支持通过kinit方式传递访问hbase的用户，并支持创建PhoenixRDD，但spark1.6不支持。

通过重新编译phoenix-for-cloudera-4.8-HBase-1.2-cdh5.8，并将其中的phoenix-spark目录源代码更新为支持spark2.0的master分支代码（保持其它目录不变）
mvn clean package -e -Dspark.version=2.0.0.cloudera1  -Dcdh.flume.version=1.6.0 -Dmaven.test.failure.ignore=true

在命令行运行
spark2-shell -v --master yarn-client --jars /opt/phoenix/phoenix-4.8.0-cdh5.8.0-client.jar,/opt/phoenix/lib/phoenix-spark-4.8.0-cdh5.8.0.jar --conf "spark.driver.extraClassPath=/etc/hbase/conf:/opt/phoenix/lib/phoenix-spark-4.8.0-cdh5.8.0.jar:/opt/phoenix/phoenix-4.8.0-cdh5.8.0-client.jar"  --conf "spark.executor.extraClassPath=/etc/hbase/conf:/opt/phoenix/lib/phoenix-spark-4.8.0-cdh5.8.0.jar:/opt/phoenix/phoenix-4.8.0-cdh5.8.0-client.jar"

以下代码在spark2-shell中运行通过
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
