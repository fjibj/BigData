impala-kudu��impala-shell�µ�kerberos��֤��Ҫ��
��10.45.53.76��hd3)��ִ�У�
kinit -k -t /var/run/cloudera-scm-agent/process/2974-impala-IMPALAD/impala.keytab impala/hd3@ZTESOFT.COM
��10.45.53.75��hd2)��ִ�У�
kinit -k -t /var/run/cloudera-scm-agent/process/2973-impala-IMPALAD/impala.keytab impala/hd2@ZTESOFT.COM
��10.45.53.143��hd1)��ִ�У�
kinit -k -t /var/run/cloudera-scm-agent/process/2970-impala-IMPALAD/impala.keytab impala/hd1@ZTESOFT.COM

��impala-kudu�еĽ�����䣺
CREATE TABLE my_first_table (id BIGINT PRIMARY KEY, name STRING)
DISTRIBUTE BY HASH INTO 16 BUCKETS
STORED AS KUDU
TBLPROPERTIES('kudu.table_name' = 'my_first_table','kudu.master_addresses' = 'hd1:7051,hd3:7051')