import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import SparkContext._
import org.apache.hadoop.hbase.{CellUtil, HBaseConfiguration}
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapred.JobConf
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.client.Result
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.client.Put
import java.util.concurrent.{Callable, FutureTask, Executors, ExecutorService, TimeUnit}

//����һ��10W�У�500�е�RDD
val rows = 100000
val cols = 500
var myMatrix = Array.ofDim[Int](rows,cols)
for (i <- 0 to rows-1){
	for(j <- 0 to cols-1){
		myMatrix(i)(0)=i;
		if(j>0) {
			myMatrix(i)(j) = j;
		}
	}
}
var rdd1 = sc.makeRDD(myMatrix)

//����300����
class ThreadDemo3 extends Runnable{ 
	override def run(){
		Thread.sleep(300)
	}
}

var t0 = System.currentTimeMillis()
//�ط���
var rdd2 = rdd1.repartition(1000)
val start_t = System.currentTimeMillis()
print("repartition:"+(System.currentTimeMillis() - t0)/1000+" seconds")
rdd2.foreachPartition(f => {
	//��ÿ���������̷ֱ߳���
	val threadPool:ExecutorService=Executors.newFixedThreadPool(100);
	f.foreach(line => {
		threadPool.execute(new ThreadDemo3());
	});
	threadPool.shutdown();
	threadPool.awaitTermination(1, TimeUnit.DAYS);
})
print("foreach:"+(System.currentTimeMillis() - start_t)/1000+" seconds")

