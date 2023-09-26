job "middleware-deploy" {
  datacenters = ["dc1"]
  type        = "service"

  group "redis" {
    count = 1
    task "redis-task" {
      driver = "docker"
      config {
        image = "redis:latest"
      }
    }
  }
  group "vault" {
    task "vault-task" {
      driver = "docker"
      config {
        image = "vault:1.13.3"
      }
    }
  }
  group "mysql" {
    task "mysql-task" {
      driver = "docker"

      // set default password
      env = {
        "MYSQL_ROOT_PASSWORD" = "password"
      }

      csi_plugin {
        id                     = "csi-hostpath"
        type                   = "node"
        mount_dir              = "/var/lib/mysql"
        stage_publish_base_dir = "/data/local-stack/mysql-data"
        health_timeout         = "30s"
      }

      config {
        image = "mysql:8.1.0"
      }

    }
  }
  group "rabbitmq" {
    task "rabbitmq-task" {
      driver = "docker"
      config {
        image = "rabbitmq:latest"
      }
    }
  }
}
