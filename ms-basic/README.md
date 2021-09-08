# Spec
* Response
  * [x] Content-type
  * [x] Body
  * [x] Exception
  * [x] Status code
* Security
  * [ ] Authentication filter
  * [ ] Token switch to subject
* Logging
  * [x] log format
  * [x] log level
  * [x] log file
* Common Utils
  * [ ] verification code
  * [x] upload and download
  * [x] zip
  * [x] codec
  * [x] spring application context
* Observability
  * [ ] Database connection pool metrics
  * [ ] Springboot metrics
  * [ ] System load
  * [ ] RPC metrics
  * [ ] log

# CLI List
```bash
# Write dependency tree to file, local in ignore path ./target/dependency_tree.txt
mvn dependency:tree -Dverbose outputFile=target/dependency_tree.txt
```