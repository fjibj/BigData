经过几天的折腾，终于完成了基于centos7制作guacd镜像并支持SSH连接时正常显示中文

总结一下，有几点需要注意：

1. 要设置LANG和LC_ALL为zh_CN.UTF-8

2. 必须 yum -y install kde-l10n-Chinese telnet           && \
    yum -y reinstall glibc-common                    && \
    yum clean all                                    && \
    localedef -c -f UTF-8 -i zh_CN zh_CN.utf8        && \
    cat /dev/null > /etc/locale.conf
    
详见本目录下 Dockerfile.c7
