# Build
```bash
# Create
mvn archetype:create-from-project
# Deploy to maven repository
cd target/generated-sources/archetype
mvn install
```

# Usage
```bash
mvn archetype:generate -B \
-DarchetypeGroupId=io.github.kevinpan45 \
-DarchetypeArtifactId=kp45-quickstart-archetype \
-DarchetypeVersion=1.0.0-SNAPSHOT \
-DgroupId=[YOUR_BASE_PACKAGE] \
-DartifactId=[YOUR_PROJECT_NAME] \
-Dversion=[YOUR_PROJECT_VERSION] -DarchetypeCatalog=local
```

***-DarchetypeCatalog=local arg for archetype has not deploy to Maven Central Repository***

Create project by archetype script sample
```
mvn archetype:generate -B -DarchetypeGroupId=io.github.kevinpan45 -DarchetypeArtifactId=kp45-quickstart-archetype -DarchetypeVersion=1.0.0-SNAPSHOT -DgroupId=io.github.kevinpan45 -DartifactId=test -Dversion=0.0.1-SNAPSHOT -DarchetypeCatalog=local
```

# Design

## Dependencies
* Language: Java 11
* Microservice: Spring cloud
* Discovery and Config: Spring cloud config (Consul)
* Cache: Redis
* MQ: Kafka
* RDB: MySQL
* RDB conn pool: Hirika
* DBVM: flyway
* ORM: Mybatis-Plus
* JSON: Jackson
* Test: JUnit

## Spec
* Response
  * Content-type
  * Body
  * Exception
  * Status code
* Security
  * WAF/SQL Firewall/Malware Check
  * Authentication filter
  * Token switch to subject
* Logging
  * log format
  * log level
  * log file
* Common Utils
  * verification code
  * upload and download
  * zip
  * codec
  * spring application context

## Guideline
* How to create a springboot project
* How to use config and use springboot project property
  * Define a variable
  * @Configuration annotation
* How to use springboot lifecycle events
* How to implement restful API
* How to validate request parameter
* How to process upload file
* How to get authentication and authorization info
* How to handle exception
* How to use MyBatis-Plus
  * ORM
  * SQL
* How to use flyway to manage database version
* How to invoke a RPC call
  * Feign
  * GRpc
  * HTTP client
* How to use middleware
  * Redis
  * Kafka
* How to schedule a task
* How to close springboot application