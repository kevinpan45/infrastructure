# Dependencies
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

# Spec
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

# Guideline
* How to create a springboot project
* How to use config springboot project property
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