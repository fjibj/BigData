C:一致性：指的是分布式中一个数据多个节点的备份是一样的。这就要求对数据备份的每个节点的操作是一个原子事务，要么都成功，要么都失败。所以MIT证明CAP理论的时候，认为这个叫原子性更准确。
A:对集群的访问，无论正确或错误，都能在一定的时间范围内返回响应。
P:集群中部分节点或分区失效，不会导致整体系统失效。

分析一下HADOOP，其主要放弃的是C，也就是说，在某个时刻，数据在三个节点可能只存在两份（比如写三份的时候，只写对了两份）。

分析一下HBASE，其主要放弃的是A，也就是说，在某个时刻，会访问HBASE，无论结果正确或错误，他都不返回，返回的是网络响应错误。（某个Region  Server挂掉了）。

参考 [http://lovemitter.blog.163.com/blog/static/165798134201242112745849/](http://lovemitter.blog.163.com/blog/static/165798134201242112745849/)

HBase写请求分析 

参考：[https://blog.csdn.net/pun_c/article/details/46841679](https://blog.csdn.net/pun_c/article/details/46841679)

HBase最佳实践－HBase中的写性能优化策略 

参考 [https://blog.csdn.net/ourpush/article/details/53558292](https://blog.csdn.net/ourpush/article/details/53558292)
