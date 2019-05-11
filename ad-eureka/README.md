# AD-Eureka

## 单机测试多节点高可用

### 配置 hosts

修改`/etc/hosts`添加以下内容

```
127.0.0.1 server1.local
127.0.0.1 server2.local
127.0.0.1 server3.local
```

### 打包 ad-eureka 

```bash
$ mvn clean package -Dmaven.text.skip=true -U
```

### 启动

```bash
$ cd ad-eureka/target/
$ java -jar ad-eureka-1.0-SNAPSHOT.jar --spring.profiles.active=server1 
$ java -jar ad-eureka-1.0-SNAPSHOT.jar --spring.profiles.active=server2 
$ java -jar ad-eureka-1.0-SNAPSHOT.jar --spring.profiles.active=server3
```
