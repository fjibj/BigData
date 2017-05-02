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

val rows = 1000000
val cols = 50
var myMatrix = Array.ofDim[Int](rows,cols)
for (i <- 0 to rows-1){for(j <- 0 to cols-1){myMatrix(i)(0)=i;if(j>0) {myMatrix(i)(j) = j;}}}
var rdd1 = sc.makeRDD(myMatrix)

var rdd2 = rdd1.repartition(5000)
val start_t = System.currentTimeMillis()
rdd2.foreach(line => {Thread.sleep(300)})
print("foreach:"+(System.currentTimeMillis() - start_t)/1000+" seconds")



sc.hadoopConfiguration.set(TableOutputFormat.OUTPUT_TABLE,"fj1234")
val job = new Job(sc.hadoopConfiguration)
job.setOutputKeyClass(classOf[ImmutableBytesWritable])
job.setOutputValueClass(classOf[Result])
job.setOutputFormatClass(classOf[TableOutputFormat[ImmutableBytesWritable]])
val t0 = System.currentTimeMillis()
val data = rdd1.map( x => {var put = new Put(Bytes.toBytes(x(0)));  for( i <- 1 to cols-1){ put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("c"+i), Bytes.toBytes(x(i))); };  (new ImmutableBytesWritable,put)})
val t1 = System.currentTimeMillis()
println("map:"+(t1 - t0)/1000 +" seconds")
data.saveAsNewAPIHadoopDataset(job.getConfiguration)
val spent = (System.currentTimeMillis() - t1)/1000
println("rows:"+ rows +",cols:"+cols)
println("spent:" + spent +"seconds")
println("speed:"+ rows/spent +"/seconds")

val start_t = System.currentTimeMillis()
val incRdd = rdd1.map(x => { var jbinfo:Map[String,Int] = Map(); for(i <- 0 to cols - 1) { val c = "c"+i;  val v = x(i);if( i == 0) {jbinfo += ("row" -> x(0)); } ; jbinfo += (c -> v); }; jbinfo; }).filter(_("c2")==2)
val tt = System.currentTimeMillis()
print("filter:"+( tt - start_t)/1000+" seconds")
val lRdd = incRdd.collect()
print("collect:"+(System.currentTimeMillis() - tt)/1000+" seconds")
   

var rdd2 = rdd1.repartition(20)
val start_t = System.currentTimeMillis()
rdd2.foreachPartition(f => {f.foreach(line => {Thread.sleep(1)})})
print("foreach:"+(System.currentTimeMillis() - start_t)/1000+" seconds")



val start_t = System.currentTimeMillis()
var t = 0
for(i <- 0 to rows-1) { Thread.sleep(1) }
println("spent:"+(System.currentTimeMillis() - start_t)/1000+" seconds")


import java.util.concurrent.{Callable, FutureTask, Executors, ExecutorService}

object Test {
  def main(args: Array[String]) {
    val threadPool:ExecutorService=Executors.newFixedThreadPool(3)
    try {
      val future=new FutureTask[String](new Callable[String] {
        override def call(): String = {
          Thread.sleep(100)
          return "im result"
        }
      })
      threadPool.execute(future)
      println(future.get())
    }finally {
      threadPool.shutdown()
    }
  }
}


import java.util.concurrent.{Callable, FutureTask, Executors, ExecutorService, TimeUnit}

object Test {
  def main(args: Array[String]) {
    //创建线程池
    val threadPool:ExecutorService=Executors.newFixedThreadPool(1000)
    try {
      //提交5个线程
      val start_t = System.currentTimeMillis()
      for(i <- 1 to 1000){ threadPool.execute(new ThreadDemo("thread"+i))}
      threadPool.shutdown()
      threadPool.awaitTermination(1, TimeUnit.DAYS)
      print("multithreads:"+(System.currentTimeMillis() - start_t)/1000+" seconds")
    }finally {
      threadPool.shutdown()
    }
  }

  //定义线程类，每打印一次睡眠100毫秒
  class ThreadDemo(threadName:String) extends Runnable{ override def run(){ for(i <- 1 to 100){Thread.sleep(300) }}}
  
 }


import java.util.concurrent.{Callable, FutureTask, Executors, ExecutorService, TimeUnit}
//每个线程处理100条，每条处理300毫秒，总共30秒
class ThreadDemo(threadName:String) extends Runnable{ override def run(){ for(i <- 1 to 100){Thread.sleep(300) }}}
//1000个线程并发执行
val threadPool:ExecutorService=Executors.newFixedThreadPool(1000)
val start_t = System.currentTimeMillis()
for(i <- 1 to 1000){ threadPool.execute(new ThreadDemo("thread"+i))}
threadPool.shutdown()
threadPool.awaitTermination(1, TimeUnit.DAYS)
print("multithreads:"+(System.currentTimeMillis() - start_t)/1000+" seconds")
//每个线程处理1条，300毫秒
class ThreadDemo2(threadName:String) extends Runnable{ override def run(){ for(i <- 1 to 10){Thread.sleep(300) }}}
//10000个线程并发
var num = 10000
val threadPool:ExecutorService=Executors.newFixedThreadPool(num)
val start_t = System.currentTimeMillis()
for(i <- 1 to num){ threadPool.execute(new ThreadDemo2("thread"+i))}
threadPool.shutdown()
threadPool.awaitTermination(1, TimeUnit.DAYS)
print("multithreads:"+(System.currentTimeMillis() - start_t)/1000+" seconds")

class ThreadDemo3 extends Runnable{ override def run(){ Thread.sleep(300)}}
var t0 = System.currentTimeMillis()
var rdd2 = rdd1.repartition(1000)
val start_t = System.currentTimeMillis()
print("repartition:"+(System.currentTimeMillis() - t0)/1000+" seconds")
rdd2.foreachPartition(f => {val threadPool:ExecutorService=Executors.newFixedThreadPool(1000);f.foreach(line => {threadPool.execute(new ThreadDemo3());});threadPool.shutdown();threadPool.awaitTermination(1, TimeUnit.DAYS);})
print("foreach:"+(System.currentTimeMillis() - start_t)/1000+" seconds")
