impala-kudu的impala-shell下的kerberos认证需要：
在10.45.53.76（hd3)上执行：
kinit -k -t /var/run/cloudera-scm-agent/process/2974-impala-IMPALAD/impala.keytab impala/hd3@ZTESOFT.COM
在10.45.53.75（hd2)上执行：
kinit -k -t /var/run/cloudera-scm-agent/process/2973-impala-IMPALAD/impala.keytab impala/hd2@ZTESOFT.COM
在10.45.53.143（hd1)上执行：
kinit -k -t /var/run/cloudera-scm-agent/process/2970-impala-IMPALAD/impala.keytab impala/hd1@ZTESOFT.COM

在impala-kudu中的建表语句：
CREATE TABLE my_first_table (id BIGINT PRIMARY KEY, name STRING)
DISTRIBUTE BY HASH INTO 16 BUCKETS
STORED AS KUDU
TBLPROPERTIES('kudu.table_name' = 'my_first_table','kudu.master_addresses' = 'hd1:7051,hd3:7051')