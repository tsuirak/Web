



# 启动配置

***nginx***

> 主机：***macOsBigSur***

```bash
/usr/local/Cellar/nginx # 安装目录

# /usr/local/Cellar/nginx/1.19.4/bin
./nginx -c /usr/local/etc/nginx/nginx.conf # bin 目录下启动 nginx
```

***nacos***

> 主机：***macOsBigSur***

```bash
/usr/local/nacos/nacos-1.1.4 # 安装目录

#/usr/local/nacos/nacos-1.1.4/bin
sh startup.sh -m standalone # bin目录下启动 nacos

localhost:8848/nacos # 访问
username:nacos
password:nacos
```

***redis***

> 虚拟机：***centos7***

```bash
/usr/local/bin # 安装目录
/opt/etc/edu-parent-conf/redis.conf # 配置文件目录

# /usr/local/bin
./redis-server /opt/etc/edu-parent-conf/redis.conf # bin 目录下启动 redis
```

