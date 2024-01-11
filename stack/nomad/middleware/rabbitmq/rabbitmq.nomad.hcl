job "rabbitmq-deploy" {
  datacenters = ["dc1"]
  type        = "service"

  group "rabbitmq" {
    network {
      port "amqp" {
        static = 5672
      }
      port "ui" {
        static = 15672
      }
    }
    task "rabbitmq-task" {
      driver = "docker"

      env = {
        RABBITMQ_DEFAULT_USER = "admin"
        RABBITMQ_DEFAULT_PASS = "password"
      }

      config {
        image = "rabbitmq:3-management"
        ports = ["amqp", "ui"]
      }
      resources {
        cpu    = 512
        memory = 1024
      }
    }
  }
}