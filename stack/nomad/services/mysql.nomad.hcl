job "mysql-deploy" {
  datacenters = ["dc1"]
  type        = "service"

  group "mysql" {
    volume "edge-stack" {
      type      = "host"
      source    = "edge-stack"
      read_only = false
    }

    network {
      port "mysql" {
        static = 3306
      }
    }

    task "mysql-task" {
      driver = "docker"

      // set default password
      env = {
        "MYSQL_ROOT_PASSWORD" = "password"
      }

      config {
        image = "mysql:8.1.0"
        ports = ["mysql"]
        volumes = [
          "/data/edge-stack/${NOMAD_JOB_NAME}:/var/lib/mysql"
        ]
      }
    }
  }
}
