# Microservice parent project
* Language: Java 11
* Microservice: Spring cloud
* Discovery and Config: Spring cloud config (Consul)
* Cache: Redis
* MQ: Kafka
* RDB: MySQL
* RDB conn pool: Hirika
* ORM: Mybatis-Plus
* Util: hutool
* Test: JUnit

## Build & Deploy
[Sonatype offical doc](https://central.sonatype.org/publish/publish-maven/)
### Snapshot version

```
mvn clean deploy
```
### Release version
```
mvn clean deploy -P release
```

If occurs error "gpg: signing failed: Inappropriate ioctl for device" when build, execute command
```
export GPG_TTY=$(tty)
```