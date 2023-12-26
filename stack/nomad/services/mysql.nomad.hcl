job "mysql-deploy" {
  datacenters = ["dc1"]
  type        = "service"

  group "mysql" {
    task "mysql-task" {
      driver = "docker"

      // set default password
      env = {
        "MYSQL_ROOT_PASSWORD" = "password"
      }

      config {
        image = "mysql:8.1.0"
      }
    }
  }
}
