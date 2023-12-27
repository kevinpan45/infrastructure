job "rabbitmq-deploy" {
  datacenters = ["dc1"]
  type        = "service"

  group "rabbitmq" {
    network {
      port "amqp" {
        static = 5672
      }
    }
    task "rabbitmq-task" {
      driver = "docker"
      config {
        image = "rabbitmq:latest"
        ports = ["amqp"]
      }
      resources {
        cpu    = 512
        memory = 1024
      }
    }
  }
}
