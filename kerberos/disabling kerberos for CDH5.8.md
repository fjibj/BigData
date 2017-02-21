关键步骤：

1. 先在CM中将各组件配置中与security、kerbeos、authen、acl相关的设置都关掉

2. 在CM中在zookeeper的设置zoo.cfg中添加skipAcl=yes ！！！

3. 重启集群及部署客户端

4. 在CM中关闭hbase服务

5. 在hbase zkcli中运行
   rmr /hbase (注，也可以对/hbase及其子目录分别执行setAcl /hbase/xxx world:anyone:cdrwa，但目录众多，不及rmr干脆）

6. 在CM中启动hbase服务（重启后会自动在zookeeper中创建/hbase及其子目录）
