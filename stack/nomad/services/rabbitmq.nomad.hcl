job "rabbitmq-deploy" {
  datacenters = ["dc1"]
  type        = "service"

  group "rabbitmq" {
    task "rabbitmq-task" {
      driver = "docker"
      config {
        image = "rabbitmq:latest"
      }
    }
  }
}
